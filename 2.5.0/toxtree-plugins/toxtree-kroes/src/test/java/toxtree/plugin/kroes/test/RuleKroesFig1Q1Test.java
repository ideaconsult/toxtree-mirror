package toxtree.plugin.kroes.test;

import junit.framework.Assert;

import org.junit.Test;

import toxtree.plugins.kroes.rules.RuleKroesFig1Q1;

/**
 * Test for {@link RuleKroesFig1Q1}
 * @author nina
 *
 */
public class RuleKroesFig1Q1Test {
	@Test
	public void test() {
		RuleKroesFig1Q1 rule = new RuleKroesFig1Q1();
		Assert.assertEquals(3,rule.getSubstructures().size());
	}
}
