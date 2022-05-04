package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexer�g�[�N���i�}�[�N�A�b�v�j.
 * <p>
 * �}�[�N�A�b�v�i{@literal <�`>}�j���Ӗ�����g�[�N���i���ۃN���X�j�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.10
 */
public class Markup extends ListToken {

	private boolean existTagC = false;

	/**
	 * @since 2009.02.07
	 */
	@Override
	public Markup clone() throws CloneNotSupportedException {
		return new Markup(this);
	}

	protected Markup(Markup o) {
		super(o);
		existTagC = o.existTagC;
	}

	protected Markup() {
	}

	protected static enum Pos {
		TAGO,
		// TAGC,
	}

	/**
	 * �^�O�J���ݒ�.
	 *
	 * @param sb
	 *            TAGO
	 */
	public void setTag1(StringBuilder sb) {
		AtomToken atom = getTag1Atom();
		if (atom == null) {
			atom = new AtomToken(sb);
			set(Pos.TAGO, atom);
		} else {
			atom.setAtom(sb);
		}
	}

	/**
	 * �󔒒ǉ�.
	 * <p>
	 * �^�O���̒��O�ɋ󔒂�}������B
	 * </p>
	 *
	 * @param skip
	 *            ��
	 */
	public void addSkip(SkipToken skip) {
		addBeforeTagC(skip);
	}

	/**
	 * �󔒒ǉ�.
	 * <p>
	 * �^�O���̒��O�ɋ󔒂�}������B
	 * </p>
	 *
	 * @param skip
	 *            ��
	 * @since 2011.11.13
	 */
	public void addSkip(String skip) {
		SkipToken token = new SkipToken();
		token.add(new WordAtom(skip));
		addSkip(token);
	}

	protected void addBeforeTagC(Token token) {
		if (token == null) {
			return;
		}
		if (existTagC) {
			add(list.size() - 1, token);
		} else {
			add(token);
		}
	}

	/**
	 * �^�O���ݒ�.
	 *
	 * @param sb
	 *            TAGC
	 */
	public void setTag2(StringBuilder sb) {
		AtomToken atom = getTag2Atom();
		if (atom == null) {
			atom = new AtomToken(sb);
			add(atom);
			existTagC = true;
		} else {
			atom.setAtom(sb);
		}
	}

	/**
	 * �^�O�J���擾.
	 *
	 * @return TAGO�i���݂��Ȃ��ꍇ�Anull�j
	 */
	public AtomToken getTag1Atom() {
		return (AtomToken) get(Pos.TAGO);
	}

	/**
	 * �^�O�J���擾.
	 *
	 * @return TAGO�i���݂��Ȃ��ꍇ�Anull�j
	 */
	public String getTag1() {
		AtomToken atom = getTag1Atom();
		if (atom != null) {
			return atom.getAtom();
		}
		return null;
	}

	/**
	 * �^�O���擾.
	 *
	 * @return TAGC�i���݂��Ȃ��ꍇ�Anull�j
	 */
	public AtomToken getTag2Atom() {
		if (existTagC) {
			return (AtomToken) getLast();
		}
		return null;
	}

	/**
	 * �^�O���擾.
	 *
	 * @return TAGC�i���݂��Ȃ��ꍇ�Anull�j
	 */
	public String getTag2() {
		AtomToken atom = getTag2Atom();
		if (atom != null) {
			return atom.getAtom();
		}
		return null;
	}
}
