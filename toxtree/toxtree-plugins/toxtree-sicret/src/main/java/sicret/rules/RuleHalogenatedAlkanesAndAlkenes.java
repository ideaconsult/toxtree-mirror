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

import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSubstructure;
import ambit2.smarts.query.SMARTSException;

/**
 * Halogenated Alkanes And Alkenes<br>
 * SMARTS patterns  <ul>
 * <li>[FX1,ClX1,BrX1,IX1][CX4H3]
 * <li>[FX1,ClX1,BrX1,IX1][CX4H2][#6]
 * <li>[FX1,ClX1,BrX1,IX1][CX3]=[CX3]
 * </ul>
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleHalogenatedAlkanesAndAlkenes extends  RuleSMARTSubstructure{
	private static final long serialVersionUID = 0;
	public RuleHalogenatedAlkanesAndAlkenes() {
		//TODO fix sterically hindered condition (example NO fails)
		super();		
		try {
			//TODO this fails "ClC(Cl)(Cl)C"
			/*
			super.initSingleSMARTS(super.smartsPatterns,"1", "[FX1,ClX1,BrX1,IX1][CX4H3]");
			super.initSingleSMARTS(super.smartsPatterns,"2", "[FX1,ClX1,BrX1,IX1][CX4H2][#6]");
			super.initSingleSMARTS(super.smartsPatterns,"3", "[FX1,ClX1,BrX1,IX1][CX3]=[CX3]");
			*/
			super.initSingleSMARTS(super.smartsPatterns,"3", "[$([FX1,ClX1,BrX1,IX1][CX3]=[CX3]);$([!c]);$([!R])]");
			super.initSingleSMARTS(super.smartsPatterns,"4", "[$([FX1,ClX1,BrX1,IX1][CX4]);$([!c]);$([R0])]");			
			id = "69";
			title = "HalogenatedAlkanesAndAlkenes";
			
			examples[0] = "CCN";
			examples[1] = "CCCl";	
			editable = false;
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
	}
}
