package jp.hishidama.eval.sample;

import jp.hishidama.eval.Expression;
import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.var.MapVariable;

/**
 * �ϐ��̎g�p��
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >�Ђ�����</a>
 */
public class VarSample {

	public static void main(String[] args) {
		example1();
		System.out.println();
		example2();
	}

	private static void example1() {
		MapVariable<String, Long> varMap = new MapVariable<String, Long>(
				String.class, Long.class);
		varMap.put("aaa", 2L);
		dumpMap(varMap);
		String str = "1 + aaa * 3";
		System.out.println("���@�F" + str);
		Expression exp = ExpRuleFactory.getDefaultRule().parse(str);

		exp.setVariable(varMap);
		System.out.println("���ʁF" + exp.evalLong());
	}

	private static void example2() {
		MapVariable<String, Long> varMap = new MapVariable<String, Long>(
				String.class, Long.class);
		varMap.put("aaa", 3L);
		dumpMap(varMap);
		String str = "bbb=4, aaa+=bbb*5, aaa+bbb";
		System.out.println("���@�F" + str);
		Expression exp = ExpRuleFactory.getDefaultRule().parse(str);

		exp.setVariable(varMap);
		System.out.println("���ʁF" + exp.evalLong());
		dumpMap(varMap);
	}

	private static void dumpMap(MapVariable<String, Long> varMap) {
		for (String key : varMap.getMap().keySet()) {
			Long val = varMap.get(key);
			System.out.println(key + " = " + val);
		}
	}
}
