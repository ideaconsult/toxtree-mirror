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
package verhaar.rules;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.tree.rules.RuleRingAllowedSubstituents;

public abstract class RuleRingMainStrucSubstituents extends RuleRingAllowedSubstituents {
	protected transient QueryAtomContainer mainStructure = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -6483397620197083015L;

	public RuleRingMainStrucSubstituents() throws Exception  {
		super();
		mainStructure = createMainStructure();
	}
	protected abstract QueryAtomContainer createMainStructure() throws CDKException;
}
