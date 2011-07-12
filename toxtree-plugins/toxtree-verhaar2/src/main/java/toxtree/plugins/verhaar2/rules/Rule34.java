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

import toxTree.tree.rules.DefaultAlertCounter;
import toxTree.tree.rules.IAlertCounter;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;



/**
 * 
 * Possess a three-membered heterocyclic ring. Compounds containing an epoxide or azaridine function.
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 * <b>Modified</b> July 12, 2011
 */
public class Rule34 extends RuleSMARTSSubstructureAmbit   {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9151362364966315658L;
	protected IAlertCounter alertsCounter;
	protected String[][] smarts = {
			{"epoxide","C1OC1"},
			{"aziridine","C1NC1"},
			{"3-membered heterocyclic ring","[r3;!$([#6])]"},
	};
	
	public Rule34() {
		super();
		id = "3.4";
		setTitle("Possess a three-membered heterocyclic ring. Compounds containing an epoxide or azaridine function");
		explanation = new StringBuffer();

		editable = false;
		alertsCounter = new DefaultAlertCounter();
		
		explanation.append("<html><ul>");
		for (String[] smart: smarts) try {
			addSubstructure(smart[0],smart[1]);
			explanation.append(String.format("<li>%s SMARTS: %s",smart[0],smart[1]));

		} catch (Exception x) {
			x.printStackTrace();
		}
		explanation.append("</ul></html>");		
		setExamples(new String[] {"C1CC1","C1CN1CCC"});
	}


}
