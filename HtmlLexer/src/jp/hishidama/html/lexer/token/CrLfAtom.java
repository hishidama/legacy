package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexer�g�[�N���i���s�j.
 *<p>
 * ���s�P����ێ�����g�[�N���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.10
 */
public final class CrLfAtom extends WordAtom {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public CrLfAtom clone() throws CloneNotSupportedException {
		return new CrLfAtom(this);
	}

	protected CrLfAtom(CrLfAtom o) {
		super(o);
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param sb
	 *            ���s������
	 */
	public CrLfAtom(StringBuilder sb) {
		super(sb);
	}

	@Override
	public int calcLine(int n) {
		line = n;
		return n + 1;
	}
}
