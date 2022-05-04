package jp.hishidama.net;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

/**
 * URIユーティリティー.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/uri.html">ひしだま</a>
 * @since 2010.01.22
 */
public class URIUtil {

	/**
	 * URI生成.
	 * <p>
	 * パス・クエリー・部分識別子（fragment）にスペース等の不正な文字が入っている場合もURIインスタンスを生成する。
	 * </p>
	 *
	 * @param str
	 *            URI に解析される文字列（null不可）
	 * @return URI（必ずnull以外）
	 * @throws URISyntaxException
	 *             それでもURIが生成できなかった場合
	 * @see URI#URI(String)
	 */
	public static URI create(String str) throws URISyntaxException {
		try {
			return new URI(str);
		} catch (URISyntaxException e) {
			if (str.startsWith("#")) {
				return new URI(null, null, str.substring(1));
			}
			return create(str, " %^`|,\\\"{}<>[]", 0, e);
		}
	}

	protected static URI create(String str, String illegals, int n,
			URISyntaxException e) throws URISyntaxException {
		char c = 'x';
		while (n < illegals.length()) {
			c = illegals.charAt(n++);
			if (str.indexOf(c) >= 0) {
				break;
			}
		}
		if (c == 'x') {
			throw e;
		}

		String esc = String.format("-%02x-", (int) c);
		while (str.indexOf(esc) >= 0) {
			esc = "-" + esc + "-";
		}
		String reg = String.valueOf(c);
		str = str.replaceAll("\\" + reg, esc);

		URI uri;
		try {
			uri = new URI(str);
		} catch (URISyntaxException e2) {
			return create(str, illegals, n, e2);
		}
		String scheme = replaceAll(uri.getScheme(), esc, reg);
		if (scheme != null && scheme.indexOf(reg) >= 0) {
			throw e;
		}
		String fragment = replaceAll(uri.getRawFragment(), esc, reg);
		if (uri.isOpaque()) {
			String ssp = replaceAll(uri.getRawSchemeSpecificPart(), esc, reg);
			try {
				return new URI(scheme, ssp, fragment);
			} catch (URISyntaxException e2) {
				return create(str, illegals, n, e2);
			}
		}
		String user = replaceAll(uri.getRawUserInfo(), esc, reg);
		String host = replaceAll(uri.getHost(), esc, " ");
		if (host != null && host.indexOf(reg) >= 0) {
			throw e;
		}
		int port = uri.getPort();
		String path = replaceAll(uri.getRawPath(), esc, reg);
		String query = replaceAll(uri.getRawQuery(), esc, reg);
		try {
			return new URI(scheme, user, host, port, path, query, fragment);
		} catch (URISyntaxException e2) {
			return create(str, illegals, n, e2);
		}
	}

	protected static String replaceAll(String str, String regex, String repl) {
		if (str == null) {
			return null;
		}
		if (repl.equals("\\") || repl.equals("$")) {
			repl = "\\" + repl;
		}
		try {
			return Pattern.compile(regex).matcher(str).replaceAll(repl);
		} catch (RuntimeException e) {
			System.err.println("[" + str + "]");
			System.err.println("[" + regex + "]");
			System.err.println("[" + repl + "]");
			throw e;
		}
	}

	/**
	 * 指定されたURIの絶対URIを返す。
	 * <p>
	 * uriが絶対URIの場合は、normalize()してそのまま返す。<br>
	 * uriが相対URIの場合は、ファイルの基準ディレクトリーからの相対パスを元にURIを生成する。
	 * </p>
	 *
	 * @param base
	 *            基準URI（null不可。絶対URIであること）
	 * @param dir
	 *            基準ディレクトリー（null不可）
	 * @param f
	 *            ファイル（null不可）
	 * @param uri
	 *            対象URI
	 * @return URI（必ずnull以外）
	 */
	public static URI resolveFile(URI base, File dir, File f, URI uri) {
		if (uri.isAbsolute()) {
			return uri.normalize();
		}
		URI fu = resolveFile(base, dir, f);
		return fu.resolve(uri); // normalize()されている
	}

	/**
	 * 指定されたファイルのURIを返す。
	 * <p>
	 * ファイルの基準ディレクトリーからの相対パスを元に、基準URIからURIを生成する。
	 * </p>
	 *
	 * @param base
	 *            基準URI（null不可）
	 * @param dir
	 *            基準ディレクトリー（null不可）
	 * @param f
	 *            ファイル（null不可）
	 * @return URI（必ずnull以外）
	 */
	public static URI resolveFile(URI base, File dir, File f) {
		// URI fru = dir.toURI().relativize(f.toURI());

		// File#toURI()は、存在しないディレクトリーの場合は末尾に「/」が付かない。
		// 当メソッドではdirはディレクトリーという想定なので、「/」が付いてなかったら付ける。
		URI du = dir.toURI();
		if (!du.getPath().endsWith("/")) {
			try {
				// see File#toURI()
				du = new URI(du.getScheme(), du.getHost(), du.getPath() + "/",
						du.getFragment());
			} catch (URISyntaxException e) {
				// 例外は発生しないはず
			}
		}

		URI fru = relativize(du, f.toURI());
		return base.resolve(fru);
	}

