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
 * Ketones, but not alpha-, beta unsaturated ketones (e.g. 1-butenone or acetophenone).
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 * <b>Modified</b> July 12, 2011
 */
public class Rule154 extends RuleSMARTSSubstructureAmbit {
	transient QueryAtomContainer ketone_a_b_unsaturated = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -453162542432372824L;
	static final String TITLE="Be ketones, but not alpha-, beta unsaturated ketones (e.g. 1-butenone or acetophenone)";
	protected Object[][] smarts = {
			{TITLE,"O=C([C;!$(C=C)])([C;!$(C=C)])",
			Boolean.TRUE},
	};	
	
	public Rule154() {
		super();
		id = "1.5.4";
		setTitle(TITLE);
		examples[0] = "CC(=O)C1=CC=CC=C1"; //acetophenone
		examples[1] = "O=C(C)CC";
		for (Object[] smart: smarts) try { 
			addSubstructure(smart[0].toString(),smart[1].toString(),!(Boolean) smart[2]);
		} catch (Exception x) {}
		editable = false;
	}
	
	@Override
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		return verifyRule(mol, null);
	}


}
