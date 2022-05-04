package jp.hishidama.win32.mshtml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Native2 {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String dir = "src/jp/hishidama/win32/mshtml";
		File f = new File(dir, "IHTMLPhraseElement.java");
		BufferedReader br = new BufferedReader(new FileReader(f));

		boolean ntv = false;
		while (br.ready()) {
			String line = br.readLine();
			if (line.indexOf("private static class Native") > 0) {
				ntv = true;
				continue;
			}
			if (!ntv)
				continue;
			if (line.trim().startsWith("//"))
				continue;
			int i = line.indexOf("private static native");
			if (i > 0) {
				if (line.indexOf(';') < 0) {
					while (br.ready()) {
						String l = br.readLine();
						if (l.trim().startsWith("//"))
							continue;
						line += l;
						if (l.indexOf(';') >= 0)
							break;
					}
				}
				String[] ss = line.split(" ");
				String rtype = ss[3];
				String fname = ss[4].substring(0, ss[4].indexOf('('));

				System.out.print("public ");
				System.out.print(rtype);
				System.out.print(" ");
				System.out.print(public_fname(fname));
				System.out.print("(");

				ss = line.substring(line.indexOf('(') + 1,
						line.lastIndexOf(')')).split(",");

				if (ss.length == 2) {
					String def = ss[1].trim();
					String[] vs = def.split(" ");
					String t = vs[0].trim();
					String v = vs[1].trim();
					if (t.equals("boolean") && v.length() == 1) {
						v = "b";
					} else if (v.length() == 1) {
						v = fname.substring(fname.indexOf('_') + 1)
								.toLowerCase();
					}
					ss[1] = t + " " + v;
				}

				for (int j = 1; j < ss.length; j++) {
					if (j != 1)
						System.out.print(", ");
					System.out.print(ss[j].trim());
				}
				if (rtype.startsWith("I")) {
					if (ss.length > 1)
						System.out.print(", ");
					System.out.print("boolean addChild");
				}
				System.out.println(") {");

				if (rtype.startsWith("I")) {
					System.out.print(rtype);
					System.out.print(" c = ");
				} else if (!rtype.equals("void")) {
					System.out.print("return ");
				}

				System.out.print("Native.");
				System.out.print(fname);
				System.out.print("(getPtr()");
				for (int j = 1; j < ss.length; j++) {
					System.out.print(", ");
					String def = ss[j].trim();
					String[] v = def.split(" ");
					System.out.print(v[1]);
				}
				System.out.println(");");

				if (rtype.startsWith("I")) {
					System.out.println("if (addChild) {");
					System.out.println("\taddChildComPtr(c);");
					System.out.println("}");
					System.out.println("return c;");
				}

				System.out.println("}");
			}
		}
	}

	public static String public_fname(String fname) {
		for (;;) {
			int i = fname.indexOf('_');
			if (i < 0)
				break;
			String c = fname.substring(i + 1, i + 2);
			fname = fname.substring(0, i) + c.toUpperCase()
					+ fname.substring(i + 2);
		}

		if (fname.startsWith("put")) {
			return "set" + fname.substring(3);
		}
		return fname.substring(0, 1).toLowerCase() + fname.substring(1);
	}
}
