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
package eye.categories;


import toxTree.tree.DefaultCategory;

/**
 * 
 * Assigned when compound is estimated to be not corrosive to skin.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class CategoryNotCorrosive2Skin extends DefaultCategory {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 0;

	/**
	 * 
	 */
	public CategoryNotCorrosive2Skin() {
		super("NOT skin corrosion R34 or R35",1);
		setExplanation("Not corrosive to skin (NOT R34 or R35)");
	}
}

