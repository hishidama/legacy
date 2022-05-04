package jp.hishidama.ant.types.htlex.eval.func;

import jp.hishidama.ant.types.htlex.eval.conv.HtLexerConverterManager;
import jp.hishidama.lang.reflect.Invoker;
import jp.hishidama.lang.reflect.conv.TypeConverterManager;

/**
 * HtHtmlLexerタグ属性演算の関数：Stringで全角文字を含むかどうか判定する.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2010.02.17
 */
public class String_containsZenkaku extends Invoker {

	public String_containsZenkaku(HtLexerConverterManager manager) {
		super("String_containsZenkaku", String.class, null, manager);
	}

	@Override
	protected void initArgsConverter(TypeConverterManager manager) {
		Class<?>[] types = {};
		convs = getArgsConverter(types, manager);
	}

	@Override
	protected boolean isStatic() {
		return false;
	}

	@Override
	public Boolean invoke(Object obj, Object... args) throws Exception {
		checkArgs(args);
		String str = objectConvert(obj);
		if (str == null) {
			return Boolean.FALSE;
		}
		return str.getBytes("MS932").length != str.length();
	}
}
