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
 * Created on 2005-5-3

 * @author Nina Jeliazkova nina@acad.bg
 *
 * Project : toxtree
 * Package : toxTree.tree.rules
 * Filename: RuleRingsWithSubstituents.java
 */
package cramer2.rules;

import toxTree.tree.rules.RuleAnySubstituents;

/**
 * Rule 27 of the Cramer scheme (see {@link cramer2.CramerRulesExtendedExtended})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-3
 */
public class RuleRingsWithSubstituents extends RuleAnySubstituents {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -7684427072750973831L;

    /**
	 * Constructor
	 * 
	 */
	public RuleRingsWithSubstituents() {
		super();
		id = "27";
		title = "Rings with substituents";
		explanation.append("<html>");
		explanation.append("Do(es) the ring(s) have any substituents? <p>");
		explanation.append("Q27-31 deal with aromatic compounds.");
		explanation.append("</html>");
		examples[0] = "C1=CC2=C(C=C1)C=CC=C2";
		examples[1] = "CC1=CC=CC=C1";
		editable = false;
	}

}
