package toxTree.tree;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.nonotify.NoNotificationChemObjectBuilder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.openscience.jchempaint.renderer.selection.IChemObjectSelection;
import org.openscience.jchempaint.renderer.selection.SingleSelection;

import toxTree.exceptions.DecisionMethodException;
import ambit2.base.exceptions.AmbitException;
import ambit2.base.interfaces.IProcessor;

public abstract class AbstractRuleHilightHits extends AbstractRule {

	
	  /**
	 * 
	 */
	private static final long serialVersionUID = -6028214376570266393L;

	public IProcessor<IAtomContainer, IChemObjectSelection> getSelector() {
	    	return new IProcessor<IAtomContainer, IChemObjectSelection>() {
	    		public IChemObjectSelection process(IAtomContainer mol)
	    				throws AmbitException {
	    			try {
	    				IAtomContainer selected = NoNotificationChemObjectBuilder.getInstance().newAtomContainer();
		    			verifyRule(mol, selected);
		    			//selected = AtomContainerManipulator.removeHydrogensPreserveMultiplyBonded(selected);
		    			if (selected.getAtomCount()==0) return null;
		    			else return new SingleSelection<IAtomContainer>(selected);
	    			} catch (DecisionMethodException x) {
	    				throw new AmbitException(x);
	    			}
	    		}
	    		public boolean isEnabled() {
	    			return true;
	    		}
	    		public long getID() {
	    			return 0;
	    		}
	    		public void setEnabled(boolean arg0) {
	    		}
	    	};
	    }
		   
	  public abstract boolean  verifyRule(org.openscience.cdk.interfaces.IAtomContainer mol,IAtomContainer selected) throws DecisionMethodException;

}
