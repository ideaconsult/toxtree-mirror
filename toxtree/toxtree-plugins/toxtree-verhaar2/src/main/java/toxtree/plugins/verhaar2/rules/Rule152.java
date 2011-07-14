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
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.plugins.verhaar2.rules.helper.RuleOnlyAllowedSubstructuresCounter;
import ambit2.smarts.SmartsManager;
import ambit2.smarts.query.SMARTSException;
import ambit2.smarts.query.SmartsPatternAmbit;

/**
 * 
 * Aliphatic alcohols but not allylic/propargylic alcohols.
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 * <b>Modified</b> July 12, 2011
 */
public class Rule152 extends RuleSMARTSSubstructureAmbit {
	protected transient SmartsPatternAmbit allyl = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 8890813695847536800L;
	static final String TITLE="Be aliphatic alcohols but not allylic/propargylic alcohols";
	protected Object[][] smarts = {
			{TITLE,
				"[OH1]C[!$(C=C);!$(C#C)]"
				,Boolean.TRUE},
	};		
	
	public Rule152() {
		super();
		id = "1.5.2";
		setTitle(TITLE);
		for (Object[] smart: smarts) try { 
			addSubstructure(smart[0].toString(),smart[1].toString(),!(Boolean) smart[2]);
		} catch (Exception x) {}
		examples[0] = "C#CCO"; //propargyl alcohol ;  H2C=CH-CH2OH  - allyl alcohol   
		examples[1] = "CCCCC(O)CC";
	
		editable = false;
	}
	@Override
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		return verifyRule(mol,null);
	}
	protected boolean isAllyl(IAtomContainer mol) throws SMARTSException {
		if (allyl==null) allyl = new SmartsPatternAmbit("C=C");
		return allyl.match(mol)>0;
	}
	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected) throws DecisionMethodException {
		logger.info(toString());
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf ==null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
	    try {
		    if (mf.isAliphatic())  {
		    	logger.debug("Aliphatic\tYES");
				if (super.verifyRule(mol,selected)) {
					if (mf.isAcetylenic()) {
						logger.debug("Propargylic alcohol\tYES");
						return false;
					} else if (isAllyl(mol)) {
						logger.debug("Allylic alcohol\tYES");
						return false;
					} else return true;
				} else {
					logger.debug("Alcohol\tNO");
					return false;
				}
		    } else {
				logger.debug("Aliphatic\tNO");
		    	return false; 
		    }
	    } catch (DecisionMethodException x) {
	    	throw x;
	    } catch (Exception x) {
	    	throw new DecisionMethodException(x);
	    }
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleOnlyAllowedSubstructures#isImplemented()
	 */
	public boolean isImplemented() {
		return true;
	}

}
