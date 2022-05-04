package jp.hishidama.ant.types;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.hishidama.ant.taskdefs.MapPropertyTask;
import jp.hishidama.ant.taskdefs.MapPropertyTask.MapEntry;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.PropertyHelper;
import org.apache.tools.ant.types.DataType;
import org.apache.tools.ant.util.FileNameMapper;

/**
 * �}�b�v���g�p�����}�b�p�[.
 * <p>
 * copy�Emove�^�X�N��mapper�Ɏg�p����B<br>
 * �R�s�[�E�ړ����̕ϊ����t�@�C�����E�ϊ���t�@�C�������}�b�v�i�L�[�F�ϊ����t�@�C�����A�l�F�ϊ���t�@�C�����j�ŕێ�����B<br>
 * �w�肳�ꂽ�}�b�v�̕ϊ����t�@�C�����ɑ��݂��Ȃ��t�@�C���́Acopy�Emove�Ώۂ��珜�O�����B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2010.02.11
 */
public class MapMapper extends DataType implements FileNameMapper {

	protected Map<String, String> map;

	protected String removePrefix;
	protected String from, to;

	/**
	 * ��������ړ�����ݒ�.
	 * <p>
	 * {@link #mapFileName(String)}�ɂ̓t�@�C���������΃p�X�œ����Ă���B<br>
	 * �}�b�v�ɐ�΃p�X�Ŏw�肷��ꍇ�A���ړ�����ݒ肵�Ă����ƁA��΃p�X�̐ړ����������������Ĕ��肷��B
	 * </p>
	 *
	 * @param s
	 *            �ړ���
	 */
	public void setPrefix(String s) {
		// ��؂蕶�����@��Ǝ��̂��̂ɕϊ�����
		if (s.indexOf('/') >= 0) {
			s = s.replace('/', File.separatorChar);
		}
		removePrefix = s;
	}

	/**
	 * �}�b�v�̃L�[�ƂȂ�ϊ����t�@�C������ێ�.
	 *
	 * @param from
	 *            �ϊ����t�@�C����
	 * @see #addConfiguredEntry(MapPropertyTask.MapEntry)
	 */
	@Override
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * �}�b�v�̒l�ƂȂ�ϊ���t�@�C������ێ�.
	 *
	 * @param to
	 *            �ϊ���t�@�C����
	 * @see #addConfiguredEntry(MapPropertyTask.MapEntry)
	 */
	@Override
	public void setTo(String to) {
		this.to = to;
	}

	protected String propertyName;

	/**
	 * �}�b�v�v���p�e�B�[���ݒ�.
	 * <p>
	 * ���̃v���p�e�B�[�̒l�́AMap�łȂ���΂Ȃ�Ȃ��B
	 * </p>
	 *
	 * @param name
	 *            �v���p�e�B�[��
	 * @see MapPropertyTask
	 */
	public void setProperty(String name) {
		propertyName = name;
	}

	protected File file;

	/**
	 * �v���p�e�B�[�t�@�C�����ݒ�.
	 * <p>
	 * �v���p�e�B�[�t�@�C�����̃v���p�e�B�[�����ϊ����t�@�C�����A�l���ϊ���t�@�C�����Ƃ��Ĉ�����B
	 * </p>
	 *
	 * @param f
	 *            �v���p�e�B�[�t�@�C��
	 */
	public void setFile(File f) {
		this.file = f;
	}

	protected List<MapPropertyTask> mptaskList;

	/**
	 * �}�b�v�v���p�e�B�[�ǉ�.
	 *
	 * @param mptask
	 *            �}�b�v�v���p�e�B�[�^�X�N
	 */
	public void addConfiguredMapProperty(MapPropertyTask mptask) {
		if (mptaskList == null) {
			mptaskList = new ArrayList<MapPropertyTask>();
		}
		mptaskList.add(mptask);
		mpentry = null;
	}

	protected MapPropertyTask mpentry;

	/**
	 * �}�b�v�G���g���[�ǉ�.
	 *
	 * @param entry
	 *            �}�b�v�G���g���[�i�L�[�͕ϊ����t�@�C�����A�l�͕ϊ���t�@�C�����j
	 */
	public void addConfiguredEntry(MapEntry entry) {
		if (mpentry == null) {
			mpentry = new MapPropertyTask();
			mpentry.setProject(getProject());
			mpentry.setLocation(entry.getLocation());
			if (mptaskList == null) {
				mptaskList = new ArrayList<MapPropertyTask>();
			}
			mptaskList.add(mpentry);
		}
		mpentry.addConfiguredEntry(entry);
	}

	protected void initMap() {
		if (map != null) {
			return;
		}
		map = new HashMap<String, String>();

		if (file != null) {
			MapPropertyTask mptask = new MapPropertyTask();
			mptask.setProject(getProject());
			mptask.setLocation(getLocation());
			mptask.setFile(file);
			putAll(mptask.initExecute());
		}

		if (propertyName != null) {
			PropertyHelper ph = PropertyHelper.getPropertyHelper(getProject());
			Map<?, ?> prop;
			try {
				prop = (Map<?, ?>) ph.getProperty(propertyName);
			} catch (ClassCastException e) {
				throw new BuildException("property[" + propertyName
						+ "] is not Map.", e, getLocation());
			}
			putAll(prop);
		}

		if (mptaskList != null) {
			for (MapPropertyTask mptask : mptaskList) {
				putAll(mptask.initExecute());
			}
		}

		if (from != null || to != null) {
			put(from, to);
		}

		log("MapMapper: " + map, Project.MSG_VERBOSE);
	}

	protected void putAll(Map<?, ?> other) {
		if (other != null) {
			for (Entry<?, ?> entry : other.entrySet()) {
				Object key = entry.getKey();
				Object val = entry.getValue();
				put(key, val);
			}
		}
	}

	protected void put(Object key, Object value) {
		String skey = toString(key);
		String sval = toString(value);
		map.put(skey, sval);
	}

	protected String toString(Object obj) {
		if (obj == null) {
			return null;
		}
		String s = obj.toString();

		// ��؂蕶�����@��Ǝ��̂��̂ɕϊ�����
		if (s.indexOf('/') >= 0) {
			s = s.replace('/', File.separatorChar);
		}

		if (removePrefix != null) {
			if (s.startsWith(removePrefix)) {
				s = s.substring(removePrefix.length());
			}
		}

		return s;
	}

	/**
	 * �t�@�C������ϊ�����.
	 * <p>
	 * sourceFileName�͐�΃p�X�ł͂Ȃ��Afileset��dir����̑��΃p�X�B<br>
	 * �f�B���N�g���[�̋�؂蕶���͋@��ˑ��iWindows�̏ꍇ�́u\�v�j�B
	 * </p>
	 * <p>
	 * ���N���X�ł́AsourceFileName���}�b�v�i�̃L�[�j�ɖ���������A����i�R�s�[�E�ړ��j�̑ΏۊO�Ƃ���inull��Ԃ��j�B
	 * </p>
	 *
	 * @param sourceFileName
	 *            �ϊ����t�@�C����
	 * @return �ϊ���t�@�C������ێ������z��inull�̏ꍇ�A�R�s�[��ړ��̑ΏۂɂȂ�Ȃ��j
	 */
	@Override
	public String[] mapFileName(String sourceFileName) {
		initMap();

		String convertFileName = map.get(sourceFileName);
		if (convertFileName == null) {
			// null��Ԃ��ƁA�R�s�[(�ړ�)�ΏۂɂȂ�Ȃ��B
			return null;
		}

		return new String[] { convertFileName };
	}
}
