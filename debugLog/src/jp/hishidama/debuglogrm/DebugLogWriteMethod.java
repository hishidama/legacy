package jp.hishidama.debuglogrm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * デバッグログ出力メソッドであることを表すアノテーション.
 * <p>
 * {@link DebugRemoveEditor}では、当アノテーションを付けているメソッドをデバッグログ出力用メソッドと見なす。<br>
 * アプリケーションのクラスが【当アノテーションを付けているメソッド】を呼んでいるとき、{@link DebugRemoveEditor}によってそのメソッド呼び出しが削除される。
 * <br> →<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">使用例</a>
 * </p>
 * 
 * @see DebugLevel
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/index.html">ひしだま</a>
 * @since 2007.11.17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DebugLogWriteMethod {
	DebugLevel value();
}
