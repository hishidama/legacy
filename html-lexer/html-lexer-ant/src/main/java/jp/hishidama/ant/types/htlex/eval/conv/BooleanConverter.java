package jp.hishidama.ant.types.htlex.eval.conv;

import jp.hishidama.lang.reflect.conv.TypeConverter;

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
			return Boolean.FALSE;
		}
		if (object instanceof Boolean) {
			return (Boolean) object;
		}
		if (object instanceof Number) {
			return ((Number) object).intValue() != 0;
		}

		String s = object.toString();
		if (s == null || s.isEmpty()) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}
}
