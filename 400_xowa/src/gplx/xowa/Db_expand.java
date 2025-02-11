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
import gplx.langs.htmls.*;
import gplx.xowa.parsers.Xop_ctx;
public class Db_expand {

	private static final Object lock = "a";
	private static byte[][] uniq = new byte[20][];
	private static int uniqcount;
	private static void substitute(Bry_bfr tmp, int uniqofs) {
            if (uniqofs < uniqcount)
		tmp.Add(uniq[uniqofs]);
	}
	private static void expand(Bry_bfr tmp, byte[] src, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			if (b == 0)
				substitute(tmp, src[++i]);
			else
				tmp.Add_byte(b);
		}
	}
	public static byte[] Extracheck(byte[] buf, String accessibility_label, Xowe_wiki wiki) {
//            System.out.println(String_.new_u8(buf));
		Bry_bfr tmp_ref = Bry_bfr_.New();
		tmp_ref.Add(buf);
		return Extracheck(tmp_ref, accessibility_label, wiki);
	}
	private static void replace_spaces(byte[] src, int bgn, int end) {
		while (bgn < end)
			if (src[bgn++] == ' ')
				src[bgn-1] = '_';
	}
	public static byte[] Extracheck(Bry_bfr bfr, String accessibility_label, Xowe_wiki wiki) {
            //System.out.println(String_.new_u8(bfr.Bfr(), 0, bfr.Len()));
            //if (bfr.Bfr()[0] == '<' &&bfr.Bfr()[1] == 'l' &&bfr.Bfr()[2] == 'i') {
            //    int a=1;
            //}
		synchronized (lock){
			// big hack for lnki [[.aa.|.bb.]] or [[.cc.]] AND <nowiki>x</nowiki> - arrrgh
			// scan for <nowik> first
			// reuse the bfr
			byte b;
			boolean extreme = false;
			boolean inbold = false;
			boolean initalic = false;
			int nowiki_bgn, nowiki_end, lnki_bgn, lnki_end, bar_pos;
			byte[] src = bfr.Bfr();
			int len = bfr.Len();
			int pos = 0;
			int ofs = 0;
			uniqcount = 0;
	
			while (pos < len) {
				b = src[pos++];
				if (b == '<') {
					if (pos + 7 < len && src[pos] == 'n' && src[pos+1] == 'o' && src[pos+2] == 'w' && src[pos+3] == 'i' && src[pos+4] == 'k' && src[pos+5] == 'i' && src[pos+6] == '>') {
						// <nowiki> found
						pos += 7;
						nowiki_bgn = pos;
						nowiki_end = -1;
						while (pos < len) {
							b = src[pos++];
							if (b == '<') {
								if (pos + 8 < len && src[pos] == '/' && src[pos+1] == 'n' && src[pos+2] == 'o' && src[pos+3] == 'w' && src[pos+4] == 'i' && src[pos+5] == 'k' && src[pos+6] == 'i' && src[pos+7] == '>') {
									// </nowiki> found
									nowiki_end = pos - 1;
									pos += 8;
									break;
								}
							}
						}
						if (nowiki_end > 0) {
							if (ofs == 0)
								ofs = nowiki_bgn - 8;
							int size = nowiki_end - nowiki_bgn;
							byte[] ustr = new byte[size];
							for (int i = 0; i < size; i++) {
								ustr[i] = src[nowiki_bgn + i];
							}
							uniq[uniqcount] = ustr;
							src[ofs++] = 0;
							src[ofs++] = (byte)uniqcount;
							uniqcount++;
						}
					}
					else if (ofs > 0) {
						src[ofs++] = b;
					}
				}
				else if (ofs > 0) {
					src[ofs++] = b;
				}
			}
			if (ofs > 0)
				//bfr.Len_(ofs);
				bfr.Del_by(len - ofs);

			// now go thru looking for [[ .. ]]
			len = bfr.Len();
			pos = 0;
			Bry_bfr tmp_bfr = Bry_bfr_.New();
			while (pos < len && !extreme) {
				b = src[pos++];
				switch (b) {
				case '[':
					if (pos < len && src[pos] == '[') {
						// found '[['
                                                int start = pos - 1;
                                                // consume all others
						pos++;
                                                while (pos < len) {
                                                    if (src[pos] == '[')
                                                        pos++;
                                                    else
                                                        break;
                                                }
						lnki_bgn = pos;
						lnki_end = -1;
						bar_pos = -1;
						int pipe_count = 0;
						while (pos < len) {
							b = src[pos++];
							if (b == ']') {
								if (pos < len && src[pos] == ']') {
									// ']]' found
									lnki_end = pos - 1;
									pos++;
									break;
								}
							}
							else if (b == '|') {
								bar_pos = pos - 1;
								pipe_count++;
							}
						}
						if (pipe_count > 1 && src[lnki_bgn] != '#') {
							extreme = true;
							break;
						}
						if (lnki_end > 0) {
							byte[] ttl_bry;
							if (bar_pos > 0)
								ttl_bry = Bry_.Mid(src, lnki_bgn, bar_pos);
							else
								ttl_bry = Bry_.Mid(src, lnki_bgn, lnki_end);
							if (src[lnki_bgn] != '#') {
								Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ttl_bry);
								if (ttl == null) {
									tmp_bfr.Add_mid(src, start, lnki_end + 2);
									break;
								}
								ttl_bry = ttl.Full_url();
							}
							tmp_bfr.Add_str_a7("<a href=\"");
							if (src[lnki_bgn] != '#') {
								tmp_bfr.Add_str_a7("/wiki/");
							}
							tmp_bfr.Add(ttl_bry);
							tmp_bfr.Add_str_a7("\"");
							if (accessibility_label.length() == 0) {
								tmp_bfr.Add_str_a7(" title=\"");
								if (bar_pos > 0)
									tmp_bfr.Add_mid(src, bar_pos + 1, lnki_end);
								else
									tmp_bfr.Add_mid(src, lnki_bgn, lnki_end);
								tmp_bfr.Add_str_a7("\"");
							}
							else
								tmp_bfr.Add_str_a7(accessibility_label);
							tmp_bfr.Add_byte(Byte_ascii.Angle_end);
							if (bar_pos > 0)
								expand(tmp_bfr, src, bar_pos+1, lnki_end);
							else
								expand(tmp_bfr, src, lnki_bgn, lnki_end);
							tmp_bfr.Add_str_a7("</a>");
						}
					}
					else
						tmp_bfr.Add_byte(b);
					break;
				case '\'': // possible emphasis(''') or italic ('')
					int apos_count = 1;
					while (pos < len) {
						if (src[pos] == '\'') {
							pos++;
							apos_count++;
						}
						else
							break;
					}
					if (apos_count > 1) {
						if (apos_count == 2) {
							if (initalic) {
								tmp_bfr.Add(Gfh_tag_.I_rhs);
								initalic = false;
							}
							else {
								tmp_bfr.Add(Gfh_tag_.I_lhs);
								initalic = true;
							}
						}
						else if (apos_count == 3) {
							if (inbold) {
								tmp_bfr.Add(Gfh_tag_.B_rhs);
								inbold = false;
							}
							else {
								tmp_bfr.Add(Gfh_tag_.B_lhs);
								inbold = true;
							}
						}
						// could be 5!!
					}
					else
						tmp_bfr.Add_byte(b);
					break;
				case 0:
					substitute(tmp_bfr, src[pos++]);
					break;
				case '{':
					if (src[pos] == '|')
						extreme = true;
				default:
					tmp_bfr.Add_byte(b);
				}
			}
			for (int i = 0; i < uniqcount; i++) {
				uniq[i] = null;
			}
			if (extreme) {
				Xop_ctx ctx = Xop_ctx.New__sub__reuse_page(wiki.Parser_mgr().Ctx());
            System.out.println("b " + String_.new_u8(bfr.Bfr(), 0, bfr.Len()));
				byte[] txt = wiki.Parser_mgr().Main().Parse_wtxt_to_html(ctx, bfr.To_bry());
            System.out.println("a " + String_.new_u8(txt));
				return txt;
			}
			bfr.Clear();
			return tmp_bfr.To_bry_and_clear();
		}
	}
	public static byte[] Wtxt_expand(Bry_bfr bfr, Xowe_wiki wiki) {
		if (Bry_find_.Find_fwd(bfr.Bfr(), Byte_ascii.Colon) > 0) {
                    int a=1;
                }
                        Xop_ctx ctx = Xop_ctx.New__sub__reuse_page(wiki.Parser_mgr().Ctx());
			//byte[] txt = wiki.Parser_mgr().Main().Parse_text_to_html(ctx, bfr.To_bry());
			byte[] txt = wiki.Parser_mgr().Main().Parse_wtxt_to_html(ctx, bfr.To_bry());
            System.out.println("z " + String_.new_u8(txt));
			return txt;
        }
}
