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

import toxTree.tree.rules.StructureAlertCDK;
import ambit2.smarts.query.SMARTSException;

public class SA53_nogen extends StructureAlertCDK {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8723199576199214305L;

	public SA53_nogen() {
		super();
		try {
			addSubstructure("c1cc(N)ccc1S(=O)(=O)N");
			addSubstructure("c1cc(S)ccc1S(=O)(=O)N");
			addSubstructure("c1ccccc1S(=O)(=O)[N;-1]");
			addSubstructure("c1cc(C)ccc1S(=O)(=O)O");
			//addSubstructure("c1(O)cc(C)ccc1S(=O)(=O)O");
			setID("SA53_nogen");
			setTitle("Benzensulfonic ethers");
			setExplanation("Nongenotoxic mechanism");
		
	
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}

}


