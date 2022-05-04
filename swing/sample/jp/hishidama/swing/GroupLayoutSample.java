package jp.hishidama.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import jp.hishidama.swing.layout.GroupLayoutUtil;
import static jp.hishidama.swing.layout.GroupLayoutUtil.*;

public class GroupLayoutSample extends JFrame {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		GroupLayoutSample me = new GroupLayoutSample();
		me.init();
		me.setVisible(true);
	}

	private void init() {
		setTitle("GroupLayoutUtilのサンプル");
		initBounds();
		initCloseOperation();

		Container c = getContentPane();
		switch (1) {
		default:
			initPane(c);
			break;
		case 1:
			initPaneGap(c);
			break;
		}
	}

	private void initBounds() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int nx = d.width / 2;
		int ny = d.height / 2;
		int x = (d.width - nx) / 2;
		int y = (d.height - ny) / 2;

		setBounds(x, y, nx, ny);
	}

	private void initCloseOperation() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@SuppressWarnings("unused")
	private void initPane(Container container) {

		Component[][] compos0 = {
				{ new JLabel("テキスト1"), new JTextField("text1"),
						new JButton("ボタンA") },
				{ new JLabel("テキスト2"), new JTextField("text2"),
						new JButton("ボタンB") },

		};
		Component[][] compos1 = {
				{ new JLabel("長い見出しを指定") },
				{ new JLabel("テキスト1"), new JTextField("text1"),
						new JButton("ボタンA") },
				{ new JLabel("テキスト2"), null, new JButton("ボタンB") }, };

		Component[][] compos2 = {
				{ new JLabel("長い見出しを指定"), SAME_L },
				{ new JLabel("テキスト1"), new JTextField("text1"),
						new JButton("ボタンA") },
				{ new JLabel("テキスト2"), null, new JButton("ボタンB") }, };

		Component[][] compos3 = {
				{
						new JLabel("リスト1"),
						new JScrollPane(new JList(new Object[] { "AA", "BB" })),
						new JButton("ボタン1") },
				{ null, SAME_U, new JButton("ボタン2") },
				{ null, SAME_U, new JButton("ボタン3") },
				{ new JLabel("テキスト"), new JTextField("text"),
						new JButton("ボタン4") },

		};

		Component[][] compos4 = {
				{
						new JLabel("リスト1"),
						new JScrollPane(new JList(new Object[] { "AA", "BB" })),
						SAME_L, new JButton("ボタン1") },
				{ null, SAME_U, SAME_L, new JButton("ボタン2") },
				{ null, SAME_U, SAME_L, new JButton("ボタン3") },
				{ new JLabel("テキスト"), new JTextField("text1"),
						new JTextField("text2"), new JButton("ボタン4") }, };

		GroupLayoutUtil groupLayoutUtil = new GroupLayoutUtil();
		groupLayoutUtil.setComponents(compos2);
		groupLayoutUtil.setGroupLayoutTo(container);
		GroupLayout layout = groupLayoutUtil.getGroupLayout();

		// コンポーネント同士の間隔を空ける設定
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
	}

	private JComponent line1Label = new JLabel("1行目ラベル");
	private JComponent line1Text = new JTextField("テキストボックス");
	private JComponent line1Button11 = new JButton("ボタン1-1");
	private JComponent line1Button12 = new JButton("ボタン1-2");

	private JComponent line2Label = new JLabel("2行目ラベル");
	private JComponent line2List = new JScrollPane(new JList(new String[] {
			"選択肢1", "選択肢2" }));
	private JComponent line2Button2 = new JButton("ボタン2");

	private void initPaneGap(Container container) {
		// 作りたい構造
		Component[][] compos = {
				{ line1Label, line1Text, new Gap(line1Button11, 0, -1), line1Button12 },
				{ line2Label, line2List, SAME_L, new Gap(line2Button2, -1, 0) }, };

		GroupLayoutUtil groupLayoutUtil = new GroupLayoutUtil() {
			@Override
			protected void initGroupLayout(GroupLayout layout) {
				// コンポーネント同士の間隔を空ける設定
				layout.setAutoCreateGaps(true);
				layout.setAutoCreateContainerGaps(true);
			}

		};
		groupLayoutUtil.setComponents(compos);
		groupLayoutUtil.setGroupLayoutTo(container);
	}
}
