/*
 * �쐬��: 2004/03/14
 */
package jp.hishidama.bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * �r�b�g�}�b�v�����N���X
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">�Ђ�����</a>
 */
public class BitmapFactory {

	/**
	 * �������[��ő��삷��r�b�g�}�b�v�𐶐�����
	 * @param nx �摜�T�C�YX
	 * @param ny �摜�T�C�YY
	 * @param bitCount �F���i�r�b�g�P�ʁj
	 * @return �r�b�g�}�b�v
	 */
	public static Bitmap createMemeryBitmap(int nx,int ny,int bitCount){
		Bitmap bmp;
		switch(bitCount){
			case  4:	bmp=new MemoryBitmap04(nx,ny);break;
			case  8:	bmp=new MemoryBitmap08(nx,ny);break;
			case 16:	bmp=new MemoryBitmap16(nx,ny);break;
			case 24:	bmp=new MemoryBitmap24(nx,ny);break;
			case 32:	bmp=new MemoryBitmap32(nx,ny);break;
			default:	throw new RuntimeException("����`�r�b�g�}�b�v�@�F���F"+bitCount);
		}
		return bmp;
	}

	/**
	 * �t�@�C����ő��삷��r�b�g�}�b�v�𐶐�����<br>
	 * �i�t�@�C�����V���������j
	 * @param pathName �r�b�g�}�b�v�t�@�C���̃p�X��
	 * @param fileName �r�b�g�}�b�v�̃t�@�C����
	 * @param nx �摜�T�C�YX
	 * @param ny �摜�T�C�YY
	 * @param bitCount �F���i�r�b�g�P�ʁj
	 * @return �r�b�g�}�b�v
	 */
	public static Bitmap createFileBitmap(String pathName,String fileName,int nx,int ny,int bitCount){
		FileBitmap bmp;
		switch(bitCount){
			case  4:	bmp=new FileBitmap04(nx,ny,pathName,fileName);break;
			case  8:	bmp=new FileBitmap08(nx,ny,pathName,fileName);break;
			case 16:	bmp=new FileBitmap16(nx,ny,pathName,fileName);break;
			case 24:	bmp=new FileBitmap24(nx,ny,pathName,fileName);break;
			case 32:	bmp=new FileBitmap32(nx,ny,pathName,fileName);break;
			default:	throw new RuntimeException("����`�r�b�g�}�b�v�@�F���F"+bitCount);
		}
		return bmp;
	}

