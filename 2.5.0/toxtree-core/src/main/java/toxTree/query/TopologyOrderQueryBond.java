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
import org.openscience.cdk.isomorphism.matchers.IQueryAtom;
import org.openscience.cdk.isomorphism.matchers.OrderQueryBond;

/**
 * The same as {@link org.openscience.cdk.isomorphism.matchers.OrderQueryBond}
 * with additional condition to be acyclic or cyclic.
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-8-17
 */
public class TopologyOrderQueryBond extends OrderQueryBond {
	protected boolean inRing = false; 
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 2152216425045457981L;
	/**
	 * 
	 */
	public TopologyOrderQueryBond() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public TopologyOrderQueryBond(IQueryAtom arg0, IQueryAtom arg1, IBond.Order arg2) {
		super(arg0, arg1, arg2);
	}
	public TopologyOrderQueryBond(IQueryAtom arg0, IQueryAtom arg1, IBond.Order arg2, boolean inRing) {
		super(arg0, arg1, arg2);
		this.inRing = inRing;
	}	
	/* (non-Javadoc)
	 * @see org.openscience.cdk.isomorphism.matchers.OrderQueryBond#matches(org.openscience.cdk.interfaces.Bond)
	 */
	
	@Override
	public boolean matches(IBond arg0) {
		return super.matches(arg0) && (
		(arg0.getFlag(CDKConstants.ISINRING) && inRing) ||
		(!arg0.getFlag(CDKConstants.ISINRING) && !inRing)
		);
	}

	/**
	 * @return Returns the inRing.
	 */
	public boolean isInRing() {
		return inRing;
	}
	/**
	 * @param inRing The inRing to set.
	 */
	public void setInRing(boolean inRing) {
		this.inRing = inRing;
	}
}
