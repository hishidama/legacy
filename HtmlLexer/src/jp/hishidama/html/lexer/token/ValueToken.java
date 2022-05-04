package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexer�g�[�N���i�����l�j.
 * <p>
 * �����l��ێ�����g�[�N���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.10
 */
public class ValueToken extends ListToken {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public ValueToken clone() throws CloneNotSupportedException {
		return new ValueToken(this);
	}

	protected ValueToken(ValueToken o) {
		super(o);
	}

	protected static enum Pos {
		BEGIN, VALUE, END
	}

	/**
	 * �R���X�g���N�^�[.
	 */
	public ValueToken() {
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param q1
	 *            ���N�H�[�e�[�V����
	 * @param text
	 *            �l
	 * @param q2
	 *            �E�N�H�[�e�[�V����
	 * @since 2011.12.10
	 */
	public ValueToken(String q1, String text, String q2) {
		setQuote1(q1);
		setValue(text);
		setQuote2(q2);
	}

	/**
	 * �N�H�[�e�[�V�����ݒ�.
	 * <p>
	 * �����l�̐擪�̃N�H�[�e�[�V�����B
	 * </p>
	 *
	 * @param sb
	 *            �N�H�[�e�[�V����
	 */
	public void setQuote1(StringBuilder sb) {
		if (sb == null) {
			setQuote1Atom(null);
			return;
		}
		AtomToken atom = getQuote1Atom();
		if (atom == null) {
			atom = new AtomToken(sb);
			setQuote1Atom(atom);
		} else {
			atom.setAtom(sb);
		}
	}

	/**
	 * �N�H�[�e�[�V�����ݒ�.
	 * <p>
	 * �����l�̐擪�̃N�H�[�e�[�V�����B
	 * </p>
	 *
	 * @param s
	 *            �N�H�[�e�[�V����
	 */
	public void setQuote1(String s) {
		if (s == null) {
			setQuote1Atom(null);
			return;
		}
		AtomToken atom = getQuote1Atom();
		if (atom == null) {
			atom = new AtomToken(s);
			setQuote1Atom(atom);
		} else {
			atom.setAtom(s);
		}
	}

	/**
	 * �N�H�[�e�[�V�����ݒ�.
	 * <p>
	 * �����l�̐擪�̃N�H�[�e�[�V�����B
	 * </p>
	 *
	 * @param atom
	 *            �N�H�[�e�[�V����
	 */
	public void setQuote1Atom(AtomToken atom) {
		set(Pos.BEGIN, atom);
	}

	/**
	 * �l�ݒ�.
	 *
	 * @param text
	 *            �l
	 */
	public void setValue(TextToken text) {
		set(Pos.VALUE, text);
	}

	/**
	 * �l�ݒ�
	 *
	 * @param text
	 *            �l
	 * @since 2011.12.10
	 */
	public void setValue(String text) {
		if (text == null) {
			setValue((TextToken) null);
		} else {
			TextToken token = new TextToken();
			WordAtom word = new WordAtom(text);
			token.add(word);
			setValue(token);
		}
	}

	/**
	 * �N�H�[�e�[�V�����ݒ�.
	 * <p>
	 * �����l�̖����̃N�H�[�e�[�V�����B
	 * </p>
	 *
	 * @param sb
	 *            �N�H�[�e�[�V����
	 */
	public void setQuote2(StringBuilder sb) {
		if (sb == null) {
			setQuote2Atom(null);
			return;
		}
		AtomToken atom = getQuote2Atom();
		if (atom == null) {
			atom = new AtomToken(sb);
			setQuote2Atom(atom);
		} else {
			atom.setAtom(sb);
		}
	}

	/**
	 * �N�H�[�e�[�V�����ݒ�.
	 * <p>
	 * �����l�̖����̃N�H�[�e�[�V�����B
	 * </p>
	 *
	 * @param s
	 *            �N�H�[�e�[�V����
	 */
	public void setQuote2(String s) {
		AtomToken atom = getQuote2Atom();
		if (atom == null) {
			atom = new AtomToken(s);
			setQuote2Atom(atom);
		} else {
			atom.setAtom(s);
		}
	}

	/**
	 * �N�H�[�e�[�V�����ݒ�.
	 * <p>
	 * �����l�̖����̃N�H�[�e�[�V�����B
	 * </p>
	 *
	 * @param atom
	 *            �N�H�[�e�[�V����
	 */
	public void setQuote2Atom(AtomToken atom) {
		set(Pos.END, atom);
	}

	/**
	 * �N�H�[�e�[�V�����擾.
	 * <p>
	 * �����l�̐擪�̃N�H�[�e�[�V�����B
	 * </p>
	 *
	 * @return �N�H�[�e�[�V�����i���݂��Ȃ��ꍇ�Anull�j
	 */
	public String getQuote1() {
		AtomToken q = getQuote1Atom();
		if (q == null) {
			return null;
		}
		return q.getAtom();
	}

	/**
	 * �N�H�[�e�[�V�����擾.
	 * <p>
	 * �����l�̐擪�̃N�H�[�e�[�V�����B
	 * </p>
	 *
	 * @return �N�H�[�e�[�V�����i���݂��Ȃ��ꍇ�Anull�j
	 */
	public AtomToken getQuote1Atom() {
		return (AtomToken) get(Pos.BEGIN);
	}

	/**
	 * �l�擾.
	 *
	 * @return �l�i���݂��Ȃ��ꍇ�Anull�j
	 */
	public TextToken getValueToken() {
		return (TextToken) get(Pos.VALUE);
	}

	/**
	 * �l�擾.
	 * <p>
	 * �N�H�[�e�[�V�����͊܂܂�Ȃ��B
	 * </p>
	 *
	 * @return �l�i���݂��Ȃ��ꍇ�Anull�j
	 */
	public String getValue() {
		TextToken text = getValueToken();
		if (text != null) {
			return text.getText();
		}
		return null;
	}

	/**
	 * �����l�擾.
	 *
	 * @param quote
	 *            true�̏ꍇ�A�N�H�[�e�[�V�������܂ށB
	 * @return �l�i���݂��Ȃ��ꍇ�Anull�j
	 */
	public String getValue(boolean quote) {
		if (quote) {
			return getText();
		} else {
			return getValue();
		}
	}

	/**
	 * �N�H�[�e�[�V�����擾.
	 * <p>
	 * �����l�̖����̃N�H�[�e�[�V�����B
	 * </p>
	 *
	 * @return �N�H�[�e�[�V�����i���݂��Ȃ��ꍇ�Anull�j
	 */
	public String getQuote2() {
		AtomToken q = getQuote2Atom();
		if (q == null) {
			return null;
		}
		return q.getAtom();
	}

	/**
	 * �N�H�[�e�[�V�����擾.
	 * <p>
	 * �����l�̖����̃N�H�[�e�[�V�����B
	 * </p>
	 *
	 * @return �N�H�[�e�[�V�����i���݂��Ȃ��ꍇ�Anull�j
	 */
	public AtomToken getQuote2Atom() {
		return (AtomToken) get(Pos.END);
	}
}
