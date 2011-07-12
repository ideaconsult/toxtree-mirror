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
 * Activated nitriles like alpha hydroxynitriles (cyanohidrins) or allylic /propargylic nitriles.
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 * <b>Modified</b> July 12, 2011
 */
public class Rule37 extends RuleSMARTSSubstructureAmbit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 832778835073402054L;
	protected String[][] smarts = {
			{"cyanohydrins","[OH1][CX4]C#N"},
			{"allylic/propargylic nitriles","[CX2]#CC#N"}

	};		

	public Rule37() {
		super();
		id = "3.7";
		setTitle("Be activated nitriles like alpha hydroxynitriles (cyanohidrins) or allylic /propargylic nitriles");
		explanation = new StringBuffer();
		
		
		explanation.append("<html><ul>");
		for (String[] smart: smarts) try {
			addSubstructure(smart[0],smart[1]);
			explanation.append(String.format("<li>%s SMARTS: %s",smart[0],smart[1]));

		} catch (Exception x) {
			x.printStackTrace();
		}
		explanation.append("</ul></html>");		
		examples[1] = "CC#CC#N";
		examples[0] = "C(C)CC#N";
		editable = false;
	}

	
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleSubstructures#isImplemented()
	 */
	public boolean isImplemented() {
		return true;
	}

}
