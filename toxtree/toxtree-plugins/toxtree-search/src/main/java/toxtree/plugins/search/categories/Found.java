/*
Copyright Ideaconsult Ltd (C) 2005-2013 
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
package toxtree.plugins.search.categories;

import toxTree.tree.DefaultCategory;



/**
 * 
 * Not found
 * @author Nina Jeliazkova jelaizkova.nina@gmail.com
 * <b>Modified</b>July 11, 2011
 */
public class Found extends DefaultCategory {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8773253240824666710L;

	public Found() {
		super("Found",1);
		setExplanation("Found");
		setThreshold("");
	}
	@Override
	public CategoryType getCategoryType() {
		return CategoryType.InconclusiveCategory;
	}
}
