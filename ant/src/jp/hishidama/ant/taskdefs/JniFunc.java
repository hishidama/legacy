package jp.hishidama.ant.taskdefs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

/**
 * JNI�w�b�_�[�t�@�C���֐����o.
 * <p>
 * javah�ɂ���Đ������ꂽ�w�b�_�[�t�@�C�����̊֐���񋓂��܂��B
 * </p>
 * <p>
 * ���̃t�@�C����C++�̃\�[�X�Ŕz��ϐ��̏����l�Ƃ���#include���ăR���p�C���E�����N���邱�Ƃɂ��A�������̊֐����G���[�ɂȂ�̂ŕ�����Ƃ������@�ł��B<br>��<a
 * target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/jnif.html">�g�p��</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant.html">�Ђ�����</a>
 * @since 2007.10.07
 */
public class JniFunc extends Task {

	/** �o�̓t�@�C�����ړ��� */
	public String PREFIX = "jni_func.";

	/** �o�̓t�@�C�����ڔ��� */
	public String SUFFIX = ".txt";

	protected File destDir = null;

	protected boolean force = false;

	/**
	 * build.xml�Ŏw�肳�ꂽFileSet�̈ꗗ
	 */
	protected List fss = new ArrayList();

	public void addFileset(FileSet set) {
		fss.add(set);
	}

	/**
	 * �ړ����w��.
	 * <p>
	 * �o�͂���t�@�C�����̐ړ������w�肷��B
	 * </p>
	 * 
	 * @param pre
	 *            �ړ���
	 */
	public void setPrefix(String pre) {
		this.PREFIX = pre;
	}

	/**
	 * �ڔ����w��.
	 * <p>
	 * �o�͂���t�@�C�����̐ڔ������w�肷��B
	 * </p>
	 * 
	 * @param suf
	 *            �ڔ���
	 */
	public void setSuffix(String suf) {
		this.SUFFIX = suf;
	}

	/**
	 * �o�͐�f�B���N�g���w��.
	 * 
	 * @param dir
	 *            �f�B���N�g��
	 */
	public void setDestdir(File dir) {
		this.destDir = dir;
	}

	/**
	 * �����o�͎w��.
	 * <p>
	 * false�i�f�t�H���g�j���ƁA���ƂȂ�javah�ɂ��w�b�_�[�t�@�C���Ɠ��^�X�N�ɂ���Đ��������t�@�C���̃^�C���X�^���v���r���A�V�����Ƃ������쐬����B<br>
	 * true���Ɩ��֌W�ɍ쐬����B
	 * </p>
	 * 
	 * @param f
	 *            true�F�����㏑������
	 */
	public void setForce(boolean f) {
		this.force = f;
	}

	public void execute() throws BuildException {
		for (int i = 0; i < fss.size(); i++) {
			FileSet fs = (FileSet) fss.get(i);
			DirectoryScanner ds = fs.getDirectoryScanner(getProject());
			String[] names = ds.getIncludedFiles();
			for (int j = 0; j < names.length; j++) {
				File f = new File(ds.getBasedir(), names[j]);
				execute(f);
			}
		}
	}

	/**
	 * �t�@�C������
	 * 
	 * @param f
	 *            �t�@�C��
	 * @throws BuildException
	 */
	protected void execute(File f) throws BuildException {
		File parent = (destDir != null) ? destDir : f.getParentFile();
		File wf = new File(parent, PREFIX + f.getName() + SUFFIX);

		if (!force && f.lastModified() < wf.lastModified()) {
			log("skip: " + f.getAbsolutePath(), Project.MSG_DEBUG);
			return;
		}
		log(f.getAbsolutePath(), Project.MSG_INFO);
		log(wf.getAbsolutePath(), Project.MSG_DEBUG);

		BufferedReader br = null;
		PrintStream ps = null;
		try {
			br = new BufferedReader(new FileReader(f));
			ps = new PrintStream(new FileOutputStream(wf));

			// ps.print("#include \"");
			// ps.print(f.getName());
			// ps.println("\"");

			// ps.print("void* ");
			// ps.print(f.getName().replace('.', '_'));
			// ps.println("[] = {");

			execute(br, ps);

			// ps.println("};");
		} catch (Exception e) {
			throw new BuildException(e);
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
				}
			if (ps != null)
				ps.close();
		}
	}

	protected final String KEYWORD = " JNICALL ";

	protected void execute(BufferedReader br, PrintStream ps) throws Exception {

		for (;;) {
			String line = br.readLine();
			if (line == null)
				break;
			int i = line.indexOf(KEYWORD);
			if (i > 0) {
				i += KEYWORD.length();
				ps.print("\t");
				ps.print(line.substring(i));
				ps.println(",");
			}
		}
	}
}
