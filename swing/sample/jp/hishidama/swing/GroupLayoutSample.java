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
		setTitle("GroupLayoutUtil�̃T���v��");
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
				{ new JLabel("�e�L�X�g1"), new JTextField("text1"),
						new JButton("�{�^��A") },
				{ new JLabel("�e�L�X�g2"), new JTextField("text2"),
						new JButton("�{�^��B") },

		};
		Component[][] compos1 = {
				{ new JLabel("�������o�����w��") },
				{ new JLabel("�e�L�X�g1"), new JTextField("text1"),
						new JButton("�{�^��A") },
				{ new JLabel("�e�L�X�g2"), null, new JButton("�{�^��B") }, };

		Component[][] compos2 = {
				{ new JLabel("�������o�����w��"), SAME_L },
				{ new JLabel("�e�L�X�g1"), new JTextField("text1"),
						new JButton("�{�^��A") },
				{ new JLabel("�e�L�X�g2"), null, new JButton("�{�^��B") }, };

		Component[][] compos3 = {
				{
						new JLabel("���X�g1"),
						new JScrollPane(new JList(new Object[] { "AA", "BB" })),
						new JButton("�{�^��1") },
				{ null, SAME_U, new JButton("�{�^��2") },
				{ null, SAME_U, new JButton("�{�^��3") },
				{ new JLabel("�e�L�X�g"), new JTextField("text"),
						new JButton("�{�^��4") },

		};

		Component[][] compos4 = {
				{
						new JLabel("���X�g1"),
						new JScrollPane(new JList(new Object[] { "AA", "BB" })),
						SAME_L, new JButton("�{�^��1") },
				{ null, SAME_U, SAME_L, new JButton("�{�^��2") },
				{ null, SAME_U, SAME_L, new JButton("�{�^��3") },
				{ new JLabel("�e�L�X�g"), new JTextField("text1"),
						new JTextField("text2"), new JButton("�{�^��4") }, };

		GroupLayoutUtil groupLayoutUtil = new GroupLayoutUtil();
		groupLayoutUtil.setComponents(compos2);
		groupLayoutUtil.setGroupLayoutTo(container);
		GroupLayout layout = groupLayoutUtil.getGroupLayout();

		// �R���|�[�l���g���m�̊Ԋu���󂯂�ݒ�
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
	}

	private JComponent line1Label = new JLabel("1�s�ڃ��x��");
	private JComponent line1Text = new JTextField("�e�L�X�g�{�b�N�X");
	private JComponent line1Button11 = new JButton("�{�^��1-1");
	private JComponent line1Button12 = new JButton("�{�^��1-2");

	private JComponent line2Label = new JLabel("2�s�ڃ��x��");
	private JComponent line2List = new JScrollPane(new JList(new String[] {
			"�I����1", "�I����2" }));
	private JComponent line2Button2 = new JButton("�{�^��2");

	private void initPaneGap(Container container) {
		// ��肽���\��
		Component[][] compos = {
				{ line1Label, line1Text, new Gap(line1Button11, 0, -1), line1Button12 },
				{ line2Label, line2List, SAME_L, new Gap(line2Button2, -1, 0) }, };

		GroupLayoutUtil groupLayoutUtil = new GroupLayoutUtil() {
			@Override
			protected void initGroupLayout(GroupLayout layout) {
				// �R���|�[�l���g���m�̊Ԋu���󂯂�ݒ�
				layout.setAutoCreateGaps(true);
				layout.setAutoCreateContainerGaps(true);
			}

		};
		groupLayoutUtil.setComponents(compos);
		groupLayoutUtil.setGroupLayoutTo(container);
	}
}
