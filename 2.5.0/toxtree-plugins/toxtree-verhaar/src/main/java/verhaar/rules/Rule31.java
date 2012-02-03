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


import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;

/**
 * 
 * Possess alylic/propargylic activation. Compounds with a (good) leaving group at an alpha position of C-C double or triple bond.
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 * <b>Modified</b> July 12, 2011
 */
public class Rule31 extends RuleSMARTSSubstructureAmbit {
	protected String[][] smarts = {
			{"[*]C=CC(X)[*] & X=halogen","C=;!:CC([Cl,Br,I])"},      
			{"[*]C=CC(X)[*] & X=cyano","C=;!:CC(C#N)"},
			{"[*]C=CC(X)[*] & X=hydroxyl","C=;!:CC(O[H])"},
			{"[*]C=CC(X)[*] & X=ketone","C=;!:CC(C(C)=O)"},
			{"[*]C=CC(X)[*] & X=aldehyde","C=;!:CC([CH1]=O)"},
			{"[*]C#CC([*])X & X=halogen","C#C[C;v4;H1][Cl,Br,I]"},
			{"[*]C#CC([*])X & X=cyano","C#C[C;v4;H1]C#N"},
			{"[*]C#CC([*])X & X=hydroxyl","C#C[C;v4;H1]O[H]"},
			{"[*]C#CC([*])X & X=ketone","C#C[C;v4;H1]C(C)=O"},
			{"[*]C#CC([*])X & X=aldehyde & ","CC#C[C;v4;H1](C)[CH1]=O"} 
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
		
		for (String[] smart: smarts) try {
			addSubstructure(smart[0],smart[1]);
		} catch (Exception x) {
			x.printStackTrace();
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
