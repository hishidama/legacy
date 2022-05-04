package jp.hishidama.swing.layout;

import java.awt.Component;
import java.awt.Container;
import java.util.Iterator;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.GroupLayout.Group;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

/**
 * GroupLayoutユーティリティー.
 *
 * <p>
 * コンポーネントの二次元配列を{@link javax.swing.GroupLayout GroupLayout}で配置する。<br>
 * →<a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing/GroupLayoutUtil.html"
 * >使用例</a>
 * </p>
 * <p>
 * ※当クラスはインスタンスフィールドに個別の値を保持するので、MTセーフではない。
 * </p>
 *
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing/GroupLayoutUtil.html"
 *         >ひしだま</a>
 * @since 2009.11.06
 * @version 2009.11.07
 */
public class GroupLayoutUtil {

	/** 配列の左側と同じコンポーネントを配置する */
	public static final Component SAME_L = new JLabel("左と同じ");
	/** 配列の上側と同じコンポーネントを配置する */
	public static final Component SAME_U = new JLabel("上と同じ");

	/**
	 * ギャップ指定コンポーネント.
	 * <p>
	 * コンポーネントの左および上にギャップを追加する為のコンポーネント。<br>
	 * →<a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing/GroupLayoutUtil.html#h_sample_gap"
	 * >使用例</a>
	 * </p>
	 *
	 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing/GroupLayoutUtil.html"
	 *         >ひしだま</a>
	 * @since 2009.11.07
	 */
	public static class Gap extends Component {
		private static final long serialVersionUID = -3143356632258067324L;
		protected Component comp;
		protected int colMin, colPref, colMax;
		protected int rowMin, rowPref, rowMax;

		/**
		 * コンストラクター.
		 *
		 * @param comp
		 *            コンポーネント
		 * @param colSize
		 *            列ギャップサイズ（負のときはギャップを追加しない）
		 * @param rowSize
		 *            行ギャップサイズ（負のときはギャップを追加しない）
		 * @see SequentialGroup#addGap(int)
		 */
		public Gap(Component comp, int colSize, int rowSize) {
			this(comp, colSize, colSize, colSize, rowSize, rowSize, rowSize);
		}

		/**
		 * コンストラクター.
		 *
		 * @param comp
		 *            コンポーネント
		 * @param colMin
		 *            列ギャップ最小サイズ
		 * @param colPref
		 *            列ギャップ推奨サイズ（負のときはギャップを追加しない）
		 * @param colMax
		 *            列ギャップ最大サイズ
		 * @param rowMin
		 *            行ギャップ最小サイズ
		 * @param rowPref
		 *            行ギャップ推奨サイズ（負のときはギャップを追加しない）
		 * @param rowMax
		 *            行ギャップ最大サイズ
		 * @see SequentialGroup#addGap(int, int, int)
		 */
		public Gap(Component comp, int colMin, int colPref, int colMax,
				int rowMin, int rowPref, int rowMax) {
			this.comp = comp;
			this.colMin = colMin;
			this.colPref = colPref;
			this.colMax = colMax;
			this.rowMin = rowMin;
			this.rowPref = rowPref;
			this.rowMax = rowMax;
		}

		/**
		 * コンポーネント取得.
		 *
		 * @return コンポーネント
		 */
		public Component getComponent() {
			return comp;
		}
	}

	protected Component[][] components;
	protected int xsize, ysize;

	protected GroupLayout groupLayout;

	protected CreateColGroup colCreator = new CreateColGroup();
	protected CreateRowGroup rowCreator = new CreateRowGroup();

	/**
	 * コンストラクター.
	 */
	public GroupLayoutUtil() {
	}

	/**
	 * コンポーネント配列設定.
	 *
	 * @param components
	 *            コンポーネントの二次元配列
	 */
	public void setComponents(Component[][] components) {
		this.components = components;
		this.ysize = components.length;
		this.xsize = 0;
		for (int i = 0; i < ysize; i++) {
			Component[] line = components[i];
			xsize = Math.max(xsize, line.length);
		}
	}

	/**
	 * コンポーネント配列取得.
	 *
	 * @return コンポーネントの二次元配列
	 */
	public Component[][] getComponents() {
		return components;
	}

	/**
	 * コンポーネント配列の横方向の個数を取得.
	 *
	 * @return 配列数
	 */
	public int getXSize() {
		return xsize;
	}

	/**
	 * コンポーネント配列の縦方向の個数を取得.
	 *
	 * @return 配列数
	 */
	public int getYSize() {
		return ysize;
	}

	/**
	 * コンポーネント取得.
	 *
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @return コンポーネント（配列の範囲外の場合はnull）
	 */
	public Component getComponent(int x, int y) {
		if (y < 0 || y >= ysize || x < 0) {
			return null;
		}
		Component[] line = components[y];
		if (x >= line.length) {
			return null;
		}
		return line[x];
	}

