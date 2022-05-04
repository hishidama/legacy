package jp.hishidama.html.parser.rule;

import java.util.*;

/**
 * HtHtmlParser�Ǘ��N���X.
 *<p>
 * HtHtmlParser�̗v�f���̃C���X�^���X���Ǘ�����B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.02.08
 */
public class HtParserManager {

	/**
	 * �R���X�g���N�^�[.
	 */
	public HtParserManager() {
		init();
	}

	protected final Map<String, HtParser> map = new HashMap<String, HtParser>();

	protected void init() {
		initParsers();
		initNoBody();
		initOmmitS();
		initOmmitE();
		initBlock();
	}

	/**
	 * �ʃp�[�T�[�C���X�^���X����.
	 */
	protected void initParsers() {
		// �Q�l�F http://www.tohoho-web.com/html/memo/elmtree.htm
		String[] inline = { "p", "h1", "h2", "h3", "h4", "h5", "h6", "address",
				"legend", "a", "font", "tt", "i", "b", "big", "small", "em",
				"strong", "dfn", "code", "samp", "kbd", "var", "cite", "abbr",
				"acronym", "sub", "sup", "span", "bdo", "q", "label" };
		for (String name : inline) {
			setParser(new ParseInline(name));
		}

		int prio = 255;
		setParser(new ParseHtml(prio));
		prio--;
		setParser(new ParsePriority("frameset", prio));
		prio--;
		setParser(new ParsePriority("noframes", prio));

		prio--;
		setParser(new ParseHead(prio));
		setParser(new ParseBody(prio));

		prio--;
		int table_prio = prio;
		int dl_prio = prio;
		setParser(new ParsePriority("table", prio));
		setParser(new ParsePriority("ul", prio));
		setParser(new ParsePriority("ol", prio));
		setParser(new ParsePriority("dl", prio));

		prio--;
		setParser(new ParseLi(prio));

		dl_prio--;
		setParser(new ParseDd("dd", dl_prio));
		setParser(new ParseDd("dt", dl_prio));

		table_prio--;
		setParser(new ParseTbody("thead", table_prio));
		setParser(new ParseTbody("tbody", table_prio));
		setParser(new ParseTbody("tfoot", table_prio));
		table_prio--;
		setParser(new ParseTr(table_prio));
		table_prio--;
		setParser(new ParseTd("td", table_prio));
		setParser(new ParseTd("th", table_prio));

		setParser(new ParseOption());
		setParser(new ParseRb("rb"));
		setParser(new ParseRb("rp"));
		setParser(new ParseRb("rt"));
	}

	/**
	 * �{�f�B�[���̖����v�f�̐ݒ�.
	 */
	protected void initNoBody() {
		// �Q�l�F http://www.tohoho-web.com/html/index.htm
		String[] names = { "area", "base", "basefont", "br", "col", "frame",
				"hr", "img", "input", "isindex", "link", "meta", "param", "wbr" };
		for (String name : names) {
			HtParser p = getParser(name);
			p.setNoBody(true);
		}
	}

	/**
	 * �J�n�^�O�ȗ��\�̐ݒ�.
	 */
	protected void initOmmitS() {
		// �Q�l�F http://www.tohoho-web.com/html/index.htm
		String[] names = { "body", "head", "html", "rb", "tbody" };
		for (String name : names) {
			HtParser p = getParser(name);
			p.setOmmitS(true);
		}
	}

	/**
	 * �I���^�O�ȗ��\�̐ݒ�.
	 */
	protected void initOmmitE() {
		// �Q�l�F http://www.tohoho-web.com/html/index.htm
		String[] names = { "body", "dd", "dt", "head", "html", "li", "option",
				"p", "rb", "rp", "rt", "tbody", "td", "tfoot", "th", "thead",
				"tr" };
		for (String name : names) {
			HtParser p = getParser(name);
			p.setOmmitE(true);
		}
	}

	/**
	 * �u���b�N�v�f�̐ݒ�.
	 */
	protected void initBlock() {
		// �Q�l�F http://www.tohoho-web.com/html/memo/elem.htm#blockinline
		String[] names = { "p", "h1", "h2", "h3", "h4", "h5", "h6", "ul", "ol",
				"dir", "menu", "pre", "dl", "div", "center", "noscript",
				"noframes", "blockquote", "form", "isindex", "hr", "table",
				"fieldset", "address", "multicol" };
		for (String name : names) {
			HtParser p = getParser(name);
			p.setBlock(true);
		}
	}

	protected void setParser(HtParser p) {
		p.setManager(this);
		map.put(p.getName(), p);
	}

	protected final HtParser getCachedParser(String name) {
		if (name == null || name.isEmpty()) {
			return null;
		}
		return map.get(name.toLowerCase());
	}

	/**
	 * HtHtmlParser�擾.
	 *
	 * @param name
	 *            �v�f��
	 * @return HtHtmlParser�i�K��null�ȊO�j
	 */
	public final HtParser getParser(String name) {
		HtParser p = getCachedParser(name);
		if (p == null) {
			if (name == null || name.isEmpty()) {
				return getDefaultParser();
			}
			p = newParser(name);
			setParser(p);
		}
		return p;
	}

	protected HtParser defaultParser;

	/**
	 * �f�t�H���gHtHtmlParser�擾.
	 *
	 * @return HtHtmlParser
	 */
	public final HtParser getDefaultParser() {
		if (defaultParser == null) {
			HtParser p = newParser(null);
			p.setManager(this);
			defaultParser = p;
		}
		return defaultParser;
	}

	/**
	 * HtHtmlParser�C���X�^���X����.
	 *
	 * @param name
	 *            �v�f��
	 * @return �C���X�^���X
	 */
	protected HtParser newParser(String name) {
		return new HtParser(name);
	}
}
