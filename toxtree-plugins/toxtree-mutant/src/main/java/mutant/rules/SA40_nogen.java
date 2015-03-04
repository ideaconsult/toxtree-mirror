/*
Copyright Ideaconsult Ltd. (C) 2005-2007  

Contact: nina@acad.bg

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

package mutant.rules;

import toxTree.tree.rules.StructureAlertCDK;

public class SA40_nogen extends StructureAlertCDK {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8723199576199214305L;

	public SA40_nogen() throws Exception {
		super();
		addSubstructure("c1(OC(C)(C)C(=O)O)ccc([#6,#17])cc1");
		addSubstructure("c1(OCC(=O)[O;H0])cc(Cl)c(Cl)cc1");
		addSubstructure("c1(OCC(=O)[O;H0])c(Cl)cc(Cl)cc1");
		setID("SA40_nogen");
		setTitle("substituted phenoxyacid");
		setExplanation("Nongenotoxic mechanism");

		examples[0] = "c1ccc(OC(C)(C)C(OCC)=O)cc1";
		examples[1] = "Clc1ccc(OC(C)(C)C(OCC)=O)cc1";
		editable = false;
	}

}
