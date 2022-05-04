package jp.hishidama.ant.types.htlex;

import java.io.IOException;

import jp.hishidama.html.lexer.rule.HtLexer;
import jp.hishidama.html.lexer.token.*;
import jp.hishidama.html.parser.elem.HtElement;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.DataType;
import org.apache.tools.ant.types.EnumeratedAttribute;

/**
 * HtHtmlLexer�e�L�X�g�^�C�v.
 *<p>
 * HTML�t�@�C�����̃e�L�X�g�̏������f�E�u�����s���f�[�^�^�C�v�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2009.01.28
 * @version 2009.02.17
 */
public class TextType extends DataType {

	/**
	 * @see org.apache.tools.ant.taskdefs.Replace.NestedString
	 */
	public class NestedString {

		private StringBuilder buf = new StringBuilder();

		public void addText(String val) {
			buf.append(val);
		}

		public String getText() {
			return buf.toString();
		}
	}

	protected NestedString text;

	/**
	 * ����������ݒ�.
	 *
	 * @param s
	 *            ������
	 */
	public void setText(String s) {
		createText().addText(s);
	}

	/**
	 * ����������i�v�f�j�쐬.
	 *
	 * @return token�v�f
	 */
	public NestedString createText() {
		if (text == null) {
			text = new NestedString();
		}
		return text;
	}

	protected NestedString newText = null;
	protected String newTextString = null;

	/**
	 * �u���敶����ݒ�.
	 *
	 * @param s
	 *            ������
	 */
	public void setNewText(String s) {
		createNewText().addText(s);
	}

	/**
	 * �u���敶����i�v�f�j�쐬.
	 *
	 * @return replacevalue�v�f
	 */
	public NestedString createNewText() {
		if (newText == null) {
			newText = new NestedString();
		}
		return newText;
	}

	protected MatchEnum textMatch;

	/**
	 * �e�L�X�g�}�b�`���@�ݒ�.
	 *
	 * @param match
	 *            �}�b�`���O���@
	 */
	public void setTextMatch(MatchEnum match) {
		textMatch = match;
	}

	protected StringOpeEnum textOpe = new StringOpeEnum(StringOpeEnum.LET);

	/**
	 * �e�L�X�g�u�����@�ݒ�.
	 *
	 * @param ope
	 *            �u�����@
	 */
	public void setNewTextOperation(StringOpeEnum ope) {
		textOpe = ope;
	}

	/**
	 * �e�L�X�g���.
	 *
	 * @version 2009.02.01
	 */
	public static class TextEnum extends EnumeratedAttribute {

		/** "all"�F�S�e�L�X�g */
		public static final String ALL = "all";
		/** "text"�F�ʏ�e�L�X�g�̂� */
		public static final String TEXT = "text";
		/** "script"�F�X�N���v�g�e�L�X�g�̂� */
		public static final String SCRIPT = "script";

		@Override
		public String[] getValues() {
			return new String[] { ALL, TEXT, SCRIPT };
		}

		public TextEnum() {
		}

		private TextEnum(String s) {
			setValue(s);
		}

		/**
		 * �e�L�X�g��ޔ��f.
		 *
		 * @param token
		 *            �e�L�X�g
		 * @return �w�肳��Ă�����ނ̏ꍇ�Atrue
		 */
		public boolean isType(TextToken token) {
			switch (getIndex()) {
			default:
				return true;
			case 1:
				return !(token instanceof ScriptToken);
			case 2:
				return (token instanceof ScriptToken);
			}
		}
	}

	protected TextEnum textType = new TextEnum(TextEnum.TEXT);

	/**
	 * �e�L�X�g��ސݒ�.
	 *
	 * @param type
	 *            �e�L�X�g���
	 */
	public void setTextType(TextEnum type) {
		textType = type;
	}

	protected boolean lines = false;

	/**
	 * �����s�w��.
	 *
	 * @param b
	 *            true�̏ꍇ�A���s���܂񂾕����s����̕�����Ƃ��Č����E�u������B<br>
	 *            false�̏ꍇ�A�s���ƂɌ����E�u������B
	 */
	public void setLines(boolean b) {
		lines = b;
	}

	protected HtLexerConverter converter;

	/**
	 * �������s.
	 *
	 * @param conv
	 *            �R���o�[�^�[
	 * @throws BuildException
	 *             �����G���[��
	 */
	public void validate(HtLexerConverter conv) throws BuildException {
		converter = conv;
		if (text == null) {
			throw new BuildException("token attribute must be set.",
					getLocation());
		}
		if (text.getText().isEmpty()) {
			throw new BuildException(
					"token attribute must not be an empty string.",
					getLocation());
		}

		if (textMatch == null) {
			textMatch = new MatchEnum(MatchEnum.EQUALS);
		}
		textMatch.setPattern(text.getText());
		textOpe.setProject(getProject());
		textOpe.validate(textMatch, getLocation());

		if (newText != null) {
			newTextString = newText.getText();
		}
	}

	/**
	 * �e�L�X�g�ϊ����s.
	 *
	 * @param token
	 *            �e�L�X�g
	 * @param he
	 *            �e�L�X�g�̑����Ă���v�f�i��͂���Ă��Ȃ��ꍇ�Anull�j
	 * @return ���e��ύX�����ꍇ�Atrue
	 */
	public boolean convert(TextToken token, HtElement he) {
		if (!textType.isType(token)) {
			return false;
		}

		if (lines) {
			return convertLines(token, he);
		} else {
			return contertLine(token, he);
		}
	}

	protected boolean convertLines(TextToken token, HtElement he) {
		String o = token.getText();
		if (textMatch.matches(o)) {
			converter.logMatch(token);

			String n = convert(o, newTextString);
			if (!converter.equals(n, o)) {
				HtLexer lexer = converter.getLexer(n);
				TextToken tt;
				try {
					tt = lexer.parseText();
				} catch (IOException e) {
					throw new BuildException(e, getLocation());
				} finally {
					try {
						lexer.close();
					} catch (IOException e) {
					}
				}
				tt.calcLine(token.getLine());
				token.setText(tt);
				converter.logConvert("rep", token.getLine(), token);
				return true;
			}
		}

		return false;
	}

	protected boolean contertLine(TextToken token, HtElement he) {
		boolean ret = false;
		for (Token i : token) {
			if (i instanceof WordAtom) {
				String s = i.getText();
				if (textMatch.matches(s)) {
					converter.logMatch(i);
					boolean r = updateWord(token, (WordAtom) i, s);
					if (r) {
						converter.logConvert("rep", i.getLine(), i);
						ret |= r;
					}
				}
			}
		}
		return ret;
	}

	protected boolean updateWord(TextToken text, WordAtom t, String o) {
		if (newTextString == null) {
			return false;
		}
		String n = convert(o, newTextString);
		if (!converter.equals(n, o)) {
			t.setString(n);
			return true;
		}

		return false;
	}

	protected String convert(String text, String newText) {
		return textOpe.convert(text, newText);
	}
}
