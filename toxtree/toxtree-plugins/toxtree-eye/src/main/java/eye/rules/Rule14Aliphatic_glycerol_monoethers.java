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
 * LipidSolubility < 4.0
 * @author Nina Jeliazkova
 *
 */
public class Rule14Aliphatic_glycerol_monoethers extends RuleSMARTSubstructureCDK {
	
	private static final long serialVersionUID = 0;
	public Rule14Aliphatic_glycerol_monoethers()
	{
		super();
		setTitle("Aliphatic glycerol monoethers");
		try {
			addSubstructure("[C][OX2][CH2]C(O)[CH2][OH]"); //[AR0][OX2]CC(O)CO");
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
		setID("14");
		examples[0] = "C=CCCC(C)CC";
		examples[1] = "C=COCC(O)CO";

	}


}
