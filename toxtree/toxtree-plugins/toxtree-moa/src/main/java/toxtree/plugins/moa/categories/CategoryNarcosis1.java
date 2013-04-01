/*
Copyright Ideaconsult Ltd.(C) 2006  
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
package toxtree.plugins.moa.categories;

import toxTree.tree.ToxicCategory;

/**
 * Assigned when compound is estimated to be corrosive to skin. 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class CategoryNarcosis1 extends ToxicCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1965751817452223027L;

	/**
	 * 
	 */
	public CategoryNarcosis1() {
		super("Narcosis I",1);
		setExplanation("Base-line narcosis");

	}
}

