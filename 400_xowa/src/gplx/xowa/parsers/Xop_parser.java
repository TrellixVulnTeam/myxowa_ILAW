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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
import gplx.core.btries.*;
import gplx.xowa.langs.*; import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.wikis.nss.*;
import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.tmpls.*; import gplx.xowa.parsers.lists.*;
public class Xop_parser {	// NOTE: parsers are reused; do not keep any read-write state
	private final    Xowe_wiki wiki;
	private final    Btrie_fast_mgr tmpl_trie, wtxt_trie;
	private Xot_compile_data tmpl_props = new Xot_compile_data();	// NOTE: probably should not be a member variable, but leave for now; DATE:2016-12-02
	Xop_parser(Xowe_wiki wiki, Xop_lxr_mgr tmpl_lxr_mgr, Xop_lxr_mgr wtxt_lxr_mgr) {
		this.wiki = wiki;
		this.tmpl_lxr_mgr = tmpl_lxr_mgr; this.tmpl_trie = tmpl_lxr_mgr.Trie();
		this.wtxt_lxr_mgr = wtxt_lxr_mgr; this.wtxt_trie = wtxt_lxr_mgr.Trie();
	}
	public Xop_lxr_mgr Tmpl_lxr_mgr() {return tmpl_lxr_mgr;} private final    Xop_lxr_mgr tmpl_lxr_mgr;
	public Xop_lxr_mgr Wtxt_lxr_mgr() {return wtxt_lxr_mgr;} private final    Xop_lxr_mgr wtxt_lxr_mgr;
	public void Init_by_wiki(Xowe_wiki wiki) {
		tmpl_lxr_mgr.Init_by_wiki(wiki);
		wtxt_lxr_mgr.Init_by_wiki(wiki);
	}
	public void Init_by_lang(Xol_lang_itm lang) {
		tmpl_lxr_mgr.Init_by_lang(lang);
		wtxt_lxr_mgr.Init_by_lang(lang);
	}
	public byte[] Expand_tmpl(byte[] src) {	// expands {{A}} -> some wikitext; called by tmpl_invk, lang_msgs, sidebar
       		synchronized (this) {
		Xop_ctx ctx = Xop_ctx.New__sub__reuse_page(wiki.Parser_mgr().Ctx());	// PERF: reuse root ctx
		return Expand_tmpl(ctx, ctx.Tkn_mkr(), src);
                }
	}
	private byte[] Expand_tmpl(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, byte[] src) {return Expand_tmpl(tkn_mkr.Root(src), ctx, Xot_invk_temp.Null_frame, tkn_mkr, src);}
	public byte[] Expand_tmpl(Xop_root_tkn root, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, byte[] src) {return Expand_tmpl(root, ctx, Xot_invk_temp.Null_frame, tkn_mkr, src);}
	public byte[] Expand_tmpl(Xop_root_tkn root, Xop_ctx ctx, Xot_invk frame, Xop_tkn_mkr tkn_mkr, byte[] src) {
		Parse(root, ctx, tkn_mkr, src, Xop_parser_tid_.Tid__tmpl, tmpl_trie, Xop_parser_.Doc_bgn_bos);
		int len = root.Subs_len();
		for (int i = 0; i < len; ++i)
			root.Subs_get(i).Tmpl_compile(ctx, src, tmpl_props);
		return Xot_tmpl_wtr.Write_all(ctx, frame, root, src);
	}

	public byte[] Parse_text_to_html(Xop_ctx ctx, byte[] src) {
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		Parse_text_to_html(bfr, ctx, ctx.Page(), false, src);
		return bfr.To_bry_and_rls();
	}
	public void Parse_text_to_html(Bry_bfr trg, Xop_ctx pctx, Xoae_page page, boolean para_enabled, byte[] src) {Parse_text_to_html(trg, pctx, page, Xoh_wtr_ctx.Basic, para_enabled, src);}
	public void Parse_text_to_html(Bry_bfr trg, Xop_ctx pctx, Xoae_page page, Xoh_wtr_ctx hctx, boolean para_enabled, byte[] src) {
		Xop_ctx ctx = Xop_ctx.New__sub(wiki, pctx, page);
		Xop_tkn_mkr tkn_mkr = ctx.Tkn_mkr();
		Xop_root_tkn root = tkn_mkr.Root(src);
		Xop_parser parser = wiki.Parser_mgr().Main();
		byte[] wtxt = parser.Expand_tmpl(root, ctx, tkn_mkr, src);
		root.Reset();
		ctx.Para().Enabled_(para_enabled);
		parser.Parse_wtxt_to_wdom(root, ctx, ctx.Tkn_mkr(), wtxt, Xop_parser_.Doc_bgn_bos);
		wiki.Html_mgr().Html_wtr().Write_doc(trg, ctx, hctx, wtxt, root);
	}

