package jp.hishidama.lang.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.hishidama.lang.IllegalArgumentLengthException;
import jp.hishidama.lang.reflect.conv.TypeConverter;
import jp.hishidama.lang.reflect.conv.TypeConverterManager;

/**
 * メソッド呼び出しユーティリティー.
 * <p>
 * 指定された名前のメソッドを呼び出すユーティリティー。
 * </p>
 * <p>
 * まず、呼び出したいクラスのメソッドを管理用の名前を付けて登録する。<br>
 * そして、その管理用の名前でメソッドを呼び出す。<br>
 * このとき、オブジェクトや引数の型は呼び出すメソッドに合致する型に変換する。
 * </p>
 * <p>
 * オーバーロードされているメソッドの場合、引数の個数が一致しているメソッドが1つだけあれば、それを呼び出す。<br>
 * 複数ある場合、なるべく型が一致しているメソッドを呼び出す（かなり適当にマッチさせているので、そんなに厳密ではない…）。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2010.02.16
 */
public class InvokeUtil {

	protected TypeConverterManager manager;

	protected Map<String, Methods> MAP = new HashMap<String, Methods>();

	/**
	 * コンストラクター.
	 */
	public InvokeUtil() {
		this(new TypeConverterManager());
	}

	/**
	 * コンストラクター.
	 *
	 * @param manager
	 *            型変換オブジェクト管理クラス
	 */
	public InvokeUtil(TypeConverterManager manager) {
		this.manager = manager;
	}

	/**
	 * 型変換オブジェクト管理クラス取得.
	 *
	 * @return 型変換オブジェクト管理クラス
	 */
	public TypeConverterManager getConverterManager() {
		return manager;
	}

	/**
	 * メソッド登録.
	 * <p>
	 * 指定されたクラスの全メソッドを管理対象に追加する。<br>
	 * 管理用の名前には、メソッド名の前に指定された接頭辞を付加する。
	 * </p>
	 *
	 * @param clazz
	 *            クラス
	 * @param prefix
	 *            名前の接頭辞
	 * @see #addMethod(Class, String, Method)
	 */
	public void addMethods(Class<?> clazz, String prefix) {
		for (Method m : clazz.getMethods()) {
			addMethod(clazz, prefix + m.getName(), m);
		}
	}

	/**
	 * メソッド登録.
	 *
	 * @param clazz
	 *            対象クラス
	 * @param name
	 *            管理用の名前
	 * @param method
	 *            メソッド
	 * @see #addInvoker(String, Invoker)
	 */
	public void addMethod(Class<?> clazz, String name, Method method) {
		addInvoker(name, new Invoker(name, clazz, method, manager));
	}

	/**
	 * メソッド呼び出しクラス登録.
	 *
	 * @param invoker
	 *            メソッド呼び出しクラス
	 * @since 2010.02.17
	 */
	public void addInvoker(Invoker invoker) {
		String name = invoker.getName();
		addInvoker(name, invoker);
	}

	/**
	 * メソッド呼び出しクラス登録.
	 *
	 * @param name
	 *            管理用の名前
	 * @param invoker
	 *            メソッド呼び出しクラス
	 */
	public void addInvoker(String name, Invoker invoker) {
		Methods mm = MAP.get(name);
		if (mm == null) {
			mm = createMethods(name);
			MAP.put(name, mm);
		}
		mm.add(invoker);
	}

	protected Methods createMethods(String name) {
		return new Methods();
	}

