package jp.hishidama.lang.reflect.conv;

/**
 * Float変換クラス.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2010.02.16
 */
public class FloatConverter extends TypeConverter {

	public static final FloatConverter INSTANCE = new FloatConverter();

	@Override
	public int match(Object obj) {
		if (obj == null) {
			return MATCH_NULL;
		}

		if (obj instanceof Float) {
			return MATCH_EQUALS;
		}

		if (obj instanceof Double) {
			return MATCH_EQUALS / 2 + 1;
		}

		if (obj instanceof Long) {
			return MATCH_EQUALS - 1;
		}
		if (obj instanceof Integer) {
			return MATCH_EQUALS - 2;
		}
		if (obj instanceof Short) {
			return MATCH_EQUALS - 3;
		}
		if (obj instanceof Byte) {
			return MATCH_EQUALS - 4;
		}

		if (obj instanceof Number) {
			return MATCH_EQUALS / 2;
		}
		return MATCH_STRING;
	}

	@Override
	public Float convert(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof Float) {
			return (Float) object;
		}
		if (object instanceof Number) {
			return ((Number) object).floatValue();
		}
		return Float.valueOf(object.toString());
	}
}
