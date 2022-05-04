package jp.hishidama.robot;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Robotユーティリティー.
 * <p>
 * {@link java.awt.Robot}を拡張したユーティリティークラス。<br> →<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">使用例</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">ひしだま</a>
 * @version 2007.9.18
 */
public class RobotUtil extends Robot {

	protected int KEY_WAIT = 10;

	/**
	 * 座標補正値
	 */
	protected Point offset = new Point(0, 0);

	public RobotUtil() throws AWTException {
	}

	/**
	 * ファクトリー.
	 * 
	 * @return インスタンス
	 */
	public static RobotUtil getInstance() {
		try {
			return new RobotUtil();
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 座標補正値設定.
	 * 
	 * @param offset
	 *            座標補正値
	 */
	public void setOffset(Point offset) {
		this.offset = offset;
	}

	/**
	 * 座標補正値取得.
	 * 
	 * @return 座標補正値
	 */
	public Point getOffset() {
		return offset;
	}

	/**
	 * 文字列コピー.
	 * <p>
	 * CTRL+Cを送信し、クリップボードから文字列を取得する。
	 * </p>
	 * 
	 * @return 文字列（null：取得失敗時）
	 * @see #clipboardGetString()
	 */
	public String copyString() {
		sendCtrlString("C");
		delay(20);
		return clipboardGetString();
	}

	/**
	 * クリップボードから文字列取得.
	 * 
	 * @return 文字列（null：取得失敗時）
	 */
	public String clipboardGetString() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Clipboard clip = kit.getSystemClipboard();

		Transferable transfer = clip.getContents(null);
		if (!transfer.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			return null;
		}
		try {
			return (String) transfer.getTransferData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 文字列貼り付け.
	 * <p>
	 * クリップボードに文字列をコピーし、CTRL+Vキーを送信する。
	 * </p>
	 * 
	 * @param str
	 *            文字列
	 * @see #clipboardCopyString(String)
	 */
	public void pasteString(String str) {
		clipboardCopyString(str);
		sendCtrlString("V");
	}

	/**
	 * クリップボードへコピー.
	 * 
	 * @param str
	 *            文字列
	 */
	public void clipboardCopyString(String str) {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Clipboard clip = kit.getSystemClipboard();
		StringSelection ss = new StringSelection(str);
		clip.setContents(ss, ss);
	}

	/**
	 * キー送信.
	 * 
	 * @param str
	 *            文字列
	 * @see #sendChar(char)
	 */
	public void sendString(String str) {
		for (int i = 0; i < str.length(); i++) {
			sendChar(str.charAt(i));
		}
	}

	/**
	 * キー送信.
	 * 
	 * @param c
	 *            文字
	 * @see #convertCodeFromChar(char)
	 */
	public void sendChar(char c) {
		int code = convertCodeFromChar(c);
		sendKeyCode(code);
	}

	/**
	 * キーコード送信.
	 * 
	 * @param code
	 *            {@link KeyEvent}
	 * @see #keyPress(int)
	 * @see #keyRelease(int)
	 */
	public void sendKeyCode(int code) {
		super.keyPress(code);
		super.delay(KEY_WAIT);
		super.keyRelease(code);
		super.delay(KEY_WAIT);
	}

	/**
	 * Ctrl+キー送信.
	 * <p>
	 * コントロールキーと同時にキーを送信する。
	 * </p>
	 * 
	 * @param str
	 *            文字列
	 * @see #sendString(String)
	 */
	public void sendCtrlString(String str) {
		super.keyPress(KeyEvent.VK_CONTROL);
		super.delay(KEY_WAIT);
		sendString(str);
		super.keyRelease(KeyEvent.VK_CONTROL);
		super.delay(KEY_WAIT);
	}

	/**
	 * Shift+キー送信.
	 * <p>
	 * シフトキーと同時にキーを送信する。
	 * </p>
	 * 
	 * @param str
	 *            文字列
	 * @see #sendString(String)
	 */
	public void sendShiftString(String str) {
		super.keyPress(KeyEvent.VK_SHIFT);
		super.delay(KEY_WAIT);
		sendString(str);
		super.keyRelease(KeyEvent.VK_SHIFT);
		super.delay(KEY_WAIT);
	}

	/**
	 * キーコード変換.
	 * <p>
	 * 文字を、キーイベントの仮想キーコードに変換する。
	 * </p>
	 * 
	 * @param c
	 *            文字
	 * @return コード
	 * @exception RuntimeException
	 *                変換できなかったとき
	 * @see KeyEvent
	 */
	public static int convertCodeFromChar(char c) {
		switch (c) {
		case '\n':
			return KeyEvent.VK_ENTER;
		case '\b':
			return KeyEvent.VK_BACK_SPACE;
		case '\t':
			return KeyEvent.VK_TAB;
		case '\u001b':
			return KeyEvent.VK_ESCAPE;
		case ' ':
			return KeyEvent.VK_SPACE;
		case ',':
			return KeyEvent.VK_COMMA;
		case '-':
			return KeyEvent.VK_MINUS;
		case '.':
			return KeyEvent.VK_PERIOD;
		case '/':
			return KeyEvent.VK_SLASH;
		case ';':
			return KeyEvent.VK_SEMICOLON;
		case '=':
			return KeyEvent.VK_EQUALS;
		case '[':
			return KeyEvent.VK_OPEN_BRACKET;
		case '\\':
			return KeyEvent.VK_BACK_SLASH;
		case ']':
			return KeyEvent.VK_CLOSE_BRACKET;
		case '`':
			return KeyEvent.VK_BACK_QUOTE;
		case '\'':
			return KeyEvent.VK_QUOTE;
		case '&':
			return KeyEvent.VK_AMPERSAND;
		case '*':
			return KeyEvent.VK_ASTERISK;
		case '\"':
			return KeyEvent.VK_QUOTEDBL;
		case '<':
			return KeyEvent.VK_LESS;
		case '>':
			return KeyEvent.VK_GREATER;
		case '@':
			return KeyEvent.VK_AT;
		case ':':
			return KeyEvent.VK_COLON;
		case '^':
			return KeyEvent.VK_CIRCUMFLEX;
		case '$':
			return KeyEvent.VK_DOLLAR;
		case '!':
			return KeyEvent.VK_EXCLAMATION_MARK;
		case '(':
			return KeyEvent.VK_LEFT_PARENTHESIS;
		case '#':
			return KeyEvent.VK_NUMBER_SIGN;
		case '+':
			return KeyEvent.VK_PLUS;
		case ')':
			return KeyEvent.VK_RIGHT_PARENTHESIS;
		case '_':
			return KeyEvent.VK_UNDERSCORE;
		case '0':
			return KeyEvent.VK_0;
		case '1':
			return KeyEvent.VK_1;
		case '2':
			return KeyEvent.VK_2;
		case '3':
			return KeyEvent.VK_3;
		case '4':
			return KeyEvent.VK_4;
		case '5':
			return KeyEvent.VK_5;
		case '6':
			return KeyEvent.VK_6;
		case '7':
			return KeyEvent.VK_7;
		case '8':
			return KeyEvent.VK_8;
		case '9':
			return KeyEvent.VK_9;
		case 'A':
		case 'a':
			return KeyEvent.VK_A;
		case 'B':
		case 'b':
			return KeyEvent.VK_B;
		case 'C':
		case 'c':
			return KeyEvent.VK_C;
		case 'D':
		case 'd':
			return KeyEvent.VK_D;
		case 'E':
		case 'e':
			return KeyEvent.VK_E;
		case 'F':
		case 'f':
			return KeyEvent.VK_F;
		case 'G':
		case 'g':
			return KeyEvent.VK_G;
		case 'H':
		case 'h':
			return KeyEvent.VK_H;
		case 'I':
		case 'i':
			return KeyEvent.VK_I;
		case 'J':
		case 'j':
			return KeyEvent.VK_J;
		case 'K':
		case 'k':
			return KeyEvent.VK_K;
		case 'L':
		case 'l':
			return KeyEvent.VK_L;
		case 'M':
		case 'm':
			return KeyEvent.VK_M;
		case 'N':
		case 'n':
			return KeyEvent.VK_N;
		case 'O':
		case 'o':
			return KeyEvent.VK_O;
		case 'P':
		case 'p':
			return KeyEvent.VK_P;
		case 'Q':
		case 'q':
			return KeyEvent.VK_Q;
		case 'R':
		case 'r':
			return KeyEvent.VK_R;
		case 'S':
		case 's':
			return KeyEvent.VK_S;
		case 'T':
		case 't':
			return KeyEvent.VK_T;
		case 'U':
		case 'u':
			return KeyEvent.VK_U;
		case 'V':
		case 'v':
			return KeyEvent.VK_V;
		case 'W':
		case 'w':
			return KeyEvent.VK_W;
		case 'X':
		case 'x':
			return KeyEvent.VK_X;
		case 'Y':
		case 'y':
			return KeyEvent.VK_Y;
		case 'Z':
		case 'z':
			return KeyEvent.VK_Z;
		case '\u007f':
			return KeyEvent.VK_DELETE;

		}

		throw new RuntimeException("convert error:" + Integer.toHexString(c));
	}

	/**
	 * マウスカーソル移動.
	 * 
	 * @param x
	 * @param y
	 * @see #setOffset(Point)
	 */
	public void mouseMove(int x, int y) {
		super.mouseMove(x + offset.x, y + offset.y);
	}

	/**
	 * 左クリック.
	 * 
	 * @param x
	 * @param y
	 * @see #mouseMove(int, int)
	 * @see #clickL()
	 */
	public void clickL(int x, int y) {
		mouseMove(x, y);
		super.delay(10);
		clickL();
	}

	/**
	 * 左クリック.
	 * 
	 * @see #mousePress(int)
	 * @see #mouseRelease(int)
	 */
	public void clickL() {
		super.mousePress(InputEvent.BUTTON1_MASK);
		super.delay(10);
		super.mouseRelease(InputEvent.BUTTON1_MASK);
		super.delay(10);
	}

	/**
	 * 右クリック.
	 * 
	 * @param x
	 * @param y
	 * @see #mouseMove(int, int)
	 * @see #clickR()
	 */
	public void clickR(int x, int y) {
		mouseMove(x, y);
		super.delay(10);
		clickR();
	}

	/**
	 * 右クリック.
	 * 
	 * @see #mousePress(int)
	 * @see #mouseRelease(int)
	 */
	public void clickR() {
		super.mousePress(InputEvent.BUTTON3_MASK);
		super.delay(10);
		super.mouseRelease(InputEvent.BUTTON3_MASK);
		super.delay(10);
	}

}
