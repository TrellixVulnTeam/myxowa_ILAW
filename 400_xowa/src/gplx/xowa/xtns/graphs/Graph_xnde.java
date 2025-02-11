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
package gplx.xowa.xtns.graphs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.logs.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*;
import gplx.xowa.htmls.heads.*;
import gplx.langs.jsons.*;
public class Graph_xnde implements Xox_xnde {
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, Object xatr_id_obj) {}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_bgn);
		boolean log_wkr_enabled = Log_wkr != Xop_log_basic_wkr.Null; if (log_wkr_enabled) Log_wkr.Log_end_xnde(ctx.Page(), Xop_log_basic_wkr.Tid_graph, src, xnde);
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_end);
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		// cleanup json
		byte[] json = Bry_.Mid(src, xnde.Tag_open_end(), xnde.Tag_close_bgn());
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		json = Json_fmtr.clean(tmp_bfr, json);
		Json_doc jdoc = app.Utl__json_parser().Parse(json);
		if (jdoc == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "invalid json; page=~{0}", ctx.Page().Url().To_bry_full_wo_qargs());
			return;
		}

		//Xoa_app_.Usr_dlg().Log_many("", "", "graph: page=~{0}", ctx.Page().Ttl().Full_db());
		ctx.Wiki().Logger().Log_many("graph: page=~{0}", ctx.Page().Ttl().Full_db());

		// swap out fsys_root; ISSUE#:553; DATE:2019-09-25
		Graph_json_save_mgr json_save_mgr = new Graph_json_save_mgr(app.Fsys_mgr());
		if (hctx.Mode_is_hdump()) {
			json = json_save_mgr.Save(wpg, ctx, wpg.Wiki().Domain_bry(), wpg.Url().To_str(), json, 0, json.length);
		}

		// enable graph
		Xoh_head_itm__graph itm_graph = ctx.Page().Html_data().Head_mgr().Itm__graph();
		itm_graph.Enabled_y_();

		// get version; NOTE: default is 2; REF: https://noc.wikimedia.org/conf/InitialiseSettings.php.txt and 'wgGraphDefaultVegaVer' => ['default' => 2]; also, extension.json and "GraphDefaultVegaVer": 2
		int version = jdoc.Get_val_as_int_or(Bry_.new_a7("version"), 0); // start with no version
		itm_graph.Version_(wpg.Url(), version);

		// add to bfr
		bfr.Add(Html__div_lhs_bgn);
		if (version > 0) {
			bfr.Add_int_fixed(version, 1);
			if (json_save_mgr.Root_dir_found())
				bfr.Add_byte_space().Add(Graph_json_load_mgr.HDUMP_ATR);
			bfr.Add(Html__div_lhs_end);
			bfr.Add(json);
		} else {
			// if no version specified use 2
			bfr.Add_int_fixed(2, 1);
			if (json_save_mgr.Root_dir_found())
				bfr.Add_byte_space().Add(Graph_json_load_mgr.HDUMP_ATR);
			bfr.Add(Html__div_lhs_end);
			int lastb = Bry_find_.Find_bwd__skip_ws(json, json.length, 0);
			bfr.Add_mid(json, 0, lastb - 1);
			bfr.Add(Bry_.new_a7(",\"version\": 2"));
			bfr.Add_byte(Byte_ascii.Curly_end);
		}
		bfr.Add(Html__div_rhs);
	}
	public static Xop_log_basic_wkr Log_wkr = Xop_log_basic_wkr.Null;
	private static final    byte[]
	  Html__div_lhs_bgn = Bry_.new_a7("<div class=\"mw-graph\" xo-graph-version=")
	, Html__div_lhs_end = Bry_.new_a7(">")
	, Html__div_rhs     = Bry_.new_a7("</div>\n")
	;
}