	/**
	 * �������[��ő��삷��r�b�g�}�b�v�����[�h����
	 * @param pathName �r�b�g�}�b�v�t�@�C���̃p�X��
	 * @param fileName �r�b�g�}�b�v�̃t�@�C����
	 * @return �r�b�g�}�b�v
	 */
	public static Bitmap loadMemoryBitmap(String pathName,String fileName){
		BitmapFileHeader fh=new BitmapFileHeader();
		BitmapInfoHeader bi=new BitmapInfoHeader();
		BitmapInfoColors pl=new BitmapInfoColors(0);

		File file=loadHeader(pathName,fileName,fh,bi,pl);
		if(file==null) return null;

		Bitmap bmp;
		switch(bi.getBitCount()){
			case  4:	bmp=new MemoryBitmap04(bi,pl);break;
			case  8:	bmp=new MemoryBitmap08(bi,pl);break;
			case 16:	bmp=new MemoryBitmap16(bi);break;
			case 24:	bmp=new MemoryBitmap24(bi);break;
			case 32:	bmp=new MemoryBitmap32(bi);break;
			default:	throw new RuntimeException("����`�r�b�g�}�b�v�@�F���F"+bi.getBitCount());
		}

		FileInputStream input;
		try {
			input=new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try {
			int pitch=bmp.getPitch();
			long pos=FileBitmapControl.getFileOffset(fh,bi,0,bi.getHeight()-1,pitch);
			input.skip(pos);

			byte[] line=new byte[pitch];
			for(int y=bi.getHeight()-1;y>=0;y--){
				input.read(line);
				((AbstractBitmap)bmp).setDataBytes(y,line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bmp;
	}

	/**
	 * �t�@�C����ő��삷��r�b�g�}�b�v���I�[�v������
	 * @param pathName �r�b�g�}�b�v�t�@�C���̃p�X��
	 * @param fileName �r�b�g�}�b�v�̃t�@�C����
	 * @return �r�b�g�}�b�v
	 */
	public static FileBitmap openFileBitmap(String pathName,String fileName){
		BitmapFileHeader fh=new BitmapFileHeader();
		BitmapInfoHeader bi=new BitmapInfoHeader();
		BitmapInfoColors pl=new BitmapInfoColors(0);

		File file=loadHeader(pathName,fileName,fh,bi,pl);
		if(file==null) return null;

		return openFileBitmap(file,fh,bi,pl);
	}

	/**
	 * �t�@�C����ň����r�b�g�}�b�v���I�[�v������B
	 * @param file �t�@�C��
	 * @param fh �r�b�g�}�b�v�t�@�C���w�b�_�[
	 * @param bi �r�b�g�}�b�v���w�b�_�[
	 * @param pl �r�b�g�}�b�v�p���b�g
	 * @return �t�@�C���r�b�g�}�b�v
	 */
	static FileBitmap openFileBitmap(File file,BitmapFileHeader fh,BitmapInfoHeader bi,BitmapInfoColors pl){
		FileBitmap bmp;
		switch(bi.getBitCount()){
			case  4:	bmp=new FileBitmap04(file,fh,bi,pl);break;
			case  8:	bmp=new FileBitmap08(file,fh,bi,pl);break;
			case 16:	bmp=new FileBitmap16(file,fh,bi);break;
			case 24:	bmp=new FileBitmap24(file,fh,bi);break;
			case 32:	bmp=new FileBitmap32(file,fh,bi);break;
			default:	throw new RuntimeException("����`�r�b�g�}�b�v�@�F���F"+bi.getBitCount());
		}
		return bmp;
	}

	/**
	 * �r�b�g�}�b�v�w�b�_�[�ނ��t�@�C������ǂݍ���
	 * @param pathName �t�@�C���̃p�X��
	 * @param fileName �t�@�C����
	 * @param fh �ǂݍ��񂾃w�b�_�[��Ԃ��ׂ̃r�b�g�}�b�v�t�@�C���w�b�_�[
	 * @param bi �ǂݍ��񂾃w�b�_�[��Ԃ��ׂ̃r�b�g�}�b�v���w�b�_�[
	 * @param pl �ǂݍ��񂾃p���b�g��Ԃ��ׂ̃r�b�g�}�b�v�p���b�g
	 * @return �t�@�C��
	 */
	static File loadHeader(String pathName,String fileName,BitmapFileHeader fh,BitmapInfoHeader bi,BitmapInfoColors pl){
		File file=new File(pathName,fileName);
		FileInputStream input;
		try {
			input=new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		//�r�b�g�}�b�v�t�@�C���w�b�_�[�̓ǂݍ���
		final int fhlen=fh.getBytesLength();
		byte[] fhdata=new byte[fhlen];
		try {
			input.read(fhdata);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		fh.init(fhdata);

		//�r�b�g�}�b�v���w�b�_�[�̓ǂݍ���
		final int bilen=bi.getBytesLength();
		byte[] bidata=new byte[bilen];
		try {
			input.read(bidata);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		bi.init(bidata);

		//�p���b�g�̓ǂݍ���
		final int palPos=fhlen+bi.getSize();
		final int palLen=fh.getOffBits()-palPos; 
		if(palLen>0){
			byte[] pldata=new byte[palLen];
			try {
				input.read(pldata);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			pl.init(pldata);
		}

		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}


}
