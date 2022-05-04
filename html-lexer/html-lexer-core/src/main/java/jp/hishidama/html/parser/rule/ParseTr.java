package jp.hishidama.html.parser.rule;

import java.util.List;

import jp.hishidama.html.parser.elem.HtElement;

/**
 * tr要素パーサー.
 *
 * @since 2009.02.08
 */
public class ParseTr extends ParsePriority {

	public ParseTr(int prio) {
		super("tr", prio);
	}

	@Override
	protected int parsePriorityEnd(List<HtElement> elist, int stag, int pos,
			HtElement e) {
		String name = e.getName();
		if ("thead".equalsIgnoreCase(name) || "tbody".equalsIgnoreCase(name)
				|| "tfoot".equalsIgnoreCase(name)) {
			setFixPriority(elist, stag, stag + 1, pos - 1, -1);
			return stag;
		}

		if (e.isStart()) {
			// 開始タグ
			if ("tr".equalsIgnoreCase(name) || "caption".equalsIgnoreCase(name)
					|| "col".equalsIgnoreCase(name)
					|| "colgroup".equalsIgnoreCase(name)) {
				setFixPriority(elist, stag, stag + 1, pos - 1, -1);
				return stag;
			}
		} else {
			if (getName().equalsIgnoreCase(name)) {
				// 終了タグ</tag>
				setFixPriority(elist, stag, stag + 1, pos - 1, pos);
				return stag;
			}
			if ("table".equalsIgnoreCase(name)) {
				setFixPriority(elist, stag, stag + 1, pos - 1, -1);
				return stag;
			}
		}
		return -1;
	}

	@Override
	protected int parseProperCheckEnd(List<HtElement> elist, int stag, int pos,
			HtElement he) {
		String name = he.getName();
		if ("thead".equalsIgnoreCase(name) || "tbody".equalsIgnoreCase(name)
				|| "tfoot".equalsIgnoreCase(name)) {
			setFix(elist, stag, stag + 1, pos - 1, -1);
			return stag;
		}

		if (he.isStart()) {
			// 開始タグ
			if ("tr".equalsIgnoreCase(name) || "caption".equalsIgnoreCase(name)
					|| "col".equalsIgnoreCase(name)
					|| "colgroup".equalsIgnoreCase(name)) {
				setFix(elist, stag, stag + 1, pos - 1, -1);
				return stag;
			}
		} else {
			// 終了タグ
			if ("table".equalsIgnoreCase(name)) {
				setFix(elist, stag, stag + 1, pos - 1, -1);
				return stag;
			}
		}

		return -1;
	}
}
