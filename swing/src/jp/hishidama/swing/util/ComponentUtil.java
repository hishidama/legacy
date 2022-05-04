package jp.hishidama.swing.util;

import java.awt.*;
import java.lang.reflect.Method;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * Component���[�e�B���e�B�[.
 * <p>
 * ��<a target="hishidama" href=
 * "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing/ComponentUtil.html"
 * >�g�p��</a>
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing.html"
 *         >�Ђ�����</a>
 * @since 2007.02.25
 */
public class ComponentUtil {

	/**
	 * �R���|�[�l���g�T�C�Y�ύX.
	 * <p>
	 * �R���|�[�l���g���Ɋi�[����Ă��镶����igetText()�Ŏ擾�j�����ł���傫���ɂȂ�悤 �R���|�[�l���g�̃T�C�Y��ύX����B
	 * </p>
	 *
	 * @param comp
	 *            �R���|�[�l���g
	 * @see #calcComponentSizeFromText(JComponent)
	 */
	public static void resizeComponentFromText(JComponent comp) {
		comp.setSize(calcComponentSizeFromText(comp));
	}

	/**
	 * �R���|�[�l���g�T�C�Y�v�Z.
	 * <p>
	 * �R���|�[�l���g���Ɋi�[����Ă��镶����igetText()�Ŏ擾�j�����ł���R���|�[�l���g�̑傫����Ԃ��B
	 * </p>
	 *
	 * @param comp
	 *            �R���|�[�l���g
	 * @return �T�C�Y
	 * @throws RuntimeException
	 *             getText()�����݂��Ȃ��ꍇ��
	 * @see #calcComponentSizeFromText(JComponent, String)
	 */
	public static Dimension calcComponentSizeFromText(JComponent comp) {
		try {
			Method m = comp.getClass().getMethod("getText");
			String text = (String) m.invoke(comp);
			return calcComponentSizeFromText(comp, text);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * �R���|�[�l���g�T�C�Y�v�Z.
	 * <p>
	 * ����������ł���R���|�[�l���g�̑傫����Ԃ��B
	 * </p>
	 *
	 * @param comp
	 *            �R���|�[�l���g
	 * @param text
	 *            ������
	 * @return �T�C�Y
	 * @see #getStringSize(Component, String)
	 */
	public static Dimension calcComponentSizeFromText(JComponent comp,
			String text) {
		Dimension sz = getStringSize(comp, text);
		Insets in = comp.getInsets();
		return new Dimension(sz.width + in.left + in.right, sz.height + in.top
				+ in.bottom);
	}

	/**
	 * ������T�C�Y�擾.
	 * <p>
	 * �R���|�[�l���g���̃t�H���g���g�p���� ������̃T�C�Y��Ԃ��B
	 * </p>
	 *
	 * @param comp
	 *            �R���|�[�l���g
	 * @param text
	 *            ������
	 * @return �T�C�Y
	 */
	public static Dimension getStringSize(Component comp, String text) {
		FontMetrics fm = comp.getFontMetrics(comp.getFont());
		int w = SwingUtilities.computeStringWidth(fm, text);
		int h = fm.getHeight();
		return new Dimension(w, h);
	}
}
