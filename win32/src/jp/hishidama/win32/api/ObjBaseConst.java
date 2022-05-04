package jp.hishidama.win32.api;

/**
 * objbase.hの定数.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.07
 */
public interface ObjBaseConst {

	// COM initialization flags; passed to CoInitialize.
	/** tagCOINIT：Apartment model */
	public static final int COINIT_APARTMENTTHREADED = 0x2;

	/** tagCOINIT：LE calls objects on any thread. */
	public static final int COINIT_MULTITHREADED = 0x0;

	/** tagCOINIT：Don't use DDE for Ole1 support. */
	public static final int COINIT_DISABLE_OLE1DDE = 0x4;

	/** tagCOINIT：Trade memory for speed. */
	public static final int COINIT_SPEED_OVER_MEMORY = 0x8;
}
