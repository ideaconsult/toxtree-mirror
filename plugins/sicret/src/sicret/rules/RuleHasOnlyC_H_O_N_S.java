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

 * Contains only these elements  C,O,N,S,Halogen.
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleHasOnlyC_H_O_N_S extends RuleAllAllowedElements {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 0;
    /**
	 * Constructor
	 * 
	 */
	public RuleHasOnlyC_H_O_N_S() {
		super();
		addElement("C");
		addElement("O");			
		addElement("N");
		addElement("S");
		setComparisonMode(RuleElements.modeAllSpecifiedElements);
		title = "Group CNS (C,H,O,N,S)";
		explanation = new StringBuffer();
		explanation.append("Does the structure contain elements other than C, O, N,S?");
		id = "27";
		examples[0] = "CC(=S)N";
		examples[1] = "CCOC(=O)N1C=CN(C)C1(=S)";
		editable = false;
	}

	
}

