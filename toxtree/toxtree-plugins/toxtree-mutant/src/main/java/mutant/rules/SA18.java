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

import java.util.List;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.ringsearch.RingPartitioner;
import org.openscience.cdk.ringsearch.SSSRFinder;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.DefaultAlertCounter;
import toxTree.tree.rules.IAlertCounter;
import toxTree.tree.rules.RuleAromatic;

/**
 * TODO Reimplement PAH recognition in a more robust way, possibly by generalized Hueckel rule (Randic)
 * 
 * @author nina
 *
 */
public class SA18 extends RuleAromatic  implements IAlertCounter {
    /**
     * 
     */
    private static final long serialVersionUID = 6108780282064473735L;
    /**
     * 
     */
	IAlertCounter alertsCounter;
	
    public SA18() {
        super();
        alertsCounter = new DefaultAlertCounter();
        setID("SA18");
        setTitle("Polycyclic Aromatic Hydrocarbons");
        setExplanation(" Polycyclic Aromatic Hydrocarbons, with three or more fused rings. Does not include heterocyclic compounds");
        examples[0] = "CCN2C3=CC=CC=C3(C=1C=C(N)C=CC=12)"; //heterocyclic
        examples[1] = "[H]C=1C([H])=C([H])C=5C(C=1([H]))=C([H])C([H])=C3C=5(C([H])=C2C([H])=C([H])C(=C4C2=C3C([H])([H])C4([H])([H]))C([H])([H])[H])";
//"[H]C=1C([H])=C([H])C2=C(C=1([H]))C4=C([H])C([H])=C([H])C3=C([H])C(=C(C2=C34)N(=O)O)N(=O)O"; //"C=1C=C2C=CC3=CC=CC4=CC=C(C=1)C2=C34";   
        /*
        examples[0] = "";
        examples[1] = "O=[N+]([O-])C=4C=C2C=CC=C3C=1C=CC=CC=1C(=C23)C=4[N+](=O)[O-]";
        */
//"[H]C=1C([H])=C([H])C3=C(C=1([H]))C=2C(=C([H])C([H])=C([H])C=2C3([H])([H]))N([H])C(=O)C([H])([H])[H]";
//C1=CC=C3C(=C1)CC2=CC=CC=C23";
/*
 * C1=CC=C3C(=C1)CC2=CC=CC=C23
C=1C=CC3=C(C=1)CC2=CC=CC=C23
C=1C=CC3=C(C=1)CC=2C=CC=CC=23
 */        	
        	//"[H]C=1C([H])=C([H])C3=C(C=1([H]))C=2C(=C([H])C([H])=C([H])C=2C3([H])([H]))N([H])C(=O)C([H])([H])[H]";
//"C=1C=CC=2C(C=1)=CC=C4C=2C(=C3C=CC=CC3=C4C)C";
//"C=1C=C2C=CC3=CC=CC4=CC=C(C=1)C2=C34";   

    }
    public void incrementCounter(IAtomContainer mol) {
        alertsCounter.incrementCounter(mol);
    }    
	public String getImplementationDetails() {
		StringBuffer b = new StringBuffer();
		b.append(alertsCounter.getImplementationDetails());
		b.append("<br>");
		b.append(getClass().getName());
		b.append("<ol><li>Find essential rings through SSSRFinder.findEssentialRings. This set of rings is uniquely defined.");
		b.append("<li>Split into set of fused rings. <li>For each set of fused rings verify for each ring if it is composed of aromatic atoms only.");
		b.append("If yes, the ring is considered aromatic and the counter for aromatic rings within this set of fused rings is incremented.");
		b.append("<li>If the ring is aromatic, it is verified if it contains an heteroaromatic atoms, and if yes, the counter for heteroaromatic rings is incremented.");
		b.append("<li>If the set of fused rings contains > 2 aromatic rings and no heteroaromatic rings, the rule returns true.</ol>");
		return b.toString();
	}    

    @Override
    public boolean isImplemented() {
        return true;
    }    
    
    /**
	Find essential rings through SSSRFinder.findEssentialRings. This set of rings is uniquely defined.
	Split into set of fused rings. For each set of fused rings verify for each ring if it is composed of aromatic atoms only.
	If yes, the ring is considered aromatic and the counter for aromatic rings within this set of fused rings is incremented.
	If the ring is aromatic, it is verified if it contains an heteroaromatic atoms, and if yes, the counter for heteroaromatic rings is incremented.
	
	If the set of fused rings contains > 2 aromatic rings and no heteroaromatic rings, the rule returns true.
     */
    public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
        if (super.verifyRule(mol)) {
            SSSRFinder ssrf = new SSSRFinder(mol);
            IRingSet sssrings = ssrf.findEssentialRings();        	
            //aromaticity should already be detected by MolAnalyser
        	

        	int nr = sssrings.getAtomContainerCount(); 
            if (nr < 3) {
                logger.debug("Less than 3 rings");
                return false;
            } else {
               List ringsets = RingPartitioner.partitionRings(sssrings);
               //This partitions ring into fused rings sets
               for (int i=0; i < ringsets.size();i++) {
                    IRingSet ringset = (IRingSet) ringsets.get(i);
                    
                    if (ringset.getAtomContainerCount() < 3) continue;
                    //System.out.println("Fused rings "+ringset.getAtomContainerCount());
                    int heteroaromatic_ring_count = 0;
                    int aromatic_ring_count = 0;
                    for (int j=0; j < ringset.getAtomContainerCount();j++) {
                    		
                    		IRing ring = (IRing) ringset.getAtomContainer(j);
                    		int a = getNumberOfAromaticAtoms(ring);
                    		//System.out.println("Ring with "+ring.getAtomCount()+" atoms " + a + " aromatic");
                            if (isAromaticRing(a,ring.getAtomCount())) {
                            	aromatic_ring_count++;
                              //  System.out.println("aromatic");
                        		if (isHeterocyclic((IRing)ringset.getAtomContainer(j))) heteroaromatic_ring_count++;
                        		
                            } 
                     }
                     if (acceptRingSet(ringset, heteroaromatic_ring_count,aromatic_ring_count))  {
                        	incrementCounter(mol);
                        	return true;
                     }
                }
            }
            
            logger.debug("Aromatic fused (>=3) rings not found ");
            return false;
            
        } else {
            logger.info("Not an aromatic chemical");
            return false;
        }
    }
    protected boolean isAromaticRing(int aromaticAtoms, int allAtoms) {
    	return (allAtoms - aromaticAtoms) < 2;
    }
    protected boolean acceptRingSet(IRingSet ringset, int heteroaromaticrings, int aromaticrings) {
    	logger.debug("Heteroaromatic "+ heteroaromaticrings +" aromatic" + aromaticrings);
        return ( heteroaromaticrings==0) && (aromaticrings>2);
    }
    protected int getNumberOfAromaticAtoms(IRing ring) {
    	int ar=0;
        for (int j=0; j < ring.getAtomCount(); j++) {
            IAtom a = ring.getAtom(j);
            if (a.getFlag(CDKConstants.ISAROMATIC)) ar++;
       }
        return ar;
    }    

    protected boolean isHeterocyclic(IRing ring) {
    	boolean ok = false;
        for (int j=0; j < ring.getAtomCount(); j++) {
            IAtom a = ring.getAtom(j);
            if (!a.getSymbol().equals("C"))  {
            	ok = true;
            	break;
            }
       }
        return ok;
    }
 
}


