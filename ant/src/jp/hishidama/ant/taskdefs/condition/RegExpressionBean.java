package jp.hishidama.ant.taskdefs.condition;

/**
 * Expression�^�X�N.
 * <p>
 * ���K�\���܂��͏�����ێ�����B<br>
 * filesmatch.reg�^�X�N�̎q�v�f�Ƃ��Ďg�p����B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/compsync.html">�Ђ�����</a>
 * @see jp.hishidama.ant.taskdefs.condition.RegexpFilesMatch
 */
public final class RegExpressionBean {

	private String regexp;

	private String format;

	public final void setRegexp(String value) {
		this.regexp = value;
	}

	public final String getRegexp() {
		return regexp;
	}

	public final void setFormat(String value) {
		this.format = value;
	}

	public final String getFormat() {
		return format;
	}
}
