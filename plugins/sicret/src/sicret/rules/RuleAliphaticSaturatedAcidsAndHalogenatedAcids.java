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
 * 
 * Aliphatic Saturated Acids and Halogenated Acids.<br>
 * SMARTS pattern  <ul>
 * <li>[AR0][CX3](=O)[OX2H1]
 * <li>[ClX1,BrX1][C;H2][CX3](=O)[OX2H1]
 * </ul>
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */

public class RuleAliphaticSaturatedAcidsAndHalogenatedAcids extends  RuleSMARTSubstructure{
	private static final long serialVersionUID = 0;
	public RuleAliphaticSaturatedAcidsAndHalogenatedAcids() {
		//TODO fix sterically hindered condition (example NO fails)
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[AR0][CX3](=O)[OX2H1]");
			super.initSingleSMARTS(super.smartsPatterns,"2", "[ClX1,BrX1][C;H2][CX3](=O)[OX2H1]");
			
			id = "40";
			title = "AliphaticSaturatedAcidsAndHalogenatedAcids";
			
			examples[0] = "CC=O";
			examples[1] = "ClCCC(O)=O";	
			editable = false;
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}
}
