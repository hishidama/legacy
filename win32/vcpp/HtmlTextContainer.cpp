#include <windows.h>
#include <tchar.h>

#include "import.h"
#include "jp_hishidama_win32_mshtml_IHTMLTextContainer_Native.h"

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTextContainer_00024Native_createControlRange
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTextContainerPtr p(reinterpret_cast<IDispatch*>(ptr));

	IDispatch* ret;
	HRESULT hr = p->createControlRange(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIDispatch(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTextContainer_00024Native_get_1scrollHeight
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTextContainerPtr p(reinterpret_cast<IDispatch*>(ptr));

	long ret;
	HRESULT hr = p->get_scrollHeight(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTextContainer_00024Native_get_1scrollWidth
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTextContainerPtr p(reinterpret_cast<IDispatch*>(ptr));

	long ret;
	HRESULT hr = p->get_scrollWidth(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTextContainer_00024Native_put_1scrollTop
  (JNIEnv * env, jclass, jlong ptr, jint arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTextContainerPtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_scrollTop(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTextContainer_00024Native_get_1scrollTop
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTextContainerPtr p(reinterpret_cast<IDispatch*>(ptr));

	long ret;
	HRESULT hr = p->get_scrollTop(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTextContainer_00024Native_put_1scrollLeft
  (JNIEnv * env, jclass, jlong ptr, jint arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTextContainerPtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_scrollLeft(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTextContainer_00024Native_get_1scrollLeft
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTextContainerPtr p(reinterpret_cast<IDispatch*>(ptr));

	long ret;
	HRESULT hr = p->get_scrollLeft(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

