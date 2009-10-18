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
package sicret.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructure;
import ambit2.smarts.query.SMARTSException;
/**
 * Epoxides.<br>
 * SMARTS pattern  <ul>
 * <li>OX2r3]1[#6r3][#6r3]1
 * </ul>
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleEpoxides extends  RuleSMARTSubstructure{
	private static final long serialVersionUID = 0;
	public RuleEpoxides() {
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"Epoxides", "[OX2r3]1[#6r3][#6r3]1");
			id = "49";
			title = "Epoxides";
			
			examples[0] = "CN";
			examples[1] = "C1OC1C";	
			editable = false;
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}
}