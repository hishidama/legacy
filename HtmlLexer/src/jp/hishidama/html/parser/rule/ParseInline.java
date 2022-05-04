package jp.hishidama.html.parser.rule;

import java.util.List;

import jp.hishidama.html.parser.elem.HtElement;

/**
 * インライン要素しか持たない要素のパーサー.
 *
 * @since 2009.02.08
 */
public class ParseInline extends HtParser {

	public ParseInline(String name) {
		super(name);
	}

	@Override
	protected void parsePairNotFound(List<HtElement> elist, int stag) {
		for (int i = stag + 1; i < elist.size(); i++) {
			HtElement e = elist.get(i);
			if (!e.isTag()) {
				continue;
			}
			if (e.isStart()) {
				HtParser p = manager.getParser(e.getName());
				if (p.isBlock()) {
					// 来た開始タグがブロックの場合
					setFix(elist, stag, stag + 1, i - 1, -1);
					return;
				}
			}
		}
		// ブロック要素が無かった場合
		// 先頭以降全てをひとつの要素として確定
		setFix(elist, stag, stag + 1, elist.size() - 1, -1);
	}

	@Override
	protected int parseProperCheckEnd(List<HtElement> elist, int stag, int pos,
			HtElement he) {
		if (this.canOmmitE() && he.isStart()) {
			HtParser p = manager.getParser(he.getName());
			if (p.isBlock()) {
				// 自分が終了タグ省略可能で、来た開始タグがブロックの場合
				setFix(elist, stag, stag + 1, pos - 1, -1);
				return stag;
			}
		}
		if (this.canOmmitE() && !he.isStart()) {
			// 自分が終了タグ省略可能で、何らかの終了タグが来た場合
			int etag = -1;
			if (getName().equalsIgnoreCase(he.getName())) {
				etag = pos;
			}
			setFix(elist, stag, stag + 1, pos - 1, etag);
			return stag;
		}

		return -1;
	}

	@Override
	protected int parseEndTagCheck(List<HtElement> elist, int etag, HtElement he) {
		for (int i = etag - 1; i >= 0; i--) {
			HtElement e = elist.get(i);
			if (e.isEnd()) {
				HtParser p = manager.getParser(e.getName());
				if (p.isBlock()) {
					// ブロック要素の終了タグの場合
					setFix(elist, -1, i + 1, etag - 1, etag);
					return i + 1;
				}
			}
		}
		return super.parseEndTagCheck(elist, etag, he);
	}
}
