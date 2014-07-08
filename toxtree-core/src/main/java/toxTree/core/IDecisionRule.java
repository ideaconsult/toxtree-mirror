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
/**
 * Created on 2005-5-2

 * @author Nina Jeliazkova nina@acad.bg
 *
 * Project : toxtree
 * Package : toxTree.tree
 * Filename: IDecisionRule.java
 */
package toxTree.core;

import java.beans.PropertyChangeListener;
import java.io.Serializable;

import net.idea.modbcum.i.processors.IProcessor;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.renderer.selection.IChemObjectSelection;

import toxTree.exceptions.DecisionMethodException;

/**
 * An interface definition to represent a rule, that will be used in a {@link toxTree.core.IDecisionMethod}.
 * All classes, implementing a rule to be used in toxTree application should implement this interface.
 * @author Nina Jeliazkova nina@acad.bg<br>
 * @version 0.1, 2005-5-2
 */
public interface IDecisionRule extends Serializable, Cloneable {
	/**
	 * Rule identifier is an arbitrary string, preferably short one 
	 * @return the rule identifier
	 */
	String getID();
	/**
	 * Sets rule identifier
	 * @param id  
	 */
	void setID(String id);
	
	int getNum();
	void setNum(int no);
	
	
	/**
	 * Rule name is an arbitrary string, preferably one line, reflecting the essence of the rule <br>
	 * (e.g. "Is aromatic")
	 * @return rule name
	 */
	String getTitle();
	/**
	 * Sets rule name
	 * @param name
	 */
	void setTitle(String name);
	/**
	 * Rule explanation is an arbitrary text, could be multiline and may contain 
	 * html tags. 
	 * @return explanation
	 */
	String getExplanation();
	/**
	 * Sets rule explanation
	 * @param message
	 */
	void setExplanation(String message);
	
	/**
	 * if ruleResult is TRUE, returns the example molecule if the rule is answered YES
	 * if ruleResult is FALSE, returns the example molecule if the rule is answered NO
	 * @param ruleResult 
	 * @return {@link org.openscience.cdk.interfaces.Molecule}
	 * @throws {@link DecisionMethodException}
	 */
	IMolecule getExampleMolecule(boolean ruleResult) throws DecisionMethodException ;
	/**
	 * Sets example molecule for the YES or NO answer of the rule
	 * @param mol {@link org.openscience.cdk.interfaces.Molecule}
	 * @param ruleResult
	 */
	
	void setExampleMolecule(IAtomContainer  mol, boolean ruleResult) ;
	/**
	 * When rules analyze a molecule, a set of properties are set. This method provides a way to clear the properties.
	 * Could be moved in a different class in a later release
	 * @param  mol {@link org.openscience.cdk.interfaces.Molecule}
	 */
	void clearFlags(IAtomContainer mol);
	
	/**
	 * This is the core of a {@link IDecisionRule} behaviour. The method returns true
	 * if the answer of the rule is YES for the analyzed molecule {@link org.openscience.cdk.interfaces.AtomContainer} and FALSE
	 * if the answer of the rule is NO  for the analyzed molecule {@link org.openscience.cdk.interfaces.AtomContainer}??
	 * @param mol  {@link org.openscience.cdk.interfaces.AtomContainer}
	 * @return rule result, boolean
	 * @throws {@link DecisionMethodException}
	 */
	boolean verifyRule(IAtomContainer mol) throws DecisionMethodException;
	
	String toString();
	
	void addPropertyChangeListener(PropertyChangeListener l);
	void removePropertyChangeListener(PropertyChangeListener l);
	
	/**
	 * 
	 * @return false if the rule is not implemented and true if it is
	 */
	boolean isImplemented();
	
	void hideResiduesID(boolean hide);
	boolean isResidueIDHidden();
	
	/**
	 * Each rule provides an editor, which is a class implementing {@link IDecisionRuleEditor}.
	 * The editor shall provide user interface for visualization and modification of various rule settings. 
	 * @return {@link IDecisionRuleEditor}
	 */
	IDecisionRuleEditor getEditor();
	
	public Object clone()  throws CloneNotSupportedException ;
	
	boolean isEditable();
	void setEditable(boolean value);
	
	IProcessor<IAtomContainer, IChemObjectSelection> getSelector();

}
