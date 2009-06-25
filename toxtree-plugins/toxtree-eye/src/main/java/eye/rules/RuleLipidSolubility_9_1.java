/*
Copyright (C) 2005-2008  

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package eye.rules;

public class RuleLipidSolubility_9_1 extends RuleLipidSolubility {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8357984840287595156L;

	public RuleLipidSolubility_9_1()
	{
		super();
		id = "9.1";
		propertyStaticValue = 0.4;
		condition = condition_lower;
		propertyName = LipidSolubility;
		setPropertyUnits("g/kg");
		setTitle(getPropertyName() + getCondition() + getProperty());
		examples[0] = "";
		examples[1] = "";		
	}
	public javax.swing.JComponent optionsPanel(org.openscience.cdk.interfaces.IAtomContainer atomContainer) {
	    return null;
    };	
}


