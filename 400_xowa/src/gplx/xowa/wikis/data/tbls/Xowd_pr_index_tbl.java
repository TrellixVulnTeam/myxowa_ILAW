/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.dbs.*; import gplx.dbs.qrys.*;
import gplx.xowa.wikis.data.*;
public class Xowd_pr_index_tbl implements Db_tbl {
	public static final    String Fld_page_id = "pr_page_id", Fld_count = "pr_count", Fld_q0 = "pr_q0", Fld_q1 = "pr_q1", Fld_q2 = "pr_q2", Fld_q3 = "pr_q3", Fld_q4 = "pr_q4";
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_page_id, fld_count, fld_q0, fld_q1, fld_q2, fld_q3, fld_q4;
	private final    Db_conn conn; private final    Db_stmt_bldr stmt_bldr = new Db_stmt_bldr();
	public Xowd_pr_index_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name = TBL_NAME;
		fld_page_id   = flds.Add_int_pkey(Fld_page_id);
		fld_count     = flds.Add_int(Fld_count);
		fld_q0        = flds.Add_int(Fld_q0);
		fld_q1        = flds.Add_int(Fld_q1);
		fld_q2        = flds.Add_int(Fld_q2);
		fld_q3        = flds.Add_int(Fld_q3);
		fld_q4        = flds.Add_int(Fld_q4);
		stmt_bldr.Conn_(conn, tbl_name, flds, fld_page_id);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name; public static final String TBL_NAME = "pr_index";
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Rls() {}

}
