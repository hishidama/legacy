package jp.hishidama.util;

import java.util.HashMap;

/**
 * 双方向ハッシュマップ.
 *
 * <p>
 * 値を指定してキーを取得する事も出来るハッシュマップ。<br>
 * キーと値は1:1対応している前提。
 * </p>
 *
 * @param <K>
 *            キーの型
 * @param <V>
 *            値の型
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/collection.html"
 *         >ひしだま</a>
 * @since 2010.02.11
 */
public class DoubleOrderedHashMap<K, V> extends HashMap<K, V> {

	private static final long serialVersionUID = -3301801302257383034L;

	/** 逆方向マップ */
	protected HashMap<V, K> reverseMap;

	/**
	 * コンストラクター.
	 */
	public DoubleOrderedHashMap() {
		super();
		reverseMap = new HashMap<V, K>();
	}

	/**
	 * コンストラクター.
	 *
	 * @param initialCapacity
	 *            初期サイズ
	 */
	public DoubleOrderedHashMap(int initialCapacity) {
		super(initialCapacity);
		reverseMap = new HashMap<V, K>(initialCapacity);
	}

	@Override
	public V put(K key, V value) {
		reverseMap.put(value, key);
		return super.put(key, value);
	}

	@Override
	public V remove(Object key) {
		V value = super.remove(key);
		reverseMap.remove(value);
		return value;
	}

	@Override
	public void clear() {
		super.clear();
		reverseMap.clear();
	}

	public K getKeyForValue(V value) {
		return reverseMap.get(value);
	}

	@Override
	public boolean containsValue(Object value) {
		return reverseMap.containsKey(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DoubleOrderedHashMap<K, V> clone() {
		DoubleOrderedHashMap<K, V> map = (DoubleOrderedHashMap<K, V>) super
				.clone();
		map.reverseMap = (HashMap<V, K>) this.reverseMap.clone();
		return map;
	}
}
