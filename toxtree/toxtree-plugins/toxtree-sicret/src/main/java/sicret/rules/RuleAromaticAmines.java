/*
Copyright Ideaconsult Ltd.(C) 2006  
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
package sicret.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolAnalyser;
import toxTree.tree.rules.RuleAnySubstructure;
import toxTree.tree.rules.RuleAromatic;
import toxTree.tree.rules.smarts.RuleSMARTSubstructure;

/**
 * Aromatic Amines.<br>
 * SMARTS pattern  <ul>
 * <li>[NX3,NX4+;!$([N]~[!#6]);!$([N]*~[#7,#8,#15,#16])]
 * </ul>
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleAromaticAmines extends RuleAnySubstructure {
	private static final long serialVersionUID = 0;
	

	/**
	 * 
	 */
	public RuleAromaticAmines() throws Exception {
		super();
		id = "61";
		title = "AromaticAmines";		
		examples[0] = "C1CCCCC1";
		examples[1] = "Nc1ccc(cc1)CN(CC)CC";
	}
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		logger.finer(getID());
	    
		String Amine = "[NX3,NX4+;!$([N]~[!#6]);!$([N]*~[#7,#8,#15,#16])]";
		RuleSMARTSubstructure ruleAmine = new RuleSMARTSubstructure();
		RuleAromatic ruleAromatic = new RuleAromatic();
		
		try {
			MolAnalyser.analyse(mol);
			ruleAmine.initSingleSMARTS(ruleAmine.getSmartsPatterns(),"1", Amine);		
			return (ruleAromatic.verifyRule(mol)&& ruleAmine.verifyRule(mol));
		}catch (Exception x) {
			throw new DecisionMethodException(x);
		}
	}
	/* (non-Javadoc)
     * @see toxTree.tree.AbstractRule#isImplemented()
     */
    public boolean isImplemented() {
        return true;
    }	
}
