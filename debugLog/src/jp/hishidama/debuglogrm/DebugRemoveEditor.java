package jp.hishidama.debuglogrm;

import javassist.CtClass;
import javassist.CtMethod;

/**
 * デバッグログ出力削除クラス.
 * <p>
 * デバッグログ出力メソッド（{@link DebugLogWriteMethod}アノテーションの付いているメソッド）の削除を行うクラス。<br>
 * 削除を行う対象となるのは、{@link UseDebugLog}アノテーションの付いているクラスのみ。<br> →<a
 * target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">使用例</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/index.html">ひしだま</a>
 * @since 2007.11.17
 * @version 2007.11.18
 */
public class DebugRemoveEditor extends AbstractDebugRemoveEditor {

	protected int writeLevel;

	/**
	 * コンストラクター.
	 * <p>
	 * 出力レベルはデフォルト。
	 * </p>
	 * 
	 * @see #setDefaultLevel()
	 */
	public DebugRemoveEditor() {
		setDefaultLevel();
	}

	/**
	 * コンストラクター.
	 * <p>
	 * 指定された出力レベルで初期化する。
	 * </p>
	 * 
	 * @see #setLevel(String)
	 */
	public DebugRemoveEditor(String levelString) {
		setLevel(levelString);
	}

	/**
	 * 出力レベル初期化.
	 * <p>
	 * 例：levelStringが「EWI」のとき、ERROR・WARNING・INFOレベルを出力する（その他のレベルの出力メソッドを削除する）。
	 * </p>
	 * 
	 * @param levelString
	 *            出力レベルを複数の文字の組み合わせで指定する。ただしnullの場合はデフォルトとなる。 <table border=1>
	 *            <tr>
	 *            <th>指定</th>
	 *            <th>出力レベル</th>
	 *            </tr>
	 *            <tr>
	 *            <td>F</td>
	 *            <td>FATALレベル</td>
	 *            </tr>
	 *            <tr>
	 *            <td>E</td>
	 *            <td>ERRORレベル</td>
	 *            </tr>
	 *            <tr>
	 *            <td>W</td>
	 *            <td>WARNINGレベル</td>
	 *            </tr>
	 *            <tr>
	 *            <td>I</td>
	 *            <td>INFOレベル</td>
	 *            </tr>
	 *            <tr>
	 *            <td>V</td>
	 *            <td>VERBOSEレベル</td>
	 *            </tr>
	 *            <tr>
	 *            <td>D</td>
	 *            <td>DEBUGレベル</td>
	 *            </tr>
	 *            <tr>
	 *            <td>T</td>
	 *            <td>TRACEレベル</td>
	 *            </tr>
	 *            </table>
	 */
	public void setLevel(String levelString) {
		removeAllLevel();
		if (levelString == null) {
			setDefaultLevel();
			return;
		}
		for (int i = 0; i < levelString.length(); i++) {
			switch (levelString.charAt(i)) {
			case 'F':
			case 'f':
				addWriteLevel(DebugLevel.FATAL);
				break;
			case 'E':
			case 'e':
				addWriteLevel(DebugLevel.ERROR);
				break;
			case 'W':
			case 'w':
				addWriteLevel(DebugLevel.WARNING);
				break;
			case 'I':
			case 'i':
				addWriteLevel(DebugLevel.INFO);
				break;
			case 'V':
			case 'v':
				addWriteLevel(DebugLevel.VERBOSE);
				break;
			case 'D':
			case 'd':
				addWriteLevel(DebugLevel.DEBUG);
				break;
			case 'T':
			case 't':
				addWriteLevel(DebugLevel.TRACE);
				break;
			}
		}
	}

	/**
	 * デフォルト出力レベル設定.
	 * <p>
	 * デフォルトは、FATAL・ERROR・WARNING・INFOレベル。
	 * </p>
	 */
	public void setDefaultLevel() {
		addWriteLevel(DebugLevel.FATAL);
		addWriteLevel(DebugLevel.ERROR);
		addWriteLevel(DebugLevel.WARNING);
		addWriteLevel(DebugLevel.INFO);
	}

	/**
	 * 出力レベル設定.
	 * 
	 * @param level
	 *            出力レベル
	 */
	public void addWriteLevel(DebugLevel level) {
		writeLevel |= 1 << level.ordinal();
	}

	/**
	 * 否出力レベル設定.
	 * 
	 * @param level
	 *            出力しないレベル
	 */
	public void removeLevel(DebugLevel level) {
		writeLevel &= ~(1 << level.ordinal());
	}

	/**
	 * 出力レベル全削除.
	 */
	public void removeAllLevel() {
		writeLevel = 0;
	}

	/**
	 * 出力レベル判定.
	 * 
	 * @param level
	 *            出力レベル
	 * @return 出力するとき、true
	 */
	public boolean isWriteLevel(DebugLevel level) {
		return (writeLevel & (1 << level.ordinal())) != 0;
	}

	/**
	 * 出力レベル文字列取得.
	 * 
	 * @return 出力レベル
	 */
	public String getLevelString() {
		if (writeLevel == 0) {
			return "nothing";
		}
		StringBuilder sb = new StringBuilder(64);
		for (DebugLevel level : DebugLevel.values()) {
			if (isWriteLevel(level)) {
				if (sb.length() != 0) {
					sb.append(',');
				}
				sb.append(level);
			}
		}
		return sb.toString();
	}

	@Override
	protected boolean useDebugLog(CtClass cc) {
		Object[] anns;
		try {
			anns = cc.getAnnotations();
		} catch (ClassNotFoundException e) {
			// アノテーションが無いときは対象外
			// e.printStackTrace();
			return false;
		}

		for (Object a : anns) {
			if (a instanceof UseDebugLog) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean isDebugLogWriteMethod(CtMethod m) {
		Object[] anns;
		try {
			anns = m.getAnnotations();
		} catch (ClassNotFoundException e) {
			// アノテーションが無いので変換対象外
			return false;
		}
		boolean ret = false;
		for (Object a : anns) {
			if (a instanceof DebugLogWriteMethod) {
				DebugLogWriteMethod dm = (DebugLogWriteMethod) a;
				if (isWriteLevel(dm.value())) {
					return false;
				} else {
					ret = true;
				}
			}
		}
		return ret;
	}

}
