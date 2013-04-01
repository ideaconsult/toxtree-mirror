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
package toxTree.core;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.List;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.data.CategoryFilter;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.RuleResult;
import ambit2.base.data.Property;
import ambit2.base.exceptions.AmbitException;


/**
 * An interface definition to represent the result of applying a {@link toxTree.core.IDecisionMethod}.
 * In addition to the chemical category assigned {@link toxTree.core.IDecisionCategory},
 * it provides access to the decision tree path which had led to the assignment.   
 * @author Nina Jeliazkova nina@acad.bg<br>
 * @version 0.1, 2005-5-1
 */
public interface IDecisionResult extends Comparable, Serializable , IProgressStatus , IDecisionMethodPrority{
	String toString();
	StringBuffer explain(boolean verbose) throws DecisionResultException;
	boolean classify(IAtomContainer mol) throws DecisionResultException;
	
	IDecisionCategories getAssignedCategories();
	IDecisionCategory getCategory();
	IDecisionCategory getCategory(int index) throws DecisionResultException;
	void setCategory(IDecisionCategory classID);

	IAtomContainer getMolecule(int index)   throws DecisionResultException;
	void setMolecule(IAtomContainer molecule) throws DecisionResultException;
	
	void setSilent(boolean silent) throws DecisionResultException;
	
	IDecisionRule getRule(int index) throws DecisionResultException;
	RuleResult getRuleResult(int index) throws DecisionResultException;
	void addRuleResult(IDecisionRule rule, boolean value,IAtomContainer molecule) throws DecisionResultException ;
	//void addRuleResult(int ruleIndex, boolean value) throws DecisionResultException ;	
	int getRuleResultsCount() ;
	
	void setDecisionMethod(IDecisionMethod method);
	IDecisionMethod getDecisionMethod();
	
	void clear();

	
	void addPropertyChangeListener(PropertyChangeListener l);
	void removePropertyChangeListener(PropertyChangeListener l);
    PropertyChangeListener[] getPropertyChangeListeners();
	
	void assignResult(IAtomContainer mol) throws DecisionResultException;
	/**
	 * Returns the name of the property that will contain the result
	 */
	String[] getResultPropertyNames();
	/**
	 * Returns the properties to contain the result in {@link Property} class
	 * @return
	 */
	public List<Property> getResultProperties()  throws AmbitException;
	public List<CategoryFilter> getFilters();
    void setNotify(boolean value);
    void hilightAlert(IDecisionRule rule) throws DecisionResultException;
    void hilightAlert(RuleResult ruleresult) throws DecisionResultException;
    void setWeb(Boolean web);
}
