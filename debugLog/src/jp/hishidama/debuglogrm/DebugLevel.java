package jp.hishidama.debuglogrm;

/**
 * デバッグレベル.
 * <p>
 * デバッグログの出力レベルを示す。 <br> →<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">使用例</a>
 * </p>
 * 
 * @see DebugLogWriteMethod
 * @see DebugRemoveEditor
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/index.html">ひしだま</a>
 * @since 2007.11.17
 */
public enum DebugLevel {
	/** 致命的レベル */
	FATAL,
	/** エラーレベル */
	ERROR,
	/** 警告レベル */
	WARNING,
	/** 情報レベル */
	INFO,
	/** 詳細レベル */
	VERBOSE,
	/** デバッグレベル */
	DEBUG,
	/** トレースレベル */
	TRACE,
}
