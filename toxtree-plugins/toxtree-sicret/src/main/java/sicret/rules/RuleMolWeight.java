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

import javax.swing.JComponent;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.tools.MFAnalyser;

import toxTree.tree.rules.RuleVerifyProperty;


/**
 * Molecular weight > 350.
 * Expects property to be read from IMolecule.getProperty({@link #MolWeight}).
 * If it is null, calculates molecular weight by {@link MFAnalyser}.
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleMolWeight extends RuleVerifyProperty {
	private static final long serialVersionUID = 0;
	public static String MolWeight = "MolWeight";
	public RuleMolWeight()
	{
		super();
		id = "6";
		title = "Molecular Weight >  350.0";
		propertyStaticValue = 350.0;
		condition = condition_higher;
		propertyName = MolWeight;
		
		examples[0]= "S(SCCC)CCC";
		propertyExamples[0] = 150.305997;
		examples[1] = "OS(=O)(=O)c1cc2NC4(Nc2c(c1)S(O)(=O)=O)C=3\\C=C/C4=C\\C=3";
		propertyExamples[1] = 674.567985;		

	}
	
	@Override
	public String inputProperty(IAtomContainer mol) {
		MFAnalyser mf = new MFAnalyser(mol);
		return Double.toString(mf.getMass());
	}
    public JComponent optionsPanel(IAtomContainer atomContainer) {
        return null;
    }
}

