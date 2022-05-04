package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexer�g�[�N���i���O�j.
 *<p>
 * ���O��\���g�[�N���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.10
 */
public class NameAtom extends AtomToken {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public NameAtom clone() throws CloneNotSupportedException {
		return new NameAtom(this);
	}

	protected NameAtom(NameAtom o) {
		super(o);
	}

	/**
	 * �R���X�g���N�^�[.
	 */
	public NameAtom() {
	}

	/**
	 * ���O�ݒ�.
	 *
	 * @param sb
	 *            ���O
	 */
	public void setName(StringBuilder sb) {
		super.setAtom(sb);
	}

	/**
	 * ���O�ݒ�.
	 *
	 * @param s
	 *            ���O
	 */
	public void setName(String s) {
		super.setAtom(s);
	}

	/**
	 * ���O�擾.
	 *
	 * @return ���O�i���݂��Ȃ��ꍇ�Anull�j
	 */
	public String getName() {
		return super.getAtom();
	}
}
