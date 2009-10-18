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
 * @author Nina Jeliazkova nina@acad.bg
 */
public class Rule29 extends  RuleSMARTSubstructureCDK {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7808619208415829555L;
	private static final String MSG29="Organic phosphinic acids and their derivatives";
	public Rule29() {
		//TODO fix sterically hindered condition (example NO fails)
		super();		
		try {
			addSubstructure("P([#6])([#1,CH2])(=O)([OH])");
			//"P([c,AR0])([#1,CH2])(=O)([OH])");
			setID("29");
			setTitle(MSG29);
			editable = false;
			examples[0] = "c1(ccccc1)P(=O)(C)/C=C/C";
			examples[1] = "c1(ccccc1)P(=O)(O)/CC";					
			
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}
}