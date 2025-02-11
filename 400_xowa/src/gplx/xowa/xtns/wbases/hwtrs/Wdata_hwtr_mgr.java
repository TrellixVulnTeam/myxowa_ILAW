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
package gplx.xowa.xtns.wbases.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.core.brys.fmtrs.*;
import gplx.langs.htmls.encoders.*;
import gplx.xowa.langs.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.apps.apis.xowa.html.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.apps.apis.xowa.xtns.*;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_entity_type_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_entity_;
public class Wdata_hwtr_mgr {
	private final    Bry_bfr bfr = Bry_bfr_.Reset(Io_mgr.Len_mb);
	@gplx.Internal protected Wdata_fmtr__toc_div   Fmtr_toc() {return fmtr_toc;} private final     Wdata_fmtr__toc_div fmtr_toc = new Wdata_fmtr__toc_div();
	@gplx.Internal protected Wdata_fmtr__json      Fmtr_json() {return fmtr_json;} private final   Wdata_fmtr__json fmtr_json = new Wdata_fmtr__json();
	@gplx.Internal protected Wdata_fmtr__claim_grp Fmtr_claim_stmt() {return fmtr_claim_stmt;} private final Wdata_fmtr__claim_grp fmtr_claim_stmt = new Wdata_fmtr__claim_grp(1); // statements
	@gplx.Internal protected Wdata_fmtr__claim_grp Fmtr_claim_id()   {return fmtr_claim_id;}   private final Wdata_fmtr__claim_grp fmtr_claim_id =   new Wdata_fmtr__claim_grp(2); // identifiers
	@gplx.Internal protected Wdata_fmtr__claim_grp Fmtr_claim_con()  {return fmtr_claim_con;}  private final Wdata_fmtr__claim_grp fmtr_claim_con =  new Wdata_fmtr__claim_grp(3); // constraints
	private final    Wdata_fmtr__langtext_tbl fmtr_label = new Wdata_fmtr__langtext_tbl();
	private final    Wdata_fmtr__langtext_tbl fmtr_lemma = new Wdata_fmtr__langtext_tbl();
	private final    Wdata_fmtr__langtext_tbl fmtr_descr = new Wdata_fmtr__langtext_tbl();
	private final    Wdata_fmtr__langtext_tbl fmtr_alias = new Wdata_fmtr__langtext_tbl();
	private final    Wdata_fmtr__slink_grp fmtr_slink = new Wdata_fmtr__slink_grp();
	private final    Wdata_fmtr__oview_tbl fmtr_oview = new Wdata_fmtr__oview_tbl();
	private final    Wdata_fmtr__lemma_oview_tbl fmtr_lemma_oview = new Wdata_fmtr__lemma_oview_tbl();
	private final    Wdata_fmtr__datatype fmtr_datatype = new Wdata_fmtr__datatype();
	private Wdata_lang_sorter lang_sorter = new Wdata_lang_sorter();		
	public Bry_fmtr Fmtr_main() {return fmtr_main;} private final    Bry_fmtr fmtr_main = Bry_fmtr.new_("~{oview}~{toc}~{claims_stmt}~{claims_id}~{claims_con}~{links}~{labels}~{descriptions}~{aliases}~{json}", "oview", "toc", "claims_stmt", "claims_id", "claims_con", "links", "labels", "descriptions", "aliases", "json");
	private final    Bry_fmtr fmtr_main_L = Bry_fmtr.new_("~{oview}~{toc}~{claims}~{links}~{json}", "oview", "toc", "claims", "links", "json");
	private final    Bry_fmtr fmtr_main_P = Bry_fmtr.new_("~{oview}~{toc}~{datatype}~{claims_stmt}~{claims_con}~{links}~{labels}~{descriptions}~{aliases}~{json}", "oview", "toc", "datatype", "claims_stmt", "claims_con", "links", "labels", "descriptions", "aliases", "json");
	public Wdata_hwtr_msgs Msgs() {return msgs;} private Wdata_hwtr_msgs msgs;
	public Wdata_lbl_mgr Lbl_mgr() {return lbl_mgr;} private final    Wdata_lbl_mgr lbl_mgr = new Wdata_lbl_mgr();
	public void Init_by_ctor(Xoapi_wikibase wikibase_api, Wdata_wiki_mgr wdata_mgr, Wdata_lbl_wkr lbl_wkr, Gfo_url_encoder href_encoder, Xoapi_toggle_mgr toggle_mgr, Xow_xwiki_mgr xwiki_mgr) {
		lbl_mgr.Wkr_(lbl_wkr);
		fmtr_oview.Init_by_ctor(wikibase_api, href_encoder);
		fmtr_lemma_oview.Init_by_ctor(wikibase_api, wdata_mgr);
		fmtr_claim_stmt.Init_by_ctor(wdata_mgr, new Wdata_toc_data(fmtr_toc, href_encoder), toggle_mgr, lbl_mgr);
		fmtr_claim_id.Init_by_ctor(wdata_mgr, new Wdata_toc_data(fmtr_toc, href_encoder), toggle_mgr, lbl_mgr);
		fmtr_claim_con.Init_by_ctor(wdata_mgr, new Wdata_toc_data(fmtr_toc, href_encoder), toggle_mgr, lbl_mgr);
		fmtr_slink.Init_by_ctor(lang_sorter, toggle_mgr, lbl_mgr, href_encoder, fmtr_toc, xwiki_mgr);
		fmtr_label.Init_by_ctor(new Wdata_toc_data(fmtr_toc, href_encoder), lang_sorter, toggle_mgr, "wikidatawiki-label", new Wdata_fmtr__langtext_row_base());
		fmtr_lemma.Init_by_ctor(new Wdata_toc_data(fmtr_toc, href_encoder), lang_sorter, toggle_mgr, "wikidatawiki-lemma", new Wdata_fmtr__langtext_row_base()); //check
		fmtr_descr.Init_by_ctor(new Wdata_toc_data(fmtr_toc, href_encoder), lang_sorter, toggle_mgr, "wikidatawiki-descr", new Wdata_fmtr__langtext_row_base());
		fmtr_alias.Init_by_ctor(new Wdata_toc_data(fmtr_toc, href_encoder), lang_sorter, toggle_mgr, "wikidatawiki-alias", new Wdata_fmtr__alias_row());
		fmtr_json .Init_by_ctor(new Wdata_toc_data(fmtr_toc, href_encoder), toggle_mgr);
		lang_sorter.Langs_(wikibase_api.Sort_langs());
		Gfo_evt_mgr_.Sub_same_many(wikibase_api, lang_sorter, Xoapi_wikibase.Evt_sort_langs_changed);
	}
	public void Init_by_lang(Xol_lang_itm lang, Wdata_hwtr_msgs msgs) {
		this.msgs = msgs;
		fmtr_toc.Init_by_lang(msgs);
		fmtr_oview.Init_by_lang(lang_sorter.Langs()[0], msgs);
		fmtr_lemma_oview.Init_by_lang(lang_sorter.Langs()[0], msgs);
		fmtr_claim_stmt.Init_by_lang(lang, msgs);
		fmtr_claim_id.Init_by_lang(lang, msgs);
		fmtr_claim_con.Init_by_lang(lang, msgs);
		fmtr_slink.Init_by_lang(msgs);
		fmtr_label.Init_by_lang(msgs, msgs.Label_tbl_hdr(), msgs.Label_col_hdr());
		fmtr_lemma.Init_by_lang(msgs, msgs.Lemma_tbl_hdr(), msgs.Lemma_col_hdr());
		fmtr_descr.Init_by_lang(msgs, msgs.Descr_tbl_hdr(), msgs.Descr_col_hdr());
		fmtr_alias.Init_by_lang(msgs, msgs.Alias_tbl_hdr(), msgs.Alias_col_hdr());
		fmtr_json.Init_by_lang(msgs);
	}
	public void Init_by_wdoc(Wdata_doc wdoc) {
		lang_sorter.Init_by_wdoc(wdoc);
		fmtr_toc  .Init_by_wdoc(wdoc);
		fmtr_oview.Init_by_wdoc(wdoc);
		fmtr_lemma_oview.Init_by_wdoc(wdoc);
		fmtr_claim_stmt.Init_by_wdoc(wdoc.Qid(), wdoc.Claim_list());
		fmtr_claim_id.Init_by_wdoc(wdoc.Qid(), wdoc.Claim_list());
		fmtr_claim_con.Init_by_wdoc(wdoc.Qid(), wdoc.Claim_list());
		fmtr_slink.Init_by_wdoc(wdoc.Slink_list());
		fmtr_label.Init_by_wdoc(wdoc.Label_list());
		fmtr_lemma.Init_by_wdoc(wdoc.Lemma_list());
		fmtr_descr.Init_by_wdoc(wdoc.Descr_list());
		fmtr_alias.Init_by_wdoc(wdoc.Alias_list());
		fmtr_json.Init_by_wdoc (wdoc.Jdoc());
		lbl_mgr.Gather_labels(wdoc, lang_sorter);
		if (wdoc.Type() == Wbase_claim_entity_type_.Tid__property)
			fmtr_datatype.Init_by_wdoc(wdoc.Datatype());
	}
	public byte[] Popup(Wdata_doc wdoc) {
		Object fmtr;
		if (wdoc.Type() == Wbase_claim_entity_type_.Tid__lexeme) {
			fmtr_lemma_oview.Init_by_wdoc(wdoc);
			fmtr = fmtr_lemma_oview;
		}
		else {
			fmtr_oview.Init_by_wdoc(wdoc);
			//fmtr_label.Init_by_wdoc(wdoc.Label_list());
			//fmtr_descr.Init_by_wdoc(wdoc.Descr_list());
			//fmtr_alias.Init_by_wdoc(wdoc.Alias_list());
			fmtr = fmtr_oview;
		}
		bfr.Add_str_a7("<div id='wb-item' class='wikibase-entityview wb-item' lang='en' dir='ltr'>");
		bfr.Add_str_a7("<div class='wikibase-entityview-main'>");
		fmtr_main.Bld_bfr_many(bfr, fmtr, "", "", "", "", "", "", "", "", "", "");
		bfr.Add_str_a7("</div>");
		bfr.Add_str_a7("</div>");
		return bfr.To_bry_and_clear();
	}
	public byte[] Write(Wdata_doc wdoc) {
		bfr.Add_str_a7("<div id=\"wb-");
		byte[] item = Wbase_claim_entity_type_.Reg.Get_bry_or_fail(wdoc.Type());
		bfr.Add(item);
		bfr.Add_byte(Byte_ascii.Dash);
		bfr.Add_mid(wdoc.Qid(), wdoc.Name_ofs(), wdoc.Qid().length);
		bfr.Add_str_a7("\" class=\"wikibase-entityview wb-");
		bfr.Add(item);
		bfr.Add_str_a7("\" lang=\"en\" dir=\"ltr\">");
		bfr.Add_str_a7("<div class=\"wikibase-entityview-main\">");
		if (wdoc.Type() == Wbase_claim_entity_type_.Tid__lexeme)
			fmtr_main_L.Bld_bfr_many(bfr, fmtr_lemma_oview, fmtr_toc, fmtr_claim_stmt, fmtr_slink, fmtr_json); // senses and forms?
		else if (wdoc.Type() == Wbase_claim_entity_type_.Tid__property)
			fmtr_main_P.Bld_bfr_many(bfr, fmtr_oview, fmtr_toc, fmtr_datatype, fmtr_claim_stmt, fmtr_claim_con, fmtr_slink, fmtr_label, fmtr_descr, fmtr_alias, fmtr_json);
		else
			fmtr_main.Bld_bfr_many(bfr, fmtr_oview, fmtr_toc, fmtr_claim_stmt, fmtr_claim_id, fmtr_claim_con, fmtr_slink, fmtr_label, fmtr_descr, fmtr_alias, fmtr_json);
		bfr.Add_str_a7("</div>");
		bfr.Add_str_a7("</div>");
		return bfr.To_bry_and_clear();
	}
	public static void Write_link_wikidata(Bry_bfr bfr, byte[] href, byte[] text) {
		text = gplx.langs.htmls.Gfh_utl.Escape_html_as_bry(text);
		fmtr_link_wikidata.Bld_bfr_many(bfr, href, text);
	}	private static final    Bry_fmtr fmtr_link_wikidata = Bry_fmtr.new_("<a href='/wiki/~{href}'>~{text}</a>", "href", "text");
}
