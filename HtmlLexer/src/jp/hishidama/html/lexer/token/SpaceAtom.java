package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexer�g�[�N���i�󔒁j.
 *<p>
 * �󔒕����i�X�y�[�X�E�^�u�j�݂̂�ێ�����g�[�N���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.10
 */
public final class SpaceAtom extends WordAtom {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public SpaceAtom clone() throws CloneNotSupportedException {
		return new SpaceAtom(this);
	}

	protected SpaceAtom(SpaceAtom o) {
		super(o);
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param sb
	 *            �󔒕�����
	 */
	public SpaceAtom(StringBuilder sb) {
		super(sb);
	}
}
