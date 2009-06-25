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
 * Aqueous solubility [g/l] < 2.0E-5 Expects property to be read from IMolecule.getProperty({@link #AqueousSolubility}).
 * @author Nina Jeliazkova
 * @author ania Tsakovska
 */
public class RuleAqueousSolubility extends sicret.rules.RuleAqueousSolubility {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5545033439943037006L;
	public RuleAqueousSolubility()
	{
		this(AqueousSolubility,"g/l",condition_lower,2.0E-5);
		
	}
	public RuleAqueousSolubility(String propertyName, String units, String condition, double value) {
		super(propertyName,units,condition,value);
		setID("6");
		setTitle(getPropertyName() + getCondition() + getProperty());
		examples[0] = "c1([C@@](COC(N)=O)(CC)O)ccccc1";
		examples[1] = "C(c1ccc(Cl)cc1)(c1ccc(Cl)cc1)C(Cl)(Cl)Cl";		
	}
	public javax.swing.JComponent optionsPanel(org.openscience.cdk.interfaces.IAtomContainer atomContainer) {
	    return null;
    };	
}
