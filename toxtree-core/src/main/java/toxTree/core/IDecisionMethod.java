/*
Copyright Ideaconsult Ltd. (C) 2005-2007  
Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

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

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import ambit2.core.data.IStructureDiagramHighlights;

/*
 * Created on 2005-4-30

 * @author Nina Jeliazkova nina@acad.bg
 *
 * Project : toxtree
 * Package : 
 * Filename: iDecisionMethod.java
 */

/**
 * An interface to represent a decision tree.
 * In order to be used as a decision tree in the toxTree application,
 * a class should implement this interface.<br>
 * The decision method consists of rules, which are classes implementing {@link toxTree.core.IDecisionRule}.<br>
 * and transitions {@link toxTree.core.IDecisionTransition}.
 * The result of applying a decision tree to a compound is assigning {@link toxTree.core.IDecisionCategory}.<br>
 * The assigned category and the path followed is represented by {@link toxTree.core.IDecisionResult}. 
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-4-30
 */
public interface IDecisionMethod extends Serializable, Cloneable,IDecisionMethodPrority, IStructureDiagramHighlights {
    String getTitle();
    void setTitle(String value);
    String getExplanation();
    void setExplanation(String value);    
	IDecisionRule getRule(int id);
	IDecisionRule getRule(String name);
	
	IDecisionRule getTopRule();
	IDecisionRule getBranch(IDecisionRule rule, boolean answer);
	IDecisionCategory getCategory(IDecisionRule rule, boolean answer);
	
	IDecisionCategories getCategories();
	IDecisionRuleList getRules();
	
	void setDecisionRule(IDecisionRule rule) throws DecisionMethodException;
	void addDecisionRule(IDecisionRule rule) throws DecisionMethodException;
	
	StringBuffer explainRules(IDecisionResult result,boolean verbose) throws DecisionMethodException;
	boolean verifyRules(IAtomContainer  mol,IDecisionResult result) throws DecisionMethodException;
	boolean classify(IAtomContainer  mol,IDecisionResult result) throws DecisionMethodException;
	void walkRules(IDecisionRule rule,IProcessRule processor)  throws DecisionMethodException;
	IDecisionRuleList hasUnreachableRules();
	IDecisionCategories hasUnusedCategories();
	int getNumberOfRules();	
	int getNumberOfClasses();
	void addPropertyChangeListener(PropertyChangeListener l);
	void removePropertyChangeListener(PropertyChangeListener l);
	
	IDecisionResult createDecisionResult();
	boolean isEditable();
	void setEditable(boolean value);
	boolean isModified();
	void setModified(boolean value);
	
	/**
	 * Each rule provides an editor, which is a class implementing {@link IDecisionMethodEditor}.
	 * The editor shall provide user interface for visualization and modification of various method settings. 
	 * @return {@link IDecisionMethodEditor}
	 */
	IDecisionMethodEditor getEditor();	
    void setParameters(IAtomContainer mol) ;
    
    
}
