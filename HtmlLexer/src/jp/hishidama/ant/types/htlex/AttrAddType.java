package jp.hishidama.ant.types.htlex;

import java.io.IOException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.EnumeratedAttribute;

import jp.hishidama.html.lexer.rule.HtLexer;
import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.SkipToken;
import jp.hishidama.html.lexer.token.Tag;

/**
 * HtHtmlLexer�^�O�����ǉ��^�C�v.
 *<p>
 * HTML�t�@�C�����̃^�O�̑����̒ǉ����s���f�[�^�^�C�v�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2009.01.23
 * @version 2010.01.31
 */
public class AttrAddType extends AttrNewType {

	/**
	 * �d���������������@.
	 * <p>
	 * �ϊ��ɂ���đ��������d�������ꍇ�̏������@���w�肷��B
	 * </p>
	 *
	 * @version 2009.02.01
	 */
	public static class DupEnum extends EnumeratedAttribute {

		/** {@value}�F���̂܂܋����I�ɕύX����i�����������̑������o����j */
		public static final String FORCE = "force";
		/** {@value}�F�ύX���Ȃ� */
		public static final String SKIP = "skip";
		/** {@value}�F�����̑������X�V���� */
		public static final String UPDATE = "update";
		/** {@value}�F�G���[�Ƃ��� */
		public static final String ERROR = "error";

		@Override
		public String[] getValues() {
			return new String[] { FORCE, SKIP, UPDATE, ERROR };
		}

		public DupEnum() {
		}

		private DupEnum(String s) {
			setValue(s);
		}
	}

	protected String preSkip = " ";

	/**
	 * �󔒐ݒ�.
	 * <p>
	 * �ǉ����鑮���̑O�ɑ}������󔒂�ݒ肷��B�f�t�H���g�͔��p�X�y�[�X1�B
	 * </p>
	 *
	 * @param s
	 *            ��
	 */
	public void setPreSkip(String s) {
		preSkip = s;
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
	public void validate() throws BuildException {
		super.validate();
		if (newName == null || newName.isEmpty()) {
			throw new BuildException("newName attribute must be set.",
					getLocation());
		}
	}

	@Override
	public boolean convert(Tag tag) {
		AttributeToken old = tag.getAttribute(newName);
		if (old != null) {
			switch (dup.getIndex()) {
			case 0: // force
				return add(tag);
			case 1:// skip
				return false;
			default: // update
				return update(tag, old);
			case 3:// error
				throw new BuildException("duplicate attribute (add): "
						+ htmlConverter.getReadFile().getAbsolutePath() + ":"
						+ old.getLine() + " " + newName, getLocation());
			}
		}

		return add(tag);
	}

	protected boolean add(Tag tag) {
		AttributeToken a = new AttributeToken();
		updateName(tag, a);
		updateLet(tag, a);
		updateValue(tag, a);
		updateQuote(tag, a);

		HtLexer lexer = htmlConverter.getLexer(preSkip);
		SkipToken st;
		try {
			st = lexer.parseSkip();
		} catch (IOException e) {
			throw new BuildException(e, getLocation());
		} finally {
			try {
				lexer.close();
			} catch (IOException e) {
			}
		}

		tag.addSkip(st);
		tag.addAttribute(a);

		htmlConverter.logConvert("add", tag.getLine(), a);
		return true;
	}
}
