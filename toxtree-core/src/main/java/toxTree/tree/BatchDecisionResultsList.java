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

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Observable;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionMethodEditor;
import toxTree.core.IDecisionResult;
import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleList;
import toxTree.core.IProcessRule;
import toxTree.data.CategoryFilter;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import ambit2.base.exceptions.AmbitException;

/**
 * Encapsulates {@link DecisionResultsList} as {@link IDecisionMethod}.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class BatchDecisionResultsList extends DecisionResultsList implements IDecisionMethod, IDecisionResult {
    protected PropertyChangeSupport pssupport;
	//protected boolean running = false;
    protected ProgressStatus status;
    boolean notify = true;
    /**
     * 
     */
    private static final long serialVersionUID = -4169481833049200104L;

    public BatchDecisionResultsList() {
        super();
        pssupport = new PropertyChangeSupport(this);
        status = new ProgressStatus();
    }
    public void addDecisionRule(IDecisionRule rule) throws DecisionMethodException {
        throw new DecisionMethodException("Not allowed");
        
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        pssupport.addPropertyChangeListener(l);
        
    }
    protected void firePropertyChangeEvent(String propertyName, Object oldValue, Object newValue) {
        if (notify && (pssupport != null)) 
            pssupport.firePropertyChange(propertyName, oldValue, newValue);
    }    
    public void setParameters(IAtomContainer mol) {
    	for (int i=0;i < size();i++) {
            IDecisionResult treeResult =  getResult(i);
            treeResult.getDecisionMethod().setParameters(mol);
    	}    
        
    }
    public boolean classify(IAtomContainer mol, IDecisionResult result) throws DecisionMethodException {
        boolean ok = true;
        setEstimating();
        for (int i=0;i < size();i++) {
            IDecisionResult treeResult =  getResult(i);
//            IDecisionMethod rules = treeResult.getDecisionMethod();
            try {
                ok &= treeResult.classify(mol);
                treeResult.assignResult(mol);
                //updateMolecule(mol, treeResult);
            } catch (DecisionResultException x) {
                //logger.error(x);
                treeResult.clear();
            }
        }   
        setEstimated();
        
        setChanged();
        notifyObservers();
        
        return ok;
    }
    public void assignResult(IAtomContainer mol) throws DecisionResultException {
        for (int i=0;i < size();i++) 
            getResult(i).assignResult(mol);
        
    }
    public IDecisionResult createDecisionResult() {
        return this;
    }

    public StringBuffer explainRules(IDecisionResult result, boolean verbose) throws DecisionMethodException {
        // TODO Auto-generated method stub
        return null;
    }

    public IDecisionRule getBranch(IDecisionRule rule, boolean answer) {
        return null;
    }

    public IDecisionCategories getCategories() {
        return null;
    }

    public IDecisionCategory getCategory(IDecisionRule rule, boolean answer) {
        return null;
    }
    public IDecisionCategories getAssignedCategories() {
    	// TODO Auto-generated method stub
    	return null;
    }
    public IDecisionMethodEditor getEditor() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getExplanation() {
        return null;
    }

    public int getNumberOfClasses() {

        return 0;
    }

    public int getNumberOfRules() {

        return 0;
    }

    public IDecisionRule getRule(int id) {
        return null;
    }

    public IDecisionRule getRule(String name) {
        return null;
    }

    public IDecisionRuleList getRules() {
        return null;
    }

    public String getTitle() {
        return toString();
    }

    public IDecisionRule getTopRule() {
        return null;
    }

    public IDecisionRuleList hasUnreachableRules() {
        IDecisionRuleList  list = null;
        for (int i=0; i <  size(); i++) {
            list = getMethod(i).hasUnreachableRules();
            if ((list != null) && (list.size()>0)) return list; 
            
        }    
        return list;
    }

    public boolean isEditable() {
        return false;
    }

    public boolean isModified() {
        // TODO Auto-generated method stub
        return false;
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        pssupport.removePropertyChangeListener(l);
        
    }
    public PropertyChangeListener[] getPropertyChangeListeners() {
        return pssupport.getPropertyChangeListeners();
    }

    public void setDecisionRule(IDecisionRule rule) throws DecisionMethodException {
        
    }

    public void setEditable(boolean value) {
        // TODO Auto-generated method stub
        
    }

    public void setExplanation(String value) {
        // TODO Auto-generated method stub
        
    }

    public void setModified(boolean value) {
        // TODO Auto-generated method stub
        
    }

    public void setTitle(String value) {
        // TODO Auto-generated method stub
        
    }

    public boolean verifyRules(IAtomContainer mol, IDecisionResult result) throws DecisionMethodException {
        throw new DecisionMethodException("Multiple decision trees");
    }
    public void addRuleResult(IDecisionRule rule, boolean value, IAtomContainer molecule) throws DecisionResultException {
        // TODO Auto-generated method stub
        
    }
    public boolean classify(IAtomContainer mol) throws DecisionResultException {
        try {
            return classify(mol,this);
        } catch (DecisionMethodException x) {
            throw new DecisionResultException(x);
        }
    }
    public void setMolecule(IAtomContainer molecule) throws DecisionResultException {
        // TODO Auto-generated method stub
        
    }
    public void setSilent(boolean silent) throws DecisionResultException {
        // TODO Auto-generated method stub
        
    }

    public StringBuffer explain(boolean verbose) throws DecisionResultException {
        // TODO Auto-generated method stub
        return null;
    }

    public IDecisionCategory getCategory() {
        // TODO Auto-generated method stub
        return null;
    }

    public IDecisionCategory getCategory(int index) throws DecisionResultException {
        // TODO Auto-generated method stub
        return null;
    }

    public IDecisionMethod getDecisionMethod() {
        return this;
    }

    public IAtomContainer getMolecule(int index) throws DecisionResultException {
        // TODO Auto-generated method stub
        return null;
    }

    public RuleResult getRuleResult(int index) throws DecisionResultException {
        // TODO Auto-generated method stub
        return null;
    }

    public int getRuleResultsCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setCategory(IDecisionCategory classID) {
        // TODO Auto-generated method stub
        
    }

    public void setDecisionMethod(IDecisionMethod method) {
        // TODO Auto-generated method stub
        
    }

    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        return 0;
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
    /*
    public boolean isError() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isEstimated() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isEstimating() {
        return running;
    }

    public void setError(String message) {
        // TODO Auto-generated method stub
        
    }

    public void setEstimated(boolean value) {
        // TODO Auto-generated method stub
        
    }

    public void setEstimated() {
    	running = false;
        
    }

    public void setEstimating() {
        running = true;
        
    }
    */
    public IDecisionCategories hasUnusedCategories() {
    	// TODO Auto-generated method stub
    	return null;
    }
    public String[] getResultPropertyNames() {
    	// TODO Auto-generated method stub
    	return null;
    }
    public int getPriority() {
        return Integer.MAX_VALUE;
    }	
    public void setPriority(int priority) {
        
    }
	public void update(Observable arg0, Object arg1) {
		if (isEstimating()) return;
		setChanged();
		notifyObservers();
		//do nothing
		
	}    
	public void walkRules(IDecisionRule rule, IProcessRule processor)throws DecisionMethodException {
        for (int i=0; i <  size(); i++) {
            getMethod(i).walkRules(rule,processor);
        }    
	}
	public List<CategoryFilter> getFilters() {
		return null;
	}
    public synchronized boolean isNotify() {
        return notify;
    }
    public synchronized void setNotify(boolean notify) {
        this.notify = notify;
    }
    @Override
    public String toString() {
    	
    	return "A list of " + size() + " decision trees."; 
    }
    public void hilightAlert(IDecisionRule rule) throws DecisionResultException {
    	throw new DecisionResultException("Not implemented");
    	
    }
    public void hilightAlert(RuleResult ruleresult)
    		throws DecisionResultException {
    	throw new DecisionResultException("Not implemented");
    	
    }
    public BufferedImage getStructureDiagramWithHighlights(IAtomContainer mol,
    		String ruleID, int width, int height, boolean atomnumbers)
    		throws AmbitException {
    	throw new AmbitException("not implemented");
    }
}
