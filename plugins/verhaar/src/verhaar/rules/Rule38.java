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

public class Rule38 extends RuleAnySubstructure {
	protected String[][] entities = {
			{"acid anhydrides","[*]C(=O)OC(=O)[*]"},
			{"lactones",""},
			{"acid halides","[*]C(=O)Cl"},
			{"carbamohalides","[*]NC(=O)Cl"},
			{"ketenes","[*]C=C=O"},
			{"aldehydes","[*]C(=O)[H]"},
			{"isocyanates","[*]N=C=O"},
			{"thiocyanates","[*]SC#N"},
			{"isothiocyanates","[*]N=C=S"},
			{"disulphides","[*]SS[*]"},
			{"sulphonic esters","[*]S(=O)(=O)O[*]"},
			{"sulphiric esters","[H]OS(=O)(=O)O[*]"},
			{"cyclic sulphonic esters",""},
			{"cyclic sulphuric esters",""},
			{"alpha haloethers","[*]OC(Cl)[*]"},
			{"alpha haloethers","[*]OC(F)[*]"},
			{"alpha haloethers","[*]OC(Br)[*]"},
			{"alpha haloethers","[*]OC(I)[*]"},
			{"beta haloethers","[*]OCC(Cl)[*]"},
			{"beta haloethers","[*]OCC(Br)[*]"},
			{"beta haloethers","[*]OCC(F)[*]"},
			{"beta haloethers","[*]OCC(I)[*]"},
			{"nitrogen mustards","[*]NCC(Cl)[*]"},
			{"nitrogen mustards","[*]NCC(F)[*]"},
			{"nitrogen mustards","[*]NCC(Br)[*]"},
			{"nitrogen mustards","[*]NCC(I)[*]"},
			{"sulphur mustards","[*]SCC(Cl)[*]"},
			{"sulphur mustards","[*]SCC(F)[*]"},
			{"sulphur mustards","[*]SCC(Br)[*]"},
			{"sulphur mustards","[*]SCC(I)[*]"}
	};
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -673417256329929944L;

	public Rule38() {
		super();
		id = "3.8";
		setTitle("Contain one of the following structural entities");
		explanation = new StringBuffer();
		explanation.append("<UL>");
		
		for (int i = 0; i < entities.length; i++) {
			explanation.append("<LI>");
			explanation.append(entities[i][0]);
			explanation.append(entities[i][1]);			
			explanation.append("</LI>");
		}	
		explanation.append("</UL>");
		//TODO da narisuwam ostanalite
		for (int i = 0; i < entities.length; i++) {
			if (!entities[i][1].equals("")) {
				logger.debug(entities[i][1]);
				QueryAtomContainer q = FunctionalGroups.createAutoQueryContainer(entities[i][1]);
				
				q.setID(entities[i][0]);
				addSubstructure(q);
			}
		}
		addSubstructure(FunctionalGroups.lactone(false));
		addSubstructure(FunctionalGroups.cyclicEster());
		examples[1] = "O=C(NC(C)CC)Cl";
		examples[0] = "CCCCCCCCCCCC";
		editable = false;
	}


	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleSubstructures#isImplemented()
	 */
	public boolean isImplemented() {

		return true;
	}
}
