/*
Copyright Nina Jeliazkova (C) 2005-2006  
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
package verhaar.rules;


import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.jchempaint.renderer.selection.IChemObjectSelection;

import ambit2.base.interfaces.IProcessor;
import toxTree.tree.rules.RuleElements;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;

/**
 * 
 * Contain only C&H.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule13 extends RuleElements {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3138693766571092656L;

	public Rule13() {
		super();
		setComparisonMode(modeAllSpecifiedElements);
		id = "1.3";
		setTitle("Contain only C&H");
		examples[0] = "C=O";
		examples[1] = "CCCC";
		addElement("C");
		addElement("H");
		setExplanation("Contain only C and H");
		editable = false;
	}

}
