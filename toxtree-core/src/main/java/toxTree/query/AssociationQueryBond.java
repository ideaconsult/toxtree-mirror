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


import java.util.Arrays;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.isomorphism.matchers.IQueryBond;



/**
 * TODO add description
 * @author ThinClient
 * <b>Modified</b> 2005-9-23
 */
public class AssociationQueryBond extends MyAssociationBond implements IQueryBond {
	
	private static final long serialVersionUID = 2683414374611772988L;

	/**
	 * @param atom1
	 * @param atom2
	 */
	public AssociationQueryBond(IAtom atom1, IAtom atom2) {
		super(atom1, atom2);
	}

	/**
	 * 
	 */
	public AssociationQueryBond() {
		super();
	}
	
	@Override
	public boolean matches(IBond bond) {
		return bond instanceof MyAssociationBond;
	}

	@Override
	public Iterable<IAtom> atoms() {
		return Arrays.asList(atoms);
	}

	@Override
	public IAtom getAtom(int position) {
		return atoms[position];
	}

	@Override
	public IAtom getConnectedAtom(IAtom atom) {
		return null;
	}

	@Override
	public IAtom[] getConnectedAtoms(IAtom atom) {
		return null;
	}

	@Override
	public void setAtom(IAtom atom, int position) {
		atoms[position] = atom;
	}

	@Override
	public Order getOrder() {
		return null;
	}

	@Override
	public void setOrder(Order order) {
	}

	@Override
	public Stereo getStereo() {
		return null;
	}

	@Override
	public void setStereo(Stereo stereo) {
	}

	@Override
	public Point2d get2DCenter() {
		return null;
	}

	@Override
	public Point3d get3DCenter() {
		return null;
	}

	@Override
	public boolean isConnectedTo(IBond bond) {
		return false;
	}
	@Override
	public AssociationQueryBond clone() throws CloneNotSupportedException {
		return (AssociationQueryBond)super.clone();
	}
}
