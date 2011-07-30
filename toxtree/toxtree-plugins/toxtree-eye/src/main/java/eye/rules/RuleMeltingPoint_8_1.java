/*
Copyright Ideaconsult Ltd.(C) 2005-2011
Contact: jeliazkova.nina@gmail.com

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

import sicret.rules.RuleMeltingPoint;

public class RuleMeltingPoint_8_1 extends RuleMeltingPoint {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2248078944626757369L;

	public RuleMeltingPoint_8_1()
	{
		super();
		setProperty(55);
		setCondition(condition_higher);
		setID("8.1");
		setTitle(getPropertyName() + getCondition() + getProperty());
		examples[0] = "[H]C([H])([H])C(=O)OC=1C(=O)C([H])(OC=1C([H])([H])[H])C([H])([H])[H]";
		examples[1] = "C1[C@@]2([C@H]([C@H]3[C@H]([C@@]4(C(=CC(=O)CC4)CC3)C)C1=O)CC[C@@]2(C(COC(C)=O)=O)O)C";				
	}
	public javax.swing.JComponent optionsPanel(org.openscience.cdk.interfaces.IAtomContainer atomContainer) {
	    return null;
    };	
}


