package jp.hishidama.win32.mshtml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Native1 {
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
			if (line.indexOf("private static class Native") >= 0) {
				ntv = true;
				continue;
			}
			if (!ntv)
				continue;
			if (line.trim().startsWith("//")) {
				System.out.println(line);
				continue;
			}
			int i = line.indexOf("virtual");
			if (i > 0) {
				if (line.indexOf(';') < 0) {
					while (br.ready()) {
						String l = br.readLine();
						line += l;
						if (l.indexOf(';') >= 0)
							break;
					}
				}
				String rtype = "void";

				int ks = line.indexOf('(');
				String prev = line.substring(0, ks).trim();
				int ps = prev.lastIndexOf(' ');
				String fname;
				if (ps >= 0) {
					fname = prev.substring(ps).trim();
				} else {
					fname = prev.trim();
				}
				int ke = line.lastIndexOf(')');
				line = line.substring(ks + 1, ke).trim();
				line = comment(line);

				String[] ss = line.split(",");
				List list = new ArrayList(ss.length);
				for (int j = 0; j < ss.length; j++) {
					if (ss[j].trim().length() > 0) {
						list.add(ss[j]);
					}
				}
				for (int j = 0; j < list.size(); j++) {
					String s = (String) list.get(j);
					if (s.indexOf("out") > 0 && s.indexOf("retval") > 0) {
						s = s.substring(s.indexOf("*/") + 2).trim();
						rtype = s.substring(0, s.lastIndexOf('*')).trim();
						list.remove(j);
						j--;
					}
				}

				System.out.print("private static native ");
				System.out.print(ctype(rtype));
				System.out.print(" ");
				System.out.print(fname);
				System.out.print("(long ptr");
				for (int j = 0; j < list.size(); j++) {
					System.out.print(", ");
					String s = (String) list.get(j);
					s = s.substring(s.indexOf("*/") + 2).trim();
					int k = s.lastIndexOf(' ');
					String t = s.substring(0, k).trim();
					String v = s.substring(k + 1);
					System.out.print(ctype(t));
					System.out.print(" ");
					System.out.print(v);
				}
				System.out.println(");");
			}
		}
	}

	public static String comment(String line) {
		StringBuffer sb = new StringBuffer(line.length());
		boolean cmt = false;
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			char d = '\0';
			if (i + 1 < line.length()) {
				d = line.charAt(i + 1);
			}
			if (c == '/' && d == '*') {
				cmt = true;
				i++;
				sb.append("/*");
				continue;
			}
			if (cmt && c == '*' && d == '/') {
				cmt = false;
				i++;
				sb.append("*/");
				continue;
			}
			if (c == ',' && cmt) {
				c = '_';
			}
			sb.append(c);
		}
		return sb.toString();
	}

	public static String ctype(String type) {
		if (type.startsWith("struct") && type.endsWith("*")) {
			return type.substring(6, type.length() - 1).trim();
		}
		if (type.startsWith("I") && type.endsWith("*")) {
			return type.substring(0, type.length() - 1).trim();
		}
		if (type.equals("VARIANT_BOOL")) {
			return "boolean";
		}
		if (type.equals("VARIANT")) {
			return "Variant";
		}
		if (type.equals("BSTR")) {
			return "String";
		}
		if (type.equals("long")) {
			return "int";
		}
		return type;
	}

}
