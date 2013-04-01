/*
Copyright Ideaconsult Ltd.(C) 2006-2013  
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
package eye.categories;

import toxTree.tree.NonToxicCategory;

/**
 * Assigned when compound is estimated to be not irritating to skin.
 * <b>Modified</b> Apr 1, 2013
 */
public class CategoryNotIrritating2Eye extends NonToxicCategory {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2478375025406169883L;

	/**
	 * 
	 */
	public CategoryNotIrritating2Eye() {
		super("NOT eye irritation R36",4);
		setExplanation("NOT irritating to eye (NOT R36)");

	}

}

