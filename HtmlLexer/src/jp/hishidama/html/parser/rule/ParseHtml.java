package jp.hishidama.html.parser.rule;

import java.util.List;

import jp.hishidama.html.parser.elem.HtElement;

/**
 * html要素パーサー.
 *
 * @since 2009.02.11
 */
public class ParseHtml extends ParsePriority {

	public ParseHtml(int prio) {
		super("html", prio);
	}

	@Override
	protected int parsePriorityNotFound(List<HtElement> elist, int stag) {
		setFixPriority(elist, stag, stag + 1, elist.size() - 1, -1);
		return stag;
	}
}
