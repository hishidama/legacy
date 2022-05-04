package jp.hishidama.test;

import jp.hishidama.debuglogrm.DebugRemoveClassLoader;
import jp.hishidama.test.debuglog.TestClass;

/**
 * 実行時のVM引数に独自クラスローダーを指定し、そのクラスローダーの中でJavassistを使って変換する実験。
 * <p>
 * <code>-Djava.system.class.loader=jp.hishidama.debuglog.DebugRemoveClassLoader</code>
 * </p>
 * 
 * @see DebugRemoveClassLoader
 */
public class Main5CL {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("●Main5CL：" + Main5CL.class.getClassLoader());
		System.out.println("●Thread："
				+ Thread.currentThread().getContextClassLoader());

		TestClass tt = new TestClass();
		System.out.println("●TestClass：" + tt.getClass().getClassLoader());

		tt.exec();
		tt.dump();

		// 削除対象クラスの指定でアノテーションが上手く判断できないので
		// 削除対象として認識されない。

		// ●DebugRemoveEditor#doit
		// for (Object a : anns) {
		// if (a instanceof DebugRemoveTarget) { ←これ
		// t = true;
		// break;
		// }
		// }

		// DebugRemoveEditorは【デフォルトローダー】でロードされる。
		// （そこでDebugRemoveTargetも【デフォルトローダー】でロードされる）
		// 判断時に取得されるaは【自作ローダー】によるアノテーションなので
		// instanceofでは別クラス扱いとなり、対象外となる。

		// 仕方がないので、【自作ローダー】内で
		// そのアノテーションに関してはロード対象外としている

	}
}
