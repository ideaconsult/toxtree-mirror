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
package toxtree.tree.cramer3;

import toxTree.tree.DefaultCategory;

/**
 * A {@link toxTree.tree.DefaultCategory} descendant, implementation of 
 * Cramer class 2 (see {@link toxTree.tree.RevisedCramerDecisionTree.CramerRules})
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-9
 */
public class CramerClass2 extends DefaultCategory {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -3590968254184549257L;

	/**
	 * 
	 */
	public CramerClass2() {
		super("Intermediate (Class II)",2);
		setExplanation("Substances which possess structures that are less innocuous than class I substances, but do not contain structural features suggestive of toxicity like those substances in class III.");
		setThreshold("Fifth percentile NOEL (mg/kg bw/day)  0.91 Human exposure threshold  (mg/person/day)  0.54");

	}
	
	@Override
	public CategoryType getCategoryType() {
		return CategoryType.InconclusiveCategory;
	}
}
