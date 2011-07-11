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


import toxtree.plugins.verhaar2.rules.helper.RuleElementsCounter;

/**
 * 
 * Contain only C,H,O and halogen.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule17 extends RuleElementsCounter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1870426414874844065L;

	public Rule17() {
		super();
		id = "1.7";
		setComparisonMode(modeAllSpecifiedElements);
		setTitle("Contain only C,H,O and halogen");
		addElement("C");
		addElement("H");
		addElement("O");
		addElement("Cl");
		addElement("Br");
		addElement("I");
		addElement("F");
		examples[0] = "CCCCCN";
		examples[1] = "CC(Cl)CCCCO";
		setExplanation("Contain only C,H,O and halogen");
		editable = false;
	}

}
