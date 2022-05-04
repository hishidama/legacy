#include <windows.h>
#include "jni_util.h"
#include "jp_hishidama_win32_api_WinBase.h"

void SaveLastError(JNIEnv *env, DWORD err)
{
	if (err == 0) {
		return;
	}

	ThrowNew(env, "Ljp/hishidama/win32/api/Win32Exception;", err);
}

void SaveLastError(JNIEnv *env)
{
	SaveLastError(env, ::GetLastError());
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_api_WinBase_GetLastError
  (JNIEnv *env, jclass)
{
	JNI_TRY();
	return ::GetLastError();
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_api_WinBase_FormatMessage
  (JNIEnv *env, jclass, jint errorj)
{
	JNI_TRY();
	LPVOID lpMsgBuf;
	FormatMessage(
		FORMAT_MESSAGE_ALLOCATE_BUFFER |
		FORMAT_MESSAGE_FROM_SYSTEM |
		FORMAT_MESSAGE_IGNORE_INSERTS,
		NULL,
		errorj,
		MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT),
		(LPWSTR)&lpMsgBuf,
		0,
		NULL
	);
#ifdef UNICODE
	jstring strj = env->NewString((const jchar*)lpMsgBuf, lstrlen((LPCWSTR)lpMsgBuf));
#else
	//jstring strj = env->NewStringUTF((const char*)lpMsgBuf);
	jstring strj = NewStringMS932(env, (const char*)lpMsgBuf);
#endif
	LocalFree(lpMsgBuf);

	return strj;
	JNI_END(return 0);
}
