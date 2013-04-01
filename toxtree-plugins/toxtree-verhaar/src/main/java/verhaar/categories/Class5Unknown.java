/*
Copyright Ideaconsult Ltd. (C) 2005-2013 

Contact: www.ideaconsult.net

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

package verhaar.categories;


import toxTree.tree.DefaultCategory;

/**
 * 
 * Class 5 Not possible to classify according to these rules.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
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
		super("Class 5 (Not possible to classify according to these rules)",5);
		setExplanation("<html><h3>Unknown</h3>Compounds that cannot be classified as belonging to class 1, 2 or 3 <br>and that are not known to be compounds acting by a specific mechanism can only be classified as \"not possible to classify according to these rules\".<html>");
		setThreshold("");		
	}

	/**
	 * @param name
	 * @param id
	 */
	public Class5Unknown(String name, int id) {
		super(name, id);
	}
	
		@Override
	public CategoryType getCategoryType() {
		return CategoryType.hasInconclusiveCategory;
	}

}
