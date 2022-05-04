package jp.hishidama.lang.reflect.conv;

import java.io.File;
import java.net.URI;

/**
 * File•ÏŠ·ƒNƒ‰ƒX.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >‚Ð‚µ‚¾‚Ü</a>
 * @since 2010.02.16
 */
public class FileConverter extends TypeConverter {

	public static final FileConverter INSTANCE = new FileConverter();

	@Override
	public int match(Object obj) {
		if (obj == null) {
			return MATCH_NULL;
		}

		if (obj instanceof File) {
			return MATCH_EQUALS;
		}
		if (obj instanceof URI) {
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
			File f = new File(uri);
			return f;
		}
		return new File(object.toString());
	}
}
