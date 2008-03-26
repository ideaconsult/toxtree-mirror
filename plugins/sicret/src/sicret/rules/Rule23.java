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
 * 
 * AqueousSolubility < 0.001
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule23 extends RuleAqueousSolubility {
	
	private static final long serialVersionUID = 0;
	public Rule23()
	{
		super(AqueousSolubility,"mg/l",condition_lower,0.001);
		id = "26";
		/*
		title = "AqueousSolubility < 0.001";
		propertyStaticValue = 0.001;
		condition = condition_lower;
		propertyName = AqueousSolubility;
		*/

	}
	public IMolecule getExampleMolecule(boolean ruleResult) throws DecisionMethodException {
		IMolecule m = DefaultChemObjectBuilder.getInstance().newMolecule();        
        if (ruleResult){
        	
        		m = (IMolecule)FunctionalGroups.createAtomContainer("O=C(OCCCCCCC(C)C)C1CCCCC1C(=O)OCCCCCCC(C)C",false);
    			m.setProperty(AqueousSolubility, 0.000009);
        }
    	else {
    		m = (IMolecule)FunctionalGroups.createAtomContainer("CC(O)(\\C=C\\C=C(/C)CC\\C=C(/C)C)C=C",false);
        		m.setProperty("AqueousSolubility", 2.39619);
    	}      	
		return m;
	}
    public javax.swing.JComponent optionsPanel(org.openscience.cdk.interfaces.IAtomContainer atomContainer) {
        return null;
    };
}
