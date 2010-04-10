/**
 * <b>Filename</b> toxTreeEventTest.java 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Created</b> 2005-8-1
 * <b>Project</b> toxTree
 */
package toxTree;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.cramer.CramerRules;

/**
 * 
 * @author Nina Jeliazkova <br>
 * <b>Modified</b> 2009-25-7
 */
public class toxTreeEventTest  {
	CramerRules rules = null;
	PropertyChangeListener rulesListener;
	PropertyChangeListener resultListener;	

	@Before
	public void setUp() throws Exception {
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
	@Test
    public void testClassify() throws Exception {
    	CramerRules rules = null;
   		rules = new CramerRules();

    	IDecisionResult result = rules.createDecisionResult();
		
		result.addPropertyChangeListener(resultListener);
		Molecule mol = MoleculeFactory.makeAlkane(6);		
		result.setDecisionMethod(rules);
		try {
			result.classify(mol);
		} catch (DecisionResultException x) {
		    Assert.assertTrue(!rules.isFalseIfRuleNotImplemented());
		}
		mol = MoleculeFactory.make4x3CondensedRings();
		try {
			result.classify(mol);
		} catch (DecisionResultException x) {
		    Assert.assertTrue(!rules.isFalseIfRuleNotImplemented());
		}		
    }


}

