package jp.hishidama.ant.types.htlex.eval;

import org.apache.tools.ant.Project;

import jp.hishidama.eval.log.EvalLogAdapter;

/**
 * HtHtmlLexerタグ属性演算のログ出力クラス.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2010.01.31
 */
public class HtLexerEvalLog extends EvalLogAdapter {

	protected Project project;

	public HtLexerEvalLog(Project project) {
		this.project = project;
	}

	protected int msgLevel = -1;

	public void setMsgLevel(int level) {
		msgLevel = level;
	}

	@Override
	public void logEval(String name, Object x, Object y, Object r) {
		if (msgLevel >= 0) {
			String msg = name + ": [" + x + "],[" + y + "]-> " + r;
			project.log(msg, msgLevel);
		}
	}
}
