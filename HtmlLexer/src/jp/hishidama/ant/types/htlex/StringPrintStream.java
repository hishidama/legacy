package jp.hishidama.ant.types.htlex;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

class StringPrintStream extends PrintStream {

	protected StringBuilder sb = new StringBuilder();

	public StringPrintStream() {
		super(new DummyOutputStream());
	}

	protected static class DummyOutputStream extends OutputStream {

		@Override
		public void write(int b) throws IOException {
			throw new UnsupportedOperationException();
		}
	}

	public void clear() {
		sb.setLength(0);
	}

	@Override
	public PrintStream append(char c) {
		sb.append(c);
		return this;
	}

	@Override
	public PrintStream append(CharSequence csq, int start, int end) {
		sb.append(csq, start, end);
		return this;
	}

	@Override
	public PrintStream append(CharSequence csq) {
		sb.append(csq);
		return this;
	}

	public boolean isEmpty() {
		return sb.length() == 0;
	}

	@Override
	public String toString() {
		return sb.toString();
	}
}
