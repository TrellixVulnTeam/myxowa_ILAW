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
package gplx.gflucene.searchers; import gplx.*;
public class Gflucene_searcher_qry {
	public String query;
	public int match_max;
	public int match_min;
	public long total = 0;
	public Gflucene_searcher_qry(String query, int match_min, int match_max) {
		this.query = query;
		this.match_min = match_min;
		this.match_max = match_max;
	}
}
