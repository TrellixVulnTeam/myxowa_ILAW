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
public class Db_btrie_xnde_tag_wtxt_ewv implements Db_btrie {
	private final Object[] objs;
	private int found;
	private int offset;
	public Db_btrie_xnde_tag_wtxt_ewv(Object[] objs) {this.objs = objs; }
	public static byte[] Hash() { return Bry_.new_a7("3b3fd52a888ecd54f5217a5c2ab0708a"); }
	private void Match_with_b(byte b, byte[] src, int ofs, int src_len) {
		found = -1;
		offset = -1;
		int c = b;

		if ((b & 0xE0) == 0xC0) {
			if (ofs+1 >= src_len)
				return;
			c = (b & 0x1f) << 6 | ( src[ofs+1] & 0x3f);
		}
		switch (c) {
			case 97:
			case 65:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 98:
					case 66:
						if (ofs+2 < src_len) switch ((src[ofs+2])) {
							case 98:
							case 66:
								if (ofs+3 < src_len && (src[ofs+3] | 32) == 'r') {
									found = ofs + 4;
									offset = 9; // ('abbr', 9)
								}
								break;
							case 115:
							case 83:
								if (ofs+8 < src_len && (src[ofs+3] | 32) == 'c' && (src[ofs+4] | 32) == 'h' && (src[ofs+5] | 32) == 'n' && (src[ofs+6] | 32) == 'i' && (src[ofs+7] | 32) == 't' && (src[ofs+8] | 32) == 't') {
									found = ofs + 9;
									offset = 76; // ('Abschnitt', 76)
								}
								break;
						}
						break;
					case 117:
					case 85:
						if (ofs+4 < src_len && (src[ofs+2] | 32) == 'd' && (src[ofs+3] | 32) == 'i' && (src[ofs+4] | 32) == 'o') {
							found = ofs + 5;
							offset = 127; // ('audio', 127)
						}
						break;
				}
				if (found == -1) {
					found = ofs + 1;
					offset = 52; // ('a', 52)
				}
				break;
			case 98:
			case 66:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 100:
					case 68:
						if (ofs+2 < src_len) switch ((src[ofs+2])) {
							case 105:
							case 73:
								found = ofs + 3;
								offset = 98; // ('bdi', 98)
								break;
							case 111:
							case 79:
								found = ofs + 3;
								offset = 102; // ('bdo', 102)
								break;
						}
						break;
					case 105:
					case 73:
						if (ofs+2 < src_len && (src[ofs+2] | 32) == 'g') {
							found = ofs + 3;
							offset = 15; // ('big', 15)
						}
						break;
					case 108:
					case 76:
						if (ofs+9 < src_len && (src[ofs+2] | 32) == 'o' && (src[ofs+3] | 32) == 'c' && (src[ofs+4] | 32) == 'k' && (src[ofs+5] | 32) == 'q' && (src[ofs+6] | 32) == 'u' && (src[ofs+7] | 32) == 'o' && (src[ofs+8] | 32) == 't' && (src[ofs+9] | 32) == 'e') {
							found = ofs + 10;
							offset = 21; // ('blockquote', 21)
						}
						break;
					case 114:
					case 82:
						found = ofs + 2;
						offset = 29; // ('br', 29)
						break;
					case 117:
					case 85:
						if (ofs+2 < src_len) switch ((src[ofs+2])) {
							case 116:
							case 84:
								if (ofs+5 < src_len && (src[ofs+3] | 32) == 't' && (src[ofs+4] | 32) == 'o' && (src[ofs+5] | 32) == 'n') {
									found = ofs + 6;
									offset = 88; // ('button', 88)
								}
								break;
							case 121:
							case 89:
								found = ofs + 3;
								offset = 103; // ('buy', 103)
								break;
						}
						break;
				}
				if (found == -1) {
					found = ofs + 1;
					offset = 0; // ('b', 0)
				}
				break;
			case 99:
			case 67:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 97:
					case 65:
						if (ofs+2 < src_len) switch ((src[ofs+2])) {
							case 112:
							case 80:
								if (ofs+6 < src_len && (src[ofs+3] | 32) == 't' && (src[ofs+4] | 32) == 'i' && (src[ofs+5] | 32) == 'o' && (src[ofs+6] | 32) == 'n') {
									found = ofs + 7;
									offset = 49; // ('caption', 49)
								}
								break;
							case 116:
							case 84:
								if (ofs+7 < src_len && (src[ofs+3] | 32) == 'e' && (src[ofs+4] | 32) == 'g' && (src[ofs+5] | 32) == 'o' && (src[ofs+6] | 32) == 'r' && (src[ofs+7] | 32) == 'y') {
									if (ofs+8 < src_len) switch ((src[ofs+8])) {
										case 108:
										case 76:
											if (ofs+11 < src_len && (src[ofs+9] | 32) == 'i' && (src[ofs+10] | 32) == 's' && (src[ofs+11] | 32) == 't') {
												found = ofs + 12;
												offset = 81; // ('categoryList', 81)
											}
											break;
										case 116:
										case 84:
											if (ofs+11 < src_len && (src[ofs+9] | 32) == 'r' && (src[ofs+10] | 32) == 'e' && (src[ofs+11] | 32) == 'e') {
												found = ofs + 12;
												offset = 82; // ('categoryTree', 82)
											}
											break;
									}
								}
								break;
						}
						break;
					case 101:
					case 69:
						if (ofs+5 < src_len && (src[ofs+2] | 32) == 'n' && (src[ofs+3] | 32) == 't' && (src[ofs+4] | 32) == 'e' && (src[ofs+5] | 32) == 'r') {
							found = ofs + 6;
							offset = 24; // ('center', 24)
						}
						break;
					case 104:
					case 72:
						if (ofs+2 < src_len) switch ((src[ofs+2])) {
							case 101:
							case 69:
								if (ofs+3 < src_len && (src[ofs+3] | 32) == 'm') {
									found = ofs + 4;
									offset = 134; // ('chem', 134)
								}
								break;
							case 111:
							case 79:
								if (ofs+5 < src_len && (src[ofs+3] | 32) == 'o' && (src[ofs+4] | 32) == 's' && (src[ofs+5] | 32) == 'e') {
									found = ofs + 6;
									offset = 118; // ('choose', 118)
								}
								break;
						}
						break;
					case 105:
					case 73:
						if (ofs+3 < src_len && (src[ofs+2] | 32) == 't' && (src[ofs+3] | 32) == 'e') {
							found = ofs + 4;
							offset = 4; // ('cite', 4)
						}
						break;
					case 111:
					case 79:
						if (ofs+2 < src_len) switch ((src[ofs+2])) {
							case 100:
							case 68:
								if (ofs+3 < src_len && (src[ofs+3] | 32) == 'e') {
									found = ofs + 4;
									offset = 17; // ('code', 17)
								}
								break;
							case 108:
							case 76:
								if (ofs+7 < src_len && (src[ofs+3] | 32) == 'g' && (src[ofs+4] | 32) == 'r' && (src[ofs+5] | 32) == 'o' && (src[ofs+6] | 32) == 'u' && (src[ofs+7] | 32) == 'p') {
									found = ofs + 8;
									offset = 50; // ('colgroup', 50)
								}
								if (found == -1) {
									found = ofs + 3;
									offset = 51; // ('col', 51)
								}
								break;
						}
						break;
				}
				break;
			case 100:
			case 68:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 97:
					case 65:
						if (ofs+3 < src_len && (src[ofs+2] | 32) == 't' && (src[ofs+3] | 32) == 'a') {
							found = ofs + 4;
							offset = 99; // ('data', 99)
						}
						break;
					case 100:
					case 68:
						found = ofs + 2;
						offset = 38; // ('dd', 38)
						break;
					case 101:
					case 69:
						if (ofs+2 < src_len && (src[ofs+2] | 32) == 'l') {
							found = ofs + 3;
							offset = 11; // ('del', 11)
						}
						break;
					case 102:
					case 70:
						if (ofs+2 < src_len && (src[ofs+2] | 32) == 'n') {
							found = ofs + 3;
							offset = 5; // ('dfn', 5)
						}
						break;
					case 105:
					case 73:
						if (ofs+2 < src_len && (src[ofs+2] | 32) == 'v') {
							found = ofs + 3;
							offset = 27; // ('div', 27)
						}
						break;
					case 108:
					case 76:
						found = ofs + 2;
						offset = 41; // ('dl', 41)
						break;
					case 111:
					case 79:
						found = ofs + 2;
						offset = 104; // ('do', 104)
						break;
					case 114:
					case 82:
						if (ofs+4 < src_len && (src[ofs+2] | 32) == 'i' && (src[ofs+3] | 32) == 'n' && (src[ofs+4] | 32) == 'k') {
							found = ofs + 5;
							offset = 105; // ('drink', 105)
						}
						break;
					case 116:
					case 84:
						found = ofs + 2;
						offset = 37; // ('dt', 37)
						break;
					case 121:
					case 89:
						if (ofs+14 < src_len && (src[ofs+2] | 32) == 'n' && (src[ofs+3] | 32) == 'a' && (src[ofs+4] | 32) == 'm' && (src[ofs+5] | 32) == 'i' && (src[ofs+6] | 32) == 'c' && (src[ofs+7] | 32) == 'p' && (src[ofs+8] | 32) == 'a' && (src[ofs+9] | 32) == 'g' && (src[ofs+10] | 32) == 'e' && (src[ofs+11] | 32) == 'l' && (src[ofs+12] | 32) == 'i' && (src[ofs+13] | 32) == 's' && (src[ofs+14] | 32) == 't') {
							found = ofs + 15;
							offset = 83; // ('dynamicPageList', 83)
						}
						break;
				}
				break;
			case 101:
			case 69:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 97:
					case 65:
						if (ofs+2 < src_len && (src[ofs+2] | 32) == 't') {
							found = ofs + 3;
							offset = 106; // ('eat', 106)
						}
						break;
					case 109:
					case 77:
						found = ofs + 2;
						offset = 3; // ('em', 3)
						break;
				}
				break;
			case 102:
			case 70:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 105:
					case 73:
						if (ofs+2 < src_len && (src[ofs+2] | 32) == 'g') {
							if (ofs+3 < src_len) switch ((src[ofs+3])) {
								case 99:
								case 67:
									if (ofs+9 < src_len && (src[ofs+4] | 32) == 'a' && (src[ofs+5] | 32) == 'p' && (src[ofs+6] | 32) == 't' && (src[ofs+7] | 32) == 'i' && (src[ofs+8] | 32) == 'o' && (src[ofs+9] | 32) == 'n') {
										found = ofs + 10;
										offset = 133; // ('figcaption', 133)
									}
									break;
								case 117:
								case 85:
									if (ofs+5 < src_len && (src[ofs+4] | 32) == 'r' && (src[ofs+5] | 32) == 'e') {
										if (ofs+12 < src_len && src[ofs+6] == '-' && (src[ofs+7] | 32) == 'i' && (src[ofs+8] | 32) == 'n' && (src[ofs+9] | 32) == 'l' && (src[ofs+10] | 32) == 'i' && (src[ofs+11] | 32) == 'n' && (src[ofs+12] | 32) == 'e') {
											found = ofs + 13;
											offset = 132; // ('figure-inline', 132)
										}
										if (found == -1) {
											found = ofs + 6;
											offset = 131; // ('figure', 131)
										}
									}
									break;
							}
						}
						break;
					case 111:
					case 79:
						if (ofs+2 < src_len) switch ((src[ofs+2])) {
							case 110:
							case 78:
								if (ofs+3 < src_len && (src[ofs+3] | 32) == 't') {
									found = ofs + 4;
									offset = 23; // ('font', 23)
								}
								break;
							case 114:
							case 82:
								if (ofs+3 < src_len && (src[ofs+3] | 32) == 'm') {
									found = ofs + 4;
									offset = 94; // ('form', 94)
								}
								break;
						}
						break;
				}
				break;
			case 103:
			case 71:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 97:
					case 65:
						if (ofs+6 < src_len && (src[ofs+2] | 32) == 'l' && (src[ofs+3] | 32) == 'l' && (src[ofs+4] | 32) == 'e' && (src[ofs+5] | 32) == 'r' && (src[ofs+6] | 32) == 'y') {
							found = ofs + 7;
							offset = 69; // ('gallery', 69)
						}
						break;
					case 114:
					case 82:
						if (ofs+4 < src_len && (src[ofs+2] | 32) == 'a' && (src[ofs+3] | 32) == 'p' && (src[ofs+4] | 32) == 'h') {
							found = ofs + 5;
							offset = 117; // ('graph', 117)
						}
						break;
				}
				break;
			case 104:
			case 72:
				if (ofs+1 < src_len) switch (src[ofs+1]) {
					case 49:
						found = ofs + 2;
						offset = 30; // ('h1', 30)
						break;
					case 50:
						found = ofs + 2;
						offset = 31; // ('h2', 31)
						break;
					case 51:
						found = ofs + 2;
						offset = 32; // ('h3', 32)
						break;
					case 52:
						found = ofs + 2;
						offset = 33; // ('h4', 33)
						break;
					case 53:
						found = ofs + 2;
						offset = 34; // ('h5', 34)
						break;
					case 54:
						found = ofs + 2;
						offset = 35; // ('h6', 35)
						break;
					case 105:
					case 73:
						if (ofs+4 < src_len && (src[ofs+2] | 32) == 'e' && (src[ofs+3] | 32) == 'r' && (src[ofs+4] | 32) == 'o') {
							found = ofs + 5;
							offset = 72; // ('hiero', 72)
						}
						break;
					case 114:
					case 82:
						found = ofs + 2;
						offset = 28; // ('hr', 28)
						break;
				}
				break;
			case 105:
			case 73:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 109:
					case 77:
						if (ofs+2 < src_len) switch ((src[ofs+2])) {
							case 97:
							case 65:
								if (ofs+7 < src_len && (src[ofs+3] | 32) == 'g' && (src[ofs+4] | 32) == 'e' && (src[ofs+5] | 32) == 'm' && (src[ofs+6] | 32) == 'a' && (src[ofs+7] | 32) == 'p') {
									found = ofs + 8;
									offset = 70; // ('imageMap', 70)
								}
								break;
							case 103:
							case 71:
								found = ofs + 3;
								offset = 53; // ('img', 53)
								break;
						}
						break;
					case 110:
					case 78:
						if (ofs+2 < src_len) switch ((src[ofs+2])) {
							case 99:
							case 67:
								if (ofs+10 < src_len && (src[ofs+3] | 32) == 'l' && (src[ofs+4] | 32) == 'u' && (src[ofs+5] | 32) == 'd' && (src[ofs+6] | 32) == 'e' && (src[ofs+7] | 32) == 'o' && (src[ofs+8] | 32) == 'n' && (src[ofs+9] | 32) == 'l' && (src[ofs+10] | 32) == 'y') {
									found = ofs + 11;
									offset = 58; // ('includeonly', 58)
								}
								break;
							case 100:
							case 68:
								if (ofs+8 < src_len && (src[ofs+3] | 32) == 'i' && (src[ofs+4] | 32) == 'c' && (src[ofs+5] | 32) == 'a' && (src[ofs+6] | 32) == 't' && (src[ofs+7] | 32) == 'o' && (src[ofs+8] | 32) == 'r') {
									found = ofs + 9;
									offset = 115; // ('indicator', 115)
								}
								break;
							case 112:
							case 80:
								if (ofs+4 < src_len && (src[ofs+3] | 32) == 'u' && (src[ofs+4] | 32) == 't') {
									if (ofs+7 < src_len && (src[ofs+5] | 32) == 'b' && (src[ofs+6] | 32) == 'o' && (src[ofs+7] | 32) == 'x') {
										found = ofs + 8;
										offset = 73; // ('inputBox', 73)
									}
									if (found == -1) {
										found = ofs + 5;
										offset = 85; // ('input', 85)
									}
								}
								break;
							case 115:
							case 83:
								found = ofs + 3;
								offset = 8; // ('ins', 8)
								break;
						}
						break;
				}
				if (found == -1) {
					found = ofs + 1;
					offset = 2; // ('i', 2)
				}
				break;
			case 107:
			case 75:
				if (ofs+2 < src_len && (src[ofs+1] | 32) == 'b' && (src[ofs+2] | 32) == 'd') {
					found = ofs + 3;
					offset = 19; // ('kbd', 19)
				}
				break;
			case 108:
			case 76:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 97:
					case 65:
						if (ofs+8 < src_len && (src[ofs+2] | 32) == 'n' && (src[ofs+3] | 32) == 'g' && (src[ofs+4] | 32) == 'u' && (src[ofs+5] | 32) == 'a' && (src[ofs+6] | 32) == 'g' && (src[ofs+7] | 32) == 'e' && (src[ofs+8] | 32) == 's') {
							found = ofs + 9;
							offset = 96; // ('languages', 96)
						}
						break;
					case 105:
					case 73:
						if (ofs+2 < src_len) switch ((src[ofs+2])) {
							case 110:
							case 78:
								if (ofs+3 < src_len && (src[ofs+3] | 32) == 'k') {
									found = ofs + 4;
									offset = 125; // ('link', 125)
								}
								break;
							case 115:
							case 83:
								if (ofs+6 < src_len && (src[ofs+3] | 32) == 't' && (src[ofs+4] | 32) == 'i' && (src[ofs+5] | 32) == 'n' && (src[ofs+6] | 32) == 'g') {
									found = ofs + 7;
									offset = 107; // ('listing', 107)
								}
								break;
						}
						if (found == -1) {
							found = ofs + 2;
							offset = 36; // ('li', 36)
						}
						break;
				}
				break;
			case 109:
			case 77:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 97:
					case 65:
						if (ofs+2 < src_len) switch ((src[ofs+2])) {
							case 112:
							case 80:
								if (ofs+3 < src_len) switch ((src[ofs+3])) {
									case 102:
									case 70:
										if (ofs+7 < src_len && (src[ofs+4] | 32) == 'r' && (src[ofs+5] | 32) == 'a' && (src[ofs+6] | 32) == 'm' && (src[ofs+7] | 32) == 'e') {
											found = ofs + 8;
											offset = 122; // ('mapframe', 122)
										}
										break;
									case 108:
									case 76:
										if (ofs+6 < src_len && (src[ofs+4] | 32) == 'i' && (src[ofs+5] | 32) == 'n' && (src[ofs+6] | 32) == 'k') {
											found = ofs + 7;
											offset = 123; // ('maplink', 123)
										}
										break;
								}
								break;
							case 114:
							case 82:
								if (ofs+3 < src_len && (src[ofs+3] | 32) == 'k') {
									found = ofs + 4;
									offset = 100; // ('mark', 100)
								}
								break;
							case 116:
							case 84:
								if (ofs+3 < src_len && (src[ofs+3] | 32) == 'h') {
									found = ofs + 4;
									offset = 64; // ('math', 64)
								}
								break;
						}
						break;
					case 101:
					case 69:
						if (ofs+3 < src_len && (src[ofs+2] | 32) == 't' && (src[ofs+3] | 32) == 'a') {
							found = ofs + 4;
							offset = 124; // ('meta', 124)
						}
						break;
				}
				break;
			case 110:
			case 78:
				if (ofs+1 < src_len && (src[ofs+1] | 32) == 'o') {
					if (ofs+2 < src_len) switch ((src[ofs+2])) {
						case 105:
						case 73:
							if (ofs+8 < src_len && (src[ofs+3] | 32) == 'n' && (src[ofs+4] | 32) == 'c' && (src[ofs+5] | 32) == 'l' && (src[ofs+6] | 32) == 'u' && (src[ofs+7] | 32) == 'd' && (src[ofs+8] | 32) == 'e') {
								found = ofs + 9;
								offset = 59; // ('noinclude', 59)
							}
							break;
						case 119:
						case 87:
							if (ofs+5 < src_len && (src[ofs+3] | 32) == 'i' && (src[ofs+4] | 32) == 'k' && (src[ofs+5] | 32) == 'i') {
								found = ofs + 6;
								offset = 61; // ('nowiki', 61)
							}
							break;
					}
				}
				break;
			case 111:
			case 79:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 108:
					case 76:
						found = ofs + 2;
						offset = 39; // ('ol', 39)
						break;
					case 110:
					case 78:
						if (ofs+10 < src_len && (src[ofs+2] | 32) == 'l' && (src[ofs+3] | 32) == 'y' && (src[ofs+4] | 32) == 'i' && (src[ofs+5] | 32) == 'n' && (src[ofs+6] | 32) == 'c' && (src[ofs+7] | 32) == 'l' && (src[ofs+8] | 32) == 'u' && (src[ofs+9] | 32) == 'd' && (src[ofs+10] | 32) == 'e') {
							found = ofs + 11;
							offset = 60; // ('onlyinclude', 60)
						}
						break;
					case 112:
					case 80:
						if (ofs+2 < src_len && (src[ofs+2] | 32) == 't') {
							if (ofs+3 < src_len) switch ((src[ofs+3])) {
								case 103:
								case 71:
									if (ofs+7 < src_len && (src[ofs+4] | 32) == 'r' && (src[ofs+5] | 32) == 'o' && (src[ofs+6] | 32) == 'u' && (src[ofs+7] | 32) == 'p') {
										found = ofs + 8;
										offset = 91; // ('optgroup', 91)
									}
									break;
								case 105:
								case 73:
									if (ofs+5 < src_len && (src[ofs+4] | 32) == 'o' && (src[ofs+5] | 32) == 'n') {
										found = ofs + 6;
										offset = 90; // ('option', 90)
									}
									break;
							}
						}
						break;
				}
				break;
			case 112:
			case 80:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 97:
					case 65:
						if (ofs+3 < src_len && (src[ofs+2] | 32) == 'g' && (src[ofs+3] | 32) == 'e') {
							if (ofs+4 < src_len) switch ((src[ofs+4])) {
								case 108:
								case 76:
									if (ofs+7 < src_len && (src[ofs+5] | 32) == 'i' && (src[ofs+6] | 32) == 's' && (src[ofs+7] | 32) == 't') {
										found = ofs + 8;
										offset = 80; // ('pagelist', 80)
									}
									break;
								case 113:
								case 81:
									if (ofs+10 < src_len && (src[ofs+5] | 32) == 'u' && (src[ofs+6] | 32) == 'a' && (src[ofs+7] | 32) == 'l' && (src[ofs+8] | 32) == 'i' && (src[ofs+9] | 32) == 't' && (src[ofs+10] | 32) == 'y') {
										found = ofs + 11;
										offset = 79; // ('pagequality', 79)
									}
									break;
								case 115:
								case 83:
									found = ofs + 5;
									offset = 74; // ('pages', 74)
									break;
							}
						}
						break;
					case 111:
					case 79:
						if (ofs+3 < src_len && (src[ofs+2] | 32) == 'e' && (src[ofs+3] | 32) == 'm') {
							found = ofs + 4;
							offset = 63; // ('poem', 63)
						}
						break;
					case 114:
					case 82:
						if (ofs+2 < src_len && (src[ofs+2] | 32) == 'e') {
							found = ofs + 3;
							offset = 22; // ('pre', 22)
						}
						break;
				}
				if (found == -1) {
					found = ofs + 1;
					offset = 25; // ('p', 25)
				}
				break;
			case 113:
			case 81:
				if (ofs+3 < src_len && (src[ofs+1] | 32) == 'u' && (src[ofs+2] | 32) == 'i' && (src[ofs+3] | 32) == 'z') {
					found = ofs + 4;
					offset = 114; // ('quiz', 114)
				}
				if (found == -1) {
					found = ofs + 1;
					offset = 116; // ('q', 116)
				}
				break;
			case 114:
			case 82:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 98:
					case 66:
						found = ofs + 2;
						offset = 56; // ('rb', 56)
						break;
					case 101:
					case 69:
						if (ofs+2 < src_len && (src[ofs+2] | 32) == 'f') {
							if (ofs+9 < src_len && (src[ofs+3] | 32) == 'e' && (src[ofs+4] | 32) == 'r' && (src[ofs+5] | 32) == 'e' && (src[ofs+6] | 32) == 'n' && (src[ofs+7] | 32) == 'c' && (src[ofs+8] | 32) == 'e' && (src[ofs+9] | 32) == 's') {
								found = ofs + 10;
								offset = 66; // ('references', 66)
							}
							if (found == -1) {
								found = ofs + 3;
								offset = 65; // ('ref', 65)
							}
						}
						break;
					case 112:
					case 80:
						found = ofs + 2;
						offset = 57; // ('rp', 57)
						break;
					case 115:
					case 83:
						if (ofs+2 < src_len && (src[ofs+2] | 32) == 's') {
							found = ofs + 3;
							offset = 110; // ('rss', 110)
						}
						break;
					case 116:
					case 84:
						if (ofs+2 < src_len && (src[ofs+2] | 32) == 'c') {
							found = ofs + 3;
							offset = 130; // ('rtc', 130)
						}
						if (found == -1) {
							found = ofs + 2;
							offset = 55; // ('rt', 55)
						}
						break;
					case 117:
					case 85:
						if (ofs+3 < src_len && (src[ofs+2] | 32) == 'b' && (src[ofs+3] | 32) == 'y') {
							found = ofs + 4;
							offset = 54; // ('ruby', 54)
						}
						break;
				}
				break;
			case 115:
			case 83:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 97:
					case 65:
						if (ofs+3 < src_len && (src[ofs+2] | 32) == 'm' && (src[ofs+3] | 32) == 'p') {
							found = ofs + 4;
							offset = 20; // ('samp', 20)
						}
						break;
					case 99:
					case 67:
						if (ofs+2 < src_len) switch ((src[ofs+2])) {
							case 111:
							case 79:
								if (ofs+4 < src_len && (src[ofs+3] | 32) == 'r' && (src[ofs+4] | 32) == 'e') {
									found = ofs + 5;
									offset = 87; // ('score', 87)
								}
								break;
							case 114:
							case 82:
								if (ofs+5 < src_len && (src[ofs+3] | 32) == 'i' && (src[ofs+4] | 32) == 'p' && (src[ofs+5] | 32) == 't') {
									found = ofs + 6;
									offset = 92; // ('script', 92)
								}
								break;
						}
						break;
					case 101:
					case 69:
						if (ofs+2 < src_len) switch ((src[ofs+2])) {
							case 99:
							case 67:
								if (ofs+6 < src_len && (src[ofs+3] | 32) == 't' && (src[ofs+4] | 32) == 'i' && (src[ofs+5] | 32) == 'o' && (src[ofs+6] | 32) == 'n') {
									found = ofs + 7;
									offset = 75; // ('section', 75)
								}
								break;
							case 101:
							case 69:
								found = ofs + 3;
								offset = 108; // ('see', 108)
								break;
							case 108:
							case 76:
								if (ofs+5 < src_len && (src[ofs+3] | 32) == 'e' && (src[ofs+4] | 32) == 'c' && (src[ofs+5] | 32) == 't') {
									found = ofs + 6;
									offset = 89; // ('select', 89)
								}
								break;
						}
						break;
					case 108:
					case 76:
						if (ofs+4 < src_len && (src[ofs+2] | 32) == 'e' && (src[ofs+3] | 32) == 'e' && (src[ofs+4] | 32) == 'p') {
							found = ofs + 5;
							offset = 109; // ('sleep', 109)
						}
						break;
					case 109:
					case 77:
						if (ofs+4 < src_len && (src[ofs+2] | 32) == 'a' && (src[ofs+3] | 32) == 'l' && (src[ofs+4] | 32) == 'l') {
							found = ofs + 5;
							offset = 16; // ('small', 16)
						}
						break;
					case 111:
					case 79:
						if (ofs+5 < src_len && (src[ofs+2] | 32) == 'u' && (src[ofs+3] | 32) == 'r' && (src[ofs+4] | 32) == 'c' && (src[ofs+5] | 32) == 'e') {
							found = ofs + 6;
							offset = 67; // ('source', 67)
						}
						break;
					case 112:
					case 80:
						if (ofs+3 < src_len && (src[ofs+2] | 32) == 'a' && (src[ofs+3] | 32) == 'n') {
							found = ofs + 4;
							offset = 26; // ('span', 26)
						}
						break;
					case 116:
					case 84:
						if (ofs+2 < src_len) switch ((src[ofs+2])) {
							case 114:
							case 82:
								if (ofs+3 < src_len) switch ((src[ofs+3])) {
									case 105:
									case 73:
										if (ofs+5 < src_len && (src[ofs+4] | 32) == 'k' && (src[ofs+5] | 32) == 'e') {
											found = ofs + 6;
											offset = 10; // ('strike', 10)
										}
										break;
									case 111:
									case 79:
										if (ofs+5 < src_len && (src[ofs+4] | 32) == 'n' && (src[ofs+5] | 32) == 'g') {
											found = ofs + 6;
											offset = 1; // ('strong', 1)
										}
										break;
								}
								break;
							case 121:
							case 89:
								if (ofs+4 < src_len && (src[ofs+3] | 32) == 'l' && (src[ofs+4] | 32) == 'e') {
									found = ofs + 5;
									offset = 93; // ('style', 93)
								}
								break;
						}
						break;
					case 117:
					case 85:
						if (ofs+2 < src_len) switch ((src[ofs+2])) {
							case 98:
							case 66:
								found = ofs + 3;
								offset = 13; // ('sub', 13)
								break;
							case 112:
							case 80:
								found = ofs + 3;
								offset = 14; // ('sup', 14)
								break;
						}
						break;
					case 121:
					case 89:
						if (ofs+14 < src_len && (src[ofs+2] | 32) == 'n' && (src[ofs+3] | 32) == 't' && (src[ofs+4] | 32) == 'a' && (src[ofs+5] | 32) == 'x' && (src[ofs+6] | 32) == 'h' && (src[ofs+7] | 32) == 'i' && (src[ofs+8] | 32) == 'g' && (src[ofs+9] | 32) == 'h' && (src[ofs+10] | 32) == 'l' && (src[ofs+11] | 32) == 'i' && (src[ofs+12] | 32) == 'g' && (src[ofs+13] | 32) == 'h' && (src[ofs+14] | 32) == 't') {
							found = ofs + 15;
							offset = 68; // ('syntaxHighlight', 68)
						}
						break;
				}
				if (found == -1) {
					found = ofs + 1;
					offset = 12; // ('s', 12)
				}
				break;
			case 116:
			case 84:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 97:
					case 65:
						if (ofs+2 < src_len && (src[ofs+2] | 32) == 'b') {
							if (ofs+3 < src_len) switch ((src[ofs+3])) {
								case 98:
								case 66:
									if (ofs+5 < src_len && (src[ofs+4] | 32) == 'e' && (src[ofs+5] | 32) == 'r') {
										found = ofs + 6;
										offset = 119; // ('tabber', 119)
									}
									break;
								case 108:
								case 76:
									if (ofs+4 < src_len && (src[ofs+4] | 32) == 'e') {
										found = ofs + 5;
										offset = 42; // ('table', 42)
									}
									break;
								case 118:
								case 86:
									if (ofs+6 < src_len && (src[ofs+4] | 32) == 'i' && (src[ofs+5] | 32) == 'e' && (src[ofs+6] | 32) == 'w') {
										found = ofs + 7;
										offset = 120; // ('tabview', 120)
									}
									break;
							}
						}
						break;
					case 98:
					case 66:
						if (ofs+4 < src_len && (src[ofs+2] | 32) == 'o' && (src[ofs+3] | 32) == 'd' && (src[ofs+4] | 32) == 'y') {
							found = ofs + 5;
							offset = 48; // ('tbody', 48)
						}
						break;
					case 100:
					case 68:
						found = ofs + 2;
						offset = 44; // ('td', 44)
						break;
					case 101:
					case 69:
						if (ofs+2 < src_len) switch ((src[ofs+2])) {
							case 109:
							case 77:
								if (ofs+7 < src_len && (src[ofs+3] | 32) == 'p' && (src[ofs+4] | 32) == 'l' && (src[ofs+5] | 32) == 'a' && (src[ofs+6] | 32) == 't' && (src[ofs+7] | 32) == 'e') {
									if (ofs+8 < src_len) switch ((src[ofs+8])) {
										case 100:
										case 68:
											if (ofs+11 < src_len && (src[ofs+9] | 32) == 'a' && (src[ofs+10] | 32) == 't' && (src[ofs+11] | 32) == 'a') {
												found = ofs + 12;
												offset = 97; // ('templateData', 97)
											}
											break;
										case 115:
										case 83:
											if (ofs+13 < src_len && (src[ofs+9] | 32) == 't' && (src[ofs+10] | 32) == 'y' && (src[ofs+11] | 32) == 'l' && (src[ofs+12] | 32) == 'e' && (src[ofs+13] | 32) == 's') {
												found = ofs + 14;
												offset = 126; // ('templatestyles', 126)
											}
											break;
									}
								}
								break;
							case 120:
							case 88:
								if (ofs+7 < src_len && (src[ofs+3] | 32) == 't' && (src[ofs+4] | 32) == 'a' && (src[ofs+5] | 32) == 'r' && (src[ofs+6] | 32) == 'e' && (src[ofs+7] | 32) == 'a') {
									found = ofs + 8;
									offset = 86; // ('textarea', 86)
								}
								break;
						}
						break;
					case 102:
					case 70:
						if (ofs+4 < src_len && (src[ofs+2] | 32) == 'o' && (src[ofs+3] | 32) == 'o' && (src[ofs+4] | 32) == 't') {
							found = ofs + 5;
							offset = 47; // ('tfoot', 47)
						}
						break;
					case 104:
					case 72:
						if (ofs+4 < src_len && (src[ofs+2] | 32) == 'e' && (src[ofs+3] | 32) == 'a' && (src[ofs+4] | 32) == 'd') {
							found = ofs + 5;
							offset = 46; // ('thead', 46)
						}
						if (found == -1) {
							found = ofs + 2;
							offset = 45; // ('th', 45)
						}
						break;
					case 105:
					case 73:
						if (ofs+3 < src_len && (src[ofs+2] | 32) == 'm' && (src[ofs+3] | 32) == 'e') {
							if (ofs+7 < src_len && (src[ofs+4] | 32) == 'l' && (src[ofs+5] | 32) == 'i' && (src[ofs+6] | 32) == 'n' && (src[ofs+7] | 32) == 'e') {
								found = ofs + 8;
								offset = 71; // ('timeline', 71)
							}
							if (found == -1) {
								found = ofs + 4;
								offset = 84; // ('time', 84)
							}
						}
						break;
					case 114:
					case 82:
						if (ofs+2 < src_len) switch ((src[ofs+2])) {
							case 97:
							case 65:
								if (ofs+3 < src_len) switch ((src[ofs+3])) {
									case 99:
									case 67:
										if (ofs+4 < src_len && (src[ofs+4] | 32) == 'k') {
											found = ofs + 5;
											offset = 129; // ('track', 129)
										}
										break;
									case 110:
									case 78:
										if (ofs+8 < src_len && (src[ofs+4] | 32) == 's' && (src[ofs+5] | 32) == 'l' && (src[ofs+6] | 32) == 'a' && (src[ofs+7] | 32) == 't' && (src[ofs+8] | 32) == 'e') {
											found = ofs + 9;
											offset = 95; // ('translate', 95)
										}
										break;
								}
								break;
							case 101:
							case 69:
								if (ofs+5 < src_len && (src[ofs+3] | 32) == 'c' && (src[ofs+4] | 32) == 'h' && (src[ofs+5] | 32) == 'o') {
									found = ofs + 6;
									offset = 78; // ('trecho', 78)
								}
								break;
						}
						if (found == -1) {
							found = ofs + 2;
							offset = 43; // ('tr', 43)
						}
						break;
					case 116:
					case 84:
						found = ofs + 2;
						offset = 18; // ('tt', 18)
						break;
				}
				break;
			case 117:
			case 85:
				if (ofs+1 < src_len && (src[ofs+1] | 32) == 'l') {
					found = ofs + 2;
					offset = 40; // ('ul', 40)
				}
				if (found == -1) {
					found = ofs + 1;
					offset = 7; // ('u', 7)
				}
				break;
			case 118:
			case 86:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 97:
					case 65:
						if (ofs+2 < src_len && (src[ofs+2] | 32) == 'r') {
							found = ofs + 3;
							offset = 6; // ('var', 6)
						}
						break;
					case 105:
					case 73:
						if (ofs+4 < src_len && (src[ofs+2] | 32) == 'd' && (src[ofs+3] | 32) == 'e' && (src[ofs+4] | 32) == 'o') {
							found = ofs + 5;
							offset = 128; // ('video', 128)
						}
						break;
				}
				break;
			case 119:
			case 87:
				if (ofs+2 < src_len && (src[ofs+1] | 32) == 'b' && (src[ofs+2] | 32) == 'r') {
					found = ofs + 3;
					offset = 101; // ('wbr', 101)
				}
				break;
			case 120:
			case 88:
				if (ofs+1 < src_len) switch ((src[ofs+1])) {
					case 111:
					case 79:
						if (ofs+4 < src_len && (src[ofs+2] | 32) == 'w' && (src[ofs+3] | 32) == 'a' && src[ofs+4] == '_') {
							if (ofs+5 < src_len) switch ((src[ofs+5])) {
								case 99:
								case 67:
									if (ofs+7 < src_len && (src[ofs+6] | 32) == 'm' && (src[ofs+7] | 32) == 'd') {
										found = ofs + 8;
										offset = 62; // ('xowa_cmd', 62)
									}
									break;
								case 104:
								case 72:
									if (ofs+8 < src_len && (src[ofs+6] | 32) == 't' && (src[ofs+7] | 32) == 'm' && (src[ofs+8] | 32) == 'l') {
										found = ofs + 9;
										offset = 111; // ('xowa_html', 111)
									}
									break;
								case 119:
								case 87:
									if (ofs+14 < src_len && (src[ofs+6] | 32) == 'i' && (src[ofs+7] | 32) == 'k' && (src[ofs+8] | 32) == 'i' && src[ofs+9] == '_' && (src[ofs+10] | 32) == 's' && (src[ofs+11] | 32) == 'e' && (src[ofs+12] | 32) == 't' && (src[ofs+13] | 32) == 'u' && (src[ofs+14] | 32) == 'p') {
										found = ofs + 15;
										offset = 121; // ('xowa_Wiki_setup', 121)
									}
									break;
							}
						}
						break;
					case 116:
					case 84:
						if (ofs+4 < src_len && (src[ofs+2] | 32) == 'a' && (src[ofs+3] | 32) == 'g' && src[ofs+4] == '_') {
							if (ofs+5 < src_len) switch ((src[ofs+5])) {
								case 98:
								case 66:
									if (ofs+7 < src_len && (src[ofs+6] | 32) == 'g' && (src[ofs+7] | 32) == 'n') {
										found = ofs + 8;
										offset = 112; // ('xtag_bgn', 112)
									}
									break;
								case 101:
								case 69:
									if (ofs+7 < src_len && (src[ofs+6] | 32) == 'n' && (src[ofs+7] | 32) == 'd') {
										found = ofs + 8;
										offset = 113; // ('xtag_end', 113)
									}
									break;
							}
						}
						break;
				}
				break;
			case 1511:
				if (ofs+5 < src_len && src[ofs+2] == -41 && src[ofs+3] == -104 && src[ofs+4] == -41 && src[ofs+5] == -94) {
					found = ofs + 6;
					offset = 77; // ('×§×\x98×¢', 77)
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
