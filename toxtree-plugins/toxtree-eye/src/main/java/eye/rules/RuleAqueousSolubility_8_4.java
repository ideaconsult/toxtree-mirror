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

public class RuleAqueousSolubility_8_4 extends RuleAqueousSolubility {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4585282455276205726L;
	public RuleAqueousSolubility_8_4()
	{
		this(AqueousSolubility,"g/l",condition_lower,1.0E-4);
	}
	public RuleAqueousSolubility_8_4(String propertyName, String units, String condition, double value) {
		super(propertyName,units,condition,value);
		setID("8.4");
		setTitle(getPropertyName() + getCondition() + getProperty());
		examples[0] = "C=O";
		examples[1] = "c12c(C(c3ccccc3C1=O)=O)c(ccc2O)O";		
	}
	public javax.swing.JComponent optionsPanel(org.openscience.cdk.interfaces.IAtomContainer atomContainer) {
	    return null;
    };	
}


