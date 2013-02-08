/*
Copyright Ideaconsult Ltd. & Curios-IT (C) 2006-2008
Contact: nina@acad.bg, kazius@Curios-IT.com

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
package cramer2.rules;

import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;

/**
 * free a,b-unsaturates heteroatoms (allyl amine), where free implies that the b carbon has 2 H's.
 * <b>Modified</b> Dec, 2008
 */
public class RuleFreeABUnsaturatedHetero extends RuleSMARTSSubstructureAmbit{//jeroen
	private static final long serialVersionUID = 0;
	public RuleFreeABUnsaturatedHetero() {
		//TODO fix sterically hindered condition (example NO fails)
		super();
		id = "44";
		title = "Free a,b-unsaturated heteroatom...";
		explanation = new StringBuffer();
		explanation.append("<html>Does the compound contain a free a,b-unsaturated functional group?<br> The functional group consists of a carbon with an attached heteroatom (O in case of an alcohol or ester). Here, 'free' means that position beta from this group contains an sp2 [CH2] or an sp1 [CH].</html>");

		examples[0] = "C=CCC(=O)N";//  no hit (X,0,X,0)
		examples[1] = "C=CCOC(=O)C";//hit (0,X,0,X)
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1",
                    "[$(C!-*)!$(C(!-*)~*)]!-CC~[O,N]");
			editable = false;
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
        logger.finer("44 finished");
	}
}
