/*
Copyright Ideaconsult Ltd. (C) 2005-2007 

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*//**
 * Created on 2005-5-2

 * @author Nina Jeliazkova nina@acad.bg
 *
 * Project : toxtree
 * Package : toxTree.tree.rules
 * Filename: RuleCommonterpene.java
 */
package toxTree.tree.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.query.MolFlags;
import toxTree.tree.AbstractRule;
import toxTree.tree.AbstractRuleHilightHits;

/**
 * Verifies if the molecule is a common terpene
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class RuleCommonTerpene extends AbstractRuleHilightHits {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 6594005948854719886L;

    /**
	 * Constructor
	 * 
	 */
	public RuleCommonTerpene() {
		super();
		id = "[Terpene]";
		title = "Common terpene";
		explanation.append("<html>Is it a terpene?</html>");
		examples[0] = "CCCCCCCCCCCCC";
		examples[1] = "[H][C@@]1(O)CC2CCC1(C)C2(C)C";
	}
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		return verifyRule(mol, null);
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractRule#isImplemented()
	 */
	@Override
	public boolean isImplemented() {
		return true;
	}
	
	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		try {
			logger.info(getID());
		    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
		    if (mf == null) {
		    	MolAnalyser.analyse(mol);
		    	mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
		    	if (mf == null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
		    }
			boolean ok = FunctionalGroups.isCommonTerpene(mol,mf.getRingset());
			
			if (selected != null) {
				for (int i=0; i < mol.getAtomCount(); i++)
					if (mol.getAtom(i).getProperty(FunctionalGroups.ALLOCATED)!=null)
						selected.addAtom(mol.getAtom(i));
				for (int i=0; i < mol.getBondCount(); i++)
					if (mol.getBond(i).getProperty(FunctionalGroups.ALLOCATED)!=null)
						selected.addBond(mol.getBond(i));				
			}	
			
			return ok;
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
	}

}
