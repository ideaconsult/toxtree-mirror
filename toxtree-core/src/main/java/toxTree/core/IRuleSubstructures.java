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
package toxTree.core;

import java.util.List;

import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.interfaces.IAtomContainer;

/**
 * 
 * An interface to support substructure rules 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> 2005-10-18
 */
public interface IRuleSubstructures extends IDecisionRule {
	/**
	 * Adds a substructure {@link AtomContainer} to the rule
	 * @param fragment
	 */
	public void addSubstructure(IAtomContainer fragment);
	/**
	 * Returns a substructure {@link AtomContainer} at a given rules
	 * @param index
	 * @return molecule {@link AtomContainer}
	 */
	public IAtomContainer getSubstructure(int index);
	/**
	 * sets substructure at index
	 * @param index
	 * @param atomContainer
	 */
	public void setSubstructure(int index,IAtomContainer atomContainer);
	/**
	 * removes all substructures defined for a rule
	 */
	public void clearSubstructures();
	/**
	 * @return the number of substructures defined for the rule
	 */
	public int getSubstructuresCount() ;
	/**
	 * Removes substructure at a given index
	 * @param index
	 * @return {@link AtomContainer}
	 */
	public IAtomContainer removeSubstructure(int index);
	/**
	 * Returns a list {@link java.util.ArrayList} of {@link AtomContainer} defined for the rule 
	 * @return list {@link List}
	 */
	public List getSubstructures();

}
