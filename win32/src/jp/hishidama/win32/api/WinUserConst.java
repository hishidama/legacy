package jp.hishidama.win32.api;

/**
 * winuser.hÇÃíËêî.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">Ç–ÇµÇæÇ‹</a>
 * @since 2007.10.01
 * @version 2007.11.01
 */
public interface WinUserConst {

	/*
	 * ShowWindow() Commands
	 */
	public static final int SW_HIDE = 0;

	public static final int SW_SHOWNORMAL = 1;

	public static final int SW_NORMAL = 1;

	public static final int SW_SHOWMINIMIZED = 2;

	public static final int SW_SHOWMAXIMIZED = 3;

	public static final int SW_MAXIMIZE = 3;

	public static final int SW_SHOWNOACTIVATE = 4;

	public static final int SW_SHOW = 5;

	public static final int SW_MINIMIZE = 6;

	public static final int SW_SHOWMINNOACTIVE = 7;

	public static final int SW_SHOWNA = 8;

	public static final int SW_RESTORE = 9;

	public static final int SW_SHOWDEFAULT = 10;

	public static final int SW_FORCEMINIMIZE = 11;

	public static final int SW_MAX = 11;

	/*
	 * Window field offsets for GetWindowLong()
	 */
	public static final int GWL_WNDPROC = (-4);

	public static final int GWL_HINSTANCE = (-6);

	public static final int GWL_HWNDPARENT = (-8);

	public static final int GWL_STYLE = (-16);

	public static final int GWL_EXSTYLE = (-20);

	public static final int GWL_USERDATA = (-21);

	public static final int GWL_ID = (-12);

	/*
	 * Window Messages
	 */

	public static final int WM_NULL = 0x0000;

	public static final int WM_CREATE = 0x0001;

	public static final int WM_DESTROY = 0x0002;

	public static final int WM_MOVE = 0x0003;

	public static final int WM_SIZE = 0x0005;

	public static final int WM_ACTIVATE = 0x0006;

	public static final int WM_SETFOCUS = 0x0007;

	public static final int WM_KILLFOCUS = 0x0008;

	public static final int WM_ENABLE = 0x000A;

	public static final int WM_SETREDRAW = 0x000B;

	public static final int WM_SETTEXT = 0x000C;

	public static final int WM_GETTEXT = 0x000D;

	public static final int WM_GETTEXTLENGTH = 0x000E;

	public static final int WM_PAINT = 0x000F;

	public static final int WM_CLOSE = 0x0010;

	public static final int WM_QUERYENDSESSION = 0x0011;

	public static final int WM_QUERYOPEN = 0x0013;

	public static final int WM_ENDSESSION = 0x0016;

	public static final int WM_QUIT = 0x0012;

	public static final int WM_ERASEBKGND = 0x0014;

	public static final int WM_SYSCOLORCHANGE = 0x0015;

	public static final int WM_SHOWWINDOW = 0x0018;

	public static final int WM_WININICHANGE = 0x001A;

	public static final int WM_SETTINGCHANGE = WM_WININICHANGE;

	public static final int WM_DEVMODECHANGE = 0x001B;

	public static final int WM_ACTIVATEAPP = 0x001C;

	public static final int WM_FONTCHANGE = 0x001D;

	public static final int WM_TIMECHANGE = 0x001E;

	public static final int WM_CANCELMODE = 0x001F;

	public static final int WM_SETCURSOR = 0x0020;

	public static final int WM_MOUSEACTIVATE = 0x0021;

	public static final int WM_CHILDACTIVATE = 0x0022;

	public static final int WM_QUEUESYNC = 0x0023;

	public static final int WM_GETMINMAXINFO = 0x0024;

	public static final int WM_PAINTICON = 0x0026;

	public static final int WM_ICONERASEBKGND = 0x0027;

	public static final int WM_NEXTDLGCTL = 0x0028;

	public static final int WM_SPOOLERSTATUS = 0x002A;

	public static final int WM_DRAWITEM = 0x002B;

	public static final int WM_MEASUREITEM = 0x002C;

	public static final int WM_DELETEITEM = 0x002D;

	public static final int WM_VKEYTOITEM = 0x002E;

