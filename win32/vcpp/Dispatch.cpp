#include <windows.h>
#include "import.h"
#include "jp_hishidama_win32_com_IDispatch_Native.h"

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_com_IDispatch_00024Native_GetTypeInfoCount
  (JNIEnv *env, jclass, jlong ptr)
{
	JNI_TRY();
	IDispatchPtr p(reinterpret_cast<IDispatch*>(ptr));

	UINT ret;
	HRESULT hr = p->GetTypeInfoCount(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_com_IDispatch_00024Native_GetTypeInfo
  (JNIEnv *env, jclass, jlong ptr, jint iTInfo, jint lcid)
{
	JNI_TRY();
	IDispatchPtr p(reinterpret_cast<IDispatch*>(ptr));

	ITypeInfo* ret;
	HRESULT hr = p->GetTypeInfo(iTInfo, lcid, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewObject(env, "Ljp/hishidama/win32/com/ITypeInfo;", ret);
	JNI_END(return 0);
}

// éQçlÅFhttp://www.sol.dti.ne.jp/~yoshinor/ni/ni0003.html
JNIEXPORT jlongArray JNICALL Java_jp_hishidama_win32_com_IDispatch_00024Native_GetIDsOfNames
  (JNIEnv *env, jclass, jlong ptr, jlong riidj, jobjectArray nameArr, jint lcid)
{
	JNI_TRY();
	IDispatchPtr p(reinterpret_cast<IDispatch*>(ptr));

	struct Names {
		JNIEnv *m_env;
		int ct;
		LPOLESTR *ole;
		DISPID *mem;

		Names(JNIEnv *env, jobjectArray arr) {
			m_env = env;
			ct = env->GetArrayLength(arr);
			ole = new LPOLESTR[ct];
			for (int i = 0; i < ct; i++) {
				jstring s = (jstring)env->GetObjectArrayElement(arr, i);
				ole[i] = JStringToBSTR(env, s);
			}
			mem = new DISPID[ct];
		}

		operator jlongArray() {
			jlongArray arr = m_env->NewLongArray(ct);
			jlong * elems = m_env->GetLongArrayElements(arr, NULL);
			for (int i = 0; i < ct; i++) {
				elems[i] = mem[i];
			}
			m_env->ReleaseLongArrayElements(arr, elems, 0);
			return arr;
		}

		~Names() {
			for (int i = 0; i < ct; i++) {
				ReleaseJStringBSTR(ole[i]);
			}
			delete[] mem;
			delete[] ole;
		}
	} names(env, nameArr);

	REFIID riid = IID_NULL;

	HRESULT hr = p->GetIDsOfNames(riid, names.ole, names.ct, lcid, names.mem);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return names;
	JNI_END(return 0);
}
