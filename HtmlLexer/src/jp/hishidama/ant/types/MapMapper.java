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
 * マップを使用したマッパー.
 * <p>
 * copy・moveタスクのmapperに使用する。<br>
 * コピー・移動時の変換元ファイル名・変換先ファイル名をマップ（キー：変換元ファイル名、値：変換先ファイル名）で保持する。<br>
 * 指定されたマップの変換元ファイル名に存在しないファイルは、copy・move対象から除外される。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2010.02.11
 */
public class MapMapper extends DataType implements FileNameMapper {

	protected Map<String, String> map;

	protected String removePrefix;
	protected String from, to;

	/**
	 * 除去する接頭辞を設定.
	 * <p>
	 * {@link #mapFileName(String)}にはファイル名が相対パスで入ってくる。<br>
	 * マップに絶対パスで指定する場合、当接頭辞を設定しておくと、絶対パスの接頭辞部分を除去して判定する。
	 * </p>
	 *
	 * @param s
	 *            接頭辞
	 */
	public void setPrefix(String s) {
		// 区切り文字を機種独自のものに変換する
		if (s.indexOf('/') >= 0) {
			s = s.replace('/', File.separatorChar);
		}
		removePrefix = s;
	}

	/**
	 * マップのキーとなる変換元ファイル名を保持.
	 *
	 * @param from
	 *            変換元ファイル名
	 * @see #addConfiguredEntry(MapPropertyTask.MapEntry)
	 */
	@Override
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * マップの値となる変換先ファイル名を保持.
	 *
	 * @param to
	 *            変換先ファイル名
	 * @see #addConfiguredEntry(MapPropertyTask.MapEntry)
	 */
	@Override
	public void setTo(String to) {
		this.to = to;
	}

	protected String propertyName;

	/**
	 * マッププロパティー名設定.
	 * <p>
	 * このプロパティーの値は、Mapでなければならない。
	 * </p>
	 *
	 * @param name
	 *            プロパティー名
	 * @see MapPropertyTask
	 */
	public void setProperty(String name) {
		propertyName = name;
	}

	protected File file;

	/**
	 * プロパティーファイル名設定.
	 * <p>
	 * プロパティーファイル内のプロパティー名が変換元ファイル名、値が変換先ファイル名として扱われる。
	 * </p>
	 *
	 * @param f
	 *            プロパティーファイル
	 */
	public void setFile(File f) {
		this.file = f;
	}

	protected List<MapPropertyTask> mptaskList;

	/**
	 * マッププロパティー追加.
	 *
	 * @param mptask
	 *            マッププロパティータスク
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
	 * マップエントリー追加.
	 *
	 * @param entry
	 *            マップエントリー（キーは変換元ファイル名、値は変換先ファイル名）
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

		// 区切り文字を機種独自のものに変換する
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
	 * ファイル名を変換する.
	 * <p>
	 * sourceFileNameは絶対パスではなく、filesetのdirからの相対パス。<br>
	 * ディレクトリーの区切り文字は機種依存（Windowsの場合は「\」）。
	 * </p>
	 * <p>
	 * 当クラスでは、sourceFileNameがマップ（のキー）に無かったら、操作（コピー・移動）の対象外とする（nullを返す）。
	 * </p>
	 *
	 * @param sourceFileName
	 *            変換元ファイル名
	 * @return 変換先ファイル名を保持した配列（nullの場合、コピーや移動の対象にならない）
	 */
	@Override
	public String[] mapFileName(String sourceFileName) {
		initMap();

		String convertFileName = map.get(sourceFileName);
		if (convertFileName == null) {
			// nullを返すと、コピー(移動)対象にならない。
			return null;
		}

		return new String[] { convertFileName };
	}
}
