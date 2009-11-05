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

import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;

/**
 * Uncharged Organophosph(othion)ates
 * <b>Modified</b> Dec, 2008
 */
public class RuleUnchargedOrganophosphates extends  RuleSMARTSSubstructureAmbit{//jeroen
	private static final long serialVersionUID = 0;
	public RuleUnchargedOrganophosphates() {
		//TODO fix sterically hindered condition (example NO fails)
		super();
		id = "40";
		title = "Possibly harmful organophosphate or organophosphothionate...";
		explanation = new StringBuffer();
		explanation.append("<html>Is any element not listed in Q3 an uncharged organophosphate?</html>");

		examples[0] = "OP(=O)(O)CC(O)C(=O)O";//  no hit (X,0,X,0)
		examples[1] = "COP(=O)(OC)OC(C)=CC(=O)NC";//hit (0,X,0,X)
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1","[$(P)!$([P;R0]([O-,OH])(=O)([$([O-,OH]),$(O(P)[C,P])])[$([O-,OH]),$(O(P)[C,P])])]");
                    //"[Cr]");//anything goes to Q41, nothing goes High
                    //"[$([P;R0]O)!$(P([O-,OH])(=O)([$([O-,OH]),$(O(P)[C,P])])[$([O-,OH]),$(O(P)[C,P])])]");
			editable = false;
		} catch (SMARTSException x) {
			logger.error(x);
		}
        logger.debug("40 finished");
	}
}
