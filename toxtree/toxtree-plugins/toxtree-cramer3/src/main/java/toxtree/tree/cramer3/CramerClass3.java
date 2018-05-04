/*
Copyright Ideaconsult Ltd. (C) 2005-2007 

Contact: nina@acad.bg

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

import toxTree.tree.ToxicCategory;

/**
 * A {@link toxTree.tree.DefaultCategory} descendant, implementation of 
 * Cramer class 3 (see {@link toxTree.tree.RevisedCramerDecisionTree.CramerRules})
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-9
 */
public class CramerClass3 extends ToxicCategory {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -6261392771965489148L;

	/**
	 * 
	 */
	public CramerClass3() {
		super("High (Class III)",3);
		setExplanation("Substances with chemical structures that permit no strong initial presumption of safety or may even suggest significant toxicity or have reactive functional groups.");
		setThreshold("\nFifth percentile NOEL (mg/kg bw/day)  0.15 Human exposure threshold  (mg/person/day)  0.09");
		
	}


}
