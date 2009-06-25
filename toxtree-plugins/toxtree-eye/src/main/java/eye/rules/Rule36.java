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
import toxTree.tree.rules.smarts.SMARTSException;

public class Rule36 extends RuleSMARTSubstructureCDK  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6609334742089655488L;
	public static final String MSG36 = "Chlorosilanes";

	public Rule36() {
		
		super();		
		try {
			addSubstructure("[Cl][Si]([*])([*])([*])");
			setID("36");
			setTitle(MSG36);
			editable = false;
			examples[0] = "C(Cl)(CCC)(CCC)CCC";
			examples[1] = "[Si](Cl)(CCC)(CCC)CCC";					
			
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}
}


