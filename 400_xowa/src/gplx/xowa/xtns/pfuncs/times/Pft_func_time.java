/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pft_func_time extends Pf_func_base {
	Pft_func_time(boolean utc) {this.utc = utc;} private boolean utc;
	@Override public int Id() {return Xol_kwd_grp_.Id_xtn_time;}
	@Override public Pf_func New(int id, byte[] name) {return new Pft_func_time(utc).Name_(name);}
	//@Override public boolean Func_require_colon_arg() {return true;} // 20210425 enwiki Moalboal
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {// REF.MW:ParserFunctions_body.php
		int self_args_len = self.Args_len();
		byte[] arg_fmt = Eval_argx(ctx, src, caller, self);
		Pft_fmt_itm[] fmt_ary = Pft_fmt_itm_.Parse(arg_fmt);
		byte[] arg_date = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 0);
		byte[] arg_lang = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 1);
		byte[] local = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 2);
		Bry_bfr error_bfr = Bry_bfr_.New();
		boolean local_utc = true;
		if (local != null && ((local.length == 1 && local[0] != '0') || (local.length > 1)))
			local_utc = false;
		DateAdp date = ParseDate(arg_date, local_utc, error_bfr, ctx);
		Xowe_wiki wiki = ctx.Wiki();
		if (date == null || error_bfr.Len() > 0)
			bfr.Add_str_a7("<strong class=\"error\">").Add_bfr_and_clear(error_bfr).Add_str_a7("</strong>");
		else {
			Xol_lang_itm lang = ctx.Lang();
			if (Bry_.Len_gt_0(arg_lang)) {
				int lang_len = arg_lang.length;
				if (lang_len == 5 && Bry_.Eq(arg_lang, Bry_.new_a7("local")))
					arg_lang = Bry_.new_a7("en");
				Xol_lang_stub specified_lang_itm = Xol_lang_stub_.Get_by_key_or_null(arg_lang);
				if (specified_lang_itm != null) {	// NOTE: if lang_code is bad, then ignore (EX:bad_code)
					Xol_lang_itm specified_lang = wiki.Appe().Lang_mgr().Get_by_or_load(arg_lang);
					lang = specified_lang;	
				}
			}
			wiki.Parser_mgr().Date_fmt_bldr().Format(bfr, wiki, lang, date, fmt_ary);
		}
	}
	public static DateAdp ParseDate(byte[] date, boolean utc, Bry_bfr error_bfr, Xop_ctx ctx) {
		DateAdp rv = null;
		// check for four digits - always treat as a year
		// underlying date parser (PHP) treats four digits as mmdd
		//System.out.println(String_.new_u8(date));
		if (date.length == 4) {
			boolean isyear = true;
			for (int i = 0; i < 4; i++) {
				byte b = date[i];
				if (b < '0' || b > '9') {
					isyear = false;
					break;
				}
			}
			if (isyear)
				date = Bry_.Add(zerohours, date);
		}
		if (date == Bry_.Empty)
			rv = Datetime_now.Get().XtoUtc();
		else {
			try {
				rv = new Pxd_parser(ctx).Parse(date, error_bfr, ctx.Page().Ttl());
			}
			catch (Exception exc) {
				Err_.Noop(exc);
//                                System.out.println("invalid date/time:" + String_.new_u8(date));
				error_bfr.Add_str_a7("Invalid time");
			}
		}
		if (rv != null && !utc)
			rv.SetTimeZone(ctx.Wiki().Tz_mgr().Get_zoneid());
		return rv;
	}
	private static final byte[] zerohours = Bry_.new_a7("00:00 ");
	public static final    Pft_func_time _Lcl = new Pft_func_time(false), _Utc = new Pft_func_time(true);
}
class DateAdpTranslator_xapp {
	public static void Translate(Xowe_wiki wiki, Xol_lang_itm lang, int type, int val, Bry_bfr bb) {
		byte[] itm_val = lang.Msg_mgr().Val_by_id(type + val); 
		if (itm_val != null)
			bb.Add(itm_val);
	}
}
