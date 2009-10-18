/*
Copyright (C) 2005-2007  

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package toxTree.tree.rules.smarts;

import java.util.Map;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.smarts.SmartsPatternFactory.SmartsParser;
import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.SMARTSException;

/**
 * An IDecisionRule, making use of CDK SMARTS parser.
 * @author Nina Jeliazkova nina@acad.bg
 *
 */
public class RuleSMARTSubstructureCDK extends AbstractRuleSmartSubstructure<IAtomContainer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3210450290267138768L;

	public ISmartsPattern createSmartsPattern(String smarts, boolean negate) throws SMARTSException {
		return SmartsPatternFactory.createSmartsPattern(
				SmartsParser.smarts_cdk, smarts, negate);
		
	}	

	@Override
	protected IAtomContainer getObjectToVerify(IAtomContainer mol) {
		return mol;
	}

	@Override
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		boolean ok = super.verifyRule(mol);
		//clear atom properties because of a bug http://sourceforge.net/tracker/index.php?func=detail&aid=1838820&group_id=20024&atid=120024
		for (int i=0; i < mol.getAtomCount();i++) {
			IAtom atom = mol.getAtom(i); 
			Map p = atom.getProperties();
			Object[] keys = p.keySet().toArray();
			for (int j=0; j < keys.length-1; j++) {
				atom.removeProperty(keys[j]);
			}
		}
		return ok;
	}
}


