#include <windows.h>
#include "import.h"
#include "jp_hishidama_win32_api_ObjBase.h"

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_api_ObjBase_CoInitialize
  (JNIEnv *env, jclass)
{
	JNI_TRY();
	HRESULT hr = ::CoInitialize(NULL);
	if (FAILED(hr)) {
		ThrowHResultException(env, hr);
	}
	return hr;
	JNI_END(return 0);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_api_ObjBase_CoInitializeEx
  (JNIEnv *env, jclass, jint coInit)
{
	JNI_TRY();
	HRESULT hr = ::CoInitializeEx(NULL, coInit);
	if (FAILED(hr)) {
		ThrowHResultException(env, hr);
	}
	return hr;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_api_ObjBase_CoUninitialize
  (JNIEnv *env, jclass)
{
	JNI_TRY();
	::CoUninitialize();
	JNI_END(return);
}
