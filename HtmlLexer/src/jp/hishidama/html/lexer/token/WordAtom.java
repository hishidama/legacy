package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexer�g�[�N���i�P��j.
 *<p>
 * �P��i�Ƃ�����������j��ێ�����g�[�N���B<br>
 * ���N���X�Œ��ڕێ����镶����́A���s�R�[�h�������Ȃ��B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.10
 */
public class WordAtom extends AtomToken {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public WordAtom clone() throws CloneNotSupportedException {
		return new WordAtom(this);
	}

	protected WordAtom(WordAtom o) {
		super(o);
	}

	/**
	 * �R���X�g���N�^�[.
	 */
	public WordAtom() {
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param sb
	 *            ������
	 */
	public WordAtom(StringBuilder sb) {
		super(sb);
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param s
	 *            ������
	 */
	public WordAtom(String s) {
		super(s);
	}

	/**
	 * ������ݒ�.
	 *
	 * @param sb
	 *            ������
	 */
	public void setString(StringBuilder sb) {
		super.setAtom(sb);
	}

	/**
	 * ������ݒ�.
	 *
	 * @param s
	 *            ������
	 */
	public void setString(String s) {
		super.setAtom(s);
	}

	/**
	 * ������擾.
	 *
	 * @return ������i���݂��Ȃ��ꍇ�Anull�j
	 */
	public String getString() {
		return super.getAtom();
	}
}
