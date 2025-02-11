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
package gplx.xowa.xtns.kartographers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.logs.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*;
import gplx.langs.jsons.*;
public class Maplink_xnde implements Xox_xnde, Mwh_atr_itm_owner2 {
	private byte[] lat, lon, zoom, show, group, mapstyle, text, klass;
	private int json_bgn, json_end;
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, byte xatr_id) {
		switch (xatr_id) {
			case Map_atrs.Tid__latitude:   lat = Bry_.Zerotrim(xatr.Val_as_bry()); break;
			case Map_atrs.Tid__longitude:  lon = Bry_.Zerotrim(xatr.Val_as_bry()); break;
			case Map_atrs.Tid__zoom:       zoom = Bry_.Zerotrim(xatr.Val_as_bry()); break;
			case Map_atrs.Tid__show:       show = xatr.Val_as_bry(); break;
			case Map_atrs.Tid__group:      group = xatr.Val_as_bry(); break;
			case Map_atrs.Tid__mapstyle:   mapstyle = xatr.Val_as_bry(); break;
			case Map_atrs.Tid__text:       text = xatr.Val_as_bry(); break;
			case Map_atrs.Tid__class:      klass = xatr.Val_as_bry(); break;
			default:
				break;
		}
	}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		Xox_xnde_.Parse_xatrs(wiki, this, Map_atrs.Key_hash, src, xnde);
		if (this.mapstyle == null) {
			//this.mapstyle = Bry_.new_a7("osm-intl"); // osm-intl or osm
			this.mapstyle = Bry_.new_a7("osm-tegola"); // ~2022 different mapper
		}
		json_bgn = xnde.Tag_open_end();
		json_end = xnde.Tag_close_bgn();
		Json_parser jdoc_parser = new Json_parser();
		Json_doc jdoc = jdoc_parser.Parse(src, json_bgn, json_end);
		//System.out.println(String_.new_u8(src, xnde.Tag_open_bgn(), xnde.Tag_close_end()));
                
		if (jdoc != null) {
			Db_karto_counters counters = ctx.Page().Karto_counters();
			Json_grp grp = jdoc.Root_grp();
			if (grp instanceof Json_nde)
				doCountersRecursive(grp, counters);
			else if (grp.Len() == 1) {
				grp = (Json_grp)grp.Get_at(0);
				doCountersRecursive(grp, counters);
			}
			else {
				// not sure what to do
				int a = 1;
			}
		}
