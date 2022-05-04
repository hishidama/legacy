package jp.hishidama.html.lexer.rule;

import java.io.IOException;
import java.util.*;

import jp.hishidama.html.lexer.reader.Char;
import jp.hishidama.html.lexer.reader.CharType;
import jp.hishidama.html.lexer.token.*;

/**
 * HtHtmlLexer���[��.
 *<p>
 * HTML�̃^�O�E�����E�e�L�X�g�̉��߂��s���N���X�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.10
 * @version 2009.02.06
 */
public abstract class HtLexerRule {

	protected HtLexer data;

	protected HtLexerRule(HtLexer data) {
		this.data = data;
	}

	protected class Chars {
		private List<Char> d = new ArrayList<Char>();

		/**
		 * �R���X�g���N�^�[.
		 */
		public Chars() {
		}

		/**
		 * �R���X�g���N�^�[.
		 *
		 * @param c
		 *            ����
		 */
		public Chars(Char c) {
			d.add(c);
		}

		/**
		 * �����擾.
		 * <p>
		 * �C���f�b�N�X�Ɏw�肳�ꂽ�ԍ��܂ŕ�����ǂݍ��ށB
		 * </p>
		 *
		 * @param n
		 *            �C���f�b�N�X
		 * @return �����i�K��null�ȊO�j
		 * @throws IOException
		 */
		public Char get(int n) throws IOException {
			for (int i = d.size(); i <= n; i++) {
				d.add(data.readChar());
			}
			return d.get(n);
		}

		public boolean isType(CharType[] types) throws IOException {
			for (int i = 0; i < types.length; i++) {
				if (get(i).getType() != types[i]) {
					return false;
				}
			}
			return true;
		}

		public StringBuilder clear(int use) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < use; i++) {
				sb.append(d.get(i).getSB());
			}
			unread(use);
			return sb;
		}

		protected void unread(int n) {
			for (int i = d.size() - 1; i >= n; i--) {
				data.unreadChar(d.get(i));
			}
			d.clear();
		}
	}

	/**
	 * ���[���ɏ]�������߂��s���B
	 *
	 * @return �g�[�N��
	 * @throws IOException
	 */
	public abstract Token parse() throws IOException;

	protected SeparateRule sepRule;

	/**
	 * ��؂胋�[���ݒ�.
	 *
	 * @param sepRule
	 *            ��؂胋�[��
	 */
	protected void setSepRule(SeparateRule sepRule) {
		this.sepRule = sepRule;
	}

	/**
	 * ��؂蕶�����f.
	 *
	 * @param ch
	 *            ����
	 * @return ���߂��I�����镶���̏ꍇ�Atrue
	 * @throws IOException
	 */
	protected boolean parseEnd(Char ch) throws IOException {
		// if (sepRule != null) {
		return sepRule.isEnd(ch);
		// }
		// return false;
	}

	/**
	 * �X�L�b�v��������.
	 * <p>
	 * �X�y�[�X�E�^�u�E���s�݂̂𒊏o����B
	 * </p>
	 *
	 * @return �X�L�b�v�g�[�N���i�����ꍇ��null�j
	 * @throws IOException
	 */
	public SkipToken skipSpace() throws IOException {
		SkipToken skip = null;
		loop: for (;;) {
			Char ch = data.readChar();
			switch (ch.getType()) {
			case SPACE:
				if (skip == null) {
					skip = createSkipToken();
				}
				skip.add(new SpaceAtom(ch.getSB()));
				continue;
			case EOL:
				if (skip == null) {
					skip = createSkipToken();
				}
				skip.add(new CrLfAtom(ch.getSB()));
				continue;
			default:
				data.unreadChar(ch);
				break loop;
			}
		}
		return skip;
	}

	protected SkipToken createSkipToken() {
		return new SkipToken();
	}
}
