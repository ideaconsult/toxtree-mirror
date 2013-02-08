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

import toxTree.tree.rules.StructureAlertCDK;
import ambit2.smarts.query.SMARTSException;

public class SA13 extends StructureAlertCDK {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	protected static String HYDRAZINE = "Hydrazine";
	protected static String HYDRAZINE_SMARTS = "[N+0]!@;-[N+0](=[!O;!N])";

	public SA13() {
		super();
		try {
			setContainsAllSubstructures(false);
			/**
			 * + is not working in Joelib SMARTS implementation , for some
			 * reason
			 */
			// [#1,!#8]~[N;$([N!X4])]([#1,!#8])!@;-[N;$([N!X4])]~[#1,!#8]
			// addSubstructure("Hydrazine",
			// "[N;$([N!X4])]([#1,!#8])([#1,!#8])!@;-[N;$([N!X4])]~[#1,!#8]");
			addSubstructure("Hydrazine not N-N=[O,N]", HYDRAZINE_SMARTS);
			addSubstructure(HYDRAZINE, "[N+0]([#1,*])!@;-[N+0]([#1,*])");

			setID("SA13");
			setTitle(HYDRAZINE);
			setExplanation("This applies to molecules that contain a NN group not in a ring, and not NN=O.<br>Chemicals fired by alert <a href=\"#SA22\">SA22</a> should be excluded from this alert.<br>Chemicals with a quaternary protonated nitrogen, should be excluded.");

			examples[0] = "CN(C)N=NC=1NC=NC=1(C(N)=O)"; // "C1CCNNC1"
			examples[1] = "NNCNN=O";
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
	}

}
