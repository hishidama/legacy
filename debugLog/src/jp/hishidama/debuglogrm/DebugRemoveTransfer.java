package jp.hishidama.debuglogrm;

import javassist.CannotCompileException;
import javassist.CtClass;
import jp.hishidama.jas.JasTransfer;
import jp.hishidama.jas.JasTransferPremain;

/**
 * �f�o�b�O���O�o�͍폜�p�ϊ��N���X.
 * <p>
 * {@link JasTransferPremain#premain(String, java.lang.instrument.Instrumentation)}����o�^����ăf�o�b�O���O�o�̓��\�b�h�̍폜���s���N���X�B<br>
 * ������{@link DebugRemoveEditor}���Ăяo���Ă���B<br> ��<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">�g�p��</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/index.html">�Ђ�����</a>
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
