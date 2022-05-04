package jp.hishidama.lang.reflect.conv;

/**
 * Short•ÏŠ·ƒNƒ‰ƒX.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >‚Ð‚µ‚¾‚Ü</a>
 * @since 2010.02.16
 */
public class ShortConverter extends TypeConverter {

	public static final ShortConverter INSTANCE = new ShortConverter();

	@Override
	public int match(Object obj) {
		if (obj == null) {
			return MATCH_NULL;
		}

		if (obj instanceof Short) {
			return MATCH_EQUALS;
		}

		if (obj instanceof Double) {
			return MATCH_EQUALS / 2 + 4;
		}
		if (obj instanceof Float) {
			return MATCH_EQUALS / 2 + 3;
		}
		if (obj instanceof Long) {
			return MATCH_EQUALS / 2 + 2;
		}
		if (obj instanceof Integer) {
			return MATCH_EQUALS / 2 + 1;
		}

		if (obj instanceof Byte) {
			return MATCH_EQUALS - 1;
		}

		if (obj instanceof Number) {
			return MATCH_EQUALS / 2;
		}
		return MATCH_STRING;
	}

	@Override
	public Short convert(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof Short) {
			return (Short) object;
		}
		if (object instanceof Number) {
			return ((Number) object).shortValue();
		}
		return Short.valueOf(object.toString());
	}
}
