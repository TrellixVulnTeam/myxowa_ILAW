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
package gplx.xowa.xtns.scribunto;

import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Err;
import gplx.Err_;
import gplx.GfoMsg;
import gplx.Gfo_invk;
import gplx.GfsCtx;
import gplx.String_;
import gplx.core.brys.fmtrs.Bry_fmtr;
import gplx.core.envs.System_;
import gplx.core.threads.Thread_adp;
import gplx.core.threads.Thread_adp_;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.langs.kwds.Xol_kwd_grp_;
import gplx.xowa.langs.msgs.Xol_msg_itm_;
import gplx.xowa.langs.msgs.Xow_msg_mgr;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.logs.Xop_log_invoke_wkr;
import gplx.xowa.parsers.tmpls.Xot_invk;
import gplx.xowa.wikis.nss.Xow_ns;
import gplx.xowa.wikis.nss.Xow_ns_;
import gplx.xowa.xtns.pfuncs.Pf_func;
import gplx.xowa.xtns.pfuncs.Pf_func_;
import gplx.xowa.xtns.pfuncs.Pf_func_base;
import gplx.xowa.xtns.scribunto.cfgs.ScribCfg;
import gplx.xowa.xtns.scribunto.cfgs.ScribCfgResolver;

import gplx.xowa.wikis.nss.Xow_ns_case_;
import gplx.xowa.langs.cases.Xol_case_cvt;
public class Scrib_invoke_func extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_invoke;}
	@Override public Pf_func New(int id, byte[] name) {return new Scrib_invoke_func().Name_(name);}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {// {{#invoke:mod_name|prc_name|prc_args...}}
		//boolean stat_enabled = ctx.Page().Stat_itm().Enabled();
		/*if (stat_enabled)*/ ctx.Page().Stat_itm().Scrib().Bgn();
		Xowe_wiki wiki = ctx.Wiki();
		byte[] mod_name = Eval_argx(ctx, src, caller, self);
		int args_len = self.Args_len();
		byte[] fnc_name = Pf_func_.Eval_arg_or(ctx, src, caller, self, args_len, 0, null);
		if (Bry_.Len_eq_0(mod_name) || args_len < 1) {
			String Err_mod_missing = String_.new_u8(wiki.Msg_mgr().Val_by_key_args(Key_nofunction, (Object[])null));
			Local_error(bfr, wiki.Msg_mgr(), Err_mod_missing);		// EX: "{{#invoke:}}"
			return;
		}
                //if (Bry_.Eq(mod_name, Bry_.new_a7("TemplateBox")) && Bry_.Eq(fnc_name, Bry_.new_a7("templatedata"))) {
/*
                if (Bry_.Eq(mod_name, Bry_.new_a7("Languages")) && Bry_.Eq(fnc_name, Bry_.new_a7("autolang"))) {
                    int a=1;
                }
*/
		Xop_log_invoke_wkr invoke_wkr = ctx.Xtn__scribunto__invoke_wkr();
		long log_time_bgn = 0;
		if (invoke_wkr != null) {
			log_time_bgn = System_.Ticks();
			if (!invoke_wkr.Eval_bgn(ctx.Page(), mod_name, fnc_name)) return;
		}
		Scrib_core core = wiki.Parser_mgr().Scrib().Core();
		if (core == null) {
			synchronized (this) {
				core = wiki.Parser_mgr().Scrib().Core_init(ctx);
				core.Init();
				core.When_page_changed(ctx.Page());
			}
		}
		byte[] mod_raw = null;
			Xow_ns module_ns = wiki.Ns_mgr().Ids_get_or_null(Xow_ns_.Tid__module);
		if (module_ns.Case_match() == Xow_ns_case_.Tid__1st) {
			byte char_1st = mod_name[0];
			int char_1st_len = gplx.core.intls.Utf8_.Len_of_char_by_1st_byte(char_1st);
			int len = mod_name.length;
			if (char_1st_len < len)	// ttl is too too short ignore!!
				mod_name = Xol_case_cvt.Upper_1st(mod_name, 0, len, char_1st_len);
		}
		Scrib_lua_mod mod = core.Mods_get(mod_name);
		if (mod == null) {
			Xoa_ttl mod_ttl = Xoa_ttl.Parse(wiki, Bry_.Add(module_ns.Name_db_w_colon(), mod_name));
			mod_raw = wiki.Cache_mgr().Page_cache().Get_src_else_load_or_null(mod_ttl, wiki.Domain_str());
			if (mod_raw == null) {
				String Err_mod_missing = String_.new_u8(wiki.Msg_mgr().Val_by_key_args(Key_nosuchmodule, null, mod_name));
				Local_error(bfr, wiki.Msg_mgr(), Err_mod_missing); // EX: "{{#invoke:missing_mod}}"
				return;
			}
		}
		else
			mod_raw = mod.Text_bry();
		if (!core.Enabled()) {bfr.Add_mid(src, self.Src_bgn(), self.Src_end()); return;}
                //System.out.println(String_.new_u8(Bry_.Replace_nl_w_tab(src, self.Src_bgn(), self.Src_end())));
		try {
//                    Gfo_usr_dlg_.Instance.Prog_many("", "", "mod ~{0} fnc ~{1}", String_.new_u8(mod_name), String_.new_u8(fnc_name));
/*
int alen = self.Args_len();
String args = "";
for (int i = 0; i < alen; i++) {
    Arg_nde_tkn ant = self.Args_get_by_idx(i);
    args += String_.new_u8(Bry_.Mid(src, ant.Src_bgn(), ant.Src_end())) + ",";
}
System.out.println(String_.new_u8(mod_name) + " " + String_.new_u8(fnc_name) + " " + args);
*/
			//if (String_.Eq(String_.new_u8(mod_name), "Authority control")) {
			//	Tfds.Write(String_.new_u8(ctx.Page().Ttl().Page_db()), String_.new_u8(mod_name), String_.new_u8(fnc_name));
			//}
			// check if configured for threaded execution
			boolean exec = true;
			ScribCfgResolver resolver = wiki.Parser_mgr().Scrib().CfgResolver();
			if (resolver != null) {
				ScribCfg cfg = resolver.Resolve(ctx.Page().Ttl().Page_db(), Xoa_ttl.Replace_spaces(mod_name), fnc_name);
				if (false/*cfg != null*/) {
					if (cfg.TimeoutInMs() != 0) {
						exec = false;
						int timeoutInMs = cfg.TimeoutInMs();
						long timeBgn = System_.Ticks();

						InvokeInvoker invoker = new InvokeInvoker(core, wiki, ctx, src, caller, self, bfr, mod_name, mod_raw, fnc_name);
						Thread_adp thread = Thread_adp_.Start_by_key("scribunto", invoker, "default");
						while (thread.Thread__is_alive()) {
							Thread_adp_.Sleep(cfg.SleepInMs());
							if (System_.Ticks__elapsed_in_frac(timeBgn) > timeoutInMs) {
								thread.Thread__stop();
								invoker.Exc = Err_.new_wo_type(String_.Format("scribunto timeout: page={0} mod={1} func={2} time={3}", ctx.Page_url_str(), mod_name, fnc_name, timeoutInMs));
							}
						}
						if (invoker.Exc != null) {
							throw invoker.Exc;
						}
					}
				}
			}
			// no threaded execution; run sequentially
			if (exec) {
//                            System.out.println("Module:" + String_.new_u8(mod_name));
				core.Invoke(wiki, ctx, src, caller, self, bfr, mod_name, mod_raw, fnc_name);
			}
			if (invoke_wkr != null)
				invoke_wkr.Eval_end(ctx.Page(), mod_name, fnc_name, log_time_bgn);
		}
		catch (Throwable e) {
			Err err = Err_.Cast_or_make(e);
			int error_level = Error(bfr, wiki.Msg_mgr(), err);
			Scrib_err_filter_mgr err_filter_mgr = Scrib_err_filter_mgr.INSTANCE;
			if (	err_filter_mgr.Empty() // err_filter_mgr exists, but no definitions
				||	!err_filter_mgr.Match(String_.new_u8(mod_name), String_.new_u8(fnc_name), err.To_str__msg_only()))	// NOTE: must be To_str__msg_only; err_filter_mgr has defintion and it doesn't match current; print warn; DATE:2015-07-24
                            if (error_level == '0')
				ctx.App().Usr_dlg().Warn_many("", "", "invoke failed level 0: ~{0} ~{1} ~{2}", ctx.Page().Ttl().Raw(), Bry_.Replace_nl_w_tab(src, self.Src_bgn(), self.Src_end()), err.To_str__log());
                            else {
				ctx.App().Usr_dlg().Warn_many("", "", "invoke failed: ~{0} ~{1} ~{2}", ctx.Page().Ttl().Raw(), Bry_.Replace_nl_w_tab(src, self.Src_bgn(), self.Src_end()), err.To_str__log());
			wiki.Parser_mgr().Scrib().Terminate_when_page_changes_y_();	// NOTE: terminate core when page changes; not terminating now, else page with many errors will be very slow due to multiple remakes of core; PAGE:th.d:all; DATE:2014-10-03
                            }
		}
		/*if (stat_enabled)*/ ctx.Page().Stat_itm().Scrib().End();
	}
	public static int Error(Bry_bfr bfr, Xow_msg_mgr msg_mgr, Err err) {return Error(bfr, msg_mgr, Err_.Cast_or_make(err).To_str__top_wo_args());}// NOTE: must use "short" error message to show in wikitext; DATE:2015-07-27
	public static int Error(Bry_bfr bfr, Xow_msg_mgr msg_mgr, String error) {
		// for Luaj, msg combines both err; split out traceback else error message will be very long; note that Warn_many will still log traceback; DATE:2016-09-09
                // also contains error level
		String error_visible = error;
                char error_level = error_visible.charAt(0);
                error_visible = String_.Mid(error_visible, 1); // strip first char
		int traceback_pos = String_.FindFwd(error, "\nstack traceback:\n");	// NOTE: produced by LuaError.getMessage()
		if (traceback_pos != String_.Find_none)
			error_visible = String_.Mid(error_visible, 0, traceback_pos);

		// write "Script error: some error"
		byte[] script_error_msg = msg_mgr.Val_by_id(Xol_msg_itm_.Id_scribunto_parser_error);
		error_fmtr.Bld_bfr_many(bfr, script_error_msg, error_visible);
                return error_level;
	}
	private static final    Bry_fmtr error_fmtr = Bry_fmtr.new_("<strong class=\"error\"><span class=\"scribunto-error\" id=\"mw-scribunto-error-0\">~{0}: ~{1}</span></strong>");	// NOTE: must be "error" not 'error'; iferror checks for quote not apos; DATE:2015-09-17
	private static void Local_error(Bry_bfr bfr, Xow_msg_mgr msg_mgr, String error) {
		local_error_fmtr.Bld_bfr_many(bfr, error);
	}
	private static final    Bry_fmtr local_error_fmtr = Bry_fmtr.new_("<strong class=\"error\"><span class=\"scribunto-error\" id=\"mw-scribunto-error-0\">~{0}</span></strong>");
	private static byte[] Key_nofunction = Bry_.new_a7("scribunto-common-nofunction")
	, Key_nosuchmodule = Bry_.new_a7("scribunto-common-nosuchmodule")
	;
	public static final String Err_mod_missing = "No such module"; // only used by Scrib_lib_mw__invoke_tst.java

	class InvokeInvoker implements Gfo_invk {
		private final Scrib_core core;
		private final Xowe_wiki wiki;
		private final Xop_ctx ctx;
		private final byte[] src;
		private final Xot_invk caller;
		private final Xot_invk self;
		private final Bry_bfr bfr;
		private final byte[] mod_name;
		private final byte[] mod_raw;
		private final byte[] fnc_name;

		public InvokeInvoker(Scrib_core core, Xowe_wiki wiki, Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr, byte[] mod_name, byte[] mod_raw, byte[] fnc_name) {
			this.core = core;
			this.wiki = wiki;
			this.ctx = ctx;
			this.src = src;
			this.caller = caller;
			this.self = self;
			this.bfr = bfr;
			this.mod_name = mod_name;
			this.mod_raw = mod_raw;
			this.fnc_name = fnc_name;
		}
		public Exception Exc;
		public Object Invk(GfsCtx gctx, int ikey, String k, GfoMsg m) {
			try {
				core.Invoke(wiki, ctx, src, caller, self, bfr, mod_name, mod_raw, fnc_name);
			}
			catch (Exception exc) {
				this.Exc = exc;
			}
			return null;
		}
	}
}
