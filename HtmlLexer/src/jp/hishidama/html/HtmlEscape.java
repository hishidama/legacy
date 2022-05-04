package jp.hishidama.html;

import java.util.*;

/**
 * HTML�G�X�P�[�v�N���X.
 * <p>
 * HTML�G�X�P�[�v�iHTML�G���R�[�h�j�̔������s���s���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.25
 * @version 2009.02.08
 */
public class HtmlEscape {

	/**
	 * �R���X�g���N�^�[.
	 *<p>
	 * {@link #HtmlEscape(boolean, boolean) ��������R���X�g���N�^�[}�ɑS��true���w�肵���ꍇ�Ɠ����B
	 * </p>
	 *
	 * @see #HtmlEscape(boolean, boolean)
	 */
	public HtmlEscape() {
		this(true, true);
	}

	/**
	 * �R���X�g���N�^�[.
	 * <table border="1">
	 * <caption>�f�t�H���g�Ŏw�肳��镶��</caption>
	 * <tr>
	 * <th>����</th>
	 * <th>�ϊ�</th>
	 * <th>���l</th>
	 * <th>version</th>
	 * </tr>
	 * <tr>
	 * <td>&amp;</td>
	 * <td>&amp;amp;</td>
	 * <td>-</td>
	 * <td>&nbsp</td>
	 * </tr>
	 * <tr>
	 * <td>&lt;</td>
	 * <td>&amp;lt;</td>
	 * <td>-</td>
	 * <td>&nbsp</td>
	 * </tr>
	 * <tr>
	 * <td>&gt;</td>
	 * <td>&amp;gt;</td>
	 * <td>-</td>
	 * <td>&nbsp</td>
	 * </tr>
	 * <tr>
	 * <td>&quot;</td>
	 * <td>&amp;quot;</td>
	 * <td>dq��true�̏ꍇ</td>
	 * <td>&nbsp</td>
	 * </tr>
	 * <tr>
	 * <td>&#39;</td>
	 * <td>&amp;#39;</td>
	 * <td>sq��true�̏ꍇ</td>
	 * <td>&nbsp</td>
	 * </tr>
	 * <tr>
	 * <td>&nbsp;</td>
	 * <td>&amp;nbsp;</td>
	 * <td>���̂Ƃ���A�ϊ��ΏۊO</td>
	 * <td>2009.02.08</td>
	 * </tr>
	 * <tr>
	 * <td>�Ȃ�</td>
	 * <td>&amp;copy;</td>
	 * <td>�ϊ��ΏۊO</td>
	 * <td>2009.02.08</td>
	 * </tr>
	 * <tr>
	 * <td>�Ȃ�</td>
	 * <td>&amp;reg;</td>
	 * <td>�ϊ��ΏۊO</td>
	 * <td>2009.02.08</td>
	 * </tr>
	 * </table>
	 *
	 * @param dq
	 *            true�̏ꍇ�A�_�u���N�H�[�e�[�V�����̒�`���܂߂�
	 * @param sq
	 *            true�̏ꍇ�A�V���O���N�H�[�e�[�V�����̒�`���܂߂�
	 */
	public HtmlEscape(boolean dq, boolean sq) {
		addEntity("amp", "&");
		addEntity("lt", "<");
		addEntity("gt", ">");
		if (dq) {
			addEntity("quot", "\"");
		}
		if (sq) {
			addEntity("#39", "\'");
			// addEntity("apos", "\'");
		}

		// addEntity("nbsp", " ");
		addEntity("nbsp", null);

		addEntity("copy", null);
		addEntity("reg", null);
	}

	protected final Map<String, String> nameMap = new HashMap<String, String>();
	protected final Map<Character, String> entityMap = new HashMap<Character, String>();

	/**
	 * ENTITY�ǉ�.
	 * <p>
	 * �G���e�B�e�B�[�i�����Q�Ƃ̎��́j��ǉ�����B<br>
	 * ����G���e�B�e�B�[�i�����j�ɂȂ閼�O�́A�ォ��ǉ����������D�悳���B
	 * </p>
	 *
	 * @param name
	 *            ���O�i��Famp�j
	 * @param entity
	 *            ���́i��F&amp;�j�B�K��1�����ł���K�v������B<br>
	 *            null�̏ꍇ�͕ϊ��ΏۂƂ��Ȃ��B
	 */
	public void addEntity(String name, String entity) {
		nameMap.put(name, entity);
		if (entity == null) {
			return;
		}
		if (entity.length() != 1) {
			throw new IllegalArgumentException("entity.length != 1: " + entity);
		}
		entityMap.put(entity.charAt(0), "&" + name + ";");
	}

