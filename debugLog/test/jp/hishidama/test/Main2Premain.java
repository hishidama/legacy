package jp.hishidama.test;

import jp.hishidama.jas.JasTransferPremain;
import jp.hishidama.test.debuglog.DebugLog;
import jp.hishidama.test.debuglog.TestClass;

/**
 * JDK1.5�ȍ~�̋@�\�ł���Apremain�֐��̒���Javassit�̕ϊ����ĂԎ����B
 * <p>
 * premain�̒��ŕϊ��@�\��o�^���Ă���̂ŁA����ȍ~�͉����C�ɂ���K�v�͖����B
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
