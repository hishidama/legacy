package jp.hishidama.swing.drag.window;

import java.awt.*;

/**
 * �[���I�ɔw�i�𓧉߂��锠���h���b�O����E�B���h�E.
 * <p>
 * �g���ȊO�̔w�i���[���I�ɓ��߂���B<br>
 * �ŏ��Ƀf�X�N�g�b�v�̑S��ʂ��擾����̂ŁA�}�V���p���[���Ȃ��Əd���B
 * </p>
 * <p>��<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing/DragWindow.html">�g�p��</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing.html">�Ђ�����</a>
 * @since 2007.02.11
 * @version 2007.02.22
 */
public class DragWindow extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2338068862539204147L;

	/** �g���̐F */
	protected Color borderColor = Color.BLACK;

	/** �E�B���h�E�쐬���̑S��ʂ̉摜 */
	protected Image image;

	/** �}�E�X�ƃE�B���h�E�̑��΍��WX */
	protected int nx;

	/** �}�E�X�ƃE�B���h�E�̑��΍��WY */
	protected int ny;

	/**
	 * jre1.6�Ŏg�p�\�ȃR���X�g���N�^�[
	 * 
	 * @param mouse
	 *            �ŏ��̃}�E�X�̈ʒu
	 * @param location
	 *            �ŏ��̃E�B���h�E�̈ʒu
	 * @param size
	 *            �E�B���h�E�̃T�C�Y
	 */
	public DragWindow(Point mouse, Point location, Dimension size) {
		this(null, mouse, location, size);
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param owner
	 *            �e�E�B���h�E
	 * @param mouse
	 *            �ŏ��̃}�E�X�̈ʒu
	 * @param location
	 *            �ŏ��̃E�B���h�E�̈ʒu
	 * @param size
	 *            �E�B���h�E�̃T�C�Y
	 */
	public DragWindow(Window owner, Point mouse, Point location, Dimension size) {
		super(owner);
		setBounds(location.x, location.y, size.width, size.height);
		nx = location.x - mouse.x;
		ny = location.y - mouse.y;
		initImage();
	}

	/**
	 * �摜�̏�����
	 */
	public void initImage() {
		Dimension sz = Toolkit.getDefaultToolkit().getScreenSize();

		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}
		Point pt = new Point(0, 0);
		image = robot.createScreenCapture(new Rectangle(pt, sz));
	}

	/**
	 * �g���̐F��ݒ�
	 * 
	 * @param c
	 *            �g���̐F
	 */
	public void setBorderColor(Color c) {
		borderColor = c;
	}

	/**
	 * �g���̐F���擾
	 * 
	 * @return �g���̐F
	 */
	public Color getBorderColor() {
		return borderColor;
	}

	/**
	 * �}�E�X�ʒu�w��ł̃E�B���h�E�ړ�.
	 * <p>
	 * ���E�B���h�E�쐬���ɕێ������ŏ��̃}�E�X�ʒu�Ɣ�r���A�E�B���h�E�̈ʒu���ړ�������B
	 * </p>
	 */
	public void setMouseLocation(Point mouse) {
		int x = mouse.x + nx;
		int y = mouse.y + ny;
		setLocation(x, y);
	}

	public void setBounds(int x, int y, int width, int height) {
		int oldx = getX();
		int oldy = getY();
		super.setBounds(x, y, width, height);
		if (x != oldx || y != oldy) {
			invalidate();
			repaint();
		}
	}

	/**
	 * �`����s
	 */
	public void paint(Graphics g) {
		int x = getX();
		int y = getY();
		int w = getWidth();
		int h = getHeight();

		g.drawImage(image, 0, 0, w, h, x, y, x + w, y + h, null);

		g.setColor(getBorderColor());
		g.drawRect(0, 0, w - 1, h - 1);
	}

}
