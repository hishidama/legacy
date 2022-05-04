package jp.hishidama.sample.sql.oracle;

/**
 * Oracle���O�C�����
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/sql.html"
 *         >�Ђ�����</a>
 * @since 2007.09.14
 */
public class OracleLoginInfo {
	private String server;

	private String sid;

	private String user;

	private String pwd;

	public OracleLoginInfo(String server, String sid, String user, String pwd) {
		this.server = server;
		this.sid = sid;
		this.user = user;
		this.pwd = pwd;
	}

	public String getServer() {
		return server;
	}

	public String getPort() {
		return "1521";
	}

	public String getSid() {
		return sid;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return pwd;
	}
}