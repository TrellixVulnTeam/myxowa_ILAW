/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2022 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.wikis.caches;

import gplx.Bry_bfr;
import gplx.Bry_bfr_;
//import gplx.String_;
import gplx.core.caches.Gfo_cache_mgr;
import gplx.xowa.langs.Xol_lang_itm;
import gplx.xowa.parsers.tmpls.Xot_defn;
import gplx.xowa.wikis.nss.Xow_ns_case_;
import gplx.xowa.DB_case_mgr;

public class Xow_defn_cache {				// stores compiled Xot_defn
	private final    Xol_lang_itm lang;		// needed to lowercase names;
	private final    Bry_bfr upper_1st_bfr = Bry_bfr_.Reset(255);
	private final    Gfo_cache_mgr cache = new Gfo_cache_mgr().Max_size_(64 * 1024 * 1024).Reduce_by_(32 * 1024 * 1024);
	public Xow_defn_cache(Xol_lang_itm lang) {this.lang = lang;}
	public Xot_defn Get_by_key(byte[] name) {return (Xot_defn)cache.Get_by_key(name);}
	public Xot_defn Get_by_key(byte[] name, byte case_match) {
		if (case_match == Xow_ns_case_.Tid__1st) {
//                    upper_1st_bfr.SetThreadId();
//			name = lang.Case_mgr().Case_build_1st_upper(upper_1st_bfr, name, 0, name.length);
                        name = DB_case_mgr.Case_build_1st_reuse(true, name);
                }
		Xot_defn defn = (Xot_defn)cache.Get_by_key(name);
                //System.out.println(Thread.currentThread().getName()+"-defn_cache-" + String_.new_a7(name) + " " + defn);
		return defn;
	}
	public void Free_mem_all()	{
		cache.Clear();
	}
	public void Add(Xot_defn defn, byte case_match) {
		byte[] name = defn.Name();
		if (case_match == Xow_ns_case_.Tid__1st) 
//			name = lang.Case_mgr().Case_build_1st_upper(upper_1st_bfr, name, 0, name.length);
			name = DB_case_mgr.Case_build_1st_reuse(true, name);
                //System.out.println(String_.new_u8(name));
		cache.Add_replace(name, defn, defn.Cache_size());
	}
}
