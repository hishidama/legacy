package jp.hishidama.ant.types.htlex;

import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.Tag;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.EnumeratedAttribute;

/**
 * HtHtmlLexer�^�O�����u���^�C�v.
 *<p>
 * HTML�t�@�C�����̃^�O�̑����̒u�����s���f�[�^�^�C�v�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2009.01.23
 */
public class AttrRepType extends AttrNewType {

	/**
	 * �d���������������@.
	 * <p>
	 * �ϊ��ɂ���đ��������d�������ꍇ�̏������@���w�肷��B
	 * </p>
	 *
	 * @version 2009.02.01
	 */
	public static class DupEnum extends EnumeratedAttribute {

		/** {@value}�F���̂܂܋����I�ɒǉ�����i�����������̑������o����j */
		public static final String FORCE = "force";
		/** {@value}�F�ύX���Ȃ� */
		public static final String SKIP = "skip";
		/** {@value}�F�����̑������폜���A�V���������𐶂��� */
		public static final String UPDATE = "update";
		/** {@value}�F�G���[�Ƃ��� */
		public static final String ERROR = "error";
		/** {@value}�F�ϊ����悤�Ƃ��Ă����������폜���� */
		public static final String DELETE = "delete";

		@Override
		public String[] getValues() {
			return new String[] { FORCE, SKIP, UPDATE, ERROR, DELETE };
		}

		public DupEnum() {
		}

		private DupEnum(String s) {
			setValue(s);
		}
	}

	protected DupEnum dup = new DupEnum(DupEnum.UPDATE);

	/**
	 * ���O�d��������ݒ�.
	 *
	 * @param d
	 *            �d���������������@
	 */
	public void setDup(DupEnum d) {
		dup = d;
	}

	@Override
	public boolean convert(Tag tag, AttributeToken a) throws BuildException {
		if (newName != null) {
			AttributeToken old = tag.getAttribute(newName);
			if (old != null && old != a) {
				switch (dup.getIndex()) {
				case 0:// force
					break;
				case 1:// skip
					return false;
				default:// update
					return delete(tag, old) | update(tag, a);
					// case ?:
					// return delete(tag, a) | update(tag, old);
				case 3: // error
					throw new BuildException("duplicate attribute (replace): "
							+ htmlConverter.getReadFile().getAbsolutePath()
							+ ":" + +old.getLine() + " " + newName,
							getLocation());
				case 4:// delete
					return delete(tag, a);
				}
			}
		}

		return update(tag, a);
	}
}
