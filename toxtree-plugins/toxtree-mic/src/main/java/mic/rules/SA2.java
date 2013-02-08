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

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.StructureAlertCDK;
import ambit2.core.data.MoleculeTools;
import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.SMARTSException;

/**
 * SA2
 * 
 * @author nina
 * 
 */
public class SA2 extends StructureAlertCDK {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8584645139767927034L;
	/*
	 * public static String SA2_sulphonic =
	 * "S([!$([OH1,SH1])])(=O)(=O)O([C&H3,$([CH2][CH3]),$([CH2][CH2][CH3]),$([CH]([CH3])[CH3]),$([CH2][CH2][CH2][CH3]),$([CH2][CH]([CH3])[CH3]),$([C]([CH3])([CH3])[CH3]),$([CH]([CH3])[CH2][CH3]),$([CH2]c1ccccc1)])"
	 * ; public static String SA2_phosphonic =
	 * "P(=O)([!$([OH1,SH1])])(O([C&H3,$([CH2][CH3]),$([CH2][CH2][CH3]),$([CH]([CH3])[CH3]),$([CH2][CH2][CH2][CH3]),$([CH2][CH]([CH3])[CH3]),$([C]([CH3])([CH3])[CH3]),$([CH]([CH3])[CH2][CH3]),$([CH2]c1ccccc1)]))O([C&H3,$([CH2][CH3]),$([CH2][CH2][CH3]),$([CH]([CH3])[CH3]),$([CH2][CH2][CH2][CH3]),$([CH2][CH]([CH3])[CH3]),$([C]([CH3])([CH3])[CH3]),$([CH]([CH3])[CH2][CH3]),$([CH2]c1ccccc1)])"
	 * ; public static String SA2_sulphonic =
	 * "S([!$([OH1,SH1])])(=O)(=O)O([C&H3,$([CH2][CH3]),$([CH2][CH2][CH3]),$([CH]([CH3])[CH3]),$([CH2][CH2][CH2][CH3]),$([CH2][CH]([CH3])[CH3]),$([C]([CH3])([CH3])[CH3]),$([CH]([CH3])[CH2][CH3]),$([CH2]c1ccccc1)])"
	 * ; public static String SA2_phosphonic =
	 * "P(=O)([!$([OH1,SH1])])(O([C&H3,$([CH2][CH3]),$([CH2][CH2][CH3]),$([CH]([CH3])[CH3]),$([CH2][CH2][CH2][CH3]),$([CH2][CH]([CH3])[CH3]),$([C]([CH3])([CH3])[CH3]),$([CH]([CH3])[CH2][CH3]),$([CH2]c1ccccc1)]))O([C&H3,$([CH2][CH3]),$([CH2][CH2][CH3]),$([CH]([CH3])[CH3]),$([CH2][CH2][CH2][CH3]),$([CH2][CH]([CH3])[CH3]),$([C]([CH3])([CH3])[CH3]),$([CH]([CH3])[CH2][CH3]),$([CH2]c1ccccc1)])"
	 * ;
	 */
	public static String SA2_sulphonic = "S(=O)(=O)O";
	public static String SA2_phosphonic = "P(=O)(O)(O)";
	protected ISmartsPattern<IAtomContainer> prescreenSulphonic;
	protected ISmartsPattern<IAtomContainer> prescreenPhosphonic;

