package jp.hishidama.ant.types.htlex.eval.conv;

import java.io.File;
import java.net.URISyntaxException;

import jp.hishidama.lang.reflect.conv.TypeConverter;
import jp.hishidama.net.URI;

public class FileConverter extends TypeConverter {

	public static final FileConverter INSTANCE = new FileConverter();

	@Override
	public int match(Object object) {
		if (object == null) {
			return MATCH_NULL;
		}
		if (object instanceof File) {
			return MATCH_EQUALS;
		}
		if (object instanceof URI) {
			return MATCH_EQUALS - 2;
		}
		return MATCH_STRING;
	}

	@Override
	public File convert(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof File) {
			return (File) object;
		}
		if (object instanceof URI) {
			URI uri = (URI) object;
			File f;
			try {
				f = new File(uri.toURI());
			} catch (URISyntaxException e) {
				return new File(uri.toString());
			}
			return f;
		}
		if (object instanceof java.net.URI) {
			java.net.URI urj = (java.net.URI) object;
			File f = new File(urj);
			return f;
		}
		return new File(object.toString());
	}
}