	/**
	 * 指定されたURIの、基準URIからの相対パスを返す。
	 *<p>
	 * {@link URI#relativize(URI)}は 基準URIが対象URIの一部になっている場合しか相対化できないが、
	 * 当メソッドでは「../」を付加するので、パスの先頭部分が一致していれば使える。
	 * </p>
	 *
	 * @param base
	 *            基準URI（null不可。normalize()されていること）
	 * @param uri
	 *            相対化するURI（null不可。normalize()されていること）
	 * @return URI（必ずnull以外）（相対化できなかった場合は元のuri）
	 */
	public static URI relativize(URI base, URI uri) {
		if (!equalsIgnoreCase(base.getScheme(), uri.getScheme())) {
			return uri;
		}
		if (!equals(base.getRawAuthority(), uri.getRawAuthority())) {
			return uri;
		}

		// fragment以外が全く同じ場合
		if (uri.getFragment() != null
				&& URIUtil.setFragment(base, uri.getFragment()).equals(uri)) {
			try {
				return new URI(null, null, uri.getFragment());
			} catch (URISyntaxException e) {
				// through
			}
		}

		String rel = relativizePath(base.getPath(), uri.getPath());
		if (rel == null) {
			return uri;
		}
		try {
			return new URI(null, null, null, -1, rel, uri.getQuery(), uri
					.getFragment());
		} catch (URISyntaxException e) {
			return uri;
		}
	}

	protected static boolean equals(String s1, String s2) {
		if (s1 == null) {
			return s2 == null;
		} else {
			return s1.equals(s2);
		}
	}

	protected static boolean equalsIgnoreCase(String s1, String s2) {
		if (s1 == null) {
			return s2 == null;
		} else {
			return s1.equalsIgnoreCase(s2);
		}
	}

	/**
	 * 指定されたパスの、基準パスからの相対パスを返す。
	 *
	 * @param base
	 *            基準パス
	 * @param path
	 *            相対化するパス
	 * @return 相対パス（一致している部分が全く無かった場合、あるいはbaseやpathがnullの場合はnull）
	 */
	public static String relativizePath(String base, String path) {
		int len = getEqualsPathLength(base, path);
		if (len < 0) { // 一致している部分が無い
			return null;
		}
		int c = getSlashCount(base, len);
		if (c > 0) {
			StringBuilder sb = new StringBuilder(3 * c + path.length());
			for (int i = 0; i < c; i++) {
				sb.append("../");
			}
			sb.append(path, len, path.length());
			return sb.toString();
		} else {
			return path.substring(len);
		}
	}

	/**
	 * 2つのパスの中で、先頭から一致している範囲（パス）の文字数を返す。
	 *
	 * @param path1
	 *            パス
	 * @param path2
	 *            パス
	 * @return 文字数（path1あるいはpath2がnullの場合は負数）
	 */
	public static int getEqualsPathLength(String path1, String path2) {
		if (path1 == null || path2 == null) {
			return -1;
		}
		int n = -1;
		int m = Math.min(path1.length(), path2.length());
		int i;
		for (i = 0; i < m; i++) {
			char c1 = path1.charAt(i);
			char c2 = path2.charAt(i);
			if (c1 != c2) {
				break;
			}
			if (c1 == '/') {
				n = i;
			}
		}
		if (i == m) {
			// 片方が他方に完全に含まれる場合、長い方が/かどうか
			String s = null;
			if (path1.length() > m) {
				s = path1;
			} else if (path2.length() > m) {
				s = path2;
			}
			if (s != null) {
				if (s.charAt(m) == '/') {
					n = m;
				}
			}
		}
		return n + 1;
	}

	/**
	 * スラッシュの個数を返す。
	 *
	 * @param path
	 *            パス（null不可）
	 * @param start
	 *            検索を始める位置（0以上）
	 * @return 個数
	 */
	public static int getSlashCount(String path, int start) {
		int n = 0;
		int m = path.length();
		for (int i = start; i < m; i++) {
			if (path.charAt(i) == '/') {
				n++;
			}
		}
		return n;
	}

