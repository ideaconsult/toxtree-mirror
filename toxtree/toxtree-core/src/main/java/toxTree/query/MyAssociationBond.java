/*
Copyright Ideaconsult Ltd. (C) 2005-2007 

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/
package toxTree.query;


import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.silent.Bond;

/**
 * This is a hack to allow query for ionic bonds
 * if Association class is used, there is no way to implement QueryBond interface to
 * return smth like "bond instance of Association" 
 * @author ThinClient
 * <b>Modified</b> 2005-9-23
 */
public class MyAssociationBond extends Bond {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -8560854906118304413L;

	/**
	 * 
	 */
	public MyAssociationBond() {
		super();
	}

	/**
	 * @param atom1
	 * @param atom2
	 */
	public MyAssociationBond(IAtom atom1, IAtom atom2) {
		super(atom1, atom2,null);
	}

	/**
	 * @param atom1
	 * @param atom2
	 * @param order
	 */
	public MyAssociationBond(IAtom atom1, IAtom atom2, IBond.Order order) {
		super(atom1, atom2, order);
	}

	/**
	 * @param atom1
	 * @param atom2
	 * @param order
	 * @param stereo
	 */
	public MyAssociationBond(IAtom atom1, IAtom atom2, IBond.Order order, Stereo stereo) {
		super(atom1, atom2, order, stereo);
	}

}
