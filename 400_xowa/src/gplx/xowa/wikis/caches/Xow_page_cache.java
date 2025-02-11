/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.wikis.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.caches.*;
import gplx.core.logs.Gfo_log_wtr;
public class Xow_page_cache {
	private Db_parser dbp = new Db_parser();
	private final Gfo_log_wtr cache_log;
	private boolean noadd = false;
	public void Stop_cache() { noadd = true; }
	private final    Object thread_lock = new Object(); // NOTE: thread-safety needed for xomp since one page-cache is shared across all wkrs
	private final    Object cache_lock = new Object();
	private final    Xowe_wiki wiki;
	private final    xLru_cache cache;
	private long cache_tries = 0;
	private long cache_misses = 0;
	public Xow_page_cache(Xowe_wiki wiki) {
		this.wiki = wiki;
		this.cache_key = "xowa.app.page_cache.'" + wiki.Domain_str() + "'." + this.hashCode();
		//this.cache = new xLru_cache(Bool_.Y, cache_key, 8 * Io_mgr.Len_mb, 16 * Io_mgr.Len_mb);
		this.cache = xLru_cache.Instance;
		this.cache_log = Gfo_log_wtr.New_dflt("page", "cache_log_{0}.csv");
	}
	public String Cache_key() {return cache_key;} private final    String cache_key;
	public void Load_wkr_(Xow_page_cache_wkr v) {this.load_wkr = v;} private Xow_page_cache_wkr load_wkr;
	public void Min_max_(long min, long max) {cache.Min_max_(min, max);}

	public void Add_itm(String ttl_full_db, Xow_page_cache_itm itm) {
		synchronized (thread_lock) {
			cache.Set(ttl_full_db, itm, itm.Cache_len());
		}
	}
	public Xow_page_cache_itm Get_itm_or_null(String ttl_full_db) {
		synchronized (thread_lock) {
			return (Xow_page_cache_itm)cache.Get_or_null(ttl_full_db);
		}
	}
	public Xow_page_cache_itm Get_itm_else_load_or_null(Xoa_ttl ttl, String domain) {
		//synchronized (thread_lock) {
		synchronized (cache_lock) {
			cache_tries++;
		}
		Xow_page_cache_itm rv = (Xow_page_cache_itm)cache.Get_or_null(domain + ttl.Full_db_as_str());
//System.out.println("ttl " + String_.new_u8(ttl.Full_db()));
		if (rv == Xow_page_cache_itm.Missing)
			return null;
		else if (rv == null) {
			synchronized (cache_lock) {
				cache_misses++;
			}
			return Load_page(ttl, domain);
		}
		else {
			synchronized (cache_lock) {
				rv.Access_count_increment();
				return rv;
			}
		}
	}
	public byte[] Get_src_else_load_or_null(Xoa_ttl ttl, String domain) {
		//synchronized (thread_lock) {
			Xow_page_cache_itm rv = Get_itm_else_load_or_null(ttl, domain);
			return rv == null ? null : rv.Wtxt__direct();
		//}
	}
	public void Free_mem(boolean clear_permanent_itms) {
		synchronized (thread_lock) {	// LOCK:app-level; DATE:2016-07-06
			if (clear_permanent_itms) {
				cache.Clear_all();
				Xow_page_cache_itm.Reset();
			}
		}
	}
	private Xow_page_cache_itm Load_page(Xoa_ttl ttl, String domain) {
		// vars
		Xow_page_cache_itm rv = null;
		Xoa_ttl page_ttl = ttl;
		boolean page_exists = false;
		byte[] page_text = null;
		byte[] page_redirect_from = null;
		int page_id = -1;

//System.out.println("ttl " + String_.new_u8(ttl.Full_db()));
		// load_page if load_wkr exists; only for Xop_mediawiki_mgr
		// gplx.core.consoles.Console_adp__sys.Instance.Write_str("page_cache:" + String_.new_u8(ttl_full_db));
		if (load_wkr != null) {
			page_text = load_wkr.Get_page_or_null(ttl.Full_db());
			page_exists = page_text != null;
		}

		synchronized (thread_lock) {
		// load_page in other cases
		if (page_text == null) {
			Xoae_page page = wiki.Data_mgr().Load_page_by_ttl(ttl);	// NOTE: do not call Db_mgr.Load_page; need to handle redirects
			if (    page.Db().Page().Exists()                   // page exists
				||  page.Redirect_trail().Itms__len() > 0 ) {   // page redirects to missing page; note that page.Missing == true and page.Redirected_src() != null; PAGE:en.w:Shah_Rukh_Khan; DATE:2016-05-02
				page_exists = true; // NOTE: page.Db().Page().Exists() will be false if page redirects to missing page; PAGE:en.w:Shah_Rukh_Khan; DATE:2019-06-16
				page_ttl = page.Ttl();
				page_text = page.Db().Text().Text_bry();
				page_redirect_from = page.Redirect_trail().Itms__get_wtxt_at_0th_or_null();
				page_id = page.Db().Page().Id();
			}
		}
		if (!noadd) {

			// create item
			if (page_exists) {
				//page_text = dbp.stripcomments(page_text);
				int src_len = page_text.length;
				int pos = 0;
				while (pos < src_len) {
					if (page_text[pos] == Byte_ascii.Nl)
						pos++;
					else
						break;
				}
				if (pos > 0) {
					page_text = Bry_.Mid(page_text, pos);
				}
				rv = new Xow_page_cache_itm(false, page_id, page_ttl, page_text, page_redirect_from, 0, 0);
				Add_itm(domain + ttl.Full_db_as_str(), rv);
			}
			else {
				rv = null;
				Add_itm(domain + ttl.Full_db_as_str(), Xow_page_cache_itm.Missing);
			}
		}
		}
		return rv;
	}
	public void Cleanup() {
/*		int count = 0;
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		// dump out the cache items
		java.util.Iterator iterator = cache.iterator();
		while(iterator.hasNext()) {
			Xow_page_cache_itm itm = (Xow_page_cache_itm)iterator.next();
			if (itm.Ttl() == null) {
				continue; //int a = 1;
			}
			tmp_bfr.Add(itm.Ttl().Raw());
			tmp_bfr.Add_byte_pipe().Add_int_variable((int)itm.Access_count());
			tmp_bfr.Add_byte_pipe().Add_int_variable((int)itm.Cache_len());
			tmp_bfr.Add_byte_nl();
			cache_log.Write(tmp_bfr);
			//System.out.println( String.format("%8d", itm.Access_count()) + "|" + itm.Ttl() + "|" + itm.Cache_len() );
			count++;
		}
		//System.out.println(count + " " + t);
		cache_log.Flush();
*/
}
	public String To_str() {
		return String_.Format("cache_pct:{0} cache_misses:{1} cache_evicts:{2} cache_tries:{3} cache_size:{4}", Decimal_adp_.divide_(cache_misses * 100, cache_tries).To_str("0.00"), cache_misses, cache.Evicts(), cache_tries, cache.Cur());
	}
}
