#include <windows.h>
#include "import.h"
#include "jp_hishidama_win32_com_ComMgr.h"

JNIEXPORT void JNICALL Java_jp_hishidama_win32_com_ComMgr_delete
  (JNIEnv *env, jclass, jlong ptr)
{
	JNI_TRY();
	IUnknown *p = reinterpret_cast<IUnknown*>(ptr);
	p->Release();
	JNI_END(return);
}

//
//
//

jmethodID getListAddID(JNIEnv *env, jobject listj)
{
	jclass jlist = env->GetObjectClass(listj);
	if (jlist==NULL) return NULL;

	jmethodID add = env->GetMethodID(jlist, "add", "(Ljava/lang/Object;)Z");

	env->DeleteLocalRef(jlist);
	return add;
}

BOOL ListAdd(JNIEnv *env, jobject listj, jmethodID add, jobject obj)
{
	if (add==NULL) return FALSE;

	env->CallVoidMethod(listj, add, obj);
	return TRUE;
}

BOOL ListAdd(JNIEnv *env, jobject listj, jobject obj)
{
	jmethodID add = getListAddID(env, listj);
	if (add==NULL) return FALSE;

	BOOL ret = ListAdd(env, listj, add, obj);

	return ret;
}

jobject NewObject(JNIEnv *env, jclass clsj, jmethodID cons, IUnknown *ptr)
{
	if (cons==NULL) return NULL;
	if (ptr ==NULL) return NULL;

	return env->NewObject(clsj ,cons, IUnknownToJLong(ptr));
}

jobject NewObject(JNIEnv *env, const char *className, IUnknown *ptr)
{
	if (ptr==NULL) return NULL;

	DEF_NewObjectJLong(className,clsj, cons);
	jobject ret = NewObject(env, clsj, cons, ptr);
	DEL_LocalRef(clsj);
	return ret;
}
