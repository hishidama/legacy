package jp.hishidama.html.parser.rule;

import java.util.List;

import jp.hishidama.html.parser.elem.HtElement;

/**
 * body�v�f�p�[�T�[.
 *
 * @since 2009.02.08
 */
public class ParseBody extends ParsePriority {

	public ParseBody(int prio) {
		super("body", prio);
	}

	@Override
	protected int parseProperCheckEnd(List<HtElement> elist, int stag, int pos,
			HtElement he) {
		String name = he.getName();
		if (he.isStart()) {
			// �J�n�^�O
		} else {
			// �I���^�O
			if ("html".equalsIgnoreCase(name)
					|| "noframes".equalsIgnoreCase(name)) {
				setFix(elist, stag, stag + 1, pos - 1, -1);
				return stag;
			}
		}

		return -1;
	}
}
