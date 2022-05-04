package jp.hishidama.eval.sample;

import jp.hishidama.eval.Expression;
import jp.hishidama.eval.ExpRuleFactory;

/**
 * •¶Žš—ñ•\Ž¦‚Ì—á
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html">‚Ð‚µ‚¾‚Ü</a>
 */
public class Print {

	public static void main(String[] args) {
		if (args.length <= 0) {
			System.out.println("set args");
			return;
		}
		String str = args[0];
		System.out.println("“ü—ÍF" + str);

		Expression exp = ExpRuleFactory.getDefaultRule().parse(str);
		System.out.println("•ÛŽF" + exp.toString());
	}
}
