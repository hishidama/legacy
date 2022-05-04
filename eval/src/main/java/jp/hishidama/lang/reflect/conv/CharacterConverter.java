package jp.hishidama.lang.reflect.conv;

/**
 * Character変換クラス.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2010.02.16
 */
public class CharacterConverter extends TypeConverter {

	public static final CharacterConverter INSTANCE = new CharacterConverter();

	@Override
	public int match(Object obj) {
		if (obj == null) {
			return MATCH_NULL;
		}

		if (obj instanceof Character) {
			return MATCH_EQUALS;
		}
		if (obj instanceof Number) {
			return MATCH_EQUALS / 2;
		}
		return MATCH_STRING;
	}

	@Override
	public Character convert(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof Character) {
			return (Character) object;
		}
		if (object instanceof Number) {
			return (char) ((Number) object).intValue();
		}
		return object.toString().charAt(0);
	}
}
