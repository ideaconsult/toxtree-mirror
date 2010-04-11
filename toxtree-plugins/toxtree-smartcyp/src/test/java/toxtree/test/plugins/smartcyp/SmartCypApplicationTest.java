package toxtree.test.plugins.smartcyp;

import org.junit.Test;

import toxtree.plugins.smartcyp.cyp450.SMARTCyp;

public class SmartCypApplicationTest {
	@Test
	public void test() throws Exception {
		String file = getClass().getClassLoader().getResource("toxtree/test/plugins/smartcyp/3A4_substrates.sdf").getFile();

		SMARTCyp.main(new String[] {file});
	}
}
