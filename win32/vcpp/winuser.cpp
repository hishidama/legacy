#include <windows.h>
#include "jni_util.h"
#include "jp_hishidama_win32_api_WinUser.h"

extern void SaveLastError(JNIEnv *env, DWORD err);
extern void SaveLastError(JNIEnv *env);
#define If0SaveLastError(r, env) do{ if (r==0) SaveLastError(env); }while(0)

#define rectfX(clsj) jfieldID rectfIDx = env->GetFieldID(clsj, "x", "I")
#define rectfY(clsj) jfieldID rectfIDy = env->GetFieldID(clsj, "y", "I")
#define rectfW(clsj) jfieldID rectfIDw = env->GetFieldID(clsj, "width", "I")
#define rectfH(clsj) jfieldID rectfIDh = env->GetFieldID(clsj, "height", "I")
#define rectf(clsj) rectfX(clsj);rectfY(clsj);rectfW(clsj);rectfH(clsj)
#define getRectX(obj) (env->GetIntField(obj,rectfIDx))
#define getRectY(obj) (env->GetIntField(obj,rectfIDy))
#define getRectW(obj) (env->GetIntField(obj,rectfIDw))
#define getRectH(obj) (env->GetIntField(obj,rectfIDh))
#define setRectX(obj,val) env->SetIntField(obj,rectfIDx,val)
#define setRectY(obj,val) env->SetIntField(obj,rectfIDy,val)
#define setRectW(obj,val) env->SetIntField(obj,rectfIDw,val)
#define setRectH(obj,val) env->SetIntField(obj,rectfIDh,val)
#define setRect(obj,x,y,w,h) setRectX(obj,x);setRectY(obj,y);setRectW(obj,w);setRectH(obj,h)

#define pointfX(clsj) jfieldID pointfIDx = env->GetFieldID(clsj, "x", "I")
#define pointfY(clsj) jfieldID pointfIDy = env->GetFieldID(clsj, "y", "I")
#define pointf(clsj) pointfX(clsj);pointfY(clsj)
#define getPointX(obj) (env->GetIntField(obj,pointfIDx))
#define getPointY(obj) (env->GetIntField(obj,pointfIDy))
#define setPointX(obj,val) env->SetIntField(obj,pointfIDx,val)
#define setPointY(obj,val) env->SetIntField(obj,pointfIDy,val)
#define setPoint(obj,x,y) setPointX(obj,x);setPointY(obj,y)

class TBUF
{
	TCHAR *p;
public:
	TBUF(int len) {
		p = new TCHAR[len];
	}
	~TBUF() {
		delete[] p;
	}
	operator TCHAR*() {
		return p;
	}
	operator jchar*() {
		return (jchar*)p;
	}
};


