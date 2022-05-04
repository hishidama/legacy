package jp.hishidama.eval.sample;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.Expression;
import jp.hishidama.eval.Rule;
import jp.hishidama.eval.exp.*;
import jp.hishidama.eval.lex.LexFactory;
import jp.hishidama.eval.lex.comment.CommentLex;
import jp.hishidama.eval.lex.comment.LineComment;

/**
 * �Ǝ����[���̍쐬��
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >�Ђ�����</a>
 */
public class FactorySample {
	public static void main(String[] args) {

		BasicPowerRuleFactory factory = new BasicPowerRuleFactory();
		Rule rule = factory.getRule();

		String str = "256 ^ 2 '���";
		System.out.println("���F" + str);

		Expression exp = rule.parse(str);
		long ret = exp.evalLong();

		System.out.println("= " + ret);
	}

	/**
	 * �ݏ�̉��Z�q��BASIC�́u^�v�ɂ���t�@�N�g���[.
	 */
	public static class BasicPowerRuleFactory extends ExpRuleFactory {

		public BasicPowerRuleFactory() {
			super();
		}

		@Override
		protected AbstractExpression createBitXorExpression() {
			// �u^�v��r���I�_���a�ł͎g��Ȃ��悤�ɂ���
			return null;
		}

		@Override
		protected AbstractExpression createLetXorExpression() {
			// �u^=�v��r���I�_���a�ł͎g��Ȃ��悤�ɂ���
			return null;
		}

		@Override
		protected AbstractExpression createPowerExpression() {
			// �u^�v���w�����Z�q�Ƃ���
			AbstractExpression e = new PowerExpression();
			e.setOperator("^");
			return e;
		}

		@Override
		protected AbstractExpression createLetPowerExpression() {
			// �u^=�v���w�����Z�̑�����Z�q�Ƃ���
			AbstractExpression e = new LetPowerExpression();
			e.setOperator("^=");
			return e;
		}

		@Override
		protected LexFactory getLexFactory() {
			// �u'�v���s�R�����g�Ƃ���
			List<CommentLex> list = new ArrayList<CommentLex>();
			list.add(new LineComment("'"));

			LexFactory factory = super.getLexFactory();
			factory.setDefaultCommentLexList(list);
			return factory;
		}
	}
}
