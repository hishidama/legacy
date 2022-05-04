package jp.hishidama.win32.mshtml;

/**
 * IHTMLTextContainer�N���X.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.10.22
 */
public interface IHTMLTextContainer {

	public int getScrollLeft();

	public void setScrollLeft(int x);

	public int getScrollTop();

	public void setScrollTop(int y);

	public int getScrollWidth();

	public int getScrollHeight();
}
