package jp.hishidama.win32.com;

/**
 * ITypeInfo�N���X.
 * <p>
 * COM��ITypeInfo�C���^�[�t�F�[�X�̃N���X�ł��B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.11.06
 */
public class ITypeInfo extends IUnknown {

	protected ITypeInfo(long dll_ptr) {
		super(dll_ptr);
	}

	/**
	 * �I�u�W�F�N�g���擾.
	 * 
	 * @return �I�u�W�F�N�g��
	 * @see #getDocumentation(long, String[], String[], int[], String[])
	 */
	public String getName() {
		String[] name = new String[1];
		Native.GetDocumentation(getPtr(), -1, name, null, null, null);
		return name[0];
	}

	/**
	 * �I�u�W�F�N�g�����擾.
	 * 
	 * @return �I�u�W�F�N�g����
	 * @see #getDocumentation(long, String[], String[], int[], String[])
	 */
	public String getDoc() {
		String[] doc = new String[1];
		Native.GetDocumentation(getPtr(), -1, null, doc, null, null);
		return doc[0];
	}

	/**
	 * MEMBERID�擾.
	 * <p>
	 * ���\�b�h�܂��̓v���p�e�B�̖��O����MEMBERID���擾����B
	 * </p>
	 * 
	 * @param name
	 *            ����
	 * @return MEMBERID
	 */
	public long getIDOfName(String name) {
		long[] ret = Native.GetIDsOfNames(getPtr(), new String[] { name });
		return ret[0];
	}

	// ���������z��Ȃ̂��s��
	// public long[] getIDsOfNames(String[] names) {
	// return Native.GetIDsOfNames(getPtr(), names);
	// }

	/**
	 * �h�L�������e�[�V�����擾.
	 * <p>
	 * ���������擾����B<br>
	 * memid��-1�̏ꍇ�A�I�u�W�F�N�g���̂��̂̐�����Ԃ��B
	 * </p>
	 * 
	 * @param memid
	 *            MEMBERID
	 * @param name
	 *            null�ȊO�̏ꍇ�A�Y��0�ɃI�u�W�F�N�g����Ԃ�
	 * @param doc
	 *            null�ȊO�̏ꍇ�A�Y��0�ɃI�u�W�F�N�g�̐�������Ԃ�
	 * @param helpContext
	 *            null�ȊO�̏ꍇ�A�Y��0�Ƀw���v�̃R���e�L�X�gID��Ԃ�
	 * @param helpFile
	 *            null�ȊO�̏ꍇ�A�Y��0�Ƀw���v�t�@�C���̃p�X��Ԃ�
	 */
	public void getDocumentation(long memid, String[] name, String[] doc,
			int[] helpContext, String[] helpFile) {
		Native.GetDocumentation(getPtr(), memid, name, doc, helpContext,
				helpFile);
	}

	/**
	 * �}�[�V�������O���擾.
	 * 
	 * @param memid
	 *            MEMBERID
	 * @return �}�[�V�������O���
	 */
	public String getMops(long memid) {
		return Native.GetMops(getPtr(), memid);
	}

	private static class Native {
		//
		// ITypeInfo
		//

		// private static native TYPEATTR GetTypeAttr(long ptr);
		// private static native ITypeComp GetTypeComp(long ptr);
		// private static native FUNCDESC GetFuncDesc(long ptr, int index);
		// private static native VARDESC GetVarDesc(long ptr, int index);
		// private static native int GetNames(long ptr, long memid, String
		// rgBstrNames, int cMaxNames);
		// private static native HREFTYPE GetRefTypeOfImplType(long ptr, int
		// index);
		// private static native int GetImplTypeFlags(long ptr, int index);
		private static native long[] GetIDsOfNames(long ptr, String[] rgszNames);

		// private static native void Invoke(long ptr, PVOID pvInstance,
		// MEMBERID memid, WORD wFlags, DISPPARAMS *pDispParams, Variant
		// *pVarResult, EXCEPINFO *pExcepInfo, UINT *puArgErr);
		private static native void GetDocumentation(long ptr, long memid,
				String[] pBstrName, String[] pBstrDocString,
				int[] pdwHelpContext, String[] pBstrHelpFile);

		// private static native void GetDllEntry(long ptr, long memid,
		// INVOKEKIND invKind, String *pBstrDllName, String *pBstrName, WORD
		// *pwOrdinal);
		// private static native ITypeInfo GetRefTypeInfo(long ptr, HREFTYPE
		// hRefType);
		// private static native PVOID AddressOfMember(long ptr, long memid,
		// INVOKEKIND invKind);
		// private static native PVOID CreateInstance(long ptr, IUnknown
		// pUnkOuter, REFIID riid);
		private static native String GetMops(long ptr, long memid);
		// private static native void GetContainingTypeLib(long ptr, ITypeLib
		// **ppTLib, UINT *pIndex);
		// private static native void ReleaseTypeAttr(long ptr, TYPEATTR
		// pTypeAttr);
		// private static native void ReleaseFuncDesc(long ptr, FUNCDESC
		// pFuncDesc);
		// private static native void ReleaseVarDesc(long ptr, VARDESC
		// pVarDesc);
	}
}
