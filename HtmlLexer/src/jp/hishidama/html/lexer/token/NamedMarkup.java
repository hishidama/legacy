package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexer�g�[�N���i���O�t���}�[�N�A�b�v�j.
 *<p>
 * ���O�̂���}�[�N�A�b�v�i{@literal <name�`>}�j���Ӗ�����g�[�N���i���ۃN���X�j�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.10
 */
public abstract class NamedMarkup extends Markup {

	protected NamedMarkup(NamedMarkup o) {
		super(o);
	}

	protected static enum Pos {
		TAGO, NAME,
		// ETC,
		// TAGC,
	}

	protected NamedMarkup() {
	}

	/**
	 * ���O�ݒ�.
	 *
	 * @param atom
	 *            ���O
	 */
	public void setName(NameAtom atom) {
		set(Pos.NAME, atom);
	}

	/**
	 * ���O�ݒ�.
	 *
	 * @param s
	 *            ���O
	 */
	public void setName(String s) {
		NameAtom name = getNameAtom();
		if (name == null) {
			name = new NameAtom();
			setName(name);
		}
		name.setName(s);
	}

	/**
	 * ���O�擾.
	 *
	 * @return ���O�i���݂��Ȃ��ꍇ�Anull�j
	 */
	public String getName() {
		NameAtom name = getNameAtom();
		if (name != null) {
			return name.getName();
		}
		return null;
	}

	/**
	 * ���O�擾.
	 *
	 * @return ���O�i���݂��Ȃ��ꍇ�Anull�j
	 */
	public NameAtom getNameAtom() {
		return (NameAtom) get(Pos.NAME);
	}
}
