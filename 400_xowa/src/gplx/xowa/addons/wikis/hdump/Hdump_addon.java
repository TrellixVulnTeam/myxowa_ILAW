/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2022 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.addons.wikis.hdump;
import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.addons.*;
import gplx.xowa.bldrs.wkrs.*;
public class Hdump_addon implements Xoax_addon_itm, Xoax_addon_itm__init {
	public void Init_addon_by_app(Xoa_app app) {}
	public void Init_addon_by_wiki(Xow_wiki wiki) {
		wiki.Hxtn_mgr().Reg_wkr(new Hdump_hxtn_page_wkr(wiki.Hxtn_mgr().Blob_tbl()));
	}

	public String Addon__key() {return "xowa.hdump";}
}
