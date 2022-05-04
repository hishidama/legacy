package jp.hishidama.eval.func;

import jp.hishidama.lang.reflect.InvokeUtil;

/**
 * リフレクションを使用した関数.
 * <p>
 * リフレクションのMethod#invoke()を使用してオブジェクトのメソッドを呼び出す。<br>
 * オブジェクトが無い場合はnullを返す。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2010.02.16
 */
public class MathFunction implements Function {

	protected InvokeUtil invoker;

	@Override
	public Object eval(String name, Object[] args) throws Exception {
		if (invoker == null) {
			invoker = new InvokeUtil();
			invoker.addMethods(Math.class, "");
		}
		return invoker.invoke(name, null, args);
	}

	@Override
	public Object eval(Object object, String name, Object[] args)
			throws Exception {
		return eval(name, args);
	}
}
