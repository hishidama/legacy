package jp.hishidama.net;

import java.io.File;
import java.net.URISyntaxException;

/**
 * URI�N���X.
 * <p>
 * URI��ێ�����ȈՃN���X�B<br>
 * {@link java.net.URI java.net.URI}��
 * URI�Ƃ��ĕs���ȕ������܂�ł���ƃG���[�ɂȂ��ăC���X�^���X�𐶐��ł��Ȃ��ׁA���N���X���쐬�����B<br>
 * ��ړI��http�̕ێ��Ȃ̂ŁA���̃X�L�[�}�ɂ��Ă͂قƂ�Ǎl�����Ă��Ȃ��B
 * </p>
 * <ul>
 * <li>java.net.URI�Ƃ͈قȂ�A�C���X�^���X�������ɕs�������␮�����̃`�F�b�N�͍s��Ȃ��B</li>
 * <li>ss�́A�u//�v�܂���null�ƂȂ�z��ł���B</li>
 * <li>port�́Ajava.net.URI�ł͐��l�����A���N���X�ł͕�����ł���B���݂��Ȃ��ꍇ��-1�łȂ�null�ƂȂ�B</li>
 * <li>authority��schemeSpecificPart���̂��͕̂ێ����Ȃ��B
 * �umailto:�v��ustring:�v�ł����ɓ����镔���́Ahost����path���ɕێ�����邱�ƂɂȂ�Ǝv���B</li>
 * </ul>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/uri.html">�Ђ�����</a>
 * @since 2010.02.06
 */
public class URI implements Comparable<URI> {

	protected String scheme;

	protected String ss;
	protected String user;
	protected String host;
	protected String port;

	protected String path;
	protected String query;
	protected String fragment;

