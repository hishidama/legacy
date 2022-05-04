package jp.hishidama.ant.types.htlex;

import org.apache.tools.ant.types.EnumeratedAttribute;

/**
 * 文字ケース変換クラス.
 *<p>
 * 大文字・小文字の変更を行うクラス。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2009.01.21
 * @version 2009.02.01
 */
public class CaseEnum extends EnumeratedAttribute {

	/** {@value}：変更しない */
	public static final String SAME = "same";
	/** {@value}：小文字に変換する */
	public static final String LOWER = "lower";
	/** {@value}：大文字に変換する */
	public static final String UPPER = "upper";
	/** {@value}：単語の先頭のみ大文字、他は小文字に変換する */
	public static final String PROPER = "proper";

	@Override
	public String[] getValues() {
		return new String[] { SAME, LOWER, UPPER, PROPER };
	}

	public CaseEnum() {
	}

	public CaseEnum(String s) {
		setValue(s);
	}

	protected interface Converter {
		public String convert(String s);
	}

	protected static class Same implements Converter {
		@Override
		public String convert(String s) {
			return s;
		}
	}

	protected static class Lower implements Converter {
		@Override
		public String convert(String s) {
			return s.toLowerCase();
		}
	}

	protected static class Upper implements Converter {
		@Override
		public String convert(String s) {
			return s.toUpperCase();
		}
	}

	protected static class Proper implements Converter {
		@Override
		public String convert(String s) {
			boolean first = true;
			char[] ca = s.toCharArray();
			for (int i = 0; i < ca.length; i++) {
				char c = ca[i];
				char u = Character.toUpperCase(c);
				char l = Character.toLowerCase(c);
				if (u != l) {
					if (first) {
						ca[i] = u;
						first = false;
					} else {
						ca[i] = l;
					}
				} else {
					first = true;
				}
			}
			return new String(ca);
		}
	}

	protected Converter c = null;

	/**
	 * 変換実行.
	 *
	 * @param s
	 *            文字列
	 * @return 変換後文字列（入力がnullだった場合はnull）
	 */
	public String convert(String s) {
		if (s == null) {
			return null;
		}
		if (c == null) {
			switch (getIndex()) {
			case 0:
				c = new Same();
				break;
			case 1:
				c = new Lower();
				break;
			case 2:
				c = new Upper();
				break;
			case 3:
				c = new Proper();
				break;
			}
		}
		return c.convert(s);
	}
}
