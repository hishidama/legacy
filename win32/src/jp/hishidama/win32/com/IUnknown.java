package jp.hishidama.win32.com;

/**
 * IUnknownクラス.
 * <p>
 * COMのIUnknownインターフェース、という位置付けのつもりのクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.04
 */
public class IUnknown extends ComPtr {

	protected IUnknown(long dll_ptr) {
		super(dll_ptr);
	}

}
