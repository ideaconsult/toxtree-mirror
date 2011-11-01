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

import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import sicret.rules.RuleMolWeight;
import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import ambit2.core.data.MoleculeTools;

/**
 * Molecular Weight > 280
 *
 */
public class RuleMolWeight_12_2  extends RuleMolWeight {
	
	private static final long serialVersionUID = 0;
	public RuleMolWeight_12_2()
	{
		super();
		id = "12.2";
		title = "Molecular Weight  > 280";
		propertyStaticValue = 280.0;
		condition = condition_higher;
		propertyName = MolWeight;
		examples[0] = "FC1=CNC(=O)NC1=O";
		examples[1] = "Oc1c(cc(c(c1Cc1c(c(cc(c1Cl)Cl)Cl)O)Cl)Cl)Cl";
		setTitle(getPropertyName() + getCondition() + getProperty());

	}
	public IMolecule getExampleMolecule(boolean ruleResult) throws DecisionMethodException {
		IMolecule m = MoleculeTools.newMolecule(SilentChemObjectBuilder.getInstance());  
        if (ruleResult){
        	
        		m = (IMolecule)FunctionalGroups.createAtomContainer("OS(=O)(=O)c1cc2NC4(Nc2c(c1)S(O)(=O)=O)C=3\\C=C/C4=C\\C=3",false);
    			m.setProperty(MolWeight, 674.567985);
        }
    	else {
    		m = (IMolecule)FunctionalGroups.createAtomContainer("S(SCCC)CCC",false);
        		m.setProperty(MolWeight, 150.305997);
    	}      	
		return m;
	}


}
