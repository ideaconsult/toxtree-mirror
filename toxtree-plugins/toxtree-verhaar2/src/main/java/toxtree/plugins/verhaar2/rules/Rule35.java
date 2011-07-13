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
 * Possess activated C-C double/triple bonds. Compounds containing a polarizable substituent R1 (carbonyl, nitrile, amide, nitro, sulphone, etc) at an alpha position of a double or triple bond .
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 * <b>Modified</b> July 12, 2011
 */
public class Rule35 extends RuleSMARTSSubstructureAmbit {
	protected String[][] smarts = {
			{"carbonyl","[C!R]=,#[C!R]C(=O)O"},
			{"ketone","[C!R]=,#[C!R]C(=O)"},
			{"amide	","[C!R]=,#[C!R]C(=O)[N!R]([C!R])[C!R]"},
			{"nitrile","[C!R]=,#[C!R]C#N"},
			{"nitro","[C!R]=,#[C!R](N(=O)=O)"},
			{"nitro(charged)","[C!R]=,#[C!R]([N+](=O)[O-])"},
			{"sulphone","[C!R]=,#[C!R]S(=O)(=O)"},
			{"X","O=C1C=CC(=O)C=C1"},
	};		
	/**
	 * 
	 */
	private static final long serialVersionUID = 6071923192483785229L;

	public Rule35() {
		super();
		id = "3.5";
		setTitle("Possess activated C-C double/triple bonds. Compounds containing a polarizable substituent R1 (carbonyl, nitrile, amide, nitro, sulphone, etc) at an alpha position of a double or triple bond ");
		explanation.append("<html><ul>");
		for (String[] smart: smarts) try {
			addSubstructure(smart[0],smart[1]);
			explanation.append(String.format("<li>%s SMARTS: %s",smart[0],smart[1]));

		} catch (Exception x) {
			x.printStackTrace();
		}
		explanation.append("</ul></html>");
		//TODO verify if entities are suffiecient for the conition
		//add triple bond
		examples[0] = "CCCC(=O)OC";
		examples[1] = "CC=CC(=O)N(C)C";
		editable = false;
	}



}
