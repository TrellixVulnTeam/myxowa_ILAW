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
package gplx.xowa; import gplx.*;
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*;
import gplx.xowa.wikis.data.Xow_db_mgr;
import gplx.xowa.wikis.data.tbls.Xowd_page_tbl;
public class Db_breadcrumb {
	private Db_conn conn;
	private final Xow_wiki wiki;
	private boolean initialised = false;
	private final boolean hasdata = true;
	public Db_breadcrumb(Xow_wiki wiki) {
		this.wiki = wiki;
	}
	private void Init() {
		Xow_db_mgr db_mgr = wiki.Data__core_mgr();
		if (db_mgr != null) {
			Xowd_page_tbl page_tbl = db_mgr.Db__core().Tbl__page();
			this.conn = page_tbl.Conn();
		}
		boolean found = false;
		// need to check that the table 'parent' exists
		try {
			Db_stmt stmt = conn.Stmt_sql("select * from parent limit 1;");
			found = true;
		}
		catch (Exception e) { }
		if (!found) {
			Sqlite_engine_.Tbl_create(conn, "parent", "create table parent (ttl varchar(255), parent varchar(255), ttl_namespace int, parent_namespace int);");
			Sqlite_engine_.Tbl_create(conn, "parent", "create unique index if not exists parentindex on parent(ttl, ttl_namespace);");
		}
		initialised = true;
	}
	public List_adp Get_breadcrumbs(Xoa_ttl ttl, byte[] isin) {
		if (!initialised) Init();
		int nsid = ttl.Ns().Id();
		List_adp breadcrumb_list = List_adp_.New();
		if (hasdata) {
			Db_stmt stmt = conn.Stmt_sql(sql);
			stmt.Crt_bry_as_str("ttl", ttl.Page_txt());
			stmt.Crt_int("ttl_namespace", nsid);
			Db_rdr rdr = stmt.Exec_select__rls_auto();
			try {
				while (rdr.Move_next()) {
					byte[] parent = rdr.Read_str("parent").getBytes();
					// HACK! presenceof '*' means it does not exist in page table!!
					if (parent[0] != '*')
						breadcrumb_list.Add(parent);
				}
			} finally {rdr.Rls();}

		} else {
			breadcrumb_list.Add(isin);
		}
		return breadcrumb_list;
	}
	public void Insert(Xoa_ttl ttl, byte[] isin) {
		if (!initialised) Init();
                // somehow need to batch this up (say in groups of 100 / 1000?)
		// insert into parent (ttl, parent, ttl_namespace, parent_namespace) values(?,?,?,?)
		Xoa_ttl parent = Xoa_ttl.Parse(wiki, isin);
		/*Db_stmt stmt = conn.Stmt_sql("insert into parent (ttl, parent, ttl_namespace, parent_namespace) values(?,?,?,?);");
		stmt.Crt_bry_as_str("ttl", ttl.Page_txt());
		stmt.Crt_int("ttl_namespace", ttl.Ns().Id());
		stmt.Crt_bry_as_str("parent", parent.Page_txt());
		stmt.Crt_int("parent_namespace", parent.Ns().Id());
		stmt.Exec_insert(); // ugh!*/
		// assume somewhere else is committing?!?!
	}
	private static final String sql = String_.Concat_lines_nl_skip_last
	( "WITH RECURSIVE bread(count, parent, parent_namespace) AS ("
	, "  SELECT 1, parent, parent_namespace FROM parent WHERE ttl=? and ttl_namespace=?"
	, "  UNION ALL"
	, "  SELECT bread.count+1, parent.parent, parent.parent_namespace FROM parent, bread WHERE bread.parent=parent.ttl and bread.parent_namespace=parent.ttl_namespace)"
	, "SELECT parent FROM bread ORDER BY count DESC;"
	);
}