	/**
	 * 部分識別子（fragment）のみかどうか
	 *
	 * @param uri
	 *            URI（null不可）
	 * @return true：部分識別子のみ
	 */
	public static boolean isFragmentOnly(URI uri) {
		if (uri.getFragment() == null) {
			return false;
		}
		String ssp = uri.getRawSchemeSpecificPart();
		if (ssp == null || ssp.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * スキーマからパスまでのURIを返す。
	 * <p>
	 * クエリーおよび部分識別子（fragment）を除去したURIを返す。<br>
	 * クエリーも部分識別子も無いURIだった場合は、対象URIそのものを返す。
	 * </p>
	 * <p>
	 * 注意：{@code mailto:user@host?query }という形式の場合、「?query」はクエリーとして認識されない。
	 * すなわち「?query」が付いたままのURIが返る。
	 * </p>
	 *
	 * @param uri
	 *            対象URI（null不可）
	 * @return URI（必ずnull以外）
	 */
	public static URI getSchemePath(URI uri) {
		String query = uri.getQuery();
		if (query == null && uri.getFragment() == null) {
			return uri;
		}
		try {
			if (query == null) {
				return new URI(uri.getScheme(), uri.getSchemeSpecificPart(),
						null);
			} else {
				return new URI(uri.getScheme(), uri.getUserInfo(), uri
						.getHost(), uri.getPort(), uri.getPath(), null, null);
			}
		} catch (URISyntaxException e) {
			return uri; // 基本的にはエラーにならないはず
		}
	}

	/**
	 * スキーマからパスまでのURIを返す。
	 * <p>
	 * クエリーおよび部分識別子（fragment）を除去したURIを返す。<br>
	 * クエリーも部分識別子も無いURIだった場合は、対象URIそのものを返す。
	 * </p>
	 * <p>
	 * 注意：{@code mailto:user@host?query }という形式の場合、「?query」はクエリーとして認識されない。
	 * すなわち「?query」が付いたままのURIが返る。
	 * </p>
	 *
	 * @param uri
	 *            対象URI（null不可）
	 * @return URI（必ずnull以外）
	 */
	public static URI getRawSchemePath(URI uri) {
		String query = uri.getRawQuery();
		if (query == null && uri.getRawFragment() == null) {
			return uri;
		}
		try {
			if (query == null) {
				return new URI(uri.getScheme(), uri.getRawSchemeSpecificPart(),
						null);
			} else {
				return new URI(uri.getScheme(), uri.getRawUserInfo(), uri
						.getHost(), uri.getPort(), uri.getRawPath(), null, null);
			}
		} catch (URISyntaxException e) {
			return uri; // 基本的にはエラーにならないはず
		}
	}

	/**
	 * スキーマからクエリーまでのURIを返す。
	 * <p>
	 * 部分識別子（fragment）を除去したURIを返す。<br>
	 * 部分識別子の無いURIだった場合は、対象URIそのものを返す。
	 * </p>
	 *
	 * @param uri
	 *            対象URI（null不可）
	 * @return URI（必ずnull以外）
	 */
	public static URI getSchemeQuery(URI uri) {
		if (uri.getFragment() == null) {
			return uri;
		}
		try {
			return new URI(uri.getScheme(), uri.getSchemeSpecificPart(), null);
		} catch (URISyntaxException e) {
			return uri; // 基本的にはエラーにならないはず
		}
	}

	/**
	 * スキーマからクエリーまでのURIを返す。
	 * <p>
	 * 部分識別子（fragment）を除去したURIを返す。<br>
	 * 部分識別子の無いURIだった場合は、対象URIそのものを返す。
	 * </p>
	 *
	 * @param uri
	 *            対象URI（null不可）
	 * @return URI（必ずnull以外）
	 */
	public static URI getRawSchemeQuery(URI uri) {
		if (uri.getFragment() == null) {
			return uri;
		}
		try {
			return new URI(uri.getScheme(), uri.getRawSchemeSpecificPart(),
					null);
		} catch (URISyntaxException e) {
			return uri; // 基本的にはエラーにならないはず
		}
	}

	/**
	 * スキーマをセットしたURIを返す。
	 *
	 * @param uri
	 *            URI（null不可）
	 * @param schema
	 *            置換するスキーマ
	 * @return 変換されたURI（必ずnull以外）
	 */
	public static URI setSchema(URI uri, String schema) {
		try {
			return new URI(schema, uri.getRawSchemeSpecificPart(), uri
					.getRawFragment());
		} catch (URISyntaxException e) {
			return uri;
		}
	}

	/**
	 * パスをセットしたURIを返す。
	 * <p>
	 * 不透明なURI（パス部の無いURI）の場合は、変換せずに引数のURIをそのまま返す。
	 * </p>
	 *
	 * @param uri
	 *            URI（null不可）
	 * @param path
	 *            置換するパス
	 * @return 変換されたURI（必ずnull以外）
	 * @see URI#isOpaque()
	 */
	public static URI setPath(URI uri, String path) {
		if (uri.isOpaque()) {
			return uri;
		}
		try {
			return new URI(uri.getScheme(), uri.getRawUserInfo(),
					uri.getHost(), uri.getPort(), path, uri.getRawQuery(), uri
							.getRawFragment());
		} catch (URISyntaxException e) {
			return uri;
		}
	}

	/**
	 * 部分識別子（fragment）をセットしたURIを返す。
	 *
	 * @param uri
	 *            URI（null不可）
	 * @param fragment
	 *            置換する部分識別子
	 * @return 変換されたURI（必ずnull以外）
	 */
	public static URI setFragment(URI uri, String fragment) {
		try {
			return new URI(uri.getScheme(), uri.getRawSchemeSpecificPart(),
					fragment);
		} catch (URISyntaxException e) {
			return uri; // 基本的にはエラーにならないはず
		}
	}
}
