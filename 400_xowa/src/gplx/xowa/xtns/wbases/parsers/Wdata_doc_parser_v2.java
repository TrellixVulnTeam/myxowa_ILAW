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
package gplx.xowa.xtns.wbases.parsers;

import gplx.Bry_;
import gplx.Byte_;
import gplx.Err_;
import gplx.List_adp;
import gplx.List_adp_;
import gplx.Ordered_hash;
import gplx.Ordered_hash_;
import gplx.String_;
import gplx.langs.jsons.Json_ary;
import gplx.langs.jsons.Json_doc;
import gplx.langs.jsons.Json_itm;
import gplx.langs.jsons.Json_kv;
import gplx.langs.jsons.Json_nde;
import gplx.xowa.xtns.wbases.claims.Wbase_claim_grp_list;
import gplx.xowa.xtns.wbases.claims.Wbase_references_grp;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_base;
import gplx.xowa.xtns.wbases.core.Wdata_alias_itm;
import gplx.xowa.xtns.wbases.core.Wdata_dict_langtext;
import gplx.xowa.xtns.wbases.core.Wdata_dict_sitelink;
import gplx.xowa.xtns.wbases.core.Wdata_langtext_itm;
import gplx.xowa.xtns.wbases.core.Wdata_sitelink_itm;
import gplx.xowa.xtns.wbases.core.Wdata_sitelink;
import gplx.xowa.xtns.wbases.core.Wdata_list;
import gplx.xowa.xtns.wbases.core.Wdata_list_alias;
import gplx.xowa.xtns.wbases.core.Wdata_list_label;

import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_type_;
public class Wdata_doc_parser_v2 implements Wdata_doc_parser {
	private final Wdata_claims_parser_v2 claims_parser = new Wdata_claims_parser_v2();
	private final Wdata_forms_parser forms_parser = new Wdata_forms_parser();
	private final Wdata_senses_parser senses_parser = new Wdata_senses_parser();
	public byte[] Parse_qid(Json_doc doc) {
		try {
			Json_itm itm = doc.Find_nde(Bry_id);
			return Bry_.Lcase__1st(itm.Data_bry());	// standardize on "q" instead of "Q" for compatibility with v1
		}	catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to parse qid", "src", String_.new_u8(doc.Src()));}
	}
	public Wdata_sitelink Parse_sitelinks(byte[] qid, Json_doc doc) {
			Json_nde list_nde = Json_nde.Cast(doc.Get_grp(Bry_sitelinks));
                        return new Wdata_sitelink(list_nde, qid);
        }
	public Ordered_hash xxParse_sitelinks(byte[] qid, Json_doc doc) {
		try {
			Json_nde list_nde = Json_nde.Cast(doc.Get_grp(Bry_sitelinks)); if (list_nde == null) return Wdata_doc_parser_v1.Empty_ordered_hash_bry;
			Ordered_hash rv = Ordered_hash_.New_bry();
			int list_len = list_nde.Len();
			for (int i = 0; i < list_len; ++i) {
				Json_kv data_kv		= Json_kv.Cast(list_nde.Get_at(i));
				Json_nde data_nde	= Json_nde.Cast(data_kv.Val());
				int data_nde_len		= data_nde.Len();
				Json_kv site_kv = null, name_kv = null; Json_ary badges_ary = null;
				for (int j = 0; j < data_nde_len; ++j) {
					Json_kv sub = Json_kv.Cast(data_nde.Get_at(j));
					byte tid = Wdata_dict_sitelink.Reg.Get_tid_or_max_and_log(qid, sub.Key().Data_bry()); if (tid == Byte_.Max_value_127) continue;
					switch (tid) {
						case Wdata_dict_sitelink.Tid__site:			site_kv	= Json_kv.Cast(sub); break;
						case Wdata_dict_sitelink.Tid__title:		name_kv	= Json_kv.Cast(sub); break;
						case Wdata_dict_sitelink.Tid__badges:		badges_ary = Json_ary.cast_or_null(Json_kv.Cast(sub).Val()); break;
					}
				}
				byte[] site_bry			= site_kv.Val().Data_bry();
				Wdata_sitelink_itm itm	= new Wdata_sitelink_itm(site_bry, name_kv.Val().Data_bry(), badges_ary.Xto_bry_ary());
				rv.Add(site_bry, itm);
			}
			return rv;
		} catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to parse sitelinks", "qid", String_.new_u8(qid));}
	}
	public Wdata_list_label Parse_langvals(byte[] qid, Json_doc doc, byte[] langval_key) {
            Json_nde list_nde = Json_nde.Cast(doc.Get_grp(langval_key));
                        return new Wdata_list_label(list_nde, qid);
        }
	public Wdata_list_label Parse_local_langval(byte[] qid, Json_nde list_nde) {
                        return new Wdata_list_label(list_nde, qid);
        }
	public Ordered_hash xxParse_langvals(byte[] qid, Json_doc doc, byte[] langval_key) {
		try {
			Json_nde list_nde = Json_nde.Cast(doc.Get_grp(langval_key)); if (list_nde == null) return Wdata_doc_parser_v1.Empty_ordered_hash_bry;
			return yParse_local_langval(qid, list_nde);
		} catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to parse langvals", "qid", String_.new_u8(qid), "langval_key", langval_key);}
	}
	public Ordered_hash yParse_local_langval(byte[] qid, Json_nde list_nde) {
		Ordered_hash rv = Ordered_hash_.New_bry();
		int list_len = list_nde.Len();
		for (int i = 0; i < list_len; ++i) {
			Json_kv data_kv		= Json_kv.Cast(list_nde.Get_at(i));
			Json_itm val_itm = data_kv.Val();
			byte[] lang_bry;
			Wdata_langtext_itm itm;
			if (val_itm instanceof Json_nde) {
				Json_nde data_nde = Json_nde.Cast(val_itm);
				Json_kv text_kv = null;
				int data_nde_len = data_nde.Len();
				for (int j = 0; j < data_nde_len; ++j) {
					Json_kv sub = Json_kv.Cast(data_nde.Get_at(j));
					byte tid = Wdata_dict_langtext.Reg.Get_tid_or_max_and_log(qid, sub.Key().Data_bry()); if (tid == Byte_.Max_value_127) continue;
					switch (tid) {
						case Wdata_dict_langtext.Tid__language:		break;
						case Wdata_dict_langtext.Tid__value:		text_kv	= Json_kv.Cast(sub); break;
					}
				}
				lang_bry = data_kv.Key().Data_bry();
				itm = new Wdata_langtext_itm(lang_bry, text_kv.Val().Data_bry());
			}
			else {
				lang_bry = data_kv.Key().Data_bry();
				itm = new Wdata_langtext_itm(lang_bry, val_itm.Data_bry());
			}
			rv.Add(lang_bry, itm);
		}
		return rv;
	}
	public Wdata_list_alias Parse_aliases(byte[] qid, Json_doc doc) {
            Json_nde list_nde = Json_nde.Cast(doc.Get_grp(Bry_aliases));
                        return new Wdata_list_alias(list_nde, qid);
        }
	public Ordered_hash xxParse_aliases(byte[] qid, Json_doc doc) {
		try {
			Json_nde list_nde = Json_nde.Cast(doc.Get_grp(Bry_aliases)); if (list_nde == null) return Wdata_doc_parser_v1.Empty_ordered_hash_bry;
			Ordered_hash rv = Ordered_hash_.New_bry();
			int list_len = list_nde.Len();
			for (int i = 0; i < list_len; ++i) {
				Json_kv data_kv		= Json_kv.Cast(list_nde.Get_at(i));
				Json_ary vals_ary	= Json_ary.cast_or_null(data_kv.Val());
				int vals_len = vals_ary.Len();
				byte[][] vals = new byte[vals_len][];
				for (int j = 0; j < vals_len; ++j) {
					Json_nde lang_nde = Json_nde.Cast(vals_ary.Get_at(j));
					int k_len = lang_nde.Len();
					for (int k = 0; k < k_len; ++k) {
						Json_kv sub = Json_kv.Cast(lang_nde.Get_at(k));
						byte tid = Wdata_dict_langtext.Reg.Get_tid_or_max_and_log(qid, sub.Key().Data_bry()); if (tid == Byte_.Max_value_127) continue;
						switch (tid) {
							case Wdata_dict_langtext.Tid__language:		break;
							case Wdata_dict_langtext.Tid__value:		vals[j] = sub.Val().Data_bry(); break;
						}
					}
				}
				byte[] lang_bry			= data_kv.Key().Data_bry();
				Wdata_alias_itm itm		= new Wdata_alias_itm(lang_bry, vals);
				rv.Add(lang_bry, itm);
			}
			return rv;
		} catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to parse sitelinks", "qid", String_.new_u8(qid));}
	}
	public Ordered_hash Parse_claims(byte[] qid, Json_doc doc) {
		synchronized (this) {// TS; DATE:2016-07-06
			Json_nde list_nde = Json_nde.Cast(doc.Get_grp(Bry_claims)); if (list_nde == null) return Wdata_doc_parser_v1.Empty_ordered_hash_generic;
			byte[] src = doc.Src();
			return Parse_local_claims(qid, list_nde, src);
		}
	}
	public Ordered_hash Parse_local_claims(byte[] qid, Json_nde list_nde, byte[] src) {
		try {
			List_adp temp_list = List_adp_.New();
			int len = list_nde.Len();
			for (int i = 0; i < len; i++) {
				Json_kv claim_nde = Json_kv.Cast(list_nde.Get_at(i));
				claims_parser.Make_claim_itms(qid, temp_list, src, claim_nde);
			}
			return Wdata_doc_parser_v1.Claims_list_to_hash(temp_list);
		} catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to parse claims", "qid", String_.new_u8(src));}
	}
	public Wbase_claim_base Parse_claims_data(byte[] qid, int pid, byte snak_tid, Json_nde nde) {return claims_parser.Parse_datavalue(qid, pid, snak_tid, nde);}
	public Wbase_claim_grp_list Parse_qualifiers(byte[] qid, Json_nde nde) {return claims_parser.Parse_qualifiers(qid, nde);}
	public Wbase_references_grp[] Parse_references(byte[] qid, Json_ary owner) {return claims_parser.Parse_references(qid, owner);}
	public int[] Parse_pid_order(byte[] qid, Json_ary ary) {return claims_parser.Parse_pid_order(ary);}

