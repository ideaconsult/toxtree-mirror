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
/**
 * Created on 2005-5-2

 * @author Nina Jeliazkova nina@acad.bg
 *
 * Project : toxtree
 * Package : toxTree.tree.rules
 * Filename: RuleCyclopropaneEtc.java
 */
package toxTree.tree.cramer;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.query.MolFlags;
import toxTree.query.QueryAtomContainers;
import toxTree.tree.rules.RuleRingAllowedSubstituents;

/**
 * Rule 25 of the Cramer scheme (see {@link toxTree.tree.cramer.CramerRules})
 * 
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class RuleCyclopropaneEtc extends RuleRingAllowedSubstituents {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -5837604671480301679L;

	/**
	 * Constructor
	 * 
	 */
	public RuleCyclopropaneEtc() throws Exception {
		super();
		id = "25";
		title = "Cyclopropane, etc. (see explanation)";
		explanation.append("<html>");
		explanation.append("Is the substance <UL>");
		explanation
				.append("<LI>(a)a cyclopropane or cyclobutane with only the substituents mentioned in Q24 or");
		explanation.append("<LI>(b)a mono- or bicyclic sulphide or mercaptan?");
		explanation.append("</html>");
		examples[0] = "NC1CCC1";
		examples[1] = "OC1CCC1";
		editable = false;
	}

	@Override
	protected QueryAtomContainers initQuery() throws Exception {
		QueryAtomContainers q = super.initQuery();
		setQuery(q);
		addSubstructure(FunctionalGroups.alcohol(true));
		addSubstructure(FunctionalGroups.aldehyde());
		addSubstructure(FunctionalGroups.acetal());
		addSubstructure(FunctionalGroups.sidechain_ketone());
		addSubstructure(FunctionalGroups.carboxylicAcid());
		addSubstructure(FunctionalGroups.ester());
		addSubstructure(FunctionalGroups.sulphonate(new String[] { "Na", "K",
				"Ca" }));

		return q;
	}

	@Override
	public boolean isImplemented() {
		return true;
	}

	@Override
	protected IRingSet hasRingsToProcess(IAtomContainer mol)
			throws DecisionMethodException {
		MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
		if (mf == null)
			throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);

		IRingSet rings = mf.getRingset();
		if (rings == null)
			return null;
		if (rings.getAtomContainerCount() > 1)
			return null; // monocarbocyclic
		IRing r = (IRing) rings.getAtomContainer(0);
		if (r.getAtomCount() > 4)
			return null;
		Object o = r.getProperty(MolAnalyser.HETEROCYCLIC);
		if ((o != null) && ((Boolean) o).booleanValue())
			return null;
		else
			return rings;

	}
}
