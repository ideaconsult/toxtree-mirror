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
		super(AqueousSolubility,"g/l",condition_lower,0.001);
		id = "26";
		examples[0]="CC(O)(\\C=C\\C=C(/C)CC\\C=C(/C)C)C=C";
		propertyExamples[0] = 2.39619;
		examples[1] = "O=C(OCCCCCCC(C)C)C1CCCCC1C(=O)OCCCCCCC(C)C";
		propertyExamples[1] =  0.000009;		

	}

    public javax.swing.JComponent optionsPanel(org.openscience.cdk.interfaces.IAtomContainer atomContainer) {
        return null;
    };
}
