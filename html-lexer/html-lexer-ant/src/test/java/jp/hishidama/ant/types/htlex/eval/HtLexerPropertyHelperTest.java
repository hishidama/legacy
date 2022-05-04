package jp.hishidama.ant.types.htlex.eval;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import jp.hishidama.html.lexer.token.Tag;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.PropertyHelper;
import org.junit.Test;

public class HtLexerPropertyHelperTest {

	@Test
	public void getEvaluator() {
		Project project = new Project();
		PropertyHelper helper = PropertyHelper.getPropertyHelper(project);
		HtLexerPropertyHelper evaluator = HtLexerPropertyHelper.getInstance(project, "htlex");

		Tag expected = new Tag();
		evaluator.pushTagToken(expected);

		Tag value = (Tag) helper.getProperty("htlex.tag");

		assertThat(value, is(sameInstance(expected)));
	}
}
