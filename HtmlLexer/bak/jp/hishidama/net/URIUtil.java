package jp.hishidama.net;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

/**
 * URI���[�e�B���e�B�[.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/uri.html">�Ђ�����</a>
 * @since 2010.01.22
 */
public class URIUtil {

	/**
	 * URI����.
	 * <p>
	 * �p�X�E�N�G���[�E�������ʎq�ifragment�j�ɃX�y�[�X���̕s���ȕ����������Ă���ꍇ��URI�C���X�^���X�𐶐�����B
	 * </p>
	 *
	 * @param str
	 *            URI �ɉ�͂���镶����inull�s�j
	 * @return URI�i�K��null�ȊO�j
	 * @throws URISyntaxException
	 *             ����ł�URI�������ł��Ȃ������ꍇ
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
	 * �w�肳�ꂽURI�̐��URI��Ԃ��B
	 * <p>
	 * uri�����URI�̏ꍇ�́Anormalize()���Ă��̂܂ܕԂ��B<br>
	 * uri������URI�̏ꍇ�́A�t�@�C���̊�f�B���N�g���[����̑��΃p�X������URI�𐶐�����B
	 * </p>
	 *
	 * @param base
	 *            �URI�inull�s�B���URI�ł��邱�Ɓj
	 * @param dir
	 *            ��f�B���N�g���[�inull�s�j
	 * @param f
	 *            �t�@�C���inull�s�j
	 * @param uri
	 *            �Ώ�URI
	 * @return URI�i�K��null�ȊO�j
	 */
	public static URI resolveFile(URI base, File dir, File f, URI uri) {
		if (uri.isAbsolute()) {
			return uri.normalize();
		}
		URI fu = resolveFile(base, dir, f);
		return fu.resolve(uri); // normalize()����Ă���
	}

	/**
	 * �w�肳�ꂽ�t�@�C����URI��Ԃ��B
	 * <p>
	 * �t�@�C���̊�f�B���N�g���[����̑��΃p�X�����ɁA�URI����URI�𐶐�����B
	 * </p>
	 *
	 * @param base
	 *            �URI�inull�s�j
	 * @param dir
	 *            ��f�B���N�g���[�inull�s�j
	 * @param f
	 *            �t�@�C���inull�s�j
	 * @return URI�i�K��null�ȊO�j
	 */
	public static URI resolveFile(URI base, File dir, File f) {
		// URI fru = dir.toURI().relativize(f.toURI());

		// File#toURI()�́A���݂��Ȃ��f�B���N�g���[�̏ꍇ�͖����Ɂu/�v���t���Ȃ��B
		// �����\�b�h�ł�dir�̓f�B���N�g���[�Ƃ����z��Ȃ̂ŁA�u/�v���t���ĂȂ�������t����B
		URI du = dir.toURI();
		if (!du.getPath().endsWith("/")) {
			try {
				// see File#toURI()
				du = new URI(du.getScheme(), du.getHost(), du.getPath() + "/",
						du.getFragment());
			} catch (URISyntaxException e) {
				// ��O�͔������Ȃ��͂�
			}
		}

		URI fru = relativize(du, f.toURI());
		return base.resolve(fru);
	}

