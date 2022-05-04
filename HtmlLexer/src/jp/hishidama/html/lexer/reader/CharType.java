package jp.hishidama.html.lexer.reader;

/**
 * HtHtmlLexer文字タイプ.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.01
 */
public enum CharType {
	/** 空白文字 {@code (space|tab)*} */
	SPACE,
	/** 改行 {@code CrLf|Lf|Cr} */
	EOL,
	/** タグ開き {@code <} */
	TAGO,
	/** タグ閉じ {@code >} */
	TAGC,
	/** スラッシュ {@code /} */
	SLASH,
	/** エクスクラメーションマーク {@code !} */
	EXCL,
	/** クエスチョンマーク {@code ?} */
	QUES,
	/** ブラケット開き {@code [} */
	BRAO,
	/** ブラケット閉じ {@code ]} */
	BRAC,
	/** ハイフン {@code -} */
	HYPHEN,
	/** 等号 {@code =} */
	EQ,
	/** ダブルクォーテーション {@code "} */
	DQ,
	/** シングルクォーテーション {@code '} */
	SQ,
	/** エスケープ {@code \} */
	ESC,
	/** その他文字列 */
	STRING,
	/** EOF */
	EOF
}
