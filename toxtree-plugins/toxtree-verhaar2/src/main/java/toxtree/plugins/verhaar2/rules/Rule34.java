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

import toxTree.tree.cramer.Rule3MemberedHeterocycle;



/**
 * 
 * Possess a three-membered heterocyclic ring. Compounds containing an epoxide or azaridine function.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule34 extends Rule3MemberedHeterocycle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9151362364966315658L;

	public Rule34() {
		super();
		id = "3.4";
		setTitle("Possess a three-membered heterocyclic ring. Compounds containing an epoxide or azaridine function");
		explanation = new StringBuffer();
		explanation.append("<UL>");
		explanation.append("<LI>");
		explanation.append("O1C([*])C1[*]");
		explanation.append("<LI>");
		explanation.append("N1C([*])C1[*]");
		explanation.append("</UL>");
		editable = false;
	}


}
