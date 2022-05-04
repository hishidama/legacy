package jp.hishidama.ant.taskdefs.condition;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

/**
 * テキストファイル正規表現比較クラス.
 * <p>
 * テキストファイルの内容を、指定された正規表現を適用して比較する。<br>
 * ファイルの各行を素直に比較して不一致だった場合、正規表現を1つずつ適用していく。 適用した正規表現が1つでも一致すれば その行は一致したものとして扱う。
 * </p>
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/compsync.html">ひしだま</a>
 */
public class RegexpFilesMatch extends TextFilesMatch {

	/**
	 * 正規表現パターンのリスト.
	 */
	List list = new ArrayList();

	/**
	 * 正規表現属性を追加する.
	 * 
	 * @param regexp
	 *            正規表現文字列
	 */
	public void setRegexp(String regexp) {
		Pattern p = Pattern.compile(regexp);
		list.add(p);
		log("regexp: " + p.pattern(), Project.MSG_VERBOSE);
	}

	/**
	 * 書式属性を追加する.
	 * 
	 * @param format
	 *            書式文字列
	 * @see #formatToRegexp(String)
	 * @see #setRegexp(String)
	 */
	public void setFormat(String format) {
		setRegexp(formatToRegexp(format));
	}

	/**
	 * ネストされたExpressionタスクを追加する.
	 * 
	 * @param expression
	 *            Expression
	 * @see #setRegexp(String)
	 * @see #setFormat(String)
	 */
	public void addConfiguredExpression(RegExpressionBean expression) {
		String reg = expression.getRegexp();
		String fmt = expression.getFormat();
		if (reg != null && fmt != null) {
			throw new BuildException(
					"Only one of regexp and format may be set.");
		}
		if (reg == null && fmt == null) {
			throw new BuildException("One of regexp or format must be set.");
		}
		if (reg != null)
			setRegexp(reg);
		if (fmt != null)
			setFormat(fmt);
	}

