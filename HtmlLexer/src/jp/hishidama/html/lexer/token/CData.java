package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexer�g�[�N���iCDATA�j.
 *<p>
 * CDATA�Z�N�V�����i{@literal <![CDATA[�`]]>}�j��ێ�����g�[�N���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.25
 */
public class CData extends NamedMarkup {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public CData clone() throws CloneNotSupportedException {
		return new CData(this);
	}

	protected CData(CData o) {
		super(o);
	}

	static enum Pos {
		TAGO, NAME, BRAO, TEXT, TAGC
	}

	/**
	 * �R���X�g���N�^�[.
	 */
	public CData() {
	}

	/**
	 * �p���ʊJ���ݒ�.
	 * <p>
	 * �uCDATA�v�̒���̊p���ʁB
	 * </p>
	 *
	 * @param word
	 *            �p���ʁi���ʂ́u[�v�j
	 */
	public void setBraO(WordAtom word) {
		set(Pos.BRAO, word);
	}

	/**
	 * �e�L�X�g�ݒ�.
	 *
	 * @param text
	 *            �e�L�X�g
	 */
	public void setData(TextToken text) {
		set(Pos.TEXT, text);
	}

	@Override
	public void setTag2(StringBuilder sb) {
		AtomToken atom = getTag2Atom();
		if (atom == null) {
			atom = new AtomToken(sb);
			set(Pos.TAGC, atom);
		} else {
			atom.setAtom(sb);
		}
	}

	@Deprecated
	@Override
	public void addSkip(SkipToken skip) {
		throw new UnsupportedOperationException();
	}

	/**
	 * �e�L�X�g�擾.
	 *
	 * @return �e�L�X�g�i���݂��Ȃ��ꍇ�Anull�j
	 */
	public String getData() {
		TextToken text = getDataToken();
		if (text != null) {
			return text.getText();
		}
		return null;
	}

	/**
	 * �e�L�X�g�擾.
	 *
	 * @return �e�L�X�g�i���݂��Ȃ��ꍇ�Anull�j
	 */
	public TextToken getDataToken() {
		return (TextToken) get(Pos.TEXT);
	}

	@Override
	public String getTag2() {
		AtomToken atom = getTag2Atom();
		if (atom != null) {
			return atom.getAtom();
		}
		return null;
	}

	@Override
	public AtomToken getTag2Atom() {
		return (AtomToken) get(Pos.TAGC);
	}
}
