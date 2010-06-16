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
package toxTree.tree.rules;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.jchempaint.renderer.selection.IChemObjectSelection;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolFlags;
import toxTree.tree.AbstractRule;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.base.interfaces.IProcessor;

/**
 * Verifies if the molecule consists of only allowed substituents
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-8-17
 */
public class RuleAnySubstituents extends AbstractRule {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -1913165069798234598L;

	/**
	 * 
	 */
	public RuleAnySubstituents() {
		super();

	}
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractRule#isImplemented()
	 */
	@Override
	public boolean isImplemented() {
		return true;
	}

	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		logger.info(getID());
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf == null) throw new DecisionMethodException("Structure should be preprocessed!");

		return FunctionalGroups.hasSubstituents(mol,mf.getRingset());
	}

    @Override
    public IProcessor<IAtomContainer, IChemObjectSelection> getSelector() {
    	RuleSMARTSSubstructureAmbit rule = new RuleSMARTSSubstructureAmbit();
    	//try { rule.addSubstructure("[R]!@[*;!#1]"); } catch (Exception x) {x.printStackTrace();};
    	try { rule.addSubstructure("[*;!#1]!@[R]"); } catch (Exception x) {x.printStackTrace();};
    	return rule.getSelector();
    }
}
