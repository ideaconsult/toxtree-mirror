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


import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;

/**
 * 
 * Possess benzylic activation. Compounds with a (good) leaving group at an alpha position of an aromatic bond.
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 * <b>Modified</b> July 12, 2011
 */
public class Rule32 extends RuleSMARTSSubstructureAmbit {
	protected String[][] smarts = {
			{"c1ccc(cc1)C([*])X & X=Cl","a[$([CX4H1]),$([CX4][C,N,O,S,Cl,Br,I])][Cl,Br,I]"},      
			{"c1ccc(cc1)C([*])X & X=cyano","a[$([CX4H1]),$([CX4][C,N,O,S,Cl,Br])]C#N"},
			{"c1ccc(cc1)C([*])X & X=hydroxyl","a[$([CX4H1]),$([CX4][C,N,O,S,Cl,Br])][OH1]"},
			{"c1ccc(cc1)C([*])X & X=ketone","a[$([CX4H1]),$([CX4][C,N,O,S,Cl,Br])]C(C)=O"},
			{"c1ccc(cc1)C([*])X & X=aldehyde","a[$([CX4H1]),$([CX4][C,N,O,S,Cl,Br])][CH1]=O"},
	};		
	/*
			{"c1ccc(cc1)C([*])X & X=cyano","a[$([CX4H1]),$([CX4][C,N,O,S,Cl,Br])]C#N"},
			{"c1ccc(cc1)C([*])X & X=hydroxyl","a[$([CX4H1]),$([CX4][C,N,O,S,Cl,Br])][OH1]"},
			{"c1ccc(cc1)C([*])X & X=ketone","a[$([CX4H1]),$([CX4][C,N,O,S,Cl,Br])]C(C)=O"},
			{"c1ccc(cc1)C([*])X & X=aldehyde","a[$([CX4H1]),$([CX4][C,N,O,S,Cl,Br])][CH1]=O"},
	 */
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
		
		
		for (String[] smart: smarts) try {
			addSubstructure(smart[0],smart[1]);
		} catch (Exception x) {
			x.printStackTrace();
		}		
	}


	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleSubstructures#isImplemented()
	 */
	public boolean isImplemented() {

		return true;
	}

}
