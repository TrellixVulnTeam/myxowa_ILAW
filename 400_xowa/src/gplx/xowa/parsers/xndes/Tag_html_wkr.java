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
package gplx.xowa.parsers.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.parsers.*;
import gplx.langs.htmls.encoders.*;
public interface Tag_html_wkr {
	void Tag__process_name(byte[] name);
	void Tag__process_attr(byte[] key, byte[] val);
	void Tag__process_body(byte[] body);
	byte[] Tag__build(Xowe_wiki wiki, Xop_ctx ctx);
	void Tag__rls();
}
class Tag_html_wkr_noop implements Tag_html_wkr {
	public void Tag__process_name(byte[] name) {}
	public void Tag__process_attr(byte[] key, byte[] val) {}
	public void Tag__process_body(byte[] body) {}
	public byte[] Tag__build(Xowe_wiki wiki, Xop_ctx ctx) {return Bry_.Empty;}
	public void Tag__rls() {}
        public static final    Tag_html_wkr_noop Instance = new Tag_html_wkr_noop(); Tag_html_wkr_noop() {}
}
class Tag_html_wkr_basic implements Tag_html_wkr {
	private final    boolean atrs_encode;
	private final    Bry_bfr tmp_bfr;
	private byte[] tag_name;
	public Tag_html_wkr_basic(Bry_bfr tmp_bfr, boolean atrs_encode) {
		this.tmp_bfr = tmp_bfr;
		this.atrs_encode = atrs_encode;
	}
	public void Tag__process_name(byte[] tag_name) {
		this.tag_name = tag_name;
		tmp_bfr.Add_byte(Byte_ascii.Lt).Add(tag_name);	// EX: "<ref"
	}		
	public void Tag__process_attr(byte[] key, byte[] val) {
		int val_len = Bry_.Len(val);
		if (val_len == 0) return;	// ignore atrs with empty vals: EX:{{#tag:ref||group=}} PAGE:ru.w:Колчак,_Александр_Васильевич DATE:2014-07-03

		// NOTE: this behavior emulates /includes/parser/CoreParserFunctions.php|tagObj; REF: $attrText .= ' ' . htmlspecialchars( $name ) . '="' . htmlspecialchars( $value ) . '"';
		// write key
		tmp_bfr.Add_byte(Byte_ascii.Space);	// write space between html_args
		int key_len = Bry_.Len(key);
		boolean isclass = false; // to over come #tag  gallery enwiki:Flag_of_Greenland
		if (key_len > 0) {
			if (key_len == 5 && key[0] == 'c' && key[1] == 'l' && key[2] == 'a' && key[3] == 's' && key[4] == 's')
				isclass = true;
			if (atrs_encode)
				Gfo_url_encoder_.Id.Encode(tmp_bfr, key, 0, key_len);
			else
				tmp_bfr.Add(key);
			tmp_bfr.Add_byte(Byte_ascii.Eq);
		}

		// write val
		tmp_bfr.Add_byte(Byte_ascii.Quote);
		if (atrs_encode && !isclass) {
			// BIG HACK - convert '"' to .22
			val = convert22(val);
			Gfo_url_encoder_.Id.Encode(tmp_bfr, val, 0, val.length);
  	}
		else
			tmp_bfr.Add(val);
		tmp_bfr.Add_byte(Byte_ascii.Quote);
	}
	private byte[] convert22(byte[] val) {
		int len = val.length;
		int pos = 0;
		int sect = 0;
		Bry_bfr tmp_bfr = null;
		while (pos < len) {
			if (val[pos++] != '"') continue;
			if (tmp_bfr == null)
				tmp_bfr = Bry_bfr_.New();
			tmp_bfr.Add_mid(val, sect, pos-1);
			tmp_bfr.Add_str_a7(".22");
			sect = pos;
		}
		if (sect > 0) {
			tmp_bfr.Add_mid(val, sect, len);
			return tmp_bfr.To_bry();
		}
		return val;
	}
	public void Tag__process_body(byte[] body) {
		tmp_bfr.Add_byte(Byte_ascii.Gt);
		tmp_bfr.Add(body);
		tmp_bfr.Add_byte(Byte_ascii.Lt).Add_byte(Byte_ascii.Slash).Add(tag_name).Add_byte(Byte_ascii.Gt);	// EX: "</ref>"
	}
	public byte[] Tag__build(Xowe_wiki wiki, Xop_ctx ctx) {
		return tmp_bfr.To_bry_and_clear();
	}
	public void Tag__rls() {
		tmp_bfr.Mkr_rls();
	}
}
