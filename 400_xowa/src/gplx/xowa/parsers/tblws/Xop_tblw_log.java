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
package gplx.xowa.parsers.tblws; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.log_msgs.*;
public class Xop_tblw_log {		
	private static final Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "tblw");
	public static final Gfo_msg_itm
		  Dangling							= Gfo_msg_itm_.new_warn_(owner, "dangling_tblw")
		, Elem_without_tbl					= Gfo_msg_itm_.new_warn_(owner, "elem_without_tbl")
//			, Row_trailing						= Gfo_msg_itm_.new_warn_(owner, "Row_trailing")
		, Caption_after_tr					= Gfo_msg_itm_.new_warn_(owner, "caption_after_tr")
		, Caption_after_td					= Gfo_msg_itm_.new_warn_(owner, "caption_after_td")
		, Caption_after_tc					= Gfo_msg_itm_.new_warn_(owner, "caption_after_tc")
		, Hdr_after_cell					= Gfo_msg_itm_.new_warn_(owner, "hdr_after_cell")
		, Tbl_empty							= Gfo_msg_itm_.new_warn_(owner, "tbl_empty")
		;
}
