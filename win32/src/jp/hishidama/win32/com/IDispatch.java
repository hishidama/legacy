package jp.hishidama.win32.com;

import jp.hishidama.win32.api.WinNTConst;

/**
 * IDispatch�N���X.
 * <p>
 * COM��IDispatch�C���^�[�t�F�[�X�̃N���X�ł��B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.11.04
 * @version 2008.07.14
 */
public class IDispatch extends IUnknown {

	protected IDispatch(long dll_ptr) {
		super(dll_ptr);
	}

	/**
	 * �I�u�W�F�N�g���擾.
	 * <p>
	 * �uIWebBrowser2�v��uDispHTMLDocument�v�Ƃ������A�I�u�W�F�N�g�̎��̖̂��̂�Ԃ��B
	 * </p>
	 * 
	 * @return �I�u�W�F�N�g���i�擾�Ɏ��s�����ꍇ�Anull�j
	 * @see ITypeInfo#getName()
	 */
	public String getTypeName() {
		ITypeInfo info = null;
		try {
			info = Native.GetTypeInfo(getPtr(), 0,
					WinNTConst.LOCALE_USER_DEFAULT);
			return info.getName();
		} catch (RuntimeException e) {
			return null;
		} finally {
			ComPtr.dispose(info);
		}
	}

	/**
	 * �^��񐔎擾.
	 * <p>
	 * {@link ITypeInfo �^���}�̌���Ԃ��B<br>
	 * IDispatch���T�|�[�g����I�u�W�F�N�g�ɂ����Ă� ���1�Ȃ񂾂������B
	 * </p>
	 * 
	 * @return ���i0�܂���1�j
	 * @see #getTypeInfo(int, int, boolean)
	 */
	public int getTypeInfoCount() {
		return Native.GetTypeInfoCount(getPtr());
	}

	/**
	 * �^���擾.
	 * 
	 * @param iTInfo
	 *            �^���̔ԍ�
	 * @param lcid
	 *            ���P�[��ID�i{@link WinNTConst#LOCALE_SYSTEM_DEFAULT}���j
	 * @return �^���
	 */
	public ITypeInfo getTypeInfo(int iTInfo, int lcid) {
		ITypeInfo c = Native.GetTypeInfo(getPtr(), iTInfo, lcid);
		return c;
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getTypeInfo(int, int)
	 */
	public ITypeInfo getTypeInfo(int iTInfo, int lcid, boolean addChild) {
		return getTypeInfo(iTInfo, lcid);
	}

	/**
	 * DISPID�擾.
	 * <p>
	 * ���\�b�h�܂��̓v���p�e�B�̖��O����DISPID���擾����B
	 * </p>
	 * 
	 * @param name
	 *            ����
	 * @return DISPID
	 */
	public long getIDOfName(String name) {
		long[] id = Native.GetIDsOfNames(getPtr(), 0/* IID_NULL */,
				new String[] { name }, WinNTConst.LOCALE_USER_DEFAULT);
		return id[0];
	}

	// ���������z��Ȃ̂��s���i�v�f����1�̂Ƃ��͓����Ă邯�ǁj
	// public long[] getIDsOfNames(long riid, String[] names, int lcid) {
	// return Native.GetIDsOfNames(getPtr(), riid, names, lcid);
	// }

	private static class Native {
		//
		// IDispatch
		//
		private static native int GetTypeInfoCount(long ptr);

		private static native ITypeInfo GetTypeInfo(long ptr, int iTInfo,
				int lcid);

		private static native long[] GetIDsOfNames(long ptr, long riid,
				String[] rgszNames, int lcid);

		// private static native void Invoke(long ptr, DISPID dispIdMember,
		// REFIID riid, LCID lcid, WORD wFlags, DISPPARAMS *pDispParams, Variant
		// *pVarResult, EXCEPINFO *pExcepInfo, UINT *puArgErr);
	}
}
