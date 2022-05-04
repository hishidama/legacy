package jp.hishidama.win32.com;

import java.util.Iterator;

import jp.hishidama.win32.api.ObjBase;
import jp.hishidama.win32.api.ObjBaseConst;

/**
 * COM�Ǘ�.
 * <p>
 * COM�̐�����s���N���X�B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.10.22
 * @version 2007.11.07
 */
public class ComMgr {
	static {
		System.loadLibrary("hmwin32");
	}

	/**
	 * COM�iSTA�j������.
	 * <p>
	 * {@link ObjBase#CoInitialize()}���Ăяo���ACOM��STA�i�V���O���X���b�h�A�p�[�g�����g�j�ŏ���������B
	 * </p>
	 * <p>
	 * COM�֘A�N���X���g���O�ɁA�����\�b�h����񂾂��Ăяo���K�v������B<br>
	 * �����āA�Ō��{@link #uninitialize()}���Ăяo���K�v������B
	 * </p>
	 */
	public static synchronized void initialize() {
		ObjBase.CoInitialize();
		// ObjBase.CoInitializeEx(ObjBaseConst.COINIT_APARTMENTTHREADED);
	}

	/**
	 * COM�iMTA�j������.
	 * <p>
	 * {@link ObjBase#CoInitializeEx(int)}���Ăяo���ACOM��MTA�i�}���`�X���b�h�A�p�[�g�����g�j�ŏ���������B
	 * </p>
	 * <p>
	 * COM�֘A�N���X���g���O�ɁA�����\�b�h����񂾂��Ăяo���K�v������B<br>
	 * �����āA�Ō��{@link #uninitialize()}���Ăяo���K�v������B
	 * </p>
	 */
	public static synchronized void initializeMTA() {
		ObjBase.CoInitializeEx(ObjBaseConst.COINIT_MULTITHREADED);
	}

	/**
	 * COM�I��.
	 * <p>
	 * {@link ObjBase#CoUninitialize()}���Ăяo���ACOM�̎g�p���I������B
	 * </p>
	 * <p>
	 * �擾���ꂽ��{@link ComPtr#dispose()}���Ă΂�Ă��Ȃ�COM�I�u�W�F�N�g���������B
	 * </p>
	 */
	public static synchronized void uninitialize() {

		// �g�����I��dispose()���Ă΂�Ă��Ȃ��I�u�W�F�N�g�h���������
		disposeAll();

		ObjBase.CoUninitialize();
	}

	/**
	 * �SCOM�I�u�W�F�N�g�j��.
	 */
	public static synchronized void disposeAll() {
		// System.out.println("all:" + ComPtr.debug);
		for (Iterator i = ComPtr.clist.iterator(); i.hasNext();) {
			ComPtr d = (ComPtr) i.next();
			if (d != null) {
				d.dispose();
			}
		}
		ComPtr.clist.clear();
		// System.out.println("end:" + ComPtr.debug);
	}

	/**
	 * �Q�ƃJ�E���^�[������������.
	 * 
	 * @param ptr
	 *            DLL���|�C���^�[
	 */
	protected static native void delete(long ptr);
}