	public static final int WM_CHARTOITEM = 0x002F;

	public static final int WM_SETFONT = 0x0030;

	public static final int WM_GETFONT = 0x0031;

	public static final int WM_SETHOTKEY = 0x0032;

	public static final int WM_GETHOTKEY = 0x0033;

	public static final int WM_QUERYDRAGICON = 0x0037;

	public static final int WM_COMPAREITEM = 0x0039;

	public static final int WM_GETOBJECT = 0x003D;

	public static final int WM_COMPACTING = 0x0041;

	public static final int WM_COMMNOTIFY = 0x0044;

	public static final int WM_WINDOWPOSCHANGING = 0x0046;

	public static final int WM_WINDOWPOSCHANGED = 0x0047;

	public static final int WM_POWER = 0x0048;

	public static final int WM_COPYDATA = 0x004A;

	public static final int WM_CANCELJOURNAL = 0x004B;

	public static final int WM_NOTIFY = 0x004E;

	public static final int WM_INPUTLANGCHANGEREQUEST = 0x0050;

	public static final int WM_INPUTLANGCHANGE = 0x0051;

	public static final int WM_TCARD = 0x0052;

	public static final int WM_HELP = 0x0053;

	public static final int WM_USERCHANGED = 0x0054;

	public static final int WM_NOTIFYFORMAT = 0x0055;

	public static final int WM_CONTEXTMENU = 0x007B;

	public static final int WM_STYLECHANGING = 0x007C;

	public static final int WM_STYLECHANGED = 0x007D;

	public static final int WM_DISPLAYCHANGE = 0x007E;

	public static final int WM_GETICON = 0x007F;

	public static final int WM_SETICON = 0x0080;

	public static final int WM_NCCREATE = 0x0081;

	public static final int WM_NCDESTROY = 0x0082;

	public static final int WM_NCCALCSIZE = 0x0083;

	public static final int WM_NCHITTEST = 0x0084;

	public static final int WM_NCPAINT = 0x0085;

	public static final int WM_NCACTIVATE = 0x0086;

	public static final int WM_GETDLGCODE = 0x0087;

	public static final int WM_SYNCPAINT = 0x0088;

	public static final int WM_NCMOUSEMOVE = 0x00A0;

	public static final int WM_NCLBUTTONDOWN = 0x00A1;

	public static final int WM_NCLBUTTONUP = 0x00A2;

	public static final int WM_NCLBUTTONDBLCLK = 0x00A3;

	public static final int WM_NCRBUTTONDOWN = 0x00A4;

	public static final int WM_NCRBUTTONUP = 0x00A5;

	public static final int WM_NCRBUTTONDBLCLK = 0x00A6;

	public static final int WM_NCMBUTTONDOWN = 0x00A7;

	public static final int WM_NCMBUTTONUP = 0x00A8;

	public static final int WM_NCMBUTTONDBLCLK = 0x00A9;

	public static final int WM_KEYFIRST = 0x0100;

	public static final int WM_KEYDOWN = 0x0100;

	public static final int WM_KEYUP = 0x0101;

	public static final int WM_CHAR = 0x0102;

	public static final int WM_DEADCHAR = 0x0103;

	public static final int WM_SYSKEYDOWN = 0x0104;

	public static final int WM_SYSKEYUP = 0x0105;

	public static final int WM_SYSCHAR = 0x0106;

	public static final int WM_SYSDEADCHAR = 0x0107;

	public static final int WM_IME_STARTCOMPOSITION = 0x010D;

	public static final int WM_IME_ENDCOMPOSITION = 0x010E;

	public static final int WM_IME_COMPOSITION = 0x010F;

	public static final int WM_IME_KEYLAST = 0x010F;

	public static final int WM_INITDIALOG = 0x0110;

	public static final int WM_COMMAND = 0x0111;

	public static final int WM_SYSCOMMAND = 0x0112;

	public static final int WM_TIMER = 0x0113;

	public static final int WM_HSCROLL = 0x0114;

	public static final int WM_VSCROLL = 0x0115;

	public static final int WM_INITMENU = 0x0116;

	public static final int WM_INITMENUPOPUP = 0x0117;

