#include <windows.h>
#include <tchar.h>

#include <oleacc.h> //ObjectFromLresult
#pragma comment(lib, "oleacc.lib")

#include <shlguid.h> //SID

#include "import.h"
#include "jp_hishidama_win32_shdocvw_IWebBrowser_Native.h"


JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_create
  (JNIEnv *env, jclass)
{
	JNI_TRY();
	SHDocVw::IWebBrowser2Ptr pIE;
	HRESULT hr = pIE.CreateInstance(__uuidof(SHDocVw::InternetExplorer));
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }

	return IUnknownToJLong(pIE);
	JNI_END(return 0);
}

JNIEXPORT void JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_enumWebBrowser
  (JNIEnv *env, jclass, jobject listj)
{
	JNI_TRY();
	// Javaオブジェクト準備
	jmethodID add = getListAddID(env, listj);
	if (add==NULL) return;

	DEF_NewObjectJLong("Ljp/hishidama/win32/shdocvw/IWebBrowser;",jwebb,webc);
	if (webc==NULL) return;

	// WebBrowser一覧取得
	SHDocVw::IShellWindowsPtr pWins;
	HRESULT hr = pWins.CreateInstance(__uuidof(SHDocVw::ShellWindows));
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }

	// リストへ保存
	long count;
	hr = pWins->get_Count(&count);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return; }

	for(long i = 0; i < count; i++) {
		_variant_t var(i);
		IDispatch *p = NULL;
		hr = pWins->Item(var, &p);
		if (FAILED(hr)) { ThrowHResultException(env, hr); return; }

		SHDocVw::IWebBrowser2Ptr pIE(p);
		if (pIE != NULL){
			jobject obj = NewObject(env, jwebb,webc, pIE);
			ListAdd(env, listj, add, obj);
		}
	}
	JNI_END(return);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_00024Native_findWebBrowser
  (JNIEnv *env, jclass, jlong hwndj)
{
	JNI_TRY();
	HWND hwnd = (HWND)hwndj;

	extern	MSHTML::IHTMLDocument2Ptr FindHtmlDocument(HWND hwnd);
	MSHTML::IHTMLDocument2Ptr pDoc = FindHtmlDocument(hwnd);
	if (pDoc == NULL) {
		return 0;
	}

	extern	HRESULT FindWebBrowser(MSHTML::IHTMLDocument2Ptr& pDoc, SHDocVw::IWebBrowser2Ptr& pIE);
	SHDocVw::IWebBrowser2Ptr pIE;
	HRESULT hr = FindWebBrowser(pDoc, pIE);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }

	return IUnknownToJLong(pIE);
	JNI_END(return 0);
}