	/**
	 * �w�肳�ꂽURI�́A�URI����̑��΃p�X��Ԃ��B
	 *<p>
	 * {@link URI#relativize(URI)}�� �URI���Ώ�URI�̈ꕔ�ɂȂ��Ă���ꍇ�������Ή��ł��Ȃ����A
	 * �����\�b�h�ł́u../�v��t������̂ŁA�p�X�̐擪��������v���Ă���Ύg����B
	 * </p>
	 *
	 * @param base
	 *            �URI�inull�s�Bnormalize()����Ă��邱�Ɓj
	 * @param uri
	 *            ���Ή�����URI�inull�s�Bnormalize()����Ă��邱�Ɓj
	 * @return URI�i�K��null�ȊO�j�i���Ή��ł��Ȃ������ꍇ�͌���uri�j
	 */
	public static URI relativize(URI base, URI uri) {
		if (!equalsIgnoreCase(base.getScheme(), uri.getScheme())) {
			return uri;
		}
		if (!equals(base.getRawAuthority(), uri.getRawAuthority())) {
			return uri;
		}

		// fragment�ȊO���S�������ꍇ
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
	 * �w�肳�ꂽ�p�X�́A��p�X����̑��΃p�X��Ԃ��B
	 *
	 * @param base
	 *            ��p�X
	 * @param path
	 *            ���Ή�����p�X
	 * @return ���΃p�X�i��v���Ă��镔�����S�����������ꍇ�A���邢��base��path��null�̏ꍇ��null�j
	 */
	public static String relativizePath(String base, String path) {
		int len = getEqualsPathLength(base, path);
		if (len < 0) { // ��v���Ă��镔��������
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
	 * 2�̃p�X�̒��ŁA�擪�����v���Ă���͈́i�p�X�j�̕�������Ԃ��B
	 *
	 * @param path1
	 *            �p�X
	 * @param path2
	 *            �p�X
	 * @return �������ipath1���邢��path2��null�̏ꍇ�͕����j
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
			// �Е��������Ɋ��S�Ɋ܂܂��ꍇ�A��������/���ǂ���
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
	 * �X���b�V���̌���Ԃ��B
	 *
	 * @param path
	 *            �p�X�inull�s�j
	 * @param start
	 *            �������n�߂�ʒu�i0�ȏ�j
	 * @return ��
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
	 * �������ʎq�ifragment�j�݂̂��ǂ���
	 *
	 * @param uri
	 *            URI�inull�s�j
	 * @return true�F�������ʎq�̂�
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
	 * �X�L�[�}����p�X�܂ł�URI��Ԃ��B
	 * <p>
	 * �N�G���[����ѕ������ʎq�ifragment�j����������URI��Ԃ��B<br>
	 * �N�G���[���������ʎq������URI�������ꍇ�́A�Ώ�URI���̂��̂�Ԃ��B
	 * </p>
	 * <p>
	 * ���ӁF{@code mailto:user@host?query }�Ƃ����`���̏ꍇ�A�u?query�v�̓N�G���[�Ƃ��ĔF������Ȃ��B
	 * ���Ȃ킿�u?query�v���t�����܂܂�URI���Ԃ�B
	 * </p>
	 *
	 * @param uri
	 *            �Ώ�URI�inull�s�j
	 * @return URI�i�K��null�ȊO�j
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
			return uri; // ��{�I�ɂ̓G���[�ɂȂ�Ȃ��͂�
		}
	}

	/**
	 * �X�L�[�}����p�X�܂ł�URI��Ԃ��B
	 * <p>
	 * �N�G���[����ѕ������ʎq�ifragment�j����������URI��Ԃ��B<br>
	 * �N�G���[���������ʎq������URI�������ꍇ�́A�Ώ�URI���̂��̂�Ԃ��B
	 * </p>
	 * <p>
	 * ���ӁF{@code mailto:user@host?query }�Ƃ����`���̏ꍇ�A�u?query�v�̓N�G���[�Ƃ��ĔF������Ȃ��B
	 * ���Ȃ킿�u?query�v���t�����܂܂�URI���Ԃ�B
	 * </p>
	 *
	 * @param uri
	 *            �Ώ�URI�inull�s�j
	 * @return URI�i�K��null�ȊO�j
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
			return uri; // ��{�I�ɂ̓G���[�ɂȂ�Ȃ��͂�
		}
	}

	/**
	 * �X�L�[�}����N�G���[�܂ł�URI��Ԃ��B
	 * <p>
	 * �������ʎq�ifragment�j����������URI��Ԃ��B<br>
	 * �������ʎq�̖���URI�������ꍇ�́A�Ώ�URI���̂��̂�Ԃ��B
	 * </p>
	 *
	 * @param uri
	 *            �Ώ�URI�inull�s�j
	 * @return URI�i�K��null�ȊO�j
	 */
	public static URI getSchemeQuery(URI uri) {
		if (uri.getFragment() == null) {
			return uri;
		}
		try {
			return new URI(uri.getScheme(), uri.getSchemeSpecificPart(), null);
		} catch (URISyntaxException e) {
			return uri; // ��{�I�ɂ̓G���[�ɂȂ�Ȃ��͂�
		}
	}

	/**
	 * �X�L�[�}����N�G���[�܂ł�URI��Ԃ��B
	 * <p>
	 * �������ʎq�ifragment�j����������URI��Ԃ��B<br>
	 * �������ʎq�̖���URI�������ꍇ�́A�Ώ�URI���̂��̂�Ԃ��B
	 * </p>
	 *
	 * @param uri
	 *            �Ώ�URI�inull�s�j
	 * @return URI�i�K��null�ȊO�j
	 */
	public static URI getRawSchemeQuery(URI uri) {
		if (uri.getFragment() == null) {
			return uri;
		}
		try {
			return new URI(uri.getScheme(), uri.getRawSchemeSpecificPart(),
					null);
		} catch (URISyntaxException e) {
			return uri; // ��{�I�ɂ̓G���[�ɂȂ�Ȃ��͂�
		}
	}

	/**
	 * �X�L�[�}���Z�b�g����URI��Ԃ��B
	 *
	 * @param uri
	 *            URI�inull�s�j
	 * @param schema
	 *            �u������X�L�[�}
	 * @return �ϊ����ꂽURI�i�K��null�ȊO�j
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
	 * �p�X���Z�b�g����URI��Ԃ��B
	 * <p>
	 * �s������URI�i�p�X���̖���URI�j�̏ꍇ�́A�ϊ������Ɉ�����URI�����̂܂ܕԂ��B
	 * </p>
	 *
	 * @param uri
	 *            URI�inull�s�j
	 * @param path
	 *            �u������p�X
	 * @return �ϊ����ꂽURI�i�K��null�ȊO�j
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
	 * �������ʎq�ifragment�j���Z�b�g����URI��Ԃ��B
	 *
	 * @param uri
	 *            URI�inull�s�j
	 * @param fragment
	 *            �u�����镔�����ʎq
	 * @return �ϊ����ꂽURI�i�K��null�ȊO�j
	 */
	public static URI setFragment(URI uri, String fragment) {
		try {
			return new URI(uri.getScheme(), uri.getRawSchemeSpecificPart(),
					fragment);
		} catch (URISyntaxException e) {
			return uri; // ��{�I�ɂ̓G���[�ɂȂ�Ȃ��͂�
		}
	}
}
