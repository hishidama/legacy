package jp.hishidama.ant.html.parser;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import jp.hishidama.html.lexer.rule.HtLexer;
import jp.hishidama.html.lexer.token.ListToken;
import jp.hishidama.html.lexer.token.Tag;
import jp.hishidama.html.parser.elem.HtElement;
import jp.hishidama.html.parser.elem.HtElementUtil;
import jp.hishidama.html.parser.elem.HtListElement;
import jp.hishidama.html.parser.elem.HtTagElement;
import jp.hishidama.html.parser.rule.HtParserManager;

import org.junit.BeforeClass;
import org.junit.Test;

public class HtParserFileTest {

	@Test
	public void test() throws IOException {
		file(new File("C:/temp/lexer/from"));
		// file(new File("C:/share/hmdata/html/asahi"));
	}

	protected static Set<String> exclude = new HashSet<String>();

	@BeforeClass
	public static void init() {
		// exclude.add("clear.html");
		// exclude.add("gui.html");
		// exclude.add("compsync.html");
		// exclude.add("bat.html");
	}

	protected void file(File file) throws IOException {
		if (file.isDirectory()) {
			File[] list = file.listFiles();
			for (File f : list) {
				file(f);
			}
		} else {
			String name = file.getName();
			if (exclude.contains(name)) {
				return;
			}
			if (name.endsWith(".html")) {
				try {
					// System.out.println(file);

					FileInputStream fis = new FileInputStream(file);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(fis, "MS932"));
					HtLexer lexer = null;
					ListToken tlist;
					try {
						lexer = new HtLexer(br);
						tlist = lexer.parse();
					} finally {
						if (lexer != null) {
							lexer.close();
						}
					}
					tlist.calcLine(1);

					HtListElement hle = new HtParserManager()
							.getDefaultParser().parse(tlist);
					assertEquals(tlist.getText(), hle.getText());

					HtTagElement he = getFirstTag(hle);
					// HtTagElement he = getLastTag(hle);
					if (he == null) {
						System.out.println(he + "ÅF " + file);
						// } else if (he.getStartTag() == null) {
						// System.out.println(he.getName() + "ÅF " + file);
					} else if (!he.isFix()) {
						System.out.println(false + "ÅF " + file);
						HtElementUtil.dumpNotFix(hle);
					}
				} catch (RuntimeException e) {
					System.err.println(file);
					throw e;
				}
			}
		}
	}

	protected HtTagElement getFirstTag(HtListElement hle) {
		loop: for (;;) {
			if (hle instanceof HtTagElement) {
				return (HtTagElement) hle;
			}

			for (int i = 0; i < hle.size(); i++) {
				HtElement e = hle.get(i);
				if (e instanceof HtTagElement) {
					return (HtTagElement) e;
				}
				if (e instanceof HtListElement) {
					hle = (HtListElement) e;
					continue loop;
				}
			}

			break;
		}
		return null;
	}

	protected HtTagElement getLastTag(HtListElement hle) {
		loop: for (;;) {
			if (hle instanceof HtTagElement) {
				Tag etag = hle.getEndTag();
				if (etag != null) {
					return (HtTagElement) hle;
				}
			}
			for (int i = hle.size() - 1; i >= 0; i--) {
				HtElement e = hle.get(i);
				if (e instanceof HtListElement) {
					hle = (HtListElement) e;
					continue loop;
				}
			}
			break;
		}
		return null;
	}
}