	/**
	 * ENTITY��`��S�č폜����.
	 */
	public void clearEntities() {
		nameMap.clear();
		entityMap.clear();
	}

	/**
	 * HTML�G�X�P�[�v.
	 * <p>
	 * HTML�G�X�P�[�v���s���B���ɃG�X�P�[�v����Ă��镶���ɂ��Ă͕ϊ����Ȃ��B
	 * </p>
	 *
	 * @param s
	 *            ������
	 * @return HTML�G�X�P�[�v���ꂽ������is��null�������ꍇ��null�j
	 */

	public String escape(String s) {
		if (s == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder(s.length() * 2);
		for (int i = 0; i < s.length();) {
			char c = s.charAt(i);
			if (c == '&') {
				String e = escapedAmp(s, i);
				if (e != null) {
					sb.append(c);
					sb.append(e);
					sb.append(';');
					i += e.length() + 2;
					continue;
				}
			}
			String name = entityMap.get(c);
			if (name != null) {
				sb.append(name);
			} else {
				sb.append(c);
			}
			i++;
		}
		return sb.toString();
	}

	/**
	 * HTML�G�X�P�[�v����Ă��镶�����ǂ����𔻒肷��
	 *
	 * @param s
	 *            ������
	 * @param pos
	 *            ����J�n�ʒu�i�u&amp;�v������ʒu�j
	 * @return �G�X�P�[�v����Ă���ꍇ�A���̕������B<br>
	 *         �G�X�P�[�v����Ă��Ȃ��ꍇ��0
	 */
	public String escapedAmp(String s, int pos) {
		pos++;
		int semi = s.indexOf(';', pos + 1);
		if (semi < 0) {
			return null;
		}
		String t = s.substring(pos, semi);
		if (nameMap.containsKey(t)) {
			return t;
		}
		char c = t.charAt(0);
		switch (c) {
		case '#':
			if (t.length() < 2) {
				return null;
			}
			c = t.charAt(1);
			if (c == 'x' || c == 'X') {
				if (isHexDigits(t, 2)) {
					return t;
				} else {
					return null;
				}
			} else {
				if (isDigits(t, 1)) {
					return t;
				} else {
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * �\�i�����ǂ����𔻒肷��
	 *
	 * @param s
	 *            ������
	 * @param first
	 *            ����J�n�ʒu
	 * @return �\�i���̏ꍇ�Atrue
	 */
	public static boolean isDigits(String s, int first) {
		int len = s.length();
		for (int i = first; i < len; i++) {
			if (!isDigit(s.charAt(i))) {
				return false;
			}
		}
		return first < len;
	}

	/**
	 * �\�i���������ǂ����𔻒肷��
	 *
	 * @param c
	 *            ����
	 * @return �\�i�������̏ꍇ�Atrue
	 */
	public static boolean isDigit(char c) {
		return ('0' <= c && c <= '9');
	}

	/**
	 * �\�Z�i�����ǂ����𔻒肷��
	 *
	 * @param s
	 *            ������
	 * @param first
	 *            ����J�n�ʒu
	 * @return �\�Z�i���̏ꍇ�Atrue
	 */
	public static boolean isHexDigits(String s, int first) {
		int len = s.length();
		for (int i = first; i < len; i++) {
			if (!isHexDigit(s.charAt(i))) {
				return false;
			}
		}
		return first < len;
	}

	/**
	 * �\�Z�i���������ǂ����𔻒肷��
	 *
	 * @param c
	 *            ����
	 * @return �\�Z�i�������̏ꍇ�Atrue
	 */
	public static boolean isHexDigit(char c) {
		return ('0' <= c && c <= '9') || ('a' <= c && c <= 'f')
				|| ('A' <= c && c <= 'F');
	}
}
