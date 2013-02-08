/*
Copyright Ideaconsult Ltd. (C) 2005-2007  

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

package mutant.rules;

import java.util.logging.Level;

import toxTree.tree.rules.StructureAlert;
import ambit2.smarts.query.SMARTSException;

public class SA3_gen extends StructureAlert {

	/*
	 * SA8_gen
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3604542364984702836L;

	public SA3_gen() {

		try {
			super.initSingleSMARTS(super.smartsPatterns,"N-methylol derivatives", "[CX4H2](N)([OX2H1])");
			setID("SA3_gen");
			setTitle("N-methylol derivatives");
			setExplanation("N-methylol derivatives");
			
			examples[0] = "CC(C)(N)O";
			examples[1] = "C=CC(=O)NCO";	
			editable = false;
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}		
	}

}


