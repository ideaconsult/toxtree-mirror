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

package toxTree.tree.demo;

import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.core.IDecisionCategory;
import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNode;
import toxTree.tree.DecisionNodesList;
import toxTree.tree.UserDefinedTree;
import toxTree.tree.categories.DefaultClass1;
import toxTree.tree.categories.DefaultClass2;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import ambit2.smarts.query.SMARTSException;

public class SMARTSTree extends UserDefinedTree {

	/**
     * 
     */
	private static final long serialVersionUID = -1574710229749897050L;

	public SMARTSTree() throws DecisionMethodException {
		super(new CategoriesList(), new DecisionNodesList());
		IDecisionCategory c1 = new DefaultClass1("Not an aldehyde", 1);
		IDecisionCategory c2 = new DefaultClass2("Aldehyde", 2);

		categories.add(c1);
		categories.add(c2);

		RuleSMARTSSubstructureAmbit rs = new RuleSMARTSSubstructureAmbit();
		rs.setTitle("Aldehyde");
		try {
			rs.addSubstructure("Aldehyde", "[$([CX3H][#6]),$([CX3H2])]=[OX1]");
		} catch (SMARTSException x) {
			throw new DecisionMethodException(x);
		}
		rs.setID("1");
		rs.setExampleMolecule(MoleculeFactory.makeAlkane(4), false);

		try {
			rs.setExampleMolecule(
					FunctionalGroups.createAtomContainer("O=C([H])CCC", false),
					true);
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}

		DecisionNodesList nodes = (DecisionNodesList) rules;
		nodes.add(new DecisionNode(rs, null, null, c1, c2));

		setTitle("SMARTS tree");
		setExplanation("DEMO decision tree with a single SMARTS-based rule.");
	}

}
