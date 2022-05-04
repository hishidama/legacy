package jp.hishidama.html.parser.rule;

import java.util.List;

import jp.hishidama.html.parser.elem.HtElement;

/**
 * rb,rp,rt�v�f�p�[�T�[.
 *
 * @since 2009.02.08
 */
public class ParseRb extends HtParser {

	public ParseRb(String name) {
		super(name);
	}

	@Override
	protected int parseProperCheckEnd(List<HtElement> elist, int stag, int pos,
			HtElement he) {
		String name = he.getName();
		if (he.isStart()) {
			// �J�n�^�O
			if ("rb".equalsIgnoreCase(name) || "rp".equalsIgnoreCase(name)
					|| "rt".equalsIgnoreCase(name)) {
				setFix(elist, stag, stag + 1, pos - 1, -1);
				return stag;
			}
		} else {
			// �I���^�O
			if ("ruby".equalsIgnoreCase(name)) {
				setFix(elist, stag, stag + 1, pos - 1, -1);
				return stag;
			}
		}

		return -1;
	}
}
