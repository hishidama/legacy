package jp.hishidama.eval.srch;

import static org.junit.Assert.*;

import org.junit.Test;

import jp.hishidama.eval.*;
import jp.hishidama.eval.exp.AbstractExpression;
import jp.hishidama.eval.exp.NumberExpression;
import jp.hishidama.eval.oper.LongOperator;
import jp.hishidama.eval.srch.SearchAdapter;

/**
 * Expressionをサーチするテスト.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class SearchTest {

	/*
	 * Expressionをサーチするテスト・メソッド
	 */
	@Test
	public void testSearch() {

		Rule rule = ExpRuleFactory.getDefaultRule().defaultOperator(
				new LongOperator());
		Expression exp;
		NumberCounter nc;

		exp = rule.parse("99");
		exp.search(nc = new NumberCounter());
		assertEquals(1, nc.count);
		assertEquals(99, nc.check);

		// 単項演算子
		exp = rule.parse("((100)++)-- +-2");
		exp.search(nc = new NumberCounter());
		assertEquals(2, nc.count);
		assertEquals(100 * 1 + 2 * 2, nc.check);

		// 二項演算子
		exp = rule.parse("6+5*4/3%2");
		exp.search(nc = new NumberCounter());
		assertEquals(5, nc.count);
		assertEquals(6 * 1 + 5 * 2 + 4 * 3 + 3 * 4 + 2 * 5, nc.check);

		// 見つからない
		exp = rule.parse("a-b&c^d");
		exp.search(nc = new NumberCounter());
		assertEquals(0, nc.count);
		assertEquals(0, nc.check);

		// 関数・配列
		exp = rule.parse("test[1].get(2,3)[4+5][6]");
		exp.search(nc = new NumberCounter());
		assertEquals(6, nc.count);
		assertEquals(1 + 2 * 2 + 3 * 3 + 4 * 4 + 5 * 5 + 6 * 6, nc.check);
	}

	class NumberCounter extends SearchAdapter {

		public int count = 0;

		public long check = 0;

		@Override
		public void search(AbstractExpression exp) {
			if (exp instanceof NumberExpression) {
				// System.out.println("[" + count + "]" + exp);
				count++;
				long val = ((Number) exp.eval()).longValue();
				check += count * val;
			}
		}

	}

}
