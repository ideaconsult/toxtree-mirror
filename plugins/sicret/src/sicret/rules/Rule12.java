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
import org.openscience.cdk.Molecule;
import org.openscience.cdk.interfaces.IMolecule;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;

/**
 * VapourPressure <  0.0001
 * @author Nina Jeliazkova
 *
 */
public class Rule12 extends RuleVapourPressure {
	
	private static final long serialVersionUID = 0;
	public Rule12()
	{
		super(VapourPressure,"Pa",condition_lower,0.0001);
		id = "14";
		/*
		title = "VapourPressure <  0.0001";
		propertyStaticValue = 0.0001;
		condition = condition_lower;
		propertyName = VapourPressure;
		*/

	}
	public IMolecule getExampleMolecule(boolean ruleResult) throws DecisionMethodException {
		IMolecule m = DefaultChemObjectBuilder.getInstance().newMolecule(); 
        if (ruleResult){
        	
        		m = (IMolecule)FunctionalGroups.createAtomContainer("O=C(O)c1ccsc1NC=O",false);
    			m.setProperty(VapourPressure, 0.000001);
        }
    	else {
    		m = (IMolecule)FunctionalGroups.createAtomContainer("O=C1C(=C(OC1C)C)OC(=O)C",false);
        		m.setProperty(VapourPressure, 0.012201);
    	}      	
		return m;
	}
    public javax.swing.JComponent optionsPanel(org.openscience.cdk.interfaces.IAtomContainer atomContainer) {
        return null;
    };
}
