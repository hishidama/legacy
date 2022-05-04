package jp.hishidama.sample;

import java.io.File;
import java.io.IOException;

import jp.hishidama.zip.ZipCloak;

public class ZipCloakEncode {

	public static void main(String[] args) throws IOException {
		String dir = "experiment/";

		File s = new File(dir + "test_src_null.zip");
		File d = new File(dir + "test_encrypt.zip");
		String passwd = "abc";
		ZipCloak zip = new ZipCloak(s);
		zip.encrypt(d, passwd.getBytes("MS932"));

		// zip = new ZipCloak(s);
		zip.setFix(1);
		zip.encrypt(new File(dir + "enc_fix.zip"), passwd.getBytes("MS932"));

		// zip = new ZipCloak(s);
		zip.setAdjust(1);
		zip.encrypt(new File(dir + "enc_adjust.zip"), passwd.getBytes("MS932"));

		// zip = new ZipCloak(s);
		zip.setDosify(1);
		zip.encrypt(new File(dir + "enc_dos.zip"), passwd.getBytes("MS932"));

		// zip = new ZipCloak(d);
		zip.encrypt(new File(dir + "enc_copy.zip"), "zzz".getBytes("MS932"));
	}

}
