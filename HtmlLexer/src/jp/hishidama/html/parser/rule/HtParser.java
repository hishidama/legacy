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
 * HtHtmlParser�{��.
 *<p>
 * {@link ListToken}��ǂݍ��݁A�^�O�̃c���[���\�z����B<br>
 * ��<a target="hishidama" href=
 * "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html">�g�p��</a>
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.02.08
 */
public class HtParser {

	protected boolean DEBUG = false;

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param name
	 *            �v�f��
	 */
	protected HtParser(String name) {
		setName(name);
	}

	private String name;

	/**
	 * �v�f���ݒ�.
	 *
	 * @param name
	 *            �v�f��
	 */
	public final void setName(String name) {
		if (name != null) {
			this.name = name.toLowerCase();
		}
	}

	/**
	 * �v�f���擾.
	 *
	 * @return �v�f��
	 */
	public final String getName() {
		return name;
	}

	protected boolean noBody = false;

	/**
	 * �{�f�B�[���������Ȃ��v�f���ǂ�����ݒ肷��B
	 *
	 * @param b
	 *            true�̏ꍇ�A�{�f�B�[���������Ȃ�
	 */
	public final void setNoBody(boolean b) {
		noBody = b;
	}

	/**
	 * �{�f�B�[���������Ȃ��v�f���ǂ����B
	 *
	 * @return true�̏ꍇ�A�{�f�B�[���������Ȃ�
	 */
	public final boolean hasNoBody() {
		return noBody;
	}

	protected boolean ommitS = false;

	/**
	 * �J�n�^�O���ȗ��\���ǂ�����ݒ肷��B
	 *
	 * @param b
	 *            true�̏ꍇ�A�ȗ��\
	 */
	public final void setOmmitS(boolean b) {
		ommitS = b;
	}

	/**
	 * �J�n�^�O���ȗ��\���ǂ����B
	 *
	 * @return true�̏ꍇ�A�ȗ��\
	 */
	public final boolean canOmmitS() {
		return ommitS;
	}

	protected boolean ommitE = false;

	/**
	 * �I���^�O���ȗ��\���ǂ�����ݒ肷��B
	 *
	 * @param b
	 *            true�̏ꍇ�A�ȗ��\
	 */
	public final void setOmmitE(boolean b) {
		ommitE = b;
	}

	/**
	 * �I���^�O���ȗ��\���ǂ����B
	 *
	 * @return true�̏ꍇ�A�ȗ��\
	 */
	public final boolean canOmmitE() {
		return ommitE;
	}

	protected boolean block = false;

	/**
	 * �u���b�N�v�f���ǂ�����ݒ肷��B
	 *
	 * @param b
	 *            true�̏ꍇ�A�u���b�N�v�f
	 */
	public final void setBlock(boolean b) {
		block = b;
	}

	/**
	 * �u���b�N�v�f���ǂ����B
	 *
	 * @return true�̏ꍇ�A�u���b�N�v�f
	 */
	public final boolean isBlock() {
		return block;
	}

	protected int prio = 0;

	/**
	 * �D��x�擾.
	 *
	 * @return �D�揈���ɂ�����D��x
	 */
	protected final int getPriority() {
		return prio;
	}

	protected HtParserManager manager;

	protected final void setManager(HtParserManager manager) {
		this.manager = manager;
	}

	/**
	 * ��͎��s.
	 *
	 * @param tlist
	 *            ���X�g�g�[�N��
	 * @return �v�f���X�g
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
	 * �g�[�N�����X�g����v�f���X�g�����o���B
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
					// <�`/>�̏ꍇ
					HtTagElement te = new HtTagElement();
					te.setStartTag(tag);
					te.setFix(true);
					elist.add(te);
					continue;
				}
				String name = tag.getName();
				if (name == null || name.isEmpty()) {
					// ���O�������ꍇ
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
				// �^�O�ȊO�i�e�L�X�g���j�́A�f�t�H���g�Ŋm����
				HtElement e = new HtTokenElement(t);
				e.setFix(true);
				elist.add(e);
			}
		}
		return elist;
	}

	/**
	 * �D��x�̍����^�O����͂���B�i�f�t�H���g�p�[�T�[�g�p�j
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
	 * �D��x�̍����^�O����͂���B�i�v�f�̎�ޖ��j
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
				// �v�f�̊J�n�ƏI���̗���������ꍇ
				// ���Ȃ킿<tag/>
				// �܂���<tag>�`</tag>
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
	 * �D�揈���ɂ����āA�I�������̔�����s���B
	 *
	 * @param elist
	 * @param stag
	 * @param pos
	 *            ����Ώۗv�f�̈ʒu
	 * @param e
	 *            ����Ώۗv�f
	 * @return �I�������ꍇ�̈ʒu
	 */
	protected int parsePriorityEnd(List<HtElement> elist, int stag, int pos,
			HtElement e) {
		return -1;
	}

	/**
	 * �D�揈���ɂ����āA�I���^�O�����������ꍇ�̏������s���B
	 *
	 * @param elist
	 * @param stag
	 * @return
	 */
	protected int parsePriorityNotFound(List<HtElement> elist, int stag) {
		return -1;
	}

	/**
	 * �^�O�v�f���.
	 *<p>
	 * �^�O�v�f�����ɕێ����Ă���e�v�f�ɑ΂��A��͏������Ăяo���B<br>
	 * ���̌��ʁA�����S�Ă��m��ς݂ɂȂ�����^�O�v�f���g���m��ς݂ɂ���B
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
	 * �v�f�ꗗ�̊e�v�f�ɑ΂��A��͏������Ăяo���B
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
	 * �{�f�B�[���������v�f�ŏI���^�O��������Ȃ����̂��m�肳����B
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
				// �J�n�^�O<tag>
				String name = e.getName();
				HtParser p = manager.getParser(name);
				if (p.hasNoBody()) {
					// �{�f�B�[���������v�f
					int n = searchEndTag(elist, i + 1, name);
					if (n < 0) {
						// �����̏I���^�O��������Ȃ������ꍇ
						setFix(elist, i, -1, -1, -1);
					}
				}
			}
		}
	}

	/**
	 * �}�b�`����I���^�O��T���B
	 *
	 * @param elist
	 * @param pos
	 * @param name
	 * @return ���������ꍇ�A���̈ʒu
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
	 * �J�n�^�O�ƕ��^�O�̃y�A������΁A������m�肳����B
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
					// ���m��̊J�n�^�O<tag>�̏ꍇ
					String name = e.getName();
					HtParser p = manager.getParser(name);
					p.parsePairSub(elist, i);
				}
			}
		}
	}

	/**
	 * �ԂɊm�肵�����̂���������Ԃ̕��^�O�܂ł��m�肳����B
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
				// �v�f�̊J�n�ƏI���̗���������ꍇ
				// ���Ȃ킿<tag/>
				// �܂���<tag>�`</tag>
				continue;
			}
			if (e.isEnd() && name.equalsIgnoreCase(e.getName())) {
				// �I���^�O</tag>�̂݁A�����O����v
				setFix(elist, stag, stag + 1, i - 1, i);
				return;
			}

			if (!e.isFix()) {
				return; // ���m�肪�������_�ŏI��
			}
		}

		// �擪�ȍ~���S�Ċm�肾�������I���^�O�����������ꍇ
		parsePairNotFound(elist, stag);
	}

	/**
	 * �擪�^�O�ȍ~���S�Ċm�肾�������I���^�O�����������ꍇ
	 *
	 * @param elist
	 * @param stag
	 */
	protected void parsePairNotFound(List<HtElement> elist, int stag) {
		if (this.hasNoBody()) {
			// �{�f�B�[���������Ȃ��v�f
			setFix(elist, stag, -1, -1, -1);
		} else if (this.canOmmitE()) {
			// �I���^�O���ȗ��\�ȗv�f
			// �擪�ȍ~�S�Ă��ЂƂ̗v�f�Ƃ��Ċm��
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
	 * �v�f���L���[���ɂ����.
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
			int n = parseProperCheckEnd(elist, stag, i, e); // �m�肵�Ă��邩�ǂ������֌W�Ƀ`�F�b�N
			if (n >= 0) {
				i = n;
				return;
			}
			if (e.isStart() && e.isEnd()) {
				continue;
			}
			if (e.isStart()) {
				// �J�n�^�O
				if (!e.isFix()) {
					HtParser p = manager.getParser(e.getName());
					p.parseProper(elist, i);
				}
			} else {
				// �I���^�O
				if (name != null && name.equalsIgnoreCase(e.getName())) {
					setFix(elist, stag, stag + 1, i - 1, i);
					return;
				}
			}
		}

		// ��ʂ菈�����I�������
		parseProperNotFound(elist, stag);
	}

	/**
	 * �v�f���L���[���ɂ��I����������.
	 *
	 * @param elist
	 * @param stag
	 * @param pos
	 *            ����Ώۃ^�O�̈ʒu
	 * @param he
	 *            ����Ώۃ^�O
	 * @return �m�肵���ꍇ�A���̈ʒu
	 */
	protected int parseProperCheckEnd(List<HtElement> elist, int stag, int pos,
			HtElement he) {
		return -1;
	}

	/**
	 * �v�f���L���[���ɂ���ďI�������Ƀ��X�g���̑S�^�O���X�L�b�v���ꂽ�ꍇ�̏���
	 *
	 * @param elist
	 * @param stag
	 */
	protected void parseProperNotFound(List<HtElement> elist, int stag) {
		for (int i = elist.size() - 1; i > stag; i--) {
			HtElement e = elist.get(i);
			if (!e.isFix() && e.isStart() && !e.isEnd()) {
				// �J�n�^�O�݂̂̏ꍇ

				HtParser p = manager.getParser(e.getName());
				if (p.hasNoBody()) {
					// �{�f�B�[���������Ȃ��v�f
					setFix(elist, i, -1, -1, -1);
				} else if (false && p.canOmmitE()) { // TODO
					// �I���^�O���ȗ��\�ȗv�f
					// �����܂őS�����{�f�B�[���ɓ����
					setFix(elist, i, i + 1, elist.size() - 1, -1);
				} else {
					// �����܂őS�����{�f�B�[���ɓ����
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
	 * ���m��̏I���^�O�̏���
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
				// ���m��̏I���^�O</tag>
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
	 * �͂���I���^�O����
	 *
	 * @param elist
	 * @param etag
	 *            �����Ώۂ̏I���^�O�̈ʒu
	 * @param he
	 *            �����Ώۂ̏I���^�O
	 * @return �m�肵���ꍇ�A���̐擪�ʒu
	 */
	protected int parseEndTagCheck(List<HtElement> elist, int etag, HtElement he) {
		// �f�t�H���g�ł́A�͂���I���^�O��
		if (this.hasNoBody()) {
			// �{�f�B�[���������Ȃ��v�f
			setFix(elist, -1, -1, -1, etag);
			return etag;
		} else if (false && this.canOmmitS()) { // TODO
			// �J�n�^�O���ȗ��\�ȗv�f
			// ���X�g�̐擪���炻�̏I���^�O�܂őS�Ă��u���b�N������
			setFix(elist, -1, 0, etag - 1, etag);
			return 0;
		} else {
			// ���X�g�̐擪���炻�̏I���^�O�܂őS�Ă��u���b�N������
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
