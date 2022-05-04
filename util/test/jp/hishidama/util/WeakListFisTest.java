package jp.hishidama.util;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;

public class WeakListFisTest {
	private WeakList<FileInputStream> fisList = new WeakList<FileInputStream>();

	@Test
	public void main() throws IOException {
		try {
			loop();
		} finally {
			for (FileInputStream fis : fisList) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
		}
	}

	void loop() throws IOException {
		for (int i = 0; i < 100; i++) {
			FileInputStream fis = new FileInputStream("C:/temp/t.txt") {

				@Override
				public void close() throws IOException {
					System.out.println("close");
					super.close();
				}
			};
			fisList.add(fis);

			fis.read();
			// fisのクローズはしない

			if (i % 30 == 0) {
				System.gc();
			}
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		System.out.println("loop end");
	}
}
