package jp.hishidama.swing.drag.window;

import java.awt.*;

/**
 * �摜���h���b�O����E�B���h�E.
 * 
 * <p>��<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing/DragWindow.html">�g�p��</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing.html">�Ђ�����</a>
 * @since 2007.02.25
 */
public class DragImageWindow extends DragWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2474361760012233166L;

	/**
	 * jre1.6�Ŏg�p�\�ȃR���X�g���N�^�[
	 * 
	 * @param mouse
	 *            �ŏ��̃}�E�X�̈ʒu
	 * @param location
	 *            �ŏ��̃E�B���h�E�̈ʒu
	 * @param image
	 *            �h���b�O���ɕ\������摜
	 */
	public DragImageWindow(Point mouse, Point location, Image image) {
		this(null, mouse, location, image);
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
	 * @param image
	 *            �h���b�O���ɕ\������摜
	 */
	public DragImageWindow(Window owner, Point mouse, Point location,
			Image image) {
		super(owner, mouse, location, new Dimension(image.getWidth(null), image
				.getHeight(null)));
		this.image = image;
	}

	public void initImage() {
	}

	public void paint(Graphics g) {
		// int w = getWidth();
		// int h = getHeight();

		g.drawImage(image, 0, 0, this);

		// g.setColor(getBorderColor());
		// g.drawRect(0, 0, w - 1, h - 1);
	}
}
