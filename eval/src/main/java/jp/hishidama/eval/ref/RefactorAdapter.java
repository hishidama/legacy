package jp.hishidama.eval.ref;

/**
 * リファクタリングアダプタークラス.
 * <p>
 * 空実装されたリファクタリングインターフェース。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html">ひしだま</a>
 * @since 2007.02.19
 */
public class RefactorAdapter implements Refactor {

	public String getNewFuncName(Object target, String name) {
		return null;
	}

	public String getNewName(Object target, String name) {
		return null;
	}
}
