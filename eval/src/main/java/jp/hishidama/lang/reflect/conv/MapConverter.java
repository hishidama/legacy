package jp.hishidama.lang.reflect.conv;

import java.util.Map;

/**
 * Map変換クラス.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2010.02.16
 */
public class MapConverter extends TypeConverter {

	public static final MapConverter INSTANCE = new MapConverter();

	@Override
	public int match(Object obj) {
		if (obj == null) {
			return MATCH_NULL;
		}

		if (obj instanceof Map) {
			return MATCH_EQUALS;
		}
		return UNMATCH;
	}

	@Override
	public Map<?, ?> convert(Object object) {
		if (object == null) {
			return null;
		}
		return (Map<?, ?>) object; // Mapでなかったら例外発生
	}
}
