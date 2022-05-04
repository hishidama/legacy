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
 * Hadoop Cascading用の拡張Configured.
 * <p>
 * →<a href="http://www.ne.jp/asahi/hishidama/home/tech/apache/hadoop/cascading/wordcount.html#h_ToolRunner"
 * >使用例</a>
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/apache/hadoop/cascading/index.html"
 *         >ひしだま</a>
 * @since 2010.04.16
 */
@SuppressWarnings("deprecation")
public class CascadingConfigured extends Configured {

	private JobConf jobConf;

	private Map<Object, Object> properties;

	/**
	 * JobConf取得.
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
	 * パス完全化.
	 *
	 * @param path
	 *            パス
	 * @return 修飾されたパス
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
	 * プロパティー取得.
	 *
	 * @return プロパティー
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
