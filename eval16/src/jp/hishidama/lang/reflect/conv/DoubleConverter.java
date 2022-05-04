package jp.hishidama.lang.reflect.conv;

/**
 * Double•ÏŠ·ƒNƒ‰ƒX.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >‚Ð‚µ‚¾‚Ü</a>
 * @since 2010.02.16
 */
public class DoubleConverter extends TypeConverter {

	public static final DoubleConverter INSTANCE = new DoubleConverter();

	@Override
	public int match(Object obj) {
		if (obj == null) {
			return MATCH_NULL;
		}

		if (obj instanceof Double) {
			return MATCH_EQUALS;
		}

		if (obj instanceof Float) {
			return MATCH_EQUALS - 1;
		}
		if (obj instanceof Long) {
			return MATCH_EQUALS - 2;
		}
		if (obj instanceof Integer) {
			return MATCH_EQUALS - 3;
		}
		if (obj instanceof Short) {
			return MATCH_EQUALS - 4;
		}
		if (obj instanceof Byte) {
			return MATCH_EQUALS - 5;
		}

		if (obj instanceof Number) {
			return MATCH_EQUALS / 2;
		}
		return MATCH_STRING;
	}

	@Override
	public Double convert(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof Double) {
			return (Double) object;
		}
		if (object instanceof Number) {
			return ((Number) object).doubleValue();
		}
		return Double.valueOf(object.toString());
	}
}
