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
package gplx.xowa.htmls.heads; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.xowa.guis.*;
public class Xoh_head_itm__css extends Xoh_head_itm__base {
	@Override public byte[] Key() {return Xoh_head_itm_.Key__css;}
	@Override public int Flags() {return Flag__css_text;}
	@Override public void Write_css_text(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_css_style_itm(wiki.Ctg__catpage_mgr().Missing_ctg_cls_css());
		app.Html_mgr().Page_mgr().Write_css(wtr);
	}
}
