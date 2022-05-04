package jp.hishidama.html.lexer.token;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * HtHtmlLexerトークン（リスト）.
 *<p>
 * {@link Token HtHtmlLexerトークン}のうち、内部に他のトークンを保持するトークン。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.10
 * @see AtomToken
 */
public class ListToken extends Token implements Iterable<Token> {

	protected final List<Token> list;

	/**
	 * @since 2009.02.07
	 */
	@Override
	public ListToken clone() throws CloneNotSupportedException {
		return new ListToken(this);
	}

	protected ListToken(ListToken o) {
		super(o);
		list = new ArrayList<Token>(o.list.size());
		for (int i = 0; i < o.list.size(); i++) {
			try {
				Token t = o.list.get(i);
				if (t != null) {
					t = t.clone();
				}
				list.add(t);
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * コンストラクター.
	 */
	public ListToken() {
		this(8);
	}

	/**
	 * コンストラクター.
	 *
	 * @param size
	 *            内部リストの初期サイズ
	 */
	public ListToken(int size) {
		list = new ArrayList<Token>(size);
	}

	/**
	 * トークン追加.
	 *<p>
	 * 内部リストの末尾にトークンを追加する。
	 * </p>
	 *
	 * @param token
	 *            トークン
	 */
	public void add(Token token) {
		list.add(token);
	}

	/**
	 * トークン追加.
	 * <p>
	 * 内部リストの指定位置にトークンを挿入する。
	 * </p>
	 *
	 * @param n
	 *            追加位置
	 * @param token
	 *            トークン
	 */
	public void add(int n, Token token) {
		if (n < 0) {
			n = 0;
		}
		if (n >= list.size()) {
			add(token);
		} else {
			list.add(n, token);
		}
	}

	protected void set(Enum<?> en, Token token) {
		set(en.ordinal(), token);
	}

	/**
	 * トークン設定.
	 * <p>
	 * 内部リストの指定位置にトークンをセットする。
	 * </p>
	 *
	 * @param n
	 *            設定位置
	 * @param token
	 *            トークン
	 */
	public void set(int n, Token token) {
		for (int i = list.size(); i <= n; i++) {
			list.add(null);
		}
		list.set(n, token);
	}

	protected Token get(Enum<?> en) {
		return get(en.ordinal());
	}

	/**
	 * トークン取得.
	 *
	 * @param n
	 *            位置
	 * @return トークン
	 */
	public Token get(int n) {
		if (n >= list.size()) {
			return null;
		}
		return list.get(n);
	}

	/**
	 * 末尾トークン取得.
	 *
	 * @return トークン
	 */
	public Token getLast() {
		int n = list.size() - 1;
		if (n >= 0) {
			return list.get(n);
		}
		return null;
	}

	/**
	 * トークン数取得.
	 * <p>
	 * 内部で保持しているトークンの個数を返す。
	 * </p>
	 *
	 * @return 個数
	 */
	public int size() {
		return list.size();
	}

	@Override
	public int getTextLength() {
		int len = 0;
		for (Token token : list) {
			if (token != null) {
				len += token.getTextLength();
			}
		}
		return len;
	}

	@Override
	public void writeTo(StringBuilder sb) {
		for (Token token : list) {
			if (token != null) {
				token.writeTo(sb);
			}
		}
	}

	@Override
	public void writeTo(Writer w) throws IOException {
		for (Token token : list) {
			if (token != null) {
				token.writeTo(w);
			}
		}
	}

	@Override
	public int calcLine(int n) {
		line = n;
		for (Token token : list) {
			if (token != null) {
				n = token.calcLine(n);
			}
		}
		return n;
	}

	@Override
	public Iterator<Token> iterator() {
		return list.iterator();
	}

	/**
	 * トークン削除.
	 *<p>
	 * リストに直接保持しているトークンを削除する。
	 * </p>
	 *
	 * @param t
	 *            削除対象トークン
	 * @return 削除できた場合、そのトークン<br>
	 *         削除対象が無かった場合はnull
	 */
	public Token remove(Token t) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == t) {
				return list.remove(i);
			}
		}
		return null;
	}

	/**
	 * トークン切り出し.
	 * <p>
	 * 指定された範囲のトークンを削除する。
	 * </p>
	 *
	 * @param start
	 *            切り出し開始トークン（nullの場合は先頭）
	 * @param end
	 *            切り出し終了トークン（nullの場合は末尾）
	 * @return 指定された範囲のトークン。<br>
	 *         start又はendが見つからなかった場合や逆転していた場合はnull
	 */
	public ListToken cut(Token start, Token end) {
		int s = -1, e = -1;
		if (start == null) {
			s = 0;
		}
		if (end == null) {
			e = list.size() - 1;
			if (e < 0)
				return null;
		}

		if (s < 0 || e < 0) {
			for (int i = 0; i < list.size(); i++) {
				Token t = list.get(i);
				if (t == null) {
					continue;
				}
				if (t == start) {
					s = i;
					if (e >= 0)
						break;
				}
				if (t == end) {
					e = i;
					if (s >= 0)
						break;
				}
			}
		}
		return cut(s, e);
	}

	/**
	 * トークン切り出し.
	 * <p>
	 * 指定された範囲のトークンを削除する。
	 * </p>
	 *
	 * @param s
	 *            切り出し開始位置
	 * @param e
	 *            切り出し終了位置
	 * @return 指定された範囲のトークン。<br>
	 *         s又はeが範囲外の場合や逆転していた場合はnull
	 */
	public ListToken cut(int s, int e) {
		if (s < 0 || e < 0 || e >= list.size() || s > e) {
			return null;
		}
		return cut0(s, e);
	}

	protected ListToken cut0(int s, int e) {
		ListToken n = new ListToken(e - s);
		for (int i = s; i <= e; i++) {
			Token t = list.remove(s);
			if (t != null) {
				n.add(t);
			}
		}
		return n;
	}

	/**
	 * トークン切り出し.
	 * <p>
	 * 指定されたトークンを削除する。<br>
	 * その際、そのトークン直前にスペース・改行があったらそれも含める。
	 * </p>
	 *
	 * @param token
	 *            切り出すトークン
	 * @return 切り出したトークン。<br>
	 *         見つからなかった場合はnull
	 */
	public ListToken cutWithPreSkip(Token token) {
		int s = -1;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == token) {
				s = i;
				break;
			}
		}
		if (s < 0) {
			return null;
		}
		int e = s;

		while (s > 0) {
			Token t = list.get(s - 1);
			if (t instanceof SkipToken || t instanceof SpaceAtom
					|| t instanceof CrLfAtom) {
				s--;
				continue;
			}
			break;
		}

		return cut0(s, e);
	}
}
