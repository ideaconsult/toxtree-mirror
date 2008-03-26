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


import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.tree.rules.RuleAnySubstructure;
import verhaar.query.FunctionalGroups;

public class Rule32 extends RuleAnySubstructure {
	protected String[][] entities = {
			{"c1ccc(cc1)C([*])X & X=Cl","c1ccc(cc1)C([*])Cl"},      
			{"c1ccc(cc1)C([*])X & X=Br","c1ccc(cc1)C([*])Br"},
			{"c1ccc(cc1)C([*])X & X=I","c1ccc(cc1)C([*])I"},
			{"c1ccc(cc1)C([*])X & X=cyano","c1ccc(cc1)C([*])C#N"},
			{"c1ccc(cc1)C([*])X & X=hydroxyl","c1ccc(cc1)C([*])O[H]"},
			{"c1ccc(cc1)C([*])X & X=ketone","c1ccc(cc1)C([*])C(C)=O"},
			{"c1ccc(cc1)C([*])X & X=aldehyde","c1ccc(cc1)C([*])C([H])=O"},
	};		
	/**
	 * 
	 */
	private static final long serialVersionUID = -1272754289124954435L;

	public Rule32() {
		super();
		id = "3.2";
		setTitle("Possess benzylic activation. Compounds with a (good) leaving group at an alpha position of an aromatic bond");
		explanation = new StringBuffer();
		explanation.append("c1ccc(cc1)C(R)X");
		examples[1] = "c1ccc(cc1)C(C)Cl";
		examples[0] = "c1ccc(cc1)C(C)C";
		editable = false;
		
		for (int i = 0; i < entities.length; i++) {
			if (!entities[i][1].equals("")) {
				logger.debug(entities[i][1]);
				QueryAtomContainer q = FunctionalGroups.createAutoQueryContainer(entities[i][1]);
				
				q.setID(entities[i][0]);
				addSubstructure(q);
			}
		}		
	}


	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleSubstructures#isImplemented()
	 */
	public boolean isImplemented() {

		return true;
	}

}
