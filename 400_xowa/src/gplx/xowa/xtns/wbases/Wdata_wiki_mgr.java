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
package gplx.xowa.xtns.wbases;

import gplx.Bool_;
import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.GfoMsg;
import gplx.Gfo_evt_itm;
import gplx.Gfo_evt_mgr;
import gplx.Gfo_evt_mgr_;
import gplx.Gfo_invk;
import gplx.Gfo_invk_;
import gplx.GfsCtx;
import gplx.String_;
import gplx.Yn;
import gplx.langs.jsons.Json_doc;
import gplx.langs.jsons.Json_kv;
import gplx.langs.jsons.Json_parser;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.apps.apis.xowa.html.Xoapi_toggle_mgr;
import gplx.xowa.apps.apis.xowa.xtns.Xoapi_wikibase;
import gplx.xowa.htmls.Xoh_consts;
import gplx.xowa.langs.Xol_lang_itm;
import gplx.xowa.langs.msgs.Xol_msg_itm_;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.logs.Xop_log_property_wkr;
import gplx.xowa.users.Xoue_user;
import gplx.xowa.wikis.domains.Xow_domain_itm;
import gplx.xowa.wikis.domains.Xow_domain_tid_;
import gplx.xowa.wikis.nss.Xow_ns_;
import gplx.xowa.xtns.wbases.claims.Wbase_claim_grp;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_rank_;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_value_type_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_base;
import gplx.xowa.xtns.wbases.hwtrs.Wdata_hwtr_mgr;
import gplx.xowa.xtns.wbases.hwtrs.Wdata_hwtr_msgs;
import gplx.xowa.xtns.wbases.hwtrs.Wdata_lbl_wkr_wiki;
import gplx.xowa.xtns.wbases.parsers.Wdata_doc_parser;
import gplx.xowa.xtns.wbases.parsers.Wdata_doc_parser_v1;
import gplx.xowa.xtns.wbases.parsers.Wdata_doc_parser_v2;
import gplx.xowa.xtns.wbases.stores.Wbase_doc_mgr;
import gplx.xowa.xtns.wbases.stores.Wbase_pid_mgr;
import gplx.xowa.xtns.wbases.stores.Wbase_pid_mgr_redis;
import gplx.xowa.xtns.wbases.stores.Wbase_prop_mgr;
import gplx.xowa.xtns.wbases.stores.Wbase_prop_mgr_loader_;
import gplx.xowa.xtns.wbases.stores.Wbase_qid_mgr;
import gplx.xowa.xtns.wbases.stores.Wbase_qid_mgr_redis;

import gplx.core.brys.fmtrs.Bry_fmtr;
import gplx.Byte_ascii;
import gplx.Ordered_hash;
import gplx.langs.htmls.Gfh_utl;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_entity_type_;
import gplx.xowa.xtns.wbases.core.Wdata_langtext_itm;
import gplx.xowa.xtns.wbases.core.Wdata_list_label;
import gplx.xowa.langs.Xol_lang_stub;
import gplx.xowa.langs.Xol_lang_stub_;
public class Wdata_wiki_mgr implements Gfo_evt_itm, Gfo_invk {
	private final Xoae_app app;
//	private final Wdata_prop_val_visitor prop_val_visitor;
	private final Wdata_doc_parser wdoc_parser_v1 = new Wdata_doc_parser_v1(), wdoc_parser_v2 = new Wdata_doc_parser_v2();
	private final Object thread_lock = new Object();
	private final Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(32);
	private byte[] page_display_title, no_label;
	private byte[] oview_label;
	public byte[] Overview_label() { return oview_label;}

