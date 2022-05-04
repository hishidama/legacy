package jp.hishidama.html.lexer.token;

import java.util.ArrayList;
import java.util.List;

/**
 * HtHtmlLexerトークン（マーク宣言）.
 *<p>
 * マーク宣言（{@literal <!DOCTYPE～>}）を保持するトークン。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.10
 */
public class MarkDeclare extends NamedMarkup {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public MarkDeclare clone() throws CloneNotSupportedException {
		return new MarkDeclare(this);
	}

	protected MarkDeclare(MarkDeclare o) {
		super(o);
	}

	protected static enum Pos {
		TAGO, NAME,
		// VALUE,
		// TAGC,
	}

	/**
	 * コンストラクター.
	 */
	public MarkDeclare() {
	}

	/**
	 * 値追加.
	 *
	 * @param value
	 *            値
	 */
	public void addValue(ValueToken value) {
		addBeforeTagC(value);
	}

	/**
	 * 値一覧取得.
	 *
	 * @return 値一覧（必ずnull以外）
	 */
	public List<ValueToken> getValueList() {
		List<ValueToken> ret = new ArrayList<ValueToken>(list.size());
		for (Token token : list) {
			if (token instanceof ValueToken) {
				ret.add((ValueToken) token);
			}
		}
		return ret;
	}
}
