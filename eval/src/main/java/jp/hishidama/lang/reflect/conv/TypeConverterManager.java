package jp.hishidama.lang.reflect.conv;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 型変換オブジェクト管理クラス.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2010.02.16
 */
public class TypeConverterManager {

	/**
	 * 型変換オブジェクト取得.
	 *
	 * @param clazz
	 *            変換先の型
	 * @return 型変換オブジェクト
	 */
	public TypeConverter getConverter(Class<?> clazz) {
		if (clazz.isArray()) {
			return new ArrayConverter(clazz, this);
		}

		TypeConverter conv = findConverter(clazz);
		if (conv == null) {
			conv = getDefaultConverter(clazz);
		}
		return conv;
	}

	/**
	 * 登録されている型変換オブジェクトを返す。
	 *
	 * @param clazz
	 *            変換先の型
	 * @return 型変換オブジェクト（見つからなかった場合はnull）
	 */
	protected TypeConverter findConverter(Class<?> clazz) {
		Map<Class<?>, TypeConverter> map = getConverterMap();

		if (map.containsKey(clazz)) {
			return map.get(clazz);
		}
		if (clazz == Object.class) {
			return ObjectConverter.INSTANCE;
		}

		// 親クラスを探す
		for (Entry<Class<?>, TypeConverter> entry : map.entrySet()) {
			Class<?> c = entry.getKey();
			if (c.isAssignableFrom(clazz)) {
				TypeConverter conv = entry.getValue();
				map.put(clazz, conv);
				return conv;
			}
		}

		map.put(clazz, null);
		return null;
	}

	/**
	 * 登録されていないクラスの場合の処理を行う。
	 *
	 * @param clazz
	 *            変換先の型
	 * @return 型変換オブジェクト
	 */
	protected TypeConverter getDefaultConverter(Class<?> clazz) {
		return new TypeCheckConverter(clazz);
	}

	protected Map<Class<?>, TypeConverter> MAP = null;

	/**
	 * 型変換オブジェクトマップ取得.
	 * <p>
	 * 当インスタンス内唯一のマップを返す。
	 * </p>
	 *
	 * @return マップ
	 */
	public Map<Class<?>, TypeConverter> getConverterMap() {
		if (MAP == null) {
			Map<Class<?>, TypeConverter> map = new HashMap<Class<?>, TypeConverter>(
					32);
			initConverterMap(map);
			MAP = map;
		}
		return MAP;
	}

	/**
	 * 初期化の為に一度だけ呼ばれる。
	 *
	 * @param map
	 *            初期化対象
	 */
	protected void initConverterMap(Map<Class<?>, TypeConverter> map) {
		map.put(boolean.class, BooleanConverter.INSTANCE);
		map.put(Boolean.class, BooleanConverter.INSTANCE);

		map.put(byte.class, ByteConverter.INSTANCE);
		map.put(Byte.class, ByteConverter.INSTANCE);

		map.put(char.class, CharacterConverter.INSTANCE);
		map.put(Character.class, CharacterConverter.INSTANCE);

		map.put(double.class, DoubleConverter.INSTANCE);
		map.put(Double.class, DoubleConverter.INSTANCE);

		map.put(File.class, FileConverter.INSTANCE);

		map.put(float.class, FloatConverter.INSTANCE);
		map.put(Float.class, FloatConverter.INSTANCE);

		map.put(int.class, IntegerConverter.INSTANCE);
		map.put(Integer.class, IntegerConverter.INSTANCE);

		map.put(long.class, LongConverter.INSTANCE);
		map.put(Long.class, LongConverter.INSTANCE);

		map.put(Map.class, MapConverter.INSTANCE);
		// map.put(Object.class, ObjectConverter.INSTANCE);

		map.put(short.class, ShortConverter.INSTANCE);
		map.put(Short.class, ShortConverter.INSTANCE);

		map.put(String.class, StringConverter.INSTANCE);
		map.put(URI.class, URIConverter.INSTANCE);
	}
}
