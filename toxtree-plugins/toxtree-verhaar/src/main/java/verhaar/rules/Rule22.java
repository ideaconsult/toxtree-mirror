/*
Copyright Nina Jeliazkova (C) 2005-2011  
Contact: jeliazkova.nina@gmail.com

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

import verhaar.query.FunctionalGroups;

/**
 * 
 * Anilines with one nitro substituent and/or one to three chlorine substituents, and/or alkyl substituents.
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 * <b>Modified</b> July 12, 2011
 */
public class Rule22 extends Rule21 {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8022147695771746487L;

	public Rule22() throws Exception {
		super();
		id = "2.2";
		setTitle("Be anilines with one nitro substituent and/or one to three chlorine substituents, and/or alkyl substituents");
		setExplanation("Be anilines with one nitro substituent and/or one to three chlorine substituents, and/or alkyl substituents");
		examples[0]= "O=[N+]([O-])c1cccc(c1)Cl";  //not an aniline
		examples[1]= "O=[N+]([O-])c1cccc(N)c1";
		editable = false;
	}
	protected QueryAtomContainer createMainStructure() throws CDKException {
		return FunctionalGroups.aniline();
	}
	@Override
	protected String[] getHalogens() {
		return new String[] {"Cl"};
	}
}
