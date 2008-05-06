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
import toxTree.tree.rules.smarts.SMARTSException;

/**
 * @author Nina Jeliazkova nina@acad.bg
 */
public class Rule27 extends  RuleSMARTSubstructureCDK {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2060891977483904587L;
	private static final String MSG27="Thiazolones";
	public Rule27() {

		super();		
		try {
			addSubstructure("C=1([C])C(N(SC=1([C]))C)=O");
			addSubstructure("c21C(N([C])[#16]c1[cH][cH][cH][cH]2)=O");
			setID("27");
			setTitle(MSG27);
			editable = false;
			examples[0] = "C=1(C(C(CC=1C)C)=O)C";
			examples[1] = "C=1(C(N(SC=1C)C)=O)C";					
			
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}
}
