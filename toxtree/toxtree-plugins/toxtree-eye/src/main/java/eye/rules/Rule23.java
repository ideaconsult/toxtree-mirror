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
	Substituted pyrasoles
 * @author Nina Jeliazkova nina@acad.bg

 */
public class Rule23 extends  RuleSMARTSubstructureCDK {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6832236568300428556L;
	private static final String MSG23=" Substituted pyrazoles";
	public Rule23() {

		super();		
		try {
            setContainsAllSubstructures(false);
			addSubstructure("c1([#1,NH2,C])nnc([#1,NH2,C])c1([#1,NH2,C])");
            
					//"c1([H,NH2,AR0])nnc([H,NH2,AR0])c1([H,NH2,AR0])");
			addSubstructure("c1([#1,NH2,C])nn(C(*)(*)(*))c([#1,NH2,C])c1([#1,NH2,C])");
            
            addSubstructure("C([#1,NH2,C])1=C([#1,NH2,C])C([#1,NH2,C])=NN1");
			//"c1([H,NH2,AR0])nn(C(*)(*)(*))c([H,NH2,AR0])c1([H,NH2,AR0])");
			setID("23");
			setTitle(MSG23);
			editable = false;
			examples[0] = "c1nc(c(c1C)C)C";
			//examples[1] = "n1nc(c(c1C)C)C";
            examples[1] = "C(C)1=C(C)C(C)=NN1";
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}
}

