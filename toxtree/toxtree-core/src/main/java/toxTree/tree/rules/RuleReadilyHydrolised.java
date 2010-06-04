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

*/
package toxTree.tree.rules;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;

import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.ReactionException;
import toxTree.query.MolAnalyser;
import toxTree.query.MolFlags;
import toxTree.query.SimpleReactions;
import toxTree.tree.AbstractRule;
import toxTree.tree.AbstractRuleHilightHits;

/**
 * Verifies if a compound is readily hydrolised
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-8-18
 */
public class RuleReadilyHydrolised extends AbstractRuleHilightHits {
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -4332600573329605106L;

	/**
	 * 
	 */
	public RuleReadilyHydrolised() {
		super();

	}
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractRule#isImplemented()
	 */
	@Override
	public boolean isImplemented() {
		return true;
	}

	/**
	 * Returns true if the compound is readily hydrolised
	 * Sets {@link MolFlags} hydrolysis products
	 * (to be used further )
	 */
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		logger.info(toString());
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf == null) {
	    	try { MolAnalyser.analyse(mol); } catch (Exception x) {}
	    	mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    	if (mf == null)  	throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
	    }
	    IAtomContainerSet sc = mf.getHydrolysisProducts();
	    if (sc != null) return true;
		SimpleReactions sr = new SimpleReactions();
		try {
			sc = sr.isReadilyHydrolised(mol);
		} catch (ReactionException x) {
			throw new DecisionMethodException(x);
		}
		mf.setHydrolysisProducts(sc);
		return (sc != null);
	}
	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		boolean ok = verifyRule(mol);
		MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
		if (mf.getHydrolysisProducts()!= null)
			for (int i=0; i < mf.getHydrolysisProducts().getAtomContainerCount();i++) {
				IAtomContainer ac = mf.getHydrolysisProducts().getAtomContainer(i);
				for (int b=0; b < ac.getAtomCount(); b++) 
					selected.addAtom(ac.getAtom(b));
				/*
				for (int b=0; b < ac.getBondCount(); b++) {
					
					if ("H".equals(ac.getBond(b).getAtom(0).getSymbol())) continue;
					if ("H".equals(ac.getBond(b).getAtom(1).getSymbol())) continue;
					
					//selected.addBond(ac.getBond(b));
					selected.addAtom(ac.getBond(b).getAtom(0));
					selected.addAtom(ac.getBond(b).getAtom(1));
				}
				*/
			}
			//	selected.add(mf.getHydrolysisProducts().getAtomContainer(i));
		return ok;
	}
	
}
