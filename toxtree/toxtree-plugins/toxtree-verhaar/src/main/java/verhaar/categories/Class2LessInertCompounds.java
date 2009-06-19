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
package verhaar.categories;


import toxTree.tree.DefaultCategory;

/**
 * 
 * Class 2 (less inert compounds).
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Class2LessInertCompounds extends DefaultCategory {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -6642389618393648862L;
	public Class2LessInertCompounds() {
		super("Class 2 (less inert compounds)",2);
		setExplanation("Less inert compounds");
		setThreshold("");
	}
}
