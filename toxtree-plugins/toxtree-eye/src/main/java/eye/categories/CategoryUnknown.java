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

package eye.categories;


import toxTree.tree.DefaultCategory;
import eye.EyeIrritationRules;

/**
 * 
 * Assigned when {@link EyeIrritationRules} are not able to estimate eye  irritation or corrosive potential. 
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Mar 31, 2008
 */
public class CategoryUnknown extends DefaultCategory {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3578629943501329146L;

	/**
	 * 
	 */
	public CategoryUnknown() {
		super("Unknown",11);
		setExplanation("Unable to classify");
	}
	
	
	@Override
	public CategoryType getCategoryType() {
		return CategoryType.hasInconclusiveCategory;
	}
}


