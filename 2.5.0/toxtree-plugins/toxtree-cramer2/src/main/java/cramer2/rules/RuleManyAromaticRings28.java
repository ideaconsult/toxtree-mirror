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

*/
package cramer2.rules;

import toxTree.tree.rules.RuleManyAromaticRings;

/**
 * Rule 28 of the Cramer scheme (see {@link cramer2.CramerRulesExtendedExtended})
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-8-19
 */
public class RuleManyAromaticRings28 extends RuleManyAromaticRings {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 2045207740586587284L;

	/**
	 * 
	 */
	public RuleManyAromaticRings28() {
		super();
		id = "28";
		title = "More than one aromatic ring";
		explanation.append("<html>Does the structure contain more than one <i>aromatic</i> (B) ring?</html>");
		examples[0] = "c1ccc(cc1)C(C)CCCCC";
		examples[1] = "c1ccc(cc1)c2ccc(cc2)";	
		editable = false;
	}

}
