package jp.hishidama.lang.reflect.conv;

/**
 * オブジェクト型変換クラス.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2010.02.16
 */
public abstract class TypeConverter {

	protected static final int UNMATCH = -1;
	protected static final int MATCH_NULL = 1;
	protected static final int MATCH_OBJECT = 2;
	protected static final int MATCH_STRING = 3;
	protected static final int MATCH_EQUALS = Short.MAX_VALUE;

	/**
	 * 型がマッチしている度合いを返す。
	 *
	 * @param obj
	 *            オブジェクト
	 * @return 合致度（大きいほどマッチしている）
	 */
	public abstract int match(Object obj);

	/**
	 * 型変換.
	 *
	 * @param obj
	 *            オブジェクト
	 * @return 変換後オブジェクト
	 */
	public abstract Object convert(Object obj);
}