	/**
	 * GroupLayout設定.
	 *<p>
	 * コンポーネントを配置したGroupLayoutを生成し、指定されたコンテナに登録する。
	 * </p>
	 *
	 * @param container
	 *            コンテナ
	 */
	public void setGroupLayoutTo(Container container) {
		groupLayout = createGroupLayout(container);
		initGroupLayout(groupLayout);
		{
			Group cols = colCreator.createGroup(0, xsize);
			groupLayout.setHorizontalGroup(cols);
		}
		{
			Group rows = rowCreator.createGroup(0, ysize);
			groupLayout.setVerticalGroup(rows);
		}
		container.setLayout(groupLayout);
	}

	/**
	 * GroupLayoutインスタンス生成.
	 *
	 * @param container
	 *            コンテナ
	 * @return GroupLayout
	 * @see #setGroupLayoutTo(Container)
	 */
	protected GroupLayout createGroupLayout(Container container) {
		return new GroupLayout(container);
	}

	/**
	 * GroupLayout初期化.
	 *
	 * @param layout
	 *            GroupLayout
	 */
	protected void initGroupLayout(GroupLayout layout) {
		// 例
		// コンポーネント同士の間隔を空ける設定
		// layout.setAutoCreateGaps(true);
		// layout.setAutoCreateContainerGaps(true);
	}

	/**
	 * GroupLayout取得.
	 *<p>
	 * {@link #setGroupLayoutTo(Container)}を呼んでからでないと、nullが返る。
	 * </p>
	 *
	 * @return GroupLayout
	 * @see #setGroupLayoutTo(Container)
	 */
	public GroupLayout getGroupLayout() {
		return groupLayout;
	}

	/**
	 * レイアウト配置本体.
	 */
	protected abstract class CreateGroup {

		/**
		 * レイアウト実行.
		 *
		 * @param x
		 *            開始位置
		 * @param mx
		 *            終了位置
		 * @return グループ
		 */
		public Group createGroup(int x, int mx) {
			SequentialGroup sg = groupLayout.createSequentialGroup();
			while (x < mx) {
				if (!nextIsSame(x)) {
					// すぐ右にSAMEが1つも無いとき
					ParallelGroup pg = createGroup1(x);
					if (pg != null) {
						addGap(sg, x);
						sg.addGroup(pg);
					}
					x++;
					continue;
				}

				// すぐ右にSAMEが1つでもあるとき
				int sx = lastSame(x + 1, mx);
				if (sx < 0) {
					throw new IllegalStateException();
				}
				Group ng = createGroup(x + 1, sx + 1);

				ParallelGroup pg = createGroup1(x, true); // 右にSAMEあり
				ParallelGroup pg0 = createGroup1(x, false); // 右にSAMEなし
				if (pg0 == null) {
					pg0 = createParallelGroup(); // 空のグループ
				}
				SequentialGroup sg0 = groupLayout.createSequentialGroup();
				sg0.addGroup(pg0);
				addGap(sg0, x + 1);
				sg0.addGroup(ng);
				pg.addGroup(sg0);

				addGap(sg, x);
				sg.addGroup(pg);
				x = sx + 1;
			}
			return sg;
		}

		/**
		 * 一列分のParallelGroupを作成する。
		 *
		 * @param x
		 * @return グループ
		 */
		protected ParallelGroup createGroup1(int x) {
			ParallelGroup pg = null;
			for (int y : getIterable()) {
				Component c = get(x, y);
				if (c == null || c == SAME_U || c == SAME_L) {
					continue;
				}
				if (pg == null) {
					pg = createParallelGroup();
				}
				addComponent(pg, c);
			}
			return pg;
		}

		/**
		 * 一列分のParallelGroupを作成する。
		 *
		 * @param x
		 * @param nextIsSame
		 *            true：右側がSAMEのものだけを選択する。<br>
		 *            false：右側がSAMEでないものだけを選択する。
		 * @return グループ
		 */
		protected ParallelGroup createGroup1(int x, boolean nextIsSame) {
			ParallelGroup pg = null;
			for (int y : getIterable()) {
				Component c = get(x, y);
				if (c == null || c == SAME_U || c == SAME_L) {
					continue;
				}
				Component n = get(x + 1, y);
				if (isSame(n) == nextIsSame) {
					if (pg == null) {
						pg = createParallelGroup();
					}
					addComponent(pg, c);
				}
			}
			return pg;
		}

		/**
		 * 「自分が有効なコンポーネントで、右側がSAMEである」ものが存在するかどうかを判定する。
		 *
		 * @param x
		 * @return true：右側がSAMEであるものが存在する
		 */
		protected boolean nextIsSame(int x) {
			for (int y : getIterable()) {
				Component c = get(x, y);
				if (c == null || c == SAME_U || c == SAME_L) {
					continue;
				}
				Component n = get(x + 1, y);
				if (isSame(n)) {
					return true;
				}
			}
			return false;
		}

