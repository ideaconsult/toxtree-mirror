/*
Copyright Ideaconsult (C) 2005-2006  
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

import org.openscience.cdk.Bond;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.Atom;
import org.openscience.cdk.isomorphism.matchers.QueryBond;

import toxTree.logging.TTLogger;

/**
 * Verifies if a bond is unsaturated (double, triple, aromatic)
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-30
 */
public class QueryUnsaturatedBond extends Bond implements QueryBond {
	protected static TTLogger logger = new TTLogger(QueryUnsaturatedBond.class);
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -7992622589225200981L;

	/**
	 * 
	 */
	public QueryUnsaturatedBond() {
		super();

	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public QueryUnsaturatedBond(Atom arg0, Atom arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public QueryUnsaturatedBond(Atom arg0, Atom arg1, double arg2) {
		super(arg0, arg1, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public QueryUnsaturatedBond(Atom arg0, Atom arg1, double arg2, int arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.openscience.cdk.isomorphism.matchers.QueryBond#matches(org.openscience.cdk.interfaces.Bond)
	 */
	public boolean matches(org.openscience.cdk.interfaces.Bond arg0) {
		if (arg0.getFlag(CDKConstants.ISAROMATIC)) {
			logger.debug("Aromatic bond found");
			return true;
		}
		else 
			if (arg0.getOrder() == CDKConstants.BONDORDER_DOUBLE) {
				logger.debug("Double bond found");
				return true;
			} else if (arg0.getOrder() == CDKConstants.BONDORDER_TRIPLE) {
				logger.debug("Triple bond found");
				return true;
			} else if (arg0.getOrder() == CDKConstants.BONDORDER_AROMATIC) {
				logger.debug("Aromatic bond found");
				return true;
			} else return false;
			

	
	}

}
