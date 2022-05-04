package jp.hishidama.eval.ref;

/**
 * 変数名変更リファクタリングクラス.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2007.02.19
 */
public class RefactorVarName extends RefactorAdapter {

	protected Class<?> targetClass;

	protected String oldName;

	protected String newName;

	public RefactorVarName(Class<?> targetClass, String oldName, String newName) {
		this.targetClass = targetClass;
		this.oldName = oldName;
		this.newName = newName;
		if (oldName == null || newName == null) {
			throw new NullPointerException();
		}
	}

	@Override
	public String getNewName(Object target, String name) {
		if (!name.equals(oldName)) {
			return null;
		}
		if (targetClass == null) {
			if (target == null) {
				return newName;
			}
		} else {
			if (target != null
					&& targetClass.isAssignableFrom(target.getClass())) {
				return newName;
			}
		}
		return null;
	}

}
