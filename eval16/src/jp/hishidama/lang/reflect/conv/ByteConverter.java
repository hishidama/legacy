package jp.hishidama.lang.reflect.conv;

/**
 * Byte•ÏŠ·ƒNƒ‰ƒX.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >‚Ð‚µ‚¾‚Ü</a>
 * @since 2010.02.16
 */
public class ByteConverter extends TypeConverter {

	public static final ByteConverter INSTANCE = new ByteConverter();

	@Override
	public int match(Object obj) {
		if (obj == null) {
			return MATCH_NULL;
		}

		if (obj instanceof Byte) {
			return MATCH_EQUALS;
		}

		if (obj instanceof Double) {
			return MATCH_EQUALS / 2 + 5;
		}
		if (obj instanceof Float) {
			return MATCH_EQUALS / 2 + 4;
		}
		if (obj instanceof Long) {
			return MATCH_EQUALS / 2 + 3;
		}
		if (obj instanceof Integer) {
			return MATCH_EQUALS / 2 + 2;
		}
		if (obj instanceof Short) {
			return MATCH_EQUALS / 2 + 1;
		}

		if (obj instanceof Number) {
			return MATCH_EQUALS / 2;
		}
		return MATCH_STRING;
	}

	@Override
	public Byte convert(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof Byte) {
			return (Byte) object;
		}
		if (object instanceof Number) {
			return ((Number) object).byteValue();
		}
		return Byte.valueOf(object.toString());
	}
}
