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
package gplx.xowa.addons.bldrs.mass_parses.parses.pools; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.mass_parses.parses.*;
import gplx.xowa.wikis.data.tbls.*;
public class Xomp_page_itm implements Xowd_text_bry_owner {
	public Xomp_page_itm(int id) {this.id = id;}
	public int Id() {return id;} private final    int id;
	public int Ns_id() {return ns_id;} private int ns_id;
	public int Page_score() {return page_score;} private int page_score;
	public DateAdp Page_touched() {return page_touched;} private DateAdp page_touched;
	public byte[] Ttl_bry() {return ttl_bry;} private byte[] ttl_bry;
	public int Text_db_id() {return text_db_id;} private int text_db_id;
	public byte[] Text() {return text;} private byte[] text;
	public int Page_len() {return orig_page_len;} private int orig_page_len;
	public long Offset() {return orig_page_text_offset;} private long orig_page_text_offset;

	public void Init_by_page(int ns_id, byte[] ttl_bry, int text_db_id, int page_score, String page_touched, int page_len, long text_offset) {
		this.ns_id = ns_id;
		this.ttl_bry = ttl_bry;
		this.text_db_id = text_db_id;
		this.page_score = page_score;
		this.page_touched = DateAdp_.parse_fmt(page_touched, gplx.xowa.wikis.data.tbls.Xowd_page_tbl.Page_touched_fmt);
		this.orig_page_text_offset = text_offset;
		this.orig_page_len = page_len;
	}

	public int Page_id() {return id;}
	public void Set_text_bry_by_db(byte[] v) {this.text = v;}

	public static final    Xomp_page_itm Null = new Xomp_page_itm(-1);
}
