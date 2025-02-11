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
package gplx.core.net; import gplx.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
import gplx.xowa.Db_btrie_http;
public class Http_request_parser {
	private boolean dnt;
	private int type, content_length;
	private byte[] url, protocol, host, user_agent, accept, accept_language, 
				accept_encoding, x_requested_with, cookie, referer, content_type, 
				content_type_boundary, connection, pragma, cache_control, origin;
	private Http_post_data_hash post_data_hash;
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(255);
        private final    Btrie_rv trv = new Btrie_rv();
	private final    Http_server_wtr server_wtr; private final    boolean log;
	public Http_request_parser(Http_server_wtr server_wtr, boolean log) {
		this.server_wtr = server_wtr;
		this.log = log;
		byte[] md5 = trie.Md5();
		if (Bry_.Eq(md5, Db_btrie_http.Hash()))
			trie_http = new Db_btrie_http(trie.Objs());
		else {
			// need to rebuild the list
			int a = 1/0; // eeeeek
		}
			
	}
	public void Clear() {
		this.dnt = false;
		this.type = this.content_length = 0;
		this.url = this.protocol = this.host = this.user_agent = this.accept
				= this.accept_language = this.accept_encoding = this.x_requested_with = this.cookie
				= this.referer = this.content_type = this.content_type_boundary 
				= this.connection = this.pragma = this.cache_control = this.origin 
				= null;
		this.post_data_hash = null;
	}
	public Http_request_itm Parse(Http_client_rdr rdr) {
		synchronized (tmp_bfr) {
			this.Clear();
			while (true) {
				String line_str = rdr.Read_line(); if (line_str == null) break;	// needed for TEST
				if (log) server_wtr.Write_str_w_nl(line_str);
				byte[] line = Bry_.new_u8(line_str);
				int line_len = line.length;
				if (line_len == 0) {
					switch (type) {
						case Http_request_itm.Type_get:
							break;
						case Http_request_itm.Type_post:
							Process_post_data(rdr);
							break;
						default:
							throw Err_.new_unimplemented(62);
					}
					break;
				}
				//Object o = trie.Match_at(trv, line, 0, line_len);
				Object o = trie_http.Match_expand(trv, line, 0, line_len);
				if (o == null) {
					server_wtr.Write_str_w_nl(String_.Format("http.request.parser; unknown line; line={0} request={1}", line_str, To_str()));
					continue;
				}
				int val_bgn = Bry_find_.Find_fwd_while_ws(line, trv.Pos(), line_len);	// skip ws after key; EX: "Host: "
				int tid = ((Int_obj_val)o).Val();
				switch (tid) {
					case Tid_get:
					case Tid_post:						Parse_type(tid, val_bgn, line, line_len); break;
					case Tid_host:						this.host = Bry_.Mid(line, val_bgn, line_len); break;
					case Tid_user_agent:				this.user_agent = Bry_.Mid(line, val_bgn, line_len); break;
					case Tid_accept:					this.accept = Bry_.Mid(line, val_bgn, line_len); break;
					case Tid_accept_language:			this.accept_language = Bry_.Mid(line, val_bgn, line_len); break;
					case Tid_accept_encoding:			this.accept_encoding = Bry_.Mid(line, val_bgn, line_len); break;
					case Tid_dnt:						this.dnt = line[val_bgn] == Byte_ascii.Num_1; break;
					case Tid_x_requested_with:			this.x_requested_with = Bry_.Mid(line, val_bgn, line_len); break;
					case Tid_cookie:					this.cookie = Bry_.Mid(line, val_bgn, line_len); break;
					case Tid_referer:					this.referer = Bry_.Mid(line, val_bgn, line_len); break;
					case Tid_content_length:			this.content_length = Bry_.To_int_or(line, val_bgn, line_len, -1); break;
					case Tid_content_type:				Parse_content_type(val_bgn, line, line_len); break;
					case Tid_connection:				this.connection = Bry_.Mid(line, val_bgn, line_len); break;
					case Tid_pragma:					this.pragma = Bry_.Mid(line, val_bgn, line_len); break;
					case Tid_cache_control:				this.cache_control = Bry_.Mid(line, val_bgn, line_len); break;
					case Tid_origin:					this.origin = Bry_.Mid(line, val_bgn, line_len); break;
					case Tid_upgrade_request:			break;
					case Tid_x_host:					break;
					case Tid_x_real_ip:     			break;
					case Tid_accept_charset:			break;
					case Tid_sec_fetch_mode:            break;
					case Tid_sec_fetch_site:            break;
					case Tid_sec_fetch_dest:            break;
					case Tid_sec_fetch_user:            break;
					case Tid_sec_ch_ua:                 break;
					case Tid_sec_ch_ua_mobile:          break;
					case Tid_sec_gpc:                   break;
					case Tid_sec_ch_ua_platform:        break;
					default:							throw Err_.new_unhandled(tid);
				}
			}
			return Make_request_itm();
		}
	}
	private void Process_post_data(Http_client_rdr rdr) {
		if (content_length == 0) return; // nothing to do
		if (post_data_hash == null) post_data_hash = new Http_post_data_hash();
		String line_str = rdr.Read_line();
		if (log) server_wtr.Write_str_w_nl(line_str);
		byte[] line = Bry_.new_u8(line_str);
		int line_len = line.length;
		if (content_type_boundary != null && Bry_.Has_at_bgn(line, content_type_boundary)) {
			while (true) {
				if (Bry_.Has_at_end(line, Tkn_content_type_boundary_end)) break;	// last form_data pair will end with "--"; stop
				line = Parse_content_type_boundary(rdr);
			}
			return;	// assume form_data sends POST request
		}
		// assume all on one line
		post_data_hash.Add(Bry_.new_a7("data"), line);
	}
	private void Parse_type(int tid, int val_bgn, byte[] line, int line_len) {	// EX: "POST /xowa-cmd:exec_as_json HTTP/1.1"
		int url_end = Bry_find_.Find_bwd(line, Byte_ascii.Space, line_len); if (url_end == Bry_find_.Not_found) throw Err_.new_wo_type("invalid protocol", "line", line, "request", To_str());
		switch (tid) {
			case Tid_get	: this.type = Http_request_itm.Type_get; break;
			case Tid_post	: this.type = Http_request_itm.Type_post; break;
			default			: throw Err_.new_unimplemented(63);
		}
		if (val_bgn + 5 < url_end && line[val_bgn] == '/' && line[val_bgn+1] == 'x' && line[val_bgn+2] == 'o' && line[val_bgn+3] == 'w' && line[val_bgn+4] == 'a')
			val_bgn += 5;
		this.url = Bry_.Mid(line, val_bgn, url_end);
		this.protocol = Bry_.Mid(line, url_end + 1, line_len);
	}
	private void Parse_content_type(int val_bgn, byte[] line, int line_len) {	// EX: Content-Type: multipart/form-data; boundary=---------------------------72432484930026
		// handle wolfram and other clients; DATE:2015-08-03
		int boundary_bgn = Bry_find_.Find_fwd(line, Tkn_boundary, val_bgn, line_len); if (boundary_bgn == Bry_find_.Not_found) return; // PURPOSE: ignore content-type for GET calls like by Mathematica server; DATE:2015-08-04 // throw Err_.new_wo_type("invalid content_type", "line", line, "request", To_str());
		int content_type_end = Bry_find_.Find_bwd(line, Byte_ascii.Semic, boundary_bgn);
		this.content_type = Bry_.Mid(line, val_bgn, content_type_end);
		this.content_type_boundary = Bry_.Add(Tkn_content_type_boundary_end, Bry_.Mid(line, boundary_bgn += Tkn_boundary.length, line_len));
	}
	private Http_request_itm Make_request_itm() {
		return new Http_request_itm(type, url, protocol, host, user_agent, accept, accept_language, accept_encoding, dnt, x_requested_with, cookie, referer, content_length, content_type, content_type_boundary, connection, pragma, cache_control, origin, post_data_hash);
	}
	private byte[] Parse_content_type_boundary(Http_client_rdr rdr) {
		byte[] line = Bry_.new_u8(rdr.Read_line());  // cur line is already known to be content_type_boundary; skip it
		byte[] key = Parse_post_data_name(line);
		String line_str = rdr.Read_line();	// blank-line
		if (String_.Len_gt_0(line_str)) {throw Err_.new_wo_type("http.request.parser; blank_line should follow content_type_boundary", "request", To_str());}
		while (true) {
			line = Bry_.new_u8(rdr.Read_line());
			if (Bry_.Has_at_bgn(line, content_type_boundary)) break;

			// add \n between lines, but not after last line
			if (tmp_bfr.Len_gt_0())
				tmp_bfr.Add_byte_nl();
			tmp_bfr.Add(line);
		}
		byte[] val = tmp_bfr.To_bry_and_clear();
		post_data_hash.Add(key, val);
		return line;
	}
	private byte[] Parse_post_data_name(byte[] line) { // EX: Content-Disposition: form-data; name="data"
		int line_len = line.length;
		int pos = Assert_tkn(line, 0, line_len, Tkn_content_disposition);
		pos = Assert_tkn(line, pos, line_len, Tkn_form_data);
		pos = Assert_tkn(line, pos, line_len, Tkn_name);
		int name_end = line_len;
		if (line[pos] == Byte_ascii.Quote) {
			if (line[name_end - 1] != Byte_ascii.Quote) throw Err_.new_wo_type("http.request.parser; invalid form at end", "line", line, "request", To_str());
			++pos;
			--name_end;
		}
		return Bry_.Mid(line, pos, name_end);
	}
	private int Assert_tkn(byte[] src, int src_pos, int src_len, byte[] tkn) {
		int tkn_len = tkn.length;
		if (!Bry_.Match(src, src_pos, src_pos + tkn_len, tkn)) throw Err_.new_wo_type("http.request.parser; invalid form_data line", "tkn", tkn, "line", src, "request", To_str());
		int rv = src_pos += tkn_len;
		return Bry_find_.Find_fwd_while_ws(src, rv, src_len);
	}
	private String To_str() {return Make_request_itm().To_str(Bool_.N);}
	private static final int Tid_get = 1, Tid_post = 2, Tid_host = 3, Tid_user_agent = 4, Tid_accept = 5, Tid_accept_language = 6, Tid_accept_encoding = 7, Tid_dnt = 8
	, Tid_x_requested_with = 9, Tid_cookie = 10, Tid_referer = 11, Tid_content_length = 12, Tid_content_type = 13, Tid_connection = 14, Tid_pragma = 15, Tid_cache_control = 16
	, Tid_origin = 17, Tid_accept_charset = 188, Tid_upgrade_request = 19, Tid_x_host = 20, Tid_x_real_ip = 21
	, Tid_sec_fetch_mode = 22, Tid_sec_fetch_site = 23, Tid_sec_fetch_dest = 24, Tid_sec_fetch_user = 25
	, Tid_sec_ch_ua = 26, Tid_sec_ch_ua_mobile = 27, Tid_sec_gpc = 28, Tid_sec_ch_ua_platform = 29;
	private static final    Btrie_slim_mgr trie = Btrie_slim_mgr.ci_a7()
	.Add_str_int("GET"                                    , Tid_get)
	.Add_str_int("POST"                                   , Tid_post)
	.Add_str_int("Host:"                                  , Tid_host)
	.Add_str_int("User-Agent:"                            , Tid_user_agent)
	.Add_str_int("Accept:"                                , Tid_accept)
	.Add_str_int("Accept-Language:"                       , Tid_accept_language)
	.Add_str_int("Accept-Encoding:"                       , Tid_accept_encoding)
	.Add_str_int("Accept-Charset:"                        , Tid_accept_charset)
	.Add_str_int("DNT:"                                   , Tid_dnt)
	.Add_str_int("X-Requested-With:"                      , Tid_x_requested_with)
	.Add_str_int("Cookie:"                                , Tid_cookie)
	.Add_str_int("Referer:"                               , Tid_referer)
	.Add_str_int("Content-length:"                        , Tid_content_length)
	.Add_str_int("Content-Type:"                          , Tid_content_type)
	.Add_str_int("Connection:"                            , Tid_connection)
	.Add_str_int("Pragma:"                                , Tid_pragma)
	.Add_str_int("Cache-Control:"                         , Tid_cache_control)
	.Add_str_int("Origin:"                                , Tid_origin)
	.Add_str_int("Upgrade-Insecure-Requests:"             , Tid_upgrade_request)
	.Add_str_int("X-Host:"                                , Tid_x_host)
	.Add_str_int("X-Real-IP:"                             , Tid_x_real_ip)
	.Add_str_int("Sec-Fetch-Mode:"                        , Tid_sec_fetch_mode)
	.Add_str_int("Sec-Fetch-Site:"                        , Tid_sec_fetch_site)
	.Add_str_int("Sec-Fetch-Dest:"                        , Tid_sec_fetch_dest)
	.Add_str_int("Sec-Fetch-User:"                        , Tid_sec_fetch_user)
	.Add_str_int("Sec-Ch-Ua:"                             , Tid_sec_ch_ua)
	.Add_str_int("Sec-Ch-Ua-Mobile:"                      , Tid_sec_ch_ua_mobile)
	.Add_str_int("Sec-GPC:"                               , Tid_sec_gpc)
	.Add_str_int("Sec-Ch-Ua-platform:"                    , Tid_sec_ch_ua_platform)
	;
	private static Db_btrie_http trie_http;
	private static final    byte[] Tkn_boundary = Bry_.new_a7("boundary="), Tkn_content_type_boundary_end = Bry_.new_a7("--")
	, Tkn_content_disposition = Bry_.new_a7("Content-Disposition:"), Tkn_form_data = Bry_.new_a7("form-data;")
	, Tkn_name = Bry_.new_a7("name=")
	;
}
