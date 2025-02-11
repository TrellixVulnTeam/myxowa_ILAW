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
package gplx.xowa.htmls.heads; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*; import gplx.xowa.langs.numbers.*;
import gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.doubles.*;
public class Xoh_head_itm__globals extends Xoh_head_itm__base {
	private final    Xoh_head_wtr tmp_wtr = new Xoh_head_wtr();
	@Override public byte[] Key() {return Xoh_head_itm_.Key__globals;}
	@Override public int Flags() {return Flag__css_include | Flag__js_include | Flag__js_head_script | Flag__js_tail_script | Flag__js_head_global;}
	@Override public void Write_css_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		if (Url_core_css == null) {
			Url_core_css = app.Fsys_mgr().Bin_any_dir().GenSubFil_nest("xowa", "html", "res", "src", "xowa", "core", "core.css").To_http_file_bry();
			Url_core_redirect_css = app.Fsys_mgr().Bin_any_dir().GenSubFil_nest("xowa", "html", "res", "src", "xowa", "core", "core_redirect.css").To_http_file_bry();
		}
		wtr.Write_css_include(app.Gui_mgr().Redirect_mgr().Enabled() ? Url_core_redirect_css : Url_core_css);
	}
	@Override public void Write_js_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		if (Url_core_js == null) {
			Io_url core_dir = app.Fsys_mgr().Bin_any_dir().GenSubDir_nest("xowa", "html", "res", "src", "xowa", "core");
			Url_core_js				= core_dir.GenSubFil("core.js").To_http_file_bry();
//			Url_exec_js				= core_dir.GenSubFil("exec.js").To_http_file_bry();
			Url_DOMContentLoaded_js = core_dir.GenSubFil("DOMContentLoaded.js").To_http_file_bry();
			Io_url j_dir = app.Fsys_mgr().Bin_any_dir().GenSubDir_nest("xowa", "html", "res", "lib", "jquery");
                        Url_jquery = j_dir.GenSubFil("jquery-3.3.1.js").To_http_file_bry();
                        Url_jquery_webfont = j_dir.GenSubFil("jquery.webfonts.js").To_http_file_bry();
			Url_redirect_js = app.Fsys_mgr().Bin_any_dir().GenSubDir_nest("xowa", "html", "res", "lib").GenSubFil("mediawiki.action.view.redirect.js").To_http_file_bry();
		}
		wtr.Write_js_include(Url_jquery);
		wtr.Write_js_include(Url_jquery_webfont);
		wtr.Write_js_include(Url_core_js);
