/*
Copyright Nina Jeliazkova (C) 2005-2011  
Contact: jeliazkova.nina@gmail.com

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
package toxtree.plugins.verhaar2.rules;


import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxtree.plugins.verhaar2.rules.helper.RuleElementsCounter;

/**
 * 
 * Does not contain I.
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 * <b>Modified</b> July 12, 2011
 */
public class Rule11 extends RuleElementsCounter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1198975584661041102L;

	public Rule11() {
		super();
		setHalogens(new String[] {"Cl","Br","F"});
		setComparisonMode(modeAnySpecifiedElements);
		addElement("I");
		id = "1.1";
		setTitle("Not contain I");
		explanation = new StringBuffer();
		explanation.append("Organic compounds containing covalently bound F are to be considered equivalent with the H-analogues; but please");
		explanation.append(" note that F-compounds are non-metabolizable if F substitutes for metabolically important H-atoms. This can give rise to");
		explanation.append(" chronic specific toxicity. Compounds containing CI or Br atoms should not be activating these halogens or be activated by them");
		explanation.append(" Activated C1 or Br can be found in e.g. allylic/propargylic halogenides, activating CI or Br can be found in e.g. trichloroethanol or");
		explanation.append(" pentachlorophenol");
		examples[0] = "CCCCC(I)C";
		examples[1] = "CCCCCCCCCC";
		editable = false;

	}
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		return !super.verifyRule(mol);
	}

	public void select(IAtomContainer mol, IAtomContainer selected) {
		if (selected != null) elements.select(mol, selected,false);
	}
}
