/*
Copyright (C) 2005-2006  

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
package toxtree.ui.tree.rules;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import ambit2.core.data.MoleculeTools;

import toxTree.core.IDecisionRule;
import toxTree.exceptions.DecisionMethodException;

/**
 * An action to edit the example molecule of a rule
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-12
 */
public class RuleExampleEditAction extends RuleMoleculeEditAction {
	protected boolean answer = false;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 5783467183012345391L;

	/**
	 * 
	 * @param rule
	 * @param answer
	 */
	public RuleExampleEditAction(IDecisionRule rule,boolean answer) {
		super(null);
		setRule(rule);
		setAnswer(answer);
	}
	/**
	 * 
	 * @param rule
	 * @param answer
	 * @param arg0
	 */
	public RuleExampleEditAction(IDecisionRule rule,boolean answer, String arg0) {
		super(null, arg0);
		setRule(rule);
		setAnswer(answer);		
	}
	/**
	 * 
	 * @param rule
	 * @param answer
	 * @param arg0
	 * @param arg1
	 */
	public RuleExampleEditAction(IDecisionRule rule,boolean answer, String arg0, Icon arg1) {
		super(null, arg0, arg1);
		setRule(rule);
		setAnswer(answer);		
	}

	/**
	 * @return Returns the answer.
	 */
	public synchronized boolean isAnswer() {
		return answer;
	}
	/**
	 * @param answer The answer to set.
	 */
	public synchronized void setAnswer(boolean answer) {
		this.answer = answer;
		try {
			setMolecule(rule.getExampleMolecule(answer));
		} catch (Exception e) {
			if (molecule != null)
				molecule.removeAllElements();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (rule != null) {
			IAtomContainer m = null;
			try {
				m = rule.getExampleMolecule(answer);
			} catch (DecisionMethodException x) {
				m = null;
			}
			if (m==null) {
				m = MoleculeTools.newAtomContainer(SilentChemObjectBuilder.getInstance());
			}
			setMolecule(m);
			super.actionPerformed(arg0);
	
		}
	}
	@Override
	protected void updateMolecule(IAtomContainer mol) {
	    super.updateMolecule(mol);
        rule.setExampleMolecule(mol,answer);
        setMolecule(null);
	}
}
