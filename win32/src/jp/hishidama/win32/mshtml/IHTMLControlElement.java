package jp.hishidama.win32.mshtml;

/**
 * IHTMLControlElementƒNƒ‰ƒX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">‚Ð‚µ‚¾‚Ü</a>
 * @since 2007.10.22
 */
public interface IHTMLControlElement {

	public void setTabIndex(int index);

	public int getTabIndex();

	public void setAccessKey(String key);

	public String getAccessKey();

	public void focus();

	public void blur();

	public int getClientTop();

	public int getClientLeft();

	public int getClientWidth();

	public int getClientHeight();
}
