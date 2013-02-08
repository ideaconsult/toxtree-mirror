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

import java.util.logging.Level;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.tree.rules.RuleAnySubstructure;
import toxTree.tree.rules.smarts.RuleSMARTSubstructure;
import ambit2.smarts.query.SMARTSException;


/**
 * Rule Quaternary Organic Ammonium And Phosphonium Salts<br>
 * 
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleQuaternaryOrganicAmmoniumAndPhosphoniumSalts extends RuleAnySubstructure {
	private static final long serialVersionUID = 0;
	RuleSMARTSubstructure rule = null;
	public RuleQuaternaryOrganicAmmoniumAndPhosphoniumSalts() {
		//TODO fix sterically hindered condition (example NO fails)
		super();
		try {
			addSubstructure(FunctionalGroups.createAtomContainer("[N+]23(CN1CN(CN(C1)C2)C3)",false));	
			rule = new RuleSMARTSubstructure();
			String PhosphoniumSalts = "[PX4]c1ccccc1";
			rule.initSingleSMARTS(rule.getSmartsPatterns(),"1", PhosphoniumSalts);
			
			id = "56";
			title = "Quaternary Organic Ammonium And Phosphonium Salts";
			
			examples[0] = "c1ccccc1ON";
			examples[1] = "ClC=CC[N+]23(CN1CN(CN(C1)C2)C3)";	
			editable = false;
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
	}
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		logger.finer(toString());
		int c = 0;		
		for (int i=0; i < mol.getAtomCount();i++) {
			IAtom a = mol.getAtom(i);				
			if (a.getSymbol().equals("Cl")) {
				 c++;
				 
			}
			
		}
		return ((super.verifyRule(mol) || rule.verifyRule(mol))   && c > 0);
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractRule#isImplemented()
	 */
	public boolean isImplemented() {
		return true;
	}
}
