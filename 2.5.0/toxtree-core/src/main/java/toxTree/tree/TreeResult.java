/*
Copyright Ideaconsult Ltd. (C) 2005-2007 

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/
package toxTree.tree;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionResult;
import toxTree.core.IDecisionRule;
import toxTree.data.CategoryFilter;
import toxTree.exceptions.DMethodNotAssigned;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.logging.TTLogger;
/**
 * A default class implementing {@link toxTree.core.IDecisionResult}
 * @author Nina Jeliazkova nina@acad.bg<br>
 * @version 0.1, 2005-4-30
 */
public class TreeResult implements IDecisionResult {
    protected static transient TTLogger logger = null;
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 87786882555929168L;
    protected transient PropertyChangeSupport changes = null;    
	protected IDecisionMethod decisionMethod = null;
	protected IDecisionCategory category = null;
	protected IDecisionCategories assignedCategories = null;
	protected transient IAtomContainer originalMolecule;
	protected ArrayList<RuleResult> ruleResults = null;
	
	protected ProgressStatus status;
	
	protected boolean notify = true;
	protected boolean web = false;
	
	public synchronized boolean isNotify() {
        return notify;
    }
    public synchronized void setNotify(boolean notify) {
        this.notify = notify;
    }
    /**
	 * 
	 * Constructor
	 * @param method
	 */
	public TreeResult() {
		super();
		status = new ProgressStatus();
		ruleResults = new ArrayList<RuleResult>();
		assignedCategories = new CategoriesList();
		if (logger ==null) logger = new TTLogger(TreeResult.class);
	}
	public void clear() {

		clearResults();
	}
	
