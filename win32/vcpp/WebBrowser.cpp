#include <windows.h>
#include <tchar.h>

#include "import.h"
#include "jp_hishidama_win32_shdocvw_IWebBrowser_Native.h"

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_GoBack
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->GoBack();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_GoForward
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->GoForward();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_GoHome
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->GoHome();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_GoSearch
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->GoSearch();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_Navigate
  (JNIEnv * env, jclass, jlong ptr, jstring arg1, jstring arg2, jstring arg3)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	JString _arg2(env, arg2);
	JString _arg3(env, arg3);
	HRESULT hr = p->Navigate(_arg1, _arg2, _arg3);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_Refresh
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->Refresh();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_Stop
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->Stop();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1Document
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	IDispatch* ret;
	HRESULT hr = p->get_Document(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIDispatch(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1Type
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_Type(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1Left
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	long ret;
	HRESULT hr = p->get_Left(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_put_1Left
  (JNIEnv * env, jclass, jlong ptr, jint arg1)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_Left(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1Top
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	long ret;
	HRESULT hr = p->get_Top(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_put_1Top
  (JNIEnv * env, jclass, jlong ptr, jint arg1)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_Top(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1Width
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	long ret;
	HRESULT hr = p->get_Width(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_put_1Width
  (JNIEnv * env, jclass, jlong ptr, jint arg1)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_Width(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1Height
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	long ret;
	HRESULT hr = p->get_Height(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_put_1Height
  (JNIEnv * env, jclass, jlong ptr, jint arg1)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_Height(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1LocationName
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_LocationName(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1LocationURL
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_LocationURL(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1Busy
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT_BOOL ret;
	HRESULT hr = p->get_Busy(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_Quit
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->Quit();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1Name
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_Name(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1HWND
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	long ret;
	HRESULT hr = p->get_HWND(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return HandleToJLong(LongToHandle(ret));
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1FullName
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_FullName(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1Path
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_Path(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1Visible
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT_BOOL ret;
	HRESULT hr = p->get_Visible(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_put_1Visible
  (JNIEnv * env, jclass, jlong ptr, jboolean arg1)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_Visible(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1StatusBar
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT_BOOL ret;
	HRESULT hr = p->get_StatusBar(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_put_1StatusBar
  (JNIEnv * env, jclass, jlong ptr, jboolean arg1)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_StatusBar(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1StatusText
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_StatusText(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_put_1StatusText
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_StatusText(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1ToolBar
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	int ret;
	HRESULT hr = p->get_ToolBar(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_put_1ToolBar
  (JNIEnv * env, jclass, jlong ptr, jint arg1)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_ToolBar(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1MenuBar
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT_BOOL ret;
	HRESULT hr = p->get_MenuBar(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_put_1MenuBar
  (JNIEnv * env, jclass, jlong ptr, jboolean arg1)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_MenuBar(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1FullScreen
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT_BOOL ret;
	HRESULT hr = p->get_FullScreen(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_put_1FullScreen
  (JNIEnv * env, jclass, jlong ptr, jboolean arg1)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_FullScreen(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1ReadyState
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	tagREADYSTATE ret;
	HRESULT hr = p->get_ReadyState(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1Offline
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT_BOOL ret;
	HRESULT hr = p->get_Offline(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_put_1Offline
  (JNIEnv * env, jclass, jlong ptr, jboolean arg1)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_Offline(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1Silent
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT_BOOL ret;
	HRESULT hr = p->get_Silent(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_put_1Silent
  (JNIEnv * env, jclass, jlong ptr, jboolean arg1)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_Silent(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1RegisterAsBrowser
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT_BOOL ret;
	HRESULT hr = p->get_RegisterAsBrowser(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_put_1RegisterAsBrowser
  (JNIEnv * env, jclass, jlong ptr, jboolean arg1)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_RegisterAsBrowser(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1RegisterAsDropTarget
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT_BOOL ret;
	HRESULT hr = p->get_RegisterAsDropTarget(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_put_1RegisterAsDropTarget
  (JNIEnv * env, jclass, jlong ptr, jboolean arg1)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_RegisterAsDropTarget(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1TheaterMode
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT_BOOL ret;
	HRESULT hr = p->get_TheaterMode(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_put_1TheaterMode
  (JNIEnv * env, jclass, jlong ptr, jboolean arg1)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_TheaterMode(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1AddressBar
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT_BOOL ret;
	HRESULT hr = p->get_AddressBar(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_put_1AddressBar
  (JNIEnv * env, jclass, jlong ptr, jboolean arg1)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_AddressBar(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_get_1Resizable
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT_BOOL ret;
	HRESULT hr = p->get_Resizable(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_put_1Resizable
  (JNIEnv * env, jclass, jlong ptr, jboolean arg1)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_Resizable(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

