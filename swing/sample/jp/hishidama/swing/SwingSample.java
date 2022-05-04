package jp.hishidama.swing;

import java.awt.*;
import javax.swing.*;

public class SwingSample extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		SwingSample me = new SwingSample();
		me.init();
		me.setVisible(true);
	}

	public SwingSample() {
		super("�Ђ����� Swing���i �T���v��");
	}

	public void init() {

		// �E�B���h�E�̈ʒu�̏�����
		initBounds();

		// �E�B���h�E�̊O�ς̏�����
		initLookFeel();

		// �I�����@�̏�����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ���j���[�o�[�̏�����
		initMenuBar();

		getContentPane().add(new SamplePanel(this, menuBar));
	}

	/**
	 * ��ʂ̒����ɕ\��
	 */
	private void initBounds() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int nx = d.width / 2;
		int ny = d.height / 2;
		int x = (d.width - nx) / 2;
		int y = (d.height - ny) / 2;

		setLocation(x, y);
		setSize(nx, ny);
		// ������setBounds(x, y, nx, ny);
	}

	/**
	 * �O�ς�OS�̂��̂Ɏ�����
	 * <p>
	 * �iWindows�Ȃ�Windows�̊O�ςɎ���j
	 * </p>
	 */
	private void initLookFeel() {
		try {
			String look =
			// "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
			UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(look);
		} catch (Exception e) {
			// �ʖڂȂƂ��͒��߂�
			e.printStackTrace();
		}
	}

	SampleMenuBar menuBar;

	/**
	 * ���j���[�o�[�̏�����
	 */
	private void initMenuBar() {
		menuBar = new SampleMenuBar();
		setJMenuBar(menuBar);
	}
}
