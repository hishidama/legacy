/*
 * �쐬��: 2004/02/29
 */
package jp.hishidama.bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * �r�b�g�}�b�v�̃X�[�p�[�N���X
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">�Ђ�����</a>
 */
public abstract class AbstractBitmap implements Bitmap {
	/**
	 * �r�b�g�}�b�v���
	 */
	protected BitmapInfoHeader bi;

	/**
	 * �p���b�g
	 */
	protected BitmapInfoColors pals;

	/**
	 * @param nx
	 * @param ny
	 */
	public AbstractBitmap(int nx, int ny) {
		bi = new BitmapInfoHeader();
		bi.setWidth(nx);
		bi.setHeight(ny);
		bi.setBitCount(getClassBitCount());
	}

	/**
	 * @param bi
	 */
	public AbstractBitmap(BitmapInfoHeader bi) {
		this.bi = bi;
		bi.setBitCount(getClassBitCount());
	}

	/**
	 * �T�u�N���X�̐F���i�r�b�g�P�ʁj
	 */
	protected abstract short getClassBitCount();

	/**
	 * �w�肳�ꂽ���W���͈͓����ǂ������肷��
	 * 
	 * @param x
	 * @param y
	 * @return true:�͈͓�
	 */
	public boolean validPos(int x, int y) {
		if (x < 0 || x >= bi.getWidth())
			return false;
		if (y < 0 || y >= bi.getHeight())
			return false;
		return true;
	}

	/**
	 * �w�肳�ꂽ�F�R�[�h���������ǂ������肷��
	 * 
	 * @param c
	 * @return true:����
	 */
	public boolean validColor(int c) {
		return (0 <= c && c < (1 << bi.getBitCount()));
	}

	/**
	 * �w�肳�ꂽ���W�ɐF�R�[�h���Z�b�g����
	 * 
	 * @param x
	 * @param y
	 * @param c
	 *            �F�R�[�h
	 * @return ���������ꍇ�Atrue
	 */
	public boolean set(int x, int y, int c) {
		if (!validPos(x, y))
			return false;
		if (!validColor(c))
			return false;
		return set0(x, y, c);
	}

	/**
	 * �w�肳�ꂽ���W�̐F�R�[�h��Ԃ�
	 * 
	 * @param x
	 * @param y
	 * @return �F�R�[�h
	 */
	public int get(int x, int y) {
		if (!validPos(x, y))
			return INVALID_COLOR;
		return get0(x, y);
	}

	/**
	 * �w�肳�ꂽ���W��RGB���Z�b�g����
	 * 
	 * @param x
	 * @param y
	 * @param rgb
	 * @return ���������ꍇ�Atrue
	 */
	public boolean setRGB(int x, int y, int rgb) {
		int c = getColor(rgb);
		return set(x, y, c);
	}

	/**
	 * �w�肳�ꂽ���W��RGB��Ԃ�
	 * 
	 * @param x
	 * @param y
	 * @return RGB
	 */
	public int getRGB(int x, int y) {
		return getRGB(get(x, y));
	}

	/**
	 * �F�R�[�h��RGB��Ԃ�
	 * 
	 * @param c
	 *            �F�R�[�h
	 * @return RGB
	 */
	public abstract int getRGB(int c);

	/**
	 * �F�R�[�h�̐ԗv�f��Ԃ�
	 * 
	 * @param c
	 *            �F�R�[�h
	 * @return R�i0�`255�j
	 */
	public int getR(int c) {
		return BitmapInfoColors.getRgbR(getRGB(c));
	}

	/**
	 * �F�R�[�h�̗Ηv�f��Ԃ�
	 * 
	 * @param c
	 *            �F�R�[�h
	 * @return G�i0�`255�j
	 */
	public int getG(int c) {
		return BitmapInfoColors.getRgbG(getRGB(c));
	}

	/**
	 * �F�R�[�h�̐v�f��Ԃ�
	 * 
	 * @param c
	 *            �F�R�[�h
	 * @return B�i0�`255�j
	 */
	public int getB(int c) {
		return BitmapInfoColors.getRgbB(getRGB(c));
	}

	/**
	 * (r,g,b)�ɊY������F�R�[�h��Ԃ�
	 * 
	 * @param r
	 *            �i0�`255�j
	 * @param g
	 *            �i0�`255�j
	 * @param b
	 *            �i0�`255�j
	 * @return �F�R�[�h
	 */
	public int getColor(int r, int g, int b) {
		return getColor(BitmapInfoColors.getRGB(r, g, b));
	}

	/**
	 * �h��ׂ�
	 * 
	 * @param sx
	 *            �n�_X
	 * @param sy
	 *            �n�_Y
	 * @param nx
	 *            �T�C�YX
	 * @param ny
	 *            �T�C�YY
	 * @param c
	 *            �F�R�[�h
	 * @return ���������ꍇ�Atrue
	 */
	public boolean fill(int sx, int sy, int nx, int ny, int c) {
		if (nx < 0) {
			sx += nx + 1;
			nx = -nx;
		}
		if (ny < 0) {
			sy += ny + 1;
			ny = -ny;
		}
		for (int y = 0; y < ny; ny++) {
			for (int x = 0; x < nx; x++) {
				set(x, y, c);
			}
		}
		return true;
	}

	/**
	 * ��������
	 * 
	 * @param sx
	 *            �n�_X
	 * @param sy
	 *            �n�_Y
	 * @param nx
	 *            �T�C�YX
	 * @param ny
	 *            �T�C�YY
	 * @param c
	 *            �F�R�[�h
	 * @return ���������ꍇ�Atrue
	 */
	public boolean line(int sx, int sy, int nx, int ny, int c) {
		int ax = nx;
		if (ax < 0)
			ax = -ax;
		int ay = ny;
		if (ay < 0)
			ay = -ay;
		if (ax > ay) {
			return lineNX(sx, sy, nx, ny, c);
		} else {
			return lineNY(sx, sy, nx, ny, c);
		}
	}

	/**
	 * �����̋�`���̐�������
	 * 
	 * @param sx
	 *            �n�_X
	 * @param sy
	 *            �n�_Y
	 * @param nx
	 *            �T�C�YX
	 * @param ny
	 *            �T�C�YY
	 * @param c
	 *            �F�R�[�h
	 * @return ���������ꍇ�Atrue
	 */
	private boolean lineNX(int sx, int sy, int nx, int ny, int c) {
		if (nx < 0) {
			sx += nx + 1;
			nx = -nx;
			sy += ny + (ny < 0 ? +1 : -1);
			ny = -ny;
		}
		for (int x = 0; x < nx; x++) {
			int y = ny * x / nx;
			set(x + sx, y + sy, c);
		}
		return true;
	}

	/**
	 * �c���̋�`���̐�������
	 * 
	 * @param sx
	 *            �n�_X
	 * @param sy
	 *            �n�_Y
	 * @param nx
	 *            �T�C�YX
	 * @param ny
	 *            �T�C�YY
	 * @param c
	 *            �F�R�[�h
	 * @return
	 */
	private boolean lineNY(int sx, int sy, int nx, int ny, int c) {
		if (ny < 0) {
			sx += nx + (nx < 0 ? +1 : -1);
			nx = -nx;
			sy += ny + 1;
			ny = -ny;
		}
		for (int y = 0; y < ny; y++) {
			int x = nx * y / ny;
			set(x + sx, y + sy, c);
		}
		return true;
	}

	/**
	 * �摜���R�s�[����
	 * 
	 * @param dx
	 *            �R�s�[����WX
	 * @param dy
	 *            �R�s�[����WY
	 * @param nx
	 *            �R�s�[�T�C�YX
	 * @param ny
	 *            �R�s�[�T�C�YY
	 * @param src
	 *            �R�s�[���̃r�b�g�}�b�v
	 * @param sx
	 *            �R�s�[��������WX
	 * @param sy
	 *            �R�s�[��������WY
	 */
	public void copy(int dx, int dy, int nx, int ny, Bitmap src, int sx, int sy) {
		if (nx < 0) {
			sx += nx + 1;
			nx = -nx;
		}
		if (ny < 0) {
			sy += ny + 1;
			ny = -ny;
		}
		for (int y = 0; y < ny; y++) {
			for (int x = 0; x < nx; x++) {
				int c = src.getRGB(sx + x, sy + y);
				setRGB(dx + x, dy + y, c);
			}
		}
	}

	/**
	 * ���r�b�g�}�b�v�̃s�b�`��Ԃ�
	 * 
	 * @return �s�b�`
	 */
	public int getPitch() {
		return calcPitch(bi.getBitCount(), bi.getWidth());
	}

	/**
	 * �s�b�`���v�Z����
	 * 
	 * @param bitCount
	 * @param width
	 * @return �s�b�`
	 */
	public static int calcPitch(int bitCount, int width) {
		int pitch;
		if (bitCount < 8) {
			int n = 8 / bitCount;
			pitch = (width + n - 1) / n;
		} else {
			bitCount /= 8;
			pitch = width * bitCount;
		}
		return (pitch + 3) & ~3; // 4�o�C�g���E�ɍ��킹��
	}

	/**
	 * �t�@�C���֕ۑ�
	 * 
	 * @param pathName
	 *            �p�X��
	 * @param fileName
	 *            �t�@�C����
	 */
	public boolean save(String pathName, String fileName) {
		File file = new File(pathName, fileName);
		FileOutputStream output;
		try {
			output = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		boolean ret = save(output);

		try {
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * �t�@�C���֕ۑ�����
	 * 
	 * @param output
	 * @return ���������ꍇ�Atrue
	 */
	public boolean save(FileOutputStream output) {

		BitmapFileHeader fh = FileBitmapControl
				.createBitmapFileHeader(bi, null);
		bi.setSizeImage(fh.getSize() - fh.getOffBits());

		try {
			output.write(fh.getBytes());
			output.write(bi.getBytes());
			byte[] line = new byte[getPitch()];
			for (int y = bi.getHeight() - 1; y >= 0; y--) {
				getDataBytes(y, line);
				output.write(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * �摜�̕ۑ��p�̃o�C�g���Ԃ�
	 * 
	 * @return ���������ꍇ�Atrue
	 */
	protected abstract boolean getDataBytes(int y, byte[] line);

	/**
	 * �o�C�g�񂩂�摜�f�[�^���Z�b�g����
	 * 
	 * @param y
	 * @param line
	 * @return ���������ꍇ�Atrue
	 */
	abstract boolean setDataBytes(int y, byte[] line);

	/**
	 * @return �r�b�g�}�b�v�w�b�_�[���
	 */
	public BitmapInfoHeader getBitmapInfoHeader() {
		return bi;
	}

	/**
	 * @return �p���b�g
	 */
	public BitmapInfoColors getPalets() {
		return pals;
	}

}
