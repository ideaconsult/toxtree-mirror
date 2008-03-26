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
 * Filename: RuleHeterocyclic.java
 */
package toxTree.tree.cramer;

import toxTree.tree.rules.RuleHeterocyclic;

/**
 * Rule 7 of the Cramer scheme (see {@link toxTree.tree.cramer.CramerRules})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class RuleIsHeterocyclic extends RuleHeterocyclic {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 6440664854987866385L;
    /**
	 * Constructor
	 * 
	 */
	public RuleIsHeterocyclic() {
		super();
		id = "7";
		title = "Heterocyclic";
		explanation.append("Is the substance heterocyclic?");
		examples[0] = "C1CCC(=O)CC1";
		examples[1] = "CC1OCC(CO)O1";
		editable = false;
	}

}
