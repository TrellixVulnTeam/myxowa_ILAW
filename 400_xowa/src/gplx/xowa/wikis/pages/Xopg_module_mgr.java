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
package gplx.xowa.wikis.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.bits.*;
public class Xopg_module_mgr {
	public boolean Math_exists() {return math_exists;} public void Math_exists_(boolean v) {math_exists = v;} private boolean math_exists;
	public boolean Imap_exists() {return imap_exists;} public void Imap_exists_(boolean v) {imap_exists = v;} private boolean imap_exists;
	public boolean Gallery_packed_exists() {return gallery_packed_exists;} public void Gallery_packed_exists_(boolean v) {gallery_packed_exists = v;} private boolean gallery_packed_exists;
	public boolean Hiero_exists() {return hiero_exists;} public void Hiero_exists_(boolean v) {hiero_exists = v;} private boolean hiero_exists;
	public boolean Graph2_exists() {return graph2_exists;} public void Graph2_exists_(boolean v) {graph2_exists = v;} private boolean graph2_exists;
	public boolean Graph1_exists() {return graph1_exists;} public void Graph1_exists_(boolean v) {graph1_exists = v;} private boolean graph1_exists;
	public void Init(boolean math, boolean imap, boolean packed, boolean hiero, int graph) {
		this.math_exists = math;
		this.imap_exists = imap;
		this.gallery_packed_exists = packed;
		this.hiero_exists = hiero;
		if ((graph & 2) == 2)
			this.graph2_exists = true;
		if ((graph & 1) == 1)
			this.graph1_exists = true;
	}
	public int Flag() {return Calc_flag(math_exists, imap_exists, gallery_packed_exists, hiero_exists, graph2_exists, graph1_exists);}
	public void Flag_(int v) {
		this.math_exists			= Bitmask_.Has_int(v, Tid_math);
		this.imap_exists			= Bitmask_.Has_int(v, Tid_imap);
		this.gallery_packed_exists	= Bitmask_.Has_int(v, Tid_packed);
		this.hiero_exists			= Bitmask_.Has_int(v, Tid_hiero);
		this.graph2_exists = Bitmask_.Has_int(v, Tid_graph2);
		this.graph1_exists = Bitmask_.Has_int(v, Tid_graph1);
	}
	public void Clear() {
		math_exists = imap_exists = gallery_packed_exists = hiero_exists = graph2_exists = graph1_exists = false;
	}
	private static int Calc_flag(boolean math, boolean imap, boolean packed, boolean hiero, boolean graph2, boolean graph1) {
		int rv = 0;
		if (math)			rv = Bitmask_.Add_int(rv, Tid_math);
		if (imap)			rv = Bitmask_.Add_int(rv, Tid_imap);
		if (packed)			rv = Bitmask_.Add_int(rv, Tid_packed);
		if (hiero)			rv = Bitmask_.Add_int(rv, Tid_hiero);
		if (graph2)			rv = Bitmask_.Add_int(rv, Tid_graph2);
		if (graph1)			rv = Bitmask_.Add_int(rv, Tid_graph1);
		return rv;
	}
	private static final int		// SERIALIZED; only supports 8 different types
	  Tid_math		= 1
	, Tid_imap		= 2
	, Tid_packed	= 4
	, Tid_hiero		= 8
	, Tid_graph2		= 16
	, Tid_graph1		= 32
	;
	public static final int Tid_null = 0;
}
