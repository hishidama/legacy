package jp.hishidama.debuglogrm;

import javassist.CannotCompileException;
import javassist.CtClass;
import jp.hishidama.jas.JasTransfer;
import jp.hishidama.jas.JasTransferPremain;

/**
 * デバッグログ出力削除用変換クラス.
 * <p>
 * {@link JasTransferPremain#premain(String, java.lang.instrument.Instrumentation)}から登録されてデバッグログ出力メソッドの削除を行うクラス。<br>
 * 内部で{@link DebugRemoveEditor}を呼び出している。<br> →<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">使用例</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/index.html">ひしだま</a>
 * @since 2007.11.17
 */
public class DebugRemoveTransfer extends JasTransfer {

	protected DebugRemoveEditor editor;

	@Override
	public void init(String agentArgs) {
		editor = new DebugRemoveEditor(agentArgs);
	}

	@Override
	protected void transform(CtClass ctClass) {
		try {
			ctClass.instrument(editor);
		} catch (CannotCompileException e) {
			throw new RuntimeException(e);
		}
	}

}