	/**
	 * 書式を正規表現に変換する. <table border="1">
	 * <tr>
	 * <th>書式</th>
	 * <th>内容</th>
	 * <th>正規表現</th>
	 * </tr>
	 * <tr>
	 * <td>%d</td>
	 * <td>数字1桁以上</td>
	 * <td>\d+</td>
	 * </tr>
	 * <tr>
	 * <td>%<i>n</i>d</td>
	 * <td>数字n桁</td>
	 * <td>\d{<i>n</i>}</td>
	 * </tr>
	 * <tr>
	 * <td>%<i>n</i>-d</td>
	 * <td>数字n桁以上</td>
	 * <td>\d{<i>n</i>,}</td>
	 * </tr>
	 * <tr>
	 * <td>%<i>n</i>-<i>m</i>d</td>
	 * <td>数字n桁以上m桁以下</td>
	 * <td>\d{<i>n</i>,<i>m</i>}</td>
	 * </tr>
	 * <tr>
	 * <td>%s</td>
	 * <td>文字0桁以上</td>
	 * <td>.*</td>
	 * </tr>
	 * <tr>
	 * <td>%<i>n</i>s</td>
	 * <td>文字n桁</td>
	 * <td>.{<i>n</i>}</td>
	 * </tr>
	 * <tr>
	 * <td>%<i>n</i>-s</td>
	 * <td>文字n桁以上</td>
	 * <td>.{<i>n</i>,}</td>
	 * </tr>
	 * <tr>
	 * <td>%<i>n</i>-<i>m</i>s</td>
	 * <td>文字n桁以上m桁以下</td>
	 * <td>.{<i>n</i>,<i>m</i>}</td>
	 * </tr>
	 * <tr>
	 * <td>%Y</td>
	 * <td>年4桁</td>
	 * <td>\d{4}</td>
	 * </tr>
	 * <tr>
	 * <td>%y</td>
	 * <td>年2桁</td>
	 * <td>\d{2}</td>
	 * </tr>
	 * <tr>
	 * <td>%M</td>
	 * <td>月2桁</td>
	 * <td>[0-1]\d</td>
	 * </tr>
	 * <tr>
	 * <td>%D</td>
	 * <td>日2桁</td>
	 * <td>[0-3]\d</td>
	 * </tr>
	 * <tr>
	 * <td>%H</td>
	 * <td>時2桁</td>
	 * <td>[0-2]\d</td>
	 * </tr>
	 * <tr>
	 * <td>%m</td>
	 * <td>分2桁</td>
	 * <td>[0-5]\d</td>
	 * </tr>
	 * <tr>
	 * <td>%S</td>
	 * <td>秒2桁</td>
	 * <td>[0-5]\d</td>
	 * </tr>
	 * <tr>
	 * <td>%%</td>
	 * <td>「%」そのもの</td>
	 * <td>%</td>
	 * </tr>
	 * <tr>
	 * <td><i>字</i></td>
	 * <td>そのまま</td>
	 * <td><i>字</i></td>
	 * </tr>
	 * <tr>
	 * <td><i>記号</i></td>
	 * <td>エスケープする</td>
	 * <td>\<i>記号</i></td>
	 * </tr>
	 * <tr>
	 * <td><i>制御</i></td>
	 * <td>十六進数変換</td>
	 * <td>\x<i>制御コード</i></td>
	 * </tr>
	 * </table>
	 * 
	 * @param format
	 *            書式文字列
	 * @return 正規表現文字列
	 */
	public String formatToRegexp(String format) {
		int len = format.length();
		StringBuffer sb = new StringBuffer(len);
		for (int i = 0; i < len; i++) {
			char c = format.charAt(i);
			if (c == ' ' || '0' <= c && c <= '9' || 'A' <= c && c <= 'Z'
					|| 'a' <= c && c <= 'z') {
				sb.append(c);
				continue;
			}
			if (c != '%') {
				if (c <= 0x0f) {
					sb.append("\\x0");
					sb.append('0' + c);
					continue;
				}
				if (c < 0x20 || c == 0x7f) {
					sb.append("\\x");
					sb.append(Integer.toHexString(c));
					continue;
				}
				if (c < 0x80)
					sb.append('\\');
				sb.append(c);
				continue;
			}
			if (++i >= len)
				break;
			c = format.charAt(i);
			switch (c) {
			case 'd':
				sb.append("\\d+");
				break;
			case 's':
				sb.append(".*");
				break;
			case 'Y':
				sb.append("\\d{4}");
				break;
			case 'y':
				sb.append("\\d{2}");
				break;
			case 'M':
				sb.append("[0-1]\\d");
				break;
			case 'D':
				sb.append("[0-3]\\d");
				break;
			case 'H':
				sb.append("[0-2]\\d");
				break;
			case 'm':
			case 'S':
				sb.append("[0-5]\\d");
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				i = digit(format, len, i, sb);
				break;
			case '%':
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	private int digit(String format, int len, int i, StringBuffer sb) {
		int n = 0;
		char c;
		while (true) {
			c = format.charAt(i);
			if (c < '0' || '9' < c)
				break;
			n *= 10;
			n += c - '0';
			if (++i >= len)
				return i;
		}
		int m = n;
		if (c == '-') {
			m = 0;
			boolean nothing = true;
			while (true) {
				if (++i >= len)
					return i;
				c = format.charAt(i);
				if (c < '0' || '9' < c)
					break;
				m *= 10;
				m += c - '0';
				nothing = false;
			}
			if (nothing)
				m = -1;
		}
		switch (c) {
		case 'd':
			sb.append("\\d{");
			break;
		case 's':
			sb.append(".{");
			break;
		default:
			return i;
		}
		sb.append(n);
		if (m == n) {
			sb.append('}');
		} else if (m < 0) {
			sb.append(",}");
		} else {
			sb.append(',');
			sb.append(m);
			sb.append('}');
		}
		return i;
	}

	protected boolean lineEquals(String s, String d) {
		if (s.equals(d))
			return true;
		for (int i = 0; i < list.size(); i++) {
			if (lineEquals(s, d, (Pattern) list.get(i)))
				return true;
		}
		return false;
	}

	protected boolean lineEquals(String s, String d, Pattern p) {
		Matcher ms = p.matcher(s);
		String sp = ms.replaceAll(p.pattern());
		Matcher md = p.matcher(d);
		String dp = md.replaceAll(p.pattern());
		return sp.equals(dp);
	}

}
