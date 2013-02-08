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

import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import ambit2.smarts.query.SMARTSException;
/**
 * Organic sulphonic salts
 * @author Nina Jeliazkova nina@acad.bg

 */
public class Rule25 extends  RuleSMARTSubstructureCDK {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7467138428398345475L;
	private static final String MSG25="Organic sulphonic salts";
	public Rule25() {
		super();		
		try {
			addSubstructure("[#6]S([OX2H])(=O)=O");
			setID("25");
			setTitle(MSG25);
	
			editable = false;
			examples[0] = "C(=O)(O)CCc1ccccc1";
			examples[1] = "S(=O)(=O)(O)CCc1ccccc1";			
			
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
	}
}