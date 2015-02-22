/*
Copyright (C) 2005-2008  

Contact: nina@acad.bg

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

package eye.rules;

import java.util.List;
import java.util.logging.Level;

import net.idea.modbcum.i.processors.IProcessor;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IBond.Order;
import org.openscience.cdk.renderer.selection.IChemObjectSelection;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;
import ambit2.smarts.query.SmartsPatternCDK;

public class Rule38 extends Rule13_AliphaticMonoalcohols {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1883795218031485202L;
	public static final String MSG38 = "Aliphatic amines";

	public Rule38() {
		
		super();		
		try {
            /*
			//addSubstructure("N([$([#1]),$(C);!$(C.c);!$(C.[#7]);!$(C.[#8]);!$(C.[#16])])([$([#1]),$(C);!$(C.c);!$(C.[#7]);!$(C.[#8]);!$(C.[#16])])([$(C),$(C.[OX2]);!$(C.c);!$(C.[#7]);!$(C.[#16]);!$(C.[OH]);!$(C.[OX1])])");
					//"[C;!c]N([H,C;!c])([H,C;!c])");
			addSubstructure("N([#1,C,$(C[OX2]);!$(C.c)])([#1,C,$(C[OX2])])([C,$(C[OX2])])");
            */
            smartsPattern = new SmartsPatternCDK("N(C)([#1,C])([#1,C])");
			setExplanation("N(R)(R1)(R2), R - aliphatic chain, whicn may contain ether functions. R1, R2 = H or aliphatic chain");
			setID("38");
			
			setTitle(MSG38);
			editable = false;
			//examples[0] = "C(CCCC)C=C";
            examples[0] = "N(C)(CCCOC)CC(N)C=O";
			examples[1] = "N(CCOCC)C=C";					
			
		} catch (SMARTSException x) {
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
	}

    public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
    	try {
	        int r = smartsPattern.hasSMARTSPattern(mol);
	        if (r == 0) return false;
	        IAtomContainerSet chains = extractChains(mol, smartsPattern.getUniqueMatchingAtoms(mol));
	        
	        int okchains = 0;
	        int allchains = 0;
	        for (int i=0; i < chains.getAtomContainerCount(); i++) 
	            try {
	                allchains++;                
	                if (acceptSubstituent(chains.getAtomContainer(i))) {
	                    okchains++;
	                }
	            } catch (DecisionMethodException x) {
	            	logger.log(Level.WARNING,x.getMessage(),x);
	                return false;
	            }
	        return okchains==allchains;
    	} catch (SMARTSException x) {
    		throw new DecisionMethodException(x);
    	} catch (DecisionMethodException x) {
    		throw x;
    	}
    }
    
    protected boolean acceptSubstituent(IAtomContainer m) throws DecisionMethodException {
        int c_count = 0;
        int o_count = 0;
        int h_count = 0;
        for (int j=0; j < m.getAtomCount();j++) {
            if (m.getAtom(j).getFlag(CDKConstants.ISAROMATIC)) {
                logger.finer("Aromatic atom found");
                return false;
            } 
            if (m.getAtom(j).getFlag(CDKConstants.ISINRING)) {
                logger.finer("Ring atom found");
                return false;
            }                
            if ("C".equals(m.getAtom(j).getSymbol())) {
                c_count ++;
            } else if ("O".equals(m.getAtom(j).getSymbol())) {
                List<IBond> bonds = m.getConnectedBondsList(m.getAtom(j));
                if (bonds.size()==0) {
                    logger.finer("non ether group found");
                    return false;
                }
                for (IBond bond : bonds) {
                    if (Order.SINGLE != bond.getOrder()) {
                        logger.finer("O connected with bon-single bond found");
                        return false;
                    }
                    if (!"C".equals(bond.getConnectedAtom(m.getAtom(j)).getSymbol())) {
                        logger.finer("O connected to non-C atom found");
                        return false;
                    }
                }
                o_count ++;                
            } else if ("H".equals(m.getAtom(j).getSymbol())) {
                h_count++;
            }
            else {
                logger.finer("Non C or O atom found");
                return false;
            }
                
        }
        return (c_count+o_count+h_count) == m.getAtomCount();
        
         
    }  
    
    @Override
    public IProcessor<IAtomContainer, IChemObjectSelection> getSelector() {
    	RuleSMARTSSubstructureAmbit rule = new RuleSMARTSSubstructureAmbit();
    	try { rule.addSubstructure("N(C)([#1,C])([#1,C])"); } catch (Exception x) {x.printStackTrace();};
    	return rule.getSelector();
    }
}


