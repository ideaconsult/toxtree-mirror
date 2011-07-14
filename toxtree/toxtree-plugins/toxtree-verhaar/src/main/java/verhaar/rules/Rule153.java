/*
Copyright Nina Jeliazkova (C) 2005-2011  
Contact: jeliazkova.nina@gmail.com

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
package verhaar.rules;


import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;

/**
 * 
 * Alcohols with aromatic moieties, but NOT phenols or benzylic alcohols.
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 * <b>Modified</b> July 12, 2011
 */
public class Rule153 extends RuleSMARTSSubstructureAmbit {
	QueryAtomContainer phenol = null;
	QueryAtomContainer benzylAlcohol = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 7020871482125337347L;
	private final static String TITLE= "Be alcohols with aromatic moieties, but NOT phenols or benzylic alcohols";
	protected Object[][] smarts = {
			{TITLE,"c[OH1]",Boolean.FALSE},
			{"2","cC[OH1]",Boolean.FALSE},
			{"3","(C[OH1].a)",Boolean.TRUE},
	};	

	
	public Rule153() {
		super();
		id = "1.5.3";
		setTitle(TITLE);
		setContainsAllSubstructures(true);
		for (Object[] smart: smarts) try { 
			addSubstructure(smart[0].toString(),smart[1].toString(),!(Boolean) smart[2]);
		} catch (Exception x) {}
		examples[0] = "c1ccccc1O";  //benzyl alcohol: c1ccccc1CO
		examples[1] = "c1ccccc1CCCCO"; //
		editable = false;
	}
	@Override
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		return verifyRule(mol, null);
	}

	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleOnlyAllowedSubstructures#isImplemented()
	 */
	public boolean isImplemented() {
		return true;
	}
}
