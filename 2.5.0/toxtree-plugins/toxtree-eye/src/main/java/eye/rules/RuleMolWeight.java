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



/**
 * Molecular weight > 350.
 * Expects property to be read from IMolecule.getProperty({@link #MolWeight}).
 * If it is null, calculates molecular weight by {@link MFAnalyser}.
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleMolWeight extends sicret.rules.RuleMolWeight {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8191213894327027957L;

	public RuleMolWeight()
	{
		super();
		id = "7";
		title = "Molecular Weight >  650.0";
		setProperty(650.0);
		condition = condition_higher;
		setPropertyName(MolWeight);
		setPropertyUnits("g.mol");
		examples[0] = "CC(=O)Oc1ccccc1C(O)=O";
		examples[1] = "CCC1NC(=O)C(C(O)C(C)C\\C=C\\C)N(C)C(=O)C(C(C)C)N(C)C(=O)C(CC(C)C)N(C)C(=O)C(CC(C)C)N(C)C(=O)C(C)NC(=O)C(C)NC(=O)C(CC(C)C)N(C)C(=O)C(NC(=O)C(CC(C)C)N(C)C(=O)CN(C)C1=O)C(C)C";
		setTitle(getPropertyName() + getCondition() + getProperty());		
	}

}

