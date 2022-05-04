package jp.hishidama.eval.example;

import java.lang.reflect.Method;

import jp.hishidama.eval.Expression;
import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.func.Function;

/**
 * 関数の使用例
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class FuncExample {

	public static void main(String[] args) {

		// java.lang.Math#max(long,long)（戻り値：long）を呼び出す
		String str = "max(2, 99)";

		System.out.println("式　：" + str);

		Expression exp = ExpRuleFactory.getDefaultRule().parse(str);
		Function func = new MathFunction(); // java.lang.Mathを呼び出すサンプルクラス
		exp.setFunction(func);
		long ret = exp.evalLong();

		System.out.println("結果：" + ret);
	}

	/**
	 * 数値演算関数(long)サンプル.
	 *
	 * Mathの各関数のうち、引数がlong型の関数を呼び出すサンプル。
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
