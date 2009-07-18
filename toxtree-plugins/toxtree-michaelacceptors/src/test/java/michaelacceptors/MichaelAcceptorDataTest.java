package michaelacceptors;

import junit.framework.TestCase;
import toxTree.core.IDecisionRuleList;
import toxTree.exceptions.DecisionMethodException;
import toxTree.logging.TTLogger;

public class MichaelAcceptorDataTest extends TestCase {
	
	protected MichaelAcceptorRules cr = null;
	static protected TTLogger logger = new TTLogger(MichaelAcceptorDataTest.class);
	public static void main(String[] args) {
		junit.textui.TestRunner.run(MichaelAcceptorDataTest.class);
	}
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public MichaelAcceptorDataTest(String arg0) {
		super(arg0);
		try {
			cr = new MichaelAcceptorRules();
			cr.setResiduesIDVisible(false);
			TTLogger.configureLog4j(false);
		} catch (DecisionMethodException x) {
			fail();
		}	
	}
	public void testHasUnreachableRules() {
    	IDecisionRuleList unvisited = cr.hasUnreachableRules();
    	if ((unvisited == null) || (unvisited.size()==0))
    		assertTrue(true);
    	else {
    		System.out.println("Unvisited rules:");
    		System.out.println(unvisited);
    		fail();
    	} 
    	
    }

}
