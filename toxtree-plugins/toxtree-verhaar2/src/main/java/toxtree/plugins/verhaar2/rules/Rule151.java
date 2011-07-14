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
package toxtree.plugins.verhaar2.rules;


import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;

/**
 * 
 * Linear ethers or monocyclic mono ethers, but not epoxides or peroxides.
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 * <b>Modified</b> July 12, 2011
 */
public class Rule151 extends RuleSMARTSSubstructureAmbit {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5360285168848609301L;
	protected Object[][] smarts = {
			{"linear ethers or monocyclic mono ethers, but not epoxides or peroxides",
				"[#6]-[$([O;!R]),$([O;R;!r3])]-[#6]"
				,Boolean.TRUE},
	};		
	
	public Rule151()  {
		super();
		id = "1.5.1";
		setTitle("Be linear ethers or monocyclic mono ethers, but not epoxides or peroxides");
		setContainsAllSubstructures(false);
		for (Object[] smart: smarts) try { 
			addSubstructure(smart[0].toString(),smart[1].toString(),!(Boolean) smart[2]);
		} catch (Exception x) {}
		
		examples[0] = "C(C)CCC(COOC)C";  //epoxide C1OC1 , peroxide  X-O-O-X
		examples[1] = "C(C)CCC(COC)C";
		editable = false;
	}
	@Override
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		return verifyRule(mol,null);
	}

	public boolean isImplemented() {
		return true;
	}
}
