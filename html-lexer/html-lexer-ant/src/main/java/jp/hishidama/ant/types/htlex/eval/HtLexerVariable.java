package jp.hishidama.ant.types.htlex.eval;

import java.util.List;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.PropertyHelper;

import jp.hishidama.ant.types.htlex.eval.FieldGetter.Getter;
import jp.hishidama.eval.exp.AbstractExpression;
import jp.hishidama.eval.var.DefaultVariable;
import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.Tag;

/**
 * HtHtmlLexerタグ属性演算の変数クラス.
 * <p>
 * htlexの演算式で変数を記述した場合、プロパティーとして扱う。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2010.01.31
 */
public class HtLexerVariable extends DefaultVariable {

	protected Project project;
	protected PropertyHelper helper;

	public HtLexerVariable(Project project) {
		this.project = project;
		helper = PropertyHelper.getPropertyHelper(project);
	}

	protected Object getProperty(Object name) {
		if (name instanceof String) {
			String key = (String) name;
			return helper.getProperty(key);
		}
		throw new UnsupportedOperationException("getProperty "
				+ name.getClass() + ", name=" + name);
	}

	protected void setProperty(Object name, Object obj) {
		if (name instanceof String) {
			String key = (String) name;
			helper.setProperty(key, obj, false);
			return;
		}
		throw new UnsupportedOperationException("setProperty "
				+ name.getClass() + ", name= " + name + ", obj=" + obj);
	}

	@Override
	public void setValue(Object name, Object value) {
		if (value == null) {
			value = "";
		}
		setProperty(name, value);
	}

	@Override
	public Object getValue(Object name) {
		Object value = getProperty(name);
		if (value != null) {
			return value;
		}
		return new StringWork((String) name);
	}

	@Override
	public Object getArrayValue(Object array, String arrayName, Object index,
			AbstractExpression exp) {
		if (array == null) {
			return null;
		}
		if (array instanceof List) {
			if (index == null) {
				return null;
			}
			List<?> list = (List<?>) array;
			return getListValue(list, index.toString());
		}
		return super.getArrayValue(array, arrayName, index, exp);
	}

	@Override
	public void setArrayValue(Object array, String arrayName, Object index,
			Object value, AbstractExpression exp) {
		if (array == null) {
			return;
		}
		if (array instanceof Tag || array instanceof AttributeToken) {
			throw new UnsupportedOperationException("set " + array.getClass()
					+ ", index=" + index);
		}
		super.setArrayValue(array, arrayName, index, value, exp);
	}

	@Override
	public Object getFieldValue(Object obj, String objName, String field,
			AbstractExpression exp) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof List) {
			List<?> list = (List<?>) obj;
			return getListValue(list, field);
		}
		Getter getter = FieldGetter.getInstance().get(field);
		if (getter != null) {
			return getter.get(obj);
		}
		if (obj instanceof String || obj instanceof StringWork) {
			String name = objName + "." + field;
			return getValue(name);
		}
		return null;
	}

	protected Object getListValue(List<?> list, String name) {
		for (Object o : list) {
			if (o instanceof AttributeToken) {
				AttributeToken a = (AttributeToken) o;
				if (name.equalsIgnoreCase(a.getName())) {
					return a;
				}
			}
		}
		return null;
	}

	@Override
	public void setFieldValue(Object obj, String objName, String field,
			Object value, AbstractExpression exp) {
		if (obj == null) {
			return;
		}
		if (obj instanceof String) {
			String key = obj + "." + field;
			setValue(key, value);
			return;
		}
		throw new UnsupportedOperationException("set " + obj.getClass()
				+ ", field=" + field);
	}
}

class StringWork {
	String value;

	StringWork(String s) {
		value = s;
	}

	@Override
	public String toString() {
		return value;
	}
}
