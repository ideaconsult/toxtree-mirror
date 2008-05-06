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

/**
 * MoleculWeight > 380.0
 * @author Nina Jeliazkova
 *
 */
public class Rule21 extends RuleMolWeight {
	
	private static final long serialVersionUID = 0;
	public Rule21()
	{
		super();
		id = "24";
		title = "Molecular Weight  > 380.0";
		propertyStaticValue = 380.0;
		condition = condition_higher;
		propertyName = MolWeight;
		examples[0]="BrCCCCCCBr";
		propertyExamples[0] = 243.979991;
		examples[1] ="OS(=O)(=O)c1cc2NC4(Nc2c(c1)S(O)(=O)=O)C=3\\C=C/C4=C\\C=3";
		propertyExamples[1] =  674.567985;			

	}

	@Override
	public JComponent optionsPanel(IAtomContainer atomContainer) {
	    return null;
	}

}
