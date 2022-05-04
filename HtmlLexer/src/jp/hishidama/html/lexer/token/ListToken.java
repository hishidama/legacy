package jp.hishidama.html.lexer.token;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * HtHtmlLexer�g�[�N���i���X�g�j.
 *<p>
 * {@link Token HtHtmlLexer�g�[�N��}�̂����A�����ɑ��̃g�[�N����ێ�����g�[�N���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.10
 * @see AtomToken
 */
public class ListToken extends Token implements Iterable<Token> {

	protected final List<Token> list;

	/**
	 * @since 2009.02.07
	 */
	@Override
	public ListToken clone() throws CloneNotSupportedException {
		return new ListToken(this);
	}

	protected ListToken(ListToken o) {
		super(o);
		list = new ArrayList<Token>(o.list.size());
		for (int i = 0; i < o.list.size(); i++) {
			try {
				Token t = o.list.get(i);
				if (t != null) {
					t = t.clone();
				}
				list.add(t);
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * �R���X�g���N�^�[.
	 */
	public ListToken() {
		this(8);
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param size
	 *            �������X�g�̏����T�C�Y
	 */
	public ListToken(int size) {
		list = new ArrayList<Token>(size);
	}

	/**
	 * �g�[�N���ǉ�.
	 *<p>
	 * �������X�g�̖����Ƀg�[�N����ǉ�����B
	 * </p>
	 *
	 * @param token
	 *            �g�[�N��
	 */
	public void add(Token token) {
		list.add(token);
	}

	/**
	 * �g�[�N���ǉ�.
	 * <p>
	 * �������X�g�̎w��ʒu�Ƀg�[�N����}������B
	 * </p>
	 *
	 * @param n
	 *            �ǉ��ʒu
	 * @param token
	 *            �g�[�N��
	 */
	public void add(int n, Token token) {
		if (n < 0) {
			n = 0;
		}
		if (n >= list.size()) {
			add(token);
		} else {
			list.add(n, token);
		}
	}

	protected void set(Enum<?> en, Token token) {
		set(en.ordinal(), token);
	}

	/**
	 * �g�[�N���ݒ�.
	 * <p>
	 * �������X�g�̎w��ʒu�Ƀg�[�N�����Z�b�g����B
	 * </p>
	 *
	 * @param n
	 *            �ݒ�ʒu
	 * @param token
	 *            �g�[�N��
	 */
	public void set(int n, Token token) {
		for (int i = list.size(); i <= n; i++) {
			list.add(null);
		}
		list.set(n, token);
	}

	protected Token get(Enum<?> en) {
		return get(en.ordinal());
	}

	/**
	 * �g�[�N���擾.
	 *
	 * @param n
	 *            �ʒu
	 * @return �g�[�N��
	 */
	public Token get(int n) {
		if (n >= list.size()) {
			return null;
		}
		return list.get(n);
	}

	/**
	 * �����g�[�N���擾.
	 *
	 * @return �g�[�N��
	 */
	public Token getLast() {
		int n = list.size() - 1;
		if (n >= 0) {
			return list.get(n);
		}
		return null;
	}

	/**
	 * �g�[�N�����擾.
	 * <p>
	 * �����ŕێ����Ă���g�[�N���̌���Ԃ��B
	 * </p>
	 *
	 * @return ��
	 */
	public int size() {
		return list.size();
	}

	@Override
	public int getTextLength() {
		int len = 0;
		for (Token token : list) {
			if (token != null) {
				len += token.getTextLength();
			}
		}
		return len;
	}

	@Override
	public void writeTo(StringBuilder sb) {
		for (Token token : list) {
			if (token != null) {
				token.writeTo(sb);
			}
		}
	}

	@Override
	public void writeTo(Writer w) throws IOException {
		for (Token token : list) {
			if (token != null) {
				token.writeTo(w);
			}
		}
	}

	@Override
	public int calcLine(int n) {
		line = n;
		for (Token token : list) {
			if (token != null) {
				n = token.calcLine(n);
			}
		}
		return n;
	}

	@Override
	public Iterator<Token> iterator() {
		return list.iterator();
	}

	/**
	 * �g�[�N���폜.
	 *<p>
	 * ���X�g�ɒ��ڕێ����Ă���g�[�N�����폜����B
	 * </p>
	 *
	 * @param t
	 *            �폜�Ώۃg�[�N��
	 * @return �폜�ł����ꍇ�A���̃g�[�N��<br>
	 *         �폜�Ώۂ����������ꍇ��null
	 */
	public Token remove(Token t) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == t) {
				return list.remove(i);
			}
		}
		return null;
	}

	/**
	 * �g�[�N���؂�o��.
	 * <p>
	 * �w�肳�ꂽ�͈͂̃g�[�N�����폜����B
	 * </p>
	 *
	 * @param start
	 *            �؂�o���J�n�g�[�N���inull�̏ꍇ�͐擪�j
	 * @param end
	 *            �؂�o���I���g�[�N���inull�̏ꍇ�͖����j
	 * @return �w�肳�ꂽ�͈͂̃g�[�N���B<br>
	 *         start����end��������Ȃ������ꍇ��t�]���Ă����ꍇ��null
	 */
	public ListToken cut(Token start, Token end) {
		int s = -1, e = -1;
		if (start == null) {
			s = 0;
		}
		if (end == null) {
			e = list.size() - 1;
			if (e < 0)
				return null;
		}

		if (s < 0 || e < 0) {
			for (int i = 0; i < list.size(); i++) {
				Token t = list.get(i);
				if (t == null) {
					continue;
				}
				if (t == start) {
					s = i;
					if (e >= 0)
						break;
				}
				if (t == end) {
					e = i;
					if (s >= 0)
						break;
				}
			}
		}
		return cut(s, e);
	}

	/**
	 * �g�[�N���؂�o��.
	 * <p>
	 * �w�肳�ꂽ�͈͂̃g�[�N�����폜����B
	 * </p>
	 *
	 * @param s
	 *            �؂�o���J�n�ʒu
	 * @param e
	 *            �؂�o���I���ʒu
	 * @return �w�肳�ꂽ�͈͂̃g�[�N���B<br>
	 *         s����e���͈͊O�̏ꍇ��t�]���Ă����ꍇ��null
	 */
	public ListToken cut(int s, int e) {
		if (s < 0 || e < 0 || e >= list.size() || s > e) {
			return null;
		}
		return cut0(s, e);
	}

	protected ListToken cut0(int s, int e) {
		ListToken n = new ListToken(e - s);
		for (int i = s; i <= e; i++) {
			Token t = list.remove(s);
			if (t != null) {
				n.add(t);
			}
		}
		return n;
	}

	/**
	 * �g�[�N���؂�o��.
	 * <p>
	 * �w�肳�ꂽ�g�[�N�����폜����B<br>
	 * ���̍ہA���̃g�[�N�����O�ɃX�y�[�X�E���s���������炻����܂߂�B
	 * </p>
	 *
	 * @param token
	 *            �؂�o���g�[�N��
	 * @return �؂�o�����g�[�N���B<br>
	 *         ������Ȃ������ꍇ��null
	 */
	public ListToken cutWithPreSkip(Token token) {
		int s = -1;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == token) {
				s = i;
				break;
			}
		}
		if (s < 0) {
			return null;
		}
		int e = s;

		while (s > 0) {
			Token t = list.get(s - 1);
			if (t instanceof SkipToken || t instanceof SpaceAtom
					|| t instanceof CrLfAtom) {
				s--;
				continue;
			}
			break;
		}

		return cut0(s, e);
	}
}
