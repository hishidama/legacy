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
 * 弱参照リスト.
 * <p>
 * オブジェクトの<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/tech/java/weak.html">弱参照</a>を保持し、そのオブジェクトがGCにより破棄された場合にリスト内から削除する。
 * </p>
 * <p>
 * 基本的に{@link java.util.List 通常のリスト}と同様の使い方が出来る。ただし、nullを入れる事は出来ない。<br>
 * 値取得系のメソッドでは、タイミングによってはnullが返ることがある（GCによる破棄済みを意味する）。
 * </p>
 * <p>
 * 当クラスの基本構造はリンクリストであり、インデックスを使って操作するメソッドは基本的に実装していない。<br>
 * 当クラスはMTセーフではない。
 * </p>
 * <p>→<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/util/weak.html">使用例</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/util/weak.html">ひしだま</a>
 * 
 * @param <E>
 *            このリストが保持する値の型
 * @see java.util.WeakHashMap
 * @see java.util.LinkedList
 * @since 2008.07.14
 */
public class WeakList<E> extends AbstractSequentialList<E> implements Queue<E> {

	/** 参照キュー */
	private ReferenceQueue<E> queue = new ReferenceQueue<E>();

	/** Entryのリストの先頭と末尾を表すオブジェクト */
	private Entry root = new Entry();

	/** リスト内に保持している個数 */
	private int size = 0;

	/** リスト内に保持する要素（いわばラッパー） */
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
	 * リストの先頭に、指定された要素を挿入します。
	 * 
	 * @param e
	 *            追加する要素
	 * @throws IllegalArgumentException
	 *             引数がnullのとき
	 */
	public void addFirst(E e) {
		if (e == null) {
			throw new IllegalArgumentException();
		}
		expungeStaleEntries();
		addBefore(e, root.next);
	}

	/**
	 * リストの最後に、指定された要素を追加します。
	 * 
	 * @param e
	 *            追加する要素
	 * @throws IllegalArgumentException
	 *             引数がnullのとき
	 */
	public void addLast(E e) {
		if (e == null) {
			throw new IllegalArgumentException();
		}
		expungeStaleEntries();
		addBefore(e, root);
	}

	/**
	 * リスト内の最初の要素を返します。
	 * 
	 * @return リスト内の最初の要素（GCとのタイミングによってはnullの場合もある）
	 * @throws NoSuchElementException
	 *             リストが空の場合
	 */
	public E getFirst() {
		expungeStaleEntries();
		if (size == 0) {
			throw new NoSuchElementException();
		}
		return root.next.get();
	}

	/**
	 * リスト内の最後の要素を返します。
	 * 
	 * @return リスト内の最後の要素（GCとのタイミングによってはnullの場合もある）
	 * @throws NoSuchElementException
	 *             リストが空の場合
	 */
	public E getLast() {
		expungeStaleEntries();
		if (size == 0) {
			throw new NoSuchElementException();
		}
		return root.prev.get();
	}

	/**
	 * リストから最初の要素を削除して返します。
	 * 
	 * @return リストからの最初の要素（GCとのタイミングによってはnullの場合もある）
	 * @throws NoSuchElementException
	 *             リストが空の場合
	 */
	public E removeFirst() {
		expungeStaleEntries();
		return remove(root.next);
	}

	/**
	 * リストから最後の要素を削除して返します。
	 * 
	 * @return リストからの最後の要素（GCとのタイミングによってはnullの場合もある）
	 * @throws NoSuchElementException
	 *             リストが空の場合
	 */
	public E removeLast() {
		expungeStaleEntries();
		return remove(root.prev);
	}

	/**
	 * リストに指定された要素がある場合に true を返します。
	 * 
	 * @param o
	 *            リストにあるかどうかを調べる要素
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
	 * リスト内にある要素の数を返します。
	 * 
	 * @return リスト内の要素数
	 */
	public int size() {
		expungeStaleEntries();
		return size;
	}

	/**
	 * リストの最後に、指定された要素を追加します。
	 * 
	 * @param e
	 *            リストに追加される要素
	 * @return 常にtrue
	 * @throws IllegalArgumentException
	 *             引数がnullの場合
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
	 * 指定された要素がこのリストにあれば、その最初のものをリストから削除します。
	 * 
	 * @param o
	 *            リストから削除される要素
	 * @return 削除された場合はtrue
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
	 * リストからすべての要素を削除します。
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
	 * このリスト内の要素を適切な順序で繰り返し処理する反復子を返します。
	 * 
	 * @return 反復子
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
	 * このリスト内の要素を適切な順序で繰り返し処理するリスト反復子を返します。
	 * 
	 * @return リスト反復子
	 * @throws IndexOutOfBoundsException
	 *             インデックスが範囲外の場合
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
	 * このリストの先頭 (最初の要素) を取得します。
	 * 
	 * @return リスト内の最初の要素（GCとのタイミングによってはnullの場合もある）<br>
	 *         リストが空の場合はnull
	 */
	public E peek() {
		expungeStaleEntries();
		if (size == 0) {
			return null;
		}
		return root.next.get();
	}

	/**
	 * このリストの先頭 (最初の要素) を取得します。
	 * 
	 * @return リスト内の最初の要素（GCとのタイミングによってはnullの場合もある）
	 * @throws NoSuchElementException
	 *             リストが空の場合
	 * @see #getFirst()
	 */
	public E element() {
		return getFirst();
	}

	/**
	 * このリストの先頭 (最初の要素) 取得し、削除します。
	 * 
	 * @return リスト内の最初の要素（GCとのタイミングによってはnullの場合もある）<br>
	 *         リストが空の場合はnull
	 */
	public E poll() {
		expungeStaleEntries();
		if (size == 0)
			return null;
		return remove(root.next);
	}

	/**
	 * このリストの先頭 (最初の要素) 取得し、削除します。
	 * 
	 * @return リスト内の最初の要素（GCとのタイミングによってはnullの場合もある）
	 * @throws NoSuchElementException
	 *             リストが空の場合
	 * @see #removeFirst()
	 */
	public E remove() {
		return removeFirst();
	}

	/**
	 * 指定された要素をこのリストの末尾 (最後の要素) に追加します。
	 * 
	 * @param e
	 *            追加する要素
	 * @throws IllegalArgumentException
	 *             引数がnullの場合
	 * @see #add(Object)
	 */
	public boolean offer(E e) {
		return add(e);
	}

}
