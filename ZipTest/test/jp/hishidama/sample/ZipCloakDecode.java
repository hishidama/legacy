package jp.hishidama.sample;

import java.io.File;
import java.io.IOException;

import jp.hishidama.zip.ZipCloak;

public class ZipCloakDecode {

	public static void main(String[] args) throws IOException {
		String dir = "experiment/";

		File s = new File(dir + "test_src_abc.zip");
		File d = new File("test_decrypt.zip");
		String passwd = "abc";
		ZipCloak zip = new ZipCloak(s);
		zip.decrypt(d, passwd.getBytes("MS932"));

		// zip = new ZipCloak(s);
		zip.setFix(1);
		zip.decrypt(new File(dir + "dec_fix.zip"), passwd.getBytes("MS932"));

		// zip = new ZipCloak(s);
		zip.setAdjust(1);
		zip.decrypt(new File(dir + "dec_adjust.zip"), passwd.getBytes("MS932"));

		// zip = new ZipCloak(s);
		zip.setDosify(1);
		zip.decrypt(new File(dir + "dec_dos.zip"), passwd.getBytes("MS932"));
	}

}
