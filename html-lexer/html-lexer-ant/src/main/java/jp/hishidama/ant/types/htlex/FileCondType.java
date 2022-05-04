package jp.hishidama.ant.types.htlex;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.ant.types.TextParameter;
import jp.hishidama.ant.types.htlex.eval.HtLexerExpRuleFactory;
import jp.hishidama.eval.Expression;
import jp.hishidama.html.lexer.token.ListToken;
import jp.hishidama.html.lexer.token.Tag;
import jp.hishidama.html.lexer.token.TextToken;
import jp.hishidama.html.lexer.token.Token;
import jp.hishidama.html.parser.elem.HtElement;
import jp.hishidama.html.parser.elem.HtListElement;
import jp.hishidama.html.parser.elem.HtTagElement;
import jp.hishidama.html.parser.elem.HtTokenElement;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.IntrospectionHelper;
import org.apache.tools.ant.Location;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.DataType;

/**
 * HtHtmlLexerファイル条件タイプ.
 *<p>
 * HTMLファイルを読み込む前にそのファイルを処理対象とするかどうかの条件を保持するデータタイプ。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2010.02.06
 */
public class FileCondType extends DataType {

	protected Expression ifExp;

	/**
	 * 条件式設定.
	 *
	 * @param s
	 *            条件式
	 */
	public void setIf(String s) {
		ifExp = HtLexerExpRuleFactory.getRule(getProject()).parse(s);
	}

	protected final List<TagType> tagList = new ArrayList<TagType>();

	/**
	 * タグ条件追加.
	 *
	 * @param tag
	 *            タグタイプ
	 */
	public void addConfigured(TagType tag) {
		tagList.add(tag);
	}

	/**
	 * パラメーター設定.
	 * <p>
	 * 属性値に書きづらい長い式などをパラメーターのボディー部に記述できる。<br>
	 * {@code <cfile><param name="foo">bar</param></cfile>}の場合、{@code <cfile
	 * foo="bar">}と同じ。
	 * </p>
	 *
	 * @param param
	 *            パラメーター
	 */
	public void addConfiguredParam(TextParameter param) {
		String name = param.getName();
		if (name == null || name.isEmpty()) {
			throw new BuildException("name attribute must be set.", param
					.getLocation());
		}
		IntrospectionHelper ih = IntrospectionHelper.getHelper(getProject(),
				this.getClass());
		try {
			ih.setAttribute(getProject(), this, name, param.getValue());
		} catch (Exception e) {
			throw new BuildException(e, param.getLocation());
		}
	}

	protected final List<TextType> textList = new ArrayList<TextType>();

	/**
	 * テキスト条件追加.
	 *
	 * @param text
	 *            テキストタイプ
	 */
	public void addConfigured(TextType text) {
		textList.add(text);
	}

	protected HtLexerConverter converter;

	/**
	 * 精査実行.
	 *
	 * @param conv
	 *
	 * @throws BuildException
	 *             精査エラー時
	 */
	public void validate(HtLexerConverter conv) throws BuildException {
		converter = conv;
		for (TagType i : tagList) {
			i.validate(conv);
		}
		for (TextType i : textList) {
			i.validate(conv);
		}
	}

	/**
	 * 変換可否判断.
	 *
	 * @return trueの場合、HTML変更を実行する
	 */
	public boolean isConvert() {
		if (!equalsIf()) {
			return false;
		}

		return true;
	}

	protected boolean equalsIf() {
		if (ifExp == null) {
			return true;
		}
		try {
			Object r = ifExp.eval();
			if (r == null) {
				return false;
			}
			if (r instanceof Boolean) {
				return ((Boolean) r).booleanValue();
			}
			if (r instanceof Number) {
				return ((Number) r).intValue() != 0;
			}
			if (r instanceof String) {
				return !((String) r).isEmpty();
			}
		} catch (BuildException e) {
			e.setLocation(getLocation());
			throw e;
		} catch (Exception e) {
			throw new BuildException(e, getLocation());
		}
		return true;
	}

