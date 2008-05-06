/*
Copyright Ideaconsult Ltd.(C) 2006  - 2008
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
package eye.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import toxTree.tree.rules.smarts.SMARTSException;

/**
 * 
 * Chlorinated aliphatic alcohols
 * @author Nina Jeliazkova nina@acad.bg
 */
public class Rule18 extends  RuleSMARTSubstructureCDK {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1030146366743047422L;
	private static final String MSG18 = "Chlorinated aliphatic alcohols";
	public Rule18() {
		//TODO fix sterically hindered condition (example NO fails)
		super();		
		try {
			addSubstructure("CC(Cl)([#1,C])C([#1,C])([#1,C])[CH2][OH]");

			setID("18");
			setTitle(MSG18);
			editable = false;
			examples[0] = "C(C(C(CC)C)(Cl)C=C)CC";
			examples[1] = "C(C(C(CO)C)(Cl)C=C)CC";
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}
}

