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
/*
This file is part of XOWA: the XOWA Offline Wiki Application
Copyright (C) 2013 matthiasjasny@gmail.com; gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the following files:

GPLv3 License: LICENSE-GPLv3.txt
Apache License: LICENSE-APACHE2.txt
*/
package gplx.xowa.apps.servers.http; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.servers.*;
import gplx.core.threads.*; import gplx.core.net.*; import gplx.core.primitives.*; import gplx.core.envs.*;
import gplx.langs.jsons.*; import gplx.langs.htmls.encoders.*;
import gplx.xowa.wikis.pages.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.addons.wikis.searchs.gui.htmlbars.*;
import gplx.xowa.specials.*; import gplx.xowa.specials.xowa.errors.*;
public class Http_server_mgr implements Gfo_invk {
	private final    Object thread_lock = new Object();
	private final    Gfo_usr_dlg usr_dlg;
	private final    Http_server_socket wkr;
	private byte retrieve_mode = File_retrieve_mode.Mode_wait;
	private boolean running = true;
	public Http_server_mgr(Xoae_app app) {
		this.app = app;
		this.wkr = new Http_server_socket(this);
		this.usr_dlg = app.Usr_dlg();
		this.request_parser = new Http_request_parser(server_wtr, false);
	}
	public Xoae_app App() {return app;} private final    Xoae_app app;
	public Http_server_wtr Server_wtr() {return server_wtr;} private final    Http_server_wtr server_wtr = Http_server_wtr_.New__console();
	public Http_request_parser Request_parser() {return request_parser;} private final    Http_request_parser request_parser;
	public Gfo_url_encoder Encoder() {return encoder;} private final    Gfo_url_encoder encoder = Gfo_url_encoder_.New__http_url().Make();
	public int Port() {return port;} 
	public Http_server_mgr Port_(int v, boolean caller_is_cfg) {
		if (	caller_is_cfg
			&&	v == Port__default		// new_val == 8080
			&&  port != Port__default) {// cur_val != 8080
			return this;				// exit; do not override command-line value with cfg_value
		}
		port = v; 
		return this;
	}
	private int port = Port__default;
	public Http_server_wkr_pool Wkr_pool() {return wkr_pool;} private final    Http_server_wkr_pool wkr_pool = new Http_server_wkr_pool();
	public Int_pool Uid_pool() {return uid_pool;} private final    Int_pool uid_pool = new Int_pool();
	public byte[] Home() {return home;} public void Home_(byte[] v) {home = Bry_.Add(Byte_ascii.Slash_bry, v);} private byte[] home = Bry_.new_a7("/home/wiki/Main_Page");
	private void Running_(boolean val) {
		if (val) {
			if (running)
				Note("HTTP Server already started");
			else {
				Run();
			}
		}
		else {
			if (running) {
				wkr.Canceled_(true);
				Note("HTTP Server stopped");
			}
			else
				Note("HTTP Server not started");
		}
		running = val;
	}
	public void Run() {
		app.Cfg().Bind_many_app(this, Cfg__port, Cfg__file_retrieve_mode);

		// create a shim gui to automatically handle default XOWA gui JS calls
		Gxw_html_server.Init_gui_for_server(app, null);

		// launch listener
		Thread_adp_.Start_by_key("thread:xowa.http_server.server", wkr, Http_server_socket.Invk_run);
		Note("HTTP Server started: Navigate to http://localhost:" + Int_.To_str(port));
	}
	public void Run_xowa_cmd(Xoae_app app, String url_encoded_str) {
		Gfo_url_encoder url_converter = Gfo_url_encoder_.New__http_url().Make();	// create instance for each call
		String cmd = url_converter.Decode_str(url_encoded_str);
		app.Gfs_mgr().Run_str(cmd);
	}

	public Http_server_page Parse_page_to_html(Http_data__client data__client, Http_url_parser url_parser) {
		synchronized (thread_lock) {
			return Http_server_page.Make(app, data__client, url_parser, retrieve_mode);
		}
	}
	public String Preview_page_to_html(Http_data__client data__client, byte[] wiki_domain, byte[] ttl_bry, byte[] new_text) {
		synchronized (thread_lock) {
			// get the wiki
			Xowe_wiki wiki = (Xowe_wiki)app.Wiki_mgr().Get_by_or_make_init_y(wiki_domain);			// assert init for Main_Page; EX:click zh.w on wiki sidebar; DATE:2015-07-19
			if (Runtime_.Memory_total() > Io_mgr.Len_gb)	Xowe_wiki_.Rls_mem(wiki, true);			// release memory at 1 GB; DATE:2015-09-11

			// get the url / ttl
			if (Bry_.Len_eq_0(ttl_bry)) ttl_bry = wiki.Props().Main_page();
			Xoa_url url = wiki.Utl__url_parser().Parse(ttl_bry);
			Xoa_ttl ttl = Xoa_ttl.Parse(wiki, url.To_bry_page_w_anch()); // changed from ttl_bry to page_w_anch; DATE:2017-07-24

			// from Xog_tab_itm_edit_mgr.java
			Xoa_ttl page = Xoa_ttl.Parse(wiki, ttl_bry, 0, ttl_bry.length);
			Xoae_page new_page = Xoae_page.New(wiki, page);
			new_page.Db().Page().Id_(0);	// NOTE: page_id needed for sqlite (was not needed for xdat)
			new_page.Db().Text().Text_bry_(new_text);
			wiki.Parser_mgr().Parse(new_page, true);			// refresh html
	
			Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_m001();
			try {
				Xoh_page_wtr_wkr wkr = wiki.Html_mgr().Page_wtr_mgr().Wkr(Xopg_view_mode_.Tid__read);
				wkr.Write_body(tmp_bfr, wiki.Parser_mgr().Ctx(), Xoh_wtr_ctx.Basic, new_page);
				byte[] new_html = tmp_bfr.To_bry_and_rls();
	                        new_html = wiki.Html__hdump_mgr().Load_mgr().Parse(new_html, new_page);
	
				new_html = Http_server_wkr.Replace_fsys_hack(new_html);
				String rv = String_.new_u8(new_html);
				return rv;
				} finally {tmp_bfr.Mkr_rls();}
		}
	}
	private void Note(String s) {
		// usr_dlg.Prog_many("", "", s); // messages should write to progress bar for gui
		usr_dlg.Note_many("", "", s);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__port))					Port_(m.ReadInt("v"), true);
		else if	(ctx.Match(k, Cfg__file_retrieve_mode))		retrieve_mode = File_retrieve_mode.Xto_byte(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_running_))				Running_(m.ReadYn("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String  Invk_running_ = "running_";
	private static final String
	  Cfg__port					= "xowa.addon.http_server.port"
	, Cfg__file_retrieve_mode	= "xowa.addon.http_server.file_retrieve_mode";
	private static final int Port__default = 8080;

	private static byte[]
	  api = Bry_.new_a7("Search:Api")
	;
}
