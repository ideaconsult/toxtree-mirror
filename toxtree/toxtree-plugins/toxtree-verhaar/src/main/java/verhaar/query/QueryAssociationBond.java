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

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.isomorphism.matchers.IQueryBond;
import org.openscience.cdk.silent.Bond;
import org.openscience.cdk.silent.ElectronContainer;

import toxTree.query.MyAssociationBond;

/**
 * Used to query for ionic (association ) bond.
 * Verifies if a bond is an instance of {@link toxTree.query.MyAssociationBond}. 
 * Should be modified to use {@link org.openscience.cdk.Association}
 * once {@link org.openscience.cdk.isomorphism.matchers.QueryBond#matches(org.openscience.cdk.interfaces.Bond)}
 * method accepts {@link ElectronContainer} instead of {@link org.openscience.cdk.interfaces.Bond}.
 * Implements {@link org.openscience.cdk.isomorphism.matchers.QueryBond}.
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-30
 */
public class QueryAssociationBond extends Bond implements IQueryBond {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 3818342873632507668L;

	/**
	 * 
	 */
	public QueryAssociationBond() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public QueryAssociationBond(IAtom arg0, IAtom arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public QueryAssociationBond(IAtom arg0, IAtom arg1, IBond.Order arg2) {
		super(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public QueryAssociationBond(IAtom arg0, IAtom arg1,IBond.Order arg2, Stereo arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	/* (non-Javadoc)
	 * @see org.openscience.cdk.isomorphism.matchers.QueryBond#matches(org.openscience.cdk.interfaces.Bond)
	 */
	public boolean matches(IBond arg0) {
		return arg0 instanceof MyAssociationBond;
	}

}
