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
package gplx.xowa.htmls.core;

import gplx.Bool_;
import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_find_;
import gplx.Byte_ascii;
import gplx.GfoMsg;
import gplx.Gfo_invk;
import gplx.GfsCtx;
import gplx.Hash_adp_bry;
import gplx.Io_mgr;
import gplx.Io_url;
import gplx.core.ios.Io_stream_zip_mgr;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xow_wiki;
import gplx.xowa.htmls.Xoh_img_mgr;
import gplx.xowa.htmls.Xoh_page;
import gplx.xowa.htmls.core.hzips.Xoh_hzip_dict_;
import gplx.xowa.htmls.core.hzips.Xoh_hzip_mgr;
import gplx.xowa.htmls.core.makes.Xoh_make_mgr;
import gplx.xowa.htmls.heads.Xoh_head_mgr;
import gplx.xowa.wikis.data.Xow_db_file;
import gplx.xowa.wikis.data.tbls.Xowd_page_itm;
import gplx.xowa.wikis.pages.Xopg_module_mgr;
import gplx.xowa.wikis.pages.htmls.Xopg_html_data;
import gplx.xowa.wikis.pages.lnkis.Xopg_lnki_list;
import gplx.xowa.wikis.pages.skins.Xopg_xtn_skin_itm_stub;

