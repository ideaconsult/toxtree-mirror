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
 * benzene analogues, with 0-6 substituents that consist of at most 1 heavy atom.
 * <b>Modified</b> Dec, 2008
 */
public class RuleBenzeneAnalogues extends RuleSMARTSubstructure{//jeroen
	/**
	 * 
	 */
	private static final long serialVersionUID = -6600673583244915910L;
	
	public static final String smarts = "[$([aH1;r6]),$([aX2;r6]),$([a;r6]~A)!$(a~A~*)]1~[$([aH1]),$([aX2]),$(a~A)!$(a~A~*)]~[$([aH1]),$([aX2]),$(a~A)!$(a~A~*)]~[$([aH1]),$([aX2]),$(a~A)!$(a~A~*)]~[$([aH1]),$([aX2]),$(a~A)!$(a~A~*)]~[$([aH1]),$([aX2]),$(a~A)!$(a~A~*)]1";

	public RuleBenzeneAnalogues() {
		super();
		id = "42";
		title = "Possibly harmful analogue of benzene...";
		explanation = new StringBuffer();
		explanation.append("<html>Does the compound consist of one aromatic ring, with at most one heavy atom connected to each aromatic atom?</html>");

		examples[0] = "c1ccccc1C(=O)N";//  no hit (X,0,X,0)
		examples[1] = "c1ccccc1O";//hit (0,X,0,X)
		try {
			super.initSingleSMARTS(super.smartsPatterns,"1", smarts);
                    
                    //"[$( )" + ",$([$([aH1;r5]),$([aX2;r5]),$([a;r5]~A)!$(a~A~*)]1~[$([aH1]),$([aX2]),$(a~A)!$(a~A~*)]~[$([aH1]),$([aX2]),$(a~A)!$(a~A~*)]~[$([aH1]),$([aX2]),$(a~A)!$(a~A~*)]~[$([aH1]),$([aX2]),$(a~A)!$(a~A~*)]~1)]");
			editable = false;
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,smarts,x);
		}
        logger.finer("42 finished");
	}
}
