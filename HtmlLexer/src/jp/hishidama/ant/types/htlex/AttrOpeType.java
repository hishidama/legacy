package jp.hishidama.ant.types.htlex;

import java.util.List;

import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.ListToken;
import jp.hishidama.html.lexer.token.Tag;

import org.apache.tools.ant.BuildException;

/**
 * HtHtmlLexer�^�O��������^�C�v.
 *<p>
 * HTML�t�@�C�����̃^�O�̑����̑�����Ǘ�����f�[�^�^�C�v�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2009.01.23
 */
public class AttrOpeType extends AttrType {

	/**
	 * �������s.
	 *
	 * @throws BuildException
	 *             �����G���[��
	 */
	public void validate() throws BuildException {
		// �I�[�o�[���C�h�����
	}

	/**
	 * �^�O�����ϊ����s.
	 *
	 * @param tag
	 *            �^�O
	 * @return ���e��ύX�����ꍇ�Atrue
	 */
	public boolean convert(Tag tag) {
		boolean ret = false;
		List<AttributeToken> list = tag.getAttributeList();
		for (AttributeToken a : list) {
			pushAttributeToken(a);
			try {
				if (matches(a)) {
					ret |= convert(tag, a);
				}
			} finally {
				popAttributeToken();
			}
		}
		return ret;
	}

	protected boolean convert(Tag tag, AttributeToken a) {
		// �I�[�o�[���C�h����
		return false;
	}

	protected boolean delete(Tag tag, AttributeToken a) {
		ListToken r = tag.cutWithPreSkip(a);
		return r != null;
	}
}
