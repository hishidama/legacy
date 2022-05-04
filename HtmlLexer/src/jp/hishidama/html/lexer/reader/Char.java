package jp.hishidama.html.lexer.reader;

/**
 * HtHtmlLexer�̈ꕶ����ێ�����N���X.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.01
 */
public class Char {

	/**
	 * �ǂݍ��ݏI��.
	 */
	public static final Char EOF = new Char(CharType.EOF, null);

	protected CharType type;
	protected StringBuilder sb;

	protected Char(CharType type, StringBuilder sb) {
		this.type = type;
		this.sb = sb;
	}

	/**
	 * �C���X�^���X�쐬.
	 *
	 * @param type
	 *            �����^�C�v
	 * @param sb
	 *            ���e
	 * @return Char�C���X�^���X
	 */
	public static Char createChar(CharType type, StringBuilder sb) {
		return new Char(type, sb);
	}

	/**
	 * �C���X�^���X����.
	 *
	 * @param type
	 *            �����^�C�v
	 * @param c
	 *            ���e
	 * @return Char�C���X�^���X
	 */
	public static Char createChar(CharType type, char c) {
		StringBuilder sb = new StringBuilder(1);
		sb.append(c);
		return new Char(type, sb);
	}

	/**
	 * �����^�C�v�擾.
	 *
	 * @return �����^�C�v
	 */
	public CharType getType() {
		return type;
	}

	/**
	 * ���e�擾.
	 *
	 * @return ���e
	 */
	public String getString() {
		return sb.toString();
	}

	/**
	 * ���e�擾.
	 *
	 * @return ���e
	 */
	public StringBuilder getSB() {
		return this.sb;
	}

	@Override
	public String toString() {
		return sb.toString();
	}
}
