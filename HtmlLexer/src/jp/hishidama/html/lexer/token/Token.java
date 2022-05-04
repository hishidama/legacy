package jp.hishidama.html.lexer.token;

import java.io.IOException;
import java.io.Writer;

/**
 * HtHtmlLexer�g�[�N��.
 * <p>
 * HTML�̃^�O�E�����E�e�L�X�g��\���N���X�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.10
 */
public abstract class Token implements Cloneable {

	protected int line;

	/**
	 * @since 2009.02.07
	 */
	@Override
	public abstract Token clone() throws CloneNotSupportedException;

	protected Token(Token o) {
		this.line = o.line;
	}

	protected Token() {
	}

	/**
	 * ������擾.
	 *<p>
	 * �ێ����Ă���g�[�N����String�ɂ��ĕԂ��B
	 * </p>
	 *
	 * @return ������i�K��null�ȊO�j
	 * @see #writeTo(StringBuilder)
	 */
	public final String getText() {
		StringBuilder sb = new StringBuilder(getTextLength());
		writeTo(sb);
		return sb.toString();
	}

	/**
	 * �����񒷎擾.
	 *
	 * @return ������
	 */
	public abstract int getTextLength();

	/**
	 * ������o��.
	 *<p>
	 * �ێ����Ă���g�[�N���𕶎���ɂ��ďo�͂���B
	 * </p>
	 *
	 * @param sb
	 *            �o�͐�o�b�t�@�[
	 */
	public abstract void writeTo(StringBuilder sb);

	/**
	 * ������o��.
	 * <p>
	 * �ێ����Ă���g�[�N���𕶎���ɂ���Writer�ɏo�͂���B
	 * </p>
	 *
	 * @param w
	 *            Writer
	 * @throws IOException
	 */
	public abstract void writeTo(Writer w) throws IOException;

	/**
	 * �s�ԍ��Z�o.
	 *
	 * @param n
	 *            �J�n�s�ԍ�
	 * @return ���̃g�[�N�����n�܂�s�ԍ�
	 */
	public int calcLine(int n) {
		line = n;
		return n;
	}

	/**
	 * �s�ԍ��擾.
	 * <p>
	 * {@link #calcLine(int)}�ɂ���ăZ�b�g���ꂽ��łȂ��ƈӖ��������Ȃ��B
	 * </p>
	 *
	 * @return �s�ԍ�
	 */
	public int getLine() {
		return line;
	}
}
