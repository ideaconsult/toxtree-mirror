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

import toxTree.tree.rules.StructureAlertCDK;
import ambit2.smarts.query.SMARTSException;

public class SA4 extends StructureAlertCDK {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5946639009469464169L;
	public static String SA4_title = "Monohaloalkene";
	public static String SA4_smarts = "[CX3]([!Cl;!Br;!F;!I;!$(C=O)])(!@[#1,CX4])=[CX3]([Cl,F,Br,I])([#1,CX4])";

	/**
	 * The SMARTS
	 * [CX3]([!Cl;!Br;!F;!I;#1,CX4])(!@[#1,CX4])=[CX3]([Cl,F,Br,I])([#1,CX4])
	 * excludes the possibility to have substituents different from H or CX4.
	 * Instead only the C=O substituent should be excluded:
	 */
	// ;
	public SA4() {
		try {
			addSubstructure(SA4_title, SA4_smarts);

			setID("SA4");
			setTitle(SA4_title);
			setExplanation("This alert contains halogenated olefins where at least one hydrogen or alkyl group is attached to each carbon atom.");

			examples[0] = "CC(=O)C=CBr"; // "O=CC(=C(C(=O)O)Cl)Cl"; //iss 843
			examples[1] = "C=CCl";
			editable = false;
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}

}
