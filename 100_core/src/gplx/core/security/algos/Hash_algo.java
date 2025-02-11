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
package gplx.core.security.algos; import gplx.*; import gplx.core.security.*;
public interface Hash_algo {// THREAD.UNSAFE
	String Key();
	void   Update_digest(byte[] src, int bgn, int end);
	byte[] To_hash_bry();
	Hash_algo Clone_hash_algo(); // factory method; note that MessageDigest's are member variables, so always create a new instance
}
