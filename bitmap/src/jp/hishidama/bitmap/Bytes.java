/*
 * �쐬��: 2004/02/29
 */
package jp.hishidama.bitmap;

/**
 * �ۑ����Ƀo�C�g��ɕϊ��������\���̗p�̃X�[�p�[�N���X
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">�Ђ�����</a>
 */
public abstract class Bytes {
	
	/**
	 * �o�C�g���Ԃ�
	 * @return �o�C�g��
	 */
	public abstract byte[] getBytes();
	/**
	 * �o�C�g��̒�����Ԃ�
	 * @return ����
	 */
	public abstract int getBytesLength();
	/**
	 * �o�C�g�񂩂珉��������
	 * @param data
	 */
	public abstract void init(byte[] data);

	/**
	 * short�^���o�C�g��ɕϊ�����
	 * @param data
	 * @param pos
	 * @param var
	 * @return ���̈ʒu
	 */
	public static int set(byte[] data,int pos,short var){
		if(data!=null){
			data[pos+0]=(byte)(var);
			data[pos+1]=(byte)(var>>8);
		}
		return pos+2;
	}

	/**
	 * int�^���o�C�g��ɕϊ�����
	 * @param data
	 * @param pos
	 * @param var
	 * @return ���̈ʒu
	 */
	public static int set(byte[] data,int pos,int var){
		if(data!=null){
			data[pos+0]=(byte)(var);
			data[pos+1]=(byte)(var>>8);
			data[pos+2]=(byte)(var>>16);
			data[pos+3]=(byte)(var>>24);
		}
		return pos+4;
	}

	/**
	 * �o�C�g�񂩂�short�^���擾����
	 * @param data
	 * @param rpos
	 * @param var
	 * @return ���l
	 */
	public static short get(byte[] data,int[] rpos,short var){
		int pos=rpos[0];
		var =data[pos+0];
		var|=data[pos+1]<<8;
		rpos[0]=pos+2;
		return var;
	}

	/**
	 * �o�C�g�񂩂�short�^���擾����
	 * @param data
	 * @param rpos
	 * @param var
	 * @return ���l
	 */
	public static int get(byte[] data,int[] rpos,int var){
		int pos=rpos[0];
		var=data[pos+0];
		var|=data[pos+1]<<8;
		var|=data[pos+2]<<16;
		var|=data[pos+3]<<24;
		rpos[0]=pos+4;
		return var;
	}
}
