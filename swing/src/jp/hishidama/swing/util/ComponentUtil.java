package jp.hishidama.swing.util;

import java.awt.*;
import java.lang.reflect.Method;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * Componentユーティリティー.
 * <p>
 * →<a target="hishidama" href=
 * "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing/ComponentUtil.html"
 * >使用例</a>
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing.html"
 *         >ひしだま</a>
 * @since 2007.02.25
 */
public class ComponentUtil {

	/**
	 * コンポーネントサイズ変更.
	 * <p>
	 * コンポーネント内に格納されている文字列（getText()で取得）を内包できる大きさになるよう コンポーネントのサイズを変更する。
	 * </p>
	 *
	 * @param comp
	 *            コンポーネント
	 * @see #calcComponentSizeFromText(JComponent)
	 */
	public static void resizeComponentFromText(JComponent comp) {
		comp.setSize(calcComponentSizeFromText(comp));
	}

	/**
	 * コンポーネントサイズ計算.
	 * <p>
	 * コンポーネント内に格納されている文字列（getText()で取得）を内包できるコンポーネントの大きさを返す。
	 * </p>
	 *
	 * @param comp
	 *            コンポーネント
	 * @return サイズ
	 * @throws RuntimeException
	 *             getText()が存在しない場合等
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
	 * コンポーネントサイズ計算.
	 * <p>
	 * 文字列を内包できるコンポーネントの大きさを返す。
	 * </p>
	 *
	 * @param comp
	 *            コンポーネント
	 * @param text
	 *            文字列
	 * @return サイズ
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
	 * 文字列サイズ取得.
	 * <p>
	 * コンポーネント内のフォントを使用した 文字列のサイズを返す。
	 * </p>
	 *
	 * @param comp
	 *            コンポーネント
	 * @param text
	 *            文字列
	 * @return サイズ
	 */
	public static Dimension getStringSize(Component comp, String text) {
		FontMetrics fm = comp.getFontMetrics(comp.getFont());
		int w = SwingUtilities.computeStringWidth(fm, text);
		int h = fm.getHeight();
		return new Dimension(w, h);
	}
}
