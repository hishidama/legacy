package jp.hishidama.html.lexer.reader;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * HtHtmlLexer用リーダー.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.01
 */
public class HtLexReader extends PushbackReader {

	/**
	 * コンストラクター.
	 *
	 * @param r
	 *            Reader
	 */
	public HtLexReader(Reader r) {
		super(r, 2);
	}

	private Deque<Char> tbuf = new ArrayDeque<Char>();

	/**
	 * 文字読込.
	 *
	 * @return 文字（必ずnull以外。ファイルの末尾まで読み込んだ場合は{@link Char#EOF}を返す）
	 * @throws IOException
	 */
	public Char readChar() throws IOException {
		Char t = tbuf.pollLast();
		if (t == null) {
			t = readChar0();
		}
		return t;
	}

	/**
	 * プッシュバック.
	 *
	 * @param ch
	 *            文字（nullや{@link Char#EOF}の場合は無処理）
	 */
	public void unreadChar(Char ch) {
		if (ch != null && ch != Char.EOF) {
			tbuf.addLast(ch);
		}
	}

	protected Char readChar0() throws IOException {
		int n = read();
		if (n < 0) {
			return Char.EOF;
		}
		char c = (char) n;
		switch (c) {
		case ' ':
		case '\t':
			return readCharSpace(c);
		case '\r':
			return readCharCR(c);
		case '\n':
			return readCharLF(c);
		case '<':
			return Char.createChar(CharType.TAGO, c);
		case '>':
			return Char.createChar(CharType.TAGC, c);
		case '/':
			return Char.createChar(CharType.SLASH, c);
		case '!':
			return Char.createChar(CharType.EXCL, c);
		case '?':
			return Char.createChar(CharType.QUES, c);
		case '[':
			return Char.createChar(CharType.BRAO, c);
		case ']':
			return Char.createChar(CharType.BRAC, c);
		case '=':
			return Char.createChar(CharType.EQ, c);
		case '\'':
			return Char.createChar(CharType.SQ, c);
		case '\"':
			return Char.createChar(CharType.DQ, c);
		case '\\':
			return Char.createChar(CharType.ESC, c);
		case '-':
			return Char.createChar(CharType.HYPHEN, c);
		default:
			return readCharString(c);
		}
	}

	protected Char readCharSpace(char c) throws IOException {
		StringBuilder sb = new StringBuilder();
		for (;;) {
			sb.append(c);

			int n = read();
			if (n < 0) {
				break;
			}
			c = (char) n;
			switch (c) {
			case ' ':
			case '\t':
				continue;
			}
			unread(n);
			break;
		}
		return Char.createChar(CharType.SPACE, sb);
	}

	protected Char readCharCR(char c) throws IOException {
		StringBuilder sb = new StringBuilder(2);
		sb.append(c);

		int n = read();
		if (n >= 0) {
			c = (char) n;
			if (c == '\n') {
				sb.append(c);
			} else {
				unread(n);
			}
		}
		return Char.createChar(CharType.EOL, sb);
	}

	protected Char readCharLF(char c) {
		return Char.createChar(CharType.EOL, c);
	}

	protected Char readCharString(char c) throws IOException {
		StringBuilder sb = new StringBuilder();
		loop: for (;;) {
			sb.append(c);

			int n = read();
			if (n < 0)
				break;
			c = (char) n;
			switch (c) {
			case ' ':
			case '\t':
			case '\r':
			case '\n':
			case '<':
			case '>':
			case '/':
			case '?':
			case '!':
			case '[':
			case ']':
			case '=':
			case '\'':
			case '\"':
			case '\\':
				unread(n);
				break loop;
			case '-': {
				int m = read();
				if (m == '-') {
					unread(m);
					unread(n);
					break loop;
				}
				unread(m);
				break;
			}
			}
		}
		return Char.createChar(CharType.STRING, sb);
	}
}
