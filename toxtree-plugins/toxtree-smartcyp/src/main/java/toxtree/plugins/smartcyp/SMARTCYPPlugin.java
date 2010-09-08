package toxtree.plugins.smartcyp;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorSpecification;

import ambit2.core.data.ArrayResult;

import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionInteractive;
import toxTree.core.IDecisionResult;
import toxTree.core.IDecisionRule;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNodesFactory;
import toxTree.tree.UserDefinedTree;
import toxtree.plugins.smartcyp.categories.SitesHigherRank;
import toxtree.plugins.smartcyp.categories.SitesRank1;
import toxtree.plugins.smartcyp.categories.SitesRank2;
import toxtree.plugins.smartcyp.categories.SitesRank3;
import toxtree.plugins.smartcyp.rules.SMARTCYPRuleRank1;


public class SMARTCYPPlugin extends UserDefinedTree  implements IDecisionInteractive{
	private static final long serialVersionUID = 0;
    protected boolean residuesIDVisible;
    public final static transient String[] c_rules = { 
    	 "toxtree.plugins.smartcyp.rules.SMARTCYPRuleRank1",
    	 "toxtree.plugins.smartcyp.rules.SMARTCYPRuleRank2",
    	 "toxtree.plugins.smartcyp.rules.SMARTCYPRuleRank3",
    	 "toxtree.plugins.smartcyp.rules.SMARTCYPRuleHigherRank",

        };
    private final static transient int c_transitions[][] ={
        //{if no go to, if yes go to, assign if no, assign if yes}
        {2,2,0,1},
        {3,3,0,2},
        {4,4,0,3},
        {0,0,0,4}
  
    };	
    private final static transient String c_categories[] ={
		"toxtree.plugins.smartcyp.categories.SitesRank1", //1
		"toxtree.plugins.smartcyp.categories.SitesRank2", //2
		"toxtree.plugins.smartcyp.categories.SitesRank3", //3
		"toxtree.plugins.smartcyp.categories.SitesHigherRank" //4
		//"toxtree.plugins.smartcyp.categories.NoSites" //5
	};
    public SMARTCYPPlugin() throws DecisionMethodException {
		
		
        
    	super(new CategoriesList(c_categories,true),c_rules,c_transitions,new DecisionNodesFactory(true));
		
		setChanged();
		notifyObservers();
		/*
		if (changes != null ) {
			changes.firePropertyChange("Rules", rules,null);
			changes.firePropertyChange("Transitions", transitions,null);
		}
		*/
		setTitle("Cytochrome P450-Mediated Drug Metabolism");
		setExplanation(
				"Patrik Rydberg, David E. Gloriam, Jed Zaretzki, Curt Breneman, Lars Olsen, SMARTCyp: A 2D Method for Prediction of Cytochrome P450-Mediated Drug Metabolism, ACS Med. Chem. Lett., 2010,1 (3), pp 96 100");
				

        setPriority(30);
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
                "http://toxtree.sourceforge.net/smartcyp.html",
                getTitle(),
                this.getClass().getName(),                
                "Toxtree plugin");
	}
	
	@Override
	public IDecisionResult createDecisionResult() {
		IDecisionResult result =  new SMARTCypTreeResult();
		result.setDecisionMethod(this);
		return result;
	}	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SMARTCYPPlugin) {
			SMARTCYPPlugin p = (SMARTCYPPlugin) obj;
			int nrules=0;
			Iterator<IDecisionRule> rules = p.getRules().iterator();
			while (rules.hasNext()) {
				IDecisionRule rule = rules.next();
				if (!(rule instanceof SMARTCYPRuleRank1)) return false;
				nrules++;
			}
			int ncategories = 0;
			Iterator<IDecisionCategory> categories = p.getCategories().iterator();
			while (categories.hasNext()) {
				IDecisionCategory category = categories.next();
				if ((category instanceof SitesRank1) || 
					(category instanceof SitesRank2) ||
					(category instanceof SitesRank3) || 
					(category instanceof SitesHigherRank)) {
					ncategories++;
				}
			}
			return (nrules==1) && (ncategories==4) &&
			p.getTitle().equals(getTitle()) &&
			p.getExplanation().equals(getExplanation());
			
		} else return false;
	}
	@Override
	protected ArrayResult createArrayResult(int length) {
		return new ArrayResult(new Object[length]);
	}
	@Override
	protected void setArrayValue(ArrayResult result, int index, IAtomContainer mol,String  propertyName) {
		result.set(index,mol.getProperty(propertyName));
	}
}
