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
package gplx.dbs; import gplx.*;
public class Db_idx_itm {
	public String Xto_sql() {return sql;} private String sql;
	public static Db_idx_itm sql_(String sql) {
		Db_idx_itm rv = new Db_idx_itm();
		rv.sql = sql;
		return rv;
	}
	public static Db_idx_itm[] ary_sql_(String... ary) {
		int len = ary.length;
		Db_idx_itm[] rv = new Db_idx_itm[len];
		for (int i = 0; i < len; i++) {
			rv[i] = sql_(ary[i]);
		}
		return rv;
	}
}
