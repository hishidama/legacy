package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexer�g�[�N���i�����j.
 * <p>
 * �����i�������Ƒ����l�̑g�j���P�ێ�����g�[�N���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.10
 * @see NameAtom
 * @see ValueToken
 */
public class AttributeToken extends ListToken {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public AttributeToken clone() throws CloneNotSupportedException {
		return new AttributeToken(this);
	}

	protected AttributeToken(AttributeToken o) {
		super(o);
	}

	protected static enum Pos {
		NAME, SKIP1, LET, SKIP2, VALUE
	}

	/**
	 * �R���X�g���N�^�[.
	 */
	public AttributeToken() {
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param name
	 *            ������
	 * @since 2011.12.10
	 */
	public AttributeToken(String name) {
		setName(name);
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param name
	 *            ������
	 * @param q1
	 *            ���N�H�[�e�[�V����
	 * @param value
	 *            �����l
	 * @param q2
	 *            �E�N�H�[�e�[�V����
	 * @since 2011.12.10
	 */
	public AttributeToken(String name, String q1, String value, String q2) {
		setName(name);
		setLet("=");
		setValue(q1, value, q2);
	}

	/**
	 * �������ݒ�.
	 *
	 * @param name
	 *            ������
	 */
	public void setName(NameAtom name) {
		set(Pos.NAME, name);
	}

	/**
	 * �������ݒ�.
	 *
	 * @param s
	 *            ������
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
	 * �󔒐ݒ�.
	 * <p>
	 * �������Ƒ���L���̊Ԃ̋󔒁B
	 * </p>
	 *
	 * @param skip
	 *            ��
	 */
	public void setSkip1(SkipToken skip) {
		set(Pos.SKIP1, skip);
	}

	/**
	 * ����L���ݒ�.
	 *
	 * @param word
	 *            ����L��
	 */
	public void setLet(WordAtom word) {
		set(Pos.LET, word);
	}

	/**
	 * ����L���ݒ�.
	 *
	 * @param s
	 *            ����L���i���ʂ́u=�v�j
	 */
	public void setLet(String s) {
		setLet(new WordAtom(s));
	}

	/**
	 * �󔒐ݒ�.
	 * <p>
	 * ����L���Ƒ����l�̊Ԃ̋󔒁B
	 * </p>
	 *
	 * @param skip
	 *            ��
	 */
	public void setSkip2(SkipToken skip) {
		set(Pos.SKIP2, skip);
	}

	/**
	 * �����l�ݒ�.
	 *
	 * @param value
	 *            �����l
	 */
	public void setValue(ValueToken value) {
		set(Pos.VALUE, value);
	}

	/**
	 * �����l�ݒ�.
	 *
	 * @param q1
	 *            ���N�H�[�e�[�V����
	 * @param value
	 *            �����l
	 * @param q2
	 *            �E�N�H�[�e�[�V����
	 * @since 2011.12.10
	 */
	public void setValue(String q1, String value, String q2) {
		ValueToken token = new ValueToken(q1, value, q2);
		setValue(token);
	}

	/**
	 * �������擾.
	 *
	 * @return �������i���݂��Ȃ��ꍇ�Anull�j
	 */
	public NameAtom getNameAtom() {
		return (NameAtom) get(Pos.NAME);
	}

	/**
	 * �������擾.
	 *
	 * @return �������i���݂��Ȃ��ꍇ�Anull�j
	 */
	public String getName() {
		NameAtom name = getNameAtom();
		if (name != null) {
			return name.getName();
		}
		return null;
	}

	/**
	 * �󔒎擾.
	 * <p>
	 * �������Ƒ���L���̊Ԃ̋󔒁B
	 * </p>
	 *
	 * @return �󔒁i���݂��Ȃ��ꍇ�Anull�j
	 */
	public SkipToken getSkip1Token() {
		return (SkipToken) get(Pos.SKIP1);
	}

	/**
	 * �󔒎擾.
	 * <p>
	 * �������Ƒ���L���̊Ԃ̋󔒁B
	 * </p>
	 *
	 * @return �󔒁i���݂��Ȃ��ꍇ�Anull�j
	 */
	public String getSkip1() {
		SkipToken skip1 = getSkip1Token();
		if (skip1 != null) {
			return skip1.getText();
		}
		return null;
	}

	/**
	 * ����L���擾.
	 *
	 * @return ����L���i���݂��Ȃ��ꍇ�Anull�j
	 */
	public WordAtom getLetToken() {
		return (WordAtom) get(Pos.LET);
	}

	/**
	 * ����L���擾.
	 *
	 * @return ����L���i���݂��Ȃ��ꍇ�Anull�j
	 */
	public String getLet() {
		WordAtom eq = getLetToken();
		if (eq != null) {
			return eq.getString();
		}
		return null;
	}

	/**
	 * �󔒎擾.
	 * <p>
	 * ����L���Ƒ����l�̊Ԃ̋󔒁B
	 * </p>
	 *
	 * @return �󔒁i���݂��Ȃ��ꍇ�Anull�j
	 */
	public SkipToken getSkip2Token() {
		return (SkipToken) get(Pos.SKIP2);
	}

	/**
	 * �󔒎擾.
	 * <p>
	 * ����L���Ƒ����l�̊Ԃ̋󔒁B
	 * </p>
	 *
	 * @return �󔒁i���݂��Ȃ��ꍇ�Anull�j
	 */
	public String getSkip2() {
		SkipToken skip2 = getSkip2Token();
		if (skip2 != null) {
			return skip2.getText();
		}
		return null;
	}

	/**
	 * �����l�擾.
	 *
	 * @return �����l�i���݂��Ȃ��ꍇ�Anull�j
	 */
	public ValueToken getValueToken() {
		return (ValueToken) get(Pos.VALUE);
	}

	/**
	 * �����l�擾.
	 *
	 * @return �����l�i���݂��Ȃ��ꍇ�Anull�j
	 */
	public String getValue() {
		ValueToken value = getValueToken();
		if (value != null) {
			return value.getValue();
		}
		return null;
	}
}
