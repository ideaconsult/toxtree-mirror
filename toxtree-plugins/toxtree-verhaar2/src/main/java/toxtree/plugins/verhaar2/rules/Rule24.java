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
import toxTree.query.FunctionalGroups;
import toxTree.query.MolFlags;
import toxTree.tree.rules.RuleOnlyAllowedSubstructures;

/**
 * 
 * Primary alkyl amines (containing only C,H,N).
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule24 extends RuleOnlyAllowedSubstructures {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5243019325725208478L;

	public Rule24() {
		super();
		setTitle("Be primary alkyl amines (containing only C,H,N)");
		id = "2.4";
		addSubstructure(FunctionalGroups.primaryAmine(true));
		setExplanation("Be primary alkyl amines (containing only C,H,N)");
		examples[0]= "OC(C)CCNC(C)CC";
		examples[1] = "CC(C)CCN";
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
