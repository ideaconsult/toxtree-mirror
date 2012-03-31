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

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.StructureAlertCDK;
import ambit2.core.data.MoleculeTools;
import ambit2.smarts.query.SMARTSException;

public class SA25 extends StructureAlertCDK {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8544406595249296835L;
	public static String SA25_title = "Aromatic nitroso group";
	public static String[][] a_nitroso = { { SA25_title, "[NX2]=O" }

	};

	public SA25() {
		super();
		try {
			setContainsAllSubstructures(false);
			// addSubstructure(SA25_title, "a[NX2]=O");

			StringBuffer b = new StringBuffer();
			b.append("[a");
			for (int i = 0; i < SA28.exclusion_rules.length; i++) {
				b.append(";!$(");
				b.append(SA28.exclusion_rules[i][1]);
				b.append(")");
			}
			b.append("]!@[");
			for (int i = 0; i < a_nitroso.length; i++) {
				if (i > 0)
					b.append(',');
				b.append("$(");
				b.append(a_nitroso[i][1]);
				b.append(")");
			}
			b.append("]");
			addSubstructure(SA25_title, b.toString());
			setID("SA25");
			setTitle(SA25_title);
			setExplanation(SA25_title);

			StringBuffer e = new StringBuffer();
			e.append(SA25_title);
			e.append("<br>");
			e.append("However, the following structures should be excluded:");
			e.append("<ul>");
			Object old = "";
			for (int i = 0; i < SA28.exclusion_rules.length; i++) {
				if (old.equals(SA28.exclusion_rules[i][0]))
					continue;
				e.append("<li>");
				e.append(SA28.exclusion_rules[i][0]);
				old = SA28.exclusion_rules[i][0];
			}
			e.append("</ul>");
			setExplanation(e.toString());
			examples[0] = "CC1=CC(=CC(C)=C1(N=O))N(C)N=O"; // ortho
															// disubstituted
			examples[1] = "O=Nc1=cc=cc=c1";
			editable = false;
		} catch (SMARTSException x) {
			logger.error(x);
		}
	}

	protected boolean isAPossibleHit(IAtomContainer mol, IAtomContainer processedObject) throws DecisionMethodException  {
		IMolecularFormula formula = MolecularFormulaManipulator.getMolecularFormula(mol);
		return 
		MolecularFormulaManipulator.containsElement(formula,MoleculeTools.newElement(formula.getBuilder(),"N")) &&
		MolecularFormulaManipulator.containsElement(formula,MoleculeTools.newElement(formula.getBuilder(),"O"));
	}   
}
