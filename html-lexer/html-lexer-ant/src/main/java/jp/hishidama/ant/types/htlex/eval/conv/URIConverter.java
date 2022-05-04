package jp.hishidama.ant.types.htlex.eval.conv;

import java.io.File;

import jp.hishidama.lang.reflect.conv.TypeConverter;
import jp.hishidama.net.URI;

public class URIConverter extends TypeConverter {

	public static final URIConverter INSTANCE = new URIConverter();

	@Override
	public int match(Object object) {
		if (object == null) {
			return MATCH_NULL;
		}
		if (object instanceof URI) {
			return MATCH_EQUALS;
		}
		if (object instanceof File) {
			return MATCH_EQUALS / 2;
		}
		if (object instanceof java.net.URI) {
			return MATCH_EQUALS - 1;
		}
		return MATCH_STRING;
	}

	@Override
	public URI convert(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof URI) {
			return (URI) object;
		}
		if (object instanceof File) {
			File f = (File) object;
			return URI.valueOf(f);
		}
		if (object instanceof java.net.URI) {
			java.net.URI urj = (java.net.URI) object;
			return URI.valueOf(urj);
		}
		return new URI(object.toString());
	}
}
