package jp.hishidama.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.AbstractSequentialList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * ��Q�ƃ��X�g.
 * <p>
 * �I�u�W�F�N�g��<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/tech/java/weak.html">��Q��</a>��ێ����A���̃I�u�W�F�N�g��GC�ɂ��j�����ꂽ�ꍇ�Ƀ��X�g������폜����B
 * </p>
 * <p>
 * ��{�I��{@link java.util.List �ʏ�̃��X�g}�Ɠ��l�̎g�������o����B�������Anull�����鎖�͏o���Ȃ��B<br>
 * �l�擾�n�̃��\�b�h�ł́A�^�C�~���O�ɂ���Ă�null���Ԃ邱�Ƃ�����iGC�ɂ��j���ς݂��Ӗ�����j�B
 * </p>
 * <p>
 * ���N���X�̊�{�\���̓����N���X�g�ł���A�C���f�b�N�X���g���đ��삷�郁�\�b�h�͊�{�I�Ɏ������Ă��Ȃ��B<br>
 * ���N���X��MT�Z�[�t�ł͂Ȃ��B
 * </p>
 * <p>��<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/util/weak.html">�g�p��</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/util/weak.html">�Ђ�����</a>
 * 
 * @param <E>
 *            ���̃��X�g���ێ�����l�̌^
 * @see java.util.WeakHashMap
 * @see java.util.LinkedList
 * @since 2008.07.14
 */
public class WeakList<E> extends AbstractSequentialList<E> implements Queue<E> {

	/** �Q�ƃL���[ */
	private ReferenceQueue<E> queue = new ReferenceQueue<E>();

	/** Entry�̃��X�g�̐擪�Ɩ�����\���I�u�W�F�N�g */
	private Entry root = new Entry();

	/** ���X�g���ɕێ����Ă���� */
	private int size = 0;

	/** ���X�g���ɕێ�����v�f�i����΃��b�p�[�j */
	private class Entry extends WeakReference<E> {

		private Entry prev, next;

		private Entry() {
			super(null, null);
			this.prev = this;
			this.next = this;
		}

		private Entry(E referent, Entry next, Entry prev) {
			super(referent, queue);
			prev.next = this;
			this.prev = prev;
			this.next = next;
			next.prev = this;
		}

		private void remove() {
			prev.next = this.next;
			next.prev = this.prev;

			this.prev = null;
			this.next = null;
		}
	}

	private Entry addBefore(E e, Entry entry) {
		Entry newEntry = new Entry(e, entry, entry.prev);
		size++;
		modCount++;
		return newEntry;
	}

	private E remove(Entry e) {
		if (e == root) {
			throw new NoSuchElementException();
		}

		E result = e.get();
		e.remove();
		size--;
		modCount++;
		return result;
	}

	private void expungeStaleEntries() {
		if (size == 0) {
			return;
		}
		for (;;) {
			Entry e = (Entry) queue.poll();
			if (e == null) {
				break;
			}
			remove(e);
		}
	}

	private Entry find(E element) {
		for (Entry e = root.next; e != root; e = e.next) {
			if (element.equals(e.get()))
				return e;
		}
		return null;
	}

	/* private */Entry find(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: "
					+ size);
		}
		Entry e = root;
		if (index < size / 2) {
			for (int i = 0; i <= index; i++)
				e = e.next;
		} else {
			for (int i = size; i > index; i--)
				e = e.prev;
		}
		return e;
	}

	//
	//
	//

	/**
	 * ���X�g�̐擪�ɁA�w�肳�ꂽ�v�f��}�����܂��B
	 * 
	 * @param e
	 *            �ǉ�����v�f
	 * @throws IllegalArgumentException
	 *             ������null�̂Ƃ�
	 */
	public void addFirst(E e) {
		if (e == null) {
			throw new IllegalArgumentException();
		}
		expungeStaleEntries();
		addBefore(e, root.next);
	}

	/**
	 * ���X�g�̍Ō�ɁA�w�肳�ꂽ�v�f��ǉ����܂��B
	 * 
	 * @param e
	 *            �ǉ�����v�f
	 * @throws IllegalArgumentException
	 *             ������null�̂Ƃ�
	 */
	public void addLast(E e) {
		if (e == null) {
			throw new IllegalArgumentException();
		}
		expungeStaleEntries();
		addBefore(e, root);
	}

	/**
	 * ���X�g���̍ŏ��̗v�f��Ԃ��܂��B
	 * 
	 * @return ���X�g���̍ŏ��̗v�f�iGC�Ƃ̃^�C�~���O�ɂ���Ă�null�̏ꍇ������j
	 * @throws NoSuchElementException
	 *             ���X�g����̏ꍇ
	 */
	public E getFirst() {
		expungeStaleEntries();
		if (size == 0) {
			throw new NoSuchElementException();
		}
		return root.next.get();
	}

	/**
	 * ���X�g���̍Ō�̗v�f��Ԃ��܂��B
	 * 
	 * @return ���X�g���̍Ō�̗v�f�iGC�Ƃ̃^�C�~���O�ɂ���Ă�null�̏ꍇ������j
	 * @throws NoSuchElementException
	 *             ���X�g����̏ꍇ
	 */
	public E getLast() {
		expungeStaleEntries();
		if (size == 0) {
			throw new NoSuchElementException();
		}
		return root.prev.get();
	}

	/**
	 * ���X�g����ŏ��̗v�f���폜���ĕԂ��܂��B
	 * 
	 * @return ���X�g����̍ŏ��̗v�f�iGC�Ƃ̃^�C�~���O�ɂ���Ă�null�̏ꍇ������j
	 * @throws NoSuchElementException
	 *             ���X�g����̏ꍇ
	 */
	public E removeFirst() {
		expungeStaleEntries();
		return remove(root.next);
	}

	/**
	 * ���X�g����Ō�̗v�f���폜���ĕԂ��܂��B
	 * 
	 * @return ���X�g����̍Ō�̗v�f�iGC�Ƃ̃^�C�~���O�ɂ���Ă�null�̏ꍇ������j
	 * @throws NoSuchElementException
	 *             ���X�g����̏ꍇ
	 */
	public E removeLast() {
		expungeStaleEntries();
		return remove(root.prev);
	}

	/**
	 * ���X�g�Ɏw�肳�ꂽ�v�f������ꍇ�� true ��Ԃ��܂��B
	 * 
	 * @param o
	 *            ���X�g�ɂ��邩�ǂ����𒲂ׂ�v�f
	 */
	@Override
	public boolean contains(Object o) {
		if (o == null) {
			return false;
		}
		expungeStaleEntries();
		@SuppressWarnings("unchecked")
		E e = (E) o;
		return find(e) != null;
	}

	/**
	 * ���X�g���ɂ���v�f�̐���Ԃ��܂��B
	 * 
	 * @return ���X�g���̗v�f��
	 */
	public int size() {
		expungeStaleEntries();
		return size;
	}

	/**
	 * ���X�g�̍Ō�ɁA�w�肳�ꂽ�v�f��ǉ����܂��B
	 * 
	 * @param e
	 *            ���X�g�ɒǉ������v�f
	 * @return ���true
	 * @throws IllegalArgumentException
	 *             ������null�̏ꍇ
	 */
	@Override
	public boolean add(E e) {
		if (e == null) {
			throw new IllegalArgumentException();
		}
		expungeStaleEntries();
		addBefore(e, root);
		return true;
	}

	/**
	 * �w�肳�ꂽ�v�f�����̃��X�g�ɂ���΁A���̍ŏ��̂��̂����X�g����폜���܂��B
	 * 
	 * @param o
	 *            ���X�g����폜�����v�f
	 * @return �폜���ꂽ�ꍇ��true
	 */
	@Override
	public boolean remove(Object o) {
		if (o != null) {
			expungeStaleEntries();

			@SuppressWarnings("unchecked")
			Entry e = find((E) o);
			if (e != null) {
				remove(e);
				return true;
			}
		}
		return false;
	}

	/**
	 * ���X�g���炷�ׂĂ̗v�f���폜���܂��B
	 */
	@Override
	public void clear() {
		for (Entry e = root.next; e != root;) {
			Entry next = e.next;
			remove(e);
			e = next;
		}
		root.next = root.prev = root;
		size = 0;
		modCount++;
	}

	//
	//
	//

	/**
	 * ���̃��X�g���̗v�f��K�؂ȏ����ŌJ��Ԃ��������锽���q��Ԃ��܂��B
	 * 
	 * @return �����q
	 */
	@Override
	public Iterator<E> iterator() {
		expungeStaleEntries();
		return new Itr();
	}

	private class Itr implements Iterator<E> {

		private Entry current = root.next, lastRet = null;

		private int expectedModCount = modCount;

		public boolean hasNext() {
			return current != root;
		}

		public E next() {
			checkForComodification();
			lastRet = current;
			current = current.next;
			return lastRet.get();
		}

		public void remove() {
			if (lastRet == null) {
				throw new IllegalStateException();
			}
			checkForComodification();
			WeakList.this.remove(lastRet);
			lastRet = null;
			expectedModCount = modCount;
		}

		final void checkForComodification() {
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
		}
	}

	/**
	 * ���̃��X�g���̗v�f��K�؂ȏ����ŌJ��Ԃ��������郊�X�g�����q��Ԃ��܂��B
	 * 
	 * @return ���X�g�����q
	 * @throws IndexOutOfBoundsException
	 *             �C���f�b�N�X���͈͊O�̏ꍇ
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		// expungeStaleEntries();
		return new ListItr(index);
	}

	private class ListItr implements ListIterator<E> {
		private Entry lastReturned = root;

		private Entry next;

		private int nextIndex;

		private int expectedModCount = modCount;

		ListItr(int index) {
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException("Index: " + index
						+ ", Size: " + size);
			}
			if (index < size / 2) {
				next = root.next;
				for (nextIndex = 0; nextIndex < index; nextIndex++)
					next = next.next;
			} else {
				next = root;
				for (nextIndex = size; nextIndex > index; nextIndex--)
					next = next.prev;
			}
		}

		public boolean hasNext() {
			return nextIndex != size;
		}

		public E next() {
			checkForComodification();
			if (nextIndex == size)
				throw new NoSuchElementException();

			lastReturned = next;
			next = next.next;
			nextIndex++;
			return lastReturned.get();
		}

		public boolean hasPrevious() {
			return nextIndex != 0;
		}

		public E previous() {
			if (nextIndex == 0)
				throw new NoSuchElementException();

			lastReturned = next = next.prev;
			nextIndex--;
			checkForComodification();
			return lastReturned.get();
		}

		public int nextIndex() {
			return nextIndex;
		}

		public int previousIndex() {
			return nextIndex - 1;
		}

		public void remove() {
			checkForComodification();
			Entry lastNext = lastReturned.next;
			try {
				WeakList.this.remove(lastReturned);
			} catch (NoSuchElementException e) {
				throw new IllegalStateException();
			}
			if (next == lastReturned)
				next = lastNext;
			else
				nextIndex--;
			lastReturned = root;
			expectedModCount++;
		}

		public void set(E e) {
			throw new UnsupportedOperationException();
		}

		public void add(E e) {
			checkForComodification();
			lastReturned = root;
			addBefore(e, next);
			nextIndex++;
			expectedModCount++;
		}

		final void checkForComodification() {
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
		}
	}

	//
	// Queue
	//

	/**
	 * ���̃��X�g�̐擪 (�ŏ��̗v�f) ���擾���܂��B
	 * 
	 * @return ���X�g���̍ŏ��̗v�f�iGC�Ƃ̃^�C�~���O�ɂ���Ă�null�̏ꍇ������j<br>
	 *         ���X�g����̏ꍇ��null
	 */
	public E peek() {
		expungeStaleEntries();
		if (size == 0) {
			return null;
		}
		return root.next.get();
	}

	/**
	 * ���̃��X�g�̐擪 (�ŏ��̗v�f) ���擾���܂��B
	 * 
	 * @return ���X�g���̍ŏ��̗v�f�iGC�Ƃ̃^�C�~���O�ɂ���Ă�null�̏ꍇ������j
	 * @throws NoSuchElementException
	 *             ���X�g����̏ꍇ
	 * @see #getFirst()
	 */
	public E element() {
		return getFirst();
	}

	/**
	 * ���̃��X�g�̐擪 (�ŏ��̗v�f) �擾���A�폜���܂��B
	 * 
	 * @return ���X�g���̍ŏ��̗v�f�iGC�Ƃ̃^�C�~���O�ɂ���Ă�null�̏ꍇ������j<br>
	 *         ���X�g����̏ꍇ��null
	 */
	public E poll() {
		expungeStaleEntries();
		if (size == 0)
			return null;
		return remove(root.next);
	}

	/**
	 * ���̃��X�g�̐擪 (�ŏ��̗v�f) �擾���A�폜���܂��B
	 * 
	 * @return ���X�g���̍ŏ��̗v�f�iGC�Ƃ̃^�C�~���O�ɂ���Ă�null�̏ꍇ������j
	 * @throws NoSuchElementException
	 *             ���X�g����̏ꍇ
	 * @see #removeFirst()
	 */
	public E remove() {
		return removeFirst();
	}

	/**
	 * �w�肳�ꂽ�v�f�����̃��X�g�̖��� (�Ō�̗v�f) �ɒǉ����܂��B
	 * 
	 * @param e
	 *            �ǉ�����v�f
	 * @throws IllegalArgumentException
	 *             ������null�̏ꍇ
	 * @see #add(Object)
	 */
	public boolean offer(E e) {
		return add(e);
	}

}