	public static String[][] substituents = {
			// {"methyl","[C&H3]"},
			{ "methyl-Hal", "C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])" },
			// {"propyl","[CH2][CH3]"},
			{
					"propyl",
					"C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])" },
			// {"isopropyl","[CH2][CH2][CH3]"},
			{
					"isopropyl",
					"C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])" },
			// {"isopropyl-1","[CH]([CH3])[CH3]"},
			{
					"isopropyl-1",
					"C([#1,Cl,Br,I,F])(C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])([#1,Cl,Br,I,F]))C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])" },
			// {"butyl-2","[CH2][CH2][CH2][CH3]"},
			{
					"butyl-2",
					"C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])" },
			// {"butyl-3","[CH2][CH]([CH3])[CH3]"},
			{
					"butyl-3",
					"C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])C([#1,Cl,Br,I,F])(C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])([#1,Cl,Br,I,F]))C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])" },
			// {"butyl-4","[C]([CH3])([CH3])[CH3]"},
			{
					"butyl-4",
					"C(C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])([#1,Cl,Br,I,F]))(C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])([#1,Cl,Br,I,F]))C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])" },
			// {"butyl-5","[CH]([CH3])[CH2][CH3]"},
			{
					"butyl-5",
					"C([#1,Cl,Br,I,F])(C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])([#1,Cl,Br,I,F]))C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])C([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])([#1,Cl,Br,I,F])" },
			{ "benzyl", "[CH2]c1ccccc1" } };

	/**
	 * Alkyl (C<5) or benzyl ester of sulphonic or phosphonic acid.
	 */
	@SuppressWarnings("unchecked")
	public SA2() {
		try {
			StringBuffer sulphonic = new StringBuffer();
			StringBuffer phosphonic = new StringBuffer();
			StringBuffer s = new StringBuffer();

			for (int i = 0; i < substituents.length; i++) {
				if (i > 0)
					s.append(",");
				s.append("$(");
				s.append(substituents[i][1]);
				s.append(")");
			}

			sulphonic.append("S([!$([OH1,SH1])])(=O)(=O)O([");
			sulphonic.append(s);
			sulphonic.append("])");

			phosphonic.append("P(=O)([!$([OH1,SH1])])(O([");
			phosphonic.append(s);
			phosphonic.append("]))");
			phosphonic.append("O([");
			phosphonic.append(s);
			phosphonic.append("])");

			addSubstructure("Alkyl ester of sulphonic acid", sulphonic
					.toString());
			addSubstructure("Alkyl ester of phosphonic acid", phosphonic
					.toString());

			prescreenSulphonic = createSmartsPattern(SA2_sulphonic, false);
			prescreenPhosphonic = createSmartsPattern(SA2_phosphonic, false);

			/*
			 * addSubstructure("Alkyl ester of sulphonic acid", SA2_sulphonic );
			 * addSubstructure("Alkyl ester of phosphonic acid",SA2_phosphonic
			 * );
			 */
			setID("SA2");
			setTitle("Alkyl (C<5) or benzyl ester of sulphonic or phosphonic acid");
			setExplanation("Methyl, ethyl, propyl, butyl or benzyl esters of sulphonic or phosphonic acid. <br>P(=O)(O)(O)R or S(=O)(O)(O)R where R is not S or O <br> The alkyl chains can have halogen substituents.");

			examples[0] = "CCC(C)OP(O)(=O)OC(C)CCC"; // "CCC(C)OP(C)(=O)OC(C)CCC";
			examples[1] = "CCC(C)OP(C)(=O)OC(C)CC";
			editable = false;

		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

	/**
	 * Simple prescreening
	 * 
	 * @return true if possibly a hit
	 */
	protected boolean isAPossibleHit(IAtomContainer mol,
			IAtomContainer processedObject) throws DecisionMethodException {
	    IMolecularFormula formula = MolecularFormulaManipulator.getMolecularFormula(mol);

		@SuppressWarnings("unused")
		boolean ok = false;
		try {
			if (MolecularFormulaManipulator.containsElement(formula,MoleculeTools.newElement(formula.getBuilder(),"O"))) {
				if (MolecularFormulaManipulator.containsElement(formula,MoleculeTools.newElement(formula.getBuilder(),"S")))
					return prescreenSulphonic.match(mol) > 0;
				if (MolecularFormulaManipulator.containsElement(formula,MoleculeTools.newElement(formula.getBuilder(),"P")))
					return prescreenPhosphonic.match(mol) > 0;
			}
  		} catch (SMARTSException x) {
			throw new DecisionMethodException(x);
		}
		return false;
	}
}
