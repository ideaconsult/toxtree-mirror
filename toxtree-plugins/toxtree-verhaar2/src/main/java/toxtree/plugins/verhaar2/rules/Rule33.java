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
 * Be other compound with a (good) leaving group at an alpha position of a double or triple bond fragment.
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 * <b>Modified</b> July 12, 2011
 */
public class Rule33 extends RuleSMARTSSubstructureAmbit {
	protected String[][] smarts = {
			{"C=,#CC(C)X & X=halogen","C=,#CC([C;!R])[Cl,Br,I]"},      
			{"C=,#CC(C)X & X=cyano","C=,#CC([C;!R])[C;!R]#N"},			
			{"C=,#CC(C)X & X=hydroxyl","C=,#CC([C;!R])[OH1;!R]"},
			{"C=,#CC(C)X & X=ketone","C=,#CC([C;!R])[C;!R]C(O)=O"},			
			{"C=,#CC(C)X & X=aldehyde","C=,#CC([C;!R])[C;!R][CH1]=O"},
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
