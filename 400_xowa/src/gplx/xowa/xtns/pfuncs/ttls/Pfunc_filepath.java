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
package gplx.xowa.xtns.pfuncs.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.core.envs.*; import gplx.core.caches.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.imgs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.Xoctg_catpage_mgr;
public class Pfunc_filepath extends Pf_func_base {
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public int Id() {return Xol_kwd_grp_.Id_url_filepath;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_filepath().Name_(name);}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		// init
		// takes a second arg 20211018 ('nowiki' or a number for size of a thumbnail)
		byte[] val_ary = Eval_argx(ctx, src, caller, self); if (val_ary == Bry_.Empty) return;
		byte[] arg2 = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self.Args_len(), 0);
		Xowe_wiki wiki = ctx.Wiki();
		Xoa_ttl ttl = wiki.Ttl_parse(Xow_ns_.Tid__file, val_ary); if (ttl == null) return; // text is not valid ttl; exit;
		Pfunc_filepath_itm commons_itm = Load_page(wiki, ttl); if (!commons_itm.Exists()) return; // page not found in wiki or commons; exit;
		byte[] ttl_bry = commons_itm.Page_url();

		// look for file
		Xofw_file_finder_rslt tmp_rslt = wiki.File_mgr().Repo_mgr().Page_finder_locate(ttl_bry);
		if (tmp_rslt.Repo_idx() == Byte_.Max_value_127) return;
		Xof_repo_itm trg_repo = wiki.File_mgr().Repo_mgr().Repos_get_at(tmp_rslt.Repo_idx()).Trg();
		Xof_xfer_itm xfer_itm = ctx.Tmp_mgr().Xfer_itm();
		xfer_itm.Orig_ttl_and_redirect_(ttl_bry, Bry_.Empty);	// redirect is empty b/c Get_page does all redirect lookups
		byte[] url;
		if (arg2.length != 0 && arg2[0] != 'n') { // ignore 'nowiki' and assume if present it is a number
			//generate Number (where is the library routine?
			int alen = arg2.length;
			int value = 0;
			for (int i = 0; i < alen; i++) {
				value = value * 10 + (arg2[i] - '0');
			}
			url = wiki.Parser_mgr().Url_bldr().Init_for_trg_html(trg_repo, Xof_img_mode_.Tid__thumb, ttl_bry, xfer_itm.Orig_ttl_md5(), xfer_itm.Orig_ext(), value, Xof_lnki_time.Null, Xof_lnki_page.Null).Xto_bry();
		}
		else
			url = wiki.Parser_mgr().Url_bldr().Init_for_trg_html(trg_repo, Xof_img_mode_.Tid__orig, ttl_bry, xfer_itm.Orig_ttl_md5(), xfer_itm.Orig_ext(), Xof_img_size.Size__neg1, Xof_lnki_time.Null, Xof_lnki_page.Null).Xto_bry();
		bfr.Add(url);
	}
	public static Pfunc_filepath_itm Load_page(Xowe_wiki wiki, Xoa_ttl ttl) {
		// try to get from cache
		byte[] cache_key = ttl.Page_url();
		//Gfo_cache_mgr cache_mgr = wiki.Cache_mgr().Commons_cache();
		//Pfunc_filepath_itm cache_itm = (Pfunc_filepath_itm)cache_mgr.Get_by_key(cache_key);
		Pfunc_filepath_itm cache_itm = Get_by(cache_key);
		if (cache_itm != null) return cache_itm;

		// do db retrieval
		Xoae_page page = wiki.Data_mgr().Load_page_by_ttl(ttl);
		if (page.Db().Page().Exists_n()) {				// file not found in current wiki; try commons; 
			Xowe_wiki commons_wiki = (Xowe_wiki)wiki.Appe().Wiki_mgr().Get_by_or_null(wiki.Commons_wiki_key());
			if (commons_wiki != null) {		// commons_wiki not installed; exit; DATE:2013-06-08
				if (!Env_.Mode_testing()) {
					synchronized (commons_wiki) {	// LOCK:app-level; wiki.commons; DATE:2016-07-06
						commons_wiki.Init_assert(0);// must assert load else page_zip never detected; DATE:2013-03-10
					}
				}
				try {
					Xoctg_catpage_mgr.rwl.writeLock().lock();
					page = commons_wiki.Data_mgr().Load_page_by_ttl(ttl);
				}
				finally {
					Xoctg_catpage_mgr.rwl.writeLock().unlock();
				}
			}
		}

		// add to cache
		cache_itm = Addx(cache_key, page.Db().Page().Exists());
		//cache_itm = new Pfunc_filepath_itm(page.Db().Page().Exists(), cache_key);
		//cache_mgr.Add_replace(cache_key, cache_itm, 1);
		return cache_itm;
	}
	private static Pfunc_filepath_itm Get_by(byte[] ttl) {
		byte[] serial = Db_redis.Get_filecache(ttl);
		if (serial == null)
			return null;
		return new Pfunc_filepath_itm(serial, ttl);
	}
	private static Pfunc_filepath_itm Addx(byte[] ttl, boolean exists) {
		Db_redis.Set_filecache(ttl, exists);
		return new Pfunc_filepath_itm(exists, ttl);
	}
}
