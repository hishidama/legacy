package jp.hishidama.eval.var;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import jp.hishidama.eval.EvalException;
import jp.hishidama.eval.exp.AbstractExpression;

/**
 * デフォルト変数管理クラス.
 * <ul>
 * <li>{@link #getValue(Object)}・{@link #setValue(Object, Object)}では、特に何もしない。</li>
 * <li>{@link #getArrayValue(Object, String, Object, AbstractExpression)}・
 * {@link #setArrayValue(Object, String, Object, Object, AbstractExpression)}
 * では、オブジェクトが配列・リスト・マップに対応している。それ以外の場合は例外を発生させる。</li>
 * <li>{@link #getFieldValue(Object, String, String, AbstractExpression)}・
 * {@link #setFieldValue(Object, String, String, Object, AbstractExpression)}
 * では、リフレクションを用いてオブジェクトにアクセスする。存在しないフィールドの場合は例外が発生する。</li>
 * </ul>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since eval16
 */
public class DefaultVariable implements Variable {

	@Override
	public Object getValue(Object name) {
		// 当クラスでは常にnullを返す
		return null;
	}

	@Override
	public void setValue(Object name, Object value) {
		// 当クラスでは何もしない
	}

	@Override
	public Object getArrayValue(Object array, String arrayName, Object index,
			AbstractExpression exp) {
		if (array == null) {
			throw new EvalException(EvalException.EXP_NOT_DEF_OBJ, arrayName,
					exp, null);
		}
		if (array.getClass().isArray()) {
			int i = getInt(index);
			return Array.get(array, i);
		}
		if (array instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) array;
			return map.get(index);
		}
		if (array instanceof List) {
			int i = getInt(index);
			List<?> list = (List<?>) array;
			return list.get(i);
		}
		throw new UnsupportedOperationException(array.getClass().getName());
	}

	@Override
	public void setArrayValue(Object array, String arrayName, Object index,
			Object value, AbstractExpression exp) {
		if (array == null) {
			throw new EvalException(EvalException.EXP_NOT_DEF_OBJ, arrayName,
					exp, null);
		}
		if (array.getClass().isArray()) {
			int i = getInt(index);
			Array.set(array, i, value);
			return;
		}
		if (array instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<Object, Object> map = (Map<Object, Object>) array;
			map.put(index, value);
			return;
		}
		if (array instanceof List) {
			int i = getInt(index);
			@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>) array;
			list.set(i, value);
			return;
		}
		throw new UnsupportedOperationException(array.getClass().getName());
	}

	/**
	 * 数値変換.
	 *
	 * @param value
	 *            値
	 * @return 数値
	 * @throws NumberFormatException
	 */
	public int getInt(Object value) {
		if (value == null) {
			throw new NumberFormatException("value=" + value);
		}
		if (value instanceof Number) {
			return ((Number) value).intValue();
		}
		String str = value.toString();
		return Integer.parseInt(str);
	}

	@Override
	public Object getFieldValue(Object obj, String objName, String field,
			AbstractExpression exp) {
		if (obj == null) {
			throw new EvalException(EvalException.EXP_NOT_DEF_OBJ, objName,
					exp, null);
		}
		try {
			Class<?> c = obj.getClass();
			Field f = c.getField(field);
			return f.get(obj);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			// System.out.println("MapVariable#getObject() " + obj + "." +
			// field);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setFieldValue(Object obj, String objName, String field,
			Object value, AbstractExpression exp) {
		if (obj == null) {
			throw new EvalException(EvalException.EXP_NOT_DEF_OBJ, objName,
					exp, null);
		}
		try {
			Class<?> c = obj.getClass();
			Field f = c.getField(field);
			f.set(obj, value);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