//		if (jdoc == null) {
//			Gfo_usr_dlg_.Instance.Warn_many("", "", "invalid jdoc for ttl: orig=~{0} cur=~{1}", ttl_bry, cur_ttl_bry);
//		}
		//ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_bgn);
		//boolean log_wkr_enabled = Log_wkr != Xop_log_basic_wkr.Null; if (log_wkr_enabled) Log_wkr.Log_end_xnde(ctx.Page(), Xop_log_basic_wkr.Tid_maplink, src, xnde);
		//ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_end);
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		Render(bfr, ctx);
	}
	public static Xop_log_basic_wkr Log_wkr = Xop_log_basic_wkr.Null;
	private byte[] firstMarker = null;
	private Json_grp firstGrp = null;
	public void doCountersRecursive(Json_grp ary, Db_karto_counters counters) {
 		int len_ary = ary.Len();
		for (int i = 0; i < len_ary; i++) {
			Json_kv nde_kv = (Json_kv)ary.Get_at(i);
			if (Bry_.Eq(nde_kv.Key_as_bry(), Bry_.new_a7("properties"))) {
				Json_grp props = (Json_grp)nde_kv.Val();
				int props_len = props.Len();
				for (int j = 0; j < props_len; j++) {
					Json_kv props_kv = (Json_kv)props.Get_at(j);
					if (Bry_.Eq(props_kv.Key_as_bry(), Bry_.new_a7("marker-symbol"))) {
						Json_itm_str itm_str = (Json_itm_str)props_kv.Val();
						byte[] cntr = counters.Get_or_set(itm_str.Data_bry());
						if (cntr != null) {
							itm_str.Overwrite_bry(cntr);
							if (firstMarker == null) {
								firstMarker = cntr;
								firstGrp = props;
							}
						}
						break;
					}
				}
			}
				// other stuff
			/*if ( !property_exists( $item, 'type' ) ) {
				continue;
			}
			$type = $item->type;
			if ( $type === 'FeatureCollection' && property_exists( $item, 'features' ) ) {
				$tmp = self::doCountersRecursive( $item->features, $counters );
				if ( $firstMarker === false ) {
					$firstMarker = $tmp;
				}
			} elseif ( $type === 'GeometryCollection' && property_exists( $item, 'geometries' ) ) {
				$tmp = self::doCountersRecursive( $item->geometries, $counters );
				if ( $firstMarker === false ) {
					$firstMarker = $tmp;
				}
			}*/
		}
	}
	private byte[] extractMarkerCss() {
		if (firstGrp == null) return null;
		int props_len = firstGrp.Len();
		for (int j = 0; j < props_len; j++) {
			Json_kv props_kv = (Json_kv)firstGrp.Get_at(j);
			if (Bry_.Eq(props_kv.Key_as_bry(), Bry_.new_a7("marker-color"))) {
				Json_itm_str itm_str = (Json_itm_str)props_kv.Val();
				// should do some validating
				byte[] background;
				byte[] colorcode = itm_str.Data_bry();
				if (colorcode.length > 0 && colorcode[0] != '#')
					background = background1;
				else
					background = background2;
				return Bry_.Add(background, colorcode, semi);
			}
		}
		return null;
	}
	private static byte[]
	  background1 = Bry_.new_a7("background: #")
	, background2 = Bry_.new_a7("background: ")
	, semi = Bry_.new_a7(";")
	;
	private void Render(Bry_bfr bfr, Xop_ctx ctx) {
/*
		$text = $this->getText( 'text', null, '/\S+/' );
		if ( $text === null ) {
			$text = $this->counter
				?: CoordFormatter::format( $this->lat, $this->lon, $this->getLanguage() );
		}
		$text = $this->parser->recursiveTagParse( $text, $this->frame );
*/
		if (this.text == null) {
			//if (this.firstMarker == null)
			//    this.text = CoordFormatter( this.lat, this.lon, this.getLanguage() );
			//else
			this.text = this.firstMarker;
		}
		if (text != null && text.length > 2) {
			text = Bry_.Replace(text, Byte_ascii.Underline, Byte_ascii.Space); // eek!!!????
			Xop_root_tkn root = ctx.Tkn_mkr().Root(text);
			Xop_parser parser = ctx.Wiki().Parser_mgr().Main();
			parser.Parse_wtxt_to_wdom(root, ctx, ctx.App().Parser_mgr().Tkn_mkr(), text, Xop_parser_.Doc_bgn_bos);
			Bry_bfr tmp_bfr = Bry_bfr_.New();
			ctx.Wiki().Html_mgr().Html_wtr().Write_doc(tmp_bfr, ctx, Xoh_wtr_ctx.Basic, text, root);
			if (tmp_bfr.Bfr()[0] == '<' && tmp_bfr.Bfr()[1] == 'p') {
				tmp_bfr.Delete_rng_to_bgn(3); // remove <p>
				tmp_bfr.Delete_rng_to_end(-6); // remove \n</p>\n
			}
			text = tmp_bfr.To_bry_and_clear_and_rls();
			//text = Bry_.Replace(text, Byte_ascii.Underline, Byte_ascii.Space);
		}
                
		byte[] klass = Bry_.new_a7("mw-kartographer-maplink");
		byte[] attrs = Bry_.Add(
		Bry_.new_a7(" data-mw=\"interface\""),
		Bry_.new_a7(" data-style=\""), this.mapstyle,
		//Bry_.new_a7("\" href=\""), SpecialMap_link( this.lat, this.lon, this.zoom ),
		Bry_.new_a7("\""));
/*
		if ( $this->zoom !== null ) {
			$attrs['data-zoom'] = $this->zoom;
		}
*/
		if (this.zoom != null)
			attrs = Bry_.Add(attrs, Bry_.new_a7(" data-zoom=\""), this.zoom, Bry_.new_a7("\""));
/*
		if ( $this->lat !== null && $this->lon !== null ) {
			$attrs['data-lat'] = $this->lat;
			$attrs['data-lon'] = $this->lon;
		}
*/
		if ( this.lat != null && this.lon != null ) {
			attrs = Bry_.Add(attrs, 
				Bry_.new_a7(" data-lat=\""), this.lat,
				Bry_.new_a7("\" data-lon=\""), this.lon, Bry_.new_a7("\""));
		}
/*
		$style = $this->extractMarkerCss();
		if ( $style ) {
			$attrs['class'] .= ' mw-kartographer-autostyled';
			$attrs['style'] = $style;
		}
		if ( $this->cssClass !== '' ) {
			$attrs['class'] .= ' ' . $this->cssClass;
		}
*/
		byte[] style = extractMarkerCss();
		if (style != null) {
			klass = Bry_.Add(klass, Bry_.new_a7(" mw-kartographer-autostyled"));
			attrs = Bry_.Add(attrs, Bry_.new_a7(" style=\""), style, Bry_.new_a7("\""));
		}
		if (this.klass != null) {
			klass = Bry_.Add(klass, Bry_.new_a7(" "), this.klass);
		}
/*
		if ( $this->showGroups ) {
			$attrs['data-overlays'] = FormatJson::encode( $this->showGroups, false,
				FormatJson::ALL_OK );
		}
*/
		Fmt__link.Bld_many(bfr, attrs, klass, text);
	}
	private static final	Bry_fmt
	  Fmt__link = Bry_fmt.Auto_nl_skip_last
	( ""
	, "<a ~{attrs} class=\"~{class}\">~{text}</a>"
	);
}
