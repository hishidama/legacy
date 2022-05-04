package jp.hishidama.ant.html.parser;

import java.io.*;

import jp.hishidama.html.lexer.rule.HtLexer;
import jp.hishidama.html.lexer.token.ListToken;
import jp.hishidama.html.parser.elem.*;
import jp.hishidama.html.parser.rule.HtParser;
import jp.hishidama.html.parser.rule.HtParserManager;

/**
 * p要素の中にブロック要素が入っていたら、それをダンプ出力する。
 */
public class PCheck {

	public static void main(String[] args) throws IOException {
		// File d = new File("C:/temp/lexer/from");
		File d = new File("C:/share/hmdata/html/asahi");
		new PCheck().main(d);
	}

	void main(File file) throws IOException {
		if (file.isDirectory()) {
			String d = file.getName();
			if ("javadoc".equals(d)) {
				return;
			}
			for (File f : file.listFiles()) {
				main(f);
			}
			return;
		}
		String name = file.getName();
		if (!name.endsWith(".html")) {
			return;
		}
		HtListElement elist = list(file);
		f = false;
		searchP(elist, file);
	}

	HtParserManager manager = new HtParserManager();

	HtListElement list(File file) throws IOException {

		FileInputStream fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis,
				"MS932"));
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

		HtListElement hle = manager.getDefaultParser().parse(tlist);
		return hle;
	}

	void searchP(HtElement he, File file) {
		if (he instanceof HtTagElement) {
			HtTagElement te = (HtTagElement) he;
			if ("p".equalsIgnoreCase(te.getName())) {
				for (HtElement e : te) {
					String name = e.getName();
					if (name != null) {
						HtParser p = manager.getParser(name);
						if (p.isBlock()) {
							dump(file, te);
						}
					}
				}
			}
		}
		if (he instanceof HtListElement) {
			HtListElement le = (HtListElement) he;
			for (HtElement e : le) {
				searchP(e, file);
			}
		}
	}

	boolean f = false;

	void dump(File file, HtElement he) {
		if (!f) {
			System.out.println("▼" + file);
			f = true;
		}
		HtElementUtil.dumpTree(he, 0);
	}

}
