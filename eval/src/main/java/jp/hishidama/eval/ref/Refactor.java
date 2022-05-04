package jp.hishidama.eval.ref;

/**
 * リファクタリングインターフェース.
 * <p>
 * リファクタリングの内容を実装するインターフェース。
 * </p>
 * 
 * @see jp.hishidama.eval.Expression#refactorName(Refactor)
 * @see jp.hishidama.eval.Expression#refactorFunc(Refactor, Rule)
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html">ひしだま</a>
 * @since 2007.02.19
 */
public interface Refactor {

	/**
	 * 新名称取得.
	 * <p>
	 * リファクタリングで変更する新名称を返す。
	 * </p>
	 * <p>
	 * 変数名およびオブジェクトのフィールド名に対して当メソッドが呼ばれる。<br>
	 * その名称を変更する場合は新名称を返す。変更しない場合はnullを返す。
	 * </p>
	 * <p>
	 * オブジェクトを含んでいる場合は、そのインスタンスを返す為の変数インターフェースを登録する必要がある。
	 * </p>
	 * 
	 * @param target
	 *            「名前」がオブジェクトのフィールドであるとき、そのオブジェクト。それ以外の場合はnull
	 * @param name
	 *            名前
	 * @return 新名称（変更しない場合はnull）
	 */
	public String getNewName(Object target, String name);

	/**
	 * 新関数名取得.
	 * <p>
	 * リファクタリングで変更する新関数名を返す。
	 * </p>
	 * <p>
	 * 関数名およびオブジェクトのメソッド名に対して当メソッドが呼ばれる。<br>
	 * その名称を変更する場合は新名称を返す。変更しない場合はnullを返す。
	 * </p>
	 * <p>
	 * オブジェクトを含んでいる場合は、そのインスタンスを返す為の変数インターフェースを登録する必要がある。
	 * </p>
	 * <p>
	 * 現在のところ、関数の引数は考慮しない。（オーバーロード扱いの同一の関数名は全て変更される）
	 * </p>
	 * 
	 * @param target
	 *            「名前」がオブジェクトのフィールドであるとき、そのオブジェクト。それ以外の場合はnull
	 * @param name
	 *            関数名
	 * @return 新関数名（変更しない場合はnull）
	 */
	public String getNewFuncName(Object target, String name);
}
