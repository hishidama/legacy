package jp.hishidama.lang.reflect.conv;

/**
 * 汎用型変換クラス.
 * <p>
 * 指定された型にキャストする。<br>
 * （したがって、型が一致しないオブジェクトの場合はClassCastExceptionが発生する）
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2010.02.16
 */
public class TypeCheckConverter extends TypeConverter {

	protected Class<?> clazz;

	public TypeCheckConverter(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	public int match(Object obj) {
		if (obj == null) {
			return MATCH_NULL;
		}

		if (clazz.isInstance(obj)) {
			return MATCH_EQUALS;
		}
		return UNMATCH;
	}

	@Override
	public Object convert(Object object) {
		if (object == null) {
			return null;
		}
		if (!clazz.isInstance(object)) {
			throw new ClassCastException(object.getClass().getName()
					+ " cannot be cast to " + clazz.getName());
		}
		return object;
	}
}
