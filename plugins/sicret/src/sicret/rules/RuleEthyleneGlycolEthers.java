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
import toxTree.tree.rules.smarts.SMARTSException;

/**
 * Ethylene Glycol Ethers<br>
 * SMARTS pattern  <ul>
 * <li>[OX2H][CX4;$([H1]),$(C[#6])][CX4;$([H1]),$(C[#6])][OX2H]
 * </ul>
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleEthyleneGlycolEthers extends  RuleSMARTSubstructure{
	private static final long serialVersionUID = 0;
	public RuleEthyleneGlycolEthers() {
		//TODO fix sterically hindered condition (example NO fails)
		super();		
		try {
			
			super.initSingleSMARTS(super.smartsPatterns,"EthyleneGlycolEthers", "[OX2H][CX4;$([H1]),$(C[#6])][CX4;$([H1]),$(C[#6])][OX2H]");
			id = "53";
			title = "EthyleneGlycolEthers";
			
			examples[0] = "CC(O)C";
			examples[1] = "CC(O)CO";	
			editable = false;
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}
}

