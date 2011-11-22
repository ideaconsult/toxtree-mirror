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

import toxTree.tree.rules.StructureAlertAmbit;
import toxTree.tree.rules.StructureAlertCDK;
import ambit2.smarts.query.SMARTSException;

/**
 * Aliphatic and aromatic aldehydes. The \u03B1,\u03B2-unsaturated aldehydes are excluded.
 * @author nina
 *
 */

public class SA11 extends StructureAlertCDK {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -248350312392283257L;

	public SA11() {
		try {
			setContainsAllSubstructures(true);
			//addSubstructure("Simple aldehyde","[#6][$([CX3H1]);!$(C[#6]=*)](=O)");
			addSubstructure("Simple aldehyde","[#6][$([CX3H1]);!$(CC=C)](=O)");
			/**
			 * https://sourceforge.net/tracker/?func=detail&aid=3138569&group_id=152702&atid=785126
			 * could be resolved by
			 * setting 
			 * setContainsAllSubstructures(false);
			 * and adding
			 * addSubstructure("Formaldehyde", "[CX3H2]=O"); 
			 */
			
			//addSubstructure("Simple aldehyde", "[#6][CX3H1]=O");
			//addSubstructure("a,b- unsaturated aldehydes","[CX3H1](=O)[#6]=*",true);
			
/*
In order to avoid possible overlap with NA_10, we can perhaps use the following SMARTS:
 [#6][$([CX3H1]);!$(C(=O)C=C)](=O)

 */			
			setID("SA11");
			setTitle("Simple aldehyde");
			setExplanation("Aliphatic and aromatic aldehydes. The \u03B1,\u03B2-unsaturated aldehydes are excluded");
			
			examples[0] = "[H]OC(=O)C=C";
			examples[1] = "CC=O";	

		} catch (SMARTSException x) {
			logger.error(x);
		}	
	}

}