//		wtr.Write_js_include(Url_exec_js);
		wtr.Write_js_include(Url_DOMContentLoaded_js);

                if (page.Redirect_trail().Itms__len() > 0) {
		wtr.Write_js_include(Url_redirect_js);
                }
                    
	}
	@Override public void Write_js_head_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_js_var(Var_xowa_root_dir			, Bool_.Y, app.Fsys_mgr().Root_dir().To_http_file_bry());
		wtr.Write_js_var(Var_xowa_mode_is_server	, Bool_.N, app.Tcp_server().Running() ? Bool_.True_bry : Bool_.False_bry);
	}
	@Override public void Write_js_tail_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_js_xowa_var(Key__app_mode, Bool_.Y, app.Mode().Name());
		wtr.Write_js_alias_var	(Page__alias, Page__key);
		wtr.Write_js_alias_kv	(Page__alias, Key__wiki		, page.Wiki().Domain_bry());

		wtr.Write_js_alias_kv	(Page__alias, Key__ttl		, page.Ttl().Page_db());
		wtr.Write_js_alias_kv	(Page__alias, Key__guid		, Bry_.new_a7(page.Page_guid().To_str()));
	}	private static final    byte[] Key__app_mode = Bry_.new_a7("xowa.app.mode"), Page__alias = Bry_.new_a7("x_p"), Page__key = Bry_.new_a7("xowa.page"), Key__wiki = Bry_.new_a7("wiki"), Key__ttl = Bry_.new_a7("ttl"), Key__guid = Bry_.new_a7("guid");
	@Override public void Write_js_head_global(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_js_global_ini_atr_val(Key_mode_is_gui			, app.Mode().Tid_is_gui());
		wtr.Write_js_global_ini_atr_val(Key_mode_is_http		, app.Mode().Tid_is_http());
		wtr.Write_js_global_ini_atr_val(Key_http_port			, app.Http_server().Port());
		wtr.Write_js_global_ini_atr_msg(wiki, Key_sort_ascending);
		wtr.Write_js_global_ini_atr_msg(wiki, Key_sort_descending);
		wtr.Write_js_global_ini_atr_msg(wiki, Key_sort_initial);
		wtr.Write_js_global_ini_atr_msg(wiki, Key_brackets);
		wtr.Write_js_global_ini_atr_msg(wiki, Key_word_separator);
		Xol_lang_itm lang = wiki.Lang(); Xow_msg_mgr msg_mgr = wiki.Msg_mgr();
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
		tmp_wtr.Init(tmp_bfr);
		byte[] months_long = Html_js_table_months(tmp_wtr, msg_mgr, Xol_msg_itm_.Id_dte_month_name_january, Xol_msg_itm_.Id_dte_month_name_december);
		byte[] months_short = Html_js_table_months(tmp_wtr, msg_mgr, Xol_msg_itm_.Id_dte_month_abrv_jan, Xol_msg_itm_.Id_dte_month_abrv_dec);
		byte[] num_format_separators = Html_js_table_num_format_separators(tmp_wtr, lang.Num_mgr().Separators_mgr());
		tmp_bfr.Mkr_rls();
		wtr.Write_js_global_ini_atr_val(Key_wgContentLanguage			, lang.Key_bry());
		wtr.Write_js_global_ini_atr_obj(Key_wgSeparatorTransformTable	, num_format_separators);
		wtr.Write_js_global_ini_atr_obj(Key_wgDigitTransformTable		, Num_format_digits);
		wtr.Write_js_global_ini_atr_val(Key_wgDefaultDateFormat			, Date_format_default);
		wtr.Write_js_global_ini_atr_obj(Key_wgMonthNames				, months_long);
		wtr.Write_js_global_ini_atr_obj(Key_wgMonthNamesShort			, months_short);
		Xoa_ttl ttl = page.Ttl();
		//tmp_bfr.Add_byte_apos().Add_int_variable(page.Ttl().Ns().Id()).Add_byte_apos();
		//wtr.Write_js_global_ini_atr_obj(Key_wgNamespaceNumber			, tmp_bfr.To_bry_and_clear());
		wtr.Write_js_global_ini_atr_obj(Key_wgNamespaceNumber			, Int_.To_bry(page.Ttl().Ns().Id()));
		//tmp_bfr.Add_byte_apos().Add(ttl.Full_db()).Add_byte_apos();
		wtr.Write_js_global_ini_atr_val(Key_wgPageName			, ttl.Full_db());
		//tmp_bfr.Add_byte_apos().Add(ttl.Full_txt_w_ttl_case()).Add_byte_apos();
		byte[] rv = ttl.Page_db(); // no namespace
		Bry_.Replace_reuse(rv, Byte_ascii.Underline, Byte_ascii.Space);
		wtr.Write_js_global_ini_atr_val(Key_wgTitle			, rv);
		//tmp_bfr.Add_byte_apos().Add(ttl.Ns().Name_db()).Add_byte_apos();
		wtr.Write_js_global_ini_atr_val(Key_wgCanonicalNamespace		, ttl.Ns().Name_db());
		wtr.Write_js_global_ini_atr_val(Key_wgPageContentLanguage		, page.Lang().Key_bry());
		wtr.Write_js_global_ini_atr_val(Key_wgUserLanguage		, page.Lang().Key_bry());
		if (page.Url().Qargs_ary().length == 0) { // poor way to determine view mode
			wtr.Write_js_global_ini_atr_val(Key_wgAction			, Bry_view);
		} else {
			wtr.Write_js_global_ini_atr_val(Key_wgAction			, Bry_other);
		}
		wtr.Write_js_global_ini_atr_val(Key_wgPopupsGateway, Bry_.new_a7("restbaseHTML"));

		Bry_bfr tmp = wtr.Bfr();
		//wtr.Write_js_global_ini_atr_val(Key_wgPopupsRestGatewayEndpoint, Bry_.new_a7("/api/rest_v1/page/summary/"));
		//tmp.Add(Bry_.new_a7("\n \"wgPopupsRestGatewayEndpoint\" : \"/xowa/api/"));
		//tmp.Add(page.Wiki().Domain_bry());
		//tmp.Add(Bry_.new_a7("/rest_v1/page/summary/\","));
		tmp.Add(Bry_.new_a7("\n \"wgPopupsRestGatewayEndpoint\" : \"/xowa/"));
		tmp.Add(page.Wiki().Domain_bry());
		tmp.Add(Bry_.new_a7("/wiki/\","));

		tmp.Add(Bry_.new_a7("\n \"wgArticlePath\" : \"/site/"));
		tmp.Add(page.Wiki().Domain_bry());
		tmp.Add(Bry_.new_a7("/wiki/$1\","));

		if (page.Redirect_trail().Itms__len() > 0) {
			tmp.Add(Bry_.new_a7("\n \"wgInternalRedirectTargetUrl\" : \""));
			tmp.Add(page.Ttl().Full_url());
			tmp.Add(Bry_.new_a7("\","));
		}
		// list of categories (not hidden)
		tmp.Add(Bry_.new_a7("\n \"wgCategories\":["));
		Xoctg_double_grp grp = page.Grp_normal();
		if (grp != null)
			grp.Itms().List_categories(tmp);
		tmp.Add(Bry_.new_a7("],"));

		// if any related entries generate
		List_adp lst = page.Html_data().Related().List();
		int len = lst.Count();
		if (len > 0) {
			tmp.Add(Bry_.new_a7("\n \"wgRelatedArticles\":["));
			for (int i = 0; i < len; i++) {
				if (i > 0)
					tmp.Add_byte(Byte_ascii.Comma);
				tmp.Add_byte(Byte_ascii.Quote).Add((byte[])lst.Get_at(i)).Add_byte(Byte_ascii.Quote); // should handle titles with double quotes
			}
			tmp.Add(Bry_.new_a7("],"));
		}
		wtr.Write_js_global_ini_atr_val(Key_red_link_msg, wiki.Msg_mgr().Find_or_null(Key_red_link_msg).Val());
	}
	public static final    byte[]	// NOTE: most of these are for the table-sorter
	  Key_mode_is_gui					= Bry_.new_a7("mode_is_gui")
	, Key_mode_is_http					= Bry_.new_a7("mode_is_http")
	, Key_http_port						= Bry_.new_a7("http-port")
	, Key_sort_ascending				= Bry_.new_a7("sort-ascending")
	, Key_sort_descending				= Bry_.new_a7("sort-descending")
	, Key_sort_initial				= Bry_.new_a7("sort-initial")
	, Key_wgContentLanguage				= Bry_.new_a7("wgContentLanguage")
	, Key_wgSeparatorTransformTable		= Bry_.new_a7("wgSeparatorTransformTable")
	, Key_wgDigitTransformTable			= Bry_.new_a7("wgDigitTransformTable")
	, Key_wgDefaultDateFormat			= Bry_.new_a7("wgDefaultDateFormat")
	, Key_wgMonthNames					= Bry_.new_a7("wgMonthNames")
	, Key_wgMonthNamesShort				= Bry_.new_a7("wgMonthNamesShort")
	, Key_wgNamespaceNumber				= Bry_.new_a7("wgNamespaceNumber")
	, Key_wgPageName  				= Bry_.new_a7("wgPageName")
	, Key_wgTitle				= Bry_.new_a7("wgTitle")
	, Key_wgCanonicalNamespace			= Bry_.new_a7("wgCanonicalNamespace")
	, Key_wgPageContentLanguage			= Bry_.new_a7("wgPageContentLanguage")
	, Key_wgUserLanguage			= Bry_.new_a7("wgUserLanguage")
	, Key_wgAction				= Bry_.new_a7("wgAction")
	, Key_wgPopupsRestGatewayEndpoint				= Bry_.new_a7("wgPopupsRestGatewayEndpoint")
	, Key_wgPopupsGateway				= Bry_.new_a7("wgPopupsGateway")
	, Bry_view					= Bry_.new_a7("view")
	, Bry_other					= Bry_.new_a7("other")
	, Key_brackets				= Bry_.new_a7("brackets")
	, Key_word_separator				= Bry_.new_a7("word-separator")
	, Key_red_link_msg				= Bry_.new_a7("red-link-title")
	;
	private static byte[] Html_js_table_months(Xoh_head_wtr tmp_wtr, Xow_msg_mgr msg_mgr, int january_id, int december_id) {
		tmp_wtr.Write_js_ary_bgn();
		tmp_wtr.Write_js_ary_itm(Bry_.Empty);	// 1st month is always empty
		for (int i = january_id; i <= december_id; i++)
			tmp_wtr.Write_js_ary_itm(msg_mgr.Val_by_id(i));
		tmp_wtr.Write_js_ary_end();
		return tmp_wtr.Bfr().To_bry_and_clear();
	}
	private static byte[] Html_js_table_num_format_separators(Xoh_head_wtr tmp_wtr, Xol_transform_mgr separator_mgr) {
		byte[] dec_spr = separator_mgr.Get_val_or_self(Xol_num_mgr.Separators_key__dec);
		byte[] grp_spr = separator_mgr.Get_val_or_self(Xol_num_mgr.Separators_key__grp);
		tmp_wtr.Write_js_ary_bgn();
		tmp_wtr.Write_js_ary_itm(Bry_.Add(dec_spr, Byte_ascii.Tab_bry, Byte_ascii.Dot_bry));
		tmp_wtr.Write_js_ary_itm(Bry_.Add(grp_spr, Byte_ascii.Tab_bry, Byte_ascii.Comma_bry));
		tmp_wtr.Write_js_ary_end();
		return tmp_wtr.Bfr().To_bry_and_clear();
	}
	private static final    byte[]
	  Date_format_default			= Bry_.new_a7("dmy")
	, Num_format_digits				= Bry_.new_a7("['', '']")
	, Var_xowa_root_dir				= Bry_.new_a7("xowa_root_dir")
	, Var_xowa_mode_is_server		= Bry_.new_a7("xowa_mode_is_server")
	;
	private static byte[] Url_core_css, Url_core_redirect_css, Url_core_js, Url_exec_js, Url_DOMContentLoaded_js, Url_jquery, Url_jquery_webfont, Url_redirect_js;
}
