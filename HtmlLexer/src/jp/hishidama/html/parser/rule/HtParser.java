package jp.hishidama.html.parser.rule;

import java.util.*;

import jp.hishidama.html.lexer.rule.HtLexer;
import jp.hishidama.html.lexer.token.ListToken;
import jp.hishidama.html.lexer.token.Tag;
import jp.hishidama.html.lexer.token.Token;
import jp.hishidama.html.parser.elem.HtElement;
import jp.hishidama.html.parser.elem.HtListElement;
import jp.hishidama.html.parser.elem.HtTagElement;
import jp.hishidama.html.parser.elem.HtTokenElement;

/**
 * HtHtmlParser本体.
 *<p>
 * {@link ListToken}を読み込み、タグのツリーを構築する。<br>
 * →<a target="hishidama" href=
 * "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html">使用例</a>
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.02.08
 */
public class HtParser {

	protected boolean DEBUG = false;

	/**
	 * コンストラクター.
	 *
	 * @param name
	 *            要素名
	 */
	protected HtParser(String name) {
		setName(name);
	}

	private String name;

	/**
	 * 要素名設定.
	 *
	 * @param name
	 *            要素名
	 */
	public final void setName(String name) {
		if (name != null) {
			this.name = name.toLowerCase();
		}
	}

	/**
	 * 要素名取得.
	 *
	 * @return 要素名
	 */
	public final String getName() {
		return name;
	}

	protected boolean noBody = false;

	/**
	 * ボディー部を持たない要素かどうかを設定する。
	 *
	 * @param b
	 *            trueの場合、ボディー部を持たない
	 */
	public final void setNoBody(boolean b) {
		noBody = b;
	}

	/**
	 * ボディー部を持たない要素かどうか。
	 *
	 * @return trueの場合、ボディー部を持たない
	 */
	public final boolean hasNoBody() {
		return noBody;
	}

	protected boolean ommitS = false;

	/**
	 * 開始タグを省略可能かどうかを設定する。
	 *
	 * @param b
	 *            trueの場合、省略可能
	 */
	public final void setOmmitS(boolean b) {
		ommitS = b;
	}

	/**
	 * 開始タグを省略可能かどうか。
	 *
	 * @return trueの場合、省略可能
	 */
	public final boolean canOmmitS() {
		return ommitS;
	}

	protected boolean ommitE = false;

	/**
	 * 終了タグを省略可能かどうかを設定する。
	 *
	 * @param b
	 *            trueの場合、省略可能
	 */
	public final void setOmmitE(boolean b) {
		ommitE = b;
	}

	/**
	 * 終了タグを省略可能かどうか。
	 *
	 * @return trueの場合、省略可能
	 */
	public final boolean canOmmitE() {
		return ommitE;
	}

	protected boolean block = false;

	/**
	 * ブロック要素かどうかを設定する。
	 *
	 * @param b
	 *            trueの場合、ブロック要素
	 */
	public final void setBlock(boolean b) {
		block = b;
	}

	/**
	 * ブロック要素かどうか。
	 *
	 * @return trueの場合、ブロック要素
	 */
	public final boolean isBlock() {
		return block;
	}

	protected int prio = 0;

	/**
	 * 優先度取得.
	 *
	 * @return 優先処理における優先度
	 */
	protected final int getPriority() {
		return prio;
	}

	protected HtParserManager manager;

	protected final void setManager(HtParserManager manager) {
		this.manager = manager;
	}

	/**
	 * 解析実行.
	 *
	 * @param tlist
	 *            リストトークン
	 * @return 要素リスト
	 * @see HtLexer#parse()
	 */
	public HtListElement parse(ListToken tlist) {
		List<HtElement> elist = parseInit(tlist);
		parsePriority(elist, -1);
		parseList(elist, -1);

		HtListElement ret = new HtListElement();
		ret.setList(elist);
		return ret;

	}

	/**
	 * トークンリストから要素リストを作り出す。
	 *
	 * @param tlist
	 * @return
	 */
	protected final List<HtElement> parseInit(ListToken tlist) {
		List<HtElement> elist = new ArrayList<HtElement>(tlist.size());
		for (Token t : tlist) {
			if (t instanceof Tag) {
				Tag tag = (Tag) t;
				if (tag.isStart() && tag.isEnd()) {
					// <～/>の場合
					HtTagElement te = new HtTagElement();
					te.setStartTag(tag);
					te.setFix(true);
					elist.add(te);
					continue;
				}
				String name = tag.getName();
				if (name == null || name.isEmpty()) {
					// 名前が無い場合
					HtTagElement ee = new HtTagElement();
					ee.setStartTag(tag);
					ee.setFix(true);
					elist.add(ee);
					continue;
				}

				HtElement e = new HtTokenElement(t);
				e.setFix(false);
				elist.add(e);
			} else {
				// タグ以外（テキスト等）は、デフォルトで確定状態
				HtElement e = new HtTokenElement(t);
				e.setFix(true);
				elist.add(e);
			}
		}
		return elist;
	}

