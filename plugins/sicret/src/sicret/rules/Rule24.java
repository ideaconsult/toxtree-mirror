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
package sicret.rules;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IMolecule;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;

/**
 * Molecular Weight > 620.0
 * @author Nina Jeliazkova
 *
 */
public class Rule24 extends RuleMolWeight {
	
	private static final long serialVersionUID = 0;
	public Rule24()
	{
		super();
		id = "28";
		title = "Molecular Weight > 620.0";
		propertyStaticValue = 620.0;
		condition = condition_higher;
		propertyName = MolWeight;

	}
	public IMolecule getExampleMolecule(boolean ruleResult) throws DecisionMethodException {
		IMolecule m = DefaultChemObjectBuilder.getInstance().newMolecule();        
        if (ruleResult){
        	
        		m = (IMolecule)FunctionalGroups.createAtomContainer("OS(=O)(=O)c1cc2NC4(Nc2c(c1)S(O)(=O)=O)C=3\\C=C/C4=C\\C=3",false);
    			m.setProperty(MolWeight, 674.567985);
        }
    	else {
    		m = (IMolecule)FunctionalGroups.createAtomContainer("BrCCCCCCBr",false);
        		m.setProperty(MolWeight, 243.979991);
    	}      	
		return m;
	}

}
