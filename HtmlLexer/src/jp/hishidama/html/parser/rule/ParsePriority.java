package jp.hishidama.html.parser.rule;

import java.util.List;

import jp.hishidama.html.parser.elem.HtElement;
import jp.hishidama.html.parser.elem.HtTagElement;

/**
 * 優先処理を行う要素のパーサー.
 *
 * @since 2009.02.11
 */
public class ParsePriority extends HtParser {

	public ParsePriority(String name, int prio) {
		super(name);
		this.prio = prio;
	}

	protected void setFixPriority(List<HtElement> elist, int stag, int start,
			int end, int etag) {
		HtTagElement ne = setFix0(elist, stag, start, end, etag);
		parsePriority(ne.getList(), -1);
	}

	@Override
	protected int parsePriorityEnd(List<HtElement> elist, int stag, int pos,
			HtElement e) {
		if (e.isEnd()) {
			String name = e.getName();
			if (getName().equalsIgnoreCase(name)) {
				// 終了タグ</tag>
				setFixPriority(elist, stag, stag + 1, pos - 1, pos);
				return stag;
			}
		}
		return -1;
	}

	@Override
	protected int parsePriorityNotFound(List<HtElement> elist, int stag) {
		// if (this.canOmmitE()) {
		setFixPriority(elist, stag, stag + 1, elist.size() - 1, -1);
		return stag;
		// }
		// return -1;
	}
}