	/**
	 * 優先度の高いタグを解析する。（デフォルトパーサー使用）
	 *
	 * @param elist
	 * @param stag
	 * @return
	 */
	protected final int parsePriority(List<HtElement> elist, int stag) {
		HtParser p = manager.getDefaultParser();
		return p.parsePriority0(elist, stag);
	}

	/**
	 * 優先度の高いタグを解析する。（要素の種類毎）
	 *
	 * @param elist
	 * @param stag
	 * @return
	 */
	protected final int parsePriority0(List<HtElement> elist, int stag) {
		if (elist == null) {
			return -1;
		}
		for (int i = stag + 1; i < elist.size(); i++) {
			HtElement e = elist.get(i);
			if (!e.isTag()) {
				continue;
			}
			if (e.isStart() && e.isEnd()) {
				// 要素の開始と終了の両方がある場合
				// すなわち<tag/>
				// または<tag>～</tag>
				continue;
			}

			int n = parsePriorityEnd(elist, stag, i, e);
			if (n >= 0) {
				i = n;
				return n;
			}

			if (e.isFix()) {
				continue;
			}

			if (e.isStart()) {
				String name = e.getName();
				HtParser p = manager.getParser(name);
				int pr = p.getPriority();
				if (pr > 0 && pr >= this.getPriority()) {
					n = p.parsePriority0(elist, i);
					if (n >= 0) {
						i = n;
						continue;
					}
				}
			}
		}
		return parsePriorityNotFound(elist, stag);
	}

	/**
	 * 優先処理において、終了条件の判定を行う。
	 *
	 * @param elist
	 * @param stag
	 * @param pos
	 *            判定対象要素の位置
	 * @param e
	 *            判定対象要素
	 * @return 終了した場合の位置
	 */
	protected int parsePriorityEnd(List<HtElement> elist, int stag, int pos,
			HtElement e) {
		return -1;
	}

	/**
	 * 優先処理において、終了タグが無かった場合の処理を行う。
	 *
	 * @param elist
	 * @param stag
	 * @return
	 */
	protected int parsePriorityNotFound(List<HtElement> elist, int stag) {
		return -1;
	}

