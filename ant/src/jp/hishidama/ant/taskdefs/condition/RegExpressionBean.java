package jp.hishidama.ant.taskdefs.condition;

/**
 * Expressionタスク.
 * <p>
 * 正規表現または書式を保持する。<br>
 * filesmatch.regタスクの子要素として使用する。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/compsync.html">ひしだま</a>
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
