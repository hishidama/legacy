package jp.hishidama.html.parser.rule;

import java.util.List;

import jp.hishidama.html.parser.elem.HtElement;

/**
 * tr�v�f�p�[�T�[.
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
			// �J�n�^�O
			if ("tr".equalsIgnoreCase(name) || "caption".equalsIgnoreCase(name)
					|| "col".equalsIgnoreCase(name)
					|| "colgroup".equalsIgnoreCase(name)) {
				setFixPriority(elist, stag, stag + 1, pos - 1, -1);
				return stag;
			}
		} else {
			if (getName().equalsIgnoreCase(name)) {
				// �I���^�O</tag>
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
			// �J�n�^�O
			if ("tr".equalsIgnoreCase(name) || "caption".equalsIgnoreCase(name)
					|| "col".equalsIgnoreCase(name)
					|| "colgroup".equalsIgnoreCase(name)) {
				setFix(elist, stag, stag + 1, pos - 1, -1);
				return stag;
			}
		} else {
			// �I���^�O
			if ("table".equalsIgnoreCase(name)) {
				setFix(elist, stag, stag + 1, pos - 1, -1);
				return stag;
			}
		}

		return -1;
	}
}
