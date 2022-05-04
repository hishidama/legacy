#include <windows.h>
#include <tchar.h>

#include "import.h"
#include "jp_hishidama_win32_mshtml_IHTMLFontElement_Native.h"

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFontElement_00024Native_put_1color
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLFontElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_color(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFontElement_00024Native_get_1color
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLFontElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_color(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFontElement_00024Native_put_1face
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLFontElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_face(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFontElement_00024Native_get_1face
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLFontElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_face(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFontElement_00024Native_put_1size
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLFontElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_size(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFontElement_00024Native_get_1size
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLFontElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_size(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

