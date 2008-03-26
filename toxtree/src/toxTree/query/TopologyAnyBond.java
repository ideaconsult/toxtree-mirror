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
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.isomorphism.matchers.IQueryBond;


/** TODO add description
 * @author ThinClient
 * <b>Modified</b> 2005-8-24
 */
public class TopologyAnyBond extends org.openscience.cdk.Bond implements IQueryBond {
	protected boolean inRing = false;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -557613393294271482L;

	/**
	 * 
	 */
	public TopologyAnyBond(boolean inRing) {
		super();
		this.inRing = inRing;
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public TopologyAnyBond(IAtom arg0, IAtom arg1,boolean inRing) {
		super(arg0, arg1);
		this.inRing = inRing;
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public TopologyAnyBond(IAtom arg0, IAtom arg1, IBond.Order arg2,boolean inRing) {
		super(arg0, arg1, arg2);
		this.inRing = inRing;
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public TopologyAnyBond(IAtom arg0, IAtom arg1, IBond.Order arg2, int arg3,boolean inRing) {
		super(arg0, arg1, arg2, arg3);
		this.inRing = inRing;
	}
	public boolean matches(IBond bond) {
		return 
		(bond.getFlag(CDKConstants.ISINRING) && inRing) ||
		(!bond.getFlag(CDKConstants.ISINRING) && !inRing)
		;

	}
}