	public Wdata_wiki_mgr(Xoae_app app) {
		this.app = app;
		this.evt_mgr = new Gfo_evt_mgr(this);
		if (app.Mode().Tid_is_cmd()) {
			this.Qid_mgr = new Wbase_qid_mgr_redis(this);
			this.Pid_mgr = new Wbase_pid_mgr_redis(this);	
		}
		else {
			this.Qid_mgr = new Wbase_qid_mgr(this);
			this.Pid_mgr = new Wbase_pid_mgr(this);	
		}
		this.Doc_mgr = new Wbase_doc_mgr(this, this.Qid_mgr);
		this.prop_mgr = new Wbase_prop_mgr(Wbase_prop_mgr_loader_.New_db(this));
//		this.prop_val_visitor = new Wdata_prop_val_visitor(app, this);
		this.Enabled_(true);
	}
	public Gfo_evt_mgr Evt_mgr() {return evt_mgr;} private final Gfo_evt_mgr evt_mgr;
	public final Wbase_qid_mgr		Qid_mgr;
	public final Wbase_pid_mgr		Pid_mgr;
	public final Wbase_doc_mgr		Doc_mgr;
	public Wbase_prop_mgr				Prop_mgr() {return prop_mgr;} private final Wbase_prop_mgr prop_mgr;
	public boolean Enabled() {return enabled;} private boolean enabled;
	public void Enabled_(boolean v) {
		this.enabled = v;
		Qid_mgr.Enabled_(v);
		Pid_mgr.Enabled_(v);
		Doc_mgr.Enabled_(v);
	}
	public byte[] Domain() {return domain;} public void Domain_(byte[] v) {domain = v;} private byte[] domain = Bry_.new_a7("www.wikidata.org");
	public Wdata_hwtr_mgr Hwtr_mgr() {
		if (hwtr_mgr == null)
			Hwtr_mgr_assert();
		return hwtr_mgr;
	}	private Wdata_hwtr_mgr hwtr_mgr;
	public Xowe_wiki Wdata_wiki() {
		if (wdata_wiki == null) {
			synchronized (thread_lock) {	// LOCK:must synchronized b/c multiple threads may init wdata_mgr at same time;
				Xowe_wiki tmp_wdata_wiki = app.Wiki_mgr().Get_by_or_make(domain).Init_assert(0);
				if (wdata_wiki == null)	// synchronized is not around "if (wdata_wiki == null)", so multiple threads may try to set; only set if null; DATE:2016-09-12
					wdata_wiki = tmp_wdata_wiki;
			}
		}
		return wdata_wiki;
	}	private Xowe_wiki wdata_wiki;
	public void Init_by_app() {}
	public Wdata_doc_parser Wdoc_parser(Json_doc jdoc) {
		// override 20210719
//		return wdoc_parser_v2;

		Json_kv itm_0 = Json_kv.Cast(jdoc.Root_nde().Get_at(0));										// get 1st node
		if (Bry_.Eq(itm_0.Key().Data_bry(), Wdata_doc_parser_v2.Bry_type) 
			|| Bry_.Eq(itm_0.Key().Data_bry(), Wdata_doc_parser_v2.Bry_id))
			return wdoc_parser_v2;
		else if (Bry_.Eq(itm_0.Key().Data_bry(), Bry_entity))
			return wdoc_parser_v1;
		else {
			app.Usr_dlg().Warn_many("", "", "v1 in a v2 first: ~{0} env: ~{1}", String_.new_a7(itm_0.Key().Data_bry()), String_.new_u8(jdoc.Src()));
			//return wdoc_parser_v1;	// if "type", must be v2
			return wdoc_parser_v2;	// ALWAYS v2
		}

	}
	private static byte[] Bry_entity = Bry_.new_a7("entity");
	public Xop_log_property_wkr Property_wkr() {return property_wkr;} private Xop_log_property_wkr property_wkr;
	public void Clear() {
		synchronized (thread_lock) {	// LOCK:app-level
			Qid_mgr.Clear();
			Pid_mgr.Clear();
			Doc_mgr.Clear();
		}
	}
	public byte[] Get_claim_or(Xow_domain_itm domain, Xoa_ttl page_ttl, int pid, byte[] or) {
		byte[] qid = this.Qid_mgr.Get_qid_or_null(domain.Abrv_wm(), page_ttl); if (qid == null) return or;
		Wdata_doc wdoc = Doc_mgr.Get_by_loose_id_or_null(qid); if (wdoc == null) return or;
		Wbase_claim_grp claim_grp = wdoc.Get_claim_grp_or_null(pid);
		if (claim_grp == null || claim_grp.Len() == 0) return or;
		Wbase_claim_base claim_itm = claim_grp.Get_at(0);
		Resolve_claim(tmp_bfr, domain, claim_itm);
		return tmp_bfr.To_bry_and_clear();
	}
	public void Resolve_claim(Bry_bfr rv, Xow_domain_itm domain, Wbase_claim_base claim_itm) {
		synchronized (thread_lock) {	// LOCK:must synchronized b/c prop_val_visitor has member bfr which can get overwritten; DATE:2016-07-06
			if (hwtr_mgr == null) Hwtr_mgr_assert();

			Wdata_prop_val_visitor local_prop_val_visitor = new Wdata_prop_val_visitor(app, this, rv, hwtr_mgr.Msgs(), domain.Lang_orig_key(), Bool_.N);
			//prop_val_visitor.Init(rv, hwtr_mgr.Msgs(), domain.Lang_orig_key(), Bool_.N);
			claim_itm.Welcome(local_prop_val_visitor, false);
		}
	}
	public void Resolve_to_bfr(Bry_bfr bfr, Xowe_wiki wiki, Wbase_claim_grp prop_grp, byte[] lang_key, boolean mode_is_statements) {
//		synchronized (thread_lock) {	// LOCK:must synchronized b/c prop_val_visitor has member bfr which can get overwritten; DATE:2016-07-06
			Hwtr_mgr_assert();
			int len = prop_grp.Len();
			Wbase_claim_base selected = null;
			// this does two things
			// finds the first non deprecated prop
			// and overrides it with the first preferred prop (if there is one)
//                        if (prop_grp.Id() == 625) {
//                Thread currentThread = Thread.currentThread();
//                System.out.println(currentThread.getName()+"-res-"+Integer.toString(len));
//                        }
			for (int i = 0; i < len; i++) {								// NOTE: multiple props possible; EX: {{#property:P1082}}; PAGE:en.w:Earth DATE:2015-08-02
				Wbase_claim_base prop = prop_grp.Get_at(i);
				int rank = prop.Rank_tid();
				if (rank == Wbase_claim_rank_.Tid__deprecated)
					continue;
				if (selected == null) selected = prop;					// if selected not set, set it; will always set to 1st prop
				if (rank == Wbase_claim_rank_.Tid__preferred) {	// if prop is preferred, select it and exit;
					selected = prop;
					break;
				}
			}
			if (selected == null) return;  // no acceptable prop found
			switch (selected.Snak_tid()) { // SEE:NOTE:novalue/somevalue
				case Wbase_claim_value_type_.Tid__novalue:
					bfr.Add(wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_xowa_wikidata_novalue));
					break;
				case Wbase_claim_value_type_.Tid__somevalue:
					bfr.Add(wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_xowa_wikidata_somevalue));
					break;
				default: {
					Wdata_prop_val_visitor local_prop_val_visitor = new Wdata_prop_val_visitor(app, this, bfr, hwtr_mgr.Msgs(), lang_key, mode_is_statements);
					//prop_val_visitor.Init(bfr, hwtr_mgr.Msgs(), lang_key, mode_is_statements);
					selected.Welcome(local_prop_val_visitor, mode_is_statements);
					break;
				}
			}
