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


/**
 * MeltingPoint > 120.0
 * @author Nina Jeliazkova
 *
 */
public class Rule27  extends RuleMeltingPoint {
	
	private static final long serialVersionUID = 0;
	public Rule27()
	{
		super(MeltingPoint,"\u0260C",condition_higher,120.0);
		id = "31";

		examples[0]="BrCCCCCl";
		propertyExamples[0] =  -33.576141;
		examples[1] = "CCOC(=O)N1CCN(CC1)c2ccc(cc2)OCC4COC(Cn3cncc3)(O4)c5ccc(Cl)cc5Cl";
		propertyExamples[1] =  290.264832;			

	}


    public javax.swing.JComponent optionsPanel(org.openscience.cdk.interfaces.IAtomContainer atomContainer) {
        return null;
    };
}
