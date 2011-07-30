package toxtree.plugins.skinsensitisation;

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
import toxtree.plugins.skinsensitisation.categories.SkinSensitisationAlerts;
import toxtree.plugins.skinsensitisation.rules.SkinSensitisationTreeResult;

/**
 * Skin sensitisation
 * @author nina
 *
 */
public class SkinSensitisationPlugin extends UserDefinedTree  implements IDecisionInteractive{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8574075125273844906L; 

	   protected boolean residuesIDVisible;
	    public final static transient String[] c_rules = {
	    	"toxtree.plugins.skinsensitisation.rules.RuleSkinSensitisationAlerts",
	    	"toxtree.plugins.skinsensitisation.rules.SNARRule",
	    	"toxtree.plugins.skinsensitisation.rules.ShiffBaseRule",
	    	 "toxtree.plugins.skinsensitisation.rules.MichaelAcceptorRule",
	    	 "toxtree.plugins.skinsensitisation.rules.AcylTransferRule",
	    	 "toxtree.plugins.skinsensitisation.rules.SN2Rule",
	    	 "toxtree.plugins.skinsensitisation.rules.VerifyAlertsSkinSensitisation"

	        };
	    private final static transient int c_transitions[][] ={
	        //{if no go to, if yes go to, assign if no, assign if yes}
	        {SkinSensitisationAlerts.SNAR.ordinal()+2,SkinSensitisationAlerts.SNAR.ordinal()+2,
        		0,0},
	        {SkinSensitisationAlerts.SHIFF_BASE.ordinal()+2,SkinSensitisationAlerts.SHIFF_BASE.ordinal()+2,
	        		0,SkinSensitisationAlerts.SNAR.ordinal()+1},
	        		
   	        {SkinSensitisationAlerts.MICHAEL_ACCEPTORS.ordinal()+2,SkinSensitisationAlerts.MICHAEL_ACCEPTORS.ordinal()+2,
	        		0,SkinSensitisationAlerts.SHIFF_BASE.ordinal()+1},
	        		
   	        {SkinSensitisationAlerts.ACYL_TRANSFER.ordinal()+2,SkinSensitisationAlerts.ACYL_TRANSFER.ordinal()+2,
	        		0,SkinSensitisationAlerts.MICHAEL_ACCEPTORS.ordinal()+1},
	        		
   	        {SkinSensitisationAlerts.SN2.ordinal()+2,SkinSensitisationAlerts.SN2.ordinal()+2,
	        		0,SkinSensitisationAlerts.ACYL_TRANSFER.ordinal()+1},

	        {7,0,0,SkinSensitisationAlerts.SN2.ordinal()+1} ,     		
    	    {0,0,SkinSensitisationAlerts.NO_ALERTS.ordinal()+1,0}
	  
	    };	
	    private final static transient String c_categories[] ={
			"toxtree.plugins.skinsensitisation.categories.SNArAlerts", //1
			"toxtree.plugins.skinsensitisation.categories.ShiffBaseAlerts", //2			
			"toxtree.plugins.skinsensitisation.categories.MichaelAcceptors", //3
			"toxtree.plugins.skinsensitisation.categories.AcylTransferAlerts", //4
			"toxtree.plugins.skinsensitisation.categories.SN2Alerts", //4
			"toxtree.plugins.skinsensitisation.categories.NoAlerts" //5
		};
	    
		public SkinSensitisationPlugin() throws DecisionMethodException {
	    	super(new CategoriesList(c_categories,true),c_rules,c_transitions,new DecisionNodesFactory(true));
			
			setChanged();
			notifyObservers();
			/*
			if (changes != null ) {
				changes.firePropertyChange("Rules", rules,null);
				changes.firePropertyChange("Transitions", transitions,null);
			}
			*/
			setTitle("Skin Sensitisation Alerts");
			setExplanation(
					"Enoch SJ, Madden JC, Cronin MT,Identification of mechanisms of toxic action for skin sensitisation using a SMARTS pattern based approach.,SAR QSAR Environ Res. 2008;19(5-6):555-78.");
					

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
	                "http://toxtree.sourceforge.net/skinsensitisation.html",
	                getTitle(),
	                this.getClass().getName(),                
	                "Toxtree plugin");
		}
		
		@Override
		public IDecisionResult createDecisionResult() {
			IDecisionResult result =  new SkinSensitisationTreeResult();
			result.setDecisionMethod(this);
			return result;
		}
			
	
}
