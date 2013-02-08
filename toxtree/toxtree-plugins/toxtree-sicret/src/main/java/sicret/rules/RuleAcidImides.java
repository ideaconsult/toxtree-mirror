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
 * 
 * Acid imides.
 * <br>
 * SMARTS pattern  [CX3](=[OX1])[NX3H][CX3](=[OX1]).
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleAcidImides extends  RuleSMARTSubstructure{
	private static final long serialVersionUID = 0;
	public RuleAcidImides() {
		//TODO fix sterically hindered condition (example NO fails)
		super();		
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", "[CX3](=[OX1])[NX3H][CX3](=[OX1])");
			id = "60";
			title = "AcidImides";
			
			examples[0] = "C2NC(C1=CC=CC=C12)=O";
			examples[1] = "O=C2NC(C1=CC=CC=C12)=O";	
			editable = false;
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
	}
}

