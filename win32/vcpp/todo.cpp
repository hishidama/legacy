
class WebBrowserEvents : public SHDocVw::DWebBrowserEvents2
{
	    HRESULT DocumentComplete (
        IDispatch * pDisp,
        VARIANT * URL )
		{
			MessageBox(NULL, _T("Document Complete"), _T(""), MB_OK);
		}

};
_COM_SMARTPTR_TYPEDEF(WebBrowserEvents, __uuidof(SHDocVw::DWebBrowserEvents2));

class WebBrowserPtr :public DeletePtr
{
public:
	SHDocVw::IWebBrowser2Ptr pIE;
	//WebBrowserEventsPtr e;
	SHDocVw::DWebBrowserEvents2Ptr e;
	DWORD dw;

	WebBrowserPtr(SHDocVw::IWebBrowser2Ptr p)
	{
		pIE = p;

		e.co
		HRESULT hr = e.CreateInstance(__uuidof(SHDocVw::DWebBrowserEvents2));
		if(hr!=S_OK) {
			TCHAR str[256];
			wsprintf(str, L"%x\n",hr);
			MessageBox(NULL,str,_T("NG"),MB_OK);
		}
		Advise(pIE, e, __uuidof(SHDocVw::DWebBrowserEvents2), &dw);
	}

	virtual ~WebBrowserPtr()
	{
		Unadvise(pIE, __uuidof(SHDocVw::DWebBrowserEvents2), dw);
		e = NULL;
		pIE = NULL;
	}
};



#if 0
JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_shdocvw_IWebBrowser_findWebBrowser
  (JNIEnv *env, jclass, jlong hwndj)
{
	HWND hwnd = (HWND)hwndj;

	// WebBrowseràÍóóéÊìæ
	SHDocVw::IShellWindowsPtr pWins;
	HRESULT hr = pWins.CreateInstance(__uuidof(SHDocVw::ShellWindows));
	if(FAILED(hr)){
//		TCHAR str[256];
//		wsprintf(str, _T("ShellWindows#CreateInstance() error:%x"), hr);
//		::OutputDebugString(str);
		return NULL;
	}

	// íTçı
	for(long i = 0; i < pWins->Count; i++) {
		IDispatchPtr p = pWins->Item(i);
		SHDocVw::IWebBrowser2Ptr pIE = p;
		if (pIE != NULL){
			long hwnd;
			HRESULT hr = pIE->get_HWND(&hwnd);
			if (SUCCEEDED(hr) && hwnd == hwndj) { //àÍív
				jclass jwebb = env->FindClass(JAVA_WebBrowser_CLASSNAME);
				if (jwebb==NULL) return NULL;
				jmethodID webc = env->GetMethodID(jwebb, "<init>", "(J)V");
				if (webc==NULL) return NULL;
				WebBrowserPtr *wbp = new WebBrowserPtr(pIE);
				jlong ptr = (jlong)wbp;
				jobject webj = env->NewObject(jwebb,webc, ptr);

				if(0) {//test+++---
					extern HRESULT AdviseIE(SHDocVw::IWebBrowser2Ptr pIE);
					AdviseIE(pIE);
					pIE->GoForward();
					extern HRESULT UnadviseIE(SHDocVw::IWebBrowser2Ptr pIE);
					UnadviseIE(pIE);
				}
				return webj;
			}
		}
	}

	return NULL;
}
#endif
