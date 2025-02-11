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
package gplx.xowa.parsers.uniqs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*;
public class Xop_uniq_mgr {	// REF.MW:/parser/StripState.php
	private final	Btrie_slim_mgr general_trie = Btrie_slim_mgr.cs(); private final	Btrie_rv trv = new Btrie_rv();
	//private final	Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(32);
	private boolean partial = false;
	private boolean onlynowiki = false;
	private int nxt_idx = -1;
	public void Clear() {
		nxt_idx = -1;
		general_trie.Clear();
	}
	public byte[] Get(byte[] key) {
		Xop_uniq_itm itm = (Xop_uniq_itm)general_trie.Match_exact(key, 0, key.length);
		if (itm == null)
			return Bry_.Empty;
		return itm.Val();
	}
	public byte[] Add(boolean expand_after_template_parsing, byte[] type, byte[] val) {// "<b>" -> "\u007fUNIQ-item-1-QINU\u007f"
		int idx = ++nxt_idx;
		Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(32);
		byte[] key = tmp_bfr	
			.Add(Bry__uniq__bgn_w_dash)		  // "\u007f'\"`UNIQ-"
			.Add(type).Add_byte(Byte_ascii.Dash) // "ref-"
			.Add_int_fixed(idx, 8)
                        //.Add_int_variable(idx)			   // "1"
			.Add(Bry__uniq__add__end)			// "-QINU`\"'\u007f"
			.To_bry_and_clear();
//		System.out.println(String_.new_u8(key)+":"+String_.new_u8(val));
		Xop_uniq_itm itm = new Xop_uniq_itm(expand_after_template_parsing, type.length, idx, key, val);
		general_trie.Add_obj(key, itm);
		return key;
	}
	public void Parse(Bry_bfr bfr) {
		if (general_trie.Count() == 0) return;
		//byte[] rv = Parse_recurse(Bool_.Y, tmp_bfr, bfr.To_bry_and_clear());
                Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(32);
		byte[] rv = Parse_recurse(Bool_.N, tmp_bfr, bfr.To_bry_and_clear());
		bfr.Add(rv);
	}
	public byte[] Parse(boolean template_parsing, byte[] src) {
		if (nxt_idx < 0) // nothing to do
			return src;
                Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(32);
		return Parse_recurse(template_parsing, tmp_bfr, src);
	}
	public byte[] Partial_Parse(byte[] src, boolean onlynowiki) {
		if (nxt_idx < 0) // nothing to do
			return src;
                this.partial = true;
                this.onlynowiki = onlynowiki;
                Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(32);
		byte[] res = Parse_recurse(false, tmp_bfr, src);
                this.onlynowiki = false;
                this.partial = false;
                return res;
	}
	public byte[] Parse(byte[] src) {
		if (general_trie.Count() == 0) // nothing to do
			return src;
                Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(32);
		return Parse_recurse(Bool_.Y, tmp_bfr, src);
	}
	private byte[] Parse_recurse(boolean template_parsing, Bry_bfr bfr, byte[] src) {
		int src_len = src.length;
		int loop_end = src_len - MINKEYSIZE;
		// the keys are a minimum of MINKEYSIZE chars
		int pos = 0;
		int prv_bgn = 0;

		while (pos < loop_end) {
			byte b = src[pos];
			if (b != 0x7f) {
				pos++;
				continue;
			}
			Object o = general_trie.Match_at_w_b0(trv, b, src, pos, src_len);

			// match not found for "\x7fUNIQ"; move on to next
			if (o == null)
				pos++;
			// match found
			else {
				Xop_uniq_itm itm = (Xop_uniq_itm)o;
				int itm_end = trv.Pos();	// NOTE: must capture pos since trv is reused in the recursive call below
				// skip if template_parsing
				if (template_parsing
					&& !itm.Expand_after_template_parsing()) {
					pos = itm_end;
					continue;
				}
                                if (partial) {
                                    byte[] type = itm.Type();
                                    boolean isnowiki = false;
                                    if (type[0] == 'n' && type[1] == 'o' /*etc*/)
                                        isnowiki = true;
                                    if (onlynowiki) {
                                        if (!isnowiki) {
                                            pos++;
                                            continue; // only process nowiki
                                        }
                                    }
                                    else {
                                        if (isnowiki) {
                                            pos++;
                                            continue; // everything else except nowiki
                                        }
                                    }
                                }

				// add everything from prv_bgn up to UNIQ
				bfr.Add_mid(src, prv_bgn, pos);

				// expand UNIQ (can be recursive but only if a UNIQ could fit)
				byte[] val = itm.Val();
				if (val.length > MINKEYSIZE)
					val = Parse_recurse(template_parsing, Bry_bfr_.New(), val);
				bfr.Add(val);
				pos = prv_bgn = itm_end;
			}
		}
		if (prv_bgn != 0) {
			bfr.Add_mid(src, prv_bgn, src_len);
			return bfr.To_bry_and_clear();
		}
		return src;
	}
	public byte[] Uniq_bry_new() {
		return Bry_.Add
		( Bry__uniq__bgn			// "\x7fUNIQ" where "\x7f" is (byte)127
		, Random_bry_new(16));		// random hexdecimal String
	}
	public void Random_int_ary_(int... v) {random_int_ary = v;} private int[] random_int_ary;	// TEST:
	public byte[] Random_bry_new(int len) {
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		RandomAdp random_gen = RandomAdp_.new_();
		for (int i = 0; i < len; i += 7) {
			int rand = random_int_ary == null ? random_gen.Next(Int_.Max_value) : random_int_ary[i / 7];
			String rand_str = Int_.To_str_hex(Bool_.N, Bool_.Y, rand & 0xfffffff);	// limits value to 268435455
			tmp_bfr.Add_str_a7(rand_str);
		}
		byte[] rv = tmp_bfr.To_bry(0, len);
		tmp_bfr.Clear();
		return rv;
	}

	private static final int MINKEYSIZE = 18;
	public static final	byte[] 
	  Bry__uniq__bgn		= Bry_.new_a7("\u007f'\"`UNIQ-")
	, Bry__uniq__bgn_w_dash	= Bry_.Add(Bry__uniq__bgn, Byte_ascii.Dash_bry)
	, Bry__uniq__add__end	= Bry_.new_a7("-QINU`\"'\u007f")
	;
}
