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
package gplx.xowa.xtns.wbases; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*;
import gplx.langs.jsons.*;
import gplx.xowa.langs.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.parsers.*;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_entity_type_;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_type_;
public class Wdata_doc {
	//private final    Wdata_wiki_mgr mgr;
	private final Wdata_doc_parser wdoc_parser;
	public Wdata_doc(Wdata_wiki_mgr mgr, Json_doc jdoc, byte[] qid) {
//		this.mgr = mgr;
		this.wdoc_parser = mgr.Wdoc_parser(jdoc);
		this.jdoc = jdoc; this.qid = qid;
		Set_type_();
	}
	public byte[] Qid() {return qid;} private final    byte[] qid;
	public Json_doc Jdoc() {return jdoc;} private final    Json_doc jdoc;
	public int Jdoc_size() {return jdoc == null ? 1 : jdoc.Src().length;}
	public byte[][] Sort_langs() {return sort_langs;} public void Sort_langs_(byte[][] v) {sort_langs = v;} private byte[][] sort_langs = Bry_.Ary_empty;

	public int Datatype_id() {if (datatype_id == -1) datatype_id = wdoc_parser.Parse_datatype_id(qid, jdoc); return datatype_id;} private int datatype_id = -1;
	public byte[] Datatype() {return Wbase_claim_type_.Names(Datatype_id());}
	public byte[] Datatype_external() {return Wbase_claim_type_.External_Name(Datatype_id());}

	// NOTE: lazy instantiation b/c we don't want to parse entire json unless called; particulary necessary for {{#property}} calls;
	private Wdata_sitelink slink_list;
	//private Ordered_hash slink_list;
	//public Ordered_hash Slink_list() {
	public Wdata_sitelink Slink_list() {
		if (slink_list == null)
			synchronized (this) {
				slink_list = wdoc_parser.Parse_sitelinks(qid, jdoc);
			}
		return slink_list;
	}

	//private Ordered_hash label_list;
	//public Ordered_hash Label_list() {
	private Wdata_list_label label_list;
	public Wdata_list_label Label_list() {
		if (label_list == null)
			synchronized (this) {
				label_list = wdoc_parser.Parse_langvals(qid, jdoc, Wdata_doc_parser_v2.Bry_labels);
			}
		return label_list;
	}

	//private Ordered_hash descr_list;
	//public Ordered_hash Descr_list() {
	private Wdata_list_label descr_list;
	public Wdata_list_label Descr_list() {
		if (descr_list == null)
			synchronized (this) {
				descr_list = wdoc_parser.Parse_langvals(qid, jdoc, Wdata_doc_parser_v2.Bry_descriptions);
			}
		return descr_list;
	}

	private Wdata_list_alias alias_list;
	public Wdata_list_alias Alias_list() {
		if (alias_list == null)
			synchronized (this) {
				alias_list = wdoc_parser.Parse_aliases(qid, jdoc);
			}
		return alias_list;
	}

	private Ordered_hash claim_list;
	public Ordered_hash Claim_list() {
		if (claim_list == null)
			synchronized (this) {
				claim_list = wdoc_parser.Parse_claims(qid, jdoc);
			}
		return claim_list;
	}

	//private Ordered_hash lemma_list;
	//public Ordered_hash Lemma_list() {
	private Wdata_list_label lemma_list;
	public Wdata_list_label Lemma_list() {
		if (lemma_list == null)
			synchronized (this) {
				lemma_list = wdoc_parser.Parse_langvals(qid, jdoc, Wdata_doc_parser_v2.Bry_lemmas);
			}
		return lemma_list;
	}

	private Ordered_hash sense_list;
	public Ordered_hash Sense_list() {
		if (sense_list == null)
			synchronized (this) {
				sense_list = wdoc_parser.Parse_sense(qid, jdoc);
			}
		return sense_list;
	}

	private Ordered_hash form_list;
	public Ordered_hash Form_list() {
		if (form_list == null)
			synchronized (this) {
				form_list = wdoc_parser.Parse_form(qid, jdoc);
			}
		return form_list;
	}

	// various getters
	public Wbase_claim_grp Get_claim_grp_or_null(int pid) {
		Object o = this.Claim_list().Get_by(Int_obj_ref.New(pid));
		return (Wbase_claim_grp)o;
	}
	public byte[] Get_label_bry_or_null(byte[] lang_key) {
		Wdata_langtext_itm itm = (Wdata_langtext_itm)this.Label_list().Get_by(lang_key);
		return itm == null ? null : itm.Text();
	}
	public Wdata_langtext_itm Get_label_itm_or_null(Xol_lang_itm lang) {return Get_langtext_itm_or_null(this.Label_list(), lang);}
	public Wdata_langtext_itm Get_descr_itm_or_null(Xol_lang_itm lang) {return Get_langtext_itm_or_null(this.Descr_list(), lang);}
	public Wdata_sitelink_itm Get_slink_itm_or_null(byte[] abrv_wm)    {return (Wdata_sitelink_itm)this.Slink_list().Get_by(abrv_wm);}

	// helper method
	//private Wdata_langtext_itm Get_langtext_itm_or_null(Ordered_hash hash, Xol_lang_itm lang) {
	private Wdata_langtext_itm Get_langtext_itm_or_null(Wdata_list_label array, Xol_lang_itm lang) {
		// get itm by lang's key
		Wdata_langtext_itm itm = (Wdata_langtext_itm)array.Get_by(lang.Key_bry());
		if (itm != null) return itm;

		// loop over fallback_langs
		byte[][] fallback_langs = lang.Fallback_bry_ary();	// NOTE: en is currently automatically being added by Xol_lang_itm
		int len = fallback_langs.length;
		for (int i = 0; i < len; i++) {
			byte[] lang_key = fallback_langs[i];
			Object itm_obj = array.Get_by(lang_key);
			if (itm_obj != null) {
				return (Wdata_langtext_itm)itm_obj;
			}
		}
		return null;
	}
	public Wdata_doc Ctor_by_test(Ordered_hash slink_list, Ordered_hash label_list, Ordered_hash descr_list, Ordered_hash alias_list, Ordered_hash claim_list) {// TEST
//		this.slink_list = slink_list; this.label_list = label_list; this.descr_list = descr_list; this.alias_list = alias_list; this.claim_list = claim_list;
		return this;
	}
	public int Name_ofs() {return name;} private int name; // byte offset for Name
	public byte Type() {return type;} private byte type = Wbase_claim_entity_type_.Tid__unknown;
	private void Set_type_() {
		if (qid != null && qid.length > 0) {
			byte b = qid[0];
			switch (b) {
				//case 'q': 
				case 'Q':
					type = Wbase_claim_entity_type_.Tid__item;
					name = 0;
					break;
				//case 'p':
				case 'P':
					type = Wbase_claim_entity_type_.Tid__property;
					name = 9; // Property:
					break;
				//case 'l':
				case 'L':
					type = Wbase_claim_entity_type_.Tid__lexeme;
					name = 7; // Lexeme:
					break;
				case 'E':
					type = Wbase_claim_entity_type_.Tid__entityschema;
					name = 13; // EntitySchema:
					break;
			}
		}
	}
}
