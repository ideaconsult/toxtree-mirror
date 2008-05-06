/**
 * <b>Filename</b> toxTreeEventTest.java 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Created</b> 2005-8-1
 * <b>Project</b> toxTree
 */
package toxTree.test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import junit.framework.TestCase;

import org.openscience.cdk.Molecule;
import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.cramer.CramerRules;

/**
 * 
 * @author Nina Jeliazkova <br>
 * <b>Modified</b> 2005-8-1
 */
public class toxTreeEventTest extends TestCase {
	CramerRules rules = null;
	PropertyChangeListener rulesListener;
	PropertyChangeListener resultListener;	

    public static void main(String[] args) {
        junit.textui.TestRunner.run(toxTreeEventTest.class);
    }


	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		rules = new CramerRules();
		rulesListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println(evt.getPropertyName());
                System.out.println(evt.getNewValue());                
                System.out.println(evt.getOldValue());                

            }
        };
        rules.addPropertyChangeListener(rulesListener);
        resultListener = new PropertyChangeListener() {
            /* (non-Javadoc)
             * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
             */
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.print("ResultListener\t");
                System.out.print(evt.getPropertyName());
                System.out.print("\tNew\t");                
                System.out.print(evt.getNewValue()); 
                System.out.print("\tOld\t");                
                System.out.println(evt.getOldValue());                


            }
        };
	}
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		rules = null;
	}
    /**
     * Constructor for toxTreeEventTest.
     * @param arg0
     */
    public toxTreeEventTest(String arg0) {
        super(arg0);
    }


    public void testClassify() {
    	CramerRules rules = null;
    	try {
    		rules = new CramerRules();
    	} catch (DecisionMethodException x) {
    		fail();
    	}
    	IDecisionResult result = rules.createDecisionResult();
		
		result.addPropertyChangeListener(resultListener);
		Molecule mol = MoleculeFactory.makeAlkane(6);		
		result.setDecisionMethod(rules);
		try {
			result.classify(mol);
		} catch (DecisionResultException x) {
		    assertTrue(!rules.isFalseIfRuleNotImplemented());
		}
		mol = MoleculeFactory.make4x3CondensedRings();
		try {
			result.classify(mol);
		} catch (DecisionResultException x) {
		    assertTrue(!rules.isFalseIfRuleNotImplemented());
		}		
    }


}

