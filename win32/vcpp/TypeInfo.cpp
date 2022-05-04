#include <windows.h>
#include "import.h"
#include "jp_hishidama_win32_com_ITypeInfo_Native.h"

JNIEXPORT jlongArray JNICALL Java_jp_hishidama_win32_com_ITypeInfo_00024Native_GetIDsOfNames
  (JNIEnv *env, jclass, jlong ptr, jobjectArray nameArr)
{
	JNI_TRY();
	ITypeInfoPtr p(reinterpret_cast<IUnknown*>(ptr));

	struct Names {
		JNIEnv *m_env;
		int ct;
		LPOLESTR *ole;
		MEMBERID *mem;

		Names(JNIEnv *env, jobjectArray arr) {
			m_env = env;
			ct = env->GetArrayLength(arr);
			ole = new LPOLESTR[ct];
			for (int i = 0; i < ct; i++) {
				jstring s = (jstring)env->GetObjectArrayElement(arr, i);
				ole[i] = JStringToBSTR(env, s);
			}
			mem = new MEMBERID[ct];
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

	HRESULT hr = p->GetIDsOfNames(names.ole, names.ct, names.mem);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return names;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_com_ITypeInfo_00024Native_GetDocumentation
  (JNIEnv *env, jclass, jlong ptr, jlong memid, jobjectArray namej, jobjectArray docj, jintArray helpcj, jobjectArray helpfj)
{
	JNI_TRY();
	ITypeInfoPtr p(reinterpret_cast<IUnknown*>(ptr));

	BSTR name;
	BSTR doc;
	DWORD helpc;
	BSTR  helpf;
	HRESULT hr = p->GetDocumentation((MEMBERID)memid, &name, &doc, &helpc, &helpf);
	if (namej != NULL && env->GetArrayLength(namej) > 0) {
		env->SetObjectArrayElement(namej, 0, BSTRToJString(env, name));
	}
	if (docj != NULL && env->GetArrayLength(docj) > 0) {
		env->SetObjectArrayElement(docj, 0, BSTRToJString(env, doc));
	}
	if (helpcj != NULL && env->GetArrayLength(helpcj) > 0) {
		jint c = helpc;
		env->SetIntArrayRegion(helpcj, 0, 1, &c);
	}
	if (helpfj != NULL && env->GetArrayLength(helpfj) > 0) {
		env->SetObjectArrayElement(helpfj, 0, BSTRToJString(env, helpf));
	}
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_com_ITypeInfo_00024Native_GetMops
  (JNIEnv *env, jclass, jlong ptr, jlong memid)
{
	JNI_TRY();
	ITypeInfoPtr p(reinterpret_cast<IUnknown*>(ptr));

	BSTR ret;
	HRESULT hr = p->GetMops((MEMBERID)memid, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}
