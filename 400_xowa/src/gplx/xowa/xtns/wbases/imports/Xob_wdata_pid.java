/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.wbases.imports;

import gplx.Bry_;
import gplx.Gfo_invk;
import gplx.String_;
import gplx.Ordered_hash;
import gplx.Err_;
import gplx.dbs.Db_conn;
import gplx.langs.jsons.Json_doc;
import gplx.langs.jsons.Json_parser;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.bldrs.Xob_bldr;
import gplx.xowa.bldrs.wkrs.Xob_itm_dump_base;
import gplx.xowa.bldrs.wkrs.Xob_page_wkr;
import gplx.xowa.wikis.data.tbls.Xowd_page_itm;
import gplx.xowa.xtns.wbases.Wdata_wiki_mgr;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_type_;
import gplx.xowa.xtns.wbases.core.Wdata_dict_mainsnak;
import gplx.xowa.xtns.wbases.core.Wdata_langtext_itm;
import gplx.xowa.xtns.wbases.dbs.Wbase_pid_tbl;
import gplx.xowa.xtns.wbases.dbs.Xowb_prop_tbl;
import gplx.xowa.xtns.wbases.parsers.Wdata_doc_parser;
import gplx.xowa.xtns.wbases.core.Wdata_list_label;

import gplx.xowa.xtns.wbases.parsers.Wdata_doc_parser_v2;
public class Xob_wdata_pid extends Xob_itm_dump_base implements Xob_page_wkr, Gfo_invk {
	private Db_conn conn;
	private Wbase_pid_tbl tbl__pid;
	private Xowb_prop_tbl tbl__prop;
	private final Json_parser jdoc_parser = new Json_parser();
	public Xob_wdata_pid(Db_conn conn) {
		this.conn = conn;
	}
	public Xob_wdata_pid Ctor(Xob_bldr bldr, Xowe_wiki wiki) {
		this.Cmd_ctor(bldr, wiki); 
		return this;
	}
	public String Page_wkr__key() {return gplx.xowa.bldrs.Xob_cmd_keys.Key_wbase_pid;}
	public void Page_wkr__bgn() {this.Pid__bgn();}
	public void Page_wkr__run(Xowd_page_itm page) {
		if (page.Ns_id() != Wdata_wiki_mgr.Ns_property) return;

		Json_doc jdoc = jdoc_parser.Parse(page.Text()); 
		if (jdoc == null) {
			bldr.Usr_dlg().Warn_many(GRP_KEY, "json.invalid", "json is invalid: ns=~{0} id=~{1}", page.Ns_id(), String_.new_u8(page.Ttl_page_db()));
			return;
		}
		Pid__run(jdoc);
	}
	public void Page_wkr__run_cleanup() {}
	public void Page_wkr__end() {this.Pid__end();}
	public void Pid__bgn() {
		if (conn == null) // conn will be null unless test
			conn = wiki.Data__core_mgr().Db__wbase().Conn();

		// init wbase_pid
		tbl__pid = Wbase_pid_tbl.New_make(conn);
		tbl__pid.Create_tbl();
		tbl__pid.Insert_bgn();

		// init wbase_prop
		tbl__prop = new Xowb_prop_tbl(conn);
		tbl__prop.Create_tbl();
		tbl__prop.Insert_bgn();
	}
	public void Pid__run(Json_doc jdoc) {
		Wdata_doc_parser wdoc_parser = app.Wiki_mgr().Wdata_mgr().Wdoc_parser(jdoc);
		byte[] pid = wdoc_parser.Parse_qid(jdoc);

		// add datatype
		byte[] datatype = jdoc.Root_nde().Get_as_bry(Wdata_dict_mainsnak.Itm__datatype.Key_str());
		int tid = Wbase_claim_type_.Get_tid_by_scrib(datatype);
		if (tid == Wbase_claim_type_.Tid__unknown) {
			throw Err_.new_missing_key(String_.new_u8(datatype));
		}
		tbl__prop.Insert_cmd_by_batch(pid, tid);

		// add langs
		//Ordered_hash list = wdoc_parser.Parse_langvals(pid, jdoc, Wdata_doc_parser_v2.Bry_labels);
		Wdata_list_label list = wdoc_parser.Parse_langvals(pid, jdoc, Wdata_doc_parser_v2.Bry_labels);
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_langtext_itm label = (Wdata_langtext_itm)list.Get_at(i);
			tbl__pid.Insert_cmd_by_batch(label.Lang(), label.Text(), pid);
		}
	}
	public void Pid__end() {
		tbl__pid.Insert_end();
		tbl__pid.Create_idx();
		tbl__prop.Insert_end();
	}
	private static final String GRP_KEY = "xowa.wdata.pid_wkr";
}
