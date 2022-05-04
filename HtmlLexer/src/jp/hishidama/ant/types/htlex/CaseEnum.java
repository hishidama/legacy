package jp.hishidama.ant.types.htlex;

import org.apache.tools.ant.types.EnumeratedAttribute;

/**
 * �����P�[�X�ϊ��N���X.
 *<p>
 * �啶���E�������̕ύX���s���N���X�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2009.01.21
 * @version 2009.02.01
 */
public class CaseEnum extends EnumeratedAttribute {

	/** {@value}�F�ύX���Ȃ� */
	public static final String SAME = "same";
	/** {@value}�F�������ɕϊ����� */
	public static final String LOWER = "lower";
	/** {@value}�F�啶���ɕϊ����� */
	public static final String UPPER = "upper";
	/** {@value}�F�P��̐擪�̂ݑ啶���A���͏������ɕϊ����� */
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
	 * �ϊ����s.
	 *
	 * @param s
	 *            ������
	 * @return �ϊ��㕶����i���͂�null�������ꍇ��null�j
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
