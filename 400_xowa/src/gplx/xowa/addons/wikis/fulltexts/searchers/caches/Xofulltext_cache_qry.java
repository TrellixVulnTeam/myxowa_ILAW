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
package gplx.xowa.addons.wikis.fulltexts.searchers.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.fulltexts.*;
public class Xofulltext_cache_qry {
	public Xofulltext_cache_qry(int id, byte[] text) {
		this.id = id;
		this.text = text;
	}
	public int            Id()    {return id;} private final    int id;
	public byte[]         Text()  {return text;} private final    byte[] text;
	public Ordered_hash   Pages() {return pages;} private final    Ordered_hash pages = Ordered_hash_.New();
	public boolean done;
}
