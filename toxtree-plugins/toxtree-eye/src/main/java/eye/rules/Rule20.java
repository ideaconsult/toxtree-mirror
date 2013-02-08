/*
Copyright Ideaconsult Ltd.(C) 2006-2008 
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
 * Derivatives of alpha amino benzene
 * @author Nina Jeliazkova nina@acad.bg
 */
public class Rule20 extends  RuleSMARTSubstructureCDK{
	/**
	 * 
	 */
	private static final long serialVersionUID = 783691118834275500L;
	private static final String MSG20="Derivatives of alpha amino benzene";
	public Rule20() {
		super();		
		try {
			addSubstructure("c1([cH][cH][cH][cH][cH]1)[CH]N([#1,C])");

					//"c1(ccccc1)C(*)N([#1,C])(*)");

			setID("20");
			setTitle(MSG20);
			editable = false;
			examples[0] = "c1(ccccc1)C(C(C)CC)C#C";
			examples[1] = "c1(ccccc1)C(N(C)CC)C#C";			
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
	}
}
