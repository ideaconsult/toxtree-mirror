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
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.isomorphism.matchers.SymbolQueryAtom;

/**
 * TODO add description
 * @author Vedina
 * <b>Modified</b> 2005-8-19
 */
public class TopologySymbolQueryAtom extends SymbolQueryAtom {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 8817462179487866862L;
	protected boolean inRing = false;
	/**
	 * 
	 */
	public TopologySymbolQueryAtom(boolean inRing,IChemObjectBuilder builder) {
		super(builder);
		this.inRing = inRing;
	}

	/**
	 * @param atom
	 */
	public TopologySymbolQueryAtom(IAtom atom) {
		super(atom);
	}
	public TopologySymbolQueryAtom(IAtom atom, boolean inRing) {
		super(atom);
		this.inRing = inRing;
	}
	@Override
	public boolean matches(IAtom atom) {
		return super.matches(atom) && (
				(atom.getFlag(CDKConstants.ISINRING) && inRing) ||
				(!atom.getFlag(CDKConstants.ISINRING) && !inRing))
				;		
    };	
}
