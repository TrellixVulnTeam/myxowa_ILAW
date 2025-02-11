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
package gplx.xowa.files.fsdb.tsts; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import org.junit.*;
public class Xof_file_ext__pdf_tst {
	@Before public void init() {fxt.Reset();} private final Xof_file_fxt fxt = new Xof_file_fxt();
	@After public void term() {fxt.Rls();}
	@Test  public void Copy_thumb() {// PURPOSE: download pdf thumb only; [[File:Physical world.pdf|thumb]]
		fxt.Init_orig_db(Xof_orig_arg.new_comm("A.pdf", 2200, 1700));
		fxt.Init_fsdb_db(Xof_fsdb_arg.new_comm_thumb("A.pdf", 220, 170));
		fxt.Exec_get(Xof_exec_arg.new_thumb("A.pdf", 220));
		fxt.Test_fsys("mem/root/common/thumb/e/f/A.pdf/220px.jpg", "220,170");
	}
}
