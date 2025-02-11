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
package gplx.xowa.mediawiki.extensions.Wikibase.lib.includes.Store; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*;
// REF.WBASE:2020-01-19
/**
* Interface that contains method for the PropertyOrderProvider
*
* @license GPL-2.0-or-later
* @author Lucie-Aim�e Kaffee
*/
public interface XomwPropertyOrderProvider {

	/**
	* Get order of properties in the form [ $propertyIdSerialization => $ordinalNumber ]
	*
	* @return null|int[] An associative array mapping property ID strings to ordinal numbers.
	* 	The order of properties is represented by the ordinal numbers associated with them.
	* 	The array is not guaranteed to be sorted.
	* 	Null if no information exists.
	* @throws PropertyOrderProviderException
	*/
	XophpArray getPropertyOrder();
}
