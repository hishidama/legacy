package jp.hishidama.ant.types.htlex;

import java.io.IOException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.EnumeratedAttribute;

import jp.hishidama.html.lexer.rule.HtLexer;
import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.SkipToken;
import jp.hishidama.html.lexer.token.Tag;

/**
 * HtHtmlLexerタグ属性追加タイプ.
 *<p>
 * HTMLファイル内のタグの属性の追加を行うデータタイプ。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2009.01.23
 * @version 2010.01.31
 */
public class AttrAddType extends AttrNewType {

	/**
	 * 重複属性名処理方法.
	 * <p>
	 * 変換によって属性名が重複した場合の処理方法を指定する。
	 * </p>
	 *
	 * @version 2009.02.01
	 */
	public static class DupEnum extends EnumeratedAttribute {

		/** {@value}：そのまま強制的に変更する（同じ属性名の属性が出来る） */
		public static final String FORCE = "force";
		/** {@value}：変更しない */
		public static final String SKIP = "skip";
		/** {@value}：既存の属性を更新する */
		public static final String UPDATE = "update";
		/** {@value}：エラーとする */
		public static final String ERROR = "error";

		@Override
		public String[] getValues() {
			return new String[] { FORCE, SKIP, UPDATE, ERROR };
		}

		public DupEnum() {
		}

		private DupEnum(String s) {
			setValue(s);
		}
	}

	protected String preSkip = " ";

	/**
	 * 空白設定.
	 * <p>
	 * 追加する属性の前に挿入する空白を設定する。デフォルトは半角スペース1個。
	 * </p>
	 *
	 * @param s
	 *            空白
	 */
	public void setPreSkip(String s) {
		preSkip = s;
	}

	protected DupEnum dup = new DupEnum(DupEnum.UPDATE);

	/**
	 * 名前重複時動作設定.
	 *
	 * @param d
	 *            重複属性名処理方法
	 */
	public void setDup(DupEnum d) {
		dup = d;
	}

	@Override
	public void validate() throws BuildException {
		super.validate();
		if (newName == null || newName.isEmpty()) {
			throw new BuildException("newName attribute must be set.",
					getLocation());
		}
	}

	@Override
	public boolean convert(Tag tag) {
		AttributeToken old = tag.getAttribute(newName);
		if (old != null) {
			switch (dup.getIndex()) {
			case 0: // force
				return add(tag);
			case 1:// skip
				return false;
			default: // update
				return update(tag, old);
			case 3:// error
				throw new BuildException("duplicate attribute (add): "
						+ htmlConverter.getReadFile().getAbsolutePath() + ":"
						+ old.getLine() + " " + newName, getLocation());
			}
		}

		return add(tag);
	}

	protected boolean add(Tag tag) {
		AttributeToken a = new AttributeToken();
		updateName(tag, a);
		updateLet(tag, a);
		updateValue(tag, a);
		updateQuote(tag, a);

		HtLexer lexer = htmlConverter.getLexer(preSkip);
		SkipToken st;
		try {
			st = lexer.parseSkip();
		} catch (IOException e) {
			throw new BuildException(e, getLocation());
		} finally {
			try {
				lexer.close();
			} catch (IOException e) {
			}
		}

		tag.addSkip(st);
		tag.addAttribute(a);

		htmlConverter.logConvert("add", tag.getLine(), a);
		return true;
	}
}
