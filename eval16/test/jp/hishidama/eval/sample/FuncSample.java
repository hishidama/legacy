package jp.hishidama.eval.sample;

import java.lang.reflect.Method;

import jp.hishidama.eval.Expression;
import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.func.Function;

/**
 * �֐��̎g�p��
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >�Ђ�����</a>
 */
public class FuncSample {

	public static void main(String[] args) {

		// java.lang.Math#max(long,long)�i�߂�l�Flong�j���Ăяo��
		String str = "max(2, 99)";

		System.out.println("���@�F" + str);

		Expression exp = ExpRuleFactory.getDefaultRule().parse(str);
		Function func = new MathFunction(); // java.lang.Math���Ăяo���T���v���N���X
		exp.setFunction(func);
		long ret = exp.evalLong();

		System.out.println("���ʁF" + ret);
	}

	/**
	 * ���l���Z�֐�(long)�T���v��.
	 *
	 * Math�̊e�֐��̂����A������long�^�̊֐����Ăяo���T���v���B
	 */
	static class MathFunction implements Function {

		@Override
		public Object eval(String name, Object[] args) throws Exception {
			Class<?>[] types = new Class[args.length];
			for (int i = 0; i < types.length; i++) {
				types[i] = long.class;
			}

			Method m = Math.class.getMethod(name, types);
			Object ret = m.invoke(null, args);
			// return Long.parseLong(ret.toString());
			return ((Number) ret).longValue();
		}

		@Override
		public Object eval(Object object, String name, Object[] args)
				throws Exception {
			return eval(name, args);
		}
	}
}
