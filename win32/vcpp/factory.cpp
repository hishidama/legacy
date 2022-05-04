#include <windows.h>
#include <tchar.h>

#include "import.h"
#include "jp_hishidama_win32_mshtml_IHTMLOptionElement_Native.h"

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLOptionElement_00024Native_create
  (JNIEnv *env, jclass, jlong ptr, jstring arg1, jstring arg2, jboolean arg3, jboolean arg4)
{
	JNI_TRY();
	MSHTML::IHTMLOptionElementFactoryPtr p;
//x	HRESULT hr = p.CreateInstance(__uuidof(MSHTML::IHTMLOptionElementFactory));
//x	HRESULT hr = p.CreateInstance(__uuidof(MSHTML::HTMLOptionElementFactory));
//x	HRESULT hr = p.CreateInstance(__uuidof(MSHTML::IHTMLOptionElement));
//x	HRESULT hr = p.CreateInstance(__uuidof(MSHTML::DispHTMLOptionElement));
//	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }

	MSHTML::IHTMLWindow2Ptr w(reinterpret_cast<IDispatch*>(ptr));
	HRESULT hr = w->get_Option(&p);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }

	JString _arg1(env, arg1);
	JString _arg2(env, arg2);
	JBoolean _arg3(arg3);
	JBoolean _arg4(arg4);
	MSHTML::IHTMLOptionElement* ret;
	hr = p->create(_arg1, _arg2, _arg3, _arg4, &ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIHTMLOptionElement(env, ret);
	JNI_END(return 0);
}
