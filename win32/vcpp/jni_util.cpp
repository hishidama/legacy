#include <windows.h>
#include "jni_util.h"


//
//例外
//

void se_translator_function(unsigned int code, _EXCEPTION_POINTERS* ep)
{
	throw ep; //標準C++の例外を発生させる
}

void ThrowNativeException(JNIEnv *env, _EXCEPTION_POINTERS *ep)
{
	PEXCEPTION_RECORD rec = ep->ExceptionRecord;
	DWORD code = rec->ExceptionCode;
	DWORD flag = rec->ExceptionFlags;
	void* addr = rec->ExceptionAddress;
	DWORD nums = rec->NumberParameters;
	jlongArray arrj = env->NewLongArray(nums);
	jlong* lng = env->GetLongArrayElements(arrj, NULL);
	for (DWORD i = 0; i < nums; i++) {
		lng[i] = rec->ExceptionInformation[i];
	}
	env->ReleaseLongArrayElements(arrj, lng, 0);

	jclass clsj = env->FindClass("Ljp/hishidama/win32/NativeException;");
	jmethodID cons = env->GetMethodID(clsj, "<init>", "(IIJ[J)V");
	jthrowable ej = (jthrowable)env->NewObject(clsj, cons, code, flag, (jlong)addr, arrj);

	jint r = env->Throw(ej);

//	TCHAR buf[1024];
//	wsprintf(buf, TEXT("class:%p cons:%p e:%p r:%d\naddr:%p num:%d"), clsj, cons, ej, r, addr, nums);
//	::MessageBox(NULL, buf,TEXT("FilterNativeException"),MB_OK);
}

//throw new RuntimeException(msg);
void ThrowNewRuntimeException(JNIEnv *env, const char *msg)
{
	jclass clsj = env->FindClass("Ljava/lang/RuntimeException;");
	if (clsj==NULL) return;

	env->ThrowNew(clsj, msg);
}

void ThrowHResultException(JNIEnv *env, HRESULT hr)
{
	ThrowNew(env, "Ljp/hishidama/win32/com/HResultException;", hr);
}

//throw new className((int)code);
void ThrowNew(JNIEnv *env, const char *className, jint code)
{
	jclass clsj = env->FindClass(className);
	if (clsj==NULL) return;

	jmethodID cnsj = env->GetMethodID(clsj, "<init>", "(I)V");
	if (cnsj==NULL) return;

	jobject ej = env->NewObject(clsj, cnsj, code);
	if (ej==NULL) return;

	env->Throw((jthrowable)ej);
}


#ifdef	UNICODE

LPTSTR JStringToTSTR(JNIEnv *env, jstring strj)
{
	if (strj == NULL) {
		return NULL;
	}

	int jct = env->GetStringLength(strj);
	int len = jct * sizeof(jchar);
	int wct = len / sizeof(TCHAR);
	LPTSTR str = new TCHAR[wct + 1];

	const jchar *csj = env->GetStringChars(strj, NULL);

	memcpy(str, csj, len);
	str[wct] = '\0';

	env->ReleaseStringChars(strj, csj);

	return str;
}
void ReleaseJStringTSTR(LPTSTR str)
{
	delete[] (TCHAR*)str;
}

BSTR JStringToBSTR(JNIEnv *env, jstring strj)
{
	const jchar* csj  = env->GetStringChars(strj, NULL);
	int len = env->GetStringLength(strj);
	BSTR bstr = SysAllocStringLen((const OLECHAR *)csj, len);
	env->ReleaseStringChars(strj, csj);

	return bstr;
}
void ReleaseJStringBSTR(BSTR bstr)
{
	SysFreeString(bstr);
}

jstring BSTRToJString(JNIEnv *env, BSTR bstr)
{
	if(bstr==NULL) return NULL;

	jstring strj = env->NewString((const jchar *)bstr, SysStringLen(bstr));
	SysFreeString(bstr);

	return strj;
}

#endif

#ifndef UNICODE

/*
	SJIS文字列から、新しいStringを生成する。
*/
jstring NewStringMS932(JNIEnv *env, const char *sjis)
{
	if (sjis==NULL) return NULL;

	jthrowable ej = env->ExceptionOccurred();
	if (ej!=NULL) env->ExceptionClear(); //発生中の例外をクリア

	jbyteArray arrj = NULL;
	jstring encj    = NULL;
	jclass clsj     = NULL;
	jmethodID mj    = NULL;
	jstring strj    = NULL;

	int len = (int)strlen(sjis);
	arrj = env->NewByteArray(len);
	if (arrj==NULL) goto END;
	env->SetByteArrayRegion(arrj, 0, len, (jbyte*)sjis);

	encj = env->NewStringUTF("MS932");
	if (encj==NULL) goto END;

	clsj = env->FindClass("Ljava/lang/String;");
	if (clsj==NULL) goto END;
	mj = env->GetMethodID(clsj, "<init>", "([BLjava/lang/String;)V");
	if (mj==NULL) goto END;

	strj = (jstring)env->NewObject(clsj, mj, arrj, encj);
	if (strj==NULL) goto END;

	if (ej!=NULL) env->Throw(ej); //発生していた例外を戻す
END:
	env->DeleteLocalRef(ej);
	env->DeleteLocalRef(encj);
	env->DeleteLocalRef(clsj);
	env->DeleteLocalRef(arrj);

	return strj;
}

#endif


void JString::dispose()
{
	if (m_sfa != NULL) {
		::SafeArrayDestroy(m_sfa);
		m_sfa = NULL;
	}
	if (m_bstr != NULL) {
		ReleaseJStringBSTR(m_bstr);
		m_bstr = NULL;
	}
}

SAFEARRAY* JString::getSafeArray()
{
	SAFEARRAY* sfArray = ::SafeArrayCreateVector(VT_VARIANT, 0, 1);
	if (sfArray == NULL){
		ThrowNewRuntimeException(m_env, "SafeArrayCreateVector failed.");
		return NULL;
	}

	VARIANT* var = NULL;
	HRESULT hr = ::SafeArrayAccessData(sfArray, (void**)&var);
	if (FAILED(hr)) { ThrowHResultException(m_env, hr); goto label; }

	V_VT(var)   = VT_BSTR;
	V_BSTR(var) = getBSTR();

	hr = ::SafeArrayUnaccessData(sfArray);
	if (FAILED(hr)) { ThrowHResultException(m_env, hr); goto label; }

	return sfArray;

label:
	::SafeArrayDestroy(sfArray);
	return NULL;
}
