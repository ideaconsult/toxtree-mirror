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
 * 
 * Acid anhydrides.
 * @author Martin Martinov
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule15 extends RuleSMARTSubstructureCDK {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9104503071300748951L;
	public final static transient String MSG_15="Derivatives of 2-halogen benzoic acids and corresponding alkali salts";


    /**
	 * Constructor
	 * 
	 */
	public Rule15() {

		super();
		try {
			addSubstructure(MSG_15,"c1([Cl,F,Br,#1,C])c([Cl,F,Br,#1,C])c([Cl,F,Br,#1,C])c([Cl,F,Br,#1,C])c([Cl,F,Br])c1C(=O)[OH]");
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
		setTitle(MSG_15);
		setID("15");
		examples[0] = "c1ccc(cc1Cl)Cl";
		examples[1] = "c1(ccc(cc1Cl)Cl)C(=O)O";

		editable = false;
	}

}
