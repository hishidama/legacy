package jp.hishidama.eval;

import static jp.hishidama.eval.EvalException.*;

/**
 * 演算エラーメッセージ編集クラス.
 * <p>
 * {@link EvalException}用のエラーメッセージを編集する。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since eval16
 */
public class EvalExceptionFormatter {

	private static EvalExceptionFormatter me;

	/**
	 * デフォルトフォーマッター取得
	 *
	 * @return フォーマッター
	 */
	public static EvalExceptionFormatter getDefault() {
		if (me == null) {
			me = new EvalExceptionFormatter();
		}
		return me;
	}

	/**
	 * エラーメッセージ生成.
	 * <p>
	 * {@link EvalException}のエラーメッセージを生成する。
	 * </p>
	 *
	 * @param e
	 *            例外
	 * @return エラーメッセージ
	 * @see EvalException#toString()
	 */
	public String toString(EvalException e) {
		String msg = getErrCodeMessage(e.getErrorCode());
		String fmt = getFormat(e, msg);
		return toString(e, fmt);
	}

	/**
	 * コード別メッセージ取得
	 *
	 * @param code
	 *            エラーコード
	 * @return 主メッセージ（フォーマット付き）
	 */
	public String getErrCodeMessage(int code) {
		switch (code) {
		case PARSE_NOT_FOUND_END_OP:
			return "演算子「%0」が在りません。";
		case PARSE_INVALID_OP:
			return "演算子の文法エラーです。";
		case PARSE_INVALID_CHAR:
			return "未対応の識別子です。";
		case PARSE_END_OF_STR:
			return "式の解釈の途中で文字列が終了しています。";
		case PARSE_STILL_EXIST:
			return "式の解釈が終わりましたが文字列が残っています。";
		case PARSE_NOT_FUNC:
			return "関数として使用できません。";
		case EXP_FORBIDDEN_CALL:
			return "禁止されているメソッドを呼び出しました。";
		case EXP_NOT_VARIABLE:
			return "変数として使用できません。";
		case EXP_NOT_NUMBER:
			return "数値として使用できません。";
		case EXP_NOT_LET:
			return "代入できません。";
		case EXP_NOT_CHAR:
			return "文字として使用できません。";
		case EXP_NOT_STRING:
			return "文字列として使用できません。";
		case EXP_NOT_VAR_VALUE:
			return "変数の値が取得できません。";
		case EXP_NOT_LET_VAR:
			return "変数に代入できません。";
		case EXP_NOT_DEF_VAR:
			return "変数が未定義です。";
		case EXP_NOT_DEF_OBJ:
			return "オブジェクトが未定義です。";
		case EXP_NOT_ARR_VALUE:
			return "配列の値が取得できません。";
		case EXP_NOT_LET_ARR:
			return "配列に代入できません。";
		case EXP_NOT_FLD_VALUE:
			return "フィールドの値が取得できません。";
		case EXP_NOT_LET_FIELD:
			return "フィールドに代入できません。";
		case EXP_FUNC_CALL_ERROR:
			return "関数の呼び出しに失敗しました。";
		default:
			return "エラーが発生しました。";
		}
	}

	/**
	 * フォーマット取得
	 *
	 * @param e
	 *            例外
	 * @param msgFmt
	 *            主メッセージ
	 * @return メッセージフォーマット
	 */
	public String getFormat(EvalException e, String msgFmt) {
		StringBuilder fmt = new StringBuilder(128);
		fmt.append(msgFmt);

		String word = e.getWord();
		String string = e.getString();

		boolean bWord = false;
		if (word != null && word.length() > 0) {
			bWord = true;
			if (word.equals(string)) {
				bWord = false;
			}
		}
		if (bWord) {
			fmt.append(" %n=「%w」");
		}

		int pos = e.getPos();
		if (pos >= 0) {
			fmt.append(" pos=%p");
		}
		if (string != null) {
			fmt.append(" string=「%s」");
		}
		if (e.getCause() != null) {
			fmt.append(" cause by %e");
		}

		return fmt.toString();
	}

	/**
	 * エラーメッセージ取得.
	 * <p>
	 * フォーマットを元にメッセージを編集する。
	 * <table border="1">
	 * <tr>
	 * <td>%0～9</td>
	 * <td>付加情報の0～9番。存在しない場合は空文字列</td>
	 * </tr>
	 * <tr>
	 * <td>%c</td>
	 * <td>エラーコード</td>
	 * </tr>
	 * <tr>
	 * <td>%n</td>
	 * <td>解析名</td>
	 * </tr>
	 * <tr>
	 * <td>%s</td>
	 * <td>解析前の文字列</td>
	 * </tr>
	 * <tr>
	 * <td>%p</td>
	 * <td>エラーが発生した、解析前文字列内の位置</td>
	 * </tr>
	 * <tr>
	 * <td>%w</td>
	 * <td>エラーの原因となった文字列</td>
	 * </tr>
	 * <tr>
	 * <td>%e</td>
	 * <td>何らかの例外発生でエラーとなった場合の、その例外</td>
	 * </tr>
	 * <tr>
	 * <td>%%</td>
	 * <td>「%」</td>
	 * </tr>
	 * </table>
	 * </p>
	 *
	 * @param e
	 *            例外
	 * @param format
	 *            フォーマット
	 * @return エラーメッセージ
	 */
	public String toString(EvalException e, String format) {
		StringBuilder sb = new StringBuilder(256);
		int len = format.length();
		for (int i = 0; i < len; i++) {
			char c = format.charAt(i);
			if (c != '%') {
				sb.append(c);
			} else {
				if (i + 1 >= len) {
					sb.append(c);
					break;
				}
				c = format.charAt(++i);
				switch (c) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9': {
					int n = c - '0';
					String[] msgOpt = e.getOption();
					if (msgOpt != null && n < msgOpt.length) {
						sb.append(msgOpt[n]);
					} else {
						sb.append('%');
						sb.append(c);
					}
					break;
				}
				case 'c':
					sb.append(e.getErrorCode());
					break;
				case 'n':
					sb.append(e.getExpressionName());
					break;
				case 'w':
					sb.append(e.getWord());
					break;
				case 'p':
					sb.append(e.getPos());
					break;
				case 's':
					sb.append(e.getString());
					break;
				case 'e':
					sb.append(e.getCause());
					break;
				case '%':
					sb.append('%');
					break;
				default:
					sb.append('%');
					sb.append(c);
					break;
				}
			}
		}
		return sb.toString();
	}
}
