package jp.hishidama.eval.exp;

import static org.junit.Assert.*;

import org.junit.Test;

import jp.hishidama.eval.*;
import jp.hishidama.eval.srch.SearchAdapter;

/**
 * �ォ�牉�Z�q�����ւ���e�X�g.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >�Ђ�����</a>
 */
public class ChangeOperatorTest {

	/*
	 * ���Z�q�����ւ���e�X�g�E���\�b�h
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
			// �ݏ扉�Z�q�́u**�v���u^�v�ɕς���
			@Override
			public void search(AbstractExpression exp) {
				if (exp instanceof PowerExpression) {
					exp.setOperator("^");
				}
			}
		});
		assertEquals("5 * 4 ^ 3 ^ 2 * -1", bas.toString());

		// �����O�̂��͕̂ς��Ȃ�
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
			// �J���}���Z�q�́u,�v���u;�v�ɕς���
			@Override
			public void search(AbstractExpression exp) {
				if (exp instanceof CommaExpression) {
					exp.setOperator(";");
				}
			}
		});
		assertEquals("a = 1; b = test(1, 2); c = a + b", bas.toString());

		// �����O�̂��͕̂ς��Ȃ�
		assertEquals("a = 1, b = test(1, 2), c = a + b", src.toString());
	}

}
