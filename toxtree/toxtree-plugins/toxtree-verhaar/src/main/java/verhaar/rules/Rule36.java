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
 * Hydrazines or other compounds with a single , double or triple N-N linkage.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule36 extends RuleSMARTSSubstructureAmbit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2381714483943148195L;
	protected String[][] smarts = {
			{"hydrazine","[NH1;X3][NH1;X3]"},
			{"double N-N linkage","[NH0;X2]=[NH0;X2]"},
			{"N#N","[NX3]N#N"}

	};		
	
	public Rule36() {
		super();
		id = "3.6";
		setTitle("Be hydrazines or other compounds with a single , double or triple N-N linkage ");
		explanation = new StringBuffer();
		
		explanation.append("<html><ul>");
		for (String[] smart: smarts) try {
			addSubstructure(smart[0],smart[1]);
			explanation.append(String.format("<li>%s SMARTS: %s",smart[0],smart[1]));

		} catch (Exception x) {
			x.printStackTrace();
		}
		explanation.append("</ul></html>");
		editable = false;
		examples[1] = "CCCCCCNNCCCCC";
		examples[0] = "CCCCCCN(C)N(C)CCCCC";
	}


	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleSubstructures#isImplemented()
	 */
	public boolean isImplemented() {

		return true;
	}

}
