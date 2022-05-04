#include <windows.h>
#include <tchar.h>

#include "import.h"
#include "jp_hishidama_win32_mshtml_IHTMLHtmlElement_Native.h"

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLHtmlElement_00024Native_put_1version
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLHtmlElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_version(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLHtmlElement_00024Native_get_1version
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLHtmlElementPtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_version(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

