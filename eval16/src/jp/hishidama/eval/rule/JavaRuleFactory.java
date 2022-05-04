package jp.hishidama.eval.rule;

import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.exp.AbstractExpression;

/**
 * Javaルールファクトリークラス.
 * <p>
 * Javaに近いルールのインスタンスを生成する。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html">ひしだま</a>
 * @since 2007.02.21
 */
public class JavaRuleFactory extends ExpRuleFactory {

	private static JavaRuleFactory me;

	/**
	 * ルールファクトリー取得.
	 * <p>
	 * Javaルールのファクトリーインスタンスを返す。
	 * </p>
	 * 
	 * @return ルールファクトリー
	 */
	public static ExpRuleFactory getInstance() {
		if (me == null) {
			me = new JavaRuleFactory();
		}
		return me;
	}

	@Override
	protected AbstractRule createCommaRule(ShareRuleValue share) {
		return null;
	}

	@Override
	protected AbstractRule createPowerRule(ShareRuleValue share) {
		return null;
	}

	@Override
	protected AbstractExpression createLetPowerExpression() {
		return null;
	}
}
