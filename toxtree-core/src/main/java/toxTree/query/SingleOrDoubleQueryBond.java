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

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.isomorphism.matchers.IQueryAtom;
import org.openscience.cdk.isomorphism.matchers.OrderQueryBond;

/**
 * TODO add description
 * @author Vedina
 * <b>Modified</b> 2005-8-19
 */
public class SingleOrDoubleQueryBond extends OrderQueryBond {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -6456640796754649040L;

	/**
	 * 
	 */
	public SingleOrDoubleQueryBond(IChemObjectBuilder builder) {
		super(builder);

	}

	/**
	 * @param atom1
	 * @param atom2
	 */
	public SingleOrDoubleQueryBond(IQueryAtom atom1, IQueryAtom atom2, IChemObjectBuilder builder) {
		super(atom1, atom2,CDKConstants.BONDORDER_SINGLE,builder);
	}
	@Override
	public boolean matches(IBond bond) {
		return
			((this.getOrder() == CDKConstants.BONDORDER_SINGLE) || 
			(this.getOrder() == CDKConstants.BONDORDER_DOUBLE)) 
			&&
			((bond.getOrder() == CDKConstants.BONDORDER_SINGLE) || 
					(bond.getOrder() == CDKConstants.BONDORDER_DOUBLE));			
    };
}
