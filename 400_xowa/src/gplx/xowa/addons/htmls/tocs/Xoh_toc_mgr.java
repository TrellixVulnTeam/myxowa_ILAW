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
package gplx.xowa.addons.htmls.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.wkrs.tocs.*; import gplx.xowa.htmls.core.htmls.tidy.*;
public class Xoh_toc_mgr {
	private final    Ordered_hash itms = Ordered_hash_.New_bry();
	private final    Xoh_toc_wkr__lvl lvl_wkr = new Xoh_toc_wkr__lvl();
	private final    Xoh_toc_wkr__txt txt_wkr = new Xoh_toc_wkr__txt();
	private final    Xoh_toc_htmlr htmlr = new Xoh_toc_htmlr();
	public boolean Exists() {return exists && Enabled;} private boolean exists;
	public void Exists_y_() {exists = true;}
	private int default_pos;
	public void Toc_default_(int pos) { default_pos = pos;}
	private boolean notocseen;
	public void Hdr_notoc_y_() {notocseen = true;}
	private boolean forcetoc;
	public void Hdr_forcetoc_y_() {forcetoc = true;}
	public int Toc_bgn() {
		if (forcetoc) return default_pos;
		//if (exists) return toc_bgn;
		if (exists) return default_pos;
		if (notocseen) return -1;
		return default_pos;
	}
	private int toc_bgn;
	public void Toc_bgn_(int v) {
		this.toc_bgn = v;
		if (default_pos < 0)
			default_pos = v;
	}
	public void Clear() {
		this.exists = false;
		this.notocseen = false;
		this.default_pos = -1;
		itms.Clear();
		lvl_wkr.Clear();
		txt_wkr.Clear();
		htmlr.Clear();
		toc_bgn = -1;
	}
	public void Init(Xow_tidy_mgr_interface tidy_mgr, Xoa_url page_url, byte[] toc_title) {
		this.Clear();
		htmlr.Init(toc_title);
		txt_wkr.Init(tidy_mgr, page_url);
	}
	public Xoh_toc_itm Add(int hdr_num, byte[] hdr_txt) {
		Xoh_toc_itm itm = new Xoh_toc_itm();
		lvl_wkr.Calc_level(itm, hdr_num);
		txt_wkr.Calc_anch_text(itm, hdr_txt);
		itms.Add(itm.Anch(), itm);
		return itm;
	}
	public void To_html(Bry_bfr rv, Xoa_page pg, Xoh_wtr_ctx hctx, boolean toc_mode_is_pgbnr) {
		if (toc_mode_is_pgbnr && itms.Len() <= 3)
			return;
		To_html(rv, pg, hctx, toc_mode_is_pgbnr, 0);
	}
	public void To_html(Bry_bfr rv, Xoa_page pg, Xoh_wtr_ctx hctx, boolean toc_mode_is_pgbnr, int minimum) {
		htmlr.To_html(rv, pg, hctx, itms, toc_mode_is_pgbnr, minimum);
	}
	public byte[] Test__to_html() {
		Bry_bfr bfr = Bry_bfr_.New();
		htmlr.Test__to_html(bfr, itms);
		return bfr.To_bry_and_clear();
	}
	public static boolean Enabled = true;	// TEST
}
