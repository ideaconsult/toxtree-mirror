/*
Copyright (C) 2005-2008  

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package eye.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import ambit2.smarts.query.SMARTSException;

public class Rule34 extends RuleSMARTSubstructureCDK {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6563533686492835370L;
	public static final String MSG34 = "Substituted benzoic acid halogenides";

	public Rule34() {
		
		super();		
		try {
			addSubstructure("[cH]1ccccc1C(=O)[F,Cl]");
			//addSubstructure("cC(=O)Cl");
			setID("34");
			setTitle(MSG34);
			editable = false;
			//examples[1] = "[H]C1=C([H])C([H])=C(C([H])=C1([H]))C(=O)Cl";
			examples[0] = "c1cccc(c1)C=O";
			examples[1] = "c1cccc(c1)C(Cl)=O";					
			
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}
}


