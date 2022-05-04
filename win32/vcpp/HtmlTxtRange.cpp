#include <windows.h>
#include <tchar.h>

#include "import.h"
#include "jp_hishidama_win32_mshtml_IHTMLTxtRange_Native.h"

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_get_1htmlText
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_htmlText(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_put_1text
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_text(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_get_1text
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_text(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_parentElement
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLElement* ret;
	HRESULT hr = p->parentElement(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLElement(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_duplicate
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLTxtRange* ret;
	HRESULT hr = p->duplicate(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLTxtRange(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_inRange
  (JNIEnv * env, jclass, jlong ptr, jlong arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLTxtRangePtr _arg1(reinterpret_cast<IDispatch*>(arg1));
	VARIANT_BOOL ret;
	HRESULT hr = p->inRange(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_isEqual
  (JNIEnv * env, jclass, jlong ptr, jlong arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLTxtRangePtr _arg1(reinterpret_cast<IDispatch*>(arg1));
	VARIANT_BOOL ret;
	HRESULT hr = p->isEqual(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_scrollIntoView
  (JNIEnv * env, jclass, jlong ptr, jboolean arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->scrollIntoView(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_collapse
  (JNIEnv * env, jclass, jlong ptr, jboolean arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->collapse(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_expand
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	VARIANT_BOOL ret;
	HRESULT hr = p->expand(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_move
  (JNIEnv * env, jclass, jlong ptr, jstring arg1, jint arg2)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	long ret;
	HRESULT hr = p->move(_arg1, arg2, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_moveStart
  (JNIEnv * env, jclass, jlong ptr, jstring arg1, jint arg2)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	long ret;
	HRESULT hr = p->moveStart(_arg1, arg2, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_moveEnd
  (JNIEnv * env, jclass, jlong ptr, jstring arg1, jint arg2)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	long ret;
	HRESULT hr = p->moveEnd(_arg1, arg2, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_select
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->select();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_pasteHTML
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->pasteHTML(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_moveToElementText
  (JNIEnv * env, jclass, jlong ptr, jlong arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLElementPtr _arg1(reinterpret_cast<IDispatch*>(arg1));
	HRESULT hr = p->moveToElementText(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_setEndPoint
  (JNIEnv * env, jclass, jlong ptr, jstring arg1, jlong arg2)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	MSHTML::IHTMLTxtRangePtr _arg2(reinterpret_cast<IDispatch*>(arg2));
	HRESULT hr = p->setEndPoint(_arg1, _arg2);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_compareEndPoints
  (JNIEnv * env, jclass, jlong ptr, jstring arg1, jlong arg2)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	MSHTML::IHTMLTxtRangePtr _arg2(reinterpret_cast<IDispatch*>(arg2));
	long ret;
	HRESULT hr = p->compareEndPoints(_arg1, _arg2, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_findText
  (JNIEnv * env, jclass, jlong ptr, jstring arg1, jint arg2, jint arg3)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	VARIANT_BOOL ret;
	HRESULT hr = p->findText(_arg1, arg2, arg3, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_moveToPoint
  (JNIEnv * env, jclass, jlong ptr, jint arg1, jint arg2)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->moveToPoint(arg1, arg2);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_getBookmark
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->getBookmark(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_moveToBookmark
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	VARIANT_BOOL ret;
	HRESULT hr = p->moveToBookmark(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_queryCommandSupported
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	VARIANT_BOOL ret;
	HRESULT hr = p->queryCommandSupported(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_queryCommandEnabled
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	VARIANT_BOOL ret;
	HRESULT hr = p->queryCommandEnabled(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_queryCommandState
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	VARIANT_BOOL ret;
	HRESULT hr = p->queryCommandState(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_queryCommandIndeterm
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	VARIANT_BOOL ret;
	HRESULT hr = p->queryCommandIndeterm(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_queryCommandText
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	BSTR ret;
	HRESULT hr = p->queryCommandText(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_queryCommandValue
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	VARIANT ret;
	HRESULT hr = p->queryCommandValue(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret.bstrVal);
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_execCommand
  (JNIEnv * env, jclass, jlong ptr, jstring arg1, jboolean arg2, jobject arg3)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	JVariant _arg3(env, arg3);
	VARIANT_BOOL ret;
	HRESULT hr = p->execCommand(_arg1, arg2, _arg3, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_execCommandShowHelp
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTxtRangePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	VARIANT_BOOL ret;
	HRESULT hr = p->execCommandShowHelp(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

