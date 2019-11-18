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
package gplx.xowa.apps.servers.http; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.servers.*;
import gplx.core.envs.*;
import gplx.xowa.guis.views.*;
import gplx.xowa.specials.*; import gplx.xowa.specials.xowa.errors.*;
public class Http_server_page {
	private final    Xoae_app app;
	private Http_url_parser url_parser;
	public Http_server_page(Xoae_app app, Http_url_parser url_parser) {
		this.app = app;
		this.url_parser = url_parser;
	}
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public Xoa_url Url() {return url;} private Xoa_url url;
	public Xoa_ttl Ttl() {return ttl;} private Xoa_ttl ttl;
	public byte[] Ttl_bry() {return ttl_bry;} private byte[] ttl_bry;
	public Xog_tab_itm Tab() {return tab;} private Xog_tab_itm tab;
	public Xoae_page Page() {return page;} private Xoae_page page;
	public String Html() {return html;} private String html;
	public byte[] Redirect() {return redirect;} private byte[] redirect;
	public static Http_server_page Make(Xoae_app app, Http_data__client data__client, Http_url_parser url_parser, byte retrieve_mode) {
	//Http_data__client data__client, byte[] wiki_domain, byte[] ttl_bry, byte[] qarg, byte retrieve_mode, byte mode, boolean popup_enabled, String popup_mode, String popup_id) {
		Http_server_page page = new Http_server_page(app, url_parser);
		if (!page.Make_url(url_parser.Wiki())) return page; // exit early if xwiki
		page.Make_page(data__client);
		page.Make_html(retrieve_mode);
		return page;
	}
	public boolean Make_url(byte[] wiki_domain) {

		// get wiki
		wiki = (Xowe_wiki)app.Wiki_mgr().Get_by_or_make_init_y(wiki_domain); // assert init for Main_Page; EX:click zh.w on wiki sidebar; DATE:2015-07-19
/*            // check for a valid directory
		Io_url domain_dir = wiki.Fsys_mgr().Root_dir();
		if (!Io_mgr.Instance.ExistsDir(domain_dir)) {
                    this.redirect = Bry_.Add(Bry_.new_a7("fsys/nosuchdomain.html?link="), wiki_domain);
                    return false;
                }
*/
		if (!wiki.Installed()) {
			this.ttl = wiki.Ttl_parse(Xow_special_meta_.Itm__error.Ttl_bry());
			this.url = wiki.Utl__url_parser().Parse(Xoerror_special.Make_url__invalidWiki(wiki_domain));
			return true;
		}
		if (Runtime_.Memory_total() > Io_mgr.Len_gb) Xowe_wiki_.Rls_mem(wiki, true); // release memory at 1 GB; DATE:2015-09-11

		// get url
		// empty title returns main page; EX: "" -> "Main_Page"
		this.ttl_bry = url_parser.Page();
		if (Bry_.Len_eq_0(ttl_bry)) {
			this.ttl_bry = wiki.Props().Main_page();
		}
		// generate ttl of domain/wiki/page; needed for pages with leading slash; EX: "/abcd" -> "en.wikipedia.org/wiki//abcd"; ISSUE#:301; DATE:2018-12-16
		else {
			Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_m001();
			try {
				tmp_bfr.Add(wiki.Domain_bry()).Add(gplx.xowa.htmls.hrefs.Xoh_href_.Bry__wiki).Add(ttl_bry).Add_safe(url_parser.Qarg());
				this.ttl_bry = tmp_bfr.To_bry_and_clear();
			} finally {tmp_bfr.Mkr_rls();}
		}

		// get url
		this.url = wiki.Utl__url_parser().Parse(ttl_bry);
		if (!Bry_.Eq(url.Wiki_bry(), wiki.Domain_bry())) { // handle xwiki; EX: en.wikipedia.org/wiki/it:Roma; ISSUE#:600; DATE:2019-11-02
			this.redirect = url.To_bry();
			return false;
		}

		// get ttl
		this.ttl = wiki.Ttl_parse(url.To_bry_page_w_anch()); // changed from ttl_bry to page_w_anch; DATE:2017-07-24
		if (ttl == null) { // handle invalid titles like "Earth]"; ISSUE#:480; DATE:2019-06-02
			this.ttl = wiki.Ttl_parse(Xow_special_meta_.Itm__error.Ttl_bry());
			this.url = wiki.Utl__url_parser().Parse(Xoerror_special.Make_url__invalidTitle(ttl_bry));
		}
		return true;
	}
	public void Make_page(Http_data__client data__client) {
		// get the page
		this.tab = Gxw_html_server.Assert_tab2(app, wiki);	// HACK: assert tab exists
		this.page = wiki.Page_mgr().Load_page(url, ttl, tab, url_parser.Display());
		app.Gui_mgr().Browser_win().Active_page_(page);	// HACK: init gui_mgr's page for output (which server ordinarily doesn't need)
		if (page.Db().Page().Exists_n()) { // if page does not exist, replace with message; else null_ref error; DATE:2014-03-08
			page.Db().Text().Text_bry_(Bry_.new_a7("'''Page not found.'''"));
			wiki.Parser_mgr().Parse(page, false);
		}
		page.Html_data().Head_mgr().Itm__server().Init_by_http(data__client).Enabled_y_();
	}
	public void Make_html(byte retrieve_mode) {
// byte retrieve_mode, byte mode, boolean popup_enabled, String popup_mode, String popup_id
		byte[] page_html;
		// generate html
		if (url_parser.Popup()) {
			String popup_id = url_parser.Popup_id();
			if (String_.Eq(url_parser.Popup_mode(), "more"))
				page_html = wiki.Html_mgr().Head_mgr().Popup_mgr().Show_more(popup_id);
			else
				page_html = wiki.Html_mgr().Head_mgr().Popup_mgr().Show_init(popup_id, ttl_bry, ttl_bry, url_parser.Popup_link());
			//page_html = Bry_.Replace_many(page_html, app.Fsys_mgr().Root_dir().To_http_file_bry(), Http_server_wkr.Url__fsys);
			page_html = Http_server_wkr.Replace_fsys_hack(page_html);
			this.html = String_.new_u8(page_html);
		}
		else {
			byte mode = url_parser.Action();
			page_html = wiki.Html_mgr().Page_wtr_mgr().Gen(page, mode);

			//page_html = Bry_.Replace_many(page_html, app.Fsys_mgr().Root_dir().To_http_file_bry(), Http_server_wkr.Url__fsys);
			page_html = Http_server_wkr.Replace_fsys_hack(page_html);
			this.html = String_.new_u8(page_html); // NOTE: must generate HTML now in order for "wait" and "async_server" to work with text_dbs; DATE:2016-07-10
			boolean rebuild_html = false;
			switch (retrieve_mode) {
				case File_retrieve_mode.Mode_skip:	// noop
					break;
				case File_retrieve_mode.Mode_async_server:
					rebuild_html = true;
					app.Gui_mgr().Browser_win().Page__async__bgn(tab);
					break;
				case File_retrieve_mode.Mode_wait:
					if (page != null) {
						if (page.File_queue().Count() > 0
						    || page.Html_data().Xtn_gallery_packed_exists()
						    || page.Html_data().Xtn_imap_exists()
						    || page.File_math().Count() > 0
						    || page.Html_cmd_mgr().Count() > 0) {

							rebuild_html = true;
							gplx.xowa.guis.views.Xog_async_wkr.Async(page, tab.Html_itm());
							this.page = wiki.Page_mgr().Load_page(url, ttl, tab, url_parser.Display());	// HACK: fetch page again so that HTML will now include img data
						}
					}
					//rebuild_html = true;
					//gplx.xowa.guis.views.Xog_async_wkr.Async(page, tab.Html_itm());
					//this.page = wiki.Page_mgr().Load_page(url, ttl, tab, url_parser.Display());	// HACK: fetch page again so that HTML will now include img data
					break;
			}
			if (rebuild_html) {
				page_html = wiki.Html_mgr().Page_wtr_mgr().Gen(page, mode);
				//page_html = Bry_.Replace_many(page_html, app.Fsys_mgr().Root_dir().To_http_file_bry(), Http_server_wkr.Url__fsys);
				page_html = Http_server_wkr.Replace_fsys_hack(page_html);
				this.html = String_.new_u8(page_html);
			}
		}
	}
}
