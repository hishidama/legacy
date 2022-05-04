package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexer�g�[�N���i�e�L�X�g�j.
 *<p>
 * �e�L�X�g�i������{@link WordAtom ������}�j��ێ�����g�[�N���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.10
 * @version 2009.02.21
 */
public class TextToken extends ListToken {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public TextToken clone() throws CloneNotSupportedException {
		return new TextToken(this);
	}

	protected TextToken(TextToken o) {
		super(o);
	}

	/**
	 * �R���X�g���N�^�[.
	 */
	public TextToken() {
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param size
	 *            �������X�g�̏����T�C�Y
	 * @since 2009.02.21
	 */
	public TextToken(int size) {
		super(size);
	}

	/**
	 * ������ǉ�.
	 *
	 * @param word
	 *            ������
	 */
	public void add(WordAtom word) {
		super.add(word);
	}

	/**
	 * ������ݒ�.
	 *<p>
	 * �����ɕێ����Ă��郊�X�g���A�n���ꂽ�e�L�X�g���̃��X�g�ɒu��������B
	 * </p>
	 *
	 * @param text
	 *            �e�L�X�g
	 */
	public void setText(TextToken text) {
		list.clear();
		for (int i = 0; i < text.size(); i++) {
			this.add(text.get(i));
		}
	}
}
