/*
 * �쐬��: 2004/03/14
 */
package jp.hishidama.bitmap;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * �p���b�g���g���r�b�g�}�b�v�̃X�[�p�[�N���X
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">�Ђ�����</a>
 */
public abstract class AbstractPaletteBitmap extends AbstractBitmap implements PaletteBitmap {

	AbstractPaletteBitmap(int nx,int ny){
		super(nx,ny);
		pals=new BitmapInfoColors(1<<getClassBitCount());
	}
	AbstractPaletteBitmap(BitmapInfoHeader bi,BitmapInfoColors pals){
		super(bi);
		this.pals=pals;
	}

	/**
	 * �F�R�[�h��RGB��Ԃ�
	 * @param c �F�R�[�h
	 * @return RGB
	 */
	public int getRGB(int c){
		return pals.getRGB(c);
	}

	/**
	 * RGB�ɊY������F�R�[�h��Ԃ�
	 * @param rgb
	 * @return �F�R�[�h
	 */
	public int getColor(int rgb){
		return pals.getColor(rgb);
	}

	/**
	 * �p���b�g��ݒ肷��
	 * @param c	�F�R�[�h
	 * @param r
	 * @param g
	 * @param b
	 */
	public void setPalet(int c,int r,int g,int b){
//		System.out.println(c+":"+r+","+g+","+b);
		pals.setPalet(c,r,g,b);
	}

	/**
	 * �t�@�C���֕ۑ�����
	 * @param output
	 * @return ���������ꍇ�Atrue
	 */
	public boolean save(FileOutputStream output){

		BitmapFileHeader fh=FileBitmapControl.createBitmapFileHeader(bi,pals);
		bi.setSizeImage(fh.getSize()-fh.getOffBits());
		bi.setColorUsed(pals.getCount());

		try {
			output.write(fh.getBytes());
			output.write(bi.getBytes());
			output.write(pals.getPalBytes());	//�p���b�g�f�[�^
			byte[] line=new byte[getPitch()];
			for(int y=bi.getHeight()-1;y>=0;y--){
				getDataBytes(y,line);
				output.write(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
