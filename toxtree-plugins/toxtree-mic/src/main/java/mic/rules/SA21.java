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

import toxTree.tree.rules.smarts.SMARTSException;

public class SA21 extends StructureAlert {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4846012217142617415L;
	public static String SA21_title = "Alkyl and aryl N-nitroso groups";

	/**
	 * TODO Please, could you add in this alert also aryl or alkyl substances
	 * with the substructure: -N=N-O-H? Perhaps with the following MSARTS?
	 * 
	 * [C,c]N[NX2;v3]=O OR [C,c]N=N[OH1]
	 */
	public SA21() {
		super();
		try {
			addSubstructure(SA21_title, "[C,c]N[NX2;v3]=O");
			setID("SA21");
			setTitle(SA21_title);
			setExplanation(SA21_title);

			examples[0] = "CCN(CC)N(=O)O";
			examples[1] = "O=NNCCC";
			editable = false;
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}
}
