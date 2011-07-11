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
 * Contain only C,H and halogen.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule14 extends RuleElementsCounter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5751606276335590438L;

	public Rule14() {
		super();
		setComparisonMode(modeAllSpecifiedElements);
		addElement("C");
		addElement("H");
		addElement("F");
		addElement("Br");
		addElement("I");
		addElement("Cl");
		id = "1.4";
		setTitle("Contain only C,H and halogen");
		examples[0] = "COc1cccc(c1(O))C3C(C)C(Oc2cc4OCOc4(cc23))N5CCCCC5";
		examples[1] = "CCCCCCCCCCCl";
		setExplanation("Contain only C,H and halogen");
		editable = false;
	}

}
