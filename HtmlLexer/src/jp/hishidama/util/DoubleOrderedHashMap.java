package jp.hishidama.util;

import java.util.HashMap;

/**
 * �o�����n�b�V���}�b�v.
 *
 * <p>
 * �l���w�肵�ăL�[���擾���鎖���o����n�b�V���}�b�v�B<br>
 * �L�[�ƒl��1:1�Ή����Ă���O��B
 * </p>
 *
 * @param <K>
 *            �L�[�̌^
 * @param <V>
 *            �l�̌^
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/collection.html"
 *         >�Ђ�����</a>
 * @since 2010.02.11
 */
public class DoubleOrderedHashMap<K, V> extends HashMap<K, V> {

	private static final long serialVersionUID = -3301801302257383034L;

	/** �t�����}�b�v */
	protected HashMap<V, K> reverseMap;

	/**
	 * �R���X�g���N�^�[.
	 */
	public DoubleOrderedHashMap() {
		super();
		reverseMap = new HashMap<V, K>();
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param initialCapacity
	 *            �����T�C�Y
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