	public static final int WM_MENUSELECT = 0x011F;

	public static final int WM_MENUCHAR = 0x0120;

	public static final int WM_ENTERIDLE = 0x0121;

	public static final int WM_MENURBUTTONUP = 0x0122;

	public static final int WM_MENUDRAG = 0x0123;

	public static final int WM_MENUGETOBJECT = 0x0124;

	public static final int WM_UNINITMENUPOPUP = 0x0125;

	public static final int WM_MENUCOMMAND = 0x0126;

	public static final int WM_CTLCOLORMSGBOX = 0x0132;

	public static final int WM_CTLCOLOREDIT = 0x0133;

	public static final int WM_CTLCOLORLISTBOX = 0x0134;

	public static final int WM_CTLCOLORBTN = 0x0135;

	public static final int WM_CTLCOLORDLG = 0x0136;

	public static final int WM_CTLCOLORSCROLLBAR = 0x0137;

	public static final int WM_CTLCOLORSTATIC = 0x0138;

	public static final int MN_GETHMENU = 0x01E1;

	public static final int WM_MOUSEFIRST = 0x0200;

	public static final int WM_MOUSEMOVE = 0x0200;

	public static final int WM_LBUTTONDOWN = 0x0201;

	public static final int WM_LBUTTONUP = 0x0202;

	public static final int WM_LBUTTONDBLCLK = 0x0203;

	public static final int WM_RBUTTONDOWN = 0x0204;

	public static final int WM_RBUTTONUP = 0x0205;

	public static final int WM_RBUTTONDBLCLK = 0x0206;

	public static final int WM_MBUTTONDOWN = 0x0207;

	public static final int WM_MBUTTONUP = 0x0208;

	public static final int WM_MBUTTONDBLCLK = 0x0209;

	public static final int WM_PARENTNOTIFY = 0x0210;

	public static final int WM_ENTERMENULOOP = 0x0211;

	public static final int WM_EXITMENULOOP = 0x0212;

	public static final int WM_NEXTMENU = 0x0213;

	public static final int WM_SIZING = 0x0214;

	public static final int WM_CAPTURECHANGED = 0x0215;

	public static final int WM_MOVING = 0x0216;

	public static final int WM_POWERBROADCAST = 0x0218;

	public static final int WM_DEVICECHANGE = 0x0219;

	public static final int WM_MDICREATE = 0x0220;

	public static final int WM_MDIDESTROY = 0x0221;

	public static final int WM_MDIACTIVATE = 0x0222;

	public static final int WM_MDIRESTORE = 0x0223;

	public static final int WM_MDINEXT = 0x0224;

	public static final int WM_MDIMAXIMIZE = 0x0225;

	public static final int WM_MDITILE = 0x0226;

	public static final int WM_MDICASCADE = 0x0227;

	public static final int WM_MDIICONARRANGE = 0x0228;

	public static final int WM_MDIGETACTIVE = 0x0229;

	public static final int WM_MDISETMENU = 0x0230;

	public static final int WM_ENTERSIZEMOVE = 0x0231;

	public static final int WM_EXITSIZEMOVE = 0x0232;

	public static final int WM_DROPFILES = 0x0233;

	public static final int WM_MDIREFRESHMENU = 0x0234;

	public static final int WM_IME_SETCONTEXT = 0x0281;

	public static final int WM_IME_NOTIFY = 0x0282;

	public static final int WM_IME_CONTROL = 0x0283;

	public static final int WM_IME_COMPOSITIONFULL = 0x0284;

	public static final int WM_IME_SELECT = 0x0285;

	public static final int WM_IME_CHAR = 0x0286;

	public static final int WM_IME_REQUEST = 0x0288;

	public static final int WM_IME_KEYDOWN = 0x0290;

	public static final int WM_IME_KEYUP = 0x0291;

	public static final int WM_MOUSEHOVER = 0x02A1;

	public static final int WM_MOUSELEAVE = 0x02A3;

	public static final int WM_NCMOUSEHOVER = 0x02A0;

	public static final int WM_NCMOUSELEAVE = 0x02A2;

	public static final int WM_CUT = 0x0300;

	public static final int WM_COPY = 0x0301;

