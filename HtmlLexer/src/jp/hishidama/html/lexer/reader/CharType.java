package jp.hishidama.html.lexer.reader;

/**
 * HtHtmlLexer�����^�C�v.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.01
 */
public enum CharType {
	/** �󔒕��� {@code (space|tab)*} */
	SPACE,
	/** ���s {@code CrLf|Lf|Cr} */
	EOL,
	/** �^�O�J�� {@code <} */
	TAGO,
	/** �^�O�� {@code >} */
	TAGC,
	/** �X���b�V�� {@code /} */
	SLASH,
	/** �G�N�X�N�����[�V�����}�[�N {@code !} */
	EXCL,
	/** �N�G�X�`�����}�[�N {@code ?} */
	QUES,
	/** �u���P�b�g�J�� {@code [} */
	BRAO,
	/** �u���P�b�g�� {@code ]} */
	BRAC,
	/** �n�C�t�� {@code -} */
	HYPHEN,
	/** ���� {@code =} */
	EQ,
	/** �_�u���N�H�[�e�[�V���� {@code "} */
	DQ,
	/** �V���O���N�H�[�e�[�V���� {@code '} */
	SQ,
	/** �G�X�P�[�v {@code \} */
	ESC,
	/** ���̑������� */
	STRING,
	/** EOF */
	EOF
}
