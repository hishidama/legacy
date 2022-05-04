package jp.hishidama.win32.api;

/**
 * winnt.hÇÃíËêî
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">Ç–ÇµÇæÇ‹</a>
 * @since 2007.11.06
 */
public interface WinNTConst {
	//
	// Primary language IDs.
	//
	public static final int LANG_NEUTRAL = 0x00;

	public static final int LANG_JAPANESE = 0x11;

	//
	// Sublanguage IDs.
	//
	/** language neutral */
	public static final int SUBLANG_NEUTRAL = 0x00;

	/** user default */
	public static final int SUBLANG_DEFAULT = 0x01;

	/** system default */
	public static final int SUBLANG_SYS_DEFAULT = 0x02;

	//
	// Sorting IDs.
	//
	/** sorting default */
	public static final int SORT_DEFAULT = 0x0;

	/** Japanese XJIS order */
	public static final int SORT_JAPANESE_XJIS = 0x0;

	/** Japanese Unicode order */
	public static final int SORT_JAPANESE_UNICODE = 0x1;

	//
	// Default System and User IDs for language and locale.
	//
	public static final int LANG_SYSTEM_DEFAULT = LANG_NEUTRAL
			| (SUBLANG_SYS_DEFAULT << 10);

	public static final int LANG_USER_DEFAULT = LANG_NEUTRAL
			| (SUBLANG_DEFAULT << 10);

	public static final int LOCALE_SYSTEM_DEFAULT = LANG_SYSTEM_DEFAULT
			| (SORT_DEFAULT << 16);

	public static final int LOCALE_USER_DEFAULT = LANG_USER_DEFAULT
			| (SORT_DEFAULT << 16);

}
