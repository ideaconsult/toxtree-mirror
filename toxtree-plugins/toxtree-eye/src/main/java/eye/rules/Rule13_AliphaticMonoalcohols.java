/*
Copyright Ideaconsult Ltd.(C) 2006  
Contact: nina@acad.bg

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
package eye.rules;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import net.idea.modbcum.i.processors.IProcessor;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.renderer.selection.IChemObjectSelection;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
import ambit2.smarts.query.SmartsPatternCDK;


/**
 * A Lactams
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule13_AliphaticMonoalcohols extends AbstractRule {
	protected SmartsPatternCDK smartsPattern;
	/**
	 * 
	 */
	private static final long serialVersionUID = -93444610429767862L;

	public Rule13_AliphaticMonoalcohols() {
		//TODO fix sterically hindered condition (example NO fails)
		super();		
		
		try {
			smartsPattern = new SmartsPatternCDK("C([#1,C])([#1,C])([C])([OX2H])");
			id = "13";
			title = "Aliphatic Monoalcohols";
			
			examples[0] = "C(O)(C)(C)(CCCCCCCCCCC)";
			examples[1] = "C(O)(CCC)(CCCCC)";	
			editable = false;		
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}

	}

	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		try {
			logger.finer(getID());
			int r = smartsPattern.hasSMARTSPattern(mol);
			if (r == 0) return false;
			IAtomContainerSet chains = extractChains(mol, smartsPattern.getUniqueMatchingAtoms(mol));
			
			int longchains = 0;
			int allchains = 0;
			for (int i=0; i < chains.getAtomContainerCount(); i++) 
				try {
					int count = countChain(chains.getAtomContainer(i));
					if (count == 0) continue;
					allchains++;
					if ((count >=getMinChainLength()) && (count <=getMaxChainLength()))
						longchains++;
				} catch (DecisionMethodException x) {
					logger.log(Level.WARNING,x.getMessage(),x);
					return false;
				}
			return longchains==allchains;
    	} catch (SMARTSException x) {
    		throw new DecisionMethodException(x);
    	} catch (DecisionMethodException x) {
    		throw x;
    	}
	}
	protected int countChain(IAtomContainer m) throws DecisionMethodException {
		int count = 0;
		for (int j=0; j < m.getAtomCount();j++) {
			if (m.getAtom(j).getFlag(CDKConstants.ISAROMATIC)) {
				throw new DecisionMethodException("Aromatic atom found");
			}			
			if ("C".equals(m.getAtom(j).getSymbol())) {
				count ++;
			} else if ("H".equals(m.getAtom(j).getSymbol())) continue;
			else 
				throw new DecisionMethodException("Non C atom found");
		}
		//TODO check if 2 atoms only
		if (count > 0) {
			logger.fine("Chain length "+(count+2));
			count = count+2; //3-11 chain length, but the first C atom is removed since it is marked by SMARTS matching
			return count;

		} else return 0;	
		 
	}
	protected int getMinChainLength() {
		return 3;
	}
	protected int getMaxChainLength() {
		return 11;
	}
	protected IAtomContainerSet extractChains(IAtomContainer mol, List<List<Integer>> matchedAtoms) throws DecisionMethodException {

		try {
			IAtomContainer newmol = (IAtomContainer) mol.clone();
			List<IAtom> atoms = new ArrayList<IAtom>();
			for (List<Integer> l : matchedAtoms) {
				for (Integer i : l) 
					atoms.add(newmol.getAtom(i.intValue()));
			}
			for (IAtom atom : atoms)
				newmol.removeAtomAndConnectedElectronContainers(atom);
			
			return ConnectivityChecker.partitionIntoMolecules(newmol);
		} catch (CloneNotSupportedException x) {
			throw new DecisionMethodException(x);
		}
		
	}
	@Override
	public boolean isImplemented() {
		return true;
	}
	
    @Override
    public IProcessor<IAtomContainer, IChemObjectSelection> getSelector() {
    	RuleSMARTSSubstructureAmbit rule = new RuleSMARTSSubstructureAmbit();
    	try { rule.addSubstructure("C([#1,C])([#1,C])([C])([OX2H])"); } catch (Exception x) {x.printStackTrace();};
    	return rule.getSelector();
    }
}