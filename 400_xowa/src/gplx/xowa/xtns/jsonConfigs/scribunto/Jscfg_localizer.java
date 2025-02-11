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
package gplx.xowa.xtns.jsonConfigs.scribunto;
import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.langs.*;
class Jscfg_localizer {
	public Keyval[] Localize(Xol_lang_itm lang, byte[] page, Keyval[] root, Xowe_wiki wiki) {
		if (lang == null) return root; // if no lang, return original

//			Db_readwrite.writeFile(String_.new_u8(page), "d:/des/xowa_x/localize.json");
		int len = root.length;
		// check to see which type (Map or Table)
		// map has zoom/latitude/longitude/data
		// table has schema/data (and others)
		for (int i = 0; i < len; i++) {
			Keyval nde = root[i];
			String nde_key = nde.Key();
			if      (String_.Eq(nde_key, Id__root__license)) {
				byte[] code = nde.Val_to_bry();
				Keyval[] kva = Keyval_.Ary(
				   Keyval_.new_("code", code)
				 , Keyval_.new_("text", wiki.Msg_mgr().Val_by_key_obj("jsonconfig-license-name-" + String_.new_a7(code)))
				 , Keyval_.new_("url", wiki.Msg_mgr().Val_by_key_obj("jsonconfig-license-url-" + String_.new_a7(code)))
				 );
				root[i] = Keyval_.new_("license", kva);
			}
			else if (String_.Eq(nde_key, Id__root__schema))
				return Localize_table(lang, page,  root);
			else if (String_.Eq(nde_key, Id__root__zoom))
				return Localize_map(lang, page,  root);
		}
		return root;
	}
	public Keyval[] Localize_table(Xol_lang_itm lang, byte[] page, Keyval[] root) {
		int len = root.length;
		for (int i = 0; i < len; i++) {
			Keyval nde = root[i];
			String nde_key = nde.Key();
			if (String_.Eq(nde_key, Id__root__description)) {
				root[i] = pickLocalizedString(lang, page, nde);
			}
			else if (String_.Eq(nde_key, Id__root__schema)) {
				nde = Localize_schema(lang, page, nde);
			}
			else if (String_.Eq(nde_key, Id__root__data)) {
				nde = Localize_data(lang, page, nde);
			}
		}

		return root;
	}
	public Keyval[] Localize_map(Xol_lang_itm lang, byte[] page, Keyval[] root) {
		int len = root.length;
		for (int i = 0; i < len; i++) {
			Keyval nde = root[i];
			String nde_key = nde.Key();
			if (String_.Eq(nde_key, Id__root__data)) {
				root[i] = Localize_recursive_data(lang, nde);
			}
		}

		return root;
	}
	private Keyval Localize_schema(Xol_lang_itm lang, byte[] page, Keyval schema) {
		Keyval[] schemas = Cast_to_kvary_or_null(page, schema) ; if (schemas == null) return schema;
		Keyval[] fields  = Cast_to_kvary_or_null(page, schemas[0]); if (fields == null) return schema;
		for (Keyval field : fields) {
			Keyval[] atrs = (Keyval[])field.Val();
			int atrs_len = atrs.length;
			for (int i = 0; i < atrs_len; i++) {
				Keyval atr = atrs[i];
				if (String_.Eq(atr.Key(), Id__fld__title)) {
					atrs[i] = pickLocalizedString(lang, page, atr);
				}
			}
		}
		return schema;
	}
	private Keyval Localize_recursive_data(Xol_lang_itm lang, Keyval data) {
		Object val = data.Val();
		if (Type_.Eq_by_obj(val, Keyval[].class)) { // instanceof?
			Keyval[] rows = (Keyval[]) val;
			int len = rows.length;
			for (int i = 0; i < len; i++) {
				Keyval row = rows[i];
				String row_key = row.Key();
				if (String_.Eq(row_key, Id__root__description)
						|| String_.Eq(row_key, Id__fld__title)) {
					Object rval = row.Val();
					if (Type_.Eq_by_obj(rval, Keyval[].class)) {
						Keyval val_as_kv = pickLocalizedString(lang, Int_.To_str(i), (Keyval[]) row.Val());
						rows[i].Val_(val_as_kv.Val());
					}
				} else {
					rows[i] = Localize_recursive_data(lang, row);
				}
			}
		}
		return data;
	}
	private Keyval Localize_data(Xol_lang_itm lang, byte[] page, Keyval data) {
		Keyval[] rows = Cast_to_kvary_or_null(page, data); if (rows == null) return data;
		for (Keyval row : rows) {
			Object[] vals = (Object[])row.Val();
			int len = vals.length;
			for (int i = 0; i < len; i++) {
				Object val = vals[i];
				if (Type_.Eq_by_obj(val, Keyval[].class)) {
					Keyval val_as_kv = pickLocalizedString(lang, Int_.To_str(i), (Keyval[])val);
					vals[i] = val_as_kv.Val();
				}
			}
		}
		return data;
	}
	private static Keyval pickLocalizedString(Xol_lang_itm lang, byte[] page, Keyval kv) {
		Keyval[] kvs = Cast_to_kvary_or_null(page, kv.Key(), kv.Val());
		Keyval rv = pickLocalizedString(lang, kv.Key(), kvs);
		return rv == null ? kv : rv;
	}
	public static Keyval pickLocalizedString(Xol_lang_itm lang, String key, Keyval[] ary) {
		// local vars for conditional logic
		Object val_lang = null, val_en = null, val_1st = null;
		Object[] val_fallbacks = null;

		// local vars for lang
		String langCode = lang.Key_str();
		Ordered_hash fallback_hash = lang.Fallback_hash();

		// loop ary to populate local vars
		for (Keyval itm : ary) {
			String itm_key = itm.Key();
			Object itm_val = itm.Val();
			if (val_1st == null) {
				val_1st = itm_val;
			}
			if (String_.Eq(itm_key, langCode)) {
				val_lang = itm_val;
			}
			else if (fallback_hash.Has(itm_key)) {
				if (val_fallbacks == null) {
					val_fallbacks = new Object[fallback_hash.Len()];
				}
				int idx = fallback_hash.Idx_of(itm_key);
				val_fallbacks[idx] = itm_val;
			}
			else if (String_.Eq(itm_key, "en")) {
				val_en = itm_val;
			}

		}

		if (val_lang != null) {
			return Keyval_.new_(key, val_lang);
		}

		if (val_fallbacks != null) {
			for (Object v : val_fallbacks) {
				if (v != null)
					return Keyval_.new_(key, v);
			}
		}

		// If fallbacks fail, check if english is defined
		if (val_en != null) {
			return Keyval_.new_(key, val_en);
		}

		// We have a custom default, return that
		// if (defaultValue != null) {
		// 	return null;
		// }

		// Return first available value, or an empty String
		// There might be a better way to get the first value from an Object
		Object val = val_1st == null ? "" : val_1st;
		return Keyval_.new_(key, val);
	}
	private static Keyval[] Cast_to_kvary_or_null(byte[] page, Keyval kv) {return Cast_to_kvary_or_null(page, kv.Key(), kv.Val());}
	private static Keyval[] Cast_to_kvary_or_null(byte[] page, String key, Object val) {
		if (Type_.Eq_by_obj(val, Keyval[].class)) {
			return (Keyval[])val;
		}
		else {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "could not cast to kvary; page=~{0} key=~{1}", key);
			return null;
		}
	} 
	private static final String
	  Id__root__schema        = "schema"
	, Id__root__data          = "data"
	, Id__root__description   = "description"
	, Id__root__license       = "license"
	, Id__root__zoom          = "zoom"
	, Id__fld__title          = "title"
	, Id__fld__properties     = "properties"
	;
}
