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

public class Rule35 extends RuleSMARTSubstructureCDK  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1584909571294850146L;
	public static final String MSG35 = "Aliphatic iso(thio)cyanates";

	public Rule35() {
		
		super();		
		try {
			/*
			addSubstructure("[C!c][CH2]N=C=O");
			addSubstructure("[C!c][CH2]N=C=S");
			*/
			addSubstructure("[C][CH2]N=C=[S,O]");
			
			setID("35");
			setTitle(MSG35);
			editable = false;
			examples[0] = "O=C=C/CCC";
			examples[1] = "O=C=N/CCC";					
			
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}
}


