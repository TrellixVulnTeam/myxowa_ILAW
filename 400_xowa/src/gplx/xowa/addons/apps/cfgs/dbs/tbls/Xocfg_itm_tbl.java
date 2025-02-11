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
package gplx.xowa.addons.apps.cfgs.dbs.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.dbs.*;
import gplx.dbs.*; import gplx.dbs.utls.*;
public class Xocfg_itm_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__itm_id, fld__itm_key, fld__itm_scope, fld__itm_type, fld__itm_dflt, fld__itm_html_atrs, fld__itm_html_cls;
	private final    Db_conn conn;
	public Xocfg_itm_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "cfg_itm";
		this.fld__itm_id			= flds.Add_int_pkey("itm_id");				// EX: '1'
		this.fld__itm_key			= flds.Add_str("itm_key", 255);				// EX: 'cfg_1'
		this.fld__itm_scope			= flds.Add_int("itm_scope");				// EX: '1'; ENUM: Xoitm_scope_enum
		this.fld__itm_type			= flds.Add_str("itm_type", 255);			// EX: '1'; ENUM: Xoitm_type_enum
		this.fld__itm_dflt			= flds.Add_str("itm_dflt", 4096);			// EX: 'abc'
		this.fld__itm_html_atrs		= flds.Add_str("itm_html_atrs", 255);		// EX: 'size="5"'
		this.fld__itm_html_cls		= flds.Add_str("itm_html_cls", 255);		// EX: 'xocfg__bool__readonly' 
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds
		, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, fld__itm_key, fld__itm_key)));
	}
	public void Upsert(int id, String key, int scope, String type, String dflt, String html_atrs, String html_cls) {
		Db_tbl__crud_.Upsert(conn, tbl_name, flds, String_.Ary(fld__itm_id), id, key, scope, type, dflt, html_atrs, html_cls);
	}
	public int Select_id_or(String key, int or) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld__itm_key).Crt_str(fld__itm_key, key).Exec_select__rls_auto();
		try {return rdr.Move_next() ? rdr.Read_int(fld__itm_id) : or;}
		finally {rdr.Rls();}
	}
	public Xocfg_itm_row Select_by_key_or_null(String key) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld__itm_key).Crt_str(fld__itm_key, key).Exec_select__rls_auto();
		try {return rdr.Move_next() ? Load(rdr) : null;}
		finally {rdr.Rls();}
	}
	private Xocfg_itm_row Load(Db_rdr rdr) {
		return new Xocfg_itm_row
		( rdr.Read_int(fld__itm_id)
		, rdr.Read_str(fld__itm_key)
		, rdr.Read_int(fld__itm_scope)
		, rdr.Read_str(fld__itm_type)
		, rdr.Read_str(fld__itm_dflt)
		, rdr.Read_str(fld__itm_html_atrs)
		, rdr.Read_str(fld__itm_html_cls)
		);
	}
	public void Rls() {}
}
