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
 * Created on 2005-5-3

 * @author Nina Jeliazkova nina@acad.bg
 *
 * Project : toxtree
 * Package : toxTree.tree.rules
 * Filename: RuleRingComplexSubstituents.java
 */
package toxTree.tree.cramer;


import java.util.logging.Level;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.ringsearch.SSSRFinder;

import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.MolAnalyseException;
import toxTree.exceptions.ReactionException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.query.MolFlags;
import toxTree.query.SimpleReactions;
import toxTree.tree.rules.RuleRingOtherThanAllowedSubstituents;

/**
 * Rule 30 of the Cramer scheme (see {@link toxTree.tree.cramer.CramerRules})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-3
 */
public class RuleRingComplexSubstituents30 extends RuleRingOtherThanAllowedSubstituents {
	protected QueryAtomContainer ester = null;
	
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -8727429477067911673L;

    /**
	 * Constructor
	 * 
	 */
	public RuleRingComplexSubstituents30() {
		super();
		setAnalyzeAromatic(true);
		setAnalyzeOnlyRingsWithFlagSet(true);
		addSubstructure(FunctionalGroups.hydroxy_ring());
		addSubstructure(FunctionalGroups.methoxy_ring());
		addSubstructure(FunctionalGroups.alcohol(false));
		addSubstructure(FunctionalGroups.ketone());
		addSubstructure(FunctionalGroups.aldehyde());
		addSubstructure(FunctionalGroups.carboxylicAcid());
		ester = FunctionalGroups.ester();
		addSubstructure(ester);
		
		id = "30";
		title = "Aromatic Ring with complex substituents";
		explanation.append("<html>");
		explanation.append("Disregarding ring hydroxy or methoxy does the ring bear substituents <i>other</i> than 1-5-carbon <i>aliphatic</i> (A) groups,");
		explanation.append("either hydrocarbon or containing alcohol, ketone, aldehyde, carboxyl or simple esters that may be hydrolised to ring substituents of <=5 carbons? ");
		explanation.append("(If a simple ester that may be hydrolised, treat the aromatic portion by Q.18 and the residue by Q19.<p>");
		explanation.append("This should be answered NO if the ring bears only aliphatic groups of <=5 carbons, which are either hydrocarbon in nature or contain the groups listed.");
		explanation.append("If the ring bears any other substituents than those listed, the question should be answered YES and proceed to Q31.");
		explanation.append("</html>");
		examples[0] = "CC1=CC(=C(O)C(=C1)C(C)(C)C)C(C)(C)C";
		examples[1] = "SC1=CC=CC=C1";
		editable = false;
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleRingSubstituents#substituentIsAllowed(org.openscience.cdk.AtomContainer)
	 */
	@Override
	public boolean substituentIsAllowed(IAtomContainer  a, int[] place) throws DecisionMethodException {
		//final String cAtom = "C";
		SSSRFinder ssrf = new SSSRFinder(a);
		IRingSet rs = ssrf.findSSSR();
		boolean b =false;
		if (rs.getAtomContainerCount() > 0) {
			logger.finer(CYCLIC_SUBSTITUENT);
			b = false;
		} else {
			//MFAnalyser mf = new MFAnalyser(a);
			//int c = mf.getAtomCount(cAtom);
			int c = FunctionalGroups.getLongestCarbonChainLength(a);  //to verify if the chain is of C atoms 
			if (c>5) {
				logger.fine(LONGCHAIN_SUBSTITUENT+c);
				/*
				if (FunctionalGroups.hasGroup(a,ester,false)) {
					SimpleReactions sr = new SimpleReactions();
					try {
						//trying to hydrolize
						b = true;
						SetOfAtomContainers residues = sr.isReadilyHydrolised((IAtomContainer)a);
						canBeHydrolized = (residues != null); 
						if (residues == null) {
							logger.debug("Long chain substituent can't be hydrolized!\t - ",c);
							b = false;
						}
						else for (int i = 0; i < residues.getAtomContainerCount(); i++) {
							mf.analyseAtomContainer(residues.getAtomContainer(i));
							c = mf.getAtomCount(cAtom);
							if (c > 5) {
								b = false;
								logger.debug("Residue with > 5 carbon atoms!\t - ",c);
								break;
							}
						}
					} catch (ReactionException x) {
						throw new DecisionMethodException(x);
					}
					
				} else {
					logger.debug("Ring substituent:\tNo ester group found, will not try hydrolysis.");
					b = false;
				}
				*/
				b=false;
			} else b = true;
		}
		rs = null;
		ssrf = null;
		return b;
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleRingOtherThanAllowedSubstituents#verifyRule(org.openscience.cdk.Molecule)
	 */
	@Override
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		return verifyRule(mol, null);
	}
	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected) throws DecisionMethodException {
		logger.finer(toString());
		boolean canBeHydrolized = false;
		IAtomContainerSet residues = null;
		IAtomContainer  newMol = mol;
		if (FunctionalGroups.hasGroup(mol,ester,false)) {
			SimpleReactions sr = new SimpleReactions();
			try {
				//Ester group(s) found, trying hydrolysis ...;
				residues = sr.isReadilyHydrolisedSimpleEster(mol);
				
				 
				canBeHydrolized = (residues != null); 
				if (!canBeHydrolized) {
					logger.fine("Can't be hydrolized!");
				}
				else {
					if (residueIDHidden)
						for (int i = 0; i < residues.getAtomContainerCount(); i++)
							residues.getAtomContainer(i).setID(mol.getID());
					
					for (int i = 0; i < residues.getAtomContainerCount(); i++) 
						try {
							IAtomContainer residue = residues.getAtomContainer(i);
							MolAnalyser.analyse(residue);
						    MolFlags mf = (MolFlags) residue.getProperty(MolFlags.MOLFLAGS);
						    if (mf == null) throw new DecisionMethodException("Structure should be preprocessed!");						
							if (mf.isAromatic()) {
								logger.finer("Aromatic residue found, proceed with analysis of the residue.");
								//since here we have a single ring, this is the part we are interested in
								newMol = residue;
								break;
							}
						} catch (MolAnalyseException x) {
							logger.log(Level.SEVERE,x.getMessage(),x);
							throw new DecisionMethodException("Error when preprocessing residues!",x);
						}
					
				}
			} catch (ReactionException x) {
				throw new DecisionMethodException(x);
			}
			
		} else {
			logger.fine("Ring substituent:\tNo ester group found, will not try hydrolysis.");
		}		

		boolean result = super.verifyRule(newMol,selected);
		if (result  && (residues != null)) {
		    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
		    if (mf == null) throw new DecisionMethodException("Structure should be preprocessed!");
			mf.setResidues(residues);
		}
		return result;
	}
}
