/*
Copyright Ideaconsult (C) 2005-2006  
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
 * @author nina
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Class5Unknown extends DefaultCategory {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 2349050959864652279L;

	/**
	 * 
	 */
	public Class5Unknown() {
		super("\"Class 5 Not possible to classify according to these rules\".",5);
		setExplanation("Compounds that cannot be classified as belonging to class 1, 2 or 3 and that are not known to be compounds acting by a specific mechanism can only be classified as \"not possible to classify according to these rules\".");
		setThreshold("");		
	}

	/**
	 * @param name
	 * @param id
	 */
	public Class5Unknown(String name, int id) {
		super(name, id);
	}

}
