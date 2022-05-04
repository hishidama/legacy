package jp.hishidama.lang.reflect.conv;

/**
 * Boolean変換クラス.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2010.02.16
 */
public class BooleanConverter extends TypeConverter {

	public static final BooleanConverter INSTANCE = new BooleanConverter();

	@Override
	public int match(Object obj) {
		if (obj == null) {
			return MATCH_NULL;
		}

		if (obj instanceof Boolean) {
			return MATCH_EQUALS;
		}
		if (obj instanceof Number) {
			return MATCH_EQUALS / 4;
		}
		return MATCH_STRING;
	}

	@Override
	public Boolean convert(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof Boolean) {
			return (Boolean) object;
		}
		if (object instanceof Number) {
			return ((Number) object).intValue() != 0;
		}
		return Boolean.valueOf(object.toString());
	}
}
