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
 * AqueousSolubility < 0.1
 * @author Nina Jeliazkova
 *
 */
public class Rule18 extends RuleAqueousSolubility {
	
	private static final long serialVersionUID = 0;
	public Rule18()
	{
		super(AqueousSolubility,"mg/l",condition_lower,0.1);
		id = "21";
		/*
		title = "AqueousSolubility < 0.1";
		propertyStaticValue = 0.1;
		condition = condition_lower;
		propertyName = AqueousSolubility;
		*/

	}
	public IMolecule getExampleMolecule(boolean ruleResult) throws DecisionMethodException {
		IMolecule m = DefaultChemObjectBuilder.getInstance().newMolecule();        
        if (ruleResult){
        	
        		m = (IMolecule)FunctionalGroups.createAtomContainer("Oc(c(cc(c1)Cc(cc(c(O)c2C(C)(C)C)C(C)(C)C)c2)C(C)(C)C)c1C(C)(C)C",false);
    			m.setProperty(AqueousSolubility, 0.000171);
        }
    	else {
    		m = (IMolecule)FunctionalGroups.createAtomContainer("BrCCCCCCBr",false);
        		m.setProperty(AqueousSolubility, 10.293935);
    	}      	
		return m;
	}
    public javax.swing.JComponent optionsPanel(org.openscience.cdk.interfaces.IAtomContainer atomContainer) {
        return null;
    };
}
