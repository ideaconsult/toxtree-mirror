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
 * MeltingPoint > 120.0
 * @author Nina Jeliazkova
 *
 */
public class Rule27  extends RuleMeltingPoint {
	
	private static final long serialVersionUID = 0;
	public Rule27()
	{
		super(MeltingPoint,"°C",condition_higher,120.0);
		id = "31";
		/*
		title = "MeltingPoint > 120.0";
		propertyStaticValue = 120.0;
		condition = condition_higher;
		propertyName = MeltingPoint;
		*/

	}
	public IMolecule getExampleMolecule(boolean ruleResult) throws DecisionMethodException {
		IMolecule m = DefaultChemObjectBuilder.getInstance().newMolecule();   
        if (ruleResult){
        	
        		m = (IMolecule)FunctionalGroups.createAtomContainer("CCOC(=O)N1CCN(CC1)c2ccc(cc2)OCC4COC(Cn3cncc3)(O4)c5ccc(Cl)cc5Cl",false);
    			m.setProperty(MeltingPoint, 290.264832);
        }
    	else {
    		m = (IMolecule)FunctionalGroups.createAtomContainer("BrCCCCCl",false);
        		m.setProperty(MeltingPoint, -33.576141);
    	}      	
		return m;
	}

    public javax.swing.JComponent optionsPanel(org.openscience.cdk.interfaces.IAtomContainer atomContainer) {
        return null;
    };
}
