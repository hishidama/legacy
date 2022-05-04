package jp.hishidama.lang.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import jp.hishidama.lang.IllegalArgumentLengthException;
import jp.hishidama.lang.reflect.conv.TypeConverter;
import jp.hishidama.lang.reflect.conv.TypeConverterManager;

/**
 * メソッド呼び出しクラス.
 * <p>
 * 引数の型を当該メソッド用に変換してメソッドを呼び出す。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2010.02.16
 */
public class Invoker {

	protected String name;

	protected Method method;

	protected TypeConverter objConv;

	protected TypeConverter[] convs;

	/**
	 * コンストラクター.
	 *
	 * @param name
	 *            名前
	 * @param clazz
	 *            呼び出し対象クラス
	 * @param method
	 *            呼び出し対象メソッド
	 * @param manager
	 *            型変換管理クラス
	 */
	public Invoker(String name, Class<?> clazz, Method method,
			TypeConverterManager manager) {
		this.name = name;
		this.method = method;

		initObjectConverter(clazz, manager);
		initArgsConverter(manager);
	}

	protected void initObjectConverter(Class<?> clazz,
			TypeConverterManager manager) {
		objConv = manager.getConverter(clazz);
	}

	protected void initArgsConverter(TypeConverterManager manager) {
		Class<?>[] types = method.getParameterTypes();
		convs = getArgsConverter(types, manager);
	}

	protected TypeConverter[] getArgsConverter(Class<?>[] types,
			TypeConverterManager manager) {
		TypeConverter[] convs = new TypeConverter[types.length];
		for (int i = 0; i < convs.length; i++) {
			TypeConverter conv = manager.getConverter(types[i]);
			if (conv == null) {
				StringBuilder sb = new StringBuilder(64);
				sb.append(name);
				sb.append(" class=");
				sb.append(types[i]);
				throw new UnsupportedOperationException(sb.toString());
			}
			convs[i] = conv;
		}
		return convs;
	}

	/**
	 * 名前取得.
	 *
	 * @return 名前
	 */
	public String getName() {
		return name;
	}

	/**
	 * 引数の型変換オブジェクト取得.
	 *
	 * @return 型変換オブジェクト
	 */
	public TypeConverter[] getTypeConverter() {
		return convs;
	}

	/**
	 * メソッド呼び出し.
	 * <p>
	 * 当該オブジェクトで管理されているメソッドを呼び出す。<br>
	 * 対象オブジェクトは、当該オブジェクトのクラスに変換する。<br>
	 * 各引数も、当該メソッドの引数の型に変換する。
	 * </p>
	 *
	 * @param obj
	 *            操作対象オブジェクト
	 * @param args
	 *            メソッドの引数
	 * @return 呼び出した値
	 * @throws IllegalArgumentLengthException
	 *             引数の個数が当該メソッドの個数と一致しない場合
	 * @throws Exception
	 *             メソッド呼び出し時の例外
	 */
	public Object invoke(Object obj, Object... args) throws Exception {
		checkArgs(args);
		obj = objectConvert(obj);

		Object[] cargs = new Object[convs.length];
		for (int i = 0; i < convs.length; i++) {
			TypeConverter conv = convs[i];
			cargs[i] = conv.convert(args[i]);
		}

		return method.invoke(obj, cargs);
	}

	protected void checkArgs(Object... args) {
		if (args.length != convs.length) {
			throw new IllegalArgumentLengthException(name, args.length,
					convs.length, convs.length);
		}
	}

	@SuppressWarnings("unchecked")
	protected <T> T objectConvert(Object obj) {
		if (isStatic()) {
			return null;
		}
		if (objConv != null) {
			obj = objConv.convert(obj);
		}
		return (T) obj;
	}

	protected boolean isStatic() {
		if (method == null) {
			return false;
		}
		return Modifier.isStatic(method.getModifiers());
	}
}