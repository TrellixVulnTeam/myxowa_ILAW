/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2022 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa;
import gplx.core.btries.Btrie_rv;
import gplx.Bry_;
public class Db_btrie_http implements Db_btrie {
	private final Object[] objs;
	private int found;
	private int offset;
	public Db_btrie_http(Object[] objs) {this.objs = objs; }
	public static byte[] Hash() { return Bry_.new_a7("156b1a707b252b633b2d05c023b182f5"); }
	private void Match_with_b(byte b, byte[] src, int ofs, int src_len) {
		found = -1;
		offset = -1;
		int c = b;

		switch (b) {
			case 97:
			case 65:
				if (ofs+5 < src_len && (src[ofs+1] | 32) == 'c' && (src[ofs+2] | 32) == 'c' && (src[ofs+3] | 32) == 'e' && (src[ofs+4] | 32) == 'p' && (src[ofs+5] | 32) == 't') {
					if (ofs+6 < src_len) switch (src[ofs+6]) {
						case 45:
							if (ofs+7 < src_len) switch ((src[ofs+7])) {
								case 99:
								case 67:
									if (ofs+14 < src_len && (src[ofs+8] | 32) == 'h' && (src[ofs+9] | 32) == 'a' && (src[ofs+10] | 32) == 'r' && (src[ofs+11] | 32) == 's' && (src[ofs+12] | 32) == 'e' && (src[ofs+13] | 32) == 't' && src[ofs+14] == ':') {
										found = ofs + 15;
										offset = 7; // ('Accept-Charset:', 7)
									}
									break;
								case 101:
								case 69:
									if (ofs+15 < src_len && (src[ofs+8] | 32) == 'n' && (src[ofs+9] | 32) == 'c' && (src[ofs+10] | 32) == 'o' && (src[ofs+11] | 32) == 'd' && (src[ofs+12] | 32) == 'i' && (src[ofs+13] | 32) == 'n' && (src[ofs+14] | 32) == 'g' && src[ofs+15] == ':') {
										found = ofs + 16;
										offset = 6; // ('Accept-Encoding:', 6)
									}
									break;
								case 108:
								case 76:
									if (ofs+15 < src_len && (src[ofs+8] | 32) == 'a' && (src[ofs+9] | 32) == 'n' && (src[ofs+10] | 32) == 'g' && (src[ofs+11] | 32) == 'u' && (src[ofs+12] | 32) == 'a' && (src[ofs+13] | 32) == 'g' && (src[ofs+14] | 32) == 'e' && src[ofs+15] == ':') {
										found = ofs + 16;
										offset = 5; // ('Accept-Language:', 5)
									}
									break;
							}
							break;
						case 58:
							found = ofs + 7;
							offset = 4; // ('Accept:', 4)
							break;
					}
				}
				break;
			case 99:
			case 67:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 97:
					case 65:
						if (ofs+13 < src_len && (src[ofs+2] | 32) == 'c' && (src[ofs+3] | 32) == 'h' && (src[ofs+4] | 32) == 'e' && src[ofs+5] == '-' && (src[ofs+6] | 32) == 'c' && (src[ofs+7] | 32) == 'o' && (src[ofs+8] | 32) == 'n' && (src[ofs+9] | 32) == 't' && (src[ofs+10] | 32) == 'r' && (src[ofs+11] | 32) == 'o' && (src[ofs+12] | 32) == 'l' && src[ofs+13] == ':') {
							found = ofs + 14;
							offset = 16; // ('Cache-Control:', 16)
						}
						break;
					case 111:
					case 79:
						if (ofs+2 < src_len) switch ((src[ofs+2])) {
							case 110:
							case 78:
								if (ofs+3 < src_len) switch ((src[ofs+3])) {
									case 110:
									case 78:
										if (ofs+10 < src_len && (src[ofs+4] | 32) == 'e' && (src[ofs+5] | 32) == 'c' && (src[ofs+6] | 32) == 't' && (src[ofs+7] | 32) == 'i' && (src[ofs+8] | 32) == 'o' && (src[ofs+9] | 32) == 'n' && src[ofs+10] == ':') {
											found = ofs + 11;
											offset = 14; // ('Connection:', 14)
										}
										break;
									case 116:
									case 84:
										if (ofs+7 < src_len && (src[ofs+4] | 32) == 'e' && (src[ofs+5] | 32) == 'n' && (src[ofs+6] | 32) == 't' && src[ofs+7] == '-') {
											if (ofs+8 < src_len) switch ((src[ofs+8])) {
												case 108:
												case 76:
													if (ofs+14 < src_len && (src[ofs+9] | 32) == 'e' && (src[ofs+10] | 32) == 'n' && (src[ofs+11] | 32) == 'g' && (src[ofs+12] | 32) == 't' && (src[ofs+13] | 32) == 'h' && src[ofs+14] == ':') {
														found = ofs + 15;
														offset = 12; // ('Content-length:', 12)
													}
													break;
												case 116:
												case 84:
													if (ofs+12 < src_len && (src[ofs+9] | 32) == 'y' && (src[ofs+10] | 32) == 'p' && (src[ofs+11] | 32) == 'e' && src[ofs+12] == ':') {
														found = ofs + 13;
														offset = 13; // ('Content-Type:', 13)
													}
													break;
											}
										}
										break;
								}
								break;
							case 111:
							case 79:
								if (ofs+6 < src_len && (src[ofs+3] | 32) == 'k' && (src[ofs+4] | 32) == 'i' && (src[ofs+5] | 32) == 'e' && src[ofs+6] == ':') {
									found = ofs + 7;
									offset = 10; // ('Cookie:', 10)
								}
								break;
						}
						break;
				}
				break;
			case 100:
			case 68:
				if (ofs+3 < src_len && (src[ofs+1] | 32) == 'n' && (src[ofs+2] | 32) == 't' && src[ofs+3] == ':') {
					found = ofs + 4;
					offset = 8; // ('DNT:', 8)
				}
				break;
			case 103:
			case 71:
				if (ofs+2 < src_len && (src[ofs+1] | 32) == 'e' && (src[ofs+2] | 32) == 't') {
					found = ofs + 3;
					offset = 0; // ('GET', 0)
				}
				break;
			case 104:
			case 72:
				if (ofs+4 < src_len && (src[ofs+1] | 32) == 'o' && (src[ofs+2] | 32) == 's' && (src[ofs+3] | 32) == 't' && src[ofs+4] == ':') {
					found = ofs + 5;
					offset = 2; // ('Host:', 2)
				}
				break;
			case 111:
			case 79:
				if (ofs+6 < src_len && (src[ofs+1] | 32) == 'r' && (src[ofs+2] | 32) == 'i' && (src[ofs+3] | 32) == 'g' && (src[ofs+4] | 32) == 'i' && (src[ofs+5] | 32) == 'n' && src[ofs+6] == ':') {
					found = ofs + 7;
					offset = 17; // ('Origin:', 17)
				}
				break;
			case 112:
			case 80:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 111:
					case 79:
						if (ofs+3 < src_len && (src[ofs+2] | 32) == 's' && (src[ofs+3] | 32) == 't') {
							found = ofs + 4;
							offset = 1; // ('POST', 1)
						}
						break;
					case 114:
					case 82:
						if (ofs+6 < src_len && (src[ofs+2] | 32) == 'a' && (src[ofs+3] | 32) == 'g' && (src[ofs+4] | 32) == 'm' && (src[ofs+5] | 32) == 'a' && src[ofs+6] == ':') {
							found = ofs + 7;
							offset = 15; // ('Pragma:', 15)
						}
						break;
				}
				break;
			case 114:
			case 82:
				if (ofs+7 < src_len && (src[ofs+1] | 32) == 'e' && (src[ofs+2] | 32) == 'f' && (src[ofs+3] | 32) == 'e' && (src[ofs+4] | 32) == 'r' && (src[ofs+5] | 32) == 'e' && (src[ofs+6] | 32) == 'r' && src[ofs+7] == ':') {
					found = ofs + 8;
					offset = 11; // ('Referer:', 11)
				}
				break;
			case 115:
			case 83:
				if (ofs+3 < src_len && (src[ofs+1] | 32) == 'e' && (src[ofs+2] | 32) == 'c' && src[ofs+3] == '-') {
					if (ofs+4 < src_len) switch ((src[ofs+4])) {
						case 99:
						case 67:
							if (ofs+8 < src_len && (src[ofs+5] | 32) == 'h' && src[ofs+6] == '-' && (src[ofs+7] | 32) == 'u' && (src[ofs+8] | 32) == 'a') {
								if (ofs+9 < src_len) switch (src[ofs+9]) {
									case 45:
										if (ofs+10 < src_len) switch ((src[ofs+10])) {
											case 109:
											case 77:
												if (ofs+16 < src_len && (src[ofs+11] | 32) == 'o' && (src[ofs+12] | 32) == 'b' && (src[ofs+13] | 32) == 'i' && (src[ofs+14] | 32) == 'l' && (src[ofs+15] | 32) == 'e' && src[ofs+16] == ':') {
													found = ofs + 17;
													offset = 26; // ('Sec-Ch-Ua-Mobile:', 26)
												}
												break;
											case 112:
											case 80:
												if (ofs+18 < src_len && (src[ofs+11] | 32) == 'l' && (src[ofs+12] | 32) == 'a' && (src[ofs+13] | 32) == 't' && (src[ofs+14] | 32) == 'f' && (src[ofs+15] | 32) == 'o' && (src[ofs+16] | 32) == 'r' && (src[ofs+17] | 32) == 'm' && src[ofs+18] == ':') {
													found = ofs + 19;
													offset = 28; // ('Sec-Ch-Ua-platform:', 28)
												}
												break;
										}
										break;
									case 58:
										found = ofs + 10;
										offset = 25; // ('Sec-Ch-Ua:', 25)
										break;
								}
							}
							break;
						case 102:
						case 70:
							if (ofs+9 < src_len && (src[ofs+5] | 32) == 'e' && (src[ofs+6] | 32) == 't' && (src[ofs+7] | 32) == 'c' && (src[ofs+8] | 32) == 'h' && src[ofs+9] == '-') {
								if (ofs+10 < src_len) switch ((src[ofs+10])) {
									case 100:
									case 68:
										if (ofs+14 < src_len && (src[ofs+11] | 32) == 'e' && (src[ofs+12] | 32) == 's' && (src[ofs+13] | 32) == 't' && src[ofs+14] == ':') {
											found = ofs + 15;
											offset = 23; // ('Sec-Fetch-Dest:', 23)
										}
										break;
									case 109:
									case 77:
										if (ofs+14 < src_len && (src[ofs+11] | 32) == 'o' && (src[ofs+12] | 32) == 'd' && (src[ofs+13] | 32) == 'e' && src[ofs+14] == ':') {
											found = ofs + 15;
											offset = 21; // ('Sec-Fetch-Mode:', 21)
										}
										break;
									case 115:
									case 83:
										if (ofs+14 < src_len && (src[ofs+11] | 32) == 'i' && (src[ofs+12] | 32) == 't' && (src[ofs+13] | 32) == 'e' && src[ofs+14] == ':') {
											found = ofs + 15;
											offset = 22; // ('Sec-Fetch-Site:', 22)
										}
										break;
									case 117:
									case 85:
										if (ofs+14 < src_len && (src[ofs+11] | 32) == 's' && (src[ofs+12] | 32) == 'e' && (src[ofs+13] | 32) == 'r' && src[ofs+14] == ':') {
											found = ofs + 15;
											offset = 24; // ('Sec-Fetch-User:', 24)
										}
										break;
								}
							}
							break;
						case 103:
						case 71:
							if (ofs+7 < src_len && (src[ofs+5] | 32) == 'p' && (src[ofs+6] | 32) == 'c' && src[ofs+7] == ':') {
								found = ofs + 8;
								offset = 27; // ('Sec-GPC:', 27)
							}
							break;
					}
				}
				break;
			case 117:
			case 85:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 112:
					case 80:
						if (ofs+25 < src_len && (src[ofs+2] | 32) == 'g' && (src[ofs+3] | 32) == 'r' && (src[ofs+4] | 32) == 'a' && (src[ofs+5] | 32) == 'd' && (src[ofs+6] | 32) == 'e' && src[ofs+7] == '-' && (src[ofs+8] | 32) == 'i' && (src[ofs+9] | 32) == 'n' && (src[ofs+10] | 32) == 's' && (src[ofs+11] | 32) == 'e' && (src[ofs+12] | 32) == 'c' && (src[ofs+13] | 32) == 'u' && (src[ofs+14] | 32) == 'r' && (src[ofs+15] | 32) == 'e' && src[ofs+16] == '-' && (src[ofs+17] | 32) == 'r' && (src[ofs+18] | 32) == 'e' && (src[ofs+19] | 32) == 'q' && (src[ofs+20] | 32) == 'u' && (src[ofs+21] | 32) == 'e' && (src[ofs+22] | 32) == 's' && (src[ofs+23] | 32) == 't' && (src[ofs+24] | 32) == 's' && src[ofs+25] == ':') {
							found = ofs + 26;
							offset = 18; // ('Upgrade-Insecure-Requests:', 18)
						}
						break;
					case 115:
					case 83:
						if (ofs+10 < src_len && (src[ofs+2] | 32) == 'e' && (src[ofs+3] | 32) == 'r' && src[ofs+4] == '-' && (src[ofs+5] | 32) == 'a' && (src[ofs+6] | 32) == 'g' && (src[ofs+7] | 32) == 'e' && (src[ofs+8] | 32) == 'n' && (src[ofs+9] | 32) == 't' && src[ofs+10] == ':') {
							found = ofs + 11;
							offset = 3; // ('User-Agent:', 3)
						}
						break;
				}
				break;
			case 120:
			case 88:
				if (ofs+1 < src_len && src[ofs+1] == '-') {
					if (ofs+2 < src_len) switch ((src[ofs+2])) {
						case 104:
						case 72:
							if (ofs+6 < src_len && (src[ofs+3] | 32) == 'o' && (src[ofs+4] | 32) == 's' && (src[ofs+5] | 32) == 't' && src[ofs+6] == ':') {
								found = ofs + 7;
								offset = 19; // ('X-Host:', 19)
							}
							break;
						case 114:
						case 82:
							if (ofs+3 < src_len && (src[ofs+3] | 32) == 'e') {
								if (ofs+4 < src_len) switch ((src[ofs+4])) {
									case 97:
									case 65:
										if (ofs+9 < src_len && (src[ofs+5] | 32) == 'l' && src[ofs+6] == '-' && (src[ofs+7] | 32) == 'i' && (src[ofs+8] | 32) == 'p' && src[ofs+9] == ':') {
											found = ofs + 10;
											offset = 20; // ('X-Real-IP:', 20)
										}
										break;
									case 113:
									case 81:
										if (ofs+16 < src_len && (src[ofs+5] | 32) == 'u' && (src[ofs+6] | 32) == 'e' && (src[ofs+7] | 32) == 's' && (src[ofs+8] | 32) == 't' && (src[ofs+9] | 32) == 'e' && (src[ofs+10] | 32) == 'd' && src[ofs+11] == '-' && (src[ofs+12] | 32) == 'w' && (src[ofs+13] | 32) == 'i' && (src[ofs+14] | 32) == 't' && (src[ofs+15] | 32) == 'h' && src[ofs+16] == ':') {
											found = ofs + 17;
											offset = 9; // ('X-Requested-With:', 9)
										}
										break;
								}
							}
							break;
					}
				}
				break;
		}
	}

	@Override public Object Match_expand(Btrie_rv rv, byte[] src, int ofs, int src_len) {
		// this check should have been made by parent call
		//if (ofs >= src_len) {
		//	rv.Init(ofs, null);
		//	return null;
		//}
		Match_with_b(src[ofs], src, ofs, src_len);
		if (found == -1) {
			rv.Init(ofs, null);
			return null;
		}
		else {
			Object rv_obj = objs[offset];
			rv.Init(found, rv_obj);
			return rv_obj;
		}
	}
	@Override public Object Match_bgn(byte[] src, int bgn_pos, int end_pos) {
		// this check should have been made by parent call
		//if (bgn_pos >= end_pos)
		//	return null;
		Match_with_b(src[bgn_pos], src, bgn_pos, end_pos);
		if (found == -1) {
			return null;
		}
		else {
			Object rv_obj = objs[offset];
			return rv_obj;
		}
	}
	@Override public Object Match_at_w_b0(Btrie_rv rv, byte b, byte[] src, int bgn_pos, int end_pos) {
		// this check should have been made by parent call
		//if (ofs >= src_len) {
		//	rv.Init(ofs, null);
		//	return null;
		//}
		Match_with_b(b, src, bgn_pos, end_pos);
		if (found == -1) {
			rv.Init(bgn_pos, null);
			return null;
		}
		else {
			Object rv_obj = objs[offset];
			rv.Init(found, rv_obj);
			return rv_obj;
		}
	}
}