	public static final int WM_PASTE = 0x0302;

	public static final int WM_CLEAR = 0x0303;

	public static final int WM_UNDO = 0x0304;

	public static final int WM_RENDERFORMAT = 0x0305;

	public static final int WM_RENDERALLFORMATS = 0x0306;

	public static final int WM_DESTROYCLIPBOARD = 0x0307;

	public static final int WM_DRAWCLIPBOARD = 0x0308;

	public static final int WM_PAINTCLIPBOARD = 0x0309;

	public static final int WM_VSCROLLCLIPBOARD = 0x030A;

	public static final int WM_SIZECLIPBOARD = 0x030B;

	public static final int WM_ASKCBFORMATNAME = 0x030C;

	public static final int WM_CHANGECBCHAIN = 0x030D;

	public static final int WM_HSCROLLCLIPBOARD = 0x030E;

	public static final int WM_QUERYNEWPALETTE = 0x030F;

	public static final int WM_PALETTEISCHANGING = 0x0310;

	public static final int WM_PALETTECHANGED = 0x0311;

	public static final int WM_HOTKEY = 0x0312;

	public static final int WM_PRINT = 0x0317;

	public static final int WM_PRINTCLIENT = 0x0318;

	public static final int WM_HANDHELDFIRST = 0x0358;

	public static final int WM_HANDHELDLAST = 0x035F;

	public static final int WM_AFXFIRST = 0x0360;

	public static final int WM_AFXLAST = 0x037F;

	public static final int WM_PENWINFIRST = 0x0380;

	public static final int WM_PENWINLAST = 0x038F;

	public static final int WM_APP = 0x8000;

	public static final int WM_USER = 0x0400;

	/*
	 * Key State Masks for Mouse Messages
	 */
	public static final int MK_LBUTTON = 0x0001;

	public static final int MK_RBUTTON = 0x0002;

	public static final int MK_SHIFT = 0x0004;

	public static final int MK_CONTROL = 0x0008;

	public static final int MK_MBUTTON = 0x0010;

	/*
	 * SetWindowPos Flags
	 */
	public static final int SWP_NOSIZE = 0x0001;

	public static final int SWP_NOMOVE = 0x0002;

	public static final int SWP_NOZORDER = 0x0004;

	public static final int SWP_NOREDRAW = 0x0008;

	public static final int SWP_NOACTIVATE = 0x0010;

	public static final int SWP_FRAMECHANGED = 0x0020;

	public static final int SWP_SHOWWINDOW = 0x0040;

	public static final int SWP_HIDEWINDOW = 0x0080;

	public static final int SWP_NOCOPYBITS = 0x0100;

	public static final int SWP_NOOWNERZORDER = 0x0200;

	public static final int SWP_NOSENDCHANGING = 0x0400;

	public static final int SWP_DRAWFRAME = SWP_FRAMECHANGED;

	public static final int SWP_NOREPOSITION = SWP_NOOWNERZORDER;

	public static final int SWP_DEFERERASE = 0x2000;

	public static final int SWP_ASYNCWINDOWPOS = 0x4000;

	public static final long HWND_TOP = 0;

	public static final long HWND_BOTTOM = 1;

	public static final long HWND_TOPMOST = -1;

	public static final long HWND_NOTOPMOST = -2;

	/*
	 * MessageBox() Flags
	 */
	public static final int MB_OK = 0x00000000;

	public static final int MB_OKCANCEL = 0x00000001;

	public static final int MB_ABORTRETRYIGNORE = 0x00000002;

	public static final int MB_YESNOCANCEL = 0x00000003;

	public static final int MB_YESNO = 0x00000004;

	public static final int MB_RETRYCANCEL = 0x00000005;

	public static final int MB_CANCELTRYCONTINUE = 0x00000006;

	public static final int MB_ICONHAND = 0x00000010;

	public static final int MB_ICONQUESTION = 0x00000020;

	public static final int MB_ICONEXCLAMATION = 0x00000030;

	public static final int MB_ICONASTERISK = 0x00000040;

	public static final int MB_USERICON = 0x00000080;

	public static final int MB_ICONWARNING = MB_ICONEXCLAMATION;

