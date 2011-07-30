/*
Copyright Ideaconsult Ltd.(C) 2006-2011
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

public class Rule1 extends RuleMeltingPoint {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8649383128811407758L;

	public Rule1() {
		setID("1");
		examples[0] = "[H]OC([H])(C([H])([H])[H])C(C([H])=C([H])C1([H])(C([H])([H])C([H])=C(C([H])([H])[H])C1(C([H])([H])[H])(C([H])([H])[H])))(C([H])([H])[H])C([H])([H])[H]";
		examples[1] = "[H]C=1N=C([H])N(C=1([H]))C([H])([H])C2(OC([H])([H])C([H])(O2)C([H])([H])OC3=C([H])C([H])=C(C([H])=C3([H]))N4C([H])([H])C([H])([H])N(C(=O)OC([H])([H])C([H])([H])[H])C([H])([H])C4([H])([H]))C=5C([H])=C([H])C(=C([H])C=5Cl)Cl";		
	}
}


