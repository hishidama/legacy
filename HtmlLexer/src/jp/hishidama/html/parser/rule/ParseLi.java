package jp.hishidama.html.parser.rule;

import java.util.List;

import jp.hishidama.html.parser.elem.HtElement;

/**
 * li要素パーサー.
 *
 * @since 2009.02.08
 */
public class ParseLi extends ParsePriority {

	public ParseLi(int prio) {
		super("li", prio);
	}

	@Override
	protected int parsePriorityEnd(List<HtElement> elist, int stag, int pos,
			HtElement e) {
		String name = e.getName();
		if (e.isStart()) {
			// 開始タグ
			if (getName().equalsIgnoreCase(name)) {
				setFixPriority(elist, stag, stag + 1, pos - 1, -1);
				return stag;
			}
		} else {
			// 終了タグ</tag>
			if (getName().equalsIgnoreCase(name)) {
				setFixPriority(elist, stag, stag + 1, pos - 1, pos);
				return stag;
			}
			if ("ul".equalsIgnoreCase(name) || "ol".equalsIgnoreCase(name)
					|| "menu".equalsIgnoreCase(name)) {
				setFix0(elist, stag, stag + 1, pos - 1, -1);
				return stag;
			}
		}
		return -1;
	}

	@Override
	protected int parseProperCheckEnd(List<HtElement> elist, int stag, int pos,
			HtElement he) {
		String name = he.getName();
		if (he.isStart()) {
			// 開始タグ
			if ("li".equalsIgnoreCase(name)) {
				setFix(elist, stag, stag + 1, pos - 1, -1);
				return stag;
			}
		} else {
			// 終了タグ
			if ("ul".equalsIgnoreCase(name) || "ol".equalsIgnoreCase(name)
					|| "menu".equalsIgnoreCase(name)) {
				setFix(elist, stag, stag + 1, pos - 1, -1);
				return stag;
			}
		}

		return -1;
	}
}
