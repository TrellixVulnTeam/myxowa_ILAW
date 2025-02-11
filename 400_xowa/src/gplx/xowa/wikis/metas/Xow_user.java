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
package gplx.xowa.wikis.metas; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Xow_user implements Gfo_invk {
	public byte[] Name() {return name;} private byte[] name = Bry_.new_a7("anonymous");
	public void Name_(byte[] v) {this.name = v;}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_name_))						name = m.ReadBry("v");
		else return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_name_ = "name_";
}
