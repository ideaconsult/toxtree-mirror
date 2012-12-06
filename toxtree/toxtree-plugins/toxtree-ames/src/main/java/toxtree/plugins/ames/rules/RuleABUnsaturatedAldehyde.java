/*
Copyright Ideaconsult Ltd. (C) 2005-2012 

Contact: jeliazkova.nina@gmail.com

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

package toxtree.plugins.ames.rules;

import toxTree.tree.rules.smarts.RuleSMARTSubstructureCDK;
import ambit2.smarts.query.SMARTSException;

/**
 * Hits alfa-beta unsaturated aldehydes, exclude cyclic alfa-beta unsaturated aldehydes.
 * Used to select chemicals for {@link RuleDAMutagenicityABUnsaturatedAldehydes}. 
 * @author nina
 *
 */
public class RuleABUnsaturatedAldehyde extends RuleSMARTSubstructureCDK {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3648165533151563575L;
	protected static String abunsaturated_aldehyde="\u03B1,\u03B2 unsaturated aldehyde"; 

	public RuleABUnsaturatedAldehyde() {
			try {
				super.initSingleSMARTS(super.smartsPatterns,abunsaturated_aldehyde,"[CX3H1](=O)[#6;!R]=[!R]");
				setID("QSAR13 applicable?");
				setTitle(abunsaturated_aldehyde);
                StringBuffer b = new StringBuffer();
				b.append(abunsaturated_aldehyde);
                b.append(", excluding cyclic ");
                b.append(abunsaturated_aldehyde);
                b.append("<br>");
                b.append("For the QSAR calculation of \u03B1,\u03B2 unsaturated aldehydes, molecules that contain another separate alerting group should be excluded."); 
                b.append("This holds true except in the following cases: if the \u03B1,\u03B2 unsaturated aldehyde contains a partial overlap with SA4_gen or SA24_gen, the molecule should be considered for QSAR calculation.");                
                setExplanation(b.toString());
				examples[0] = "O=CC=1CCCCC=1";
				examples[1] = "CCC=CC=O";	
			} catch (SMARTSException x) {
				logger.error(x);
			}
	}

}


