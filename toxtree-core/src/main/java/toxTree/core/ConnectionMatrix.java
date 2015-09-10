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

package toxTree.core;

import org.openscience.cdk.graph.matrix.IGraphMatrix;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.tools.manipulator.BondManipulator;

public class ConnectionMatrix implements IGraphMatrix {

	/**
	 * Returns the connection matrix representation of this AtomContainer.
	 *
     * @param  container The AtomContainer for which the matrix is calculated
	 * @return           A connection matrix representating this AtomContainer
	 */
    public static double[][] getMatrix(IAtomContainer container) {
		IBond bond = null;
		int indexAtom1;
		int indexAtom2;
		double[][] conMat = new double[container.getAtomCount()][container.getAtomCount()];
		for (int f = 0; f < container.getBondCount(); f++)
		{
			bond = container.getBond(f);
			indexAtom1 = container.getAtomNumber(bond.getAtom(0));
			indexAtom2 = container.getAtomNumber(bond.getAtom(1));
			conMat[indexAtom1][indexAtom2] = BondManipulator.destroyBondOrder(bond.getOrder());
			conMat[indexAtom2][indexAtom1] = BondManipulator.destroyBondOrder(bond.getOrder());
		}
		return conMat;
	}

}