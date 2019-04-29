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
public class Db_null implements gplx.core.brys.Bfr_arg {
	public void Bfr_arg__add(Bry_bfr bfr) {bfr.Add_str_a7(Null_str);}
	@Override public String toString() {return Null_str;}
	public static final String Null_str = "NULL";
        public static final Db_null Instance = new Db_null(); Db_null() {}
}