	public static final int MB_ICONERROR = MB_ICONHAND;

	public static final int MB_ICONINFORMATION = MB_ICONASTERISK;

	public static final int MB_ICONSTOP = MB_ICONHAND;

	public static final int MB_DEFBUTTON1 = 0x00000000;

	public static final int MB_DEFBUTTON2 = 0x00000100;

	public static final int MB_DEFBUTTON3 = 0x00000200;

	public static final int MB_DEFBUTTON4 = 0x00000300;

	public static final int MB_APPLMODAL = 0x00000000;

	public static final int MB_SYSTEMMODAL = 0x00001000;

	public static final int MB_TASKMODAL = 0x00002000;

	public static final int MB_HELP = 0x00004000;

	public static final int MB_NOFOCUS = 0x00008000;

	public static final int MB_SETFOREGROUND = 0x00010000;

	public static final int MB_DEFAULT_DESKTOP_ONLY = 0x00020000;

	public static final int MB_TOPMOST = 0x00040000;

	public static final int MB_RIGHT = 0x00080000;

	public static final int MB_RTLREADING = 0x00100000;

	public static final int MB_TYPEMASK = 0x0000000F;

	public static final int MB_ICONMASK = 0x000000F0;

	public static final int MB_DEFMASK = 0x00000F00;

	public static final int MB_MODEMASK = 0x00003000;

	public static final int MB_MISCMASK = 0x0000C000;

	/*
	 * 
	 */

	public static final int CWP_ALL = 0x0000;

	public static final int CWP_SKIPINVISIBLE = 0x0001;

	public static final int CWP_SKIPDISABLED = 0x0002;

	public static final int CWP_SKIPTRANSPARENT = 0x0004;

	/*
	 * GetWindow() Constants
	 */
	public static final int GW_HWNDFIRST = 0;

	public static final int GW_HWNDLAST = 1;

	public static final int GW_HWNDNEXT = 2;

	public static final int GW_HWNDPREV = 3;

	public static final int GW_OWNER = 4;

	public static final int GW_CHILD = 5;

	public static final int GW_ENABLEDPOPUP = 6;

	public static final int GW_MAX = 6;

	/*
	 * Dialog Box Command IDs
	 */
	public static final int IDOK = 1;

	public static final int IDCANCEL = 2;

	public static final int IDABORT = 3;

	public static final int IDRETRY = 4;

	public static final int IDIGNORE = 5;

	public static final int IDYES = 6;

	public static final int IDNO = 7;

	public static final int IDCLOSE = 8;

	public static final int IDHELP = 9;

	public static final int IDTRYAGAIN = 10;

	public static final int IDCONTINUE = 11;

	public static final int IDTIMEOUT = 32000;

	/*
	 * User Button Notification Codes
	 */
	public static final int BN_CLICKED = 0;

	public static final int BN_PAINT = 1;

	public static final int BN_HILITE = 2;

	public static final int BN_UNHILITE = 3;

	public static final int BN_DISABLE = 4;

	public static final int BN_DOUBLECLICKED = 5;

	public static final int BN_PUSHED = BN_HILITE;

	public static final int BN_UNPUSHED = BN_UNHILITE;

	public static final int BN_DBLCLK = BN_DOUBLECLICKED;

	public static final int BN_SETFOCUS = 6;

	public static final int BN_KILLFOCUS = 7;

	/*
	 * Button Control Messages
	 */
	public static final int BM_GETCHECK = 0x00F0;

	public static final int BM_SETCHECK = 0x00F1;

	public static final int BM_GETSTATE = 0x00F2;

	public static final int BM_SETSTATE = 0x00F3;

	public static final int BM_SETSTYLE = 0x00F4;

	public static final int BM_CLICK = 0x00F5;

	public static final int BM_GETIMAGE = 0x00F6;

	public static final int BM_SETIMAGE = 0x00F7;

	public static final int BST_UNCHECKED = 0x0000;

	public static final int BST_CHECKED = 0x0001;

	public static final int BST_INDETERMINATE = 0x0002;

	public static final int BST_PUSHED = 0x0004;

	public static final int BST_FOCUS = 0x0008;
}
