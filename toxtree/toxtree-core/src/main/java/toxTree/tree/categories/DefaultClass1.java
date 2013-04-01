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
package toxTree.tree.categories;

import toxTree.tree.DefaultCategory;

public class DefaultClass1 extends DefaultCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2676192163599898441L;

	public DefaultClass1() {
		super("Default Class 1",1);
	}

	public DefaultClass1(String name, int id) {
		super(name, id);
	}
	
	@Override
	public CategoryType getCategoryType() {
		return CategoryType.hasInconclusiveCategory;
	}

}
