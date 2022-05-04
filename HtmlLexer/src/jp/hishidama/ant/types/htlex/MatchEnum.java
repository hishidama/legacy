package jp.hishidama.ant.types.htlex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.types.EnumeratedAttribute;

/**
 * ������}�b�`���O�N���X.
 *<p>
 * ������̔�r���s���N���X�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2009.01.23
 * @version 2009.02.01
 */
public class MatchEnum extends EnumeratedAttribute {

	/**
	 * {@value}�F�啶���������𖳎����Ĕ�r
	 */
	public static final String IGNORE_CASE = "ignorecase";
	/**
	 * {@value}�F�P����r
	 */
	public static final String EQUALS = "equals";
	/**
	 * {@value}�F���K�\���Ō���
	 *
	 * @see Matcher#find()
	 */
	public static final String FIND = "find";
	/**
	 * {@value}�F���K�\���ň�v
	 *
	 * @see Matcher#matches()
	 */
	public static final String MATCHES = "matches";

	@Override
	public String[] getValues() {
		return new String[] { IGNORE_CASE, EQUALS, FIND, MATCHES };
	}

	public MatchEnum() {
	}

	public MatchEnum(String s) {
		setValue(s);
	}

	private String comp = "";
	private Pattern pattern;

	/**
	 * ��r������ݒ�.
	 *
	 * @param comp
	 *            ��r��������
	 */
	public void setPattern(String comp) {
		if (comp == null) {
			comp = "";
		}
		this.comp = comp;
		switch (getIndex()) {
		case 2:
		case 3:
			pattern = Pattern.compile(comp);
			break;
		}
	}

	/**
	 * ��r������擾.
	 *
	 * @return ��r��������
	 */
	public String getPatternString() {
		return comp;
	}

	/**
	 * ��r�p�^�[���擾.
	 *
	 * @return ��r�p�^�[���i���K�\���̏ꍇ�̂ݗL���j
	 */
	public Pattern getPattern() {
		return pattern;
	}

	/**
	 * �}�b�`���O���f.
	 *
	 * @param s
	 *            ��r�Ώە�����inull�̏ꍇ�͋󕶎���Ƃ��Ĕ��f����j
	 * @return �w�肳��Ă��������Ƀ}�b�`�����ꍇ�Atrue
	 */
	public boolean matches(String s) {
		if (s == null) {
			s = "";
		}
		switch (getIndex()) {
		default: // ignore case
			return comp.equalsIgnoreCase(s);
		case 1: // equals
			return comp.equals(s);
		case 2: // find
			return pattern.matcher(s).find();
		case 3: // matches
			return pattern.matcher(s).matches();
		}
	}
}
