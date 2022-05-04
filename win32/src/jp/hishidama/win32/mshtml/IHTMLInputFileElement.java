package jp.hishidama.win32.mshtml;

import jp.hishidama.robot.ie.IHTMLInputValueUtil;

/**
 * IHTMLInputFileElementクラス.
 * <p>
 * inputタグ（type="ファイル"）を扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.22
 */
public class IHTMLInputFileElement extends IHTMLInputElement {

	protected IHTMLInputFileElement(long dll_ptr) {
		super(dll_ptr);
	}

	/**
	 * @deprecated type="ファイル"においては当メソッドを使ってもエラーにならないが、実際には値はセットされない。<br> →<a
	 *             target="hishidama"
	 *             href="http://www.ne.jp/asahi/hishidama/home/tech/web/html/input.html#file">ファイルタイプのセキュリティー上の制限</a>
	 * @see IHTMLInputValueUtil#sendValue(String)
	 */
	public void setValue(String value) {
		super.setValue(value);
	}

}
