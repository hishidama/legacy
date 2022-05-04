package jp.hishidama.ant.types;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.DataType;
import org.apache.tools.ant.types.Parameter;

/**
 * ログ情報データ.
 * 
 * @since 2007.03.10
 */
public class LogFile extends DataType {

	protected File logfile;

	protected List msgList = new ArrayList();

	protected File prop;

	public void setFile(File f) {
		logfile = f;
	}

	public File getFile() {
		return logfile;
	}

	public void setProperty(File f) {
		prop = f;
	}

	public void addParam(Parameter param) {
		msgList.add(param);
	}

	public Map getMessageMap() {
		Properties p = new Properties();
		if (prop != null)
			try {
				p.load(new FileInputStream(prop));
			} catch (Exception e) {
				throw new BuildException(e);
			}

		for (int i = 0; i < msgList.size(); i++) {
			Parameter param = (Parameter) msgList.get(i);
			String key = param.getName();
			String msg = param.getValue();
			p.put(key, msg);
		}
		return p;
	}
}
