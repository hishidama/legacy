package jp.hishidama.html.parser.elem;

import java.io.*;
import java.util.*;

import jp.hishidama.html.lexer.token.CrLfAtom;
import jp.hishidama.html.lexer.token.ListToken;
import jp.hishidama.html.lexer.token.SkipToken;
import jp.hishidama.html.lexer.token.SpaceAtom;
import jp.hishidama.html.lexer.token.Tag;
import jp.hishidama.html.lexer.token.TextToken;
import jp.hishidama.html.lexer.token.Token;
import jp.hishidama.html.parser.rule.HtParser;

/**
 * HtElementユーティリティー.
 *<p>
 * HtHtmlParserにおける要素のユーティリティー。
 * </p>
 * <p>
 * 行番号を出力するメソッドについては、パース前に{@link Token#calcLine(int)}によって行番号をセットしておく必要がある。<br>
 * </p>
 *
 * <pre>
 * ListToken tlist = lexer.parse();
 * tlist.calcLine(1);
 * HtParser parser = new HtParserManager().getDefaultParser();
 * HtListElement elist = parser.parse(tlist);
 *
 * HtElementUtil.dumpTree(elist, -1);
 * HtElementUtil.dumpNotFix(elist);
 * </pre>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>;
 * @since 2009.02.11
 * @version 2009.02.21
 */
public class HtElementUtil {

	/**
	 * 要素のデバッグダンプ.
	 * <p>
	 * 出力先は標準出力。
	 * </p>
	 *
	 * @param he
	 *            要素
	 * @param tab
	 *            インデント数
	 * @see #dumpTree(HtElement, int, PrintStream)
	 */
	public static void dumpTree(HtElement he, int tab) {
		dumpTree(he, tab, System.out);
	}

