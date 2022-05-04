package jp.hishidama.ant.types.htlex;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.condition.Condition;

import jp.hishidama.html.lexer.token.AttributeToken;

/**
 * HtHtmlLexer�^�O�����^�C�v.
 *<p>
 * HTML�t�@�C�����̃^�O�̑����̏������f���s���f�[�^�^�C�v�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2009.01.21
 * @version 2010.01.31
 */
public class AttrCondType extends AttrType implements Condition {

	protected TagType tag;

	/**
	 * �������s.
	 *
	 * @param tag
	 *            �^�O�^�C�v
	 * @throws BuildException
	 *             �����G���[��
	 */
	public void validate(TagType tag) throws BuildException {
		this.tag = tag;

		if (nameMatch == null && valueMatch == null && let == null
				&& quote == null && ifExp == null) {
			throw new BuildException("attribute must be set.", getLocation());
		}
	}

	/**
	 * �������f���s.
	 *
	 * @return �w�肳��Ă��������Ƀ}�b�`�����ꍇ�Atrue
	 */
	@Override
	public boolean eval() {
		for (AttributeToken a : tag.getTokenList()) {
			pushAttributeToken(a);
			try {
				if (matches(a)) {
					doSetProperty(a);
					return true;
				}
			} finally {
				popAttributeToken();
			}
		}
		return false;
	}
}