	protected void clearResults() {
	    firePropertyChangeEvent(ProgressStatus._pClassID,category,null);
	    category = null;	
	    assignedCategories.clear();
	    if (ruleResults != null) {
	        ruleResults.clear();
	    }
	    status.clear();
        firePropertyChangeEvent(ProgressStatus._pRuleResult, null, status);
        firePropertyChangeEvent(ProgressStatus._pRuleResult, null, "");      
        //firePropertyChangeEvent(Panel2D.property_name.panel2d_selected.toString(), null,null);
        firePropertyChangeEvent("panel2d_selected", null,null);
        /*
		setChanged();
		notifyObservers();
        */	    
	}
	public boolean isError() {
		return status.isError();
	}
	public void setError(String message) {
		status.setError(message);
        firePropertyChangeEvent(ProgressStatus._pRuleResult, null, message);
        /*
		setChanged();
    	notifyObservers();
        */
	}
	/* (non-Javadoc)
     * @see toxTree.core.IDecisionResult#isEstimated()
     */
    public boolean isEstimated() {
        return status.isEstimated();
    }
    /* (non-Javadoc)
     * @see toxTree.core.IDecisionResult#isEstimating()
     */
    public boolean isEstimating() {
        return status.isEstimating();	
    }
    /* (non-Javadoc)
     * @see toxTree.core.IDecisionResult#setEstimating()
     */
    public void setEstimating() {
        status.setEstimating();
        firePropertyChangeEvent(ProgressStatus._pRuleResult, null, status);
        /*
		setChanged();
		notifyObservers();
        */       
    }
    public void setPercentEstimated(int percent) {
        status.setPercentEstimated(percent);
        firePropertyChangeEvent(ProgressStatus._pRuleResult, null, status);
    }
    public void setEstimated() {
    	status.setEstimated();
        firePropertyChangeEvent(ProgressStatus._pRuleResult, null, status);
        /*
        setChanged();
        notifyObservers();
        */       
    	
    }
    /* (non-Javadoc)
     * @see toxTree.core.IDecisionResult#setEstimated(boolean)
     */
    public void setEstimated(boolean value) {
    	status.setEstimated(value);
        firePropertyChangeEvent(ProgressStatus._pRuleResult, null, status);
        /*
        setChanged();
        notifyObservers();
        */       ;
    }
	public IDecisionCategory getCategory() {
		return category;
	}
	public IDecisionCategories getAssignedCategories() {
		return assignedCategories;
	}
	public void setCategory(IDecisionCategory category) {
	    firePropertyChangeEvent(ProgressStatus._pClassID,this.category,category);
	    
	    if (acceptCategory(category)) {
	    	logger.info("Assign category\t",category);
	    	this.category = category;
		    if (!decisionMethod.getCategories().isMultilabel()) {
		    	assignedCategories.clear();
		    }	    	
	    	addCategory(category);
	    }
		((RuleResult) ruleResults.get(ruleResults.size()-1)).setCategory(category);
        firePropertyChangeEvent(ProgressStatus._pRuleResult, null, this);
        /*
        setChanged();
        notifyObservers();
        */       
	}
	protected boolean acceptCategory(IDecisionCategory category) {
		return ((this.category == null) || (this.category.compareTo(category) < 0));
	}
	protected void addCategory(IDecisionCategory category) {
		assignedCategories.add(category);
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionResult#setMolecule(org.openscience.cdk.AtomContainer)
	 */
	public void setMolecule(IAtomContainer molecule) {
		((RuleResult) ruleResults.get(ruleResults.size()-1)).setMolecule(molecule);
        firePropertyChangeEvent(ProgressStatus._pRuleResult, null, this);
        /*
        setChanged();
        notifyObservers();
        */       	
	}
	public void setSilent(boolean silent) throws DecisionResultException {
		((RuleResult) ruleResults.get(ruleResults.size()-1)).setSilent(silent);
        firePropertyChangeEvent(ProgressStatus._pRuleResult, null, this);
        /*
        setChanged();
        notifyObservers();
        */       	
	}
	/*
	public void addRuleResult(int ruleIndex, boolean value)
			throws DecisionResultException {
		if (decisionMethod == null) 
		    throw new DMethodNotAssigned(_eMethodNotAssigned);
		IDecisionRule rule = decisionMethod.getRule(ruleIndex);
		if (rule == null)
			 throw new DecisionResultException("Rule #" + ruleIndex + " not assigned!");
		results.add(new RuleResult(rule,value));
	}
	*/
	public void addRuleResult(IDecisionRule rule, boolean value,IAtomContainer molecule) throws DecisionResultException {
		if (decisionMethod == null)   throw new DMethodNotAssigned(ProgressStatus._eMethodNotAssigned);
		if (rule == null) throw new DecisionResultException("Rule # not assigned!");
		RuleResult ruleResult = new RuleResult(rule,value);
		ruleResult.setWeb(web);
		ruleResults.add(ruleResult);
		setMolecule(molecule);
	}
	/**
	 * return StringBuffer with textual explanation of the rules executed
	 */
	public StringBuffer explain(boolean verbose) throws DecisionResultException {
		try {
		    StringBuffer b;
		    switch (status.estimated) {
		    case COMPLETED: {
		    	b = new StringBuffer();
		    	IAtomContainer ac = null;
		    	boolean first = true;
		    	String delimiter = "";
		    	//b.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		    	//b.append("<html><head><title>Toxtree</title></head>");
		    	//b.append("<html><head><title>Toxtree</title>");
		    //	b.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head>");
		    	
		    	//b.append("<body>");
		    	for (int i=0;i < ruleResults.size();i++) {
		    		if ((!verbose) && (i>0)) delimiter = ",";
		    		else delimiter = "";
		    		RuleResult r = ((RuleResult)ruleResults.get(i));
		    		if (r.isSilent()) continue;
		    		IAtomContainer mol = r.getMolecule();
		    		if (ac == null) ac = mol;
		    		if ((ac != null) && (ac.getID() != mol.getID())) {
		    			if (!first)  if (!verbose) b.append(")"); else ;
		    			else first = false;
		    			if (verbose) b.append("<br>"); else  b.append("(");
		    			ac = mol;
		    		} else b.append(delimiter);
		    		
		    		b.append(r.explain(verbose,i));
		    		if (verbose) b.append("<br>");
		    	}
	    		if (!first) if (!verbose) b.append(")");		
	    		//b.append("</body>");
	    		//b.append("</html>");
				return b;
		    }
		    
		    default: {
		        b = new StringBuffer();
		        b.append(status.toString());
		        return b;		        
		    }
		    }
		//} catch (DecisionMethodException x) {
			//throw new DecisionResultException(x);
		} catch (NullPointerException x) {
			throw new DMethodNotAssigned(ProgressStatus._eMethodNotAssigned);
		}
	}	
	/**
	 * toString
	 */
	@Override
	public String toString() {
	    if (decisionMethod == null) return ProgressStatus._eMethodNotAssigned;
	    switch (status.estimated) {
	    case COMPLETED: return decisionMethod.toString() + "\t Class\t" + category.toString();
	    default: return status.toString();
	    }
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		TreeResult r = (TreeResult) o;
		return category.compareTo(r.getCategory());
	}
	
	public IDecisionMethod getDecisionMethod() {
		return decisionMethod;
	}
	public void setDecisionMethod(IDecisionMethod decisionMethod) {
		if (decisionMethod == null) {
		    if (this.decisionMethod != null) clear();
		    firePropertyChangeEvent(ProgressStatus._pDecisionMethod,this.decisionMethod,decisionMethod);		    
		    this.decisionMethod = null;
	    
		} else {
			setWeb(decisionMethod.isWeb());
		    if ((this.decisionMethod != null) &&
		        (this.decisionMethod.equals(decisionMethod))) 
		        return;
		    
		    firePropertyChangeEvent(ProgressStatus._pDecisionMethod,this.decisionMethod,decisionMethod);	    
			this.decisionMethod = decisionMethod;
			ruleResults.clear();

		}
        firePropertyChangeEvent(ProgressStatus._pRuleResult, null, this);
        /*
        setChanged();
        notifyObservers();
        */       
	}

	public boolean classify(IAtomContainer mol) throws DecisionResultException {
		try {
            firePropertyChangeEvent(ProgressStatus._pRuleResult, null, decisionMethod.getTitle());
            setOriginalMolecule(mol);
		    boolean r  = decisionMethod.classify(mol,this);
		    setEstimated(true);
            firePropertyChangeEvent(ProgressStatus._pRuleResult, null, "Completed.");
			//Toolkit.getDefaultToolkit().beep();
			return r;
		} catch (DecisionMethodException x) {
			//x.printStackTrace();
            setOriginalMolecule(null);
			setEstimated(false);
            firePropertyChangeEvent(ProgressStatus._pRuleResult, null, x.getMessage());
			throw new DecisionResultException(x);
		} catch (NullPointerException x) {
            setOriginalMolecule(null);
			x.printStackTrace();
			setEstimated(false);
            firePropertyChangeEvent(ProgressStatus._pRuleResult, null, x.getMessage());            
			if (decisionMethod == null)
				throw new DMethodNotAssigned(x);
			else throw new DecisionResultException(x);
		} catch (Throwable x) {
			setEstimated(false);
            firePropertyChangeEvent(ProgressStatus._pRuleResult, null, x.getMessage());            
			throw new DecisionResultException(x);			
		}
	}
	public void addPropertyChangeListener(PropertyChangeListener l) {
	    if (changes == null) changes = new PropertyChangeSupport(this);
        changes.addPropertyChangeListener(l);
	}
	public void removePropertyChangeListener(PropertyChangeListener l) {
	    if (changes != null)
	        changes.removePropertyChangeListener(l);	    
	}
    public PropertyChangeListener[] getPropertyChangeListeners() {
        return changes.getPropertyChangeListeners();
    }
	protected void fireChangeEvent(PropertyChangeEvent e) {
        if (notify && (changes != null))
	        changes.firePropertyChange(e);
	}
	protected void firePropertyChangeEvent(String propertyName, int oldValue, int newValue) {
        if (notify && (changes != null))
	        changes.firePropertyChange(propertyName, oldValue, newValue);
	}
	protected void firePropertyChangeEvent(String propertyName, boolean oldValue, boolean newValue) {
        if (notify && (changes != null))
	        changes.firePropertyChange(propertyName, oldValue, newValue);
	}
	
	protected void firePropertyChangeEvent(String propertyName, Object oldValue, Object newValue) {
	    if (notify && (changes != null)) 
	        changes.firePropertyChange(propertyName, oldValue, newValue);
	}

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
	public boolean equals(Object obj) {
        IDecisionResult r = (IDecisionResult) obj;
        //if both methods are unassigned assume equal
        if ((decisionMethod == null) && (r.getDecisionMethod() == null)) return true;
        //if one is unassigned but the other is, return false
        if ((decisionMethod == null) || (r.getDecisionMethod() == null)) return false;
        
        if ((category.equals(r.getCategory())) &&  
        	(decisionMethod.equals(r.getDecisionMethod()))) {
            int nr = getRuleResultsCount();
            if (nr != r.getRuleResultsCount()) return false;
            try {
	            for (int i =0; i < nr; i++)
	                if (!getRuleResult(i).equals(r.getRuleResult(i))) {
	                	System.out.println(getRuleResult(i)+"\tdoesn't match\t"+r.getRuleResult(i));
	                	return false;
	                }
	            return true;    
            } catch (DecisionResultException x) {
            	x.printStackTrace();
                return false;
            }
            
        } else return false;
    }
    /* (non-Javadoc)
	 * @see toxTree.core.IDecisionResult#getRuleResultsCount()
	 */
	public int getRuleResultsCount() {
		return ruleResults.size();
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionResult#getMolecule(int)
	 */
	public IAtomContainer getMolecule(int index) throws DecisionResultException {
		try {
			return ((RuleResult) ruleResults.get(index)).getMolecule();
		} catch (Exception x) {
			throw new DecisionResultException(x);
		}
		
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionResult#getCategory(int)
	 */
	public IDecisionCategory getCategory(int index) throws DecisionResultException {
		try {
			return ((RuleResult) ruleResults.get(index)).getCategory();
		} catch (Exception x) {
			throw new DecisionResultException(x);
		}
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionResult#getRuleResult(int)
	 */
	public RuleResult getRuleResult(int index) throws DecisionResultException {
		try {
			return (RuleResult) ruleResults.get(index);
		} catch (Exception x) {
			throw new DecisionResultException(x);
		}
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IDecisionResult#getRule(int)
	 */
	public IDecisionRule getRule(int index) throws DecisionResultException {
		try {
			return ((RuleResult) ruleResults.get(index)).getRule();
		} catch (Exception x) {
			throw new DecisionResultException(x);
		}
	}
	public void assignResult(IAtomContainer mol) throws DecisionResultException {
		if (mol == null) return;
		if (getCategory() != null)
	        mol.setProperty(
	        		getResultPropertyNames()[0],
	                getCategory());
		else
			mol.removeProperty(
	        		getResultPropertyNames()[0]);
        String paths = getResultPropertyNames()[0]+"#explanation";
        if (getDecisionMethod().getRules().size() > 1)
	        mol.setProperty(
	        		paths,
	                explain(false).toString());
        else
        	mol.removeProperty(paths);
        /*
            mol.setProperty(
                    getDecisionMethod().getClass().getName(),
                    getCategory().toString());
            mol.setProperty(
                    getClass().getName(),
                    explain(false).toString());
	    
        */
        firePropertyChangeEvent(ProgressStatus._pRuleResult, null, status);        
	}
	public String[] getResultPropertyNames() {
		if (getDecisionMethod() == null) return new String[] {"Unassigned method"};
		else return new String[] {getDecisionMethod().getTitle()};
	}
	public ArrayList<RuleResult> getRuleResults() {
		return ruleResults;
	}
	public void setRuleResults(ArrayList<RuleResult> ruleResults) {
		this.ruleResults = ruleResults;
	}
    public int getPriority() {
        IDecisionMethod m = getDecisionMethod();
        if (m ==null) return Integer.MAX_VALUE;
        else return m.getPriority();
    }
    public void setPriority(int priority) {
        IDecisionMethod m = getDecisionMethod();
        if (m ==null) return;
        else m.setPriority(priority);
        
    }
    public List<CategoryFilter> getFilters() {
    	ArrayList<CategoryFilter> l = new ArrayList<CategoryFilter>();
    	IDecisionCategories c = getDecisionMethod().getCategories();
		for (int i=0; i < c.size();i++) 
		try { 
    		l.add(new CategoryFilter(getResultPropertyNames()[0] , c.get(i)));
    	} catch (Exception x) {
    		logger.error(x);
    	}
    	return l;
    }
    public synchronized IAtomContainer getOriginalMolecule() {
        return originalMolecule;
    }
    public synchronized void setOriginalMolecule(IAtomContainer originalMolecule) {
        this.originalMolecule = originalMolecule;
    }
    public synchronized void hilightAlert(RuleResult ruleResult) throws DecisionResultException {
					
		//firePropertyChangeEvent(Panel2D.property_name.panel2d_molecule.toString(),
    	firePropertyChangeEvent("panel2d_molecule",
							null,
							ruleResult==null?originalMolecule:
								ruleResult.getMolecule()==null?originalMolecule:ruleResult.getMolecule());
							//AtomContainerManipulator.removeHydrogensPreserveMultiplyBonded(originalMolecule));
		//firePropertyChangeEvent(Panel2D.property_name.panel2d_selected.toString(), 
		firePropertyChangeEvent("panel2d_selected",				
				null, ruleResult==null?null:ruleResult.getSelector());

		
	}
	public synchronized void hilightAlert(IDecisionRule rule) throws DecisionResultException {
		/*

					*/
		//for (int i=0; i < ruleResults.size();i++)
		//	if (ruleResults.get(i).getRule().equals(rule)) {
			//	boolean value = ruleResults.get(i).isResult();
				//if (value) {
					
					//firePropertyChangeEvent(Panel2D.property_name.panel2d_molecule.toString(),
					firePropertyChangeEvent("panel2d_molecule",
							null,
							originalMolecule);
					firePropertyChangeEvent("panel2d_selected", null, rule==null?null:rule.getSelector());

					//firePropertyChangeEvent(Panel2D.property_name.panel2d_selected.toString(), null, rule==null?null:rule.getSelector());
				//	return;
				//}
				
		//	}
		//firePropertyChangeEvent(Panel2D.property_name.panel2d_molecule.toString(), null,originalMolecule); 
		//AtomContainerManipulator.removeHydrogensPreserveMultiplyBonded(originalMolecule));
		//firePropertyChangeEvent(Panel2D.property_name.panel2d_selected.toString(), null, null);
		
	}
	public void setWeb(Boolean web) {
		this.web = web;
		
	}
}
