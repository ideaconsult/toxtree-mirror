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

import toxTree.tree.rules.smarts.RuleSMARTSubstructure;
import ambit2.smarts.query.SMARTSException;

/**
 * Many kinds of divalent sulphurs, *-S-*, are synthetic and outside the scope of the Cramer dataset.
 * <b>Modified</b> Dec, 2008
 */
public class RuleDivalentSulphur extends RuleSMARTSubstructure{//jeroen
	/**
	 * 
	 */
	private static final long serialVersionUID = 8947181147979900214L;
	//SMARTS rewritten to avoid double recursion
	protected static final String smarts = "[$([S;H0;D2])!$([S;H0;D2]([CX4])[CX4])!$([S;H0;D2]([CX4])C(=O)C)!$([S;H0;D2](C(=O)C)C(=O)C)]";
	protected static final String originalsmarts = "[$([S;H0;D2])!$([S;H0;D2]([$([CX4]),$(C(=O)C)])[$([CX4]),$(C(=O)C)])]";
	public RuleDivalentSulphur() {
		//TODO fix sterically hindered condition (example NO fails)
		super();
		id = "43";
		title = "Possibly harmful divalent sulphur (not detected via Q3)...";
		explanation = new StringBuffer();
		explanation.append("<html>Does the compound a non-natural divalent sulphur?</html>");

		examples[0] = "CC(=O)SCC";//  no hit (X,0,X,0)
		examples[1] = "CCSC=NC";//hit (0,X,0,X)
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1",smarts);
			editable = false;
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,smarts,x);
		}
        logger.finer("43 finished");
	}
}
