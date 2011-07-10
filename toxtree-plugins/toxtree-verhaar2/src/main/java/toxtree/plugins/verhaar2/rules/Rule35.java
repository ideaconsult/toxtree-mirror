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


import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.tree.rules.RuleAnySubstructure;
import verhaar.query.FunctionalGroups;

/**
 * 
 * Possess activated C-C double/triple bonds. Compounds containing a polarizable substituent R1 (carbonyl, nitrile, amide, nitro, sulphone, etc) at an alpha position of a double or triple bond .
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule35 extends RuleAnySubstructure {
	protected String[][] entities = {
			{"carbonyl","[*]C=CC(=O)O[*]"},
			{"carbonyl","[*]C#CC(=O)O[*]"},			
			{"ketone","[*]C=CC(=O)[*]"},
			{"ketone","[*]C#CC(=O)[*]"},			
			{"amide	","[*]C=CC(=O)N(C)C"},
			{"amide	","[*]C#CC(=O)N(C)C"},			
			{"nitrile","[*]C=CC#N"},
			{"nitrile","[*]C#CC#N"},			
			{"nitro","[*]C=C(N(=O)=O)[*]"},
			{"nitro","[*]C#C(N(=O)=O)[*]"},
			{"nitro","[*]C=C([N+](=O)[O-])[*]"},
			{"nitro","[*]C#C([N+](=O)[O-])[*]"},
			{"sulphone","[*]C=CS(=O)(=O)(*)"},
			{"sulphone","[*]C#CS(=O)(=O)(*)"},
			{"X","O=C1C=CC(=O)C=C1"}
	};		
	/**
	 * 
	 */
	private static final long serialVersionUID = 6071923192483785229L;

	public Rule35() {
		super();
		id = "3.5";
		setTitle("Possess activated C-C double/triple bonds. Compounds containing a polarizable substituent R1 (carbonyl, nitrile, amide, nitro, sulphone, etc) at an alpha position of a double or triple bond ");
		explanation.append("[*]C=C[*]");
		for (int i = 0; i < entities.length; i++) {
			if (!entities[i][1].equals("")) {
				logger.debug(entities[i][1]);
				QueryAtomContainer q = FunctionalGroups.createAutoQueryContainer(entities[i][1]);
				
				q.setID(entities[i][0]);
				addSubstructure(q);
			}
		}
		//TODO verify if entities are suffiecient for the conition
		//add triple bond
		examples[0] = "CCCC(=O)OC";
		examples[1] = "CC=CC(=O)N(C)C";
		editable = false;
	}



}
