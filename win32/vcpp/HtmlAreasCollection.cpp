#include <windows.h>
#include <tchar.h>

#include "import.h"
#include "jp_hishidama_win32_mshtml_IHTMLAreasCollection_Native.h"

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLAreasCollection_00024Native_put_1length
  (JNIEnv * env, jclass, jlong ptr, jint arg1)
{
	JNI_TRY();
	MSHTML::IHTMLAreasCollectionPtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->put_length(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLAreasCollection_00024Native_get_1length
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLAreasCollectionPtr p(reinterpret_cast<IDispatch*>(ptr));

	long ret;
	HRESULT hr = p->get_length(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLAreasCollection_00024Native_get_1_1newEnum
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLAreasCollectionPtr p(reinterpret_cast<IDispatch*>(ptr));

	IUnknown* ret;
	HRESULT hr = p->get__newEnum(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIUnknown(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLAreasCollection_00024Native_item
  (JNIEnv * env, jclass, jlong ptr, jobject arg1, jobject arg2)
{
	JNI_TRY();
	MSHTML::IHTMLAreasCollectionPtr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	JVariant _arg2(env, arg2);
	IDispatch* ret;
	HRESULT hr = p->item(_arg1, _arg2, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIDispatch(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLAreasCollection_00024Native_tags
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLAreasCollectionPtr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	IDispatch* ret;
	HRESULT hr = p->tags(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIDispatch(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLAreasCollection_00024Native_add
  (JNIEnv * env, jclass, jlong ptr, jlong arg1, jobject arg2)
{
	JNI_TRY();
	MSHTML::IHTMLAreasCollectionPtr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLElementPtr _arg1(reinterpret_cast<IDispatch*>(arg1));
	JVariant _arg2(env, arg2);
	HRESULT hr = p->add(_arg1, _arg2);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLAreasCollection_00024Native_remove
  (JNIEnv * env, jclass, jlong ptr, jint arg1)
{
	JNI_TRY();
	MSHTML::IHTMLAreasCollectionPtr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->remove(arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLAreasCollection_00024Native_urns
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLAreasCollection2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	IDispatch* ret;
	HRESULT hr = p->urns(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIDispatch(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLAreasCollection_00024Native_namedItem
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLAreasCollection3Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	IDispatch* ret;
	HRESULT hr = p->namedItem(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIDispatch(env, ret);
	JNI_END(return 0);
}

