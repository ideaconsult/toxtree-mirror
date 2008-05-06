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

import toxTree.tree.rules.smarts.RuleSMARTSubstructure;
import toxTree.tree.rules.smarts.SMARTSException;

/**
Substituted indoles
 * @author Nina Jeliazkova nina@acad.bg
 */
public class Rule22 extends  RuleSMARTSubstructure {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4554821516348794326L;
	private static final String MSG22 = "Substituted indoles";
	public Rule22() {
		super();		
		try {
			setContainsAllSubstructures(false);

			addSubstructure("c21c([#1,OH])[cH]c([#1,OH])[cH]c1[$(n([#6][CX3](=O)[C])),$([nH1])]c[cH]2");
			addSubstructure("c21[cH]c([#1,OH])[cH]c([#1,OH])c1[$(n([#6][CX3](=O)[C])),$([nH1])]c[cH]2");
			addSubstructure("c21[cH][cH]c([#1,OH])c([#1,OH])c1[$(n([#6][CX3](=O)[C])),$([nH1])]c[cH]2");
			
			setID("22");
			setTitle(MSG22);
			
			examples[0] = "O=C(N)C";
			examples[1] = "O=C(C)CN2C(C)=CC=1C=CC(O[H])=CC=12";

			editable = false;
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}
}