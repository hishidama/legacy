package jp.hishidama.win32.mshtml;

/**
 * IHTMLTextContainerクラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
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
