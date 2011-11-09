package toxtree.plugins.dnabinding;

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
import toxtree.plugins.dnabinding.categories.DNABindingAlerts;
import toxtree.plugins.dnabinding.rules.DNABindingTreeResult;

/**
 * Protein binding
 * @author nina
 *
 */
public class DNABindingPlugin extends UserDefinedTree  implements IDecisionInteractive{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8574075125273844906L; 

	   protected boolean residuesIDVisible;
	    public final static transient String[] c_rules = {
	    	"toxtree.plugins.dnabinding.rules.RuleDNABindingAlerts",
	    	"toxtree.plugins.dnabinding.rules.SN1Rule",
	    	"toxtree.plugins.dnabinding.rules.ShiffBaseRule",
	    	"toxtree.plugins.dnabinding.rules.MichaelAcceptorRule",
	    	"toxtree.plugins.dnabinding.rules.AcylTransferRule",
	    	"toxtree.plugins.dnabinding.rules.SN2Rule",
	    	"toxtree.plugins.dnabinding.rules.VerifyAlertsDNABinding"

	        };
	    private final static transient int c_transitions[][] ={
	        //{if no go to, if yes go to, assign if no, assign if yes}
	        {DNABindingAlerts.SN1.ordinal()+2,DNABindingAlerts.SN1.ordinal()+2,
        		0,0},
	        {DNABindingAlerts.SHIFF_BASE.ordinal()+2,DNABindingAlerts.SHIFF_BASE.ordinal()+2,
	        		0,DNABindingAlerts.SN1.ordinal()+1},
	        		
   	        {DNABindingAlerts.MICHAEL_ACCEPTORS.ordinal()+2,DNABindingAlerts.MICHAEL_ACCEPTORS.ordinal()+2,
	        		0,DNABindingAlerts.SHIFF_BASE.ordinal()+1},
	        		
   	        {DNABindingAlerts.ACYL_TRANSFER.ordinal()+2,DNABindingAlerts.ACYL_TRANSFER.ordinal()+2,
	        		0,DNABindingAlerts.MICHAEL_ACCEPTORS.ordinal()+1},
	        		
   	        {DNABindingAlerts.SN2.ordinal()+2,DNABindingAlerts.SN2.ordinal()+2,
	        		0,DNABindingAlerts.ACYL_TRANSFER.ordinal()+1},

	        {7,0,0,DNABindingAlerts.SN2.ordinal()+1} ,     		
    	    {0,0,DNABindingAlerts.NO_ALERTS.ordinal()+1,0}
	  
	    };	
	    private final static transient String c_categories[] ={
			"toxtree.plugins.dnabinding.categories.SNArAlerts", //1
			"toxtree.plugins.dnabinding.categories.ShiffBaseAlerts", //2			
			"toxtree.plugins.dnabinding.categories.MichaelAcceptors", //3
			"toxtree.plugins.dnabinding.categories.AcylTransferAlerts", //4
			"toxtree.plugins.dnabinding.categories.SN2Alerts", //4
			"toxtree.plugins.dnabinding.categories.NoAlerts" //5
		};
	    
		public DNABindingPlugin() throws DecisionMethodException {
	    	super(new CategoriesList(c_categories,true),c_rules,c_transitions,new DecisionNodesFactory(true));
			
			setChanged();
			notifyObservers();
			/*
			if (changes != null ) {
				changes.firePropertyChange("Rules", rules,null);
				changes.firePropertyChange("Transitions", transitions,null);
			}
			*/
			setTitle("DNA binding Alerts");

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
	                "http://toxtree.sourceforge.net/dnabinding.html",
	                getTitle(),
	                this.getClass().getName(),                
	                "Toxtree plugin");
		}
		
		@Override
		public IDecisionResult createDecisionResult() {
			IDecisionResult result =  new DNABindingTreeResult();
			result.setDecisionMethod(this);
			return result;
		}
			
	
}
