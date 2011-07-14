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
package verhaar.rules;


import java.util.List;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IElement;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolFlags;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;

/**
 * 
 * Primary alkyl amines (containing only C,H,N).
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 * <b>Modified</b> July 12, 2011
 */
public class Rule24 extends RuleSMARTSSubstructureAmbit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5243019325725208478L;
	protected String[][] smarts = {
			{"Primary amine","A[NX3;H2]"} 
	};		
	
	public Rule24() {
		super();
		setTitle("Be primary alkyl amines (containing only C,H,N)");
		id = "2.4";

		for (String[] smart: smarts) try {
			addSubstructure(smart[0],smart[1]);
		} catch (Exception x) {
			x.printStackTrace();
		}
		
		setExplanation("Be primary alkyl amines (containing only C,H,N)");
		examples[0]= "OC(C)CCNC(C)CC";
		examples[1] = "CCCCCN";
		editable = false;
	}
	
	@Override
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		return verifyRule(mol, null);
	}
	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected) throws DecisionMethodException {

		logger.info(toString());
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf ==null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);

	    IMolecularFormula mfa = MolecularFormulaManipulator.getMolecularFormula(mol);
	    List<IElement> elements = MolecularFormulaManipulator.getHeavyElements(mfa);
	    for (IElement element:elements) 
	    	if ("C".equals(element.getSymbol()) || "N".equals(element.getSymbol())) { //ok 
	    	} else return false; //not only C & N 
	    if (mf.isAliphatic()) 
	    	return super.verifyRule(mol,selected);
	    else return false;
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleOnlyAllowedSubstructures#isImplemented()
	 */
	public boolean isImplemented() {

		return true;
	}
}
