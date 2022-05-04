package jp.hishidama.zip;

import java.util.zip.ZipException;

/**
 * パスワード不一致例外.
 *<p>
 * パスワード（CRC）チェックで一致しなかった場合に当例外が発生します。
 * </p>
 *<p>
 * パスワードが不一致でも必ずしも当例外が発生するわけではありません。 ブロック長不一致など他の例外が出る可能性もあります。
 * また、パスワードが違っているのに例外が発生せずに解凍終了したとしても、データの内容が圧縮前と異なっている可能性が高いです。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/zip.html"
 *         >ひしだま</a>
 * @since 2008.12.21
 * @see ZipFile#getInputStream(ZipEntry)
 * @see ZipFile#setCheckCrc(boolean)
 */
public class ZipPasswordException extends ZipException {

	private static final long serialVersionUID = -7225423289925959589L;

	public ZipPasswordException(String s) {
		super(s);
	}
}
