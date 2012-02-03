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
package eye.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import ambit2.smarts.query.SMARTSException;

/**
 * Pyrrolidones
 * @author Nina Jeliazkova nina@acad.bg
 */
public class Rule21 extends  RuleSMARTSubstructureCDK {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3597287604060552686L;
	private static final String MSG21="Pyrrolidones";
	public Rule21() {
		super();		
		try {
			addSubstructure("[#1,C]C1[CH2][CH2]C(=O)N1([#1,C])");
			
					//"C1CC(N([#1,C])C1([#1,C]))=O");
					//

			setID("21");
			setTitle(MSG21);
			
			editable = false;
			examples[0] = "C1NC(CC1)O";
			examples[1] = "C1NC(CC1)=O";			
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}
}

