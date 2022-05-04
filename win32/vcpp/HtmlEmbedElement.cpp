#include <windows.h>
#include <tchar.h>

#include "import.h"
#include "jp_hishidama_win32_mshtml_IHTMLEmbedElement_Native.h"

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLEmbedElement_00024Native_put_1hidden
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLEmbedElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_hidden(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLEmbedElement_00024Native_get_1hidden
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLEmbedElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_hidden(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLEmbedElement_00024Native_get_1palette
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLEmbedElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_palette(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLEmbedElement_00024Native_get_1pluginspage
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLEmbedElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_pluginspage(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLEmbedElement_00024Native_put_1src
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLEmbedElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_src(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLEmbedElement_00024Native_get_1src
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLEmbedElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_src(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLEmbedElement_00024Native_put_1units
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLEmbedElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_units(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLEmbedElement_00024Native_get_1units
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLEmbedElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_units(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLEmbedElement_00024Native_put_1name
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLEmbedElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_name(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLEmbedElement_00024Native_get_1name
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLEmbedElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_name(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLEmbedElement_00024Native_put_1width
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLEmbedElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_width(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLEmbedElement_00024Native_get_1width
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLEmbedElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_width(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLEmbedElement_00024Native_put_1height
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLEmbedElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_height(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLEmbedElement_00024Native_get_1height
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLEmbedElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_height(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

