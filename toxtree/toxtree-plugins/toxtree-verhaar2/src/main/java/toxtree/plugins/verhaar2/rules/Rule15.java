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


import toxTree.tree.rules.RuleElements;

/**
 * 
 * Contain only C,H,O.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule15 extends RuleElements {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3777403460548583859L;

	public Rule15() {
		super();
		id = "1.5";
		setComparisonMode(modeAllSpecifiedElements);
		setTitle("Contain C,H & O");
		addElement("C");
		addElement("H");
		addElement("O");
		examples[1] = "Oc1ccccc1";
		examples[0] = "Nc1ccccc1";
		setExplanation("Contain only C,H and O");
		setEditable(false);
	}
 
}
