package jp.hishidama.eval.var;

import java.util.*;

/**
 * 変数管理クラス.
 * <p>
 * 変数と変数値を管理する。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2007.02.13
 * @version eval16
 */
public class MapVariable<K, V> extends DefaultVariable {

	protected Map<K, V> map;

	/**
	 * コンストラクター.
	 * <p>
	 * 空の変数マップを作成する。
	 * </p>
	 */
	public MapVariable() {
		this(new HashMap<K, V>());
	}

	/**
	 * コンストラクター.
	 * <p>
	 * 空の変数マップを作成する。（動的型保証を行う）
	 * </p>
	 *
	 * @param keyType
	 *            キーの型
	 * @param valueType
	 *            値の型
	 * @see Collections#checkedMap(Map, Class, Class)
	 * @since eval16
	 */
	public MapVariable(Class<K> keyType, Class<V> valueType) {
		this(Collections.checkedMap(new HashMap<K, V>(), keyType, valueType));
	}

	/**
	 * コンストラクター.
	 *
	 * @param varMap
	 *            変数マップ
	 */
	public MapVariable(Map<K, V> varMap) {
		map = varMap;
	}

	/**
	 * 変数マップ設定.
	 * <p>
	 * 変数名(String)と値のマップを設定する。
	 * </p>
	 * <p>
	 * 値が数値の場合はNumber（LongやDouble）であること。<br>
	 * varMap.put("var", new Long(1));
	 * </p>
	 * <p>
	 * 値がそれ以外（配列）の場合はJavaのオブジェクトをそのまま格納する。<br>
	 * Long[] arr = new Long[2]; varMap.put("arr", arr);
	 * </p>
	 *
	 * @param varMap
	 *            変数マップ
	 */
	public void setMap(Map<K, V> varMap) {
		map = varMap;
	}

	/**
	 * 変数マップ取得.
	 *
	 * @return 変数マップ
	 */
	public Map<K, V> getMap() {
		return map;
	}

	/**
	 * 値設定.
	 *
	 * @param name
	 *            変数名
	 * @param value
	 *            値
	 */
	public void put(K name, V value) {
		map.put(name, value);
	}

	/**
	 * 値取得.
	 *
	 * @param name
	 *            変数名
	 * @return 値
	 */
	public V get(K name) {
		return map.get(name);
	}

	/**
	 * 変数オブジェクト取得.
	 * <p>
	 * 変数を表すオブジェクトを返す。（単なる変数や配列を想定）
	 * </p>
	 *
	 * @param name
	 *            変数名
	 * @return 変数オブジェクト
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getValue(Object name) {
		return get((K) name);
	}

	/**
	 * 変数設定.
	 * <p>
	 * 変数に値をセットする。
	 * </p>
	 *
	 * @param name
	 *            変数名
	 * @param value
	 *            値
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setValue(Object name, Object value) {
		put((K) name, (V) value);
	}
}
