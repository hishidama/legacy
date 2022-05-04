package jp.hishidama.eval.exp;

import static org.junit.Assert.*;

import org.junit.Test;

import jp.hishidama.eval.*;
import jp.hishidama.eval.srch.SearchAdapter;

/**
 * 後から演算子を入れ替えるテスト.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class ChangeOperatorTest {

	/*
	 * 演算子を入れ替えるテスト・メソッド
	 */
	@Test
	public void testPower() {

		Rule rule = ExpRuleFactory.getDefaultRule();
		Expression src = rule.parse("5*4**3**2*-1");
		assertEquals("5 * 4 ** 3 ** 2 * -1", src.toString());
		assertEquals(-5 * 64 * 64, src.evalLong());

		Expression bas = src.dup();
		assertEquals("5 * 4 ** 3 ** 2 * -1", bas.toString());
		bas.search(new SearchAdapter() {
			// 累乗演算子の「**」を「^」に変える
			@Override
			public void search(AbstractExpression exp) {
				if (exp instanceof PowerExpression) {
					exp.setOperator("^");
				}
			}
		});
		assertEquals("5 * 4 ^ 3 ^ 2 * -1", bas.toString());

		// 複製前のものは変わらない
		assertEquals("5 * 4 ** 3 ** 2 * -1", src.toString());
	}

	@Test
	public void testComma() {

		Rule rule = ExpRuleFactory.getDefaultRule();
		Expression src = rule.parse("a=1,b=test(1,2),c=a+b");
		assertEquals("a = 1, b = test(1, 2), c = a + b", src.toString());

		Expression bas = src.dup();
		assertEquals("a = 1, b = test(1, 2), c = a + b", bas.toString());
		bas.search(new SearchAdapter() {
			// カンマ演算子の「,」を「;」に変える
			@Override
			public void search(AbstractExpression exp) {
				if (exp instanceof CommaExpression) {
					exp.setOperator(";");
				}
			}
		});
		assertEquals("a = 1; b = test(1, 2); c = a + b", bas.toString());

		// 複製前のものは変わらない
		assertEquals("a = 1, b = test(1, 2), c = a + b", src.toString());
	}

}
