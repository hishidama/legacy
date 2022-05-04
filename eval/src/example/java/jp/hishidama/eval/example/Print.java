package jp.hishidama.eval.example;

import jp.hishidama.eval.Expression;
import jp.hishidama.eval.ExpRuleFactory;

/**
 * 文字列表示の例
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html">ひしだま</a>
 */
public class Print {

	public static void main(String[] args) {
		if (args.length <= 0) {
			System.out.println("set args");
			return;
		}
		String str = args[0];
		System.out.println("入力：" + str);

		Expression exp = ExpRuleFactory.getDefaultRule().parse(str);
		System.out.println("保持：" + exp.toString());
	}
}
