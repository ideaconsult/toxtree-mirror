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

import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import ambit2.smarts.query.SMARTSException;

public class Rule37 extends RuleSMARTSubstructureCDK {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2025514044404501953L;
	public static final String MSG37 = "Mixed oxycarboxysilanes";

	public Rule37() {
		
		super();		
		try {
			addSubstructure("[CH3][Si]([CH3])([OX2](*))[OX2]C(=O)(*)");
			setID("37");
			setTitle(MSG37);
			editable = false;
			examples[0] = "C(C)(C)(OC=C)OC(=O)Cc1ccccc1";
			examples[1] = "[Si](C)(C)(OC=C)OC(=O)Cc1ccccc1";					
			
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
	}
}


