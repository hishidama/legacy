package jp.hishidama.lang.reflect.conv;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * URI•ÏŠ·ƒNƒ‰ƒX.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >‚Ð‚µ‚¾‚Ü</a>
 * @since 2010.02.16
 */
public class URIConverter extends TypeConverter {

	public static final URIConverter INSTANCE = new URIConverter();

	@Override
	public int match(Object obj) {
		if (obj == null) {
			return MATCH_NULL;
		}

		if (obj instanceof URI) {
			return MATCH_EQUALS;
		}
		if (obj instanceof File) {
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
			return f.toURI();
		}
		try {
			return new URI(object.toString());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
