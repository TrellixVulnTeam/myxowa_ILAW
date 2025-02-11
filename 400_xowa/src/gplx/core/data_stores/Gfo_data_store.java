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
package gplx.core.data_stores; import gplx.*;
public class Gfo_data_store {
	private final    Hash_adp hash = Hash_adp_.New();
	public Gfo_data_itm Get_or_null(String key) {
		return (Gfo_data_itm)hash.Get_by(key);
	}
	public void Set(Gfo_data_itm itm) {
		hash.Add_if_dupe_use_nth(itm.Key(), itm);
	}
	public void Clear() {
		hash.Clear();
	}
} 
