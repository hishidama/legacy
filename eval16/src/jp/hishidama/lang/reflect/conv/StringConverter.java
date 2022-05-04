package jp.hishidama.lang.reflect.conv;

/**
 * String•ÏŠ·ƒNƒ‰ƒX.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >‚Ð‚µ‚¾‚Ü</a>
 * @since 2010.02.16
 */
public class StringConverter extends TypeConverter {

	public static final StringConverter INSTANCE = new StringConverter();

	@Override
	public int match(Object obj) {
		if (obj == null) {
			return MATCH_NULL;
		}

		if (obj instanceof String) {
			return MATCH_EQUALS;
		}
		return MATCH_STRING;
	}

	@Override
	public String convert(Object object) {
		if (object == null) {
			return null;
		}
		return object.toString();
	}
}
