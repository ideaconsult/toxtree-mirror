/*
Copyright Istituto Superiore di Sanita' 2009

Contact: rbenigni@iss.it, olga.tcheremenskaia@iss.it, cecilia.bossa@iss.it

ToxMic (Structure Alerts for the in vivo micronucleus assay in rodents) 
ToxMic plug-in is a modified version of the Benigni / Bossa  Toxtree plug-in  for mutagenicity and carcinogenicity implemented by Ideaconsult Ltd. (C) 2005-2008   
Author: Istituto Superiore di Sanita'


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

package mic.rules;

import java.util.logging.Level;

import toxTree.tree.rules.StructureAlert;
import ambit2.smarts.query.SMARTSException;

public class SA30 extends StructureAlert {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1901197961906586765L;
	public static String SA30_title = "Coumarins and Furocoumarins";

	public SA30() {
		super();
		try {
			setContainsAllSubstructures(false);

			addSubstructure("1", "O=c1ccc2ccccc2(o1)");

			setID("SA30");
			setTitle(SA30_title);

			setExplanation(SA30_title);

			examples[0] = "C=1C=COC=1";
			examples[1] = "O=C2C=CC3=CC=C1OC=CC1=C3(O2)";
			editable = false;
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
	}
}
