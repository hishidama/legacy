#pragma once

#include <exdisp.h>
#include <mshtml.h>

#pragma warning(disable:4192)
#import <shdocvw.dll> auto_rename raw_interfaces_only
#import <mshtml.tlb> auto_rename raw_interfaces_only
#pragma warning(default:4192)



#include <jni.h>
#include "jni_util.h"
#include "JVariant.h"

jmethodID getListAddID(JNIEnv *env, jobject listj);
BOOL ListAdd(JNIEnv *env, jobject listj, jmethodID add, jobject obj);
BOOL ListAdd(JNIEnv *env, jobject listj, jobject obj);

#define	DEF_NewObjectJLong(name,clsj,cons) \
	jclass clsj = env->FindClass(name);\
	jmethodID cons = NULL;\
	if (clsj!=NULL) cons = env->GetMethodID(clsj, "<init>", "(J)V")
#define DEL_LocalRef(clsj) \
	env->DeleteLocalRef(clsj)

jobject NewObject(JNIEnv *env, jclass clsj, jmethodID cons, IUnknown *ptr);
jobject NewObject(JNIEnv *env, const char *className, IUnknown *ptr);


#define DEF_New0(PTR,name) \
jobject New##name(JNIEnv *env, MSHTML::PTR *p);
#define	DEF_New_(name)	DEF_New0(name,name)
#define	DEF_New2(name)	DEF_New0(name##2,name)

DEF_New_(IHTMLAreasCollection)
DEF_New2(IHTMLDocument)
DEF_New_(IHTMLElementCollection)
DEF_New_(IHTMLFormElement)
DEF_New2(IHTMLFramesCollection)
DEF_New_(IHTMLLocation)
DEF_New_(IHTMLOptionElement)
DEF_New_(IHTMLTableCaption)
DEF_New_(IHTMLTableSection)
DEF_New_(IHTMLTxtRange)
DEF_New2(IHTMLWindow)

#undef	DEF_New_
#undef	DEF_New2
#undef	DEF_New0

jobject NewIUnknown(JNIEnv *env, IUnknown *p);
jobject NewIDispatch(JNIEnv *env, IDispatch *p);
jobject NewIHTMLElement(JNIEnv *env, IDispatch *p);
jobject NewIWebBrowser(JNIEnv *env, SHDocVw::IWebBrowser2 *p);

inline jlong IUnknownToJLong(IUnknown *unknown)
{
	if (unknown == NULL) return 0;
	unknown->AddRef();
	return reinterpret_cast<jlong>(unknown);
}

inline IDispatch* JObjectToIDispatch(JNIEnv *env, jobject obj)
{
	if (obj == NULL) return NULL;
	jfieldID fid = env->GetFieldID(env->GetObjectClass(obj), "ptr", "J");
	jlong ptr = env->GetLongField(obj, fid);
	return reinterpret_cast<IDispatch*>(ptr);
}
