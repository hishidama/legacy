package jp.hishidama.eval.exp;

import java.util.*;

import static org.junit.Assert.*;

import org.junit.Test;

import jp.hishidama.eval.*;
import jp.hishidama.eval.func.*;
import jp.hishidama.eval.var.*;

/**
 * 演算子を入れ替えたルールのテスト.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class PowerTest {

	/*
	 * 演算子を入れ替えるテスト・メソッド
	 */
	@Test
	public void testPower() {
		Map<String, Long> map = new HashMap<String, Long>();

		BasicPowerRuleFactory factory = new BasicPowerRuleFactory();
		Rule rule;

		rule = factory.getRule();
		testEval(rule, "-2^3^2", null, null, -64);
		map.put("a", Long.valueOf(5));
		testEval(rule, "a^=2", map, null, 25);

		rule = ExpRuleFactory.getDefaultRule();
		testEval(rule, "-2**3**2", null, null, -64);
		testEval(rule, "-2^3^2", null, null, -2 ^ 3 ^ 2); // -1：普通は排他的論理和

		rule = factory.getRule(); // 再作成：デフォルトルールと影響しあっていないことの確認
		testEval(rule, "-2^3^2", null, null, -64);
		testParse(rule, "-2**3**2");
	}

	/**
	 * 正常系のテスト.
	 *
	 * @param str
	 *            文字列
	 * @param map
	 *            変数マップ
	 * @param func
	 *            関数定義
	 * @param expected
	 *            正しい結果
	 * @version 2007.02.09
	 */
	private void testEval(Rule rule, String str, Map<String, Long> map,
			Function func, long expected) {
		System.out.println("●" + str + "●");
		Expression exp = rule.parse(str);
		if (map != null) {
			MapVariable<String, Long> var = new MapVariable<String, Long>();
			var.setMap(map);
			exp.setVariable(var);
		}
		// exp.dump(0);
		System.out.println(exp.toString());
		if (func != null) {
			exp.setFunction(func);
		}
		long ret = exp.evalLong();
		System.out.println("= " + ret);
		assertEquals(expected, ret);
	}

	/**
	 * 異常系のテスト.
	 * <p>
	 * 字句解析・構文解析でEvalExceptionが起きるはず。<br>
	 * （メッセージが正しいかどうかについてのチェックは省略）
	 * </p>
	 *
	 * @param str
	 *            文字列
	 */
	private void testParse(Rule rule, String str) {
		System.out.println("●" + str + "●");
		Expression exp;
		try {
			exp = rule.parse(str);
		} catch (EvalException e) {
			System.out.println(e.toString());
			return;
		}
		// exp.dump(0);
		System.out.println(exp.toString());
		fail();
	}

	/**
	 * 独自演算子に差し替えるファクトリーの例.
	 * <p>
	 * 累乗の演算子をBASICの「^」にしてみる例。
	 * </p>
	 */
	class BasicPowerRuleFactory extends ExpRuleFactory {

		public BasicPowerRuleFactory() {
			super();
		}

		protected AbstractExpression createXorExpression() {
			// 「^」を排他的論理和では使わないようにする
			return null;
		}

		@Override
		protected AbstractExpression createLetXorExpression() {
			// 「^=」を排他的論理和では使わないようにする
			return null;
		}

		@Override
		protected AbstractExpression createPowerExpression() {
			// 「^」を指数演算子とする
			AbstractExpression e = new PowerExpression();
			e.setOperator("^");
			return e;
		}

		@Override
		protected AbstractExpression createLetPowerExpression() {
			// 「^=」を指数演算の代入演算子とする
			AbstractExpression e = new LetPowerExpression();
			e.setOperator("^=");
			return e;
		}
	}
}