	public Ordered_hash Parse_sense(byte[] qid, Json_doc doc) { // id, glosses, claims
		synchronized (this) {// ?
			try {
				Json_nde list_nde = Json_nde.Cast(doc.Get_grp(Bry_senses));
				if (list_nde == null)
					return Wdata_doc_parser_v1.Empty_ordered_hash_generic;
				List_adp temp_list = List_adp_.New();
				byte[] src = doc.Src();
				int len = list_nde.Len();
				for (int i = 0; i < len; i++) {
					Json_nde sense_nde			= Json_nde.Cast(list_nde.Get_at(i));
					senses_parser.Make_sense_itms(qid, temp_list, src, sense_nde, this);
				}
				return Wdata_doc_parser_v1.Claims_list_to_hash(temp_list);
			} catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to parse sense", "qid", String_.new_u8(doc.Src()));}
		}
	}
	public Ordered_hash Parse_form(byte[] qid, Json_doc doc) {
		synchronized (this) {// ?
			try {
				Json_ary list_nde = Json_ary.cast(doc.Get_grp(Bry_forms));
				if (list_nde == null)
					return Wdata_doc_parser_v1.Empty_ordered_hash_generic;
				List_adp temp_list = List_adp_.New();
				byte[] src = doc.Src();
				int len = list_nde.Len();
				for (int i = 0; i < len; i++) {
					Json_nde form_grp = Json_nde.Cast(list_nde.Get_at(i));
					forms_parser.Make_form_itms(qid, temp_list, src, form_grp, this);
				}
				return Wdata_doc_parser_v1.Claims_list_to_hash(temp_list);
			} catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to parse form", "lid", String_.new_u8(doc.Src()));}
		}
	}
/* replaced 20210622
	public byte[] Parse_datatype(byte[] qid, Json_doc doc) {
		synchronized (this) {// ?
			try {
				byte[] datatype = doc.Get_val_as_bry_or(Bry_datatype, Bry_.Empty);
				return Wbase_claim_type_.Get_name(datatype);
			} catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to parse datatype", "lid", String_.new_u8(doc.Src()));}
		}
	}
*/
	public int Parse_datatype_id(byte[] qid, Json_doc doc) {
		synchronized (this) {// ?
			try {
				byte[] datatype = doc.Get_val_as_bry_or(Bry_datatype, Bry_.Empty);
				return Wbase_claim_type_.Get_tid_by_scrib(datatype);
			} catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to parse datatype", "lid", String_.new_u8(doc.Src()));}
		}
	}

	public static final String
	  Str_id									= "id"
	, Str_sitelinks								= "sitelinks"
	, Str_labels								= "labels"
	, Str_descriptions							= "descriptions"
	, Str_aliases								= "aliases"
	, Str_claims								= "claims"
	, Str_type									= "type"
	, Str_lemmas								= "lemmas"
	, Str_senses								= "senses"
	, Str_forms								= "forms"
	, Str_datatype								= "datatype"
	;
	public static final    byte[] 
	  Bry_id									= Bry_.new_a7(Str_id)
	, Bry_sitelinks								= Bry_.new_a7(Str_sitelinks)
	, Bry_labels								= Bry_.new_a7(Str_labels)
	, Bry_descriptions							= Bry_.new_a7(Str_descriptions)
	, Bry_aliases								= Bry_.new_a7(Str_aliases)
	, Bry_claims								= Bry_.new_a7(Str_claims)
	, Bry_type									= Bry_.new_a7(Str_type)
	, Bry_lemmas								= Bry_.new_a7(Str_lemmas)
	, Bry_senses								= Bry_.new_a7(Str_senses)
	, Bry_forms								= Bry_.new_a7(Str_forms)
	, Bry_datatype							= Bry_.new_a7(Str_datatype)
	;
}
