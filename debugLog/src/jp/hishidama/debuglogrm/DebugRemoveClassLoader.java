package jp.hishidama.debuglogrm;

import javassist.expr.ExprEditor;
import jp.hishidama.jas.JasClassLoader;

/**
 * デバッグログ出力を削除するクラスローダー.
 * <p>
 * 当クラスローダーを使うと、クラスファイルのロード時に{@link DebugRemoveEditor}を呼び出してデバッグログ出力を削除する。<br>
 * デバッグレベルは「-Ddebuglog.level=文字」で指定する。<br> →<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">使用例</a>
 * </p>
 * 
 * @see UseDebugLog
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/index.html">ひしだま</a>
 * @since 2007.11.17
 */
public class DebugRemoveClassLoader extends JasClassLoader {

	/**
	 * コンストラクター.
	 * <p>
	 * 実行時のVM引数に「-Djava.system.class.loader=jp.hishidama.debuglog.DebugRemoveClassLoader」を指定する。<br>
	 * そうして実行すると当コンストラクターが呼ばれる。
	 * </p>
	 * 
	 * @param parent
	 *            親クラスローダー
	 */
	public DebugRemoveClassLoader(ClassLoader parent) {
		super(parent);
	}

	@Override
	protected ExprEditor createEditor() {
		// DebugRemoveEditorに対して、出力するデバッグレベルを設定
		String args = System.getProperty("debuglog.level");
		DebugRemoveEditor r = new DebugRemoveEditor(args);
		return r;
	}

	protected static final String ANNOTATION_PACKAGE = UseDebugLog.class
			.getPackage().getName();

	@Override
	protected boolean isTarget(String name) {
		if (name.startsWith(ANNOTATION_PACKAGE)) {
			// {@link DebugRemoveEditor}では、アノテーションを使って
			// デバッグログ出力削除の対象かどうかを判断している。
			// if (a instanceof DebugRemoveTarget) { ～ }

			// しかし{@link DebugRemoveEditor}はデフォルトクラスローダーによってロードされるので
			// その中でコーディングされているDebugRemoveTargetもデフォルトクラスローダー所属となる。

			// 当メソッドが呼ばれたとき、その対象クラスがDebugRemoveTargetを使っていると
			// それをロードする為に当クラスローダーも再度呼ばれる。
			// もしそれを当クラスローダーでロードしてしまうと
			// 所属が別のクラスローダーになってしまい、
			// 上記のinstanceofで対象外と判断されてしまう

			// したがって、パッケージ名を見て、DebugRemoveTargetのパッケージだったら
			// デフォルトクラスローダーに任せることにする。
			return false;
		}

		return true;
	}
}
