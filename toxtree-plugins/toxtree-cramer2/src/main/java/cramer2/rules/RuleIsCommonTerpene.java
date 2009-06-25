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

*//*
 * Created on 2005-9-2
 *
 */
package cramer2.rules;

import toxTree.tree.rules.RuleCommonTerpene;

/**
 * Rule 16 of the Cramer scheme (see {@link cramer2.CramerRulesExtendedExtended})
 * Verifies if the substance is a terpene
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-2
 */
public class RuleIsCommonTerpene extends RuleCommonTerpene {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -2383248619028879671L;

	/**
	 * 
	 */
	public RuleIsCommonTerpene() {
		super();
		id = "16";
		title = "Common terpene";
		explanation = new StringBuffer();
		explanation.append("<html>Is it a <i>common terpene</i>(D)- hydrocarbon,-alcohol, -aldehyde or -carboxylic acid (not a ketone)?");
		explanation.append("<p>Q16 and Q17 deal with terpenes. A hydrocarbon terpene that is a  <i>common terpene</i>and has not already been put in class I by Q5, would go into class I by Q16.</html>");
		examples[0] = "[H][C@]1(O)CC2CCC1(C)C2(C)C";
		examples[1] = "[H][C@@]1(O)CC2CCC1(C)C2(C)C";
		editable = false;
	}

}
