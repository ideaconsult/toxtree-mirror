package toxtree.plugins.proteinbinding;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorSpecification;

import toxTree.core.IDecisionInteractive;
import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNodesFactory;
import toxTree.tree.UserDefinedTree;
import toxtree.plugins.proteinbinding.categories.AcylTransferAlerts;
import toxtree.plugins.proteinbinding.categories.MichaelAcceptors;
import toxtree.plugins.proteinbinding.categories.NoAlerts;
import toxtree.plugins.proteinbinding.categories.ProteinBindingAlerts;
import toxtree.plugins.proteinbinding.categories.SN2Alerts;
import toxtree.plugins.proteinbinding.categories.SNArAlerts;
import toxtree.plugins.proteinbinding.categories.ShiffBaseAlerts;
import toxtree.plugins.proteinbinding.rules.AcylTransferRule;
import toxtree.plugins.proteinbinding.rules.MichaelAcceptorRule;
import toxtree.plugins.proteinbinding.rules.ProteinBindingTreeResult;
import toxtree.plugins.proteinbinding.rules.RuleProteinBindingAlerts;
import toxtree.plugins.proteinbinding.rules.SN2Rule;
import toxtree.plugins.proteinbinding.rules.SNARRule;
import toxtree.plugins.proteinbinding.rules.ShiffBaseRule;
import toxtree.plugins.proteinbinding.rules.VerifyAlertsProteinBinding;

/**
 * Protein binding
 * @author nina
 *
 */
public class ProteinBindingPlugin extends UserDefinedTree  implements IDecisionInteractive{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8574075125273844906L; 

	   protected boolean residuesIDVisible;
	    public final static transient String[] c_rules = {
	    	RuleProteinBindingAlerts.class.getName(),
	    	SNARRule.class.getName(),
	    	ShiffBaseRule.class.getName(),
	    	MichaelAcceptorRule.class.getName(),
	    	AcylTransferRule.class.getName(),
	    	SN2Rule.class.getName(),
	    	VerifyAlertsProteinBinding.class.getName()

	        };
	    private final static transient int c_transitions[][] ={
	        //{if no go to, if yes go to, assign if no, assign if yes}
	        {ProteinBindingAlerts.SNAR.ordinal()+2,ProteinBindingAlerts.SNAR.ordinal()+2,
        		0,0},
	        {ProteinBindingAlerts.SHIFF_BASE.ordinal()+2,ProteinBindingAlerts.SHIFF_BASE.ordinal()+2,
	        		0,ProteinBindingAlerts.SNAR.ordinal()+1},
	        		
   	        {ProteinBindingAlerts.MICHAEL_ACCEPTORS.ordinal()+2,ProteinBindingAlerts.MICHAEL_ACCEPTORS.ordinal()+2,
	        		0,ProteinBindingAlerts.SHIFF_BASE.ordinal()+1},
	        		
   	        {ProteinBindingAlerts.ACYL_TRANSFER.ordinal()+2,ProteinBindingAlerts.ACYL_TRANSFER.ordinal()+2,
	        		0,ProteinBindingAlerts.MICHAEL_ACCEPTORS.ordinal()+1},
	        		
   	        {ProteinBindingAlerts.SN2.ordinal()+2,ProteinBindingAlerts.SN2.ordinal()+2,
	        		0,ProteinBindingAlerts.ACYL_TRANSFER.ordinal()+1},

	        {7,0,0,ProteinBindingAlerts.SN2.ordinal()+1} ,     		
    	    {0,0,ProteinBindingAlerts.NO_ALERTS.ordinal()+1,0}
	  
	    };	
	    private final static transient String c_categories[] ={
			SNArAlerts.class.getName(),
			ShiffBaseAlerts.class.getName(),			
			MichaelAcceptors.class.getName(),
			AcylTransferAlerts.class.getName(),
			SN2Alerts.class.getName(),
			NoAlerts.class.getName()
		};
	    
		public ProteinBindingPlugin() throws DecisionMethodException {
	    	super(new CategoriesList(c_categories,true),c_rules,c_transitions,new DecisionNodesFactory(true));
			
			setChanged();
			notifyObservers();
			/*
			if (changes != null ) {
				changes.firePropertyChange("Rules", rules,null);
				changes.firePropertyChange("Transitions", transitions,null);
			}
			*/
			setTitle("Protein binding Alerts");

	        setPriority(9);
	        setFalseIfRuleNotImplemented(false);
	        
		}
	    public void addPropertyChangeListener(PropertyChangeListener l) {
	        if (changes == null) 		changes = new PropertyChangeSupport(this);        
	        changes.addPropertyChangeListener(l);
			for (int i=0; i < rules.size(); i++) 
				if (rules.getRule(i) != null)
					rules.getRule(i).addPropertyChangeListener(l);
	    }
	    /* (non-Javadoc)
	     * @see toxTree.core.IDecisionMethod#removePropertyChangeListener(java.beans.PropertyChangeListener)
	     */
	    public void removePropertyChangeListener(PropertyChangeListener l) {
	        if (changes == null) {
		        changes.removePropertyChangeListener(l);
		        for (int i=0; i < rules.size(); i++) 
		        	if (rules.getRule(i) != null)
		        		rules.getRule(i).removePropertyChangeListener(l);
	        }	
	    }	
	    /* (non-Javadoc)
	     * @see java.lang.Object#toString()
	     */
	    public String toString() {
	        return getName();
	    }
		


		/* (non-Javadoc)
	     * @see toxTree.core.IDecisionMethod#getName()
	     */
	    public String getName() {
	        return name;
	    }
	    /* (non-Javadoc)
	     * @see toxTree.core.IDecisionMethod#setName(java.lang.String)
	     */
	    public void setName(String value) {
	       name = value;

	    }
		public StringBuffer explainRules(IDecisionResult result,boolean verbose) throws DecisionMethodException{
			try {
				StringBuffer b = result.explain(verbose);
				return b;
			} catch (DecisionResultException x) {
				throw new DecisionMethodException(x);
			}
		}
		/* (non-Javadoc)
		 * @see toxTree.tree.AbstractTree#createDecisionResult()
		 */
		/*public IDecisionResult createDecisionResult() {
			IDecisionResult result =  new CramerTreeResult();
			result.setDecisionMethod(this);
			return result;

		}*/


		public boolean isResiduesIDVisible() {
			return residuesIDVisible;
		}


		public void setResiduesIDVisible(boolean residuesIDVisible) {
			this.residuesIDVisible = residuesIDVisible;
			for (int i=0;i< rules.size(); i++) {
				rules.getRule(i).hideResiduesID(!residuesIDVisible);
			}
		}
		public void setEditable(boolean value) {
			editable = value;
			for (int i=0;i<rules.size();i++)
				rules.getRule(i).setEditable(value);
		}
	    @Override
	    public void setParameters(IAtomContainer mol) {
	        if (getInteractive()) {
	            JComponent c = optionsPanel(mol);
	            if (c != null)
	                JOptionPane.showMessageDialog(null,c,"Enter properties",JOptionPane.PLAIN_MESSAGE);
	        }    
	        
	    }

		public DescriptorSpecification getSpecification() {
	        return new DescriptorSpecification(
	                "http://toxtree.sourceforge.net/proteinbinding.html",
	                getTitle(),
	                this.getClass().getName(),                
	                "Toxtree plugin");
		}
		
		@Override
		public IDecisionResult createDecisionResult() {
			IDecisionResult result =  new ProteinBindingTreeResult();
			result.setDecisionMethod(this);
			return result;
		}
			
	
}
