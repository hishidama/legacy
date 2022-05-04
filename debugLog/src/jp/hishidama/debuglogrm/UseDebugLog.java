package jp.hishidama.debuglogrm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * デバッグログを出力するクラスを表すアノテーション.
 * <p>
 * このアノテーションが付いているクラス内の各メソッドに対し、デバッグログ出力削除処理を行う。<br> →<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">使用例</a>
 * </p>
 * 
 * @see DebugRemoveEditor
 * @see DebugLogWriteMethod
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/index.html">ひしだま</a>
 * @since 2007.11.17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE })
public @interface UseDebugLog {
}
