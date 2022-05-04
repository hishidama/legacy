package jp.hishidama.test;

class TestData {

	String name;

	byte[] data;

	TestData(String name, int len) {
		this.name = name;
		this.data = create(len);
	}

	byte[] create(int len) {
		byte[] data = new byte[len];
		String slen = len + "\r\n";
		for (int i = 0; i < len; i++) {
			data[i] = (byte) slen.charAt(i % slen.length());
		}
		return data;
	}
}
