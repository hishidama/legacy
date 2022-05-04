package jp.hishidama.ant.html.parser;

import java.io.*;

import jp.hishidama.html.lexer.rule.HtLexer;
import jp.hishidama.html.lexer.token.ListToken;
import jp.hishidama.html.parser.elem.HtElementUtil;
import jp.hishidama.html.parser.elem.HtListElement;
import jp.hishidama.html.parser.rule.HtParser;
import jp.hishidama.html.parser.rule.HtParserManager;

public class ParseFile {

	public static void main(String[] args) throws IOException {
		String[] fs = {
				"C:/temp/lexer/from/index.html",
				"C:/temp/lexer/from/game/dq8/clear.html",
				// "C:/temp/lexer/from/book/index.html",
				"C:/temp/lexer/from/book/replay1987.html",
				"C:/temp/lexer/from/javadoc/jp/hishidama/ant/taskdefs/CompareSync.html",
				"C:/temp/lexer/from/tech/postgres/jdbc_src.html",
				"C:/temp/lexer/from/tech/vcpp/menubar.html",
				"C:/share/hmdata/html/asahi/game/ff12/map.html", };
		if (false) {
			main(new File(fs[0]));
		} else {
			for (String f : fs) {
				main(new File(f));
			}
		}
	}

	public static void main(File f) throws IOException {
		System.out.println("Å•" + f);
		Reader r = new InputStreamReader(new FileInputStream(f), "MS932");
		HtLexer lexer = new HtLexer(r);
		ListToken tlist;
		try {
			tlist = lexer.parse();
		} finally {
			lexer.close();
		}
		tlist.calcLine(1);

		HtParser parser = new HtParserManager().getDefaultParser();
		HtListElement hle = parser.parse(tlist);
		// System.out.println(hle.getText());

		File wf = new File("C:/temp/lexer", f.getName() + ".txt");
		HtElementUtil.dumpTree(hle, -1, wf);
		HtElementUtil.dumpNotFix(hle);
		System.out.println("Å£" + wf);
	}
}
