package jp.hishidama.ant.types.htlex.eval.conv;

import java.io.File;
import java.util.Map;

import jp.hishidama.lang.reflect.conv.TypeConverter;
import jp.hishidama.lang.reflect.conv.TypeConverterManager;
import jp.hishidama.net.URI;

public class HtLexerConverterManager extends TypeConverterManager {

	@Override
	protected void initConverterMap(Map<Class<?>, TypeConverter> map) {
		super.initConverterMap(map);

		map.put(URI.class, URIConverter.INSTANCE);
		map.put(File.class, FileConverter.INSTANCE);
		map.put(boolean.class, BooleanConverter.INSTANCE);
		map.put(Boolean.class, BooleanConverter.INSTANCE);
	}
}
