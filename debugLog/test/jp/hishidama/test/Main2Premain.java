package jp.hishidama.test;

import jp.hishidama.jas.JasTransferPremain;
import jp.hishidama.test.debuglog.DebugLog;
import jp.hishidama.test.debuglog.TestClass;

/**
 * JDK1.5以降の機能である、premain関数の中でJavassitの変換を呼ぶ実験。
 * <p>
 * premainの中で変換機能を登録しているので、それ以降は何も気にする必要は無い。
 * </p>
 * 
 * @see JasTransferPremain
 */
public class Main2Premain {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		TestClass tt = new TestClass(new DebugLog());

		tt.exec();
		tt.dump();
	}
}
