#include <windows.h>
#include <tchar.h>

#include <oleacc.h>	//ObjectFromLresult
#pragma comment(lib, "oleacc.lib")

#include <shlguid.h>	//SID_STopLevelBrowser,SID_SWebBrowserApp

#include "import.h"
#include "jp_hishidama_win32_mshtml_IHTMLDocument_Native.h"

static const UINT WM_HTML_GETOBJECT = ::RegisterWindowMessage(_T("WM_HTML_GETOBJECT"));

// HTMLドキュメント探索
static BOOL CALLBACK FindHtmlDocumentProc(
  HWND hwnd,    // 親ウィンドウのウィンドウハンドル
  LPARAM lParam // アプリケーション定義の値：今回はpDocへのポインター
)
{
	LRESULT res = 0;
	::SendMessageTimeout(hwnd, WM_HTML_GETOBJECT, 0, 0, SMTO_ABORTIFHUNG, 1000, reinterpret_cast<PDWORD_PTR>(&res));
	if (res == 0) {
		// オブジェクトが取得できなかったときは、Internet Explorer_Serverではなかったのだろう
		return TRUE; //探索続行
	}

	MSHTML::IHTMLDocument2Ptr &pDoc = *reinterpret_cast<MSHTML::IHTMLDocument2Ptr*>(lParam);
	HRESULT hr = ::ObjectFromLresult(res, __uuidof(MSHTML::IHTMLDocument2), 0, reinterpret_cast<void**>(&pDoc));
	if (SUCCEEDED(hr)) {
		return FALSE; //探索終了
	}

	return TRUE; //探索続行
}

MSHTML::IHTMLDocument2Ptr FindHtmlDocument(HWND hwnd)
{
	MSHTML::IHTMLDocument2Ptr pDoc;
	::EnumChildWindows(hwnd, FindHtmlDocumentProc, reinterpret_cast<LPARAM>(&pDoc));
	return pDoc;
}

HRESULT FindWebBrowser(MSHTML::IHTMLDocument2Ptr& pDoc, SHDocVw::IWebBrowser2Ptr& pIE)
{
	HRESULT hr = E_INVALIDARG;
	IServiceProviderPtr sp1(pDoc);
	if (sp1 != NULL) {
		IServiceProviderPtr sp2;
		hr = sp1->QueryService(SID_STopLevelBrowser, IID_IServiceProvider, reinterpret_cast<void**>(&sp2));
		if (sp2 != NULL) {
			hr = sp2->QueryService(SID_SWebBrowserApp, __uuidof(SHDocVw::IWebBrowser2), reinterpret_cast<void**>(&pIE));
		}
	}
	return hr;
}

JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLDocument_00024Native_getWebBrowser
  (JNIEnv *env, jclass, jlong ptr)
{
	JNI_TRY();
	MSHTML::IHTMLDocument2Ptr p(reinterpret_cast<IDispatch*>(ptr));

	SHDocVw::IWebBrowser2Ptr ret;
	HRESULT hr = FindWebBrowser(p, ret);
	if (FAILED(hr)) { ThrowHResultException(env, hr); return 0; }
	return NewIWebBrowser(env, ret);
	JNI_END(return 0);
}
