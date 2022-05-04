package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexer�g�[�N���i�X�N���v�g�e�L�X�g�j.
 *<p>
 * �X�N���v�g�p�e�L�X�g�i{@literal <script></script>�ň͂܂ꂽ�e�L�X�g}�j���Ӗ�����g�[�N���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.28
 */
public class ScriptToken extends TextToken {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public ScriptToken clone() throws CloneNotSupportedException {
		return new ScriptToken(this);
	}

	protected ScriptToken(ScriptToken o) {
		super(o);
	}

	/**
	 * �R���X�g���N�^�[.
	 */
	public ScriptToken() {
	}
}
