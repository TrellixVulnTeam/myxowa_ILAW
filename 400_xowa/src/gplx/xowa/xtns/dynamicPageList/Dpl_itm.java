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
package gplx.xowa.xtns.dynamicPageList; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*;
import gplx.xowa.wikis.nss.*;
public class Dpl_itm {
	public byte[] Page_ttl() {return page_ttl;} private byte[] page_ttl;
	public List_adp Ctg_includes() {return ctg_includes;} private List_adp ctg_includes;
	public int[] Ctg_include_ids() {return ctg_include_ids;} public void Ctg_include_ids_(int[] vals) {ctg_include_ids = vals;} private int[] ctg_include_ids;
	public List_adp Ctg_excludes() {return ctg_excludes;} private List_adp ctg_excludes;
	public int[] Ctg_exclude_ids() {return ctg_exclude_ids;} public void Ctg_exclude_ids_(int[] vals) {ctg_exclude_ids = vals;} private int[] ctg_exclude_ids;
	public int Count() {return count;} private int count = Int_.Min_value;
	public int Offset() {return offset;} private int offset = Int_.Min_value;
	public boolean No_follow() {return no_follow;} private boolean no_follow;
	public boolean Suppress_errors() {return suppress_errors;} private boolean suppress_errors;
	public boolean Show_ns() {return show_ns;} private boolean show_ns;
	public boolean Show_date() {return ctg_date;} private boolean ctg_date = false;
	public byte Sort_ascending() {return sort_ascending;} private byte sort_ascending = Bool_.__byte;
	public boolean Ns_filter_active() {return ns_filter_active;} private boolean ns_filter_active = false;
	public int Ns_filter() {return ns_filter;} private int ns_filter = 0;
	public boolean Gallery_filesize() {return gallery_filesize;} private boolean gallery_filesize;
	public boolean Gallery_filename() {return gallery_filename;} private boolean gallery_filename;
	public int Gallery_imgs_per_row() {return gallery_imgs_per_row;} private int gallery_imgs_per_row;
	public int Gallery_img_w() {return gallery_img_w;} private int gallery_img_w;
	public int Gallery_img_h() {return gallery_img_h;} private int gallery_img_h;
	public byte[] Gallery_caption() {return gallery_caption;} private byte[] gallery_caption;
	public byte Redirects_mode() {return redirects_mode;} private byte redirects_mode = Dpl_redirect.Tid_unknown;
	public byte Sort_tid() {return sort_tid;} private byte sort_tid = Dpl_sort.Tid_categoryadd;
	public byte Mode_tid() {return mode_tid;} private byte mode_tid = Dpl_itm_keys.Key_unordered;
	public byte Quality_pages() {return quality_pages;} private byte quality_pages;
	public byte Stable_pages() {return stable_pages;} private byte stable_pages;
	public boolean IgnoreSubpages() {return ignoresubpages;} private boolean ignoresubpages;
	public boolean Showcurid() {return showcurid;} private boolean showcurid; // ignored
	private Xop_ctx sub_ctx; private Xop_tkn_mkr sub_tkn_mkr; private Xop_root_tkn sub_root;
	private void Parse_src(Xowe_wiki wiki, Xop_ctx ctx, byte[] page_ttl, byte[] src, Xop_xnde_tkn xnde) {	// parse kvps in xnde; EX:<dpl>category=abc\nredirects=y\n</dpl>
		this.page_ttl = page_ttl;
		sub_ctx = Xop_ctx.New__sub__reuse_page(ctx);
		sub_tkn_mkr = sub_ctx.Tkn_mkr();
		sub_root = sub_tkn_mkr.Root(Bry_.Empty);
		int content_bgn = xnde.Tag_open_end(), content_end = xnde.Tag_close_bgn();
		int pos = content_bgn;
		int fld_bgn = content_bgn;
		byte key_id = 0;
		Gfo_usr_dlg usr_dlg = wiki.Appe().Usr_dlg();
		boolean ws_bgn_chk = true, ws_key_found = false;
		int ws_bgn_idx = -1, ws_end_idx = -1;
		boolean loop = true;
		boolean badfield = false;
		while (loop) {										// iterate over content
			boolean done = pos >= content_end;
			byte b = done ? Byte_ascii.Nl : src[pos];		// get cur byte
			switch (b) {
				case Byte_ascii.Space: case Byte_ascii.Tab:
					if	(ws_bgn_chk) ws_bgn_idx = pos;										// definite ws at bgn; set ws_bgn_idx, and keep setting until text reached; handles mixed sequence of \s\n\t where last tkn should be ws_bgn_idx
					else			{if (ws_end_idx == -1) ws_end_idx = pos;};				// possible ws at end; may be overriden later; see AdjustWsForTxtTkn
					break;
				case Byte_ascii.Eq: {						// =; make key; EX: "=" in "category="
					if (ws_key_found) {
						ws_end_idx = -1;
						break; // ignore spurious = signs
					}
					if (ws_bgn_idx != -1) fld_bgn = ws_bgn_idx + 1;	// +1 to position after last known ws
					int fld_end = ws_end_idx == -1 ? pos : ws_end_idx;
					key_id = Dpl_itm_keys.Parse(src, fld_bgn, fld_end, Dpl_itm_keys.Key_null);
					if (key_id == Dpl_itm_keys.Key_null) {	// unknown key; warn and set pos to end of line; EX: "unknown=";
						Parse_missing_key(usr_dlg, page_ttl, src, fld_bgn, fld_end);
						fld_bgn = Bry_find_.Find_fwd(src, Byte_ascii.Nl, pos);
						if (fld_bgn == Bry_find_.Not_found)
							badfield = true;
							//loop = false;
						//else {
							pos = fld_bgn;	// set pos after \n else bounds error if multiple bad keys on same line; NOTE: ++pos below; EX: \nbad1=a bad2=b\n; PAGE:de.n:Brandenburg DATE:2016-04-21
							++fld_bgn;		// set fld_bgn after \n;
						//}
					}
					else {									// known key; set pos to val_bgn
						fld_bgn = pos + Byte_ascii.Len_1;
						ws_key_found = true;
					}
					ws_bgn_chk = true; ws_bgn_idx = ws_end_idx = -1;
					break;
				}
				case Byte_ascii.Nl: { // dlm is nl; EX: "\n" in "category=abc\n"
					if (fld_bgn == pos) { // blank arg - still needs to process
						if (ws_key_found)
							Parse_cmd(wiki, key_id, Bry_.Empty, usr_dlg, page_ttl);
					}
					else {
						if (ws_bgn_idx != -1) fld_bgn = ws_bgn_idx + 1;	// +1 to position after last known ws
						int fld_end = ws_end_idx == -1 ? pos : ws_end_idx;
						byte[] val = Bry_.Mid(src, fld_bgn, fld_end);
						if (badfield) // ignore
							badfield = false;
						else
							Parse_cmd(wiki, key_id, val, usr_dlg, page_ttl);
					}
					fld_bgn = pos + Byte_ascii.Len_1;
					ws_bgn_chk = true; ws_bgn_idx = ws_end_idx = -1;
					ws_key_found = false;
					break;
				}
				default:	// text token
					if (ws_bgn_chk) ws_bgn_chk = false; else ws_end_idx = -1;		// INLINE: AdjustWsForTxtTkn
					break;
			}
			if (done) break;
			++pos;
		}
	}
	public void Parse_cmd(Xowe_wiki wiki, byte key_id, byte[] val, Gfo_usr_dlg usr_dlg, byte[] page_ttl) {
		sub_root.Clear();
		val = wiki.Parser_mgr().Main().Expand_tmpl(sub_root, sub_ctx, sub_tkn_mkr, val);
//                System.out.println(String_.new_a7(val));
//	        System.out.println(key_id);
		switch (key_id) {
			case Dpl_itm_keys.Key_category: 			if (ctg_includes == null) ctg_includes = List_adp_.New(); ctg_includes.Add(Xoa_ttl.Replace_spaces(val)); break;
			case Dpl_itm_keys.Key_notcategory:		 	if (ctg_excludes == null) ctg_excludes = List_adp_.New(); ctg_excludes.Add(Xoa_ttl.Replace_spaces(val)); break;
			case Dpl_itm_keys.Key_ns: {
				Xow_ns ns = (Xow_ns)wiki.Ns_mgr().Names_get_or_null(val, 0, val.length);
				ns_filter = ns == null ? Xow_ns_.Tid__main : ns.Id();
				ns_filter_active = true;
				break;
			}
			case Dpl_itm_keys.Key_order:				sort_ascending = Dpl_sort.Parse_as_bool_byte(val); break;
			case Dpl_itm_keys.Key_suppresserrors:		suppress_errors = Dpl_itm_keys.Parse_as_bool(val, false); break;
			case Dpl_itm_keys.Key_nofollow:				no_follow = Dpl_itm_keys.Parse_as_bool(val, true); break;	// NOTE: default to true; allows passing nofollow=nofollow; MW: if ('false' != $arg)
			case Dpl_itm_keys.Key_shownamespace:		show_ns = Dpl_itm_keys.Parse_as_bool(val, true); break; // NOTE: default to true;
			case Dpl_itm_keys.Key_redirects:			redirects_mode = Dpl_redirect.Parse(val); break;
			case Dpl_itm_keys.Key_stablepages:			stable_pages = Dpl_stable_tid.Parse(val); break;
			case Dpl_itm_keys.Key_qualitypages:			quality_pages = Dpl_redirect.Parse(val); break;
			case Dpl_itm_keys.Key_addfirstcategorydate:	Parse_ctg_date(val); break;
			case Dpl_itm_keys.Key_count:				count = Bry_.To_int_or(val, Int_.Min_value); break;
			case Dpl_itm_keys.Key_offset:				offset = Bry_.To_int_or(val, Int_.Min_value); break;
			case Dpl_itm_keys.Key_imagesperrow:			gallery_imgs_per_row = Bry_.To_int_or(val, Int_.Min_value); break;
			case Dpl_itm_keys.Key_imagewidth:			gallery_img_w = Bry_.To_int_or(val, Int_.Min_value); break;
			case Dpl_itm_keys.Key_imageheight:			gallery_img_h = Bry_.To_int_or(val, Int_.Min_value); break;
			case Dpl_itm_keys.Key_gallerycaption:		gallery_caption = val; break;	// FUTURE: parse for {{int:}}?
			case Dpl_itm_keys.Key_galleryshowfilesize:	gallery_filesize = Dpl_itm_keys.Parse_as_bool(val, true); break;
			case Dpl_itm_keys.Key_galleryshowfilename:	gallery_filename = Dpl_itm_keys.Parse_as_bool(val, true); break;
			case Dpl_itm_keys.Key_ordermethod:			sort_tid = Dpl_sort.Parse_ordermethod(val); break;
			case Dpl_itm_keys.Key_mode:			mode_tid = Parse_mode(val, usr_dlg, page_ttl); break;
			case Dpl_itm_keys.Key_ignoresubpages:		ignoresubpages = Dpl_itm_keys.Parse_as_bool(val, false); break;
			case Dpl_itm_keys.Key_googlehack:
			case Dpl_itm_keys.Key_showcurid:		showcurid = Dpl_itm_keys.Parse_as_bool(val, false); break;
			default:
				String err_msg = String_.Format("dynamic_page_list:unknown_keyword: page={0} keyword={1}", String_.new_u8(page_ttl), key_id);
				usr_dlg.Log_many("", "", err_msg);
		}
	}
	private byte Parse_mode(byte[] val, Gfo_usr_dlg usr_dlg, byte[] page_ttl) {
		byte val_key = Dpl_itm_keys.Parse(val, Dpl_itm_keys.Key_categoryadd);
		if (val_key == Dpl_itm_keys.Key_ordered || val_key == Dpl_itm_keys.Key_unordered) {
			return val_key;
		}
		String err_msg = String_.Format("dynamic_page_list:unknown_mode: page={0} mode={1}", String_.new_u8(page_ttl), String_.new_u8(val));
		usr_dlg.Log_many("", "", err_msg);
		return Dpl_itm_keys.Key_unordered;
	}
	private byte Keys_get_or(byte[] bry) {
		byte key = Dpl_itm_keys.Parse(bry, Dpl_itm_keys.Key_false);	// NOTE: exclude is default value.
		switch (key) {
			case Dpl_itm_keys.Key_true: 			return Dpl_itm_keys.Key_true;
			case Dpl_itm_keys.Key_false: 			return Dpl_itm_keys.Key_false;
			default:								return Dpl_itm_keys.Key_null;
		}
	}
	private void Parse_ctg_date(byte[] val) {
			byte val_key = Keys_get_or(val);
			if (val_key == Dpl_itm_keys.Key_true)
				ctg_date = true;
			else
				ctg_date = false;
//			else {
//				if (val.length == 8) { 	// HACK: preg_match( '/^(?:[ymd]{2,3}|ISO 8601)$/'
//					ctg_date = true;
//					ctg_date_fmt = val;
//					if (ctg_date_fmt.length == 2) {
//						ctg_date_strip = true;
//						ctg_date_fmt = Bry_.Add(ctg_date_fmt, new byte[] {Byte_ascii.Ltr_y});
//					}
//				}
//				else
//					ctg_date = false;
//			}
	}
	private void Parse_missing_key(Gfo_usr_dlg usr_dlg, byte[] page_ttl, byte[] src, int fld_bgn, int fld_end) {
		byte[] key_bry = Bry_.Mid(src, fld_bgn, fld_end);
		boolean log = 
			(	Known_invalid_keys.Get_by_mid(src, fld_bgn, fld_end) != null	// known invalid key; just log it; handles common items like orcer and showcurid
			||	Bry_.Has_at_bgn(key_bry, Gfh_tag_.Comm_bgn)					// ignore comment-like keys; EX: <!--category=Ctg_0--> will have key of "<!--category="
			);
		String err_msg;
		if (log) {
			// known - so do not log 20210114
//			err_msg = String_.Format("dynamic_page_list:known_invalid_key: page={0} key={1}", String_.new_u8(page_ttl), String_.new_u8(key_bry));
//			usr_dlg.Log_many("", "", err_msg);
		}
		else {
			err_msg = String_.Format("dynamic_page_list:unknown_key: page={0} key={1}", String_.new_u8(page_ttl), String_.new_u8(key_bry));
			usr_dlg.Warn_many("", "", err_msg);
		}
	}
	private static final    Hash_adp_bry Known_invalid_keys = Hash_adp_bry.ci_a7()
	.Add_str_obj("orcer"						, Bool_obj_val.True)	// ignore as per http://en.wikinews.org/wiki/Template_talk:United_States; (Note it doesn't make a difference, as categoryadd is the default order method.)
	.Add_str_obj("addcategorydatefirst"			, Bool_obj_val.True)
	.Add_str_obj("mainspace"					, Bool_obj_val.True)
	.Add_str_obj("showcurid"					, Bool_obj_val.True)	// ignore for now; puts unique id at end of link for google news; https://www.mediawiki.org/wiki/Extension:DynamicPageList_%28Wikimedia%29; DATE:2015-09-07
	.Add_str_obj("googlehack"					, Bool_obj_val.True)	// same as showcurid
	.Add_str_obj("sort"							, Bool_obj_val.True)	// fr.n
	.Add_str_obj("supresserror"					, Bool_obj_val.True)	// fr.n
	.Add_str_obj("supresserrors"				, Bool_obj_val.True)	// frequency: 3 - 10
	.Add_str_obj("addlasteditor"				, Bool_obj_val.True)
	.Add_str_obj("noresultsheader"				, Bool_obj_val.True)
	.Add_str_obj("catergory"					, Bool_obj_val.True)
	.Add_str_obj("catrgory"						, Bool_obj_val.True)
	.Add_str_obj("allrevisionssince"			, Bool_obj_val.True)	// frequency: 1
	.Add_str_obj("limit"						, Bool_obj_val.True)
	.Add_str_obj("namespacename"				, Bool_obj_val.True)
	;
	public static final int Ns_filter_null = Int_.Min_value;
        // boolean ctg_date_strip = false;
	// byte[] ns_include = null;
	// byte[] ctg_date_fmt;
	public static Dpl_itm Parse(Xowe_wiki wiki, Xop_ctx ctx, byte[] page_ttl, byte[] src, Xop_xnde_tkn xnde) {
		Dpl_itm rv = new Dpl_itm();
		rv.Parse_src(wiki, ctx, page_ttl, src, xnde);
		return rv;
	}
}
class Dpl_stable_tid {
	public static final byte Tid_null = 0, Tid_include = 1, Tid_only = 2, Tid_exclude = 3;
	public static byte Parse(byte[] bry) {
		byte key = Dpl_itm_keys.Parse(bry, Dpl_redirect.Tid_exclude);	// NOTE: exclude is default value.
		switch (key) {
			case Dpl_itm_keys.Key_exclude: 			return Tid_exclude;
			case Dpl_itm_keys.Key_include: 			return Tid_include;
			case Dpl_itm_keys.Key_only: 			return Tid_only;
			default:								throw Err_.new_unhandled(key);
		}
	}
}
