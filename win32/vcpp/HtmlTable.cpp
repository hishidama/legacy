#include <windows.h>
#include <tchar.h>

#include "import.h"
#include "jp_hishidama_win32_mshtml_IHTMLTable_Native.h"

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_put_1cols
  (JNIEnv * env, jclass, jlong ptr, jint arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_cols(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1cols
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	long ret;
	HRESULT hr = p->get_cols(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_put_1border
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_border(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1border
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_border(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_put_1frame
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_frame(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1frame
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_frame(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_put_1rules
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_rules(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1rules
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_rules(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_put_1cellSpacing
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_cellSpacing(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1cellSpacing
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_cellSpacing(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_put_1cellPadding
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_cellPadding(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1cellPadding
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_cellPadding(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_put_1background
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_background(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1background
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_background(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_put_1bgColor
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_bgColor(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1bgColor
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_bgColor(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_put_1borderColor
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_borderColor(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1borderColor
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_borderColor(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_put_1borderColorLight
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_borderColorLight(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1borderColorLight
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_borderColorLight(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_put_1borderColorDark
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_borderColorDark(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1borderColorDark
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_borderColorDark(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_put_1align
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_align(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1align
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_align(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_refresh
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->refresh();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1rows
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLElementCollection* ret;
	HRESULT hr = p->get_rows(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLElementCollection(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_put_1width
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_width(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1width
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_width(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_put_1height
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_height(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1height
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_height(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_put_1dataPageSize
  (JNIEnv * env, jclass, jlong ptr, jint arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_dataPageSize(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1dataPageSize
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	long ret;
	HRESULT hr = p->get_dataPageSize(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_nextPage
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->nextPage();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_previousPage
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->previousPage();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1tHead
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLTableSection* ret;
	HRESULT hr = p->get_tHead(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLTableSection(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1tFoot
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLTableSection* ret;
	HRESULT hr = p->get_tFoot(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLTableSection(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1tBodies
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLElementCollection* ret;
	HRESULT hr = p->get_tBodies(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLElementCollection(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1caption
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLTableCaption* ret;
	HRESULT hr = p->get_caption(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLTableCaption(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_createTHead
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	IDispatch* ret;
	HRESULT hr = p->createTHead(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIDispatch(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_deleteTHead
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->deleteTHead();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_createTFoot
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	IDispatch* ret;
	HRESULT hr = p->createTFoot(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIDispatch(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_deleteTFoot
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->deleteTFoot();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_createCaption
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLTableCaption* ret;
	HRESULT hr = p->createCaption(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLTableCaption(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_deleteCaption
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->deleteCaption();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_insertRow
  (JNIEnv * env, jclass, jlong ptr, jint arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	IDispatch* ret;
	HRESULT hr = p->insertRow(arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIDispatch(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_deleteRow
  (JNIEnv * env, jclass, jlong ptr, jint arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->deleteRow(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1readyState
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTablePtr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_readyState(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_firstPage
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTable2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->firstPage();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_lastPage
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTable2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->lastPage();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1cells
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTable2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLElementCollection* ret;
	HRESULT hr = p->get_cells(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLElementCollection(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_moveRow
  (JNIEnv * env, jclass, jlong ptr, jint arg1, jint arg2)
{
	JNI_TRY();
	MSHTML::IHTMLTable2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	IDispatch* ret;
	HRESULT hr = p->moveRow(arg1, arg2, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIDispatch(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_put_1summary
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLTable3Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_summary(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTable_00024Native_get_1summary
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLTable3Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_summary(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