import gplx.Hash_adp;
import gplx.xowa.addons.wikis.hdump.Hxtn_hdump;
import gplx.xowa.wikis.caches.Db_html_body;
import gplx.xowa.htmls.core.dbs.Xowd_html_tbl;
public class Xow_hdump_mgr__load implements Gfo_invk {
	private final    Xow_wiki wiki; private final    Xoh_hzip_mgr hzip_mgr; private final    Io_stream_zip_mgr zip_mgr;
	private final    Xoh_page tmp_hpg; private final    Bry_bfr tmp_bfr; private final    Xowd_page_itm tmp_dbpg = new Xowd_page_itm();		
	private Xow_override_mgr override_mgr__html, override_mgr__page;
	public Xow_hdump_mgr__load(Xow_wiki wiki, Xoh_hzip_mgr hzip_mgr, Io_stream_zip_mgr zip_mgr, Xoh_page tmp_hpg, Bry_bfr tmp_bfr) {
		this.wiki = wiki; this.hzip_mgr = hzip_mgr; this.zip_mgr = zip_mgr; this.tmp_hpg = tmp_hpg; this.tmp_bfr = tmp_bfr;
		this.make_mgr = Xoh_make_mgr.New_make();
	}
	public boolean			Read_preferred()		{return read_preferred;}	private boolean read_preferred = true;
	public Xow_hdump_mode	Html_mode()				{return html_mode;}			private Xow_hdump_mode html_mode = Xow_hdump_mode.Shown;
	public Xoh_make_mgr Make_mgr() {return make_mgr;} private final    Xoh_make_mgr make_mgr;
	public void Init_by_wiki(Xow_wiki wiki) {
		gplx.xowa.addons.apps.cfgs.Xocfg_mgr cfg_mgr = wiki.App().Cfg();
		Xow_hdump_mode.Cfg__reg_type(cfg_mgr.Type_mgr());
		cfg_mgr.Bind_many_wiki(this, wiki, Cfg__read_preferred, Cfg__html_mode);
		wiki.Hxtn_mgr().Init_by_wiki(wiki, Bool_.N);
		make_mgr.Init_by_wiki(wiki);
	}
	public void Load_by_xowe(Xoae_page wpg) {
		tmp_hpg.Ctor_by_hview(wpg.Wiki(), wpg.Url(), wpg.Ttl(), wpg.Db().Page().Id());
		Load_by_xowh(tmp_hpg, wpg.Ttl(), Bool_.Y);
		wpg.Db().Html().Html_bry_(tmp_hpg.Db().Html().Html_bry());
		wpg.Root_(new gplx.xowa.parsers.Xop_root_tkn());	// HACK: set root, else load page will fail
		Fill_page(wpg, tmp_hpg);
	}
	public boolean Load_by_xowh(Xoh_page hpg, Xoa_ttl ttl, boolean load_ctg) {
		synchronized (tmp_dbpg) {
			if (override_mgr__page == null) {
				Io_url override_root_url = wiki.Fsys_mgr().Root_dir().GenSubDir_nest("data", "wiki");
				this.override_mgr__page = new Xow_override_mgr(override_root_url.GenSubDir_nest("page"));
				this.override_mgr__html = new Xow_override_mgr(override_root_url.GenSubDir_nest("html"));
			}
			boolean loaded = Load__dbpg(wiki, tmp_dbpg.Clear(), hpg, ttl);
			hpg.Ctor_by_hview(wiki, hpg.Url(), ttl, tmp_dbpg.Id());
			if (!loaded) {		// nothing in "page" table
				byte[] page_override = override_mgr__page.Get_or_same(ttl.Page_db(), null);
				if (page_override == null) return Load__fail(hpg);
				hpg.Db().Html().Html_bry_(page_override);
				return true;
			}
			Xow_db_file html_db = wiki.Data__core_mgr().Dbs__get_by_id_or_fail(tmp_dbpg.Html_db_id());
			if (!html_db.Tbl__html().Select_by_page(hpg)) return Load__fail(hpg);			// nothing in "html" table
			wiki.Hxtn_mgr().Load_by_page(hpg, ttl);
		Hash_adp props = hpg.Props();
		Hxtn_hdump.Deserialise(hpg, props);
			byte[] src = Parse(hpg, hpg.Db().Html().Zip_tid(), hpg.Db().Html().Hzip_tid(), hpg.Db().Html().Html_bry());

			// write ctgs (do this in Xoh_page_wtr_wkr.java)
			/*if (load_ctg) {
				Xoctg_pagebox_itm[] pagebox_itms = wiki.Ctg__pagebox_wtr().Get_catlinks_by_page(wiki, hpg);
				if (pagebox_itms.length > 0) {
					tmp_bfr.Add(src);					
					wiki.Ctg__pagebox_wtr().Write_pagebox(tmp_bfr, wiki, hpg, pagebox_itms);
					src = tmp_bfr.To_bry_and_clear();
				}
			}*/

			hpg.Db().Html().Html_bry_(src);
			return true;
		}
	}
	public boolean Load_by_wiki(Xoh_page hpg, Xoa_ttl ttl, boolean load_ctg) {
		synchronized (tmp_dbpg) {
			if (override_mgr__page == null) {
				Io_url override_root_url = wiki.Fsys_mgr().Root_dir().GenSubDir_nest("data", "wiki");
				this.override_mgr__page = new Xow_override_mgr(override_root_url.GenSubDir_nest("page"));
				this.override_mgr__html = new Xow_override_mgr(override_root_url.GenSubDir_nest("html"));
			}
			Load__dbpg(wiki, tmp_dbpg.Clear(), hpg, ttl);
			hpg.Ctor_by_hview(wiki, hpg.Url(), ttl, tmp_dbpg.Id());
			Xow_db_file text_db = wiki.Data__core_mgr().Dbs__get_by_id_or_fail(tmp_dbpg.Text_db_id());
                        byte[] src = text_db.Tbl__text().Select(tmp_dbpg.Id());
			//if (!text_db.Tbl__text().Select(hpg.Db().Page().Id())) return Load__fail(hpg);			// nothing in "html" table
			//byte[] src = Parse(hpg, hpg.Db().Html().Zip_tid(), hpg.Db().Html().Hzip_tid(), hpg.Db().Html().Html_bry());

			hpg.Db().Text().Text_bry_(src);
			//wiki.Hxtn_mgr().Load_by_page(hpg, ttl);
			return true;
		}
	}
	public byte[] Decode_as_bry(Bry_bfr bfr, Xoh_page hpg, byte[] src, boolean mode_is_diff) {hzip_mgr.Hctx().Mode_is_diff_(mode_is_diff); hzip_mgr.Decode(bfr, wiki, hpg, src); return bfr.To_bry_and_clear();}
	public byte[] Parse(byte[] src, Xoae_page page) { // NOTE: currently, only used by HTTP_SERVER; may generalize later
		Xoh_page hpg = new Xoh_page();
		hpg.Ctor_by_hview(page.Wiki(), page.Url(), page.Ttl(), page.Db().Page().Id());

		byte[] html = make_mgr.Parse(src, wiki, hpg);
                
                // transfer redlinks
		Xopg_lnki_list src_list = hpg.Html_data().Redlink_list();
		Xopg_lnki_list trg_list = page.Html_data().Redlink_list();
		trg_list.Clear();
		int len = src_list.Len();
		for (int i = 0; i < len; ++i) {
			trg_list.Add_direct(src_list.Get_at(i));
		}
		return html;
	}
	public byte[] Parse(Xoh_page hpg, int zip_tid, int hzip_tid, byte[] src) {
		if (zip_tid > gplx.core.ios.streams.Io_stream_tid_.Tid__raw) {
			if (src[0] == 0x1e) {
				// get from another file
				Db_html_body html_body = new Db_html_body(wiki.App(), wiki.Domain_str(), src[1]);
				int page_id = Db_html_body.convertByteArrayToInt(src, 2);
				long ofs = Db_html_body.convertByteArrayToLong(src, 6);
				int len = Db_html_body.convertByteArrayToInt(src, 14);
				src = html_body.Read(page_id, ofs, len);
			}
			src = zip_mgr.Unzip((byte)zip_tid, src);
		}
		switch (hzip_tid) {
			case Xoh_hzip_dict_.Hdb__htxt:
				src = make_mgr.Parse(src, hpg.Wiki(), hpg);
                                hpg.Done_hdoc_parse_(true); // let others know
				break;
			case Xoh_hzip_dict_.Hdb__page_sync:
				gplx.xowa.addons.wikis.pages.syncs.core.loaders.Xosync_page_loader page_loader = new gplx.xowa.addons.wikis.pages.syncs.core.loaders.Xosync_page_loader();
				src = page_loader.Parse(wiki, hpg, src);
				break;
			case Xoh_hzip_dict_.Hdb__hzip:
				if (override_mgr__html != null)	// null when Parse is called directly
					src = override_mgr__html.Get_or_same(hpg.Ttl().Page_db(), src);
				hpg.Section_mgr().Add(0, 2, Bry_.Empty, Bry_.Empty).Content_bgn_(0);	// +1 to skip \n
				src = Decode_as_bry(tmp_bfr.Clear(), hpg, src, Bool_.N);
				hpg.Section_mgr().Set_content(hpg.Section_mgr().Len() - 1, src, src.length);
				break;
		}
		return src;
	}
	public void Fill_page(Xoae_page wpg, Xoh_page hpg) {
		Xopg_html_data html_data = wpg.Html_data();

		html_data.Custom_head_tags().Add(hpg.Html_data().Custom_head_tags().To_ary());

		Hash_adp props = tmp_hpg.Props();
		html_data.Display_ttl_(tmp_hpg.Display_ttl());
		html_data.Content_sub_(tmp_hpg.Content_sub());			
		html_data.Xtn_skin_mgr().Add(new Xopg_xtn_skin_itm_stub(tmp_hpg.Sidebar_div()));

		html_data.Indicators().Deserialise(wiki, hpg, props);
		html_data.Quality_tots().Deserialise(wiki, wpg, props);
		html_data.Pagebanner().Deserialise(wiki, hpg, props);
		html_data.GeoCrumb().Deserialise(wiki, hpg, props);
		html_data.Pp_indexpage().Deserialise(wiki, hpg, props);
		html_data.Related().Deserialise(wiki, hpg, props);

		Xoh_head_mgr wpg_head = html_data.Head_mgr();
		Xopg_module_mgr hpg_head = hpg.Head_mgr();			
		wpg_head.Itm__mathjax().Enabled_		(hpg_head.Math_exists());
		wpg_head.Itm__popups().Bind_hover_area_	(hpg_head.Imap_exists());
		wpg_head.Itm__gallery().Enabled_		(hpg_head.Gallery_packed_exists());
		wpg_head.Itm__hiero().Enabled_			(hpg_head.Hiero_exists());
		if (hpg_head.Graph2_exists() || hpg_head.Graph1_exists()) {
			wpg_head.Itm__graph().Enabled_(true);
			if (hpg_head.Graph2_exists())
				wpg_head.Itm__graph().Version_(null, 2); // FIX
			if (hpg_head.Graph1_exists())
				wpg_head.Itm__graph().Version_(null, 1); // FIX
		}
// no need 20220606		wpg_head.Itm__timeline().Enabled_		(hpg.Xtn__timeline_exists());
		wpg_head.Itm__gallery_styles().Enabled_	(hpg.Xtn__gallery_exists());
		wpg_head.Itm__categorytree().Enabled_	(hpg.Xtn__categorytree_exists());
		wpg_head.Itm__toc().Enabled_(hpg.Html_data().Toc_mgr().Exists());
		wpg_head.Itm__pgbnr().Enabled_(hpg.Html_data().Head_mgr().Itm__pgbnr().Enabled());

		// transfer Xtn_gallery_packed_exists; needed for hdump; PAGE:en.w:Mexico; DATE:2016-08-14
		if (hpg.Html_data().Xtn_gallery_packed_exists())
			html_data.Xtn_gallery_packed_exists_y_();

		// transfer images from Xoh_page to Xoae_page 
		Xoh_img_mgr src_imgs = hpg.Img_mgr();
		int len = src_imgs.Len();
		for (int i = 0; i < len; ++i) {
			gplx.xowa.files.Xof_fsdb_itm itm = src_imgs.Get_at(i);
			if (itm.Orig_ttl() == null) continue; // skip any images without a src; occurs for <score> when lilypond not available; ISSUE#:577 DATE:2019-09-30
			wpg.Hdump_mgr().Imgs().Add(itm);
			if (!Io_mgr.Instance.ExistsFil(itm.Html_view_url()))// if exists, don't add to file_queue; needed for packed; PAGE:en.w:Mexico; DATE:2016-08-14
				wpg.File_queue().Add(itm);	// add to file_queue for http_server
		}

		// transfer redlinks
		Xopg_lnki_list src_list = hpg.Html_data().Redlink_list();
		Xopg_lnki_list trg_list = wpg.Html_data().Redlink_list();
		len = src_list.Len();
		for (int i = 0; i < len; ++i) {
			trg_list.Add_direct(src_list.Get_at(i));
		}
                wpg.Done_hdoc_parse_(hpg.Done_hdoc_parse());
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__read_preferred))	 		read_preferred = m.ReadYn("v");
		else if	(ctx.Match(k, Cfg__html_mode))				html_mode = Xow_hdump_mode.Parse(m.ReadStr("v"));
		return this;
	}
	private static final String
	  Cfg__read_preferred	= "xowa.wiki.hdumps.read_preferred"
	, Cfg__html_mode		= "xowa.wiki.hdumps.html_mode"
	;

	private static boolean Load__fail(Xoh_page hpg) {hpg.Db().Page().Exists_n_(); return false;}
	private static boolean Load__dbpg(Xow_wiki wiki, Xowd_page_itm dbpg, Xoh_page hpg, Xoa_ttl ttl) {
		wiki.Data__core_mgr().Tbl__page().Select_by_ttl(dbpg, ttl.Ns(), ttl.Page_db(), wiki.Wrk_id());
		if (dbpg.Redirect_id() != -1) Load__dbpg__redirects(wiki, dbpg);
		return dbpg.Html_db_id() != -1;
	}
	private static void Load__dbpg__redirects(Xow_wiki wiki, Xowd_page_itm dbpg) {
		int redirect_count = 0;
		while (++redirect_count < 5) {
			int redirect_id = dbpg.Redirect_id();
			wiki.Data__core_mgr().Tbl__page().Select_by_id(dbpg, redirect_id);
			if (redirect_id == -1) break;
		}
	}
}
class Xow_override_mgr {
	private final    Hash_adp_bry hash = Hash_adp_bry.cs();
	private final    Io_url root_dir;
	private boolean init = true;
	public Xow_override_mgr(Io_url root_dir) {this.root_dir = root_dir;} 
	public void Clear() {hash.Clear();}
	public byte[] Get_or_same(byte[] ttl, byte[] src) {
		if (init) {init = false; Load_from_fsys(hash, root_dir);}
		byte[] rv = (byte[])hash.Get_by_bry(ttl);
		return rv == null ? src : rv;
	}
	private static void Load_from_fsys(Hash_adp_bry hash, Io_url root_dir) {
		Io_url[] urls = Io_mgr.Instance.QueryDir_args(root_dir).Recur_(true).ExecAsUrlAry();
		int urls_len = urls.length;
		for (int i = 0; i < urls_len; ++i) {
			Io_url url = urls[i];
			byte[] raw = Io_mgr.Instance.LoadFilBry(url); int bry_len = raw.length;
			int nl_pos = Bry_find_.Find_fwd(raw, Byte_ascii.Nl, 0, bry_len); if (nl_pos == Bry_find_.Not_found) continue;
			byte[] ttl = Bry_.Mid(raw, 0, nl_pos);
			byte[] src = Bry_.Mid(raw, nl_pos + 1, bry_len);				
			hash.Add_bry_obj(ttl, src);
		}
	}
}
