#include <windows.h>
#include "import.h"
#include "JVariant.h"

jlong getInt(JNIEnv *env, jclass cls, jobject obj)
{
	jfieldID id = env->GetFieldID(cls, "num", "J");
	return env->GetIntField(obj, id);
}

jstring getStr(JNIEnv *env, jclass cls, jobject obj)
{
	jfieldID id = env->GetFieldID(cls, "str", "Ljava/lang/String;");
	return (jstring)env->GetObjectField(obj, id);
}

void* getCom(JNIEnv *env, jclass cls, jobject obj)
{
	jfieldID id = env->GetFieldID(cls, "com", "Ljp/hishidama/win32/com/ComPtr;");
	jobject com = env->GetObjectField(obj, id);
	if (com == NULL) {
		return NULL;
	}

	jclass c = env->GetObjectClass(com);
	jfieldID i = env->GetFieldID(c, "ptr", "J");
	return (void*)env->GetLongField(com, i);
}

void JVariant::convertVariant(jobject varj)
{
	jclass cls = m_env->GetObjectClass(varj);
	jfieldID id = m_env->GetFieldID(cls, "vt", "I");
	int vt = m_env->GetIntField(varj, id);

	V_VT(&m_var) = vt;
	switch (vt) {
#define	CASE_GET_INT(t,c) \
		case VT_##t:\
			V_##t(&m_var) = (c)getInt(m_env, cls, varj);\
			break
#define	CASE_GET_STR(t) \
		case VT_##t:\
			m_bstr = JStringToBSTR(m_env, getStr(m_env, cls, varj));\
			V_##t(&m_var) = m_bstr;\
			break
#define	CASE_GET_COM(t,c) \
		case VT_##t:\
			V_##t(&m_var) = (c*)getCom(m_env, cls, varj);\
			break
		case VT_NULL:
			V_NONE(&m_var) = 0;
			break;
		CASE_GET_INT(I2, SHORT);
		CASE_GET_INT(I4, jint);
		CASE_GET_INT(I8, jlong);
		CASE_GET_STR(BSTR);
		CASE_GET_COM(DISPATCH, IDispatch);
		CASE_GET_INT(BOOL, VARIANT_BOOL);
		CASE_GET_COM(UNKNOWN, IUnknown);
		default:
			char buf[256];
			sprintf_s(buf, sizeof(buf), "not yet implements convert-VARIANT VT=%d", vt);
			ThrowNewRuntimeException(m_env, buf);
			break;
	}
}

void setInt(JNIEnv *env, jclass cls, jobject obj, jlong val)
{
	jfieldID id = env->GetFieldID(cls, "num", "J");
	env->SetLongField(obj, id, val);
}

void setStr(JNIEnv *env, jclass cls, jobject obj, BSTR bstr)
{
	jfieldID id = env->GetFieldID(cls, "str", "Ljava/lang/String;");
	jstring strj = BSTRToJString(env, bstr);
	env->SetObjectField(obj, id, strj);
}

void setCom(JNIEnv *env, jclass cls, jobject obj, IUnknown* p)
{
	jobject com = NewIUnknown(env, p);
	jfieldID id = env->GetFieldID(cls, "com", "Ljp/hishidama/win32/com/ComPtr;");
	env->SetObjectField(obj, id, com);
}

void JVariant::createjVariant(const VARIANT *var)
{
	jclass cls = m_env->FindClass("Ljp/hishidama/win32/com/Variant;");
	jmethodID cns = m_env->GetMethodID(cls, "<init>", "()V");
	jobject varj = m_env->NewObject(cls, cns);

	jfieldID vid = m_env->GetFieldID(cls, "vt", "I");
	m_env->SetIntField(varj, vid, V_VT(var));
	switch(V_VT(var)){
#define CASE_SET_INT(t) \
		case VT_##t:\
			setInt(m_env, cls, varj, V_##t(var));\
			break
#define CASE_SET_STR(t) \
		case VT_##t:\
			setStr(m_env, cls, varj, V_##t(var));\
			break
#define CASE_SET_COM(t) \
		case VT_##t:\
			setCom(m_env, cls, varj, V_##t(var));\
			break
		case VT_NULL:
			break;
		CASE_SET_INT(I2);
		CASE_SET_INT(I4);
		CASE_SET_INT(I8);
		CASE_SET_STR(BSTR);
		CASE_SET_COM(DISPATCH);
		CASE_SET_INT(BOOL);
		CASE_SET_COM(UNKNOWN);
		default:
			char buf[256];
			sprintf_s(buf, sizeof(buf), "not yet implements create-Varint VT=%d", V_VT(var));
			ThrowNewRuntimeException(m_env, buf);
			break;
	}

	m_varj = varj;
}

JVariant::~JVariant()
{
	if (m_bstr != NULL) {
		ReleaseJStringBSTR(m_bstr);
		m_bstr = NULL;
	}
}
