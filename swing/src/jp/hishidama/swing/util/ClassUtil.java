package jp.hishidama.swing.util;

import java.util.HashMap;
import java.util.Map;

/**
 * �N���X���[�e�B���e�B�[.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/index.html"
 *         >�Ђ�����</a>
 * @since 2009.09.28
 */
public class ClassUtil {

	/**
	 * �C���X�^���X�ϊ�.
	 * <p>
	 * ��������w�肳�ꂽ�N���X�̃C���X�^���X�ɕϊ�����B
	 * </p>
	 *
	 * @param str
	 *            ������
	 * @param cls
	 *            �N���X
	 * @return �C���X�^���X
	 */
	public static Object toObject(String str, Class<?> cls) {
		if (map == null) {
			HashMap<Class<?>, ToObject> m = new HashMap<Class<?>, ToObject>();
			m.put(String.class, new StringToObject());
			m.put(int.class, new IntegerToObject());
			m.put(boolean.class, new BooleanToObject());
			map = m;
		}

		ToObject to = map.get(cls);
		if (to != null) {
			return to.toObject(str);
		} else {
			throw new UnsupportedOperationException("class=" + cls);
		}
	}

	protected static Map<Class<?>, ToObject> map = null;

	static interface ToObject {
		Object toObject(String str);
	}

	static class StringToObject implements ToObject {
		@Override
		public Object toObject(String str) {
			return str;
		}
	}

	static class IntegerToObject implements ToObject {
		@Override
		public Object toObject(String str) {
			try {
				return Integer.valueOf(str);
			} catch (Exception e) {
				return null;
			}
		}
	}

	static class BooleanToObject implements ToObject {
		@Override
		public Object toObject(String str) {
			try {
				if (Double.parseDouble(str) != 0) {
					return Boolean.TRUE;
				} else {
					return Boolean.FALSE;
				}
			} catch (Exception e) {
				return Boolean.valueOf(str);
			}
		}
	}
}
