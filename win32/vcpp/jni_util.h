
#pragma once

#include <comutil.h>
#include <eh.h>
#include <jni.h>


void ThrowNewRuntimeException(JNIEnv *env, const char *msg);
void ThrowNativeException(JNIEnv *env, _EXCEPTION_POINTERS *ep);
void ThrowHResultException(JNIEnv *env, HRESULT hr);
void ThrowNew(JNIEnv *env, const char *className, jint code);


void se_translator_function(unsigned int code, _EXCEPTION_POINTERS* ep);
#define	JNI_TRY()	\
	_set_se_translator(se_translator_function);\
	try {
#define	JNI_END(RET)	\
	} catch(_EXCEPTION_POINTERS *ep) {\
		ThrowNativeException(env, ep);\
		RET;\
	}



#ifdef UNICODE
LPTSTR JStringToTSTR(JNIEnv *env, jstring strj);
void ReleaseJStringTSTR(LPTSTR str);

BSTR JStringToBSTR(JNIEnv *env, jstring strj);
void ReleaseJStringBSTR(BSTR bstr);
jstring BSTRToJString(JNIEnv *env, BSTR bstr);

#endif

#ifndef UNICODE
jstring NewStringMS932(JNIEnv *env, const char *sjis);
#endif


#define JLongToHWND(j) (HWND)LongToHandle(j)
#define HandleToJLong(h) (jlong)(h)

class JBoolean
{
	jboolean m_bool;
	VARIANT m_var;
public:
	JBoolean(jboolean b) {
		m_bool = b;
	}
	operator VARIANT&() {
		V_VT(&m_var) = VT_BOOL;
		V_BOOL(&m_var) = m_bool;
		return m_var;
	}
};

class JInt
{
	jint m_int;
	VARIANT m_var;
public:
	JInt(jint val) {
		m_int = val;
	}
	operator long() const {
		return m_int;
	}
	operator VARIANT*() {
		V_VT(&m_var) = VT_I4;
		V_I4(&m_var) = m_int;
		return &m_var;
	}
};

class JString
{
	JNIEnv *m_env;
	jstring m_strj;
	BSTR m_bstr;
	VARIANT m_var;
	SAFEARRAY *m_sfa;
public:
	JString() {
		init(NULL, NULL);
	}
	JString(JNIEnv *env, jstring strj) {
		init(env, strj);
	}
	void init(JNIEnv *env, jstring strj) {
		m_env  = env;
		m_strj = strj;
		m_bstr = NULL;
		m_sfa  = NULL;
	}
	~JString() {
		dispose();
	}

	BSTR getBSTR() {
		if (m_strj == NULL) return NULL;
		if (m_bstr == NULL) {
			m_bstr = JStringToBSTR(m_env, m_strj);
		}
		return m_bstr;
	}

	operator BSTR() {
		return getBSTR();
	}

	operator VARIANT&() {
		if (m_strj == NULL) {
			return vtMissing;
		}
		V_VT(&m_var) = VT_BSTR;
		V_BSTR(&m_var) = getBSTR();
		return m_var;
	}

	operator VARIANT*() {
		if (m_strj == NULL) {
			return &vtMissing;
		}
		V_VT(&m_var) = VT_BSTR;
		V_BSTR(&m_var) = getBSTR();
		return &m_var;
	}

	operator SAFEARRAY*() {
		return getSafeArray();
	}

	SAFEARRAY* getSafeArray();
protected:
	void dispose();
};