		/**
		 * 「SAMEが1つでもある列」が連続して存在する、一番右の列の位置を返す。
		 *
		 * @param x
		 *            開始位置
		 * @param mx
		 *            終了位置
		 * @return 一番右の位置
		 */
		protected int lastSame(int x, int mx) {
			int last = -1;
			for (int i = x; i < mx; i++) {
				if (existSame(i)) {
					last = i;
					continue;
				}
				break;
			}
			return last;
		}

		protected abstract Component get(int x, int y);

		protected abstract boolean existSame(int x);

		protected abstract boolean isSame(Component c);

		protected abstract Iterable<Integer> getIterable();

		protected abstract ParallelGroup createParallelGroup();

		protected void addComponent(Group group, Component c) {
			if (c instanceof Gap) {
				c = ((Gap) c).getComponent();
			}
			group.addComponent(c);
		}

		protected abstract void addGap(SequentialGroup sg, int x);
	}

	/**
	 * 列のレイアウト配置クラス.
	 */
	protected class CreateColGroup extends CreateGroup {

		@Override
		protected Component get(int x, int y) {
			return getComponent(x, y);
		}

		@Override
		protected boolean existSame(int x) {
			for (int y : getIterable()) {
				Component c = getComponent(x, y);
				if (isSame(c)) {
					return true;
				}
			}
			return false;
		}

		@Override
		protected boolean isSame(Component c) {
			return c == SAME_L;
		}

		@Override
		protected Iterable<Integer> getIterable() {
			return new Iterable<Integer>() {
				@Override
				public Iterator<Integer> iterator() {
					return new Iterator<Integer>() {
						int i = 0;

						@Override
						public boolean hasNext() {
							return i < ysize;
						}

						@Override
						public Integer next() {
							return i++;
						}

						@Override
						public void remove() {
							throw new UnsupportedOperationException();
						}
					};
				}
			};
		}

		@Override
		protected ParallelGroup createParallelGroup() {
			return groupLayout.createParallelGroup();
		}

		@Override
		protected void addGap(SequentialGroup sg, int x) {
			final int PREF_NONE = -1;
			int min = Integer.MAX_VALUE, pref = PREF_NONE, max = Integer.MIN_VALUE;
			for (int y : getIterable()) {
				Component c = getComponent(x, y);
				if (c instanceof Gap) {
					Gap g = (Gap) c;
					if (g.colPref < 0) {
						continue;
					}
					if (pref > PREF_NONE && pref != g.colPref) {
						String msg = String.format(
								"colPref不一致(%d,%d) pref=%d/%d", x, y, pref,
								g.colPref);
						throw new IllegalStateException(msg);
					}
					pref = g.colPref;
					min = Math.min(min, g.colMin);
					max = Math.max(max, g.colMax);
				}
			}
			if (pref > PREF_NONE) {
				sg.addGap(min, pref, max);
			}
		}
	}

	/**
	 * 行のレイアウト配置クラス.
	 */
	protected class CreateRowGroup extends CreateGroup {

		@Override
		protected Component get(int y, int x) {
			return getComponent(x, y);
		}

		@Override
		protected boolean existSame(int y) {
			for (int x : getIterable()) {
				Component c = getComponent(x, y);
				if (isSame(c)) {
					return true;
				}
			}
			return false;
		}

		@Override
		protected boolean isSame(Component c) {
			return c == SAME_U;
		}

		@Override
		protected Iterable<Integer> getIterable() {
			return new Iterable<Integer>() {
				@Override
				public Iterator<Integer> iterator() {
					return new Iterator<Integer>() {
						int i = 0;

						@Override
						public boolean hasNext() {
							return i < xsize;
						}

						@Override
						public Integer next() {
							return i++;
						}

						@Override
						public void remove() {
							throw new UnsupportedOperationException();
						}
					};
				}
			};
		}

		@Override
		protected ParallelGroup createParallelGroup() {
			return groupLayout
					.createParallelGroup(GroupLayout.Alignment.BASELINE);
		}

		@Override
		protected void addGap(SequentialGroup sg, int y) {
			final int PREF_NONE = -1;
			int min = Integer.MAX_VALUE, pref = PREF_NONE, max = Integer.MIN_VALUE;
			for (int x : getIterable()) {
				Component c = getComponent(x, y);
				if (c instanceof Gap) {
					Gap g = (Gap) c;
					if (g.rowPref < 0) {
						continue;
					}
					if (pref > PREF_NONE && pref != g.rowPref) {
						String msg = String.format(
								"rowPref不一致(%d,%d) pref=%d/%d", x, y, pref,
								g.rowPref);
						throw new IllegalStateException(msg);
					}
					pref = g.rowPref;
					min = Math.min(min, g.rowMin);
					max = Math.max(max, g.rowMax);
				}
			}
			if (pref > PREF_NONE) {
				sg.addGap(min, pref, max);
			}
		}
	}
}
