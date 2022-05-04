package jp.hishidama.eval.sample;

import jp.hishidama.eval.Expression;
import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.Rule;

/**
 * 四則演算の例
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html">ひしだま</a>
 */
public class Calc {

	public static void main(String[] args) {
		if (args.length <= 0) {
			System.out.println("set args");
			return;
		}
		String str = args[0];
		System.out.println("式　：" + str);

		Rule rule = ExpRuleFactory.getDefaultRule();
		Expression exp = rule.parse(str);
		long result = exp.evalLong();
		System.out.println("結果：" + result);
	}
}
