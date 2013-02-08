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
 * Filename: RuleNormalBodyConstituent.java
 */
package cramer2.rules;

import toxTree.tree.rules.RuleStructuresList;

/**
 * Rule 1 of the Cramer scheme (see {@link cramer2.CramerRulesExtendedExtended})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class RuleNormalBodyConstituent extends RuleStructuresList {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 561099393926299574L;

    /**
	 * Constructor
	 * 
	 */
	public RuleNormalBodyConstituent() {
		super("bodymol.sdf");
		title = "Normal constituent of the body";
		explanation.append("<html>Is the substance a normal constituent of the body, or an optical isomer of such?<p>");
		explanation.append("This question throws into class I all normal constituents of body tissues and fluids, including normal metabolites. Hormones are excluded, as are, by implication, the metabolites of environmental and food contaminants or those resulting from disease state.");
		if (isImplemented()) {
			explanation.append("<p><b>Note the answer of the question relies on an incomplete list of compounds, indentified by an expert as a normal body constituents.");		
			explanation.append("If you believe a query compound is wrongly identfied as a such, or not recognised, ");
			explanation.append("please consult and/or update the list.<i>");
			explanation.append(getFile().getAbsolutePath());
			explanation.append("</i>");			
		} else {
			explanation.append("<p><b>Note the rule is not implemented and will answer <i>NO<i/> for every compound");			
		}
		explanation.append("</b></html>");
		id = "1";
		examples[0] = "C1=CN=CC=N1";
		examples[1] = "NC1=NC(=O)NC=C1";
		editable = false;
	}

	
}
