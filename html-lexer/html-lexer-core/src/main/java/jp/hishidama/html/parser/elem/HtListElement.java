package jp.hishidama.html.parser.elem;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import jp.hishidama.html.lexer.token.ListToken;
import jp.hishidama.html.lexer.token.Tag;
import jp.hishidama.html.lexer.token.Token;

/**
 * 要素リスト.
 *<p>
 * HtHtmlParserにおける、要素のリスト。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.02.09
 * @version 2009.02.21
 */
public class HtListElement extends HtElement implements Iterable<HtElement> {

	@Override
	public Tag getStartTag() {
		return null;
	}

	@Override
	public Tag getEndTag() {
		return null;
	}

	protected List<HtElement> list;

	protected class ElemList extends ArrayList<HtElement> {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean add(HtElement element) {
			element.setParent(HtListElement.this);
			return super.add(element);
		}

		@Override
		public void add(int index, HtElement element) {
			element.setParent(HtListElement.this);
			super.add(index, element);
		}

		@Override
		public HtElement set(int index, HtElement element) {
			element.setParent(HtListElement.this);
			return super.set(index, element);
		}
	}

	/**
	 * 要素追加.
	 *
	 * @param he
	 *            要素
	 */
	public void add(HtElement he) {
		if (list == null) {
			list = new ElemList();
		}
		list.add(he);
	}

	/**
	 * 要素追加.
	 *
	 * @param n
	 *            インデックス
	 * @param he
	 *            要素
	 * @since 2009.02.21
	 */
	public void add(int n, HtElement he) {
		if (list == null) {
			list = new ElemList();
		}
		list.add(n, he);
	}

	/**
	 * 要素リスト設定.
	 *
	 * @param elist
	 *            要素のリスト
	 */
	public void setList(List<HtElement> elist) {
		this.list = elist;
		for (HtElement e : elist) {
			e.setParent(this);
		}
	}

	/**
	 * 要素リスト取得.
	 *
	 * @return 要素のリスト
	 */
	public List<HtElement> getList() {
		return list;
	}

	/**
	 * 要素リストのイテレーター取得.
	 */
	@Override
	public Iterator<HtElement> iterator() {
		if (list == null) {
			return Collections.<HtElement> emptyList().iterator();
		}
		return list.iterator();
	}

	/**
	 * 要素数取得.
	 *
	 * @return 保持している要素の個数
	 */
	public int size() {
		if (list == null) {
			return 0;
		}
		return list.size();
	}

	/**
	 * 要素取得.
	 *
	 * @param i
	 *            インデックス
	 * @return 要素（無い場合はnull）
	 */
	public HtElement get(int i) {
		if (list == null) {
			return null;
		}
		if (i < 0 || i >= list.size()) {
			return null;
		}
		return list.get(i);
	}

	/**
	 * 要素位置取得.
	 *
	 * @param he
	 *            要素
	 * @return 要素の位置（無い場合は-1）
	 * @since 2009.02.21
	 */
	public int indexOf(HtElement he) {
		if (list == null) {
			return -1;
		}
		return list.indexOf(he);
	}

	/**
	 * 要素削除.
	 *
	 * @param i
	 *            インデックス
	 * @return 削除された要素（削除されなかった場合はnull）
	 */
	public HtElement remove(int i) {
		if (list == null) {
			return null;
		}
		if (i < 0 || i >= list.size()) {
			return null;
		}
		return list.remove(i);
	}

	@Override
	public boolean isEmpty() {
		return (list == null) || list.isEmpty();
	}

	@Override
	public int getTextLength() {
		int len = 0;
		if (list != null) {
			for (HtElement e : list) {
				if (e != null) {
					len += e.getTextLength();
				}
			}
		}
		return len;
	}

	@Override
	public void writeTo(StringBuilder sb) {
		if (list != null) {
			for (HtElement e : list) {
				if (e != null) {
					e.writeTo(sb);
				}
			}
		}
	}

	@Override
	public void writeTo(Writer w) throws IOException {
		if (list != null) {
			for (HtElement e : list) {
				if (e != null) {
					e.writeTo(w);
				}
			}
		}
	}

	@Override
	public HtElement searchToken(Token t) {
		if (list != null) {
			for (HtElement e : list) {
				if (e != null) {
					HtElement r = e.searchToken(t);
					if (r != null) {
						return r;
					}
				}
			}
		}
		return null;
	}

	@Override
	public void toToken(ListToken tlist) {
		if (list != null) {
			for (HtElement e : list) {
				if (e != null) {
					e.toToken(tlist);
				}
			}
		}
	}
}
