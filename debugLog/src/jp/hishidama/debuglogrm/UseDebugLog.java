package jp.hishidama.debuglogrm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * �f�o�b�O���O���o�͂���N���X��\���A�m�e�[�V����.
 * <p>
 * ���̃A�m�e�[�V�������t���Ă���N���X���̊e���\�b�h�ɑ΂��A�f�o�b�O���O�o�͍폜�������s���B<br> ��<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">�g�p��</a>
 * </p>
 * 
 * @see DebugRemoveEditor
 * @see DebugLogWriteMethod
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/index.html">�Ђ�����</a>
 * @since 2007.11.17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE })
public @interface UseDebugLog {
}