JNIEXPORT jint JNICALL Java_jp_hishidama_win32_api_WinUser_SendMessage__JIII
  (JNIEnv *env, jclass, jlong hwndj, jint msg, jint wparam, jint lparam)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	LRESULT lret = ::SendMessage(hwnd, msg, wparam, lparam);
	//SaveLastError(env);
	return (jint)lret;
	JNI_END(return 0);
}
JNIEXPORT jint JNICALL Java_jp_hishidama_win32_api_WinUser_SendMessage__JIILjava_lang_String_2
  (JNIEnv *env, jclass, jlong hwndj, jint msg, jint wparam, jstring strj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	JString lparam(env, strj);
	LRESULT lret = ::SendMessage(hwnd, msg, wparam, (LPARAM)(TCHAR*)lparam);
	//SaveLastError(env);
	return (jint)lret;
	JNI_END(return 0);
}
JNIEXPORT jint JNICALL Java_jp_hishidama_win32_api_WinUser_SendMessage__JII_3B
  (JNIEnv *env, jclass, jlong hwndj, jint msg, jint wparam, jbyteArray arrj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	jbyte* elems = env->GetByteArrayElements(arrj, NULL);
	LRESULT lret = ::SendMessage(hwnd, msg, wparam, (LPARAM)elems);
	//SaveLastError(env);
	env->ReleaseByteArrayElements(arrj, elems, 0);
	return (jint)lret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_PostMessage
  (JNIEnv *env, jclass, jlong hwndj, jint msg, jint wparam, jint lparam)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	BOOL ret = ::PostMessage(hwnd, msg, wparam, lparam);
	//SaveLastError(env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_IsWindow
  (JNIEnv *env, jclass, jlong hwndj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	BOOL ret = ::IsWindow(hwnd);
	//SaveLastError(env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_IsChild
  (JNIEnv *env, jclass, jlong hWndParentj, jlong hwndj)
{
	JNI_TRY();
	HWND hWndParent = JLongToHWND(hWndParentj);
	HWND hwnd = JLongToHWND(hwndj);
	BOOL ret = ::IsChild(hWndParent, hwnd);
	//SaveLastError(env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_ShowWindow
  (JNIEnv *env, jclass, jlong hwndj, jint cmd)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	BOOL ret = ::ShowWindow(hwnd, cmd);
	//SaveLastError(env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_MoveWindow
  (JNIEnv *env, jclass, jlong hwndj, jint x, jint y, jint width, jint height, jboolean repaint)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	BOOL ret = ::MoveWindow(hwnd, x, y, width, height, repaint);
	If0SaveLastError(ret, env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_SetWindowPos
  (JNIEnv *env, jclass, jlong hwndj, jlong hwndInsertAfterj, jint x, jint y, jint cx, jint cy, jint flags)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	HWND hwndInsertAfter = JLongToHWND(hwndInsertAfterj);
	BOOL ret = ::SetWindowPos(hwnd, hwndInsertAfter, x, y, cx, cy, flags);
	If0SaveLastError(ret, env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_IsWindowVisible
  (JNIEnv *env, jclass, jlong hwndj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	BOOL ret = ::IsWindowVisible(hwnd);
	//SaveLastError(env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_IsIconic
  (JNIEnv *env, jclass, jlong hwndj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	BOOL ret = ::IsIconic(hwnd);
	//SaveLastError(env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_IsZoomed
  (JNIEnv *env, jclass, jlong hwndj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	BOOL ret = ::IsZoomed(hwnd);
	//SaveLastError(env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_api_WinUser_GetDlgItem
  (JNIEnv *env, jclass, jlong hdlgj, jint id)
{
	JNI_TRY();
	HWND hdlg = JLongToHWND(hdlgj);
	HWND hret = ::GetDlgItem(hdlg, id);
	If0SaveLastError(hret, env);
	return HandleToJLong(hret);
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_SetDlgItemInt
  (JNIEnv *env, jclass, jlong hdlgj, jint id, jint value, jboolean sign)
{
	JNI_TRY();
	HWND hdlg = JLongToHWND(hdlgj);
	BOOL ret = ::SetDlgItemInt(hdlg, id, value, sign);
	If0SaveLastError(ret, env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_api_WinUser_GetDlgItemInt
  (JNIEnv *env, jclass, jlong hdlgj, jint id, jbooleanArray transj, jboolean sign)
{
	JNI_TRY();
	HWND hdlg = JLongToHWND(hdlgj);
	BOOL trans;
	UINT ret = ::GetDlgItemInt(hdlg, id, &trans, sign);
	If0SaveLastError(ret, env);

	if (transj != NULL) {
		jthrowable ej = env->ExceptionOccurred();
		if (ej != NULL) env->ExceptionClear();

		if (env->GetArrayLength(transj) > 0) {
			jboolean* elems = env->GetBooleanArrayElements(transj, NULL);
			elems[0] = trans;
			env->ReleaseBooleanArrayElements(transj, elems, 0);
		}

		if (ej != NULL) env->Throw(ej);
	}

	return ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_SetDlgItemText
  (JNIEnv *env, jclass, jlong hdlgj, jint id, jstring textj)
{
	JNI_TRY();
	HWND hdlg = JLongToHWND(hdlgj);
	JString text(env, textj);
	BOOL ret = ::SetDlgItemText(hdlg, id, text);
	If0SaveLastError(ret, env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_api_WinUser_GetDlgItemText
  (JNIEnv *env, jclass, jlong hdlgj, jint id, jint len)
{
	JNI_TRY();
	HWND hdlg = JLongToHWND(hdlgj);
	TBUF str(len+1);
	len = ::GetDlgItemText(hdlg, id, str, len+1);
	If0SaveLastError(len, env);

	jstring retj = NULL;
	if (len > 0) {
		retj = env->NewString(str, len);
	}

	return retj;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_CheckDlgButton
  (JNIEnv *env, jclass, jlong hdlgj, jint nIDButton, jint uCheck)
{
	JNI_TRY();
	HWND hdlg = JLongToHWND(hdlgj);
	BOOL ret = ::CheckDlgButton(hdlg, nIDButton, uCheck);
	If0SaveLastError(ret, env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_CheckRadioButton
  (JNIEnv *env, jclass, jlong hdlgj, jint nIDFirstButton, jint nIDLastButton, jint nIDCheckButton)
{
	JNI_TRY();
	HWND hdlg = JLongToHWND(hdlgj);
	BOOL ret = ::CheckRadioButton(hdlg, nIDFirstButton, nIDLastButton, nIDCheckButton);
	If0SaveLastError(ret, env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_api_WinUser_IsDlgButtonChecked
  (JNIEnv *env, jclass, jlong hdlgj, jint nIDButton)
{
	JNI_TRY();
	HWND hdlg = JLongToHWND(hdlgj);
	UINT ret = ::IsDlgButtonChecked(hdlg, nIDButton);
	//SaveLastError(env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_api_WinUser_GetNextDlgGroupItem
  (JNIEnv *env, jclass, jlong hdlgj, jlong hctlj, jboolean previous)
{
	JNI_TRY();
	HWND hdlg = JLongToHWND(hdlgj);
	HWND hctl = JLongToHWND(hctlj);
	HWND hret = ::GetNextDlgGroupItem(hdlg, hctl, previous);
	If0SaveLastError(hret, env);
	return HandleToJLong(hret);
	JNI_END(return 0);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_api_WinUser_GetNextDlgTabItem
  (JNIEnv *env, jclass, jlong hdlgj, jlong hctlj, jboolean previous)
{
	JNI_TRY();
	HWND hdlg = JLongToHWND(hdlgj);
	HWND hctl = JLongToHWND(hctlj);
	HWND hret = ::GetNextDlgTabItem(hdlg, hctl, previous);
	If0SaveLastError(hret, env);
	return HandleToJLong(hret);
	JNI_END(return 0);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_api_WinUser_GetDlgCtrlID
  (JNIEnv *env, jclass, jlong hwndj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	int ret = ::GetDlgCtrlID(hwnd);
	If0SaveLastError(ret, env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_api_WinUser_SetFocus
  (JNIEnv *env, jclass, jlong hwndj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	HWND hret = ::SetFocus(hwnd);
	If0SaveLastError(hret, env);
	return HandleToJLong(hret);
	JNI_END(return 0);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_api_WinUser_GetActiveWindow
  (JNIEnv *env, jclass)
{
	JNI_TRY();
	HWND hret = ::GetActiveWindow();
	//SaveLastError(env);
	return HandleToJLong(hret);
	JNI_END(return 0);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_api_WinUser_GetFocus
  (JNIEnv *env, jclass)
{
	JNI_TRY();
	HWND hret = ::GetFocus();
	//SaveLastError(env);
	return HandleToJLong(hret);
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_EnableWindow
  (JNIEnv *env, jclass, jlong hwndj, jboolean enable)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	BOOL ret = ::EnableWindow(hwnd, enable);
	If0SaveLastError(ret, env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_IsWindowEnabled
  (JNIEnv *env, jclass, jlong hwndj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	BOOL ret = ::IsWindowEnabled(hwnd);
	//SaveLastError(env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_api_WinUser_SetActiveWindow
  (JNIEnv *env, jclass, jlong hwndj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	HWND hret = ::SetActiveWindow(hwnd);
	If0SaveLastError(hret, env);
	return HandleToJLong(hret);
	JNI_END(return 0);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_api_WinUser_GetForegroundWindow
  (JNIEnv *env, jclass)
{
	JNI_TRY();
	HWND hret = ::GetForegroundWindow();
	//SaveLastError(env);
	return HandleToJLong(hret);
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_SetForegroundWindow
  (JNIEnv *env, jclass, jlong hwndj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	//SaveLastError(env);
	return ::SetForegroundWindow(hwnd);
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_ShowScrollBar
  (JNIEnv *env, jclass, jlong hwndj, jint bar, jboolean show)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	BOOL ret = ::ShowScrollBar(hwnd, bar, show);
	If0SaveLastError(ret, env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_EnableScrollBar
  (JNIEnv *env, jclass, jlong hwndj, jint flags, jint arrows)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	BOOL ret = ::EnableScrollBar(hwnd, flags, arrows);
	If0SaveLastError(ret, env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_SetWindowText
  (JNIEnv *env, jclass, jlong hwndj, jstring strj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	JString str(env, strj);
	BOOL ret = ::SetWindowText(hwnd, str);
	If0SaveLastError(ret, env);

	return ret;
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_api_WinUser_getWindowText
  (JNIEnv *env, jclass, jlong hwndj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	int len = ::GetWindowTextLength(hwnd);
	if (len <= 0) {
		DWORD err = ::GetLastError();
		SaveLastError(env, err);
		if (err == 0) {
			return env->NewString((const jchar*)TEXT(""), 0);
		}
		return NULL;
	}

	TBUF str(len+1);
	len = ::GetWindowText(hwnd, str, len+1);
	If0SaveLastError(len, env);
	jstring retj = NULL;
	if (len > 0) {
		retj = env->NewString(str, len);
	}

	return retj;
	JNI_END(return 0);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_api_WinUser_GetWindowTextLength
  (JNIEnv *env, jclass, jlong hwndj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	int ret = ::GetWindowTextLength(hwnd);
	If0SaveLastError(ret, env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_GetClientRect
  (JNIEnv *env, jclass, jlong hwndj, jobject rectj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);

	jclass clsj = env->GetObjectClass(rectj);
	rectf(clsj);
	RECT r;
	r.left   = getRectX(rectj);
	r.top    = getRectY(rectj);
	r.right  = r.left + getRectW(rectj);
	r.bottom = r.top  + getRectH(rectj);

	BOOL ret = ::GetClientRect(hwnd, &r);
	If0SaveLastError(ret, env);

	setRect(rectj, r.left, r.top, r.right-r.left, r.bottom-r.top);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_GetWindowRect
  (JNIEnv *env, jclass, jlong hwndj, jobject rectj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);

	jclass clsj = env->GetObjectClass(rectj);
	rectf(clsj);
	RECT r;
	r.left   = getRectX(rectj);
	r.top    = getRectY(rectj);
	r.right  = r.left + getRectW(rectj);
	r.bottom = r.top  + getRectH(rectj);

	BOOL ret = ::GetWindowRect(hwnd, &r);
	If0SaveLastError(ret, env);

	setRect(rectj, r.left, r.top, r.right-r.left, r.bottom-r.top);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_api_WinUser_MessageBox
  (JNIEnv *env, jclass, jlong hwndj, jstring textj, jstring captionj, jint type)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);

	JString lpText   (env, textj);
	JString lpCaption(env, captionj);

	int ret = ::MessageBox(hwnd, lpText, lpCaption, type);
	If0SaveLastError(ret, env);

	return ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_ClientToScreen
  (JNIEnv *env, jclass, jlong hwndj, jobject pointj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);

	jclass clsj = env->GetObjectClass(pointj);
	pointf(clsj);
	POINT pt;
	pt.x = getPointX(pointj);
	pt.y = getPointY(pointj);

	BOOL ret = ::ClientToScreen(hwnd, &pt);
	If0SaveLastError(ret, env);

	setPoint(pointj, pt.x, pt.y);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_ScreenToClient
  (JNIEnv *env, jclass, jlong hwndj, jobject pointj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);

	jclass clsj = env->GetObjectClass(pointj);
	pointf(clsj);
	POINT pt;
	pt.x = getPointX(pointj);
	pt.y = getPointY(pointj);

	BOOL ret = ::ScreenToClient(hwnd, &pt);
	If0SaveLastError(ret, env);

	setPoint(pointj, pt.x, pt.y);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_api_WinUser_WindowFromPoint
  (JNIEnv *env, jclass, jint x, jint y)
{
	JNI_TRY();
	POINT pt = { x, y };
	HWND hret = ::WindowFromPoint(pt);
	//SaveLastError(env);
	return HandleToJLong(hret);
	JNI_END(return 0);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_api_WinUser_ChildWindowFromPoint
  (JNIEnv *env, jclass, jlong hwndj, jint x, jint y)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	POINT pt = { x, y };
	HWND hret = ::ChildWindowFromPoint(hwnd, pt);
	//SaveLastError(env);
	return HandleToJLong(hret);
	JNI_END(return 0);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_api_WinUser_ChildWindowFromPointEx
  (JNIEnv *env, jclass, jlong hwndj, jint x, jint y, jint flags)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	POINT pt = { x, y };
	HWND hret = ::ChildWindowFromPointEx(hwnd, pt, flags);
	//SaveLastError(env);
	return HandleToJLong(hret);
	JNI_END(return 0);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_api_WinUser_GetWindowLong
  (JNIEnv *env, jclass, jlong hwndj, jint index)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	LONG ret = ::GetWindowLong(hwnd, index);
	If0SaveLastError(ret, env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jint JNICALL Java_jp_hishidama_win32_api_WinUser_SetWindowLong
  (JNIEnv *env, jclass, jlong hwndj, jint index, jint value)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	LONG ret = ::SetWindowLong(hwnd, index, value);
	If0SaveLastError(ret, env);
	return ret;
	JNI_END(return 0);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_api_WinUser_GetDesktopWindow
  (JNIEnv *env, jclass)
{
	JNI_TRY();
	HWND hret = ::GetDesktopWindow();
	//SaveLastError(env);
	return HandleToJLong(hret);
	JNI_END(return 0);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_api_WinUser_GetParent
  (JNIEnv *env, jclass, jlong hwndj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);

	HWND hret = ::GetParent(hwnd);
	If0SaveLastError(hret, env);

	return HandleToJLong(hret);
	JNI_END(return 0);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_api_WinUser_SetParent
  (JNIEnv *env, jclass, jlong hwndChildj, jlong hWndParentj)
{
	JNI_TRY();
	HWND hwndChild  = JLongToHWND(hwndChildj);
	HWND hWndParent = JLongToHWND(hWndParentj);

	HWND hret = ::SetParent(hwndChild, hWndParent);
	If0SaveLastError(hret, env);

	return HandleToJLong(hret);
	JNI_END(return 0);
}

struct EnumWindowCallBackData
{
	JNIEnv		*env;
	jclass		jwnd;
	jmethodID	wndc;
	jobject		list;
	jmethodID	add;
};

#define	EnumWindowCallBackDataInit(data)	\
	EnumWindowCallBackData data;	\
	data.env  = env;	\
	data.jwnd = env->FindClass("Ljp/hishidama/win32/JWnd;");	\
	data.wndc = env->GetMethodID(data.jwnd, "<init>", "(J)V");	\
	data.list = listj;	\
	data.add  = env->GetMethodID(env->GetObjectClass(listj), "add", "(Ljava/lang/Object;)Z");


static BOOL CALLBACK EnumWindowsProc(
  HWND hwnd,      // ウィンドウハンドル
  LPARAM lParam   // アプリケーション定義の値
)
{
	EnumWindowCallBackData &data = *(EnumWindowCallBackData*)lParam;
	jlong hwndj = HandleToJLong(hwnd);
	jobject wndj = data.env->NewObject(data.jwnd, data.wndc, hwndj);
	data.env->CallVoidMethod(data.list, data.add, wndj);
	return TRUE;
}

JNIEXPORT boolean JNICALL Java_jp_hishidama_win32_api_WinUser_EnumChildWindows
  (JNIEnv *env, jclass, jlong hwndj, jobject listj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);
	EnumWindowCallBackDataInit(data);

	BOOL ret = ::EnumChildWindows(hwnd, (WNDENUMPROC)EnumWindowsProc, (LPARAM)&data);
	If0SaveLastError(ret, env);

	return ret;
	JNI_END(return 0);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_api_WinUser_FindWindow
  (JNIEnv *env, jclass, jstring classNamej, jstring windowNamej)
{
	JNI_TRY();
	JString lpClassName (env, classNamej);
	JString lpWindowName(env, windowNamej);

	HWND hret = ::FindWindow(lpClassName, lpWindowName);
	If0SaveLastError(hret, env);

	return HandleToJLong(hret);
	JNI_END(return 0);
}

JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_api_WinUser_EnumWindows
  (JNIEnv *env, jclass, jobject listj)
{
	JNI_TRY();
	EnumWindowCallBackDataInit(data);

	BOOL ret = ::EnumWindows((WNDENUMPROC)EnumWindowsProc, (LPARAM)&data);
	If0SaveLastError(ret, env);

	return ret;
	JNI_END(return 0);
}

JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_api_WinUser_getClassName
  (JNIEnv *env, jclass, jlong hwndj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);

	int len = 1024;
	TBUF str(len);

	len = ::GetClassName(hwnd, str, len);
	If0SaveLastError(len, env);

	jstring retj = NULL;
	if (len > 0) {
		retj = env->NewString(str, len);
	}

	return retj;
	JNI_END(return 0);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_api_WinUser_GetTopWindow
  (JNIEnv *env, jclass, jlong hwndj)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);

	HWND hret = ::GetTopWindow(hwnd);
	If0SaveLastError(hret, env);

	return HandleToJLong(hret);
	JNI_END(return 0);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_api_WinUser_GetWindow
  (JNIEnv *env, jclass, jlong hwndj, jint cmd)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);

	HWND hret = ::GetWindow(hwnd, cmd);
	If0SaveLastError(hret, env);

	return HandleToJLong(hret);
	JNI_END(return 0);
}

JNIEXPORT jlong JNICALL Java_jp_hishidama_win32_api_WinUser_GetAncestor
  (JNIEnv *env, jclass, jlong hwndj, jint flags)
{
	JNI_TRY();
	HWND hwnd = JLongToHWND(hwndj);

	HWND hret = ::GetAncestor(hwnd, flags);
	//SaveLastError(env);

	return HandleToJLong(hret);
	JNI_END(return 0);
}
