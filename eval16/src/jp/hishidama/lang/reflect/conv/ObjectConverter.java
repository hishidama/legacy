package jp.hishidama.lang.reflect.conv;

/**
 * Object•ÏŠ·ƒNƒ‰ƒX.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >‚Ð‚µ‚¾‚Ü</a>
 * @since 2010.02.16
 */
public class ObjectConverter extends TypeConverter {

	public static final ObjectConverter INSTANCE = new ObjectConverter();

	@Override
	public int match(Object obj) {
		return MATCH_OBJECT;
	}

	@Override
	public Object convert(Object object) {
		return object;
	}
}
