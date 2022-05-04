#include <windows.h>
#include <tchar.h>

#include "import.h"
#include "jp_hishidama_win32_mshtml_IHTMLFrameBase_Native.h"

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_put_1src
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBasePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_src(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_get_1src
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBasePtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_src(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_put_1name
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBasePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_name(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_get_1name
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBasePtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_name(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_put_1border
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBasePtr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_border(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_get_1border
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBasePtr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_border(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_put_1frameBorder
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBasePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_frameBorder(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_get_1frameBorder
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBasePtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_frameBorder(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_put_1frameSpacing
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBasePtr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_frameSpacing(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_get_1frameSpacing
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBasePtr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_frameSpacing(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_put_1marginWidth
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBasePtr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_marginWidth(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_get_1marginWidth
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBasePtr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_marginWidth(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_put_1marginHeight
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBasePtr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_marginHeight(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_get_1marginHeight
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBasePtr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_marginHeight(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_put_1noResize
  (JNIEnv * env, jclass, jlong ptr, jboolean arg1)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBasePtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_noResize(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_get_1noResize
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBasePtr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT_BOOL ret;
	HRESULT hr = p->get_noResize(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_put_1scrolling
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBasePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_scrolling(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_get_1scrolling
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBasePtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_scrolling(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_get_1contentWindow
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBase2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLWindow2* ret;
	HRESULT hr = p->get_contentWindow(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLWindow(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_get_1readyState
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBase2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_readyState(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_put_1allowTransparency
  (JNIEnv * env, jclass, jlong ptr, jboolean arg1)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBase2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_allowTransparency(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_get_1allowTransparency
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBase2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT_BOOL ret;
	HRESULT hr = p->get_allowTransparency(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_put_1longDesc
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBase3Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_longDesc(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLFrameBase_00024Native_get_1longDesc
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLFrameBase3Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_longDesc(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

