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

public class SA7_gen extends StructureAlert {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3847918414478370174L;

	public SA7_gen() {
		try {
			super.initSingleSMARTS(super.smartsPatterns,"epoxides and aziridines", "C1[O,N]C1");
			setID("SA7_gen");
			setTitle("Epoxides and aziridines");
			setExplanation("epoxides and aziridines");
			
			examples[0] = "CCCO";
			examples[1] = "CC1CO1";	
			editable = false;
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}	
	}

}