//		}
	}
	public byte[] Popup_text(Xoae_page page) {
		Hwtr_mgr_assert();
		Wdata_doc wdoc = Doc_mgr.Get_by_exact_id_or_null(page.Ttl().Full_db());			 
		if (wdoc == null) return Bry_.Empty;
		return hwtr_mgr.Popup(wdoc);
	}
	public boolean Write_json_as_html(Bry_bfr bfr, Xoa_ttl page_ttl, byte[] data_raw) {
			Hwtr_mgr_assert();
			Wdata_doc wdoc = Doc_mgr.Get_by_exact_id_or_null(page_ttl.Full_db());
			if (wdoc == null) return false;
			hwtr_mgr.Init_by_wdoc(wdoc);
			bfr.Add(hwtr_mgr.Write(wdoc));
			// build page_display title for later
			page_display_title = Wdata_display_title(wdoc);
                        return true;
	}
	private void Hwtr_mgr_assert() {
		synchronized (thread_lock) {
		if (hwtr_mgr != null) return;
		Xoapi_toggle_mgr toggle_mgr = app.Api_root().Html().Page().Toggle_mgr();
		Xoapi_wikibase wikibase_api = app.Api_root().Xtns().Wikibase();
		hwtr_mgr = new Wdata_hwtr_mgr();
		hwtr_mgr.Init_by_ctor(wikibase_api, this, new Wdata_lbl_wkr_wiki(wikibase_api, this), gplx.langs.htmls.encoders.Gfo_url_encoder_.Href, toggle_mgr, app.Usere().Wiki().Xwiki_mgr());
		this.Hwtr_msgs_make();
		Gfo_evt_mgr_.Sub_same_many(app.Usere(), this, Xoue_user.Evt_lang_changed);
                }
	}
	private void Hwtr_msgs_make() {
		// if (!app.Wiki_mgr().Wiki_regy().Has(Xow_domain_itm_.Bry__wikidata)) return; // DELETE: don't know why guard is needed; breaks test; DATE:2016-10-20
		Xol_lang_itm new_lang = app.Usere().Lang();
		Xowe_wiki cur_wiki = this.Wdata_wiki();			
		cur_wiki.Xtn_mgr().Xtn_wikibase().Load_msgs(cur_wiki, new_lang);
		Wdata_hwtr_msgs hwtr_msgs = Wdata_hwtr_msgs.new_(cur_wiki.Msg_mgr());
		hwtr_mgr.Init_by_lang(new_lang, hwtr_msgs);
		this.no_label = hwtr_msgs.Wiki_no_label();
	}
	public static void Write_json_as_html(Json_parser jdoc_parser, Bry_bfr bfr, byte[] data_raw) {
		bfr.Add(Xoh_consts.Span_bgn_open).Add(Xoh_consts.Id_atr).Add(Html_json_id).Add(Xoh_consts.__end_quote);	// <span id="xowa-wikidata-json">
		Json_doc json = jdoc_parser.Parse(data_raw);
		json.Root_nde().Print_as_json(bfr, 0);
		bfr.Add(Xoh_consts.Span_end);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled))					return Yn.To_str(enabled);
		else if	(ctx.Match(k, Invk_enabled_))					enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_domain))						return String_.new_u8(domain);
		else if	(ctx.Match(k, Invk_domain_))					domain = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_property_wkr))				return m.ReadYnOrY("v") ? Property_wkr_or_new() : Gfo_invk_.Noop;
		else if	(ctx.Match(k, Xoue_user.Evt_lang_changed))		Hwtr_msgs_make();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_enabled = "enabled", Invk_enabled_ = "enabled_", Invk_domain = "domain", Invk_domain_ = "domain_", Invk_property_wkr = "property_wkr";
	public Xop_log_property_wkr Property_wkr_or_new() {
		if (property_wkr == null) property_wkr = app.Log_mgr().Make_wkr_property();
		return property_wkr;
	}
	public static final int Ns_property = 120;
	public static final String Ns_property_name = "Property";
	public static final byte[] Ns_property_name_bry = Bry_.new_a7(Ns_property_name);
	public static final int Ns_lexeme = 146;
	public static final String Ns_lexeme_name = "Lexeme";
	public static final byte[] Ns_lexeme_name_bry = Bry_.new_a7(Ns_lexeme_name);
	public static final int Ns_entityschema = 640;
	public static final String Ns_entityschema_name = "EntitySchema";
	public static final byte[] Ns_entityschema_name_bry = Bry_.new_a7(Ns_entityschema_name);

	public static final byte[] Html_json_id = Bry_.new_a7("xowa-wikidata-json");
	public static boolean Wiki_page_is_json(int wiki_tid, int ns_id) {
		switch (wiki_tid) {
			case Xow_domain_tid_.Tid__wikidata:
				if (ns_id == Xow_ns_.Tid__main || ns_id == gplx.xowa.xtns.wbases.Wdata_wiki_mgr.Ns_property || ns_id == gplx.xowa.xtns.wbases.Wdata_wiki_mgr.Ns_lexeme || ns_id == gplx.xowa.xtns.wbases.Wdata_wiki_mgr.Ns_entityschema)
					return true;
				break;
			case Xow_domain_tid_.Tid__home:
				if (ns_id == gplx.xowa.xtns.wbases.Wdata_wiki_mgr.Ns_property)
					return true;
			case Xow_domain_tid_.Tid__commons:
				if (ns_id == Xow_ns_.Tid__data)
					return true;
				break;
		}
		return false;
	}
	public static void Log_missing_qid(Xop_ctx ctx, String type, byte[] id) {
		if (id == null) id = Bry_.Empty;
		// if a single letter - ignore
		if (id.length == 1) return;
		//ctx.Wiki().Appe().Usr_dlg().Log_many("", "", "Unknown id in wikidata; type=~{0} id=~{1} page=~{2}", type, id, ctx.Page().Url_bry_safe());
		ctx.Wiki().Logger().Log_many("Unknown id in wikidata; type=~{0} id=~{1} page=~{2}", type, id, ctx.Page().Url_bry_safe());
	}
	public byte[] Page_display_title() { return page_display_title; }
	public byte[] Doc_name(Wdata_doc wdoc) {
		Xoapi_wikibase wikibase_api = app.Api_root().Xtns().Wikibase();
		byte[][] core_langs		= wikibase_api.Core_langs();
		Wdata_list_label list;
		if (wdoc.Type() == Wbase_claim_entity_type_.Tid__lexeme)
			list = wdoc.Lemma_list();
		else
			list = wdoc.Label_list();
		return list.Get_text_or_empty(core_langs);
	}
	private byte[] Wdata_display_title(Wdata_doc wdoc) {
		// P, Q, L or E
		byte[] ttl_label;
		byte[] cls = Bry_.Empty;
		if (wdoc.Type() == Wbase_claim_entity_type_.Tid__lexeme) {
			Wdata_list_label list = wdoc.Lemma_list();
			//Bry_bfr tmp_bfr = Bry_bfr_.New();
			Bry_bfr oview_bfr = Bry_bfr_.New();
			int len = list.Count();
			for (int i = 0; i < len; ++i) {
				if (i > 0) {
					oview_bfr.Add_byte(Byte_ascii.Slash);
					tmp_bfr.Add_byte(Byte_ascii.Slash);
				}
				Wdata_langtext_itm itm = (Wdata_langtext_itm)list.Get_at(i);
				Xol_lang_stub lang_itm = Xol_lang_stub_.Get_by_key_or_intl(itm.Lang());
				byte[] txt = Gfh_utl.Escape_html_as_bry(itm.Text());
				byte[] dir = Bry_.new_a7("ltr");
				lemma_first_fmtr.Bld_bfr_many(tmp_bfr, dir, Gfh_utl.Escape_html_as_bry(itm.Lang()), txt);
				oview_bfr.Add(txt);
			}
			oview_label = oview_bfr.To_bry_and_clear();
			ttl_label = tmp_bfr.To_bry_and_clear();
		}
		else {
			Xoapi_wikibase wikibase_api = app.Api_root().Xtns().Wikibase();
			byte[][] core_langs = wikibase_api.Core_langs();
			oview_label = wdoc.Label_list().Get_text_or_empty(core_langs);
			if (oview_label.length == 0) {
				oview_label = no_label;
				cls = Bry_.new_a7("wb-empty");
			}
			ttl_label = oview_label;
		}
		display_fmtr.Bld_bfr_many(tmp_bfr, cls, ttl_label, Bry_.Mid(wdoc.Qid(), wdoc.Name_ofs()));
		return tmp_bfr.To_bry_and_clear();
	}
	private Bry_fmtr display_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "<span class=\"wikibase-title ~{cls}\">"
	, "<span class=\"wikibase-title-label\">~{ttl_label}</span>"
	, "<span class=\"wikibase-title-id\">(~{ttl_id})</span>"
	, "</span>"
	), "cls", "ttl_label", "ttl_id"
	);
	private Bry_fmtr lemma_first_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "<span class=\"mw-content-~{dir}\" dir=\"~{dir}\" lang=\"~{lang}\">~{name}</span>"
	), "dir", "lang", "name"
	);
}
/*
NOTE:novalue/somevalue
Rough approximation of wikibase logic which is more involved with its different SnakFormatters
* https://github.com/wikimedia/mediawiki-extensions-Wikibase/blob/master/lib/includes/Formatters/OutputFormatSnakFormatterFactory.php: formatter factory; note lines for somevalue / novalue
* https://github.com/wikimedia/mediawiki-extensions-Wikibase/blob/master/lib/includes/Formatters/MessageSnakFormatter.php: formatter definition
* https://github.com/wikimedia/mediawiki-extensions-Wikibase/blob/master/repo/i18n/en.json: message definitions
*/
