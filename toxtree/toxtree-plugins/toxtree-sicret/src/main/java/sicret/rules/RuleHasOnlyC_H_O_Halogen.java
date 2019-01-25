/*
Copyright Ideaconsult Ltd.(C) 2006  
Contact: nina@acad.bg

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
package sicret.rules;

import toxTree.tree.rules.RuleAllAllowedElements;
import toxTree.tree.rules.RuleElements;

/**
 * Contains only these elements  C,O,Halogen.
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleHasOnlyC_H_O_Halogen extends RuleAllAllowedElements {
	
	private static final long serialVersionUID = 0;	
	public RuleHasOnlyC_H_O_Halogen () {
		super();
		addElement("C");
		addElement("O");
		addElement("X");
		
		
	
	setComparisonMode(RuleElements.modeAllSpecifiedElements);
	title = "Group CHal (C,H,O,F,Cl,Br or I )";
	explanation = new StringBuffer();
	explanation.append("Does the structure contain only elements  C,O,Halogen?");
	id = "33";
	examples[0] = "CC=O";
	examples[1] = "ClCC=O";
	editable = false;
	}


}

