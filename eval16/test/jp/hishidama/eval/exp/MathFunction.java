package jp.hishidama.eval.exp;

import java.lang.reflect.Method;

import jp.hishidama.eval.func.Function;

/**
 * 数値演算関数サンプル.
 * <p>
 * {@link java.lang.Math}のメソッドのうち、引数の型が一致するメソッドを呼び出す。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @see java.lang.Math
 * @version 2010.02.15
 */
public class MathFunction implements Function {

	@Override
	public Object eval(String name, Object[] args) throws Exception {
		Class<?>[] types = new Class[args.length];
		for (int i = 0; i < types.length; i++) {
			Class<?> c = args[i].getClass();
			if (Double.class.isAssignableFrom(c)) {
				c = Double.TYPE;
			} else if (Float.class.isAssignableFrom(c)) {
				c = Float.TYPE;
			} else if (Integer.class.isAssignableFrom(c)) {
				c = Integer.TYPE;
			} else if (Number.class.isAssignableFrom(c)) {
				c = Long.TYPE;
			}
			types[i] = c;
		}

		Method m = Math.class.getMethod(name, types);
		return m.invoke(null, args);
	}

	@Override
	public Object eval(Object object, String name, Object[] args)
			throws Exception {
		return eval(name, args);
	}
}
