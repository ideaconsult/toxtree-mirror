/*
Copyright Ideaconsult Ltd.(C) 2006-2013  
Contact: Ideaconsult Ltd.

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
package sicret.categories;

import toxTree.tree.NonToxicCategory;

/**
 * 
 * Assigned when compound is estimated to be not Irritating or corrosive to skin.
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 * <b>Modified</b> Apr 1, 2013
 */
public class CategoryNotIrritatingOrCorrosive extends NonToxicCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6262205717485184257L;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */


	/**
	 * 
	 */
	public CategoryNotIrritatingOrCorrosive() {
		super("Not Irritating or Corrosive to skin",2);
		setExplanation("Not Irritating or Corrosive to skin (NOT R34, R35 or R38)");
	}

}
