package jp.hishidama.hadoop.cascading.pipe;

import java.beans.ConstructorProperties;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import cascading.flow.Scope;
import cascading.pipe.CoGroup;
import cascading.pipe.Pipe;
import cascading.pipe.cogroup.Joiner;
import cascading.tuple.Fields;

/**
 * 拡張CoGroup.
 * <p>
 * 当クラスでは、出力項目名（declaredFields）を指定しなかった場合に、自動的に定義する。<br>
 * その際、重複した項目名があった場合は別の名前を付ける。
 * </p>
 * <p>
 * CoGroupExのコンストラクターは、CoGroupのコンストラクターのコピペ。
 * </p>
 * <p>
 * →<a href="http://www.ne.jp/asahi/hishidama/home/tech/apache/hadoop/cascading/CoGroup.html"
 * >使用例</a>
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/apache/hadoop/cascading/index.html"
 *         >ひしだま</a>
 * @since 2010.04.13
 * @version 2010.05.01 Cascading1.1
 */
public class CoGroupEx extends CoGroup {
	private static final long serialVersionUID = 7208704794137271890L;

	/** Field LOG */
	private static final Logger LOG = Logger.getLogger(CoGroupEx.class);

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param lhs
	 *            of type Pipe
	 * @param lhsGroupFields
	 *            of type Fields
	 * @param rhs
	 *            of type Pipe
	 * @param rhsGroupFields
	 *            of type Fields
	 * @param declaredFields
	 *            of type Fields
	 */
	@ConstructorProperties( { "lhs", "lhsGroupFields", "rhs", "rhsGroupFields",
			"declaredFields" })
	public CoGroupEx(Pipe lhs, Fields lhsGroupFields, Pipe rhs,
			Fields rhsGroupFields, Fields declaredFields) {
		super(lhs, lhsGroupFields, rhs, rhsGroupFields, declaredFields);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param lhs
	 *            of type Pipe
	 * @param lhsGroupFields
	 *            of type Fields
	 * @param rhs
	 *            of type Pipe
	 * @param rhsGroupFields
	 *            of type Fields
	 * @param declaredFields
	 *            of type Fields
	 * @param resultGroupFields
	 *            of type Fields
	 */
	@ConstructorProperties( { "lhs", "lhsGroupFields", "rhs", "rhsGroupFields",
			"declaredFields", "resultGroupFields" })
	public CoGroupEx(Pipe lhs, Fields lhsGroupFields, Pipe rhs,
			Fields rhsGroupFields, Fields declaredFields,
			Fields resultGroupFields) {
		super(lhs, lhsGroupFields, rhs, rhsGroupFields, declaredFields,
				resultGroupFields);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param lhs
	 *            of type Pipe
	 * @param lhsGroupFields
	 *            of type Fields
	 * @param rhs
	 *            of type Pipe
	 * @param rhsGroupFields
	 *            of type Fields
	 * @param declaredFields
	 *            of type Fields
	 * @param joiner
	 *            of type CoGrouper
	 */
	@ConstructorProperties( { "lhs", "lhsGroupFields", "rhs", "rhsGroupFields",
			"declaredFields", "joiner" })
	public CoGroupEx(Pipe lhs, Fields lhsGroupFields, Pipe rhs,
			Fields rhsGroupFields, Fields declaredFields, Joiner joiner) {
		super(lhs, lhsGroupFields, rhs, rhsGroupFields, declaredFields, joiner);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param lhs
	 *            of type Pipe
	 * @param lhsGroupFields
	 *            of type Fields
	 * @param rhs
	 *            of type Pipe
	 * @param rhsGroupFields
	 *            of type Fields
	 * @param declaredFields
	 *            of type Fields
	 * @param resultGroupFields
	 *            of type Fields
	 * @param joiner
	 *            of type Joiner
	 */
	@ConstructorProperties( { "lhs", "lhsGroupFields", "rhs", "rhsGroupFields",
			"declaredFields", "resultGroupFields", "joiner" })
	public CoGroupEx(Pipe lhs, Fields lhsGroupFields, Pipe rhs,
			Fields rhsGroupFields, Fields declaredFields,
			Fields resultGroupFields, Joiner joiner) {
		super(lhs, lhsGroupFields, rhs, rhsGroupFields, declaredFields,
				resultGroupFields, joiner);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param lhs
	 *            of type Pipe
	 * @param lhsGroupFields
	 *            of type Fields
	 * @param rhs
	 *            of type Pipe
	 * @param rhsGroupFields
	 *            of type Fields
	 * @param joiner
	 *            of type CoGrouper
	 */
	@ConstructorProperties( { "lhs", "lhsGroupFields", "rhs", "rhsGroupFields",
			"joiner" })
	public CoGroupEx(Pipe lhs, Fields lhsGroupFields, Pipe rhs,
			Fields rhsGroupFields, Joiner joiner) {
		super(lhs, lhsGroupFields, rhs, rhsGroupFields, joiner);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param lhs
	 *            of type Pipe
	 * @param lhsGroupFields
	 *            of type Fields
	 * @param rhs
	 *            of type Pipe
	 * @param rhsGroupFields
	 *            of type Fields
	 */
	@ConstructorProperties( { "lhs", "lhsGroupFields", "rhs", "rhsGroupFields" })
	public CoGroupEx(Pipe lhs, Fields lhsGroupFields, Pipe rhs,
			Fields rhsGroupFields) {
		super(lhs, lhsGroupFields, rhs, rhsGroupFields);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param pipes
	 *            of type Pipe...
	 */
	@ConstructorProperties( { "pipes" })
	public CoGroupEx(Pipe... pipes) {
		super(pipes);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param pipes
	 *            of type Pipe[]
	 * @param groupFields
	 *            of type Fields[]
	 */
	@ConstructorProperties( { "pipes", "groupFields" })
	public CoGroupEx(Pipe[] pipes, Fields[] groupFields) {
		super(pipes, groupFields);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param pipes
	 *            of type Pipe[]
	 * @param groupFields
	 *            of type Fields[]
	 * @param declaredFields
	 *            of type Fields
	 * @param joiner
	 *            of type CoGrouper
	 */
	@ConstructorProperties( { "pipes", "groupFields", "declaredFields",
			"joiner" })
	public CoGroupEx(Pipe[] pipes, Fields[] groupFields, Fields declaredFields,
			Joiner joiner) {
		super(pipes, groupFields, declaredFields, joiner);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param pipes
	 *            of type Pipe[]
	 * @param groupFields
	 *            of type Fields[]
	 * @param declaredFields
	 *            of type Fields
	 * @param resultGroupFields
	 *            of type Fields
	 * @param joiner
	 *            of type Joiner
	 */
	@ConstructorProperties( { "pipes", "groupFields", "declaredFields",
			"resultGroupFields", "joiner" })
	public CoGroupEx(Pipe[] pipes, Fields[] groupFields, Fields declaredFields,
			Fields resultGroupFields, Joiner joiner) {
		super(pipes, groupFields, declaredFields, resultGroupFields, joiner);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param groupName
	 *            of type String
	 * @param pipes
	 *            of type Pipe[]
	 * @param groupFields
	 *            of type Fields[]
	 */
	@ConstructorProperties( { "groupName", "pipes", "groupFields" })
	public CoGroupEx(String groupName, Pipe[] pipes, Fields[] groupFields) {
		super(groupName, pipes, groupFields);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param groupName
	 *            of type String
	 * @param pipes
	 *            of type Pipe[]
	 * @param groupFields
	 *            of type Fields[]
	 * @param declaredFields
	 *            of type Fields
	 */
	@ConstructorProperties( { "groupName", "pipes", "groupFields",
			"declaredFields" })
	public CoGroupEx(String groupName, Pipe[] pipes, Fields[] groupFields,
			Fields declaredFields) {
		super(groupName, pipes, groupFields, declaredFields);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param groupName
	 *            of type String
	 * @param pipes
	 *            of type Pipe[]
	 * @param groupFields
	 *            of type Fields[]
	 * @param declaredFields
	 *            of type Fields
	 * @param resultGroupFields
	 *            of type Fields
	 */
	@ConstructorProperties( { "groupName", "pipes", "groupFields",
			"declaredFields", "resultGroupFields" })
	public CoGroupEx(String groupName, Pipe[] pipes, Fields[] groupFields,
			Fields declaredFields, Fields resultGroupFields) {
		super(groupName, pipes, groupFields, declaredFields, resultGroupFields);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param groupName
	 *            of type String
	 * @param pipes
	 *            of type Pipe[]
	 * @param groupFields
	 *            of type Fields[]
	 * @param declaredFields
	 *            of type Fields
	 * @param joiner
	 *            of type CoGrouper
	 */
	@ConstructorProperties( { "groupName", "pipes", "groupFields",
			"declaredFields", "joiner" })
	public CoGroupEx(String groupName, Pipe[] pipes, Fields[] groupFields,
			Fields declaredFields, Joiner joiner) {
		super(groupName, pipes, groupFields, declaredFields, null, joiner);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param groupName
	 *            of type String
	 * @param pipes
	 *            of type Pipe[]
	 * @param groupFields
	 *            of type Fields[]
	 * @param declaredFields
	 *            of type Fields
	 * @param resultGroupFields
	 *            of type Fields
	 * @param joiner
	 *            of type CoGrouper
	 */
	@ConstructorProperties( { "groupName", "pipes", "groupFields",
			"declaredFields", "resultGroupFields", "joiner" })
	public CoGroupEx(String groupName, Pipe[] pipes, Fields[] groupFields,
			Fields declaredFields, Fields resultGroupFields, Joiner joiner) {
		super(groupName, pipes, groupFields, declaredFields, resultGroupFields,
				joiner);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param groupName
	 *            of type String
	 * @param lhs
	 *            of type Pipe
	 * @param lhsGroupFields
	 *            of type Fields
	 * @param rhs
	 *            of type Pipe
	 * @param rhsGroupFields
	 *            of type Fields
	 * @param declaredFields
	 *            of type Fields
	 */
	@ConstructorProperties( { "groupName", "lhs", "lhsGroupFields", "rhs",
			"rhsGroupFields", "declaredFields" })
	public CoGroupEx(String groupName, Pipe lhs, Fields lhsGroupFields,
			Pipe rhs, Fields rhsGroupFields, Fields declaredFields) {
		super(groupName, lhs, lhsGroupFields, rhs, rhsGroupFields,
				declaredFields);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param groupName
	 *            of type String
	 * @param lhs
	 *            of type Pipe
	 * @param lhsGroupFields
	 *            of type Fields
	 * @param rhs
	 *            of type Pipe
	 * @param rhsGroupFields
	 *            of type Fields
	 * @param declaredFields
	 *            of type Fields
	 * @param resultGroupFields
	 *            of type Fields
	 */
	@ConstructorProperties( { "groupName", "lhs", "lhsGroupFields", "rhs",
			"rhsGroupFields", "declaredFields", "resultGroupFields" })
	public CoGroupEx(String groupName, Pipe lhs, Fields lhsGroupFields,
			Pipe rhs, Fields rhsGroupFields, Fields declaredFields,
			Fields resultGroupFields) {
		super(groupName, lhs, lhsGroupFields, rhs, rhsGroupFields,
				declaredFields, resultGroupFields);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param groupName
	 *            of type String
	 * @param lhs
	 *            of type Pipe
	 * @param lhsGroupFields
	 *            of type Fields
	 * @param rhs
	 *            of type Pipe
	 * @param rhsGroupFields
	 *            of type Fields
	 * @param declaredFields
	 *            of type Fields
	 * @param joiner
	 *            of type CoGrouper
	 */
	@ConstructorProperties( { "groupName", "lhs", "lhsGroupFields", "rhs",
			"rhsGroupFields", "declaredFields", "joiner" })
	public CoGroupEx(String groupName, Pipe lhs, Fields lhsGroupFields,
			Pipe rhs, Fields rhsGroupFields, Fields declaredFields,
			Joiner joiner) {
		super(groupName, lhs, lhsGroupFields, rhs, rhsGroupFields,
				declaredFields, joiner);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param groupName
	 *            of type String
	 * @param lhs
	 *            of type Pipe
	 * @param lhsGroupFields
	 *            of type Fields
	 * @param rhs
	 *            of type Pipe
	 * @param rhsGroupFields
	 *            of type Fields
	 * @param declaredFields
	 *            of type Fields
	 * @param resultGroupFields
	 *            of type Fields
	 * @param joiner
	 *            of type Joiner
	 */
	@ConstructorProperties( { "groupName", "lhs", "lhsGroupFields", "rhs",
			"rhsGroupFields", "declaredFields", "resultGroupFields", "joiner" })
	public CoGroupEx(String groupName, Pipe lhs, Fields lhsGroupFields,
			Pipe rhs, Fields rhsGroupFields, Fields declaredFields,
			Fields resultGroupFields, Joiner joiner) {
		super(groupName, lhs, lhsGroupFields, rhs, rhsGroupFields,
				declaredFields, resultGroupFields, joiner);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param groupName
	 *            of type String
	 * @param lhs
	 *            of type Pipe
	 * @param lhsGroupFields
	 *            of type Fields
	 * @param rhs
	 *            of type Pipe
	 * @param rhsGroupFields
	 *            of type Fields
	 * @param joiner
	 *            of type CoGrouper
	 */
	@ConstructorProperties( { "groupName", "lhs", "lhsGroupFields", "rhs",
			"rhsGroupFields", "joiner" })
	public CoGroupEx(String groupName, Pipe lhs, Fields lhsGroupFields,
			Pipe rhs, Fields rhsGroupFields, Joiner joiner) {
		super(groupName, lhs, lhsGroupFields, rhs, rhsGroupFields, joiner);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param groupName
	 *            of type String
	 * @param lhs
	 *            of type Pipe
	 * @param lhsGroupFields
	 *            of type Fields
	 * @param rhs
	 *            of type Pipe
	 * @param rhsGroupFields
	 *            of type Fields
	 */
	@ConstructorProperties( { "groupName", "lhs", "lhsGroupFields", "rhs",
			"rhsGroupFields" })
	public CoGroupEx(String groupName, Pipe lhs, Fields lhsGroupFields,
			Pipe rhs, Fields rhsGroupFields) {
		super(groupName, lhs, lhsGroupFields, rhs, rhsGroupFields);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param groupName
	 *            of type String
	 * @param pipes
	 *            of type Pipe...
	 */
	@ConstructorProperties( { "groupName", "pipes" })
	public CoGroupEx(String groupName, Pipe... pipes) {
		super(groupName, pipes);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance that performs
	 * numSelfJoins number of self joins on the given {@link Pipe} instance.
	 *
	 * @param pipe
	 *            of type Pipe
	 * @param groupFields
	 *            of type Fields
	 * @param numSelfJoins
	 *            of type int
	 * @param declaredFields
	 *            of type Fields
	 */
	@ConstructorProperties( { "pipe", "groupFields", "numSelfJoins",
			"declaredFields" })
	public CoGroupEx(Pipe pipe, Fields groupFields, int numSelfJoins,
			Fields declaredFields) {
		super(pipe, groupFields, numSelfJoins, declaredFields);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param pipe
	 *            of type Pipe
	 * @param groupFields
	 *            of type Fields
	 * @param numSelfJoins
	 *            of type int
	 * @param declaredFields
	 *            of type Fields
	 * @param resultGroupFields
	 *            of type Fields
	 */
	@ConstructorProperties( { "pipe", "groupFields", "numSelfJoins",
			"declaredFields", "resultGroupFields" })
	public CoGroupEx(Pipe pipe, Fields groupFields, int numSelfJoins,
			Fields declaredFields, Fields resultGroupFields) {
		super(pipe, groupFields, numSelfJoins, declaredFields,
				resultGroupFields);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance that performs
	 * numSelfJoins number of self joins on the given {@link Pipe} instance.
	 *
	 * @param pipe
	 *            of type Pipe
	 * @param groupFields
	 *            of type Fields
	 * @param numSelfJoins
	 *            of type int
	 * @param declaredFields
	 *            of type Fields
	 * @param joiner
	 *            of type CoGrouper
	 */
	@ConstructorProperties( { "pipe", "groupFields", "numSelfJoins",
			"declaredFields", "joiner" })
	public CoGroupEx(Pipe pipe, Fields groupFields, int numSelfJoins,
			Fields declaredFields, Joiner joiner) {
		super(pipe, groupFields, numSelfJoins, declaredFields, joiner);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param pipe
	 *            of type Pipe
	 * @param groupFields
	 *            of type Fields
	 * @param numSelfJoins
	 *            of type int
	 * @param declaredFields
	 *            of type Fields
	 * @param resultGroupFields
	 *            of type Fields
	 * @param joiner
	 *            of type Joiner
	 */
	@ConstructorProperties( { "pipe", "groupFields", "numSelfJoins",
			"declaredFields", "resultGroupFields", "joiner" })
	public CoGroupEx(Pipe pipe, Fields groupFields, int numSelfJoins,
			Fields declaredFields, Fields resultGroupFields, Joiner joiner) {
		super(pipe, groupFields, numSelfJoins, declaredFields,
				resultGroupFields, joiner);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance that performs
	 * numSelfJoins number of self joins on the given {@link Pipe} instance.
	 *
	 * @param pipe
	 *            of type Pipe
	 * @param groupFields
	 *            of type Fields
	 * @param numSelfJoins
	 *            of type int
	 * @param joiner
	 *            of type CoGrouper
	 */
	@ConstructorProperties( { "pipe", "groupFields", "numSelfJoins", "joiner" })
	public CoGroupEx(Pipe pipe, Fields groupFields, int numSelfJoins,
			Joiner joiner) {
		super(pipe, groupFields, numSelfJoins, joiner);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance that performs
	 * numSelfJoins number of self joins on the given {@link Pipe} instance.
	 *
	 * @param pipe
	 *            of type Pipe
	 * @param groupFields
	 *            of type Fields
	 * @param numSelfJoins
	 *            of type int
	 */
	@ConstructorProperties( { "pipe", "groupFields", "numSelfJoins" })
	public CoGroupEx(Pipe pipe, Fields groupFields, int numSelfJoins) {
		super(pipe, groupFields, numSelfJoins);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance that performs
	 * numSelfJoins number of self joins on the given {@link Pipe} instance.
	 *
	 * @param groupName
	 *            of type String
	 * @param pipe
	 *            of type Pipe
	 * @param groupFields
	 *            of type Fields
	 * @param numSelfJoins
	 *            of type int
	 * @param declaredFields
	 *            of type Fields
	 */
	@ConstructorProperties( { "groupName", "pipe", "groupFields",
			"numSelfJoins", "declaredFields" })
	public CoGroupEx(String groupName, Pipe pipe, Fields groupFields,
			int numSelfJoins, Fields declaredFields) {
		super(groupName, pipe, groupFields, numSelfJoins, declaredFields);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param groupName
	 *            of type String
	 * @param pipe
	 *            of type Pipe
	 * @param groupFields
	 *            of type Fields
	 * @param numSelfJoins
	 *            of type int
	 * @param declaredFields
	 *            of type Fields
	 * @param resultGroupFields
	 *            of type Fields
	 */
	@ConstructorProperties( { "groupName", "pipe", "groupFields",
			"numSelfJoins", "declaredFields", "resultGroupFields" })
	public CoGroupEx(String groupName, Pipe pipe, Fields groupFields,
			int numSelfJoins, Fields declaredFields, Fields resultGroupFields) {
		super(groupName, pipe, groupFields, numSelfJoins, declaredFields,
				resultGroupFields);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance that performs
	 * numSelfJoins number of self joins on the given {@link Pipe} instance.
	 *
	 * @param groupName
	 *            of type String
	 * @param pipe
	 *            of type Pipe
	 * @param groupFields
	 *            of type Fields
	 * @param numSelfJoins
	 *            of type int
	 * @param declaredFields
	 *            of type Fields
	 * @param joiner
	 *            of type CoGrouper
	 */
	@ConstructorProperties( { "groupName", "pipe", "groupFields",
			"numSelfJoins", "declaredFields", "joiner" })
	public CoGroupEx(String groupName, Pipe pipe, Fields groupFields,
			int numSelfJoins, Fields declaredFields, Joiner joiner) {
		super(groupName, pipe, groupFields, numSelfJoins, declaredFields,
				joiner);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance.
	 *
	 * @param groupName
	 *            of type String
	 * @param pipe
	 *            of type Pipe
	 * @param groupFields
	 *            of type Fields
	 * @param numSelfJoins
	 *            of type int
	 * @param declaredFields
	 *            of type Fields
	 * @param resultGroupFields
	 *            of type Fields
	 * @param joiner
	 *            of type Joiner
	 */
	@ConstructorProperties( { "groupName", "pipe", "groupFields",
			"numSelfJoins", "declaredFields", "resultGroupFields", "joiner" })
	public CoGroupEx(String groupName, Pipe pipe, Fields groupFields,
			int numSelfJoins, Fields declaredFields, Fields resultGroupFields,
			Joiner joiner) {
		super(groupName, pipe, groupFields, numSelfJoins, declaredFields,
				resultGroupFields, joiner);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance that performs
	 * numSelfJoins number of self joins on the given {@link Pipe} instance.
	 *
	 * @param groupName
	 *            of type String
	 * @param pipe
	 *            of type Pipe
	 * @param groupFields
	 *            of type Fields
	 * @param numSelfJoins
	 *            of type int
	 * @param joiner
	 *            of type CoGrouper
	 */
	@ConstructorProperties( { "groupName", "pipe", "groupFields",
			"numSelfJoins", "joiner" })
	public CoGroupEx(String groupName, Pipe pipe, Fields groupFields,
			int numSelfJoins, Joiner joiner) {
		super(groupName, pipe, groupFields, numSelfJoins, joiner);
	}

	/**
	 * Constructor CoGroup creates a new CoGroup instance that performs
	 * numSelfJoins number of self joins on the given {@link Pipe} instance.
	 *
	 * @param groupName
	 *            of type String
	 * @param pipe
	 *            of type Pipe
	 * @param groupFields
	 *            of type Fields
	 * @param numSelfJoins
	 *            of type int
	 */
	@ConstructorProperties( { "groupName", "pipe", "groupFields",
			"numSelfJoins" })
	public CoGroupEx(String groupName, Pipe pipe, Fields groupFields,
			int numSelfJoins) {
		super(groupName, pipe, groupFields, numSelfJoins);
	}

	/**
	 * 出力項目名（declaredFields）がnullの場合に項目名を定義してから、スーパークラスのメソッドを呼び出す。 {@inheritDoc}
	 */
	@Override
	public Scope outgoingScopeFor(Set<Scope> incomingScopes) {
		if (getDeclaredFields() == null) {
			super.declaredFields = createDeclaredFields(incomingScopes);
			if (LOG.isDebugEnabled()) {
				LOG.debug("create declaredFields: [" + getDeclaredFields()
						+ "]");
			}

		}
		return super.outgoingScopeFor(incomingScopes);
	}

	/**
	 * 出力フィールドを生成する。
	 *
	 * @param incomingScopes
	 *            入力スコープ
	 * @return フィールド名
	 */
	protected Fields createDeclaredFields(Set<Scope> incomingScopes) {
		// see Group#resolveDeclared(Set<Scope>)

		Map<String, Scope> scopesMap = new HashMap<String, Scope>();
		for (Scope incomingScope : incomingScopes) {
			scopesMap.put(incomingScope.getName(), incomingScope);
		}

		Set<String> nameSet = new HashSet<String>();
		Fields appendedFields = new Fields();

		Pipe[] pipes = getPrevious();
		for (int i = 0; i < pipes.length; i++) {
			Pipe pipe = pipes[i];
			Fields appendableField = resolveFields(scopesMap
					.get(pipe.getName()));

			int sz = appendableField.size();
			Comparable<?>[] names = new Comparable[sz];
			for (int j = 0; j < sz; j++) {
				Comparable<?> name = appendableField.get(j);
				if (name instanceof String) {
					if (nameSet.contains(name)) {
						name = convertFieldName(i, pipe, (String) name);
					}
					nameSet.add((String) name);
				}
				names[j] = name;
			}

			appendedFields = appendedFields.append(new Fields(names));
		}

		return appendedFields;
	}

	/**
	 * 重複した名前を別の名前に変換する。
	 *
	 * @param i
	 *            パイプの番号
	 * @param pipe
	 *            対象フィールドの属しているパイプ
	 * @param fieldName
	 *            フィールド名
	 * @return 変換されたフィールド名
	 */
	protected String convertFieldName(int i, Pipe pipe, String fieldName) {
		return pipe.getName() + "." + fieldName;
	}
}
