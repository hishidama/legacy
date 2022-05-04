#pragma once
#include <oaidl.h>
#include <jni.h>

class JVariant 
{
protected:
	JNIEnv *m_env;
	VARIANT m_var;
	jobject m_varj;

	BSTR m_bstr; //デストラクターで破棄する

public:
	JVariant(JNIEnv *env, const VARIANT& var) {
		m_env = env;
		m_var = var;
		m_varj = NULL;
		m_bstr = NULL;
	}
	JVariant(JNIEnv *env, jobject varj) { //Variant
		m_env = env;
		convertVariant(varj);
		m_varj = varj;
		m_bstr = NULL;
	}
	virtual ~JVariant();

	operator jobject() { //Variant
		if (m_varj == NULL) {
			createjVariant(&m_var);
		}
		return m_varj;
	}
	operator VARIANT&() {
		return m_var;
	}
	operator VARIANT*() {
		return &m_var;
	}

protected:
	void convertVariant(jobject varj); //Variant
	void createjVariant(const VARIANT* var);
};
