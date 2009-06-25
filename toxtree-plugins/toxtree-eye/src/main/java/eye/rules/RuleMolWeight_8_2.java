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

public class RuleMolWeight_8_2 extends RuleMolWeight {

	private static final long serialVersionUID = -8851329850728194643L;

	public RuleMolWeight_8_2()
	{
		super();
		id = "8.2";
		title = "Molecular Weight >  380.0";
		setProperty(380);
		condition = condition_higher;
		setPropertyName(MolWeight);
		setPropertyUnits("g.mol");
		examples[0] = "CCCC(CCC)C(O)=O";
		examples[1] = "COc1ccc(cc1OC)CCOCCCC(CO)(C(C)C)c2ccc(c(c2)OC)OC";				
		setTitle(getPropertyName() + getCondition() + getProperty());
	}
}


