package jp.hishidama.ant.types.htlex;

import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.Tag;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.EnumeratedAttribute;

/**
 * HtHtmlLexerタグ属性置換タイプ.
 *<p>
 * HTMLファイル内のタグの属性の置換を行うデータタイプ。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2009.01.23
 */
public class AttrRepType extends AttrNewType {

	/**
	 * 重複属性名処理方法.
	 * <p>
	 * 変換によって属性名が重複した場合の処理方法を指定する。
	 * </p>
	 *
	 * @version 2009.02.01
	 */
	public static class DupEnum extends EnumeratedAttribute {

		/** {@value}：そのまま強制的に追加する（同じ属性名の属性が出来る） */
		public static final String FORCE = "force";
		/** {@value}：変更しない */
		public static final String SKIP = "skip";
		/** {@value}：既存の属性を削除し、新しい属性を生かす */
		public static final String UPDATE = "update";
		/** {@value}：エラーとする */
		public static final String ERROR = "error";
		/** {@value}：変換しようとしていた属性を削除する */
		public static final String DELETE = "delete";

		@Override
		public String[] getValues() {
			return new String[] { FORCE, SKIP, UPDATE, ERROR, DELETE };
		}

		public DupEnum() {
		}

		private DupEnum(String s) {
			setValue(s);
		}
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
	public boolean convert(Tag tag, AttributeToken a) throws BuildException {
		if (newName != null) {
			AttributeToken old = tag.getAttribute(newName);
			if (old != null && old != a) {
				switch (dup.getIndex()) {
				case 0:// force
					break;
				case 1:// skip
					return false;
				default:// update
					return delete(tag, old) | update(tag, a);
					// case ?:
					// return delete(tag, a) | update(tag, old);
				case 3: // error
					throw new BuildException("duplicate attribute (replace): "
							+ htmlConverter.getReadFile().getAbsolutePath()
							+ ":" + +old.getLine() + " " + newName,
							getLocation());
				case 4:// delete
					return delete(tag, a);
				}
			}
		}

		return update(tag, a);
	}
}
