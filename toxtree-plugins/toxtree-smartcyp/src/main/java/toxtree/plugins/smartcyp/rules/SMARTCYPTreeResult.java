package toxtree.plugins.smartcyp.rules;

import ambit2.ui.editors.Panel2D;
import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionRule;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.TreeResult;

public class SMARTCYPTreeResult extends TreeResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8591199846802563823L;
/*
	public void assignResult(IAtomContainer mol) throws DecisionResultException {
		if (mol == null) return;
		IDecisionCategories c = decisionMethod.getCategories();
		for (int i=0; i < c.size();i++) {
			String result = Answers.toString(Answers.NO);
			if (getAssignedCategories().indexOf(c.get(i))>-1)
				result = Answers.toString(Answers.YES);
			mol.setProperty(
	        		c.get(i).toString(),
	                result);
		}

        String paths = getClass().getName()+"#explanation";
        if (getDecisionMethod().getRules().size() > 1) {
	        mol.setProperty(
	        		paths,
	                explain(false).toString());
	        Hashtable<String,String> b = getExplanation(mol);
	        Enumeration<String> k = b.keys();
	        while (k.hasMoreElements()) {
	        	String key = k.nextElement();
	        	mol.setProperty(key,b.get(key));
	        }
        } else
        	mol.removeProperty(paths);
        firePropertyChangeEvent(ProgressStatus._pRuleResult, null, status);        

	}
	*/

	@Override
	public String[] getResultPropertyNames() {
		IDecisionCategories c = decisionMethod.getCategories();
		String[] names = new String[c.size()];
		for (int i=0; i < c.size();i++) 
			names[i] = c.get(i).toString();
		return names;
	}
	/*
	public synchronized void hilightAlert(IDecisionRule rule) throws DecisionResultException {
		firePropertyChangeEvent(Panel2D.property_name.panel2d_molecule.toString(), null, originalMolecule);
		firePropertyChangeEvent(Panel2D.property_name.panel2d_selected.toString(), null, rule.getSelector());
	}	
	*/
}
