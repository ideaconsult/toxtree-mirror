/*
Copyright Nina Jeliazkova (C) 2005-2006  
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
package verhaar.query;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.isomorphism.matchers.IQueryAtom;

/**
 * A fix until {@link AromaticQueryBond} is fixed
 * 
 * @author Nina Jeliazkova <b>Modified</b> 2005-10-30
 */
public class AromaticQueryBond extends
		org.openscience.cdk.isomorphism.matchers.smarts.AromaticQueryBond {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 7143741142261301599L;

	/**
	 * 
	 */
	public AromaticQueryBond(IChemObjectBuilder builder) {
		super(builder);
	}

	/**
	 * @param atom1
	 * @param atom2
	 * @param order
	 */
	public AromaticQueryBond(IQueryAtom atom1, IQueryAtom atom2,
			IChemObjectBuilder builder) {
		super(atom1, atom2, IBond.Order.SINGLE, builder); // TODO bond order
															// doesn't matter
		// TODO Auto-generated constructor stub
	}

	public boolean matches(IBond bond) {
		if (bond.getFlag(CDKConstants.ISAROMATIC)) {
			return true;
		}
		return false;
	};
}
