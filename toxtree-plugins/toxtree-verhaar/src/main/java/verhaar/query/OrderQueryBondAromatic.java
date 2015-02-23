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
import org.openscience.cdk.isomorphism.matchers.OrderQueryBond;

/**
 * 
 * @author Nina Jeliazkova
 *
 */
public class OrderQueryBondAromatic extends OrderQueryBond {


	public OrderQueryBondAromatic(boolean aromatic,IChemObjectBuilder builder) {
		super(builder);
		setFlag(CDKConstants.ISAROMATIC,aromatic);
	}

	public OrderQueryBondAromatic(IQueryAtom arg0, IQueryAtom arg1, IBond.Order arg2, boolean aromatic,IChemObjectBuilder builder) {
		super(arg0, arg1, arg2,builder);
		setFlag(CDKConstants.ISAROMATIC,aromatic);
	}
	public boolean matches(IBond arg0) {
		return super.matches(arg0) && 
		(getFlag(CDKConstants.ISAROMATIC) == arg0.getFlag(CDKConstants.ISAROMATIC));  
	}

}
