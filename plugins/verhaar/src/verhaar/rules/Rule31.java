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

public class Rule31 extends RuleAnySubstructure {
	protected String[][] entities = {
			{"[*]C=CC(X)[*] & X=Cl","[*]C=CC(Cl)[*]"},      
			{"[*]C=CC(X)[*] & X=Br","[*]C=CC(Br)[*]"},
			{"[*]C=CC(X)[*] & X=I","[*]C=CC(I)[*]"},
			{"[*]C=CC(X)[*] & X=cyano","[*]C=CC(C#N)[*]"},
			{"[*]C=CC(X)[*] & X=hydroxyl","[*]C=CC(O[H])[*]"},
			{"[*]C=CC(X)[*] & X=ketone","[*]C=CC(C(C)=O)[*]"},
			{"[*]C=CC(X)[*] & X=aldehyde","[*]C=CC(C([H])=O)[*]"},
			{"[*]C#CC([*])X & X=Cl","[*]C#CC([*])Cl"},
			{"[*]C#CC([*])X & X=Br","[*]C#CC([*])Br"},
			{"[*]C#CC([*])X & X=I","[*]C#CC([*])I"},
			{"[*]C#CC([*])X & X=cyano","[*]C#CC([*])C#N"},
			{"[*]C#CC([*])X & X=hydroxyl","[*]C#CC([*])O[H]"},
			{"[*]C#CC([*])X & X=ketone","[*]C#CC([*])C(C)=O"},
			{"[*]C#CC([*])X & X=qldehyde & ","[*]C#CC([*])C(C([H]))=O"}
	};		
	/**
	 * 
	 */
	private static final long serialVersionUID = 3137352402099295614L;

	/**
	 * [*]C=CC(X)[*]
	 *
	 */
	public Rule31() {
		super();
		id = "3.1";
		setTitle("Possess alylic/propargylic activation. Compounds with a (good) leaving group at an alpha position of C-C double or triple bond.");
		explanation = new StringBuffer();
		explanation.append("<UL><LI>RC=CC(R)X<LI>RC=CC(X)R</UL><p>");
		explanation.append("Leaving groups are structures that are sufficiently stable (under certain conditions) ");
		explanation.append(" that they can stabilize an isolated negative charge. Examples of good leaving groups are: ");
		explanation.append("halogen, (Cl, Br, I), cyanide, or hydroxyl group (under acidic or basic conditions).");
		editable = false;
		
		for (int i = 0; i < entities.length; i++) {
			if (!entities[i][1].equals("")) {
				logger.debug(entities[i][1]);
				QueryAtomContainer q = FunctionalGroups.createAutoQueryContainer(entities[i][1]);
				
				q.setID(entities[i][0]);
				addSubstructure(q);
			}
		}		
		
		examples[1] = "C(#CC(C)C)C(CC)Cl";
		examples[0] = "C(#CC(C)C)C(CC)C(C)C";
		
	}


	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleSubstructures#isImplemented()
	 */
	public boolean isImplemented() {
		return true;
	}
}
