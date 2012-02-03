package toxtree.plugins.smartcyp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.qsar.DescriptorSpecification;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionInteractive;
import toxTree.core.IDecisionResult;
import toxTree.core.IDecisionRule;
import toxTree.core.IMetaboliteGenerator;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNode;
import toxTree.tree.DecisionNodesFactory;
import toxTree.tree.RuleResult;
import toxTree.tree.UserDefinedTree;
import toxTree.ui.tree.categories.CategoriesRenderer;
import toxtree.plugins.smartcyp.categories.SitesHigherRank;
import toxtree.plugins.smartcyp.categories.SitesRank1;
import toxtree.plugins.smartcyp.categories.SitesRank2;
import toxtree.plugins.smartcyp.categories.SitesRank3;
import toxtree.plugins.smartcyp.rules.MetaboliteGenerator;
import toxtree.plugins.smartcyp.rules.SMARTCYPRuleRank1;
import ambit2.base.exceptions.AmbitException;
import ambit2.core.data.ArrayResult;


public class SMARTCYPPlugin extends UserDefinedTree  implements IDecisionInteractive, IMetaboliteGenerator {
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
				

        setPriority(13);
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
	
	@Override
	public BufferedImage getLegend(int width, int height) throws AmbitException {
		BufferedImage buffer = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffer.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, width,height);
		
		IDecisionCategories c = getCategories();
		if ((c==null) || (c.size()==0)) return buffer;
		int h = height/ c.size();
		g.setFont(new Font("TrebuchetMS",Font.BOLD,h/4==0?12:h/4));
		CategoriesRenderer r = new CategoriesRenderer(c);
		
		for (int i =0; i < c.size();i++) {
			IDecisionCategory cat = c.get(i);
			g.setBackground(r.getShowColor(i));
			g.setColor(r.getShowColor(i));
			g.fillOval(3, 3+h*i, h-6, h-6);
			g.setColor(Color.black);
			
			g.drawString(cat.getName(), 
					h+10,
					3+h/2+h*i);
		}
		return buffer;
	}    
	@Override
	public IAtomContainerSet getProducts(IAtomContainer reactant,
			RuleResult ruleResult) throws Exception {

		if (ruleResult==null)
	        for (int i=0; i < rules.size(); i++) 
	        	if ((rules.getRule(i) != null) && (rules.getRule(i) instanceof SMARTCYPRuleRank1))
	        		return ((SMARTCYPRuleRank1)rules.getRule(i)).getProducts(reactant,ruleResult);
	        	else {}
		else {
			IDecisionRule rule = ruleResult.getRule();
			if (ruleResult.getRule() instanceof DecisionNode) rule = ((DecisionNode)rule).getRule();
			return ((MetaboliteGenerator)rule).getProducts(reactant,ruleResult);
		}
	        		
        return null;
	}
	@Override
	public String getHelp(RuleResult ruleResult) {
		if (ruleResult!=null) {
			IDecisionRule rule = ruleResult.getRule();
			if (ruleResult.getRule() instanceof DecisionNode) rule = ((DecisionNode)rule).getRule();
			return ((IMetaboliteGenerator)rule).getHelp(ruleResult);
		} else
			for (int i=0; i < rules.size(); i++) 
				if ((rules.getRule(i) != null) && (rules.getRule(i) instanceof SMARTCYPRuleRank1))
					return ((IMetaboliteGenerator)rules.getRule(i)).getHelp(ruleResult);
        		
        return "";
	}
}
