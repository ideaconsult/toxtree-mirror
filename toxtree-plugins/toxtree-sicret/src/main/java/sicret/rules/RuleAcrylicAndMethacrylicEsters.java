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

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.smarts.RuleSMARTSubstructure;
import ambit2.smarts.query.SMARTSException;

/**
 * 
 * Acrylic and methacrylic esters.<br>
 * SMARTS pattern  [CX3](=[CX3])[CX3](=[OX1])[OX2]C.
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleAcrylicAndMethacrylicEsters extends RuleSMARTSubstructure {
	private static final long serialVersionUID = 0;

	public RuleAcrylicAndMethacrylicEsters() {
		//TODO fix sterically hindered condition (example NO fails)
			
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[CX3](=[CX3])[CX3](=[OX1])[OX2]C");
			
			id = "50";
			title = "AcrylicAndMethacrylicEsters";
			
			examples[0] = "O=C(O)C(=C)C";	
			examples[1] = "O=C(OCC)C(=C)C";	
			editable = false;
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
	}
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		logger.info(toString());		
		return super.verifyRule(mol);
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractRule#isImplemented()
	 */
	public boolean isImplemented() {
		return true;
	}
}
