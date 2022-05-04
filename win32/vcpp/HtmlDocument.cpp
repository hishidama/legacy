#include <windows.h>
#include <tchar.h>

#include "import.h"
#include "jp_hishidama_win32_mshtml_IHTMLDocument_Native.h"

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1Script
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocumentPtr p(reinterpret_cast<IDispatch*>(ptr));

	IDispatch* ret;
	HRESULT hr = p->get_Script(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIDispatch(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1all
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLElementCollection* ret;
	HRESULT hr = p->get_all(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLElementCollection(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1body
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLElement* ret;
	HRESULT hr = p->get_body(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLElement(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1activeElement
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLElement* ret;
	HRESULT hr = p->get_activeElement(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLElement(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1images
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLElementCollection* ret;
	HRESULT hr = p->get_images(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLElementCollection(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1links
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLElementCollection* ret;
	HRESULT hr = p->get_links(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLElementCollection(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1forms
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLElementCollection* ret;
	HRESULT hr = p->get_forms(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLElementCollection(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1anchors
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLElementCollection* ret;
	HRESULT hr = p->get_anchors(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLElementCollection(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_put_1title
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_title(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1title
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_title(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1readyState
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_readyState(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1frames
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLFramesCollection2* ret;
	HRESULT hr = p->get_frames(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLFramesCollection(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_put_1alinkColor
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_alinkColor(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1alinkColor
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_alinkColor(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_put_1bgColor
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_bgColor(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1bgColor
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_bgColor(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_put_1fgColor
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_fgColor(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1fgColor
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_fgColor(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_put_1linkColor
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_linkColor(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1linkColor
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_linkColor(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_put_1vlinkColor
  (JNIEnv * env, jclass, jlong ptr, jobject arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JVariant _arg1(env, arg1);
	HRESULT hr = p->put_vlinkColor(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1vlinkColor
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT ret;
	HRESULT hr = p->get_vlinkColor(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1referrer
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_referrer(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1location
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLLocation* ret;
	HRESULT hr = p->get_location(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLLocation(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1lastModified
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_lastModified(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_put_1url
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_url(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1url
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_url(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_put_1domain
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_domain(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1domain
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_domain(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_put_1cookie
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_cookie(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1cookie
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_cookie(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_put_1charset
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_charset(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1charset
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_charset(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_put_1defaultCharset
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_defaultCharset(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1defaultCharset
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_defaultCharset(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1mimeType
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_mimeType(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1fileSize
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_fileSize(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1fileCreatedDate
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_fileCreatedDate(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1fileModifiedDate
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_fileModifiedDate(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1fileUpdatedDate
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_fileUpdatedDate(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1security
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_security(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1protocol
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_protocol(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1nameProp
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_nameProp(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_write
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->write(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_writeln
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->writeln(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_open
  (JNIEnv * env, jclass, jlong ptr, jstring arg1, jobject arg2, jobject arg3, jobject arg4)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	JVariant _arg2(env, arg2);
	JVariant _arg3(env, arg3);
	JVariant _arg4(env, arg4);
	IDispatch* ret;
	HRESULT hr = p->open(_arg1, _arg2, _arg3, _arg4, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIDispatch(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_close
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->close();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_clear
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->clear();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_queryCommandSupported
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	VARIANT_BOOL ret;
	HRESULT hr = p->queryCommandSupported(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_queryCommandEnabled
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	VARIANT_BOOL ret;
	HRESULT hr = p->queryCommandEnabled(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_queryCommandState
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	VARIANT_BOOL ret;
	HRESULT hr = p->queryCommandState(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_queryCommandIndeterm
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	VARIANT_BOOL ret;
	HRESULT hr = p->queryCommandIndeterm(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_queryCommandText
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	BSTR ret;
	HRESULT hr = p->queryCommandText(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_queryCommandValue
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	VARIANT ret;
	HRESULT hr = p->queryCommandValue(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return JVariant(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_execCommand
  (JNIEnv * env, jclass, jlong ptr, jstring arg1, jboolean arg2, jobject arg3)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	JVariant _arg3(env, arg3);
	VARIANT_BOOL ret;
	HRESULT hr = p->execCommand(_arg1, arg2, _arg3, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_createElement
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	MSHTML::IHTMLElement* ret;
	HRESULT hr = p->createElement(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLElement(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_elementFromPoint
  (JNIEnv * env, jclass, jlong ptr, jint arg1, jint arg2)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLElement* ret;
	HRESULT hr = p->elementFromPoint(arg1, arg2, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLElement(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1parentWindow
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	MSHTML::IHTMLWindow2* ret;
	HRESULT hr = p->get_parentWindow(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLWindow(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_put_1baseUrl
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument3Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	HRESULT hr = p->put_baseUrl(_arg1);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_get_1baseUrl
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument3Ptr p(reinterpret_cast<IDispatch*>(ptr));

	BSTR ret;
	HRESULT hr = p->get_baseUrl(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return BSTRToJString(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_getElementsByName
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument3Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	MSHTML::IHTMLElementCollection* ret;
	HRESULT hr = p->getElementsByName(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLElementCollection(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_getElementById
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument3Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	MSHTML::IHTMLElement* ret;
	HRESULT hr = p->getElementById(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLElement(env, ret);
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_getElementsByTagName
  (JNIEnv * env, jclass, jlong ptr, jstring arg1)
{
	JNI_TRY();
	MSHTML::IHTMLDocument3Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	MSHTML::IHTMLElementCollection* ret;
	HRESULT hr = p->getElementsByTagName(_arg1, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLElementCollection(env, ret);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_focus
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument4Ptr p(reinterpret_cast<IDispatch*>(ptr));

	HRESULT hr = p->focus();
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }
	JNI_END(return);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_hasFocus
  (JNIEnv * env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument4Ptr p(reinterpret_cast<IDispatch*>(ptr));

	VARIANT_BOOL ret;
	HRESULT hr = p->hasFocus(&ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return (jboolean)ret;
	JNI_END(return 0);
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_createDocumentFromUrl
  (JNIEnv * env, jclass, jlong ptr, jstring arg1, jstring arg2)
{
	JNI_TRY();
	MSHTML::IHTMLDocument4Ptr p(reinterpret_cast<IDispatch*>(ptr));

	JString _arg1(env, arg1);
	JString _arg2(env, arg2);
	MSHTML::IHTMLDocument2* ret;
	HRESULT hr = p->createDocumentFromUrl(_arg1, _arg2, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLDocument(env, ret);
	JNI_END(return 0);
}