	/**
	 * メソッド呼び出し.
	 *
	 * @param name
	 *            管理用の名前
	 * @param obj
	 *            対象オブジェクト
	 * @param args
	 *            引数
	 * @return メソッドの戻り値
	 * @throws UnsupportedOperationException
	 *             管理対象外の名前の場合
	 * @throws RuntimeException
	 *             メソッド呼び出し時に発生した例外
	 */
	public Object invoke(String name, Object obj, Object... args) {
		Methods mm = MAP.get(name);
		if (mm == null) {
			throw new UnsupportedOperationException("name=" + name);
		}
		try {
			Invoker v = mm.getInvoker(args, BOTH, false);
			return v.invoke(obj, args);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * インスタンスメソッド呼び出しクラス取得.
	 *
	 * @param name
	 *            管理用の名前
	 * @param args
	 *            引数
	 * @return メソッド呼び出しクラス（見つからない場合、null）
	 * @throws UnsupportedOperationException
	 *             管理対象外の名前の場合
	 */
	public Invoker getInstanceInvoker(String name, Object... args) {
		Methods mm = MAP.get(name);
		if (mm == null) {
			throw new UnsupportedOperationException("name=" + name);
		}
		return mm.getInvoker(args, DYNAMIC, true);
	}

	/**
	 * クラスメソッド呼び出しクラス取得.
	 *
	 * @param name
	 *            管理用の名前
	 * @param args
	 *            引数
	 * @return メソッド呼び出しクラス（見つからない場合、null）
	 * @throws UnsupportedOperationException
	 *             管理対象外の名前の場合
	 */
	public Invoker getStaticInvoker(String name, Object... args) {
		Methods mm = MAP.get(name);
		if (mm == null) {
			throw new UnsupportedOperationException("name=" + name);
		}
		return mm.getInvoker(args, STATIC, true);
	}

	protected static final int BOTH = 0;
	protected static final int DYNAMIC = 1;
	protected static final int STATIC = 2;

	/**
	 * メソッドを管理するクラス。<br>
	 * 同一の名前のメソッド、すなわちオーバーロードされたメソッドを管理する。
	 */
	protected static class Methods {

		protected List<Invoker> list = new ArrayList<Invoker>(4);

		public void add(Invoker invoker) {
			list.add(invoker);
			Collections.sort(list, COMP);
		}

		protected static final Comparator<Invoker> COMP = new Comparator<Invoker>() {
			@Override
			public int compare(Invoker o1, Invoker o2) {
				// 引数の個数が少ない方を優先
				return o1.getTypeConverter().length
						- o2.getTypeConverter().length;
			}
		};

		public Invoker getInvoker(Object[] args, int ds, boolean search) {
			Invoker v = resolve(args, ds, search);
			return v;
		}

		protected static class Index {
			public int min, max;

			public Index(int min, int max) {
				this.min = min;
				this.max = max;
			}
		}

		protected Invoker resolve(Object[] args, int ds, boolean search) {
			// 引数の個数が一致しているものを探す
			Index index = getInvokerEqualsLength(args, ds);
			if (index.min == index.max) {
				// 1つだけ見つかった
				return list.get(index.min);
			}
			if (index.min < index.max) {
				// 複数候補あり
				Invoker r = getInvokerMatchType(index, args, ds);
				if (r == null) {
					r = list.get(index.min); // 見つからなかったら先頭のものを使用
				}
				return r;
			}

			// TODO+++args.lengthが大きいとき：可変長引数とか

			if (search) {
				return null;
			}

			int min = list.get(0).getTypeConverter().length;
			int max = list.get(list.size() - 1).getTypeConverter().length;
			throw new IllegalArgumentLengthException(args.length, min, max);
		}

		protected Index getInvokerEqualsLength(Object[] args, int ds) {
			int min = Integer.MAX_VALUE;
			int max = Integer.MIN_VALUE;
			for (int i = 0; i < list.size(); i++) {
				Invoker v = list.get(i);
				switch (ds) {
				case DYNAMIC:
					if (v.isStatic()) {
						continue;
					}
					break;
				case STATIC:
					if (!v.isStatic()) {
						continue;
					}
					break;
				}
				if (v.getTypeConverter().length == args.length) {
					min = Math.min(min, i);
					max = Math.max(max, i);
				}
			}
			return new Index(min, max);
		}

		protected Invoker getInvokerMatchType(Index index, Object[] args, int ds) {
			int max = Integer.MIN_VALUE;
			Invoker ret = null;
			for (int i = index.min; i <= index.max; i++) {
				Invoker v = list.get(i);
				switch (ds) {
				case DYNAMIC:
					if (v.isStatic()) {
						continue;
					}
					break;
				case STATIC:
					if (!v.isStatic()) {
						continue;
					}
					break;
				}
				TypeConverter[] convs = v.getTypeConverter();
				int cc = 0;
				for (int j = 0; j < args.length; j++) {
					int c = convs[j].match(args[j]);
					if (c < 0) {
						cc = -1;
						break;
					}
					cc += c;
				}
				if (cc >= 0) {
					if (max < cc) {
						max = cc;
						ret = v;
					}
				}
			}
			return ret;
		}
	}
}
