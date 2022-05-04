/**
 * 四則演算パッケージ.
 * <p>
 * 文字列内に保持している計算式を解釈し、計算を実行するクラス群です。
 * </p>
 * <pre>import jp.hishidama.eval.*;
 *
 * 	Rule rule = ExpRuleFactory.getDefaultRule();
 * 	Expression exp = rule.parse("1+2*3");
 * 	long n = exp.evalLong();</pre>
 *
 * <p>
 * →<a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html">その他の使用例</a>
 * </p>
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/tech/soft/index.html">ひしだま</a>
 */
package jp.hishidama.eval;

