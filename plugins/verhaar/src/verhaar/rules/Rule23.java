/*
Copyright Nina Jeliazkova (C) 2005-2006  
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
package verhaar.rules;

import org.openscience.cdk.interfaces.AtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.exceptions.DecisionMethodException;



public class Rule23 extends Rule21 {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1821301568847254378L;

	public Rule23() {
		super();
		id = "2.3";
		setTitle("Be mononitroaromatics with one or two chlorine substituents and/or alkyl substituents");
		setExplanation("Be mononitroaromatics with one or two chlorine substituents and/or alkyl substituents");
		examples[0] = "O=[N+]([O-])c1ccc(cc1N(=O)O)CC(C)C";
		examples[1] = "O=[N+]([O-])c1cc(ccc1Cl)CC(C)C";
		editable = false;
		setMaxHalogens(2);
		
		setAnalyzeAromatic(true);
		setAnalyzeOnlyRingsWithFlagSet(true);
	}
	
	protected QueryAtomContainer createMainStructure() {
		//QueryAtomContainer q = FunctionalGroups.createAutoQueryContainer(MoleculeFactory.makeBenzene());
		//q.setID("benzene");
		return null;
	}
	public boolean verifyRule(AtomContainer mol) throws DecisionMethodException {
		if (super.verifyRule(mol)) {
			logger.debug("Nitrogroups\t",Integer.toString(nitroGroupsCount));
			return (nitroGroupsCount == 1);
		} else return false;
	}
}