	public Xot_defn_tmpl Parse_text_to_defn_obj(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xow_ns ns, byte[] name, byte[] src) {
		Xot_defn_tmpl rv = new Xot_defn_tmpl();
		Parse_text_to_defn(rv, ctx, tkn_mkr, ns, name, src); 
		return rv;
	}
	public void Parse_text_to_defn(Xot_defn_tmpl tmpl, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xow_ns ns, byte[] name, byte[] src) {
		Xop_root_tkn root = tkn_mkr.Root(src);
		Parse(root, ctx, tkn_mkr, src, Xop_parser_tid_.Tid__defn, tmpl_trie, Xop_parser_.Doc_bgn_bos);
		tmpl_props.OnlyInclude_exists = false; int subs_len = root.Subs_len();
		for (int i = 0; i < subs_len; i++)
			root.Subs_get(i).Tmpl_compile(ctx, src, tmpl_props);
		boolean only_include_chk = Bry_find_.Find_fwd(src, Xop_xnde_tag_.Bry__onlyinclude, 0, src.length) != Bry_find_.Not_found;
		if (only_include_chk) tmpl_props.OnlyInclude_exists = true;
		tmpl.Init_by_new(ns, name, src, root, tmpl_props.OnlyInclude_exists);
	}
	public void Parse_page_all_clear(Xop_root_tkn root, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, byte[] src) {
		ctx.Page().Clear_all(); ctx.App().Msg_log().Clear();
		Parse_text_to_wdom(root, ctx, tkn_mkr, src, Xop_parser_.Doc_bgn_bos);
	}
	public Xop_root_tkn Parse_text_to_wdom_old_ctx(Xop_ctx old_ctx, byte[] src, boolean doc_bgn_pos) {return Parse_text_to_wdom(Xop_ctx.New__sub__reuse_page(old_ctx), src, doc_bgn_pos);}
	public Xop_root_tkn Parse_text_to_wdom(Xop_ctx new_ctx, byte[] src, boolean doc_bgn_pos) {
		new_ctx.Para().Enabled_n_();
		Xop_tkn_mkr tkn_mkr = new_ctx.Tkn_mkr();
		Xop_root_tkn root = tkn_mkr.Root(src);
		Parse_text_to_wdom(root, new_ctx, tkn_mkr, src, doc_bgn_pos ? Xop_parser_.Doc_bgn_bos : Xop_parser_.Doc_bgn_char_0);
		return root;
	}
	public void Parse_text_to_wdom(Xop_root_tkn root, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, byte[] src, int doc_bgn_pos) {
		byte parse_tid_old = ctx.Parse_tid();// NOTE: must store parse_tid b/c ctx can be reused by other classes
		ctx.Parse_tid_(Xop_parser_tid_.Tid__tmpl);
		root.Reset();
		byte[] mid_bry = Expand_tmpl(root, ctx, tkn_mkr, src);
		mid_bry = Db_highlight.RemoveSH(mid_bry, ctx.Page());
                int len = mid_bry.length;
			int xpos = 0;
			while (xpos < len) {
				if (mid_bry[xpos] == Byte_ascii.Nl)
					xpos++;
				else
					break;
			}
                        if (xpos > 0)
                            mid_bry = Bry_.Mid(mid_bry, xpos);
//		Db_readwrite.writeFile(String_.new_u8(mid_bry), "d:/des/xowa_x/expand.txt");
//                Gfo_usr_dlg_.Instance.Log_many("", "", "mid_bry=~{0}", String_.new_u8(mid_bry));
//System.out.println(String_.new_u8(mid_bry));
		root.Data_mid_(mid_bry);
		root.Reset();
		Parse_wtxt_to_wdom(root, ctx, tkn_mkr, mid_bry, doc_bgn_pos);
		ctx.Parse_tid_(parse_tid_old);
	}
	public void Parse_wtxt_to_wdom(Xop_root_tkn root, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, byte[] wtxt, int doc_bgn_pos) {
		root.Root_src_(wtxt);	// always set latest src; needed for Parse_all wherein src will first be raw and then parsed tmpl
		Parse(root, ctx, tkn_mkr, wtxt, Xop_parser_tid_.Tid__wtxt, wtxt_trie, doc_bgn_pos);
	}
	public byte[] Parse_wtxt_to_html(Xop_ctx pctx, byte[] src) {
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		Xop_ctx ctx = Xop_ctx.New__sub(wiki, pctx, pctx.Page());
		Xop_tkn_mkr tkn_mkr = ctx.Tkn_mkr();
		Xop_root_tkn root = tkn_mkr.Root(src);
		Xop_parser parser = wiki.Parser_mgr().Main();
		ctx.Para().Enabled_(false);
		parser.Parse_wtxt_to_wdom(root, ctx, ctx.Tkn_mkr(), src, Xop_parser_.Doc_bgn_bos);
		wiki.Html_mgr().Html_wtr().Write_doc(bfr, ctx, Xoh_wtr_ctx.Basic, src, root);
		return bfr.To_bry_and_rls();
	}
	private void Parse(Xop_root_tkn root, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, byte[] src, byte parse_type, Btrie_fast_mgr trie, int doc_bgn_pos) {
		int len = src.length; if (len == 0) return;	// nothing to parse;
		// remove trailing whitespace
		len = Bry_find_.Find_bwd__skip_ws(src, len, 0);
		if (len == 0) return;	// nothing to parse;
		byte parse_tid_old = ctx.Parse_tid();	// NOTE: must store parse_tid b/c ctx can be reused by other classes
		ctx.Parse_tid_(parse_type);
		ctx.Parser__page_init(root, src);
		ctx.App().Parser_mgr().Core__uniq_mgr().Clear();
		Xop_list_tkn_new saved_tkn = ctx.Page().Prev_list_tkn();
		ctx.Page().Prev_list_tkn_(null); // reset list new
		Parse_to_src_end(root, ctx, tkn_mkr, src, trie, doc_bgn_pos, len);
		Xop_list_tkn_new.Reset(root, ctx);
		ctx.Page().Prev_list_tkn_(saved_tkn);
		ctx.Parser__page_term(root, src, len);
		ctx.Parse_tid_(parse_tid_old);
	}
	public int Parse_to_src_end(Xop_root_tkn root, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, byte[] src, Btrie_fast_mgr trie, int pos, int len) {
		if (Bry_.Len_eq_0(src)) return 0; // if empty array, return 0, else IndexError; PAGE:commons.wikimedia.org/wiki/File:England_in_the_UK_and_Europe.svg; ISSUE#:668; DATE:2020-02-17
		byte b = pos == -1 ? Byte_ascii.Nl : src[pos];	// simulate newLine at bgn of src; needed for lxrs which rely on \n (EX: "=a=")
		int txt_bgn = pos == -1 ? 0 : pos; Xop_tkn_itm txt_tkn = null;
		Btrie_rv trv = new Btrie_rv();
		Db_btrie db_btrie;
		if (trie == tmpl_trie)
			db_btrie = db_tmpl_trie;
		else
		if (trie == wtxt_trie)
			db_btrie = db_wtxt_trie;
		else
			db_btrie = null;
		if (db_btrie == null) {
			while (true) {
				Object o = trie.Match_at_w_b0(trv, b, src, pos, len);
				Xop_lxr lxr = null;
				if (o == null)				// no lxr found; char is txt; increment pos
					pos++;
				else {						// lxr found
					lxr = (Xop_lxr)o;
					if (txt_bgn != pos)		// chars exist between pos and txt_bgn; make txt_tkn; see NOTE_1
						txt_tkn = Txt_add(ctx, tkn_mkr, root, txt_tkn, txt_bgn, pos);
					ctx.Lxr_make_(true);
					pos = lxr.Make_tkn(ctx, tkn_mkr, root, src, len, pos, trv.Pos());
					if (ctx.Lxr_make()) {txt_bgn = pos; txt_tkn = null;}	// reset txt_tkn
				}
				if (pos == len) break;
				b = src[pos];
			}
		}
		else {
//                int ln = src.length;
//                if (ln > 100)
//                    System.out.println(String_.new_u8(src, 0, 100) + "...");
//                else
//                    System.out.println(String_.new_u8(src));
			while (true) {
				Object o = db_btrie.Match_at_w_b0(trv, b, src, pos, len);
				Xop_lxr lxr = null;
				if (o == null)				// no lxr found; char is txt; increment pos
					pos++;
				else {						// lxr found
					lxr = (Xop_lxr)o;
//                                System.out.println(lxr.toString());
					if (txt_bgn != pos)		// chars exist between pos and txt_bgn; make txt_tkn; see NOTE_1
						txt_tkn = Txt_add(ctx, tkn_mkr, root, txt_tkn, txt_bgn, pos);
					ctx.Lxr_make_(true);
					pos = lxr.Make_tkn(ctx, tkn_mkr, root, src, len, pos, trv.Pos());
/*
					int npos = lxr.Make_tkn(ctx, tkn_mkr, root, src, len, pos, trv.Pos());
                                        if (npos < pos) {
                                            int a=1;
                                        }
                                        pos = npos;
*/
					if (ctx.Lxr_make()) {txt_bgn = pos; txt_tkn = null;}	// reset txt_tkn
				}
				if (pos == len) break;
				b = src[pos];
			}
		}
		if (txt_bgn != pos) txt_tkn = Txt_add(ctx, tkn_mkr, root, txt_tkn, txt_bgn, pos);
		return pos;
	}
	public int Parse_to_stack_end(Xop_root_tkn root, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, byte[] src, int src_len, Btrie_fast_mgr trie, int pos, int end) {
		byte b = pos == -1 ? Byte_ascii.Nl : src[pos];	// simulate \n at bgn of src; needed for lxrs which rely on \n (EX: "=a=")
		int txt_bgn = pos == -1 ? 0 : pos; Xop_tkn_itm txt_tkn = null;
		Xop_lxr lxr = null;
		Btrie_rv trv = new Btrie_rv();
		Db_btrie db_btrie;
		if (trie == tmpl_trie)
			db_btrie = db_tmpl_trie;
		else
		if (trie == wtxt_trie)
			db_btrie = db_wtxt_trie;
		else
			db_btrie = null;
		if (db_btrie == null) {
			while (true) {
				lxr = null;
	
				Object o = trie.Match_at_w_b0(trv, b, src, pos, src_len);
				if (o == null)				// no lxr found; char is txt; increment pos
					pos++;
				else {						// lxr found
					lxr = (Xop_lxr)o;
					if (txt_bgn != pos)		// chars exist between pos and txt_bgn; make txt_tkn; see NOTE_1
						txt_tkn = Txt_add(ctx, tkn_mkr, root, txt_tkn, txt_bgn, pos);
					ctx.Lxr_make_(true);
					pos = lxr.Make_tkn(ctx, tkn_mkr, root, src, src_len, pos, trv.Pos());
					if (ctx.Lxr_make()) {txt_bgn = pos; txt_tkn = null;}	// reset txt_tkn
				}
				if (	pos >= end 
					&&	ctx.Stack_len() == 0	// check stack is 0 to avoid dangling templates
					) {
					if		(o == null) {}		// last sequence is not text; avoids splitting words across blocks; EX: 4 block and word of "abcde" will split to "abcd" and "e"
					else {
						if (lxr != null) { 
							boolean stop = true;
							switch (lxr.Lxr_tid()) {
								case Xop_lxr_.Tid_eq:
								case Xop_lxr_.Tid_nl:
									stop = false;
									break;
							}
							if (stop)
								break;
						}
						else {
							break;
						}
					}
				}
				if (pos >= src_len) break;
				b = src[pos];
			}
		}
		else {
			while (true) {
				lxr = null;
	
				Object o = db_btrie.Match_at_w_b0(trv, b, src, pos, src_len);
				if (o == null)				// no lxr found; char is txt; increment pos
					pos++;
				else {						// lxr found
					lxr = (Xop_lxr)o;
					if (txt_bgn != pos)		// chars exist between pos and txt_bgn; make txt_tkn; see NOTE_1
						txt_tkn = Txt_add(ctx, tkn_mkr, root, txt_tkn, txt_bgn, pos);
					ctx.Lxr_make_(true);
					pos = lxr.Make_tkn(ctx, tkn_mkr, root, src, src_len, pos, trv.Pos());
					if (ctx.Lxr_make()) {txt_bgn = pos; txt_tkn = null;}	// reset txt_tkn
				}
				if (	pos >= end 
					&&	ctx.Stack_len() == 0	// check stack is 0 to avoid dangling templates
					) {
					if		(o == null) {}		// last sequence is not text; avoids splitting words across blocks; EX: 4 block and word of "abcde" will split to "abcd" and "e"
					else {
						if (lxr != null) { 
							boolean stop = true;
							switch (lxr.Lxr_tid()) {
								case Xop_lxr_.Tid_eq:
								case Xop_lxr_.Tid_nl:
									stop = false;
									break;
							}
							if (stop)
								break;
						}
						else {
							break;
						}
					}
				}
				if (pos >= src_len) break;
				b = src[pos];
			}
		}
		if (txt_bgn != pos) txt_tkn = Txt_add(ctx, tkn_mkr, root, txt_tkn, txt_bgn, pos);
		return pos;
	}
	private static Xop_tkn_itm Txt_add(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, Xop_tkn_itm tkn, int txt_bgn, int pos) {
		if (pos == Xop_parser_.Doc_bgn_bos) return null;	// don't make txt_tkn for Bos_pos
		if (tkn == null) {									// no existing txt_tkn; create new one
			tkn = tkn_mkr.Txt(txt_bgn, pos);
			ctx.Subs_add(root, tkn);
		}
		else												// existing txt_tkn; happens for false matches; EX: abc[[\nef[[a]]; see NOTE_1
			tkn.Src_end_(pos);
		return tkn;
	}
	public static Xop_parser new_(Xowe_wiki wiki, Xop_lxr_mgr tmpl_lxr_mgr, Xop_lxr_mgr wtxt_lxr_mgr) {return new Xop_parser(wiki, tmpl_lxr_mgr, wtxt_lxr_mgr);}
	public static Xop_parser new_wiki(Xowe_wiki wiki) {
		Xop_parser rv = new Xop_parser(wiki, Xop_lxr_mgr.new_tmpl_(), Xop_lxr_mgr.new_wiki_());
		rv.Init_by_wiki(wiki);
		rv.Init_by_lang(wiki.Lang());
		rv.Set_expand();
		return rv;
	}
	private Db_btrie db_wtxt_trie;
	private Db_btrie db_tmpl_trie;
	public void Set_expand() {
		byte[] md5 = wtxt_trie.Md5();
		if (Bry_.Eq(md5, Db_btrie_wtxt_1.Hash()))
			db_wtxt_trie = new Db_btrie_wtxt_1(wtxt_trie.Objs());
		else if (Bry_.Eq(md5, Db_btrie_wtxt_2.Hash()))
			db_wtxt_trie = new Db_btrie_wtxt_2(wtxt_trie.Objs());
		else
			wtxt_trie.Dumpit("parser anchor wtxt");
//            else if (Bry_.Eq(md5, Db_btrie_src_end_c.Hash()))
//		db_wtxt_trie = new Db_btrie_src_end_c(wtxt_trie.Objs());

		md5 = tmpl_trie.Md5();
		if (Bry_.Eq(md5, Db_btrie_src_end_b.Hash()))
			db_tmpl_trie = new Db_btrie_src_end_b(tmpl_trie.Objs());
		else
			tmpl_trie.Dumpit("parser anchor tmpl");
	}
}
/*
NOTE_1
abc[[\nef[[a]]
<BOS>	: txt_bgn = 0; txt_tkn = null;
abc		: increment pos
[[\n	: lnki lxr
		: (1): txt_tkn == null, so create txt_tkn with (0, 3)
		: (2): lxr.Make_tkn() entered for lnki; however \n exits lnki
		: (3): note that ctx.Lxr_make == false, so txt_bgn/txt_tkn is not reset
ef		: still just text; increment pos
[[a]]	: lnki entered
		: (1): txt_tkn != null; set end to 8
		: (2): lxr.Make_tkn() entered and lnki made
		: (3): note that ctx.Lxr_make == true, so txt_bgn = 13 and txt_tkn = null
*/
