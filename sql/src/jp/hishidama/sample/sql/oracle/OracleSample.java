package jp.hishidama.sample.sql.oracle;

import java.sql.*;

import jp.hishidama.sql.SqlUtil;

/**
 * Oracleアクセスサンプル.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/sql.html"
 *         >ひしだま</a>
 * @since 2007.09.14
 * @version 2009.03.20
 */
public class OracleSample extends SqlUtil {

	public static final OracleLoginInfo LOCAL_SCOTT = new OracleLoginInfo(
			"localhost", "ora92", "scott", "tiger");

	public static void main(String[] args) throws Exception {
		OracleLoginInfo db = LOCAL_SCOTT;

		Class.forName("oracle.jdbc.driver.OracleDriver");

		String url = "jdbc:oracle:thin:@" + db.getServer() + ":" + db.getPort()
				+ ":" + db.getSid();
		// String url = "jdbc:oracle:oci:@" + db.getSid();

		String usr = db.getUser();
		String pwd = db.getPassword();
		Connection conn = DriverManager.getConnection(url, usr, pwd);
		conn.setAutoCommit(false);
		try {
			executeEmp(conn);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}

	public static void executeEmp(Connection conn) {
		for (int empno = 9901; empno <= 9910; empno++) {
			System.out.println(empno);

			// String sql = createSqlInsertEmp(empno);
			String sql = createSqlUpdateEmp(empno);
			// System.out.println(sql);

			int count = execute(conn, sql);
			System.out.println(count);
		}
	}

	public static String createSqlInsertEmp(int empno) {
		StringBuffer sb = new StringBuffer(512);
		StringBuffer cb = new StringBuffer(256);
		StringBuffer vb = new StringBuffer(256);
		sb.append("insert into ");
		sb.append("EMP");
		sb.append("(");
		append(cb, vb, "EMPNO", empno, false);
		append(cb, vb, "ENAME", "name" + empno, true);
		append(cb, vb, "JOB", "job" + empno, true);
		append(cb, vb, "MGR", 0, false);
		// append(cb, vb, "HIREDATE", "sysdate", false);
		append(cb, vb, "HIREDATE", "to_date('2009/03/20','YYYY/MM/DD')", false);
		append(cb, vb, "SAL", 1000, false);
		append(cb, vb, "COMM", 0, false);
		append(cb, vb, "DEPTNO", 10, false);
		sb.append(cb);
		sb.append(")values(");
		sb.append(vb);
		sb.append(")");

		return sb.toString();
	}

	public static String createSqlUpdateEmp(int empno) {
		StringBuffer sb = new StringBuffer(512);
		sb.append("update ");
		sb.append("EMP");
		sb.append(" set ");
		append(sb, "SAL", "SAL+10", false);
		append(sb, "COMM", "COMM+1", false);
		sb.append(" where EMPNO=");
		sb.append(empno);

		return sb.toString();
	}

}
