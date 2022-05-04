#include <windows.h>
#include <tchar.h>

#include "import.h"
#include "jp_hishidama_win32_mshtml_IHTMLIFrameElement_Native.h"

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLIFrameElement_00024Native_put_1vspace
  (JNIEnv * env, jclass, jlong ptr, jint arg1)
{
	JNI_TRY();
	MSHTML::IHTMLIFrameElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_vspace(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLIFrameElement_00024Native_get_1vspace
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLIFrameElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	long ret;
	HRESULT hr = p->get_vspace(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLIFrameElement_00024Native_put_1hspace
  (JNIEnv * env, jclass, jlong ptr, jint arg1)
{
	JNI_TRY();
	MSHTML::IHTMLIFrameElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_hspace(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLIFrameElement_00024Native_get_1hspace
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLIFrameElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	long ret;
	HRESULT hr = p->get_hspace(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLIFrameElement_00024Native_put_1align
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLIFrameElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_align(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLIFrameElement_00024Native_get_1align
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLIFrameElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_align(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLIFrameElement_00024Native_put_1height
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLIFrameElement2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_height(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLIFrameElement_00024Native_get_1height
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLIFrameElement2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_height(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLIFrameElement_00024Native_put_1width
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLIFrameElement2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_width(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLIFrameElement_00024Native_get_1width
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLIFrameElement2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_width(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

