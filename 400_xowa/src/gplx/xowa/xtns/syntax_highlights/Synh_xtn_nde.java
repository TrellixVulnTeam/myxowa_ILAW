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
package gplx.xowa.xtns.syntax_highlights;

import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Err_;
import gplx.Hash_adp_bry;
import gplx.String_;
import gplx.core.primitives.Byte_obj_val;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.htmls.core.htmls.Xoh_html_wtr;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.Xop_root_tkn;
import gplx.xowa.parsers.htmls.Mwh_atr_itm;
import gplx.xowa.parsers.htmls.Mwh_atr_itm_owner1;
import gplx.xowa.parsers.xndes.Xop_xnde_tag;
import gplx.xowa.parsers.xndes.Xop_xnde_tkn;
import gplx.xowa.xtns.Xox_xnde;
import gplx.xowa.xtns.Xox_xnde_;

import gplx.core.texts.Base64Converter;
import gplx.xowa.parsers.xndes.Xop_xnde_tag_;
public class Synh_xtn_nde implements Xox_xnde, Mwh_atr_itm_owner1 {
	private byte[] lang = Bry_.Empty;
	private byte[] style = null;
	private byte[] klass = null;
	private byte[] id = null;
	private byte[] dir = null;
	private boolean inline = false;
	//private byte[] enclose = Bry_.Empty;
	private boolean line_enabled = false;
	private int start = 1;
	private Int_rng_mgr highlight_idxs = Int_rng_mgr_null.Instance;
	public Xop_xnde_tkn Xnde() {throw Err_.new_unimplemented(110);}
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, Object xatr_id_obj) {
		if (xatr_id_obj == null) return;
		Byte_obj_val xatr_id = (Byte_obj_val)xatr_id_obj;
		switch (xatr_id.Val()) {
			case Xatr_line:			line_enabled	= true; break;
			case Xatr_enclose:		inline			= true; break;
			case Xatr_inline:		inline			= true; break; // 2021-07-08 deprecate 'enclose' -> inline
			case Xatr_lang:			lang			= xatr.Val_as_bry(); break;
			case Xatr_style:		style			= xatr.Val_as_bry(); break;
			case Xatr_start:		start			= xatr.Val_as_int_or(1); break;
			case Xatr_highlight:	highlight_idxs	= new Int_rng_mgr_base(); highlight_idxs.Parse(xatr.Val_as_bry()); break;
			case Xatr_class:		klass			= xatr.Val_as_bry(); break;
			case Xatr_id:		id			= xatr.Val_as_bry(); break;
			case Xatr_dir:		dir			= xatr.Val_as_bry(); break;
		}
	}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		Xop_xnde_tag tag = xnde.Tag();
		ctx.Para().Process_block__xnde(tag, tag.Block_open());	// deactivate pre; pre; PAGE:en.w:Comment_(computer_programming); DATE:2014-06-24
		Xox_xnde_.Xatr__set(wiki, this, xatrs_hash, src, xnde);
		ctx.Para().Process_block__xnde(tag, tag.Block_close());	// deactivate pre; pre; PAGE:en.w:Comment_(computer_programming); DATE:2014-06-24
		ctx.Page().Html_data().Syntaxhighlight_(true);
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		byte[] tag;
		if (xnde.Tag().Id() == Xop_xnde_tag_.Tid__source)
			tag = Bry_source;
		else
			tag = Bry_syntaxhighlight;
		byte[] sh = Bry_.Add(tag, Bry_.Mid(src, xnde.Atrs_bgn(), xnde.Tag_close_end()));
		bfr.Add(Bry_.new_a7(Base64Converter.Encode(sh)));
		bfr.Add_str_a7("$$");
//		Synh_xtn_nde_.Make(bfr, app, src, xnde.Tag_open_end(), xnde.Tag_close_bgn(), lang, inline, style, line_enabled, start, highlight_idxs, klass, id, dir);
	}
	private final byte[]
	  Bry_source = Bry_.new_a7("<source ")
	, Bry_syntaxhighlight = Bry_.new_a7("<syntaxhighlight ")
	;
	private static final byte
	  Xatr_enclose = 1
	, Xatr_lang = 2
	, Xatr_style = 3
	, Xatr_line = 4
	, Xatr_start = 5
	, Xatr_highlight = 6
	, Xatr_inline = 7
	, Xatr_class = 8
	, Xatr_id = 9
	, Xatr_dir = 10
	;
	private static final    Hash_adp_bry xatrs_hash = Hash_adp_bry.ci_a7()
	.Add_str_byte("enclose"		, Xatr_enclose) // depracated
	.Add_str_byte("inline"		, Xatr_inline)
	.Add_str_byte("lang"		, Xatr_lang)
	.Add_str_byte("style"		, Xatr_style)
	.Add_str_byte("line"		, Xatr_line)
	.Add_str_byte("start"		, Xatr_start)
	.Add_str_byte("highlight"	, Xatr_highlight)
	.Add_str_byte("style"		, Xatr_style)
	.Add_str_byte("class"		, Xatr_class)
	.Add_str_byte("id"		, Xatr_id)
	.Add_str_byte("dir"		, Xatr_dir)
	;
}
