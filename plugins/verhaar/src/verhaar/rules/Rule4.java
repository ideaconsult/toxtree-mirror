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


import org.openscience.cdk.interfaces.AtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;

public class Rule4 extends AbstractRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5579727497271403749L;

	public Rule4() {
		super();
		id = "4";
		setTitle("Compounds acting by a specific mechanism");
		explanation.append("It is not possible to give definitive structural rules for this class.");
		explanation.append("Inclusion in this class must and should, be based on specific knowledge on ");
		explanation.append("mode of toxic actions of (groups of) chemicals. Examples of groups of compounds that ");
		explanation.append("are known to act by a specific mode of toxic action are ");
		explanation.append("<UL>");
		explanation.append("<LI>DDT and analogues");
		explanation.append("<LI>(dithio) carbamates");
		explanation.append("<LI>organotin compounds");
		explanation.append("<LI>pyrethroids");
		explanation.append("<LI>organophosphorothionate esters");
		explanation.append("</UL>");
		explanation.append("Compounds that cannot be classified as belonging to class 1,2 or 3 and that are not known ");
		explanation.append("to be compounds acting by a specific mechanism can only be classified as ");
		explanation.append("\"not possible to classify according to these rules\"");
		editable = false;
	}

	public boolean verifyRule(AtomContainer mol) throws DecisionMethodException {
		// TODO Auto-generated method stub
		return false;
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractRule#isImplemented()
	 */
	public boolean isImplemented() {
		return false;
	}

}
