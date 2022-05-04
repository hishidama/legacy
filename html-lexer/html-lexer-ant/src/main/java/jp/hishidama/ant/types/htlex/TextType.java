package jp.hishidama.ant.types.htlex;

import java.io.IOException;

import jp.hishidama.html.lexer.rule.HtLexer;
import jp.hishidama.html.lexer.token.*;
import jp.hishidama.html.parser.elem.HtElement;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.DataType;
import org.apache.tools.ant.types.EnumeratedAttribute;

/**
 * HtHtmlLexerテキストタイプ.
 *<p>
 * HTMLファイル内のテキストの条件判断・置換を行うデータタイプ。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2009.01.28
 * @version 2009.02.17
 */
public class TextType extends DataType {

	/**
	 * @see org.apache.tools.ant.taskdefs.Replace.NestedString
	 */
	public class NestedString {

		private StringBuilder buf = new StringBuilder();

		public void addText(String val) {
			buf.append(val);
		}

		public String getText() {
			return buf.toString();
		}
	}

	protected NestedString text;

	/**
	 * 検索文字列設定.
	 *
	 * @param s
	 *            文字列
	 */
	public void setText(String s) {
		createText().addText(s);
	}

	/**
	 * 検索文字列（要素）作成.
	 *
	 * @return token要素
	 */
	public NestedString createText() {
		if (text == null) {
			text = new NestedString();
		}
		return text;
	}

	protected NestedString newText = null;
	protected String newTextString = null;

	/**
	 * 置換先文字列設定.
	 *
	 * @param s
	 *            文字列
	 */
	public void setNewText(String s) {
		createNewText().addText(s);
	}

	/**
	 * 置換先文字列（要素）作成.
	 *
	 * @return replacevalue要素
	 */
	public NestedString createNewText() {
		if (newText == null) {
			newText = new NestedString();
		}
		return newText;
	}

	protected MatchEnum textMatch;

	/**
	 * テキストマッチ方法設定.
	 *
	 * @param match
	 *            マッチング方法
	 */
	public void setTextMatch(MatchEnum match) {
		textMatch = match;
	}

	protected StringOpeEnum textOpe = new StringOpeEnum(StringOpeEnum.LET);

	/**
	 * テキスト置換方法設定.
	 *
	 * @param ope
	 *            置換方法
	 */
	public void setNewTextOperation(StringOpeEnum ope) {
		textOpe = ope;
	}

	/**
	 * テキスト種類.
	 *
	 * @version 2009.02.01
	 */
	public static class TextEnum extends EnumeratedAttribute {

		/** "all"：全テキスト */
		public static final String ALL = "all";
		/** "text"：通常テキストのみ */
		public static final String TEXT = "text";
		/** "script"：スクリプトテキストのみ */
		public static final String SCRIPT = "script";

		@Override
		public String[] getValues() {
			return new String[] { ALL, TEXT, SCRIPT };
		}

		public TextEnum() {
		}

		private TextEnum(String s) {
			setValue(s);
		}

		/**
		 * テキスト種類判断.
		 *
		 * @param token
		 *            テキスト
		 * @return 指定されていた種類の場合、true
		 */
		public boolean isType(TextToken token) {
			switch (getIndex()) {
			default:
				return true;
			case 1:
				return !(token instanceof ScriptToken);
			case 2:
				return (token instanceof ScriptToken);
			}
		}
	}

	protected TextEnum textType = new TextEnum(TextEnum.TEXT);

	/**
	 * テキスト種類設定.
	 *
	 * @param type
	 *            テキスト種類
	 */
	public void setTextType(TextEnum type) {
		textType = type;
	}

	protected boolean lines = false;

	/**
	 * 複数行指定.
	 *
	 * @param b
	 *            trueの場合、改行を含んだ複数行を一つの文字列として検索・置換する。<br>
	 *            falseの場合、行ごとに検索・置換する。
	 */
	public void setLines(boolean b) {
		lines = b;
	}

	protected HtLexerConverter converter;

	/**
	 * 精査実行.
	 *
	 * @param conv
	 *            コンバーター
	 * @throws BuildException
	 *             精査エラー時
	 */
	public void validate(HtLexerConverter conv) throws BuildException {
		converter = conv;
		if (text == null) {
			throw new BuildException("token attribute must be set.",
					getLocation());
		}
		if (text.getText().isEmpty()) {
			throw new BuildException(
					"token attribute must not be an empty string.",
					getLocation());
		}

		if (textMatch == null) {
			textMatch = new MatchEnum(MatchEnum.EQUALS);
		}
		textMatch.setPattern(text.getText());
		textOpe.setProject(getProject());
		textOpe.validate(textMatch, getLocation());

		if (newText != null) {
			newTextString = newText.getText();
		}
	}

	/**
	 * テキスト変換実行.
	 *
	 * @param token
	 *            テキスト
	 * @param he
	 *            テキストの属している要素（解析されていない場合、null）
	 * @return 内容を変更した場合、true
	 */
	public boolean convert(TextToken token, HtElement he) {
		if (!textType.isType(token)) {
			return false;
		}

		if (lines) {
			return convertLines(token, he);
		} else {
			return contertLine(token, he);
		}
	}

	protected boolean convertLines(TextToken token, HtElement he) {
		String o = token.getText();
		if (textMatch.matches(o)) {
			converter.logMatch(token);

			String n = convert(o, newTextString);
			if (!converter.equals(n, o)) {
				HtLexer lexer = converter.getLexer(n);
				TextToken tt;
				try {
					tt = lexer.parseText();
				} catch (IOException e) {
					throw new BuildException(e, getLocation());
				} finally {
					try {
						lexer.close();
					} catch (IOException e) {
					}
				}
				tt.calcLine(token.getLine());
				token.setText(tt);
				converter.logConvert("rep", token.getLine(), token);
				return true;
			}
		}

		return false;
	}

	protected boolean contertLine(TextToken token, HtElement he) {
		boolean ret = false;
		for (Token i : token) {
			if (i instanceof WordAtom) {
				String s = i.getText();
				if (textMatch.matches(s)) {
					converter.logMatch(i);
					boolean r = updateWord(token, (WordAtom) i, s);
					if (r) {
						converter.logConvert("rep", i.getLine(), i);
						ret |= r;
					}
				}
			}
		}
		return ret;
	}

	protected boolean updateWord(TextToken text, WordAtom t, String o) {
		if (newTextString == null) {
			return false;
		}
		String n = convert(o, newTextString);
		if (!converter.equals(n, o)) {
			t.setString(n);
			return true;
		}

		return false;
	}

	protected String convert(String text, String newText) {
		return textOpe.convert(text, newText);
	}
}
