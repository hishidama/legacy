package jp.hishidama.html.parser.elem;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import jp.hishidama.html.lexer.token.ListToken;
import jp.hishidama.html.lexer.token.Tag;
import jp.hishidama.html.lexer.token.Token;

/**
 * �v�f���X�g.
 *<p>
 * HtHtmlParser�ɂ�����A�v�f�̃��X�g�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.02.09
 * @version 2009.02.21
 */
public class HtListElement extends HtElement implements Iterable<HtElement> {

	@Override
	public Tag getStartTag() {
		return null;
	}

	@Override
	public Tag getEndTag() {
		return null;
	}

	protected List<HtElement> list;

	protected class ElemList extends ArrayList<HtElement> {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean add(HtElement element) {
			element.setParent(HtListElement.this);
			return super.add(element);
		}

		@Override
		public void add(int index, HtElement element) {
			element.setParent(HtListElement.this);
			super.add(index, element);
		}

		@Override
		public HtElement set(int index, HtElement element) {
			element.setParent(HtListElement.this);
			return super.set(index, element);
		}
	}

	/**
	 * �v�f�ǉ�.
	 *
	 * @param he
	 *            �v�f
	 */
	public void add(HtElement he) {
		if (list == null) {
			list = new ElemList();
		}
		list.add(he);
	}

	/**
	 * �v�f�ǉ�.
	 *
	 * @param n
	 *            �C���f�b�N�X
	 * @param he
	 *            �v�f
	 * @since 2009.02.21
	 */
	public void add(int n, HtElement he) {
		if (list == null) {
			list = new ElemList();
		}
		list.add(n, he);
	}

	/**
	 * �v�f���X�g�ݒ�.
	 *
	 * @param elist
	 *            �v�f�̃��X�g
	 */
	public void setList(List<HtElement> elist) {
		this.list = elist;
		for (HtElement e : elist) {
			e.setParent(this);
		}
	}

	/**
	 * �v�f���X�g�擾.
	 *
	 * @return �v�f�̃��X�g
	 */
	public List<HtElement> getList() {
		return list;
	}

	/**
	 * �v�f���X�g�̃C�e���[�^�[�擾.
	 */
	@Override
	public Iterator<HtElement> iterator() {
		if (list == null) {
			return Collections.<HtElement> emptyList().iterator();
		}
		return list.iterator();
	}

	/**
	 * �v�f���擾.
	 *
	 * @return �ێ����Ă���v�f�̌�
	 */
	public int size() {
		if (list == null) {
			return 0;
		}
		return list.size();
	}

	/**
	 * �v�f�擾.
	 *
	 * @param i
	 *            �C���f�b�N�X
	 * @return �v�f�i�����ꍇ��null�j
	 */
	public HtElement get(int i) {
		if (list == null) {
			return null;
		}
		if (i < 0 || i >= list.size()) {
			return null;
		}
		return list.get(i);
	}

	/**
	 * �v�f�ʒu�擾.
	 *
	 * @param he
	 *            �v�f
	 * @return �v�f�̈ʒu�i�����ꍇ��-1�j
	 * @since 2009.02.21
	 */
	public int indexOf(HtElement he) {
		if (list == null) {
			return -1;
		}
		return list.indexOf(he);
	}

	/**
	 * �v�f�폜.
	 *
	 * @param i
	 *            �C���f�b�N�X
	 * @return �폜���ꂽ�v�f�i�폜����Ȃ������ꍇ��null�j
	 */
	public HtElement remove(int i) {
		if (list == null) {
			return null;
		}
		if (i < 0 || i >= list.size()) {
			return null;
		}
		return list.remove(i);
	}

	@Override
	public boolean isEmpty() {
		return (list == null) || list.isEmpty();
	}

	@Override
	public int getTextLength() {
		int len = 0;
		if (list != null) {
			for (HtElement e : list) {
				if (e != null) {
					len += e.getTextLength();
				}
			}
		}
		return len;
	}

	@Override
	public void writeTo(StringBuilder sb) {
		if (list != null) {
			for (HtElement e : list) {
				if (e != null) {
					e.writeTo(sb);
				}
			}
		}
	}

	@Override
	public void writeTo(Writer w) throws IOException {
		if (list != null) {
			for (HtElement e : list) {
				if (e != null) {
					e.writeTo(w);
				}
			}
		}
	}

	@Override
	public HtElement searchToken(Token t) {
		if (list != null) {
			for (HtElement e : list) {
				if (e != null) {
					HtElement r = e.searchToken(t);
					if (r != null) {
						return r;
					}
				}
			}
		}
		return null;
	}

	@Override
	public void toToken(ListToken tlist) {
		if (list != null) {
			for (HtElement e : list) {
				if (e != null) {
					e.toToken(tlist);
				}
			}
		}
	}
}
