package jp.hishidama.eval.exp;

public class FieldTestA {
	public FieldTestA(int n) {
		this.n = new Integer(n);
	}

	public FieldTestA(double n) {
		this.n = new Double(n);
	}

	public String s = "abc";

	public Number n;

	public int getInt() {
		return n.intValue();
	}

	public long getLong() {
		return n.longValue();
	}

	public double getDbl() {
		return n.doubleValue();
	}

	public int intValue = 123;

	public double dblValue = 12.3;

	public FieldTestB[] b = new FieldTestB[] { new FieldTestB(11),
			new FieldTestB(22), new FieldTestB(33) };

	public FieldTestB[] getB() {
		return b;
	}
}
