#include <windows.h>
#include <tchar.h>

#include "import.h"
#include "jp_hishidama_win32_mshtml_IHTMLCommentElement_Native.h"

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLCommentElement_00024Native_put_1text
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLCommentElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_text(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLCommentElement_00024Native_get_1text
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLCommentElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_text(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLCommentElement_00024Native_put_1atomic
  (JNIEnv * env, jclass, jlong ptr, jint arg1)
{
	JNI_TRY();
	MSHTML::IHTMLCommentElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_atomic(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLCommentElement_00024Native_get_1atomic
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLCommentElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	long ret;
	HRESULT hr = p->get_atomic(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLCommentElement_00024Native_put_1data
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLCommentElement2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_data(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLCommentElement_00024Native_get_1data
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLCommentElement2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_data(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLCommentElement_00024Native_get_1length
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLCommentElement2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	long ret;
	HRESULT hr = p->get_length(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLCommentElement_00024Native_substringData
  (JNIEnv * env, jclass, jlong ptr, jint arg1, jint arg2)
{
	JNI_TRY();
	MSHTML::IHTMLCommentElement2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->substringData(arg1, arg2, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLCommentElement_00024Native_appendData
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLCommentElement2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->appendData(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLCommentElement_00024Native_insertData
  (JNIEnv * env, jclass, jlong ptr, jint arg1, jstring arg2)
{
	JNI_TRY();
	MSHTML::IHTMLCommentElement2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg2(env, arg2);
	HRESULT hr = p->insertData(arg1, _arg2);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLCommentElement_00024Native_deleteData
  (JNIEnv * env, jclass, jlong ptr, jint arg1, jint arg2)
{
	JNI_TRY();
	MSHTML::IHTMLCommentElement2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->deleteData(arg1, arg2);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLCommentElement_00024Native_replaceData
  (JNIEnv * env, jclass, jlong ptr, jint arg1, jint arg2, jstring arg3)
{
	JNI_TRY();
	MSHTML::IHTMLCommentElement2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg3(env, arg3);
	HRESULT hr = p->replaceData(arg1, arg2, _arg3);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

