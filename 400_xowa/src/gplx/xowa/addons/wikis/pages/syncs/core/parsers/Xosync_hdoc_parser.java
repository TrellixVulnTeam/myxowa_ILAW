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
package gplx.xowa.addons.wikis.pages.syncs.core.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.pages.syncs.*; import gplx.xowa.addons.wikis.pages.syncs.core.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.xowa.wikis.domains.*;
public class Xosync_hdoc_parser {
	public byte[] Parse_hdoc(Xow_domain_itm wiki_domain, byte[] page_url, List_adp imgs, byte[] src) {
		// init
		Bry_bfr bfr = Bry_bfr_.New();
		Xosync_img_src_parser img_src_parser = new Xosync_img_src_parser().Init_by_page(wiki_domain, page_url, imgs);
		int cur = 0, src_len = src.length;
		Gfh_tag_rdr tag_rdr = Gfh_tag_rdr.New__html().Init(page_url, src, cur, src_len);

		// loop src
		while (true) {
			// look for "<"
			int find = Bry_find_.Find_fwd(src, Byte_ascii.Angle_bgn, cur, src_len);

			// "<" not found; add rest of src and stop
			if (find == Bry_find_.Not_found) {
				bfr.Add_mid(src, cur, src_len);
				break;
			}

			// "<" found; add everything between cur and "<"
			bfr.Add_mid(src, cur, find);

			// parse "<"
			cur = Parse_tag(bfr, tag_rdr, img_src_parser, src, src_len, find);
		}

		return bfr.To_bry_and_clear();
	}
	private int Parse_tag(Bry_bfr bfr, Gfh_tag_rdr tag_rdr, Xosync_img_src_parser img_src_parser, byte[] src, int src_len, int pos) {
		// note that entry point is at "<"
		tag_rdr.Pos_(pos);
		int nxt_pos = tag_rdr.Pos() + 1;

		// "<" is at EOS
		if (nxt_pos == src_len) {
			bfr.Add_byte(Byte_ascii.Angle_bgn);
			return src_len;
		}

		// check if head or tail; EX: "<a>" vs "</a>"
		byte nxt_byte = src[nxt_pos];
		// skip comment; needed else comment may gobble up rest of text; see test; DATE:2016-09-10
		if (nxt_byte == Byte_ascii.Bang) {	// assume comment; EX:"<!--"
			int end_comm = Bry_find_.Move_fwd(src, Gfh_tag_.Comm_end, nxt_pos);
			if (end_comm == Bry_find_.Not_found) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "end comment not found; src=~{0}", String_.new_u8(src));
				end_comm = src_len;
			}
			return end_comm;
		}
		Gfh_tag cur = nxt_byte == Byte_ascii.Slash ? tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__any) : tag_rdr.Tag__move_fwd_head();
		if (cur.Tag_is_tail()) {}
		else {
			int cur_name_id = cur.Name_id();
			switch (cur_name_id) {
				case Gfh_tag_.Id__span:
                        if (cur.Atrs__cls_has(Bry__span__edit_section)) {	// remove edit-section
                            tag_rdr.Tag__move_fwd_tail(cur_name_id);
						return tag_rdr.Pos();
					}
					break;
				case Gfh_tag_.Id__img: // rewrite src for XOWA; especially necessary for relative protocol; EX: "//upload.wikimedia.org"; note do not use <super> tag b/c of issues with anchors like "href=#section"
					return Parse_img_src(bfr, img_src_parser, cur);
				default:
					break;
			}
		}
		bfr.Add_mid(src, cur.Src_bgn(), cur.Src_end());
		return cur.Src_end();
	}
	private int Parse_img_src(Bry_bfr bfr, Xosync_img_src_parser img_src_parser, Gfh_tag img_tag) {
		// get @src and parse it
		Gfh_atr src_atr = img_tag.Atrs__get_by_or_empty(Gfh_atr_.Bry__src);
		img_src_parser.Parse(src_atr.Val());

		// if error, write comment; EX: <!--error--><img ...>
		String err_msg = img_src_parser.Err_msg();
		if (err_msg != null) {
			bfr.Add(Gfh_tag_.Comm_bgn);
			bfr.Add_str_u8(img_src_parser.Err_msg());
			bfr.Add(Gfh_tag_.Comm_end);
		}

		// get img_src; use img_src_parser if no error, else use original value
		byte[] img_src_val = err_msg == null ? img_src_parser.To_bry() : src_atr.Val();

		// write html
		Write_img_tag(bfr, img_tag, -1, img_src_val);

		return img_tag.Src_end();
	}
	public static void Write_img_tag(Bry_bfr bfr, Gfh_tag img_tag, int uid, byte[] img_src_val) {
		// rewrite <img> tag with custom img_src_val
		int atrs_len = img_tag.Atrs__len();
		bfr.Add(Byte_ascii.Angle_bgn_bry);
		bfr.Add(Gfh_tag_.Bry__img);
		if (uid != -1) {
			Gfh_atr_.Add(bfr, Gfh_atr_.Bry__id, Bry_.new_a7("xoimg_" + Int_.To_str(uid)));
		}
		for (int i = 0; i < atrs_len; ++i) {
			Gfh_atr atr = img_tag.Atrs__get_at(i);
			// if atr is src use img_src_val; EX: ' src="//upload.wikimedia.org/..."' -> ' src="xowa:/file/..."
			Gfh_atr_.Add(bfr, atr.Key(), Bry_.Eq(atr.Key(), Gfh_atr_.Bry__src) ? img_src_val : atr.Val());
		}
		bfr.Add(Byte_ascii.Angle_end_bry);
	}
	private static final    byte[] Bry__span__edit_section = Bry_.new_a7("mw-editsection");
}
