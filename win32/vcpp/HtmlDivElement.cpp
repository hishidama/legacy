#include <windows.h>
#include <tchar.h>

#include "import.h"
#include "jp_hishidama_win32_mshtml_IHTMLDivElement_Native.h"

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDivElement_00024Native_put_1align
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDivElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_align(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDivElement_00024Native_get_1align
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDivElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_align(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDivElement_00024Native_put_1noWrap
  (JNIEnv * env, jclass, jlong ptr, jboolean arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDivElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_noWrap(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDivElement_00024Native_get_1noWrap
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDivElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT_BOOL ret;
	HRESULT hr = p->get_noWrap(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

