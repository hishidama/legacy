#include <windows.h>
#include <tchar.h>

#include "import.h"
#include "jp_hishidama_win32_mshtml_IHTMLWindow_Native.h"

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_get_1frames
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLFramesCollection2* ret;
	HRESULT hr = p->get_frames(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLFramesCollection(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_put_1defaultStatus
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_defaultStatus(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_get_1defaultStatus
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_defaultStatus(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_put_1status
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_status(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_get_1status
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_status(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_alert
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->alert(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_confirm
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	VARIANT_BOOL ret;
	HRESULT hr = p->confirm(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_prompt
  (JNIEnv * env, jclass, jlong ptr, jstring arg1, jstring arg2)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	JString _arg2(env, arg2);
	VARIANT ret;
	HRESULT hr = p->prompt(_arg1, _arg2, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_get_1location
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLLocation* ret;
	HRESULT hr = p->get_location(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLLocation(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_close
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->close();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_put_1opener
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_opener(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_get_1opener
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_opener(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_put_1name
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_name(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_get_1name
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_name(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_get_1parent
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLWindow2* ret;
	HRESULT hr = p->get_parent(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLWindow(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_open
  (JNIEnv * env, jclass, jlong ptr, jstring arg1, jstring arg2, jstring arg3, jboolean arg4)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	JString _arg2(env, arg2);
	JString _arg3(env, arg3);
	MSHTML::IHTMLWindow2* ret;
	HRESULT hr = p->open(_arg1, _arg2, _arg3, arg4, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLWindow(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_get_1self
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLWindow2* ret;
	HRESULT hr = p->get_self(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLWindow(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_get_1top
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLWindow2* ret;
	HRESULT hr = p->get_top(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLWindow(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_get_1window
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLWindow2* ret;
	HRESULT hr = p->get_window(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLWindow(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_navigate
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->navigate(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_get_1document
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLDocument2* ret;
	HRESULT hr = p->get_document(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLDocument(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_get_1_1newEnum
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	IUnknown* ret;
	HRESULT hr = p->get__newEnum(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIUnknown(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_focus
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->focus();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_get_1closed
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT_BOOL ret;
	HRESULT hr = p->get_closed(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_blur
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->blur();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_scroll
  (JNIEnv * env, jclass, jlong ptr, jint arg1, jint arg2)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->scroll(arg1, arg2);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_execScript
  (JNIEnv * env, jclass, jlong ptr, jstring arg1, jstring arg2)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	JString _arg2(env, arg2);
	VARIANT ret;
	HRESULT hr = p->execScript(_arg1, _arg2, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_scrollBy
  (JNIEnv * env, jclass, jlong ptr, jint arg1, jint arg2)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->scrollBy(arg1, arg2);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_scrollTo
  (JNIEnv * env, jclass, jlong ptr, jint arg1, jint arg2)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->scrollTo(arg1, arg2);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_moveTo
  (JNIEnv * env, jclass, jlong ptr, jint arg1, jint arg2)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->moveTo(arg1, arg2);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_moveBy
  (JNIEnv * env, jclass, jlong ptr, jint arg1, jint arg2)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->moveBy(arg1, arg2);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_resizeTo
  (JNIEnv * env, jclass, jlong ptr, jint arg1, jint arg2)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->resizeTo(arg1, arg2);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_resizeBy
  (JNIEnv * env, jclass, jlong ptr, jint arg1, jint arg2)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->resizeBy(arg1, arg2);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_get_1external
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	IDispatch* ret;
	HRESULT hr = p->get_external(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIDispatch(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_get_1screenLeft
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow3Ptr p(reinterpret_cast<IDispatch*>(ptr));

	long ret;
	HRESULT hr = p->get_screenLeft(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_get_1screenTop
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow3Ptr p(reinterpret_cast<IDispatch*>(ptr));

	long ret;
	HRESULT hr = p->get_screenTop(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLWindow_00024Native_print
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLWindow3Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->print();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