	/**
	 * 要素のデバッグダンプ.
	 *
	 * @param he
	 *            要素
	 * @param tab
	 *            インデント数
	 * @param f
	 *            出力先ファイル名
	 * @throws IOException
	 * @see #dumpTree(HtElement, int, PrintStream)
	 */
	public static void dumpTree(HtElement he, int tab, File f)
			throws IOException {
		PrintStream out = null;
		try {
			out = new PrintStream(f);
			dumpTree(he, tab, out);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 要素のデバッグダンプ.
	 *
	 * @param he
	 *            要素
	 * @param tab
	 *            インデント数
	 * @param f
	 *            出力先ファイル名
	 * @param encoding
	 *            出力エンコーディング
	 * @throws IOException
	 * @see #dumpTree(HtElement, int, PrintStream)
	 * @since 2009.02.15
	 */
	public static void dumpTree(HtElement he, int tab, File f, String encoding)
			throws IOException {
		PrintStream out = null;
		try {
			out = new PrintStream(f, encoding);
			dumpTree(he, tab, out);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 要素のデバッグダンプ.
	 *
	 * @param he
	 *            要素
	 * @param tab
	 *            インデント数
	 * @param out
	 *            出力先
	 */
	public static void dumpTree(HtElement he, int tab, PrintStream out) {
		if (he instanceof HtListElement) {
			HtListElement hle = (HtListElement) he;
			Tag stag = hle.getStartTag();
			Tag etag = hle.getEndTag();
			if (stag != null) {
				String name = (stag != null) ? stag.getName() : "null";
				printTab(out, tab);
				out.printf("<%s> [%d]%b%n", name, stag.getLine(), hle.isFix());
			} else if (etag != null) {
				String name = etag.getName();
				printTab(out, tab);
				out.printf("(%s) [%d]%b%n", name, etag.getLine(), hle.isFix());
			}

			List<HtElement> elist = hle.getList();
			if (elist != null) {
				for (HtElement e : elist) {
					dumpTree(e, tab + 1, out);
				}
			}
			if (etag != null) {
				String ename = etag.getName();
				int line = etag.getLine();
				printTab(out, tab);
				out.printf("</%s> [%d]%b%n", ename, line, hle.isFix());
			} else if (stag != null) {
				String ename = stag.getName();
				int line = stag.getLine();
				printTab(out, tab);
				out.printf("(/%s) [%d]%b%n", ename, line, hle.isFix());
			}
		} else if (he instanceof HtTokenElement) {
			HtTokenElement het = (HtTokenElement) he;
			Token t = het.getToken();
			String s = t.getText();
			s = s.replaceAll("[\r\n]+", " ").trim();
			if (s.isEmpty()) {
				return;
			}
			String name = t.getClass().getSimpleName();
			int line = t.getLine();
			printTab(out, tab);
			out.printf("%s[%d]%b: %s%n", name, line, he.isFix(), s);
		}
	}

	protected static void printTab(PrintStream out, int tab) {
		for (int i = 0; i < tab; i++) {
			out.print("  ");
		}
	}

	/**
	 * 未確定トークン出力.
	 * <p>
	 * 出力先は標準出力。
	 * </p>
	 *
	 * @param he
	 *            要素
	 * @see #dumpNotFix(HtElement, PrintStream)
	 */
	public static void dumpNotFix(HtElement he) {
		dumpNotFix(he, System.out);
	}

	/**
	 * 未確定トークン出力.
	 *<p>
	 * {@link HtParser#parse(jp.hishidama.html.lexer.token.ListToken)
	 * HtHtmlParser}によって確定できなかったトークン要素のみを出力する。<br>
	 * このメソッドによって表示されるのは、たいていはマッチするタグが見つからないタグ。
	 * </p>
	 *
	 * @param he
	 *            要素
	 * @param out
	 *            出力先
	 * @since 2009.02.15
	 */
	public static void dumpNotFix(HtElement he, PrintStream out) {
		if (he instanceof HtTokenElement) {
			if (!he.isFix()) {
				HtTokenElement te = (HtTokenElement) he;
				Token t = te.getToken();
				out.printf("[%d]%s%n", t.getLine(), t.getText());
			}
			return;
		}
		if (he instanceof HtListElement) {
			HtListElement elist = (HtListElement) he;
			for (HtElement e : elist) {
				dumpNotFix(e, out);
			}
		}
	}

	/**
	 * リストトークン作成.
	 * <p>
	 * 要素内に保持しているトークンをリストにする。
	 * </p>
	 *
	 * @param he
	 *            要素
	 * @return リストトークン
	 * @since 2009.02.15
	 */
	public static ListToken toToken(HtElement he) {
		ListToken tlist = new ListToken();
		he.toToken(tlist);
		return tlist;
	}

	/**
	 * 要素内先頭空白分割.
	 * <p>
	 * タグ要素内の先頭の空白を要素の外に移動させる。
	 * </p>
	 *
	 * @param te
	 *            タグ要素
	 * @since 2009.02.21
	 */
	public static void splitFirstSkip(HtTagElement te) {
		HtTokenElement ce = cutFirstSkip(te);
		if (ce != null) {
			HtListElement pe = te.getParent();
			int n = pe.indexOf(te);
			pe.add(n, ce);
		}
	}

	/**
	 * 先頭空白削除.
	 * <p>
	 * 要素内の先頭の空白を削除する。
	 * </p>
	 *
	 * @param he
	 *            要素
	 * @return 削除された空白（削除されなかった場合はnull）
	 * @since 2009.02.21
	 */
	public static HtTokenElement cutFirstSkip(HtElement he) {
		if (he instanceof HtTokenElement) {
			HtTokenElement te = (HtTokenElement) he;
			Token t = te.getToken();
			if (t instanceof TextToken) {
				TextToken tt = (TextToken) t;
				TextToken c = cutFirstSkip(tt);
				if (c == tt) {
					return te;
				}
				HtTokenElement ce = new HtTokenElement(c);
				ce.setFix(te.isFix());
				return ce;
			}
		} else if (he instanceof HtListElement) {
			HtListElement le = (HtListElement) he;
			HtElement e = le.get(0);
			if (e != null) {
				HtTokenElement ce = cutFirstSkip(e);
				if (ce == e || e.isEmpty()) {
					le.remove(0);
				}
				return ce;
			}
		}
		return null;
	}

	static TextToken cutFirstSkip(TextToken tt) {
		if (tt instanceof SkipToken) {
			return tt;
		}
		int e = -1;
		for (int i = 0; i < tt.size(); i++) {
			Token t = tt.get(i);
			if (t instanceof SkipToken || t instanceof SpaceAtom
					|| t instanceof CrLfAtom) {
				e = i;
			} else {
				break;
			}
		}
		if (e >= 0) {
			if (e == tt.size() - 1) {
				return tt;
			}
			ListToken skip = tt.cut(0, e);
			TextToken nt = new TextToken(skip.size());
			for (Token t : skip) {
				nt.add(t);
			}
			return nt;
		}
		return null;
	}

	/**
	 * 要素内末尾空白分割.
	 * <p>
	 * タグ要素内の末尾の空白を要素の外に移動させる。
	 * </p>
	 *
	 * @param te
	 *            タグ要素
	 * @since 2009.02.21
	 */
	public static void splitLastSkip(HtTagElement te) {
		HtTokenElement ce = cutLastSkip(te);
		if (ce != null) {
			HtListElement pe = te.getParent();
			int n = pe.indexOf(te);
			pe.add(n + 1, ce);
		}
	}

	/**
	 * 末尾空白削除.
	 * <p>
	 * 要素内の末尾の空白を削除する。
	 * </p>
	 *
	 * @param he
	 *            要素
	 * @return 削除された空白（削除されなかった場合はnull）
	 * @since 2009.02.21
	 */
	public static HtTokenElement cutLastSkip(HtElement he) {
		if (he instanceof HtTokenElement) {
			HtTokenElement te = (HtTokenElement) he;
			Token t = te.getToken();
			if (t instanceof TextToken) {
				TextToken tt = (TextToken) t;
				TextToken c = cutLastSkip(tt);
				if (c == tt) {
					return te;
				}
				HtTokenElement ce = new HtTokenElement(c);
				ce.setFix(te.isFix());
				return ce;
			}
		} else if (he instanceof HtListElement) {
			HtListElement le = (HtListElement) he;
			int n = le.size() - 1;
			HtElement e = le.get(n);
			if (e != null) {
				HtTokenElement ce = cutLastSkip(e);
				if (ce == e || e.isEmpty()) {
					le.remove(n);
				}
				return ce;
			}
		}
		return null;
	}

	static TextToken cutLastSkip(TextToken tt) {
		if (tt instanceof SkipToken) {
			return tt;
		}
		int s = -1;
		for (int i = tt.size() - 1; i >= 0; i--) {
			Token t = tt.get(i);
			if (t instanceof SkipToken || t instanceof SpaceAtom
					|| t instanceof CrLfAtom) {
				s = i;
			} else {
				break;
			}
		}
		if (s >= 0) {
			if (s == 0) {
				return tt;
			}
			ListToken skip = tt.cut(s, tt.size() - 1);
			TextToken nt = new TextToken(skip.size());
			for (Token t : skip) {
				nt.add(t);
			}
			return nt;
		}
		return null;
	}
}
