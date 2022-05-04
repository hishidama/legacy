package jp.hishidama.eval.exp;

import java.util.*;

import static org.junit.Assert.*;

import org.junit.Test;

import jp.hishidama.eval.*;
import jp.hishidama.eval.func.*;
import jp.hishidama.eval.var.*;

/**
 * ���Z�q�����ւ������[���̃e�X�g.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >�Ђ�����</a>
 */
public class PowerTest {

	/*
	 * ���Z�q�����ւ���e�X�g�E���\�b�h
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
		testEval(rule, "-2^3^2", null, null, -2 ^ 3 ^ 2); // -1�F���ʂ͔r���I�_���a

		rule = factory.getRule(); // �č쐬�F�f�t�H���g���[���Ɖe���������Ă��Ȃ����Ƃ̊m�F
		testEval(rule, "-2^3^2", null, null, -64);
		testParse(rule, "-2**3**2");
	}

	/**
	 * ����n�̃e�X�g.
	 *
	 * @param str
	 *            ������
	 * @param map
	 *            �ϐ��}�b�v
	 * @param func
	 *            �֐���`
	 * @param expected
	 *            ����������
	 * @version 2007.02.09
	 */
	private void testEval(Rule rule, String str, Map<String, Long> map,
			Function func, long expected) {
		System.out.println("��" + str + "��");
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
	 * �ُ�n�̃e�X�g.
	 * <p>
	 * �����́E�\����͂�EvalException���N����͂��B<br>
	 * �i���b�Z�[�W�����������ǂ����ɂ��Ẵ`�F�b�N�͏ȗ��j
	 * </p>
	 *
	 * @param str
	 *            ������
	 */
	private void testParse(Rule rule, String str) {
		System.out.println("��" + str + "��");
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
	 * �Ǝ����Z�q�ɍ����ւ���t�@�N�g���[�̗�.
	 * <p>
	 * �ݏ�̉��Z�q��BASIC�́u^�v�ɂ��Ă݂��B
	 * </p>
	 */
	class BasicPowerRuleFactory extends ExpRuleFactory {

		public BasicPowerRuleFactory() {
			super();
		}

		protected AbstractExpression createXorExpression() {
			// �u^�v��r���I�_���a�ł͎g��Ȃ��悤�ɂ���
			return null;
		}

		@Override
		protected AbstractExpression createLetXorExpression() {
			// �u^=�v��r���I�_���a�ł͎g��Ȃ��悤�ɂ���
			return null;
		}

		@Override
		protected AbstractExpression createPowerExpression() {
			// �u^�v���w�����Z�q�Ƃ���
			AbstractExpression e = new PowerExpression();
			e.setOperator("^");
			return e;
		}

		@Override
		protected AbstractExpression createLetPowerExpression() {
			// �u^=�v���w�����Z�̑�����Z�q�Ƃ���
			AbstractExpression e = new LetPowerExpression();
			e.setOperator("^=");
			return e;
		}
	}
}
