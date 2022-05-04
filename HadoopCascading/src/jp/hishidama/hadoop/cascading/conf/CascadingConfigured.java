package jp.hishidama.hadoop.cascading.conf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;

import cascading.flow.FlowConnector;
import cascading.flow.MultiMapReducePlanner;

/**
 * Hadoop Cascading�p�̊g��Configured.
 * <p>
 * ��<a href="http://www.ne.jp/asahi/hishidama/home/tech/apache/hadoop/cascading/wordcount.html#h_ToolRunner"
 * >�g�p��</a>
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/apache/hadoop/cascading/index.html"
 *         >�Ђ�����</a>
 * @since 2010.04.16
 */
@SuppressWarnings("deprecation")
public class CascadingConfigured extends Configured {

	private JobConf jobConf;

	private Map<Object, Object> properties;

	/**
	 * JobConf�擾.
	 *
	 * @return JobConf
	 * @see #getConf()
	 */
	public JobConf getJobConf() {
		if (jobConf == null) {
			jobConf = new JobConf(getConf());
		}
		return jobConf;
	}

	/**
	 * �p�X���S��.
	 *
	 * @param path
	 *            �p�X
	 * @return �C�����ꂽ�p�X
	 * @see #getConf()
	 */
	public String makeQualifiedPath(String path) {
		try {
			FileSystem fs = FileSystem.get(getConf());
			return new Path(path).makeQualified(fs).toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * �v���p�e�B�[�擾.
	 *
	 * @return �v���p�e�B�[
	 * @see #getJobConf()
	 */
	public Map<Object, Object> getProperties() {
		if (properties == null) {
			// properties = new Properties();
			properties = new HashMap<Object, Object>();

			MultiMapReducePlanner.setJobConf(properties, getJobConf());
			FlowConnector.setApplicationJarClass(properties, getClass());
		}
		return properties;
	}
}
