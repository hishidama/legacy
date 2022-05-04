package jp.hishidama.ant.types.htlex.eval.func;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import jp.hishidama.ant.types.htlex.eval.conv.HtLexerConverterManager;
import jp.hishidama.lang.reflect.Invoker;
import jp.hishidama.lang.reflect.conv.TypeConverterManager;

/**
 * HtHtmlLexerタグ属性演算の関数：Mapの値からキーを取得する.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2010.01.31
 */
public class Map_getKeyForValue extends Invoker {

	public Map_getKeyForValue(HtLexerConverterManager manager) {
		super("Map_getKeyForValue", Map.class, null, manager);
	}

	@Override
	protected void initArgsConverter(TypeConverterManager manager) {
		Class<?>[] types = { Object.class };
		convs = getArgsConverter(types, manager);
	}

	@Override
	protected boolean isStatic() {
		return false;
	}

	@SuppressWarnings("rawtypes")
	protected Map<Class<? extends Map>, Method> mmap = new HashMap<Class<? extends Map>, Method>();

	@Override
	public Object invoke(Object obj, Object... args) throws Exception {
		checkArgs(args);
		Map<?, ?> map = objectConvert(obj);
		if (map == null) {
			return null;
		}
		@SuppressWarnings("rawtypes")
		Class<? extends Map> c = map.getClass();
		Method m;
		if (!mmap.containsKey(c)) {
			try {
				m = c.getMethod("getKeyForValue", Object.class);
			} catch (Exception e) {
				m = null;
			}
			mmap.put(c, m);
		} else {
			m = mmap.get(c);
		}
		if (m != null) {
			return m.invoke(map, args[0]);
		} else {
			for (Entry<?, ?> entry : map.entrySet()) {
				Object value = entry.getValue();
				if (value == null) {
					if (args[0] == null) {
						return entry.getKey();
					}
				} else {
					if (value.equals(args[0])) {
						return entry.getKey();
					}
				}
			}
			return null;
		}
	}
}