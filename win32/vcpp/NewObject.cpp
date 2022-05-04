#include <windows.h>
#include <tchar.h>

#include "import.h"

#define	CASE_RET0(PTR,name)	\
	{	MSHTML::PTR##Ptr ptr(p);\
		if (ptr != NULL) {\
			return NewObject(env, "Ljp/hishidama/win32/mshtml/" #name ";", p);\
		}\
	}
#define	CASE_RET_(name)	CASE_RET0(name,name)
#define	CASE_RET2(name)	CASE_RET0(name##2,name)

jobject NewElement0(JNIEnv *env, IDispatch *p)
{
	if (p==NULL) return NULL;

	CASE_RET_(IHTMLInputTextElement);
	CASE_RET_(IHTMLInputHiddenElement);
	CASE_RET_(IHTMLInputButtonElement);
	CASE_RET_(IHTMLInputFileElement);
	CASE_RET_(IHTMLInputElement);
	CASE_RET_(IHTMLButtonElement);
	CASE_RET_(IHTMLSelectElement);
	CASE_RET_(IHTMLOptionElement);
	CASE_RET_(IHTMLOptionButtonElement);
	CASE_RET_(IHTMLTextAreaElement);
	CASE_RET_(IHTMLLabelElement);

	CASE_RET_(IHTMLFormElement);
	CASE_RET_(IHTMLAnchorElement);
	CASE_RET_(IHTMLImgElement);

	CASE_RET_(IHTMLBRElement);
	CASE_RET_(IHTMLParaElement);
	CASE_RET_(IHTMLHeaderElement);
	CASE_RET_(IHTMLSpanElement);
	CASE_RET_(IHTMLDivElement);
	CASE_RET_(IHTMLBodyElement);

	CASE_RET_(IHTMLTable);
	CASE_RET_(IHTMLTableCell);
	CASE_RET_(IHTMLTableCol);
	CASE_RET_(IHTMLTableRow);
	CASE_RET_(IHTMLTableCaption);
	CASE_RET_(IHTMLTableSection);

	CASE_RET_(IHTMLOListElement);
	CASE_RET_(IHTMLUListElement);
	CASE_RET_(IHTMLLIElement);
	CASE_RET_(IHTMLDListElement);
	CASE_RET_(IHTMLDTElement);
	CASE_RET_(IHTMLDDElement);

	CASE_RET_(IHTMLHRElement);
	CASE_RET_(IHTMLFontElement);
	CASE_RET_(IHTMLLegendElement);
	CASE_RET_(IHTMLFieldSetElement);
	CASE_RET_(IHTMLMarqueeElement);
	CASE_RET_(IHTMLMapElement);
	CASE_RET_(IHTMLAreaElement);

	CASE_RET_(IHTMLFrameElement);
	CASE_RET_(IHTMLIFrameElement);

	CASE_RET_(IHTMLScriptElement);
	CASE_RET_(IHTMLObjectElement);
	CASE_RET_(IHTMLEmbedElement);

	CASE_RET_(IHTMLTitleElement);
	CASE_RET_(IHTMLLinkElement);
	CASE_RET_(IHTMLMetaElement);
	CASE_RET_(IHTMLBaseElement);
	CASE_RET_(IHTMLHeadElement);
	CASE_RET_(IHTMLHtmlElement);

	CASE_RET_(IHTMLListElement);
	CASE_RET_(IHTMLBlockElement);
	CASE_RET_(IHTMLFrameBase);
	CASE_RET_(IHTMLPhraseElement);
	CASE_RET_(IHTMLCommentElement);

	CASE_RET_(IHTMLUnknownElement);

	return NULL;
}

jobject NewIHTMLElement(JNIEnv *env, IDispatch *p)
{
	jobject ret = NewElement0(env, p);
	if (ret != NULL) return ret;

	return NewObject(env, "Ljp/hishidama/win32/mshtml/IHTMLElement;", p);
}

jobject NewIWebBrowser(JNIEnv *env, SHDocVw::IWebBrowser2 *p)
{
	if (p != NULL) {
		return NewObject(env, "Ljp/hishidama/win32/shdocvw/IWebBrowser;", p);
	}
	return NULL;
}

jobject NewIDispatch(JNIEnv *env, IDispatch *p)
{
	if (p == NULL) return NULL;

	jobject ret = NewElement0(env, p);
	if (ret != NULL) return ret;

	CASE_RET_(IHTMLElement);
	CASE_RET2(IHTMLDocument);
	CASE_RET2(IHTMLWindow);
	CASE_RET_(IHTMLTxtRange);
	CASE_RET_(IHTMLLocation);
	CASE_RET_(IHTMLElementCollection);
	CASE_RET2(IHTMLFramesCollection);
	CASE_RET_(IHTMLAreasCollection);

	{SHDocVw::IWebBrowser2Ptr ptr(p);
	if (ptr != NULL) {
		return NewObject(env, "Ljp/hishidama/win32/shdocvw/IWebBrowser;", p);
	}}

	return NewObject(env, "Ljp/hishidama/win32/com/IDispatch;", p);
}

jobject NewIUnknown(JNIEnv *env, IUnknown *p)
{
	if (p == NULL) return NULL;

	IDispatchPtr ptr(p);
	if (ptr != NULL) {
		return NewIDispatch(env, ptr);
	}

	return NewObject(env, "Ljp/hishidama/win32/com/IUnknown;", p);
}



#define DEF_New0(PTR,name) \
jobject New##name(JNIEnv *env, MSHTML::PTR *p)\
{\
	if (p != NULL) {\
		return NewObject(env, "Ljp/hishidama/win32/mshtml/" #name ";", p);\
	}\
	return NULL;\
}
#define	DEF_New_(name)	DEF_New0(name,name)
#define	DEF_New2(name)	DEF_New0(name##2,name)

DEF_New_(IHTMLAreasCollection)
DEF_New2(IHTMLDocument)
DEF_New_(IHTMLFormElement)
DEF_New_(IHTMLLocation)
DEF_New_(IHTMLOptionElement)
DEF_New_(IHTMLTableCaption)
DEF_New_(IHTMLTableSection)
DEF_New_(IHTMLTxtRange)
DEF_New2(IHTMLWindow)
DEF_New2(IHTMLFramesCollection)
DEF_New_(IHTMLElementCollection)

#undef	DEF_New_
#undef	DEF_New2
#undef	DEF_New0

