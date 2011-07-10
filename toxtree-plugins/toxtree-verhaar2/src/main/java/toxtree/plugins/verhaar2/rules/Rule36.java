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


import toxTree.tree.rules.RuleAnySubstructure;
import verhaar.query.FunctionalGroups;

/**
 * 
 * Hydrazines or other compounds with a single , double or triple N-N linkage.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule36 extends RuleAnySubstructure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2381714483943148195L;

	public Rule36() {
		super();
		id = "3.6";
		setTitle("Be hydrazines or other compounds with a single , double or triple N-N linkage ");
		explanation = new StringBuffer();
		explanation.append("<UL>");
		explanation.append("<LI>");
		explanation.append("N(N([*])[H])([*])[H]");
		explanation.append("<LI>");
		explanation.append("N(=N[*])[*]");
		explanation.append("<LI>");
		explanation.append("N#NN([*])[H]");		
		explanation.append("</UL>");
		addSubstructure(FunctionalGroups.createAutoQueryContainer("N(N([*])[H])([*])[H]"));
		addSubstructure(FunctionalGroups.createAutoQueryContainer("N(=N[*])[*]"));
		addSubstructure(FunctionalGroups.createAutoQueryContainer("N#NN([*])[H]"));
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
