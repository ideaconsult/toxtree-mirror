/*
Copyright Ideaconsult Ltd. (C) 2005-2007  

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

package mutant.rules;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.renderer.selection.IChemObjectSelection;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.DefaultAlertCounter;
import toxTree.tree.rules.IAlertCounter;
import toxTree.tree.rules.RuleRingSubstituents;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.base.interfaces.IProcessor;

public class SA20_nogen extends RuleRingSubstituents implements IAlertCounter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2801459527105693883L;
	public static String SA20_title = "(Poly) Halogenated Cycloalkanes (Nongenotoxic carcinogens)";
	private int ring_halogen_count = 0;
	private int halogen_count = 0;
	private final String[] halogens = {"Cl","Br","I","F"};
	protected IAlertCounter alertsCounter;
    public SA20_nogen() {
        super();
           setID("SA20_nogen");
            setTitle(SA20_title);
            setExplanation(SA20_title+"<br>The three halogens have to be directly bound to the same ring.");
            
            examples[0] = "[H]C2=C([H])C([H])=C(N=NC1=C([H])C([H])=C(C([H])=C1([H]))N(C([H])([H])[H])C([H])([H])[H])C([H])=C2([H])"; 
//            	"[H]C1=C([H])C([H])=C2C([H])=C(C([H])=C([H])C2(=C1([H])))N([H])[H]";
//"C1=CC(=C(C(=C1)Cl)Cl)Cl";
            examples[1] = "C1CC(C(CC1Br)I)Cl";   
            editable = false;
            alertsCounter =  new DefaultAlertCounter();
            setAnalyzeOnlyRingsWithFlagSet(true);
            setAnalyzeAromatic(false);
            setAnalyzeHeterocyclic(false);
    }
    public void incrementCounter(IAtomContainer mol) {
    	alertsCounter.incrementCounter(mol);
    }
    
    @Override
    public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
    		throws DecisionMethodException {
    	halogen_count = 0;
    	ring_halogen_count = 0;
    	if (super.verifyRule(mol,selected)) {
        	if (halogen_count < ring_halogen_count)
        		halogen_count = ring_halogen_count;

    		if (halogen_count > 2) {
    			logger.finer(getID()+"\tHalogens on a single ring\t"+halogen_count);
	    		incrementCounter(mol);
	    		return true;
    		}
    	}	
    	return false;
    }
    /**
     * Initialize halogen counts to zero and returns true if the ring consists of single bonds only.
     */
    @Override
    protected boolean analyze(IRing r) {
    	logger.finer("halogens on a ring\t"+ring_halogen_count);
    	if (halogen_count < ring_halogen_count)
    		halogen_count = ring_halogen_count;
    	ring_halogen_count = 0;
    	
    	for (int i =0; i < r.getBondCount(); i++)
    		if (r.getBond(i).getOrder() != CDKConstants.BONDORDER_SINGLE) return false;
    	
    	return true;
    }
    /**
     * Only counts halogens directly connected to the ring and returns true always.
     */
	@Override
	public boolean substituentIsAllowed(IAtomContainer a, int[] place) throws DecisionMethodException {
		
		if (a.getAtomCount() == 1)
			for (int i=0; i < halogens.length; i++) {
				logger.finer("substituent with atoms\t"+a.getAtom(0).getSymbol());
				if (halogens[i].equals(a.getAtom(0).getSymbol())) 
					ring_halogen_count++;
			}
		return true;
	}
	@Override
	public String allowedSubstituents() {
		return "Halogens";
	}
	public String getImplementationDetails() {
		StringBuffer b = new StringBuffer(); 
		b.append(alertsCounter.getImplementationDetails());
		b.append("Partitions structure into rings and verifies if there are at least 3 halogens directly bound to the ring.");
		return b.toString();
	}
	@Override
	public boolean isImplemented() {
		return true;
	}
	
    @Override
    public IProcessor<IAtomContainer, IChemObjectSelection> getSelector() {
    	RuleSMARTSSubstructureAmbit rule = new RuleSMARTSSubstructureAmbit();
    	try { rule.addSubstructure("[#6;R]!@[Cl,Br,I,F]"); } catch (Exception x) {x.printStackTrace();};
    	return rule.getSelector();
    }
}


