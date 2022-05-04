package jp.hishidama.debuglogrm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * �f�o�b�O���O�o�̓��\�b�h�ł��邱�Ƃ�\���A�m�e�[�V����.
 * <p>
 * {@link DebugRemoveEditor}�ł́A���A�m�e�[�V������t���Ă��郁�\�b�h���f�o�b�O���O�o�͗p���\�b�h�ƌ��Ȃ��B<br>
 * �A�v���P�[�V�����̃N���X���y���A�m�e�[�V������t���Ă��郁�\�b�h�z���Ă�ł���Ƃ��A{@link DebugRemoveEditor}�ɂ���Ă��̃��\�b�h�Ăяo�����폜�����B
 * <br> ��<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">�g�p��</a>
 * </p>
 * 
 * @see DebugLevel
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/index.html">�Ђ�����</a>
 * @since 2007.11.17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DebugLogWriteMethod {
	DebugLevel value();
}
