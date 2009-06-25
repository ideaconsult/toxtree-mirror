/*
Copyright Ideaconsult Ltd. (C) 2005-2007 

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*//**
 * Created on 2005-5-2

 * @author Nina Jeliazkova nina@acad.bg
 *
 * Project : toxtree
 * Package : toxTree.tree.rules
 * Filename: RuleAromatic.java
 */
package cramer2.rules;

import toxTree.tree.rules.RuleAromatic;

/**
 * Rule 23 of the Cramer scheme (see {@link cramer2.CramerRulesExtendedExtended})
 * Note this is descendant of {@link toxTree.tree.rules.RuleAromatic} and therefore 
 * follows the Hueckel rule, rather than verifying for the presence of 
 * benzene, furan, thiophene, pyridine or pyrrole ring
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class RuleIsAromatic extends RuleAromatic {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 6162237776922535251L;
    /**
	 * Constructor
	 * 
	 */
	public RuleIsAromatic() {
		super();
		id = "23";
		title = "Aromatic";
		explanation.append("<html>Is the substance <i>aromatic</i>");
		explanation.append("<p>Q 23-26 deal with alicyclic substances</html>");
		explanation.append("<u>The implementation follows the Hueckel rule, rather than verifying for the presence of  benzene, furan, thiophene, pyridine or pyrrole ring.</u>");
		explanation.append("</html>");
		examples[0] = "O=C1C=CC=C1";
		examples[1] = "O=C1CCC2=C(C1)C=CC=C2";
		editable = false;
	}
}