	/**
	 * タグ要素解析.
	 *<p>
	 * タグ要素内部に保持している各要素に対し、解析処理を呼び出す。<br>
	 * その結果、内部全てが確定済みになったらタグ要素自身を確定済みにする。
	 * </p>
	 *
	 * @param te
	 * @return
	 */
	protected boolean parseMain(HtTagElement te) {
		HtParser p = manager.getDefaultParser();
		p.parseList(te.getList(), -1);

		boolean fix = true;

		List<HtElement> list = te.getList();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				HtElement e = list.get(i);
				fix &= e.isFix();
			}
		}

		te.setFix(fix);
		return fix;
	}

	/**
	 * 要素一覧の各要素に対し、解析処理を呼び出す。
	 *
	 * @param elist
	 * @param stag
	 * @return
	 */
	protected void parseList(List<HtElement> elist, int stag) {
		if (elist == null) {
			return;
		}
		parseNoBody(elist, stag);
		parsePair(elist, stag);
		parseProper(elist, stag);
		parseEndTag(elist, stag);
	}

	/**
	 * ボディー部が無い要素で終了タグが見つからないものを確定させる。
	 *
	 * @param elist
	 * @param stag
	 */
	protected final void parseNoBody(List<HtElement> elist, int stag) {
		if (stag != -1) {
			return;
		}

		for (int i = elist.size() - 1; i >= stag + 1; i--) {
			HtElement e = elist.get(i);
			if (e.isFix()) {
				continue;
			}
			if (e instanceof HtTagElement) {
				HtTagElement te = (HtTagElement) e;
				parseMain(te);
				continue;
			}
			if (!e.isEnd()) {
				// 開始タグ<tag>
				String name = e.getName();
				HtParser p = manager.getParser(name);
				if (p.hasNoBody()) {
					// ボディー部が無い要素
					int n = searchEndTag(elist, i + 1, name);
					if (n < 0) {
						// 同名の終了タグが見つからなかった場合
						setFix(elist, i, -1, -1, -1);
					}
				}
			}
		}
	}

	/**
	 * マッチする終了タグを探す。
	 *
	 * @param elist
	 * @param pos
	 * @param name
	 * @return 見つかった場合、その位置
	 */
	protected final int searchEndTag(List<HtElement> elist, int pos, String name) {
		for (int i = pos; i < elist.size(); i++) {
			HtElement e = elist.get(i);
			if (!e.isTag()) {
				continue;
			}
			if (e.isStart() && e.isEnd()) {
				continue;
			}
			if (e.isEnd() && name.equalsIgnoreCase(e.getName())) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 開始タグと閉じタグのペアがあれば、それを確定させる。
	 *
	 * @param elist
	 * @param stag
	 */
	protected final void parsePair(List<HtElement> elist, int stag) {
		if (stag != -1) {
			return;
		}
		for (int i = elist.size() - 1; i >= stag + 1; i--) {
			HtElement e = elist.get(i);
			if (!e.isFix()) {
				if (e instanceof HtTokenElement && !e.isEnd()) {
					// 未確定の開始タグ<tag>の場合
					String name = e.getName();
					HtParser p = manager.getParser(name);
					p.parsePairSub(elist, i);
				}
			}
		}
	}

	/**
	 * 間に確定したものしか無い状態の閉じタグまでを確定させる。
	 *
	 * @param elist
	 * @param stag
	 */
	protected final void parsePairSub(List<HtElement> elist, int stag) {
		assert name != null;

		for (int i = stag + 1; i < elist.size(); i++) {
			HtElement e = elist.get(i);
			if (!e.isTag()) {
				continue;
			}
			if (e.isStart() && e.isEnd()) {
				// 要素の開始と終了の両方がある場合
				// すなわち<tag/>
				// または<tag>～</tag>
				continue;
			}
			if (e.isEnd() && name.equalsIgnoreCase(e.getName())) {
				// 終了タグ</tag>のみ、かつ名前が一致
				setFix(elist, stag, stag + 1, i - 1, i);
				return;
			}

			if (!e.isFix()) {
				return; // 未確定が来た時点で終了
			}
		}

		// 先頭以降が全て確定だったが終了タグが無かった場合
		parsePairNotFound(elist, stag);
	}

	/**
	 * 先頭タグ以降が全て確定だったが終了タグが無かった場合
	 *
	 * @param elist
	 * @param stag
	 */
	protected void parsePairNotFound(List<HtElement> elist, int stag) {
		if (this.hasNoBody()) {
			// ボディー部を持たない要素
			setFix(elist, stag, -1, -1, -1);
		} else if (this.canOmmitE()) {
			// 終了タグを省略可能な要素
			// 先頭以降全てをひとつの要素として確定
			setFix(elist, stag, stag + 1, elist.size() - 1, -1);
		} else {
			if (DEBUG) {
				HtElement e = elist.get(stag);
				Token t = e.getStartTag();
				System.out.printf("check1[%d] %s%n", t.getLine(), e.getName());
			}
		}
	}

	/**
	 * 要素特有ルールによる解析.
	 *
	 * @param elist
	 * @param stag
	 */
	protected final void parseProper(List<HtElement> elist, int stag) {
		if (elist == null) {
			return;
		}

		for (int i = stag + 1; i < elist.size(); i++) {
			HtElement e = elist.get(i);
			if (!e.isTag()) {
				continue;
			}
			int n = parseProperCheckEnd(elist, stag, i, e); // 確定しているかどうか無関係にチェック
			if (n >= 0) {
				i = n;
				return;
			}
			if (e.isStart() && e.isEnd()) {
				continue;
			}
			if (e.isStart()) {
				// 開始タグ
				if (!e.isFix()) {
					HtParser p = manager.getParser(e.getName());
					p.parseProper(elist, i);
				}
			} else {
				// 終了タグ
				if (name != null && name.equalsIgnoreCase(e.getName())) {
					setFix(elist, stag, stag + 1, i - 1, i);
					return;
				}
			}
		}

		// 一通り処理が終わった後
		parseProperNotFound(elist, stag);
	}

	/**
	 * 要素特有ルールによる終了条件判定.
	 *
	 * @param elist
	 * @param stag
	 * @param pos
	 *            判定対象タグの位置
	 * @param he
	 *            判定対象タグ
	 * @return 確定した場合、その位置
	 */
	protected int parseProperCheckEnd(List<HtElement> elist, int stag, int pos,
			HtElement he) {
		return -1;
	}

	/**
	 * 要素特有ルールによって終了せずにリスト内の全タグがスキップされた場合の処理
	 *
	 * @param elist
	 * @param stag
	 */
	protected void parseProperNotFound(List<HtElement> elist, int stag) {
		for (int i = elist.size() - 1; i > stag; i--) {
			HtElement e = elist.get(i);
			if (!e.isFix() && e.isStart() && !e.isEnd()) {
				// 開始タグのみの場合

				HtParser p = manager.getParser(e.getName());
				if (p.hasNoBody()) {
					// ボディー部を持たない要素
					setFix(elist, i, -1, -1, -1);
				} else if (false && p.canOmmitE()) { // TODO
					// 終了タグを省略可能な要素
					// 末尾まで全部をボディー部に入れる
					setFix(elist, i, i + 1, elist.size() - 1, -1);
				} else {
					// 末尾まで全部をボディー部に入れる
					// setFix(elist, i, i + 1, elist.size() - 1, -1);
					if (DEBUG) {
						Token t = e.getStartTag();
						System.out.printf("check3[%d] %s%n", t.getLine(), e
								.getName());
					}
				}
			}
		}
	}

	/**
	 * 未確定の終了タグの処理
	 *
	 * @param elist
	 * @param stag
	 */
	protected final void parseEndTag(List<HtElement> elist, int stag) {
		if (stag != -1) {
			return;
		}

		for (int i = stag + 1; i < elist.size(); i++) {
			HtElement e = elist.get(i);
			if (!e.isFix() && !e.isStart() && e.isEnd()) {
				// 未確定の終了タグ</tag>
				HtParser p = manager.getParser(e.getName());
				int n = p.parseEndTagCheck(elist, i, e);
				if (n >= 0) {
					i = n;
					continue;
				}
			}
		}
	}

	/**
	 * はぐれ終了タグ処理
	 *
	 * @param elist
	 * @param etag
	 *            調査対象の終了タグの位置
	 * @param he
	 *            調査対象の終了タグ
	 * @return 確定した場合、その先頭位置
	 */
	protected int parseEndTagCheck(List<HtElement> elist, int etag, HtElement he) {
		// デフォルトでは、はぐれ終了タグは
		if (this.hasNoBody()) {
			// ボディー部を持たない要素
			setFix(elist, -1, -1, -1, etag);
			return etag;
		} else if (false && this.canOmmitS()) { // TODO
			// 開始タグを省略可能な要素
			// リストの先頭からその終了タグまで全てをブロック化する
			setFix(elist, -1, 0, etag - 1, etag);
			return 0;
		} else {
			// リストの先頭からその終了タグまで全てをブロック化する
			// setFix(elist, -1, 0, etag - 1, etag);
			// return 0;
			if (DEBUG) {
				HtElement e = elist.get(etag);
				Token t = e.getEndTag();
				System.out.printf("check4[%d] %s%n", t.getLine(), e.getName());
			}
			return -1;
		}
	}

	protected void setFix(List<HtElement> elist, int stag, int start, int end,
			int etag) {
		HtTagElement ne = setFix0(elist, stag, start, end, etag);
		parseMain(ne);
	}

	protected HtTagElement setFix0(List<HtElement> elist, int stag, int start,
			int end, int etag) {

		HtTagElement ne = new HtTagElement();
		if (stag >= 0) {
			ne.setStartTag(elist.get(stag).getStartTag());
		}
		if (etag >= 0) {
			ne.setEndTag(elist.get(etag).getEndTag());
		}

		if (start <= end && start > -1) {
			for (int i = start; i <= end; i++) {
				HtElement e = elist.get(i);
				ne.add(e);
			}
		}

		int min = elist.size(), max = -1;
		if (stag >= 0) {
			if (stag < min) {
				min = stag;
			}
			if (stag > max) {
				max = stag;
			}
		}
		if (start <= end) {
			if (start >= 0) {
				if (start < min) {
					min = start;
				}
				if (start > max) {
					max = start;
				}
			}
			if (end >= 0) {
				if (end < min) {
					min = end;
				}
				if (end > max) {
					max = end;
				}
			}
		}
		if (etag >= 0) {
			if (etag < min) {
				min = etag;
			}
			if (etag > max) {
				max = etag;
			}
		}
		for (int i = max; i >= min + 1; i--) {
			elist.remove(i);
		}
		elist.set(min, ne);

		return ne;
	}
}