	/**
	 * �f�t�H���g�R���X�g���N�^�[.
	 */
	protected URI() {
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param scheme
	 *            �X�L�[�}�inull�j
	 * @param ss
	 *            �X�L�[�}����i�X�L�[�}�ŗL�����̐擪�j�́u//�v�i�����ꍇ��null�j
	 * @param user
	 *            ���[�U�[���inull�j
	 * @param host
	 *            �z�X�g���inull�j
	 * @param port
	 *            �|�[�g�inull�j
	 * @param path
	 *            �p�X�inull�j
	 * @param query
	 *            �N�G���[�inull�j
	 * @param fragment
	 *            �������ʎq�inull�j
	 */
	public URI(String scheme, String ss, String user, String host, String port,
			String path, String query, String fragment) {
		this.scheme = scheme;
		this.ss = ss;
		this.user = user;
		this.host = host;
		this.port = port;
		this.path = path;
		this.query = query;
		this.fragment = fragment;
	}

	/**
	 * �R���X�g���N�^�[.
	 * <p>
	 * {@link java.net.URI}���瓖�C���X�^���X�𐶐�����B
	 * </p>
	 *
	 * @param uri
	 *            java.net.URI
	 */
	public URI(java.net.URI uri) {
		this.scheme = uri.getScheme();
		if (uri.isOpaque()) {
			String str = uri.getRawSchemeSpecificPart();
			if (str != null) {
				new Parser(str).parseSsp();
			}
		} else {
			String str = uri.getRawSchemeSpecificPart();
			if (str != null && str.startsWith("//")) {
				this.ss = "//";
			}
			this.user = uri.getRawUserInfo();
			this.host = uri.getHost();
			int port = uri.getPort();
			if (port >= 0) {
				this.port = Integer.toString(port);
			}
			this.path = uri.getRawPath();
			this.query = uri.getRawQuery();
		}
		this.fragment = uri.getRawFragment();
	}

	/**
	 * URI�C���X�^���X����.
	 *
	 * @param uri
	 *            java.net.URI
	 * @return URI�i�K��null�ȊO�j
	 */
	public static URI valueOf(java.net.URI uri) {
		return new URI(uri);
	}

	protected URI(File f) {
		this(f.toURI());
	}

	protected URI(File f, boolean isDirectory) {
		this(f.toURI());

		// File#toURI()�́A���݂��Ȃ��f�B���N�g���[�̏ꍇ�͖����Ɂu/�v���t���Ȃ��B
		// �f�B���N�g���[�w��ł���΁A�u/�v���t���ĂȂ�������t����B
		if (path.endsWith("/")) {
			if (!isDirectory) {
				path = path.substring(0, path.length() - 1);
			}
		} else {
			if (isDirectory) {
				path += "/";
			}
		}
	}

	/**
	 * URI�C���X�^���X����.
	 * <p>
	 * �w�肳�ꂽ�t�@�C�����t�@�C���V�X�e����Ɏ��݂��Ă���f�B���N�g���[�̏ꍇ�A�������ꂽURI�̃p�X�̖����Ɂu/�v���t���B<br>
	 * ���[�U�[�̑z�肪�ufile�̓f�B���N�g���[�v���Ƃ��Ă��A���݂��Ă��Ȃ��ꍇ�́u/�v�͕t���Ȃ��B
	 * </p>
	 *
	 * @param file
	 *            �t�@�C���܂��̓f�B���N�g���[�inull�s�j
	 * @return URI�i�K��null�ȊO�j
	 */
	public static URI valueOf(File file) {
		return new URI(file);
	}

	/**
	 * URI�C���X�^���X����.
	 * <p>
	 * isDirectory��true�̏ꍇ�́A�������ꂽURI�̃p�X�̖����Ɂu/�v���t���Bfalse�̏ꍇ�́u/�v�͕t���Ȃ��B
	 * </p>
	 *
	 * @param file
	 *            �t�@�C���܂��̓f�B���N�g���[�inull�s�j
	 * @param isDirectory
	 *            true�̏ꍇ�f�B���N�g���[�Afalse�̏ꍇ�t�@�C���Ƃ��Ĉ���
	 * @return URI�i�K��null�ȊO�j
	 */
	public static URI valueOf(File file, boolean isDirectory) {
		return new URI(file, isDirectory);
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param str
	 *            URI������
	 */
	public URI(String str) {
		new Parser(str).parse();
	}

	protected class Parser {
		protected String str;

		public Parser(String str) {
			this.str = str;
		}

		public void parse() {
			int end = str.length();
			if (end <= 0) {
				path = "";
				return;
			}
			int n = 0;
			n = parseScheme(n, end);
			n = parseSS(n, end);
			if (scheme != null || ss != null) {
				n = parseAuthority(n, end);
			}
			n = parsePath(n, end);
			n = parseQuery(n, end);
			n = parseFragment(n, end);
			if (path == null && ss != null) {
				path = "";
			}
		}

		public void parseSsp() {
			int end = str.length();
			if (end <= 0) {
				path = "";
				return;
			}
			int n = 0;
			n = parseSS(n, end);
			n = parseAuthority(n, end);
			n = parsePath(n, end);
			n = parseQuery(n, end);
			// if (path == null && ss != null) {
			// path = "";
			// }
		}

		protected int parseScheme(int start, int end) {
			for (int i = start; i < end; i++) {
				char c = str.charAt(i);
				switch (c) {
				case ':':
					scheme = str.substring(start, i);
					return i + 1;
				case '/':
				case '?':
				case '#':
					return start;
				}
			}
			return start;
		}

		protected int parseSS(int start, int end) {
			if (eq(start, end, '/') && eq(start + 1, end, '/')) {
				ss = "//";
				return start + 2;
			}
			return start;
		}

		protected int parseAuthority(int start, int end) {
			boolean at = false;
			for (int i = start; i < end; i++) {
				char c = str.charAt(i);
				switch (c) {
				case '/':
				case '?':
				case '#':
					return parseUser(start, i);
				case '@':
				case ':':
					at = true;
					continue;
				}
			}
			if (ss != null || at) {
				return parseUser(start, end);
			}
			return start;
		}

		protected int parseUser(int start, int end) {
			int b = 0;
			for (int i = start; i < end; i++) {
				char c = str.charAt(i);
				switch (c) {
				case '@':
					user = str.substring(start, i);
					return parseHost(i + 1, end);
				case '[': // []�ň͂܂��̂�IPv6
					b++;
					continue;
				case ']':
					b--;
					continue;
				case ':':
					if (b > 0) { // IPv6��:�ŋ�؂�
						continue;
					}
					host = str.substring(start, i);
					return parsePort(i + 1, end);
				}
			}
			if (start < end) {
				host = str.substring(start, end);
			}
			return end;
		}

		protected int parseHost(int start, int end) {
			int b = 0;
			for (int i = start; i < end; i++) {
				char c = str.charAt(i);
				switch (c) {
				case '[': // []�ň͂܂��̂�IPv6
					b++;
					continue;
				case ']':
					b--;
					continue;
				case ':':
					if (b > 0) { // IPv6��:�ŋ�؂�
						continue;
					}
					host = str.substring(start, i);
					return parsePort(i + 1, end);
				}
			}
			if (start < end) {
				host = str.substring(start, end);
			}
			return end;
		}

		protected int parsePort(int start, int end) {
			if (start < end) {
				port = str.substring(start, end);
			}
			return end;
		}

		protected int parsePath(int start, int end) {
			for (int i = start; i < end; i++) {
				char c = str.charAt(i);
				switch (c) {
				case '?':
				case '#':
					path = str.substring(start, i);
					return i;
				}
			}
			if (start < end) {
				path = str.substring(start, end);
			}
			return end;
		}

		protected int parseQuery(int start, int end) {
			if (!eq(start, end, '?')) {
				return start;
			}
			for (int i = start + 1; i < end; i++) {
				char c = str.charAt(i);
				switch (c) {
				case '#':
					query = str.substring(start + 1, i);
					return i;
				}
			}
			query = str.substring(start + 1, end);
			return end;
		}

		protected int parseFragment(int start, int end) {
			if (!eq(start, end, '#')) {
				if (start < end) {
					append(str.substring(start, end));
				}
			} else {
				fragment = str.substring(start + 1, end);
			}
			return end;
		}

		protected void append(String s) {
			if (fragment != null) {
				fragment += s;
				return;
			}
			if (query != null) {
				query += s;
				return;
			}
			if (path != null) {
				path += s;
				return;
			}
			if (port != null) {
				port += s;
				return;
			}
			if (host != null) {
				host += s;
				return;
			}

			host = s;
		}

		protected boolean eq(int n, int end, char c) {
			if (n < end) {
				return str.charAt(n) == c;
			}
			return false;
		}
	}

	/**
	 * �X�L�[�}�擾.
	 *
	 * @return �X�L�[�}
	 */
	public String getScheme() {
		return scheme;
	}

	/**
	 * �u//�v�擾.
	 * <p>
	 * �X�L�[�}�̒���i�X�L�[�}�ŗL�����̐擪�j�́u//�v�B
	 * </p>
	 *
	 * @return �X���b�V��
	 */
	public String getSS() {
		return ss;
	}

	/**
	 * ���[�U�[���擾.
	 *
	 * @return ���[�U�[���
	 */
	public String getUserInfo() {
		return user;
	}

	/**
	 * �z�X�g�擾.
	 *
	 * @return �z�X�g
	 */
	public String getHost() {
		return host;
	}

	/**
	 * �|�[�g�擾.
	 *
	 * @return �|�[�g
	 */
	public String getPort() {
		return port;
	}

	/**
	 * �p�X�擾.
	 *
	 * @return �p�X
	 */
	public String getPath() {
		return path;
	}

	/**
	 * �N�G���[�擾.
	 *
	 * @return �N�G���[
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * �������ʎq�擾.
	 *
	 * @return �������ʎq
	 */
	public String getFragment() {
		return fragment;
	}

	/**
	 * ���URI���ǂ���.
	 *
	 * @return true�F���URI
	 */
	public boolean isAbsolute() {
		return scheme != null;
	}

	/**
	 * �s�������ǂ���.
	 *
	 * @return true�F�s����
	 */
	public boolean isOpaque() {
		// return path == null;
		// return scheme != null && (path == null || !path.startsWith("/"));
		// return scheme != null && ss == null;

		if (scheme == null) {
			return false;
		}
		String ssp = getSchemeSpecificPart();
		return ssp == null || !ssp.startsWith("/");
	}

	/**
	 * �������ʎq�ifragment�j�݂̂��ǂ���
	 *
	 * @return true�F�������ʎq�̂�
	 */
	public boolean isFragmentOnly() {
		if (fragment == null) {
			return false;
		}
		if (isEmpty(scheme) && isEmpty(ss) && isEmpty(user) && isEmpty(host)
				&& isEmpty(port) && isEmpty(path) && isEmpty(query)) {
			return true;
		}
		return false;
	}

	protected static class SB {
		protected int capacity;
		protected StringBuilder sb = null;

		public SB(int capacity) {
			this.capacity = capacity;
		}

		public void append(String s) {
			if (s == null) {
				return;
			}
			if (sb == null) {
				sb = new StringBuilder(capacity);
			}
			sb.append(s);
		}

		public void append(String s, char c) {
			if (s == null) {
				return;
			}
			if (sb == null) {
				sb = new StringBuilder(capacity);
			}
			sb.append(s);
			sb.append(c);
		}

		public void append(char c, String s) {
			if (s == null) {
				return;
			}
			if (sb == null) {
				sb = new StringBuilder(capacity);
			}
			sb.append(c);
			sb.append(s);
		}

		@Override
		public String toString() {
			if (sb == null) {
				return null;
			}
			return sb.toString();
		}
	}

	private String authority = null;

	/**
	 * �@�փR���|�[�l���g�擾.
	 * <p>
	 * ���[�U�[��� + �z�X�g + �|�[�g
	 * </p>
	 *
	 * @return �@�փR���|�[�l���g
	 */
	public String getAuthority() {
		if (authority == null) {
			SB sb = new SB(32);
			appendAuthority(sb);
			authority = sb.toString();
		}
		return authority;
	}

	private String schemeSpecificPart = null;

	/**
	 * �X�L�[�}�ŗL�����擾.
	 * <p>
	 * �u//�v + ���[�U�[��� + �z�X�g + �|�[�g + �p�X + �N�G���[
	 * </p>
	 *
	 * @return �X�L�[�}�ŗL����
	 */
	public String getSchemeSpecificPart() {
		if (schemeSpecificPart == null) {
			SB sb = new SB(64);
			appendSchemeSpecificPart(sb);
			schemeSpecificPart = sb.toString();
		}
		return schemeSpecificPart;
	}

	private String schemePath = null;

	/**
	 * �X�L�[�}�`�p�X�擾.
	 * <p>
	 * �X�L�[�} + �u//�v + ���[�U�[��� + �z�X�g + �|�[�g + �p�X
	 * </p>
	 *
	 * @return �X�L�[�}�`�p�X
	 */
	public String getSchemePath() {
		if (schemePath == null) {
			SB sb = new SB(64);
			sb.append(scheme, ':');
			sb.append(ss);
			appendAuthority(sb);
			sb.append(path);
			schemePath = sb.toString();
		}
		return schemePath;
	}

	private String schemeQuery = null;

	/**
	 * �X�L�[�}�`�N�G���[�擾.
	 * <p>
	 * �X�L�[�} + �u//�v + ���[�U�[��� + �z�X�g + �|�[�g + �p�X + �N�G���[
	 * </p>
	 *
	 * @return �X�L�[�}�`�N�G���[
	 */
	public String getSchemeQuery() {
		if (schemeQuery == null) {
			SB sb = new SB(64);
			appendSchemeQuery(sb);
			schemeQuery = sb.toString();
		}
		return schemeQuery;
	}

	private String string = null;

	@Override
	public String toString() {
		if (string == null) {
			SB sb = new SB(64);
			appendSchemeQuery(sb);
			sb.append('#', fragment);
			string = sb.toString();
		}
		return string;
	}

	protected void appendSchemeQuery(SB sb) {
		sb.append(scheme, ':');
		appendSchemeSpecificPart(sb);
	}

	protected void appendSchemeSpecificPart(SB sb) {
		sb.append(ss);
		appendAuthority(sb);
		sb.append(path);
		sb.append('?', query);
	}

	protected void appendAuthority(SB sb) {
		sb.append(user, '@');
		sb.append(host);
		sb.append(':', port);
	}

	/**
	 * java.net.URI�擾.
	 *
	 * @return java.net.URI
	 * @throws URISyntaxException
	 *             �ϊ����s
	 */
	public java.net.URI toURI() throws URISyntaxException {
		return new java.net.URI(toString());
	}

	/**
	 * ���K��.
	 *
	 * @return ���K�����ꂽURI�i�K��null�ȊO�j
	 * @see java.net.URI#normalize()
	 */
	public URI normalize() {
		if (path == null || path.isEmpty()) {
			return this;
		}
		String np = normalize(path);
		if (np.equals(path)) {
			return this;
		}
		return this.setPath(np);
	}

	// ��JDK1.6 java.net.URI
	// Normalize the given path string. A normal path string has no empty
	// segments (i.e., occurrences of "//"), no segments equal to ".", and no
	// segments equal to ".." that are preceded by a segment not equal to "..".
	// In contrast to Unix-style pathname normalization, for URI paths we
	// always retain trailing slashes.
	//
	private static String normalize(String ps) {

		// Does this path need normalization?
		int ns = needsNormalization(ps); // Number of segments
		if (ns < 0) {
			// Nope -- just return it
			return ps;
		}

		char[] path = ps.toCharArray(); // Path in char-array form

		// Split path into segments
		int[] segs = new int[ns]; // Segment-index array
		split(path, segs);

		// Remove dots
		removeDots(path, segs);

		// Prevent scheme-name confusion
		maybeAddLeadingDot(path, segs);

		// Join the remaining segments and return the result
		String s = new String(path, 0, join(path, segs));
		if (s.equals(ps)) {
			// string was already normalized
			return ps;
		}
		return s;
	}

	// Check the given path to see if it might need normalization. A path
	// might need normalization if it contains duplicate slashes, a "."
	// segment, or a ".." segment. Return -1 if no further normalization is
	// possible, otherwise return the number of segments found.
	//
	// This method takes a string argument rather than a char array so that
	// this test can be performed without invoking path.toCharArray().
	//
	private static int needsNormalization(String path) {
		boolean normal = true;
		int ns = 0; // Number of segments
		int end = path.length() - 1; // Index of last char in path
		int p = 0; // Index of next char in path

		// Skip initial slashes
		while (p <= end) {
			if (path.charAt(p) != '/') {
				break;
			}
			p++;
		}
		if (p > 1) {
			normal = false;
		}

		// Scan segments
		while (p <= end) {

			// Looking at "." or ".." ?
			if ((path.charAt(p) == '.')
					&& ((p == end) || ((path.charAt(p + 1) == '/') || ((path
							.charAt(p + 1) == '.') && ((p + 1 == end) || (path
							.charAt(p + 2) == '/')))))) {
				normal = false;
			}
			ns++;

			// Find beginning of next segment
			while (p <= end) {
				if (path.charAt(p++) != '/') {
					continue;
				}

				// Skip redundant slashes
				while (p <= end) {
					if (path.charAt(p) != '/') {
						break;
					}
					normal = false;
					p++;
				}

				break;
			}
		}

		return normal ? -1 : ns;
	}

	// Split the given path into segments, replacing slashes with nulls and
	// filling in the given segment-index array.
	//
	// Preconditions:
	// segs.length == Number of segments in path
	//
	// Postconditions:
	// All slashes in path replaced by '\0'
	// segs[i] == Index of first char in segment i (0 <= i < segs.length)
	//
	private static void split(char[] path, int[] segs) {
		int end = path.length - 1; // Index of last char in path
		int p = 0; // Index of next char in path
		int i = 0; // Index of current segment

		// Skip initial slashes
		while (p <= end) {
			if (path[p] != '/') {
				break;
			}
			path[p] = '\0';
			p++;
		}

		while (p <= end) {

			// Note start of segment
			segs[i++] = p++;

			// Find beginning of next segment
			while (p <= end) {
				if (path[p++] != '/') {
					continue;
				}
				path[p - 1] = '\0';

				// Skip redundant slashes
				while (p <= end) {
					if (path[p] != '/') {
						break;
					}
					path[p++] = '\0';
				}
				break;
			}
		}

		if (i != segs.length) {
			throw new InternalError(); // ASSERT
		}
	}

	// Remove "." segments from the given path, and remove segment pairs
	// consisting of a non-".." segment followed by a ".." segment.
	//
	private static void removeDots(char[] path, int[] segs) {
		int ns = segs.length;
		int end = path.length - 1;

		for (int i = 0; i < ns; i++) {
			int dots = 0; // Number of dots found (0, 1, or 2)

			// Find next occurrence of "." or ".."
			do {
				int p = segs[i];
				if (path[p] == '.') {
					if (p == end) {
						dots = 1;
						break;
					} else if (path[p + 1] == '\0') {
						dots = 1;
						break;
					} else if ((path[p + 1] == '.')
							&& ((p + 1 == end) || (path[p + 2] == '\0'))) {
						dots = 2;
						break;
					}
				}
				i++;
			} while (i < ns);
			if ((i > ns) || (dots == 0)) {
				break;
			}

			if (dots == 1) {
				// Remove this occurrence of "."
				segs[i] = -1;
			} else {
				// If there is a preceding non-".." segment, remove both that
				// segment and this occurrence of ".."; otherwise, leave this
				// ".." segment as-is.
				int j;
				for (j = i - 1; j >= 0; j--) {
					if (segs[j] != -1) {
						break;
					}
				}
				if (j >= 0) {
					int q = segs[j];
					if (!((path[q] == '.') && (path[q + 1] == '.') && (path[q + 2] == '\0'))) {
						segs[i] = -1;
						segs[j] = -1;
					}
				}
			}
		}
	}

	// DEVIATION: If the normalized path is relative, and if the first
	// segment could be parsed as a scheme name, then prepend a "." segment
	//
	private static void maybeAddLeadingDot(char[] path, int[] segs) {

		if (path[0] == '\0') {
			// The path is absolute
			return;
		}

		int ns = segs.length;
		int f = 0; // Index of first segment
		while (f < ns) {
			if (segs[f] >= 0) {
				break;
			}
			f++;
		}
		if ((f >= ns) || (f == 0)) {
			// The path is empty, or else the original first segment survived,
			// in which case we already know that no leading "." is needed
			return;
		}

		int p = segs[f];
		while ((p < path.length) && (path[p] != ':') && (path[p] != '\0')) {
			p++;
		}
		if (p >= path.length || path[p] == '\0') {
			// No colon in first segment, so no "." needed
			return;
		}

		// At this point we know that the first segment is unused,
		// hence we can insert a "." segment at that position
		path[0] = '.';
		path[1] = '\0';
		segs[0] = 0;
	}

	// Join the segments in the given path according to the given segment-index
	// array, ignoring those segments whose index entries have been set to -1,
	// and inserting slashes as needed. Return the length of the resulting
	// path.
	//
	// Preconditions:
	// segs[i] == -1 implies segment i is to be ignored
	// path computed by split, as above, with '\0' having replaced '/'
	//
	// Postconditions:
	// path[0] .. path[return value] == Resulting path
	//
	private static int join(char[] path, int[] segs) {
		int ns = segs.length; // Number of segments
		int end = path.length - 1; // Index of last char in path
		int p = 0; // Index of next path char to write

		if (path[p] == '\0') {
			// Restore initial slash for absolute paths
			path[p++] = '/';
		}

		for (int i = 0; i < ns; i++) {
			int q = segs[i]; // Current segment
			if (q == -1) {
				// Ignore this segment
				continue;
			}

			if (p == q) {
				// We're already at this segment, so just skip to its end
				while ((p <= end) && (path[p] != '\0')) {
					p++;
				}
				if (p <= end) {
					// Preserve trailing slash
					path[p++] = '/';
				}
			} else if (p < q) {
				// Copy q down to p
				while ((q <= end) && (path[q] != '\0')) {
					path[p++] = path[q++];
				}
				if (q <= end) {
					// Preserve trailing slash
					path[p++] = '/';
				}
			} else {
				throw new InternalError(); // ASSERT false
			}
		}

		return p;
	}

	public URI resolve(URI uri) {
		return resolve(this, uri);
	}

	// ��JDK1.6 java.net.URI
	// RFC2396 5.2
	private static URI resolve(URI base, URI child) {
		// check if child if opaque first so that NPE is thrown
		// if child is null.
		if (child.isOpaque() || base.isOpaque()) {
			return child;
		}

		// 5.2 (2): Reference to current document (lone fragment)
		if (child.isFragmentOnly()) {
			if (equals(base.fragment, child.fragment)) {
				return base;
			}
			return base.setFragment(child.fragment);
		}

		// 5.2 (3): Child is absolute
		if (child.scheme != null) {
			return child;
		}

		URI ru = new URI(); // Resolved URI
		ru.scheme = base.scheme;
		ru.ss = base.ss;
		ru.query = child.query;
		ru.fragment = child.fragment;

		// 5.2 (4): Authority
		ru.host = base.host;
		ru.user = base.user;
		ru.port = base.port;

		String cp = (child.path == null) ? "" : child.path;
		if (cp.startsWith("/")) {
			// 5.2 (5): Child path is absolute
			ru.path = child.path;
		} else {
			// 5.2 (6): Resolve relative path
			ru.path = resolvePath(base.path, cp);
		}

		// 5.2 (7): Recombine (nothing to do here)
		return ru;
	}

	// RFC2396 5.2 (6)
	private static String resolvePath(String base, String child) {
		int i = base.lastIndexOf('/');
		int cn = child.length();
		String path = "";

		if (cn == 0) {
			// 5.2 (6a)
			if (i >= 0) {
				path = base.substring(0, i + 1);
			}
		} else {
			StringBuilder sb = new StringBuilder(base.length() + cn);
			// 5.2 (6a)
			if (i >= 0) {
				sb.append(base.substring(0, i + 1));
			}
			// 5.2 (6b)
			sb.append(child);
			path = sb.toString();
		}

		// 5.2 (6c-f)
		String np = normalize(path);

		// 5.2 (6g): If the result is absolute but the path begins with "../",
		// then we simply leave the path as-is

		return np;
	}

	public URI resolve(File dir, File f) {
		return resolveFile(this, dir, f);
	}

	public URI resolve(File dir, File f, URI uri) {
		return resolveFile(this, dir, f, uri);
	}

	protected static URI resolveFile(URI base, File dir, File f, URI uri) {
		if (uri.isAbsolute()) {
			return uri.normalize();
		}
		URI fu = resolveFile(base, dir, f);
		return resolve(fu, uri); // normalize()����Ă���
	}

	protected static URI resolveFile(URI base, File dir, File f) {
		// URI fru = dir.toURI().relativize(f.toURI());

		URI du = new URI(dir, true);

		URI fru = relativize(du, new URI(f.toURI()));
		return resolve(base, fru);
	}

	/**
	 * ���Ή�.
	 * <p>
	 * �w�肳�ꂽURI���A��URI�ɑ΂��đ��Ή�����B<br>
	 * {@link java.net.URI#relativize(URI)}�� �URI���Ώ�URI�̈ꕔ�ɂȂ��Ă���ꍇ�������Ή��ł��Ȃ����A
	 * �����\�b�h�ł́u../�v��t������̂ŁA�p�X�̐擪��������v���Ă���Ύg����B
	 * </p>
	 *
	 * @param uri
	 *            ���Ή�����URI�inull�s�j
	 * @return ���Ή����ꂽURI�i�K��null�ȊO�j
	 */
	public URI relativize(URI uri) {
		return relativize(this, uri);
	}

	/**
	 * �w�肳�ꂽURI�́A�URI����̑��΃p�X��Ԃ��B
	 *<p>
	 * {@link java.net.URI#relativize(URI)}�� �URI���Ώ�URI�̈ꕔ�ɂȂ��Ă���ꍇ�������Ή��ł��Ȃ����A
	 * �����\�b�h�ł́u../�v��t������̂ŁA�p�X�̐擪��������v���Ă���Ύg����B
	 * </p>
	 *
	 * @param base
	 *            �URI�inull�s�j
	 * @param child
	 *            ���Ή�����URI�inull�s�j
	 * @return URI�i�K��null�ȊO�j�i���Ή��ł��Ȃ������ꍇ�͌���uri�j
	 */
	protected static URI relativize(URI base, URI child) {
		if (child.isOpaque() || base.isOpaque()) {
			return child;
		}
		if (!equalsIgnoreCase(base.scheme, child.scheme)
				|| !equals(base.ss, child.ss)
				|| !equals(base.getAuthority(), child.getAuthority())) {
			return child;
		}

		String bp = normalize(base.path != null ? base.path : "");
		String cp = normalize(child.path != null ? child.path : "");

		// fragment�ȊO���S�������ꍇ
		if (child.fragment != null && equals(bp, cp)
				&& equals(base.query, child.query)) {
			URI uri = new URI();
			uri.path = "";
			uri.fragment = child.fragment;
			return uri;
		}

		String rel = relativizePath(bp, cp);
		if (rel == null) {
			return child;
		} else {
			URI uri = new URI();
			uri.path = rel;
			uri.query = child.query;
			uri.fragment = child.fragment;
			return uri;
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
	protected static String relativizePath(String base, String path) {
		int len = getEqualsPathLength(base, path);
		if (len <= 0) { // ��v���Ă��镔��������
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
	protected static int getEqualsPathLength(String path1, String path2) {
		if (path1 == null || path2 == null) {
			return -1;
		}
		if (path1.isEmpty() || path2.isEmpty()) {
			return 0;
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
	protected static int getSlashCount(String path, int start) {
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
	 * �X�L�[�}��ύX����URI��Ԃ��B
	 *
	 * @param scheme
	 *            �X�L�[�}
	 * @return �ύX���ꂽURI�i�K��null�ȊO�j�i�ύX�������ꍇ�͓�URI�����̂܂ܕԂ�j
	 */
	public URI setScheme(String scheme) {
		if (equals(scheme, this.scheme)) {
			return this;
		}
		return new URI(scheme, ss, user, host, port, path, query, fragment);
	}

	/**
	 * �u//�v��ύX����URI��Ԃ��B
	 *
	 * @param ss
	 *            �X���b�V��
	 * @return �ύX���ꂽURI�i�K��null�ȊO�j�i�ύX�������ꍇ�͓�URI�����̂܂ܕԂ�j
	 */
	public URI setSS(String ss) {
		if (equals(ss, this.ss)) {
			return this;
		}
		return new URI(scheme, ss, user, host, port, path, query, fragment);
	}

	/**
	 * ���[�U�[����ύX����URI��Ԃ��B
	 *
	 * @param user
	 *            ���[�U�[���
	 * @return �ύX���ꂽURI�i�K��null�ȊO�j�i�ύX�������ꍇ�͓�URI�����̂܂ܕԂ�j
	 */
	public URI setUserInfo(String user) {
		if (equals(user, this.user)) {
			return this;
		}
		return new URI(scheme, ss, user, host, port, path, query, fragment);
	}

	/**
	 * �z�X�g��ύX����URI��Ԃ��B
	 *
	 * @param host
	 *            �z�X�g
	 * @return �ύX���ꂽURI�i�K��null�ȊO�j�i�ύX�������ꍇ�͓�URI�����̂܂ܕԂ�j
	 */
	public URI setHost(String host) {
		if (equals(host, this.host)) {
			return this;
		}
		return new URI(scheme, ss, user, host, port, path, query, fragment);
	}

	/**
	 * �|�[�g��ύX����URI��Ԃ��B
	 *
	 * @param port
	 *            �|�[�g
	 * @return �ύX���ꂽURI�i�K��null�ȊO�j�i�ύX�������ꍇ�͓�URI�����̂܂ܕԂ�j
	 */
	public URI setPort(String port) {
		if (equals(port, this.port)) {
			return this;
		}
		return new URI(scheme, ss, user, host, port, path, query, fragment);
	}

	/**
	 * �p�X��ύX����URI��Ԃ��B
	 *
	 * @param path
	 *            �p�X
	 * @return �ύX���ꂽURI�i�K��null�ȊO�j�i�ύX�������ꍇ�͓�URI�����̂܂ܕԂ�j
	 */
	public URI setPath(String path) {
		if (equals(path, this.path)) {
			return this;
		}
		return new URI(scheme, ss, user, host, port, path, query, fragment);
	}

	/**
	 * �N�G���[��ύX����URI��Ԃ��B
	 *
	 * @param query
	 *            �N�G���[
	 * @return �ύX���ꂽURI�i�K��null�ȊO�j�i�ύX�������ꍇ�͓�URI�����̂܂ܕԂ�j
	 */
	public URI setQuery(String query) {
		if (equals(query, this.query)) {
			return this;
		}
		return new URI(scheme, ss, user, host, port, path, query, fragment);
	}

	/**
	 * �������ʎq��ύX����URI��Ԃ��B
	 *
	 * @param fragment
	 *            �������ʎq
	 * @return �ύX���ꂽURI�i�K��null�ȊO�j�i�ύX�������ꍇ�͓�URI�����̂܂ܕԂ�j
	 */
	public URI setFragment(String fragment) {
		if (equals(fragment, this.fragment)) {
			return this;
		}
		return new URI(scheme, ss, user, host, port, path, query, fragment);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof URI)) {
			return false;
		}
		URI that = (URI) obj;
		return equalsIgnoreCase(this.scheme, that.scheme)
				&& equals(this.ss, that.ss) && equals(this.user, that.user)
				&& equalsIgnoreCase(this.host, that.host)
				&& equals(this.port, that.port) && equals(this.path, that.path)
				&& equals(this.query, that.query)
				&& equals(this.fragment, that.fragment);
	}

	private int hash = -1;

	@Override
	public int hashCode() {
		if (hash == -1) {
			int h = 0;
			h = hashIgnoreCase(h, scheme);
			h = hash(h, ss);
			h = hash(h, user);
			h = hashIgnoreCase(h, host);
			h = hash(h, port);
			h = hash(h, path);
			h = hash(h, query);
			h = hash(h, fragment);
			hash = h;
		}
		return hash;
	}

	@Override
	public int compareTo(URI that) {
		int c;
		if ((c = compareIgnoreCase(this.scheme, that.scheme)) != 0) {
			return c;
		}
		if ((c = compare(this.ss, that.ss)) != 0) {
			return c;
		}
		if ((c = compare(this.user, that.user)) != 0) {
			return c;
		}
		if ((c = compareIgnoreCase(this.host, that.host)) != 0) {
			return c;
		}
		if ((c = compare(this.port, that.port)) != 0) {
			return c;
		}
		if ((c = compare(this.path, that.path)) != 0) {
			return c;
		}
		if ((c = compare(this.query, that.query)) != 0) {
			return c;
		}
		return compare(this.fragment, that.fragment);
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

	protected static int hash(int h, String s) {
		if (s != null) {
			h *= 127;
			h ^= s.hashCode();
		}
		return h;
	}

	protected static int hashIgnoreCase(int h, String s) {
		if (s != null) {
			h *= 31;
			h ^= s.toLowerCase().hashCode();
		}
		return h;
	}

	protected static int compare(String s1, String s2) {
		if (s1 == s2) {
			return 0;
		}
		if (s1 != null) {
			if (s2 != null) {
				return s1.compareTo(s2);
			} else {
				return +1;
			}
		} else {
			return -1;
		}
	}

	protected static int compareIgnoreCase(String s1, String s2) {
		if (s1 == s2) {
			return 0;
		}
		if (s1 != null) {
			if (s2 != null) {
				return s1.compareToIgnoreCase(s2);
			} else {
				return +1;
			}
		} else {
			return -1;
		}
	}

	protected static boolean isEmpty(String s) {
		if (s == null || s.isEmpty()) {
			return true;
		}
		return false;
	}
}
