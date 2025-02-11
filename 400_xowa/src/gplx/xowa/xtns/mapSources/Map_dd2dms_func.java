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
package gplx.xowa.xtns.mapSources; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*;
import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Map_dd2dms_func extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_mapSources_dd2dms;}
	@Override public Pf_func New(int id, byte[] name) {return new Map_dd2dms_func().Name_(name);}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		byte[] coord = Eval_argx(ctx, src, caller, self);
		int args_len = self.Args_len();
		Bry_bfr tmp_bfr = ctx.Wiki().Utl__bfr_mkr().Get_b128();
		byte[] plus = Bry_.Empty, minus = Bry_.Empty;
		int prec = 4;
		Xop_func_arg_itm func_arg = new Xop_func_arg_itm();
		for (int i = 0; i < args_len; i++) {
			Arg_nde_tkn arg = self.Args_get_by_idx(i);
			func_arg.Set(tmp_bfr, ctx, src, caller, self, arg);
			byte[] key = func_arg.key;
			Object key_tid_obj = Key_hash.Get_by(key);
			if (key_tid_obj != null) {
				byte[] val = func_arg.val;
				switch (((Byte_obj_val)key_tid_obj).Val()) {
					case Key_tid_plus:		plus = val; break;
					case Key_tid_minus:		minus = val; break;
					case Key_tid_precision:	prec = Bry_.To_int_or(val, prec); break;
				}
			}
		}
		tmp_bfr.Mkr_rls();
		Map_math map_math = Map_math.Instance;
		if (map_math.Ctor(coord, prec, Bry_.Empty, 2, null))
			bfr.Add(map_math.Get_dms(Bool_.N, plus, minus));
		else
			map_math.Fail(ctx, src, self, bfr, this.Name());
	}
	public static void Deg_to_dms(Bry_bfr bfr, boolean wikibase, boolean coord_is_lng, byte[] coord, int prec) { // NOTE: called by wikibase
		Map_math map_math = Map_math.Instance;
		if (map_math.Ctor(coord, prec, Bry_.Empty, 2, null)) {
			bfr.Add(map_math.Get_dms(wikibase, Bry_.Empty, Bry_.Empty));
			byte[] dir = coord_is_lng ? map_math.Coord_dir_ew() : map_math.Coord_dir_ns();
			if (!wikibase)	// NOTE: do not add space if wikibase, else will fail in Module:en.w:WikidataCoord; PAGE:en.w:Hulme_Arch_Bridge DATE:2017-04-02
				bfr.Add_byte_space();
			bfr.Add(dir);
		}
	}
	public static void Deg_to_dms_lat_long(Bry_bfr bfr, boolean wikibase, byte[] coord_lat, byte[] coord_lon, int prec, byte[] glb_ttl) { // NOTE: called by wikibase
		byte[] lat = null;
		byte[] lat_dir = null;
		byte[] lon = null;
		byte[] lon_dir = null;
		//Map_math map_math = Map_math.Instance;
		Map_math map_math = new Map_math();
		if (map_math.Ctor(coord_lat, prec, Bry_.Empty, 2, glb_ttl)) {
			lat = map_math.Get_dms(wikibase, Bry_.Empty, Bry_.Empty);
			lat_dir = map_math.Coord_dir_ns();
		}
		else {
			System.out.println("Bad latitude " + Integer.toString(map_math.Error()) + " " + String_.new_u8(coord_lat));
			return; // should repor an error!
		}
		if (map_math.Ctor(coord_lon, prec, Bry_.Empty, 2, glb_ttl)) {
			lon = map_math.Get_dms(wikibase, Bry_.Empty, Bry_.Empty);
			lon_dir = map_math.Coord_dir_ew();
		}
		else {
			System.out.println("Bad longitude " + Integer.toString(map_math.Error()) + " " + String_.new_u8(coord_lon));
			return; // should repor an error!
		}
		// make sure they are the same 'length' ie DMS or DM
		byte lat_dms = lat[lat.length - 2];
		byte lon_dms = lon[lon.length - 2];
		if (lat_dms != lon_dms) {
			if (lat_dms == '4') {
				lat = reduce(lat);
			}
			else {
				lon = reduce(lon);
			}
		}
		bfr.Add(lat);
		if (!wikibase)	// NOTE: do not add space if wikibase, else will fail in Module:en.w:WikidataCoord; PAGE:en.w:Hulme_Arch_Bridge DATE:2017-04-02
			bfr.Add_byte_space();
		bfr.Add(lat_dir);
		bfr.Add_byte_comma().Add_byte_space();
		bfr.Add(lon);
		if (!wikibase)	// NOTE: do not add space if wikibase, else will fail in Module:en.w:WikidataCoord; PAGE:en.w:Hulme_Arch_Bridge DATE:2017-04-02
			bfr.Add_byte_space();
		bfr.Add(lon_dir);
	}
	private static byte[] reduce(byte[] coord) {
		int len = coord.length;
		for (int i = len - 2; i > 0; i--) {
			if (coord[i] == ';') {
				byte[] newcoord = new byte[i + 1];
				for (int j = 0; j <= i; j++)
					newcoord[j] = coord[j];
				return newcoord;
			}
		}
		return coord;
	}
	public static final    Map_dd2dms_func Instance = new Map_dd2dms_func(); Map_dd2dms_func() {}
	private static final byte Key_tid_plus = 1, Key_tid_minus = 2, Key_tid_precision = 3;
	private static final    Hash_adp_bry Key_hash = Hash_adp_bry.cs()
	.Add_str_byte("plus"		, Key_tid_plus)
	.Add_str_byte("minus"		, Key_tid_minus)
	.Add_str_byte("precision"	, Key_tid_precision)
	;
}
