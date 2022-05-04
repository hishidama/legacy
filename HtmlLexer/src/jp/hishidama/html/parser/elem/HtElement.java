package jp.hishidama.html.parser.elem;

import java.io.*;

import jp.hishidama.html.lexer.token.ListToken;
import jp.hishidama.html.lexer.token.Tag;
import jp.hishidama.html.lexer.token.Token;

/**
 * HTML�v�f.
 *<p>
 * HtHtmlParser�ɂ�����A�v�f�i�̒��ۃN���X�j�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.02.08
 * @version 2009.02.21
 */
public abstract class HtElement {

	protected HtListElement parent;

	/**
	 * �e�v�f�ݒ�.
	 *
	 * @param parent
	 *            �e�v�f
	 * @since 2009.02.21
	 */
	public void setParent(HtListElement parent) {
		this.parent = parent;
	}

	/**
	 * �e�v�f�擾.
	 *
	 * @return �e�v�f�i�e�������ꍇ�Anull�j
	 * @since 2009.02.21
	 */
	public HtListElement getParent() {
		return parent;
	}

	protected String name;

	/**
	 * �v�f���擾.
	 *
	 * @return �v�f���i�v�f�łȂ��ꍇ��null�j
	 */
	public String getName() {
		return name;
	}

	protected boolean fix;

	/**
	 * �m���Ԑݒ�.
	 *
	 * @param b
	 *            true�̏ꍇ�A�m��
	 */
	public void setFix(boolean b) {
		fix = b;
	}

	/**
	 * �m���Ԏ擾.
	 *
	 * @return true�̏ꍇ�A�m��ς�
	 */
	public boolean isFix() {
		return fix;
	}

	/**
	 * �^�O�̗v�f���ǂ���.
	 *
	 * @return �^�O�̏ꍇ�Atrue
	 */
	public boolean isTag() {
		return false;
	}

	/**
	 * �v�f�̊J�n���ǂ���.
	 *
	 * @return �v�f�̊J�n�̏ꍇ�Atrue
	 */
	public boolean isStart() {
		return false;
	}

	/**
	 * �v�f�̏I�����ǂ���.
	 *
	 * @return �v�f�̏I���̏ꍇ�Atrue
	 */
	public boolean isEnd() {
		return false;
	}

	/**
	 * �J�n�^�O�擾.
	 *
	 * @return �J�n�^�O�i�����ꍇ��null�j
	 */
	public abstract Tag getStartTag();

	/**
	 * �I���^�O�擾.
	 *
	 * @return �I���^�O�i�����ꍇ��null�j
	 */
	public abstract Tag getEndTag();

	/**
	 * ���g���������ǂ���.
	 *
	 * @return ���g�������ꍇ�Atrue
	 * @since 2009.02.21
	 */
	public abstract boolean isEmpty();

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
	 * �g�[�N���T��.
	 * <p>
	 * �w�肳�ꂽ�g�[�N����ێ����Ă���v�f��T���B<br>
	 * ���X�g�g�[�N���i�g�[�N���������̃g�[�N����ێ����Ă���j�̒��܂ł͒T���Ȃ��B
	 * </p>
	 *
	 * @param t
	 *            �g�[�N��
	 * @return �g�[�N�������������ꍇ�A���̃g�[�N����ێ����Ă���v�f�i������Ȃ������ꍇ��null�j
	 * @since 2009.02.15
	 */
	public abstract HtElement searchToken(Token t);

	/**
	 * ���X�g�g�[�N���쐬.
	 * <p>
	 * �v�f���ɕێ����Ă���g�[�N�������X�g�ɒǉ�����B
	 * </p>
	 *
	 * @param tlist
	 *            �o�͐惊�X�g�g�[�N��
	 * @since 2009.02.15
	 */
	public abstract void toToken(ListToken tlist);
}
