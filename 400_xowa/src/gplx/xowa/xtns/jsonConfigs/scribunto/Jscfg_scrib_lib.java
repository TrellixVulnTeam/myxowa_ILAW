/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.jsonConfigs.scribunto;

import gplx.Bry_;
import gplx.Byte_ascii;
import gplx.Err_;
import gplx.Gfo_usr_dlg_;
import gplx.Io_url;
import gplx.Keyval;
import gplx.Keyval_;
import gplx.String_;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.langs.Xol_lang_itm;
import gplx.xowa.wikis.domains.Xow_domain_itm_;
import gplx.xowa.xtns.scribunto.Scrib_core;
import gplx.xowa.xtns.scribunto.Scrib_lib;
import gplx.xowa.xtns.scribunto.Scrib_lua_mod;
import gplx.xowa.xtns.scribunto.libs.Scrib_lib_text;
import gplx.xowa.xtns.scribunto.libs.Scrib_lib_text__json_util;
import gplx.xowa.xtns.scribunto.libs.Scrib_lib_title;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_args;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_mgr;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_rslt;

public class Jscfg_scrib_lib implements Scrib_lib {
	private final Scrib_lib_text__json_util json_util = new Scrib_lib_text__json_util();
	private final Jscfg_localizer localizer = new Jscfg_localizer();
	private Scrib_core core;
	public String Key() {return "JCLuaLibrary";}
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public void Core_(Scrib_core v) {this.core = v;} // TEST:
	public Scrib_lib Clone_lib(Scrib_core core) {return new Jscfg_scrib_lib();}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		this.core = core;
		Init();
		mod = core.RegisterInterface(this, "JCLuaLibrary.lua", core.Core_mgr().Get_text(core.App().Fsys_mgr().Bin_xtns_dir().GenSubDir_nest("JsonConfig"), "JCLuaLibrary.lua"));
		//mod = core.RegisterInterface(this, core.App().Fsys_mgr().Bin_xtns_dir().GenSubFil_nest("JsonConfig", "JCLuaLibrary.lua"));
		return mod;
	}
	public Scrib_proc_mgr Procs() {return procs;} private Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_get:										return Get(args, rslt);
			default: throw Err_.new_unhandled(key);
		}
	}
	private static final int Proc_get = 0;
	public static final String Invk_get = "get";
	private static final    String[] Proc_names = String_.Ary(Invk_get);
	public boolean Get(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] ttl_bry = args.Xstr_bry_or_null(0);
		byte[] langCode = args.Xstr_bry_or_null(1);

		// get language; ISSUE#:779; DATE:2020-08-03
		Xol_lang_itm language = null;
		if (langCode == null) {
			language = this.core.Wiki().Lang();
		}
		else if (!Bry_.Eq(langCode, Byte_ascii.Underline_bry)) {
			language = this.core.Wiki().App().Lang_mgr().Get_by_or_null(langCode);
			if (language == null) { // if not a real language default to "en" 20211209
				language = this.core.Wiki().Lang();
			}
		}
		else {
			language = null;
		}

		// get commons wiki
		Xowe_wiki commons_wiki = (Xowe_wiki)core.App().Wiki_mgr().Get_by_or_null(Xow_domain_itm_.Bry__commons);
		if (commons_wiki == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "jsonConfigs requires commons wiki: ~{0}", ttl_bry);
			return rslt.Init_many_empty();
		}
		commons_wiki.Init_assert(0);

		// get page
		byte[] ttl_in_data_ns = Bry_.Add(gplx.xowa.wikis.nss.Xow_ns_.Bry__data, Byte_ascii.Colon_bry, ttl_bry);
		byte[] page = Scrib_lib_title.GetContentInternal(core, commons_wiki, ttl_in_data_ns);
		if (page == null) {
			// should not be catastrophic
			return rslt.Init_obj(false);
			//throw Err_.new_wo_type("bad argument #1 to 'get' (not a valid title) " + String_.new_u8(ttl_bry));
		}

		// get content
		Keyval[] rv = null;
		if (page == null) {
			rv = Keyval_.Ary_empty;
			Gfo_usr_dlg_.Instance.Warn_many("", "", "bad argument #1 to 'get' (page does not exist): ~{0}", ttl_bry);
		} else {
			rv = Scrib_lib_text.JsonDecodeStatic(args, core, json_util, page, Scrib_lib_text__json_util.Opt__force_assoc, Scrib_lib_text__json_util.Flag__none);
			if (language == null) { // "_" passed in; return entire document
			}
			else {// COMMENT:desb42@: sometime should not be called (dewiki) BUT...
				rv = localizer.Localize(language, page, rv, core.Wiki());
			}
		}

		return rslt.Init_obj(rv);
	}
}
