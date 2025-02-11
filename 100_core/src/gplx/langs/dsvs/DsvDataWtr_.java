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
package gplx.langs.dsvs; import gplx.*;
public class DsvDataWtr_ {
	public static DsvDataWtr csv_hdr_() {
		DsvDataWtr rv = new DsvDataWtr();
		rv.InitWtr(DsvStoreLayout.Key_const, DsvStoreLayout.csv_hdr_());
		return rv;
	}
	public static DsvDataWtr csv_dat_() {
		DsvDataWtr rv = new DsvDataWtr();
		rv.InitWtr(DsvStoreLayout.Key_const, DsvStoreLayout.csv_dat_());
		return rv;
	}
	public static DsvDataWtr new_() {return new DsvDataWtr();}
}
