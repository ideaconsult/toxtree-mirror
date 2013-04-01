/*
Copyright (C) 2005-2013

Contact: www.ideaconsult.net

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package toxtree.plugins.smartcyp.categories;

import toxTree.tree.DefaultCategory;
import toxtree.plugins.smartcyp.SMARTCypTreeResult;

public class SitesRank1 extends DefaultCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8169535167365480219L;
	public SitesRank1() {
		super(String.format(SMARTCypTreeResult.FORMAT, "",1, "sites"), 1);
		setExplanation("SMARTCyp primary sites of metabolism found");
		setThreshold("");
	}
	@Override
	public CategoryType getCategoryType() {
		return CategoryType.hasInconclusiveCategory;
	}
}
