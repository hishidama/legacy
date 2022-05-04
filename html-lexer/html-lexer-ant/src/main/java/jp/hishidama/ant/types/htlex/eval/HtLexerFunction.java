package jp.hishidama.ant.types.htlex.eval;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

import jp.hishidama.ant.types.htlex.eval.FieldGetter.Getter;
import jp.hishidama.ant.types.htlex.eval.conv.HtLexerConverterManager;
import jp.hishidama.ant.types.htlex.eval.func.*;
import jp.hishidama.eval.EvalThroughException;
import jp.hishidama.eval.func.Function;
import jp.hishidama.lang.reflect.InvokeUtil;
import jp.hishidama.lang.reflect.Invoker;
import jp.hishidama.net.URI;

/**
 * HtHtmlLexerタグ属性演算の関数クラス.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2010.01.31
 */
public class HtLexerFunction implements Function {

	protected Project project;

	public HtLexerFunction(Project project) {
		this.project = project;
	}

	@Override
	public Object eval(String name, Object[] args) {
		initInvoker();

		Object obj = null;

		Invoker v = invoker.getStaticInvoker(name, args);
		if (v == null) {
			obj = args[0];
			Object[] as = new Object[args.length - 1];
			System.arraycopy(args, 1, as, 0, as.length);
			args = as;
			v = invoker.getInstanceInvoker(name, args);
		}

		return invoke(v, obj, name, args);
	}

	@Override
	public Object eval(Object object, String name, Object[] args) {
		if (args.length == 0) {
			Getter getter = FieldGetter.getInstance().get(name);
			if (getter != null) {
				return getter.get(object);
			}
		}

		initInvoker();

		Invoker v = invoker.getStaticInvoker(name, args);
		if (v == null) {
			if (object == null) {
				return null;
			}
			v = invoker.getInstanceInvoker(name, args);
		}

		return invoke(v, object, name, args);
	}

	protected Object invoke(Invoker v, Object object, String name, Object[] args) {
		if (v == null) {
			throw new UnsupportedOperationException(name);
		}
		try {
			return v.invoke(object, args);
		} catch (Exception e) {
			throw new EvalThroughException(new BuildException("exception from "
					+ name + "()", e));
		}
	}

	protected static InvokeUtil invoker;

	protected static void initInvoker() {
		if (invoker != null) {
			return;
		}
		HtLexerConverterManager manager = new HtLexerConverterManager();
		invoker = new InvokeUtil(manager);

		invoker.addMethods(String.class, "String_");
		invoker.addMethods(URI.class, "URI_");
		invoker.addMethods(URLEncoder.class, "URLEncoder_");
		invoker.addMethods(URLDecoder.class, "URLDecoder_");
		invoker.addMethods(Map.class, "Map_");
		invoker.addInvoker(new Map_getKeyForValue(manager));
		invoker.addInvoker(new String_containsZenkaku(manager));
	}
}
