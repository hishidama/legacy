/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class jp_hishidama_win32_mshtml_IHTMLTxtRange_Native */

#ifndef _Included_jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
#define _Included_jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    get_htmlText
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_get_1htmlText
  (JNIEnv *, jclass, jlong);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    put_text
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_put_1text
  (JNIEnv *, jclass, jlong, jstring);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    get_text
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_get_1text
  (JNIEnv *, jclass, jlong);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    parentElement
 * Signature: (J)Ljp/hishidama/win32/mshtml/IHTMLElement;
 */
JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_parentElement
  (JNIEnv *, jclass, jlong);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    duplicate
 * Signature: (J)Ljp/hishidama/win32/mshtml/IHTMLTxtRange;
 */
JNIEXPORT jobject JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_duplicate
  (JNIEnv *, jclass, jlong);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    inRange
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_inRange
  (JNIEnv *, jclass, jlong, jlong);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    isEqual
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_isEqual
  (JNIEnv *, jclass, jlong, jlong);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    scrollIntoView
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_scrollIntoView
  (JNIEnv *, jclass, jlong, jboolean);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    collapse
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_collapse
  (JNIEnv *, jclass, jlong, jboolean);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    expand
 * Signature: (JLjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_expand
  (JNIEnv *, jclass, jlong, jstring);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    move
 * Signature: (JLjava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_move
  (JNIEnv *, jclass, jlong, jstring, jint);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    moveStart
 * Signature: (JLjava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_moveStart
  (JNIEnv *, jclass, jlong, jstring, jint);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    moveEnd
 * Signature: (JLjava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_moveEnd
  (JNIEnv *, jclass, jlong, jstring, jint);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    select
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_select
  (JNIEnv *, jclass, jlong);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    pasteHTML
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_pasteHTML
  (JNIEnv *, jclass, jlong, jstring);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    moveToElementText
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_moveToElementText
  (JNIEnv *, jclass, jlong, jlong);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    setEndPoint
 * Signature: (JLjava/lang/String;J)V
 */
JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_setEndPoint
  (JNIEnv *, jclass, jlong, jstring, jlong);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    compareEndPoints
 * Signature: (JLjava/lang/String;J)I
 */
JNIEXPORT jint JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_compareEndPoints
  (JNIEnv *, jclass, jlong, jstring, jlong);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    findText
 * Signature: (JLjava/lang/String;II)Z
 */
JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_findText
  (JNIEnv *, jclass, jlong, jstring, jint, jint);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    moveToPoint
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_moveToPoint
  (JNIEnv *, jclass, jlong, jint, jint);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    getBookmark
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_getBookmark
  (JNIEnv *, jclass, jlong);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    moveToBookmark
 * Signature: (JLjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_moveToBookmark
  (JNIEnv *, jclass, jlong, jstring);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    queryCommandSupported
 * Signature: (JLjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_queryCommandSupported
  (JNIEnv *, jclass, jlong, jstring);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    queryCommandEnabled
 * Signature: (JLjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_queryCommandEnabled
  (JNIEnv *, jclass, jlong, jstring);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    queryCommandState
 * Signature: (JLjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_queryCommandState
  (JNIEnv *, jclass, jlong, jstring);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    queryCommandIndeterm
 * Signature: (JLjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_queryCommandIndeterm
  (JNIEnv *, jclass, jlong, jstring);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    queryCommandText
 * Signature: (JLjava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_queryCommandText
  (JNIEnv *, jclass, jlong, jstring);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    queryCommandValue
 * Signature: (JLjava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_queryCommandValue
  (JNIEnv *, jclass, jlong, jstring);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    execCommand
 * Signature: (JLjava/lang/String;ZLjp/hishidama/win32/com/Variant;)Z
 */
JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_execCommand
  (JNIEnv *, jclass, jlong, jstring, jboolean, jobject);

/*
 * Class:     jp_hishidama_win32_mshtml_IHTMLTxtRange_Native
 * Method:    execCommandShowHelp
 * Signature: (JLjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_jp_hishidama_win32_mshtml_IHTMLTxtRange_00024Native_execCommandShowHelp
  (JNIEnv *, jclass, jlong, jstring);

#ifdef __cplusplus
}
#endif
#endif
