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

import org.openscience.cdk.interfaces.IRingSet;

/**
 * If the set of fused rings contains > 2 aromatic rings and > 1 heteroaromatic rings, the rule returns true.
 * @author nina
 *
 */
public class SA19_gen extends SA18_gen {
    /**
     * 
     */
    private static final long serialVersionUID = 5024954402467318417L;
    public SA19_gen() {
        super();
        setID("SA19_gen");
        setTitle("Heterocyclic Polycyclic Aromatic Hydrocarbons");
        setExplanation("Heterocyclic Polycyclic Aromatic Hydrocarbons (3 or more fused rings).");
        //examples[0] = "C=1C=C2C=CC3=CC=CC4=CC=C(C=1)C2=C34";
        examples[0] = "O=[N+]([O-])C=4C=CC=2C=CC=C3C=1C=CC=CC=1C=4(C=23)";
        //another No example O=N([O-])C2=CC=3C=CC=C4C=1C=CC=CC=1C(=C2)C=34
        examples[1] = "C=1C=CC2=NC3=CC=CC=C3(N=C2(C=1))"; //"NC1C=CC2=NC3=CC=CC=C3(N=C2(C1))"; "N=1C=3C=CC=CC=3(N=C2C=C(N)C=CC=12)";   
    }
    @Override
	public String getImplementationDetails() {
		StringBuffer b = new StringBuffer();
		b.append(alertsCounter.getImplementationDetails());
		b.append("<br>");
		b.append(getClass().getName());
		b.append("<ol><li>Find essential rings through SSSRFinder.findEssentialRings. This set of rings is uniquely defined.");
		b.append("<li>Split into set of fused rings. <li>For each set of fused rings verify for each ring if it is composed of aromatic atoms only.");
		b.append("If yes, the ring is considered aromatic and the counter for aromatic rings within this set of fused rings is incremented.");
		b.append("<li>If the ring is aromatic, it is verified if it contains an heteroaromatic atoms, and if yes, the counter for heteroaromatic rings is incremented.");
		b.append("<li>If the set of fused rings contains > 2 aromatic rings and > 1 heteroaromatic rings, the rule returns true.</ol>");
		return b.toString();
    }
    protected boolean acceptRingSet(IRingSet ringset, int heteroaromaticrings, int aromaticrings) {
        return ( heteroaromaticrings>0) && (aromaticrings>2);
    }
    protected boolean isAromaticRing(int aromaticAtoms, int allAtoms) {
    	return (allAtoms - aromaticAtoms) == 0;
    }

}


