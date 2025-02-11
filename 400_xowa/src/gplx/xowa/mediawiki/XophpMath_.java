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
package gplx.xowa.mediawiki; import gplx.*; import gplx.xowa.*;
public class XophpMath_ {
	public static double round(double v, int places) {
		if (places < 0) {	// -1 means round to 10; -2 means round to 100; etc..
			int factor = (int)Math_.Pow(10, places * -1);
			return ((int)(Math_.Round(v, 0) / factor)) * factor;	// EX: ((int)Round(123, 0) / 10) * 10: 123 -> 12.3 -> 12 -> 120
		}
		else {
			return Math_.Round(v, places);
		}
	}
	public static int min(int lhs, int rhs) {
		return Math_.Min(lhs, rhs);
	}
	public static int min_many(int... ary) {
		int rv = Int_.Max_value;
		for (int itm : ary) {
			if (itm < rv)
				rv = itm;
		}
		return rv;
	}
	public static int max_many(int... ary) {
		int rv = Int_.Min_value;
		for (int itm : ary) {
			if (itm > rv)
				rv = itm;
		}
		return rv;
	}
	// REF.PHP:https://www.php.net/manual/en/function.fmod.php
	public static Decimal_adp fmod_decimal(Decimal_adp lhs, Decimal_adp rhs) {return Decimal_adp_.double_(fmod(lhs.To_double(), rhs.To_double()));}
	public static double fmod(double lhs, double rhs) {
		return (double)lhs % (double)rhs;
	}
}
