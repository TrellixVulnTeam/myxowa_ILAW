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
package gplx.gfml; import gplx.*;
import org.junit.*;
public class z101_core_ndeInline_tst {
	GfmlParse_fxt fx = GfmlParse_fxt.new_();
	@Before public void setup() {
		fx.ini_RootLxr_Add(GfmlDocLxrs.NdeInline_lxr());
	}
	@Test  public void One() {
		fx.tst_Doc("a;", fx.nde_().Atru_("a"));
		fx.tst_Tkn("a;"
			,	fx.tkn_grp_
			(		fx.tkn_grp_ary_("a")
			,		fx.tkn_itm_(";")
			));
	}	
	@Test  public void Many() {
		fx.tst_Doc("a;b;"
			,	fx.nde_().Atru_("a")
			,	fx.nde_().Atru_("b")
			);
		fx.tst_Tkn("a;b;"
			,	fx.tkn_grp_
			(		fx.tkn_grp_ary_("a")
			,		fx.tkn_itm_(";")
			)
			,	fx.tkn_grp_
			(		fx.tkn_grp_ary_("b")
			,		fx.tkn_itm_(";")
			)
			);
	}	
}

