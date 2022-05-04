package jp.hishidama.ant.taskdefs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.PropertyHelper;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.DataType;

/**
 * �}�b�v�v���p�e�B�[�^�X�N.
 * <p>
 * �}�b�v���쐬���A�v���p�e�B�[�ɕێ�����^�X�N�B<br>
 * �ʏ��Ant�̃v���p�e�B�[�ł�String�ȊO�Ή����Ă��Ȃ��ׁA���^�X�N�ŕێ������}�b�v��property�^�X�N���Ŏ擾���悤�Ƃ���Ǝ��s����̂Œ��ӁB
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2010.01.30
 */
public class MapPropertyTask extends Task {

	protected Map<String, Object> map = null;

	protected String name;

	/**
	 * �v���p�e�B�[���ݒ�.
	 *
	 * @param s
	 *            �v���p�e�B�[��
	 */
	public void setName(String s) {
		name = s;
	}

	protected File file;

	/**
	 * �t�@�C�����ݒ�.
	 *
	 * @param f
	 *            �t�@�C��
	 */
	public void setFile(File f) {
		file = f;
	}

	protected String type = "java.util.HashMap";

	/**
	 * �^�ݒ�.
	 *
	 * @param s
	 *            Map�̋�ۃN���X��
	 */
	public void setType(String s) {
		type = s;
	}

	@SuppressWarnings("unchecked")
	protected void initMap() {
		if (map == null) {
			try {
				map = (Map<String, Object>) Class.forName(type).newInstance();
			} catch (Exception e) {
				throw new BuildException(e, getLocation());
			}
		}
	}

	/**
	 * �G���g���[�ǉ�.
	 *
	 * @param entry
	 *            �G���g���[
	 */
	public void addConfiguredEntry(MapEntry entry) {
		initMap();
		map.put(entry.key, entry.value);
	}

	@Override
	public void execute() throws BuildException {
		initExecute();

		PropertyHelper helper = PropertyHelper.getPropertyHelper(getProject());
		helper.setProperty(name, map, false);
	}

	public Map<String, Object> initExecute() {
		initMap();
		loadFile();
		return map;
	}

	protected void loadFile() {
		if (file == null) {
			return;
		}

		// @see Property#loadFile()
		Properties props = new Properties();
		log("Loading " + file.getAbsolutePath(), Project.MSG_VERBOSE);
		try {
			if (file.exists()) {
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(file);
					props.load(fis);
				} finally {
					if (fis != null) {
						fis.close();
					}
				}
				addMap(props);
			} else {
				log("Unable to find property file: " + file.getAbsolutePath(),
						Project.MSG_VERBOSE);
			}
		} catch (IOException ex) {
			throw new BuildException(ex, getLocation());
		}
	}

	protected void addMap(Properties props) {
		Enumeration<?> e = props.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = props.getProperty(key);

			map.put(key, value);
		}
	}

	public static class MapEntry extends DataType {

		protected String key;

		public void setKey(String s) {
			key = s;
		}

		public String getKey() {
			return key;
		}

		protected String value;

		public void setValue(String s) {
			value = s;
		}

		public String getValue() {
			return value;
		}
	}
}
