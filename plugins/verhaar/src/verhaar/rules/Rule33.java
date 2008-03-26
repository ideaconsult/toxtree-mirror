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

public class Rule33 extends RuleAnySubstructure {
	protected String[][] entities = {
			{"C=CC(C)X & X=Cl","C=CC(C)Cl"},      
			{"C=CC(C)X & X=Br","C=CC(C)Br"},
			{"C=CC(C)X & X=I","C=CC(C)I"},
			{"C=CC(C)X & X=cyano","C=CC(C)C#N"},			
			{"C=CC(C)X & X=hydroxyl","C=CC(C)O[H]"},
			{"C=CC(C)X & X=ketone","C=CC(C)CC(O)=O"},			
			{"C=CC(C)X & X=aldehyde","C=CC(C)CC([H])=O"},
			{"C#CC(C)X & X=Cl","C#CC(C)Cl"},      
			{"C#CC(C)X & X=Br","C#CC(C)Br"},
			{"C#CC(C)X & X=I","C#CC(C)I"},
			{"C#CC(C)X & X=cyano","C#CC(C)C#N"},			
			{"C#CC(C)X & X=hydroxyl","C#CC(C)O[H]"},
			{"C#CC(C)X & X=ketone","C#CC(C)CC(O)=O"},			
			{"C#CC(C)X & X=aldehyde","C#CC(C)CC([H])=O"}		
			
	};	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8529289379596515979L;

	public Rule33() {
		super();
		id = "3.3";
		setTitle("Be other compound with a (good) leaving group at an alpha position of a double or triple bond fragment");
		explanation.append("<UL>");
		explanation.append("<LI>XC(C)C=C");
		explanation.append("<LI>C#CC(C)(C)X");
		explanation.append("</UL>");
		examples[1] = "ClC(C)C=C";
		examples[0] = "ClC(C)CC"; 
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
