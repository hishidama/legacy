package jp.hishidama.eval.func;

/**
 * 常にnullを返す関数.
 * <p>
 * 戻り値として常にnullを返す。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @version eval16
 */
public class VoidFunction implements Function {

	protected boolean dump;

	/**
	 * コンストラクター.
	 * <p>
	 * コンソール出力を行わない。
	 * </p>
	 */
	public VoidFunction() {
		this(false);
	}

	/**
	 * コンストラクター.
	 *
	 * @param dump
	 *            出力有無（trueの場合、関数が呼ばれた際に関数名と引数をコンソールに出力する）
	 */
	public VoidFunction(boolean dump) {
		this.dump = dump;
	}

	@Override
	public Object eval(String name, Object[] args) throws Exception {
		if (dump) {
			System.out.println(name + "関数が呼ばれた");
			for (int i = 0; i < args.length; i++) {
				System.out.println("arg[" + i + "] " + args[i]);
			}
		}
		return null;

	}

	@Override
	public Object eval(Object object, String name, Object[] args)
			throws Exception {
		if (dump) {
			System.out.println(object + "." + name + "関数が呼ばれた");
			for (int i = 0; i < args.length; i++) {
				System.out.println("arg[" + i + "] " + args[i]);
			}
		}

		return null;
	}
}
