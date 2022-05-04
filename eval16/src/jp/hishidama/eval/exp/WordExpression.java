package jp.hishidama.eval.exp;

/**
 * 識別子クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public abstract class WordExpression extends AbstractExpression {

	/** 識別子. */
	protected String word;

	/**
	 * コンストラクター.
	 * 
	 * @param str
	 *            識別子
	 */
	protected WordExpression(String str) {
		word = str;
	}

	protected WordExpression(WordExpression from, ShareExpValue s) {
		super(from, s);
		word = from.word;
	}

	@Override
	public String getWord() {
		return word;
	}

	@Override
	protected void setWord(String word) {
		this.word = word;
	}

	@Override
	protected int getCols() {
		return 0;
	}

	@Override
	protected int getFirstPos() {
		return pos;
	}

	@Override
	protected void search() {
		share.srch.search(this);
		if (share.srch.end()) {
			return;
		}
		share.srch.search0(this);
	}

	@Override
	protected AbstractExpression replace() {
		return share.repl.replace0(this);
	}

	@Override
	protected AbstractExpression replaceVar() {
		return share.repl.replaceVar0(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WordExpression) {
			WordExpression e = (WordExpression) obj;
			if (getClass() == e.getClass()) {
				return word.equals(e.word);
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return word.hashCode();
	}

	@Override
	public void dump(int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(' ');
		}
		sb.append(word);
		System.out.println(sb.toString());
	}

	@Override
	public String toString() {
		return word;
	}
}
