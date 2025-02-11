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
package gplx.xowa.parsers.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.parsers.tblws.*; import gplx.xowa.parsers.xndes.*;
public class Xop_list_wkr implements Xop_ctx_wkr {
	public void Ctor_ctx(Xop_ctx ctx) {}
	public void Page_bgn(Xop_ctx ctx, Xop_root_tkn root) {}
	public void Page_end(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int src_len) {}
	public boolean List_dirty() {throw Err_.new_unimplemented(91);}
	public boolean Dd_chk() {return dd_chk;} public Xop_list_wkr Dd_chk_(boolean v) {dd_chk = v; return this;} private boolean dd_chk;
	public void AutoClose(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, Xop_tkn_itm tkn) {}
	public int MakeTkn_bgn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {// REF.MW: Parser|doBlockLevels
            int pos_bgn = bgn_pos; // need to remember this even if changed by next line!
		if (bgn_pos == Xop_parser_.Doc_bgn_bos) bgn_pos = 0;	// do not allow -1 pos

		// pop hdr if exists; EX: \n== a ==\n*b; \n* needs to close hdr
		int acsPos = ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_hdr);
		if (acsPos != -1) ctx.Stack_pop_til(root, src, acsPos, true, bgn_pos, cur_pos, Xop_tkn_itm_.Tid_list);

		// close apos
		ctx.Apos().End_frame(ctx, root, src, bgn_pos, false);

		// reset para if not already in a list
		if (ctx.Page().Prev_list_tkn() == null)
			ctx.Para().Process_nl(ctx, root, src, bgn_pos, cur_pos);

		// Multiple prefixes may abut each other for nested lists.
		byte b = 0;
		while (cur_pos < src_len) {
			b = src[cur_pos];
			if (b == Byte_ascii.Star || b == Byte_ascii.Hash || b == Byte_ascii.Semic || b == Byte_ascii.Colon)
				cur_pos++;
			else
				break;
		}
		// is the rest of the line whitespace?
		int peek_pos = cur_pos;
		while (peek_pos < src_len) {
			b = src[peek_pos];
			if (b == Byte_ascii.Space || b == Byte_ascii.Tab)
				peek_pos++;
			else
				break;
		}
		if (b == Byte_ascii.Nl) // blank line - ignore
			return peek_pos;
		// HACK 20210810 if \n*]] ignore asterix
		if (b == Byte_ascii.Brack_end) {
			if (peek_pos + 1 < src_len && src[peek_pos + 1] == Byte_ascii.Brack_end)
				return peek_pos;
		}
		//Xop_list_tkn_new itm = tkn_mkr.List_bgn(bgn_pos, cur_pos, curSymAry[curSymLen - 1], curSymLen).List_path_(posBldr.XtoIntAry()).List_uid_(listId);
		// bgn_pos + 1 skips the nl char
		Xop_list_tkn_new itm = new Xop_list_tkn_new(pos_bgn + 1, cur_pos, ctx.Page().Prev_list_tkn());
		ctx.Subs_add_and_stack(root, itm);
		ctx.Page().Prev_list_tkn_(itm);
		// peek ahead for a table eg :{|
		if (peek_pos + 2 < src_len &&
		    src[peek_pos] == '{' && src[peek_pos+1] == '|') {
		  Xop_list_tkn_new list_itm = ctx.Page().Prev_list_tkn();
		  ctx.Page().Prev_list_tkn_(null);
			peek_pos = ctx.Tblw().Make_tkn_bgn(ctx, tkn_mkr, root, src, src_len, bgn_pos, peek_pos+2, false, Xop_tblw_wkr.Tblw_type_tb, Xop_tblw_wkr.Called_from_list, -1, -1, list_itm);
		}
		//return cur_pos;
		return peek_pos;
	}
}
