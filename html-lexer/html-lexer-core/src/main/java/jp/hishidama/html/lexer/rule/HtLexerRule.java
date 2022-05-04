package jp.hishidama.html.lexer.rule;

import java.io.IOException;
import java.util.*;

import jp.hishidama.html.lexer.reader.Char;
import jp.hishidama.html.lexer.reader.CharType;
import jp.hishidama.html.lexer.token.*;

/**
 * HtHtmlLexerルール.
 *<p>
 * HTMLのタグ・属性・テキストの解釈を行うクラス。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.10
 * @version 2009.02.06
 */
public abstract class HtLexerRule {

	protected HtLexer data;

	protected HtLexerRule(HtLexer data) {
		this.data = data;
	}

	protected class Chars {
		private List<Char> d = new ArrayList<Char>();

		/**
		 * コンストラクター.
		 */
		public Chars() {
		}

		/**
		 * コンストラクター.
		 *
		 * @param c
		 *            文字
		 */
		public Chars(Char c) {
			d.add(c);
		}

		/**
		 * 文字取得.
		 * <p>
		 * インデックスに指定された番号まで文字を読み込む。
		 * </p>
		 *
		 * @param n
		 *            インデックス
		 * @return 文字（必ずnull以外）
		 * @throws IOException
		 */
		public Char get(int n) throws IOException {
			for (int i = d.size(); i <= n; i++) {
				d.add(data.readChar());
			}
			return d.get(n);
		}

		public boolean isType(CharType[] types) throws IOException {
			for (int i = 0; i < types.length; i++) {
				if (get(i).getType() != types[i]) {
					return false;
				}
			}
			return true;
		}

		public StringBuilder clear(int use) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < use; i++) {
				sb.append(d.get(i).getSB());
			}
			unread(use);
			return sb;
		}

		protected void unread(int n) {
			for (int i = d.size() - 1; i >= n; i--) {
				data.unreadChar(d.get(i));
			}
			d.clear();
		}
	}

	/**
	 * ルールに従った解釈を行う。
	 *
	 * @return トークン
	 * @throws IOException
	 */
	public abstract Token parse() throws IOException;

	protected SeparateRule sepRule;

	/**
	 * 区切りルール設定.
	 *
	 * @param sepRule
	 *            区切りルール
	 */
	protected void setSepRule(SeparateRule sepRule) {
		this.sepRule = sepRule;
	}

	/**
	 * 区切り文字判断.
	 *
	 * @param ch
	 *            文字
	 * @return 解釈を終了する文字の場合、true
	 * @throws IOException
	 */
	protected boolean parseEnd(Char ch) throws IOException {
		// if (sepRule != null) {
		return sepRule.isEnd(ch);
		// }
		// return false;
	}

	/**
	 * スキップ文字解釈.
	 * <p>
	 * スペース・タブ・改行のみを抽出する。
	 * </p>
	 *
	 * @return スキップトークン（無い場合はnull）
	 * @throws IOException
	 */
	public SkipToken skipSpace() throws IOException {
		SkipToken skip = null;
		loop: for (;;) {
			Char ch = data.readChar();
			switch (ch.getType()) {
			case SPACE:
				if (skip == null) {
					skip = createSkipToken();
				}
				skip.add(new SpaceAtom(ch.getSB()));
				continue;
			case EOL:
				if (skip == null) {
					skip = createSkipToken();
				}
				skip.add(new CrLfAtom(ch.getSB()));
				continue;
			default:
				data.unreadChar(ch);
				break loop;
			}
		}
		return skip;
	}

	protected SkipToken createSkipToken() {
		return new SkipToken();
	}
}
