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

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.tree.rules.RuleAnySubstructure;

/**
 * Aromatic ring N-oxide. TODO - aromatic ring is not recognized for unknown reason.
 * @author Nina Jeliazkova
 *
 */
public class SA26 extends RuleAnySubstructure implements IAlertCounter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1180914654531194376L;
	public static String SA26_title = "Aromatic ring N-oxide";
	protected IAlertCounter alertsCounter = new DefaultAlertCounter();
    public SA26() {
        super();
        setID("SA26");
        setTitle(SA26_title);
        setExplanation(SA26_title);
            
        examples[0] = "CCCCCCCC[N+](C)(C)[O-]";
        examples[1] = "O=[N+]([O-])C=1C=C[N+]([O-])=CC=1";
        //"O=[N+]([O-])C5=CC=C2C=CC3=C1C=CC=CC1=[N+]([O-])C=4C=CC5(=C2C3=4)";   
        editable = false;
        
        addSubstructure(FunctionalGroups.noxide_aromatic());
    }	
    @Override
    public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
    	if (super.verifyRule(mol)) {
    		incrementCounter(mol);
    		return true;
    	} else return false;
    }
	
	public void incrementCounter(IAtomContainer mol) {
		alertsCounter.incrementCounter(mol);
		
	}
	public String getImplementationDetails() {
		StringBuffer b = new StringBuffer();
		b.append(alertsCounter.getImplementationDetails());
		
		b.append(getClass().getName());
		b.append(".java");
		return b.toString();
	}
	

}


