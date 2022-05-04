package jp.hishidama.eval.sample;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.Expression;
import jp.hishidama.eval.var.MapVariable;

/**
 * �z��̎g�p��
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >�Ђ�����</a>
 */
public class ArraySample {

	public static void main(String[] args) {
		array();
		System.out.println();
		arrayLet();
		System.out.println();
		map();
	}

	static void array() {
		MapVariable<String, Object> map = new MapVariable<String, Object>(
				String.class, Object.class);
		int[] a = new int[4];
		a[1] = 11;
		map.put("a", a);

		Expression exp = ExpRuleFactory.getDefaultRule().parse("a[1]+=33");
		exp.setVariable(map);

		System.out.println("���Z�F" + exp.evalInt());
		System.out.println("�z��F" + Arrays.toString(a));
	}

	static void arrayLet() {
		Long[] a = new Long[4];
		a[1] = 11L;
		Map<String, Long[]> map = new HashMap<String, Long[]>();
		map.put("a", a);

		Expression exp = ExpRuleFactory.getDefaultRule().parse("c=a, c[1]++"); // �z��ϐ��������Ĉ�����
		exp.setVariable(new MapVariable<String, Long[]>(map));

		exp.eval();
		Long[] c = map.get("c");
		System.out.println("c=" + Arrays.toString(c));
		System.out.println("a=" + Arrays.toString(a));
	}

	static void map() {
		Map<String, Integer> m = new TreeMap<String, Integer>();
		m.put("abc", 123);
		m.put("def", 456);

		MapVariable<String, Object> varMap = new MapVariable<String, Object>();
		varMap.put("m1", m);

		String str = "m1[\"zzz\"] = m1[\"abc\"] + m1[\"def\"]";
		Expression exp = ExpRuleFactory.getDefaultRule().parse(str);
		exp.setVariable(varMap);

		System.out.println("���Z�@�F" + exp.eval());
		System.out.println("�}�b�v�F" + m);
	}
}
