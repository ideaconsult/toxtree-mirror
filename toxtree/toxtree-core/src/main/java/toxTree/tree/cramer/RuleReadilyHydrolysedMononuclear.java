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
 * Filename: RuleReadilyHydrolised.java
 */
package toxTree.tree.cramer;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.ringsearch.SSSRFinder;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolFlags;
import toxTree.tree.rules.RuleReadilyHydrolised;

/**
 * Rule 15 of the Cramer scheme (see {@link toxTree.tree.cramer.CramerRules})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class RuleReadilyHydrolysedMononuclear extends RuleReadilyHydrolised {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -4332600573329605106L;

    /**
	 * Constructor
	 * 
	 */
	public RuleReadilyHydrolysedMononuclear() {
		super();
		id = "15";
		title = "Readily hydrolised";
		explanation.append("<html>Is it <i>readily hydrolysed</i>(H) to mononuclear residues?");
		explanation.append("If YES, treat the mononuclear heterocylic residues by Q.22 and any carbocyclic residue by Q16.</html>");
		examples[0] = "C(SC1=CC=CC=C1)C2=CC=CS2";
		examples[1] = "O=C(OC1=CC=CC=C1)C2=CC=CO2";
		editable = false;
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleReadilyHydrolised#verifyRule(org.openscience.cdk.Molecule)
	 */
	@Override
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		if (!super.verifyRule(mol)) return false; //not readily hydrolysed at all
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    IAtomContainerSet sc = mf.getHydrolysisProducts();
	    //sc is not null since we had passed the super method
	    SSSRFinder sf = null;
	    IRingSet rs = null;
	    boolean result = true;
	    for (int i=0; i<sc.getAtomContainerCount();i++) {
	    	sf = new SSSRFinder(sc.getAtomContainer(i));
	    	rs = sf.findSSSR();
	    	if (rs.getAtomContainerCount() > 1) {
	    		logger.debug("Residue not mononuclear");
	    		result = false; break;
	    	} 
	    }
	    if (result) mf.setResidues(sc);
	    sf = null;
	    rs = null;
	    sc = null;
	    return result;
	}

}