	/**
	 * HTML変更.
	 * <p>
	 * {@link jp.hishidama.ant.taskdefs.HtLexerTask}によってHTMLが読み込まれた後に呼ばれる。<br>
	 * （HtHtmlParserによって構文解析された場合に当メソッドが呼ばれる）<br>
	 * 引数の内容を変更してtrueを返すと、その内容をファイルに出力する。
	 * </p>
	 * <p>
	 * 独自の変換を行いたい場合は、当メソッドをオーバーライドしてHTMLの変換ロジックを実装する。
	 * </p>
	 *
	 * @param elist
	 *            構文解析されたHTML
	 * @return elistの内容を変更した場合、true
	 * @see HtLexerConverter#setUseParser(boolean)
	 */
	public boolean convert(HtListElement elist) {
		boolean ret = false;

		ret |= convertLoop(elist);
		return ret;
	}

	protected boolean convertLoop(HtElement he) {
		if (he instanceof HtTokenElement) {
			HtTokenElement te = (HtTokenElement) he;
			Token t = te.getToken();
			if (t != null) {
				return convertToken(t, he);
			} else {
				return false;
			}
		}

		boolean ret = false;

		HtTagElement te = null;
		if (he instanceof HtTagElement) {
			te = (HtTagElement) he;
			Tag stag = te.getStartTag();
			if (stag != null) {
				ret |= convertTag(stag, he);
			}
		}

		HtListElement elist = (HtListElement) he;
		for (int i = 0; i < elist.size(); i++) {
			HtElement e = elist.get(i);
			if (e != null) {
				ret |= convertLoop(e);
			}
		}

		if (te != null) {
			Tag etag = te.getEndTag();
			if (etag != null) {
				ret |= convertTag(etag, he);
			}
		}

		return ret;
	}

	/**
	 * HTML変更.
	 * <p>
	 * {@link jp.hishidama.ant.taskdefs.HtLexerTask}によってHTMLが読み込まれた後に呼ばれる。<br>
	 * （HtHtmlParserによって構文解析されていない場合に当メソッドが呼ばれる）<br>
	 * 引数の内容を変更してtrueを返すと、その内容をファイルに出力する。
	 * </p>
	 * <p>
	 * 独自の変換を行いたい場合は、当メソッドをオーバーライドしてHTMLの変換ロジックを実装する。
	 * </p>
	 *
	 * @param tlist
	 *            読み込まれたHTML
	 * @return tlistの内容を変更した場合、true
	 * @see HtLexerConverter#setUseParser(boolean)
	 */
	public boolean convert(ListToken tlist) {
		boolean ret = false;
		for (Token t : tlist) {
			ret |= convertToken(t, null);
		}
		return ret;
	}

	protected boolean convertToken(Token t, HtElement he) {
		if (t instanceof Tag) {
			Tag tag = (Tag) t;
			return convertTag(tag, he);
		} else if (t instanceof TextToken) {
			TextToken text = (TextToken) t;
			return convertText(text, he);
		}
		return false;
	}

	/**
	 * タグ変換.
	 *
	 * @param tag
	 *            タグ
	 * @param he
	 *            タグの属している要素（解析されていない場合、null）
	 * @return 内容を変更した場合、true
	 */
	public boolean convertTag(Tag tag, HtElement he) {
		boolean ret = false;

		for (TagType i : tagList) {
			ret |= i.convert(tag, he);
		}

		return ret;
	}

	/**
	 * テキスト変換.
	 *
	 * @param text
	 *            テキスト
	 * @param he
	 *            テキストの属している要素（解析されていない場合、null）
	 * @return 内容を変更した場合、true
	 */
	public boolean convertText(TextToken text, HtElement he) {
		boolean ret = false;

		for (TextType i : textList) {
			ret |= i.convert(text, he);
		}

		return ret;
	}
}

class EmptyFileCondType extends FileCondType {

	EmptyFileCondType(Project project, Location location) {
		setProject(project);
		setLocation(location);
	}

	@Override
	public boolean isConvert() {
		return true;
	}
}