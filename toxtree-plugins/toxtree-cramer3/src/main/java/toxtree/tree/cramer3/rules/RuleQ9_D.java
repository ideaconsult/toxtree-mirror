package toxtree.tree.cramer3.rules;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.exceptions.DecisionMethodException;
import toxtree.tree.BundleRuleResource;
import ambit2.smarts.query.SMARTSException;

public class RuleQ9_D extends RuleLactone {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1620850895326811861L;

	/*
	 * 9D. Is the substance a lactone substituted or fused to another alicyclic
	 * or aromatic ring without being conjugated with the aromatic ring?
	 */

	public RuleQ9_D() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		for (String[] smarts : getSMARTS())
			super.addSubstructure(smarts[0], smarts[1], false);
		super.setContainsAllSubstructures(true);
	}

	/*
	 * @Override public String[][] getSMARTS() {
	 * 
	 * return new String[][] { { "substituted non conjugated lactone",
	 * "[C;R1]1(=[OX1])[O;R1][C;!R1;!$([C;R]=[C;R][C;R]=[C;R])](*)-,=[C;!R1;!$([C;R]=[C;R][C;R]=[C;R])](*)-,=[C;!R1!$([C;R]=[C;R][C;R]=[C;R])](*)-,=[C;!R1!$([C;R]=[C;R][C;R]=[C;R])](*)1"
	 * },
	 * 
	 * { "4-membered lactone fused to aromatic ring",
	 * "[C;R1]1(=[OX1])[O;R1][#6;R,$([#6](c))][#6;R,$([#6](c))]1" }, {
	 * "5-membered lactone fused to aromatic ring",
	 * "[C;R1]1(=[OX1])[O;R1][#6;R,$([#6](c))][#6;R,$([#6](c))][#6;R,$([#6](c))]1"
	 * }, { "6-membered lactone fused to aromatic ring:",
	 * "[C;R1]1(=[OX1])[O;R1][#6;R,$([#6](c))][#6;R,$([#6](c))][#6;R,$([#6](c))][#6;R,$([#6](c))]1"
	 * }, {
	 * "4-membered lactone fused to alicyclic ring without being conjugated with it:"
	 * ,
	 * "[C;R1]1(=[OX1])[O;R1][C;R;!$([#6;R2][#6]=[#6]);!$([#6;R2]=[#6;R2][#6]=[#6])]=[C;R]1"
	 * }, {
	 * "5-membered lactone fused to alicyclic ring without being conjugated with it (1)"
	 * ,
	 * "[C;R1]1(=[OX1])[O;R][C;R]=[C;R;!$([#6;R2][#6]=[#6]);!$([#6;R2]=[#6;R2][#6]=[#6])][C;R]1"
	 * }, {
	 * "5-membered lactone fused to alicyclic ring without being conjugated with it (2)"
	 * ,
	 * "[C;R1]1(=[OX1])[O;R][C;R][C;R;!$([#6;R2][#6]=[#6]);!$([#6;R2]=[#6;R2][#6]=[#6])]=[C;R]1"
	 * }, {
	 * "6-membered lactone fused to alicyclic ring without being conjugated with it (1)"
	 * ,
	 * "[C;R1]1(=[OX1])[O;R1][C;R]=[C;R;!$([#6;R2][#6]=[#6]);!$([#6;R2]=[#6;R2][#6]=[#6])][C;R1][C;R1]1"
	 * }, {
	 * "6-membered lactone fused to alicyclic ring without being conjugated with it (2)"
	 * ,
	 * "[C;R1]1(=[OX1])[O;R1][C;R][C;R1][C;R;!$([#6;R2][#6]=[#6]);!$([#6;R2]=[#6;R2][#6]=[#6])]=[C;R]1"
	 * }, {
	 * "6-membered lactone fused to alicyclic ring without being conjugated with it (3)"
	 * ,
	 * "[C;R1]1(=[OX1])[O;R1][C;R][C;R;!$([#6;R2][#6]=[#6]);!$([#6;R2]=[#6;R2][#6]=[#6])]=[C;R][C;R1]1"
	 * }
	 * 
	 * }; }
	 */
	@Override
	protected String[][] getSmirks() {
		return new String[][] { {
				"hydrolysis of lactones",
				"[C;H1,$(C[#6]):1](=[OX1:2])[O:3][#6:4]>>[C;H1,$(C[#6]):1](=[OX1:2])[O][H].[H][O:3][#6:4]",
				"CC1c2ccccc2OC1=O" },

		};
	}

	@Override
	protected boolean extraTest(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {

		IRingSet rings = tagRings(mol);
		try {
			// substituted
			if (rings != null && rings.getAtomContainerCount() == 1)
				return true;
			if (isFused(mol, tag_ring, rings)) {
				//check for conjugated rings
				for (IAtom atom : mol.atoms())
					if (atom.getProperty(tag) != null) {
						Object o = atom.getProperty(tag_ring);
						if (o != null && ((Integer) o).intValue() > 1) {
							if (!allowedRingsOfAtom(rings, atom))
								return false;
						}
					}

				return true;
			} else // if it's not fused and there are >1 ring, then it is substituted
				return true;
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}

	}

	// rings should not be conjugated , i.e. not all atoms should be sp2
	/**
	 * returns false if the ring is conjugated (all C atoms are C.sp2)
	 */
	@Override
	protected boolean allowedRingsOfAtom(IRingSet allrings, IAtom atom) {
		IRingSet rings = allrings.getRings(atom);
		int csp2 = 0;
		int cother = 0;
		for (IAtomContainer ring : rings.atomContainers()) {
			for (IAtom a : ring.atoms())
				if ("C".equals(a.getSymbol()))
					if ("C.sp2".equals(a.getAtomTypeName()))
						csp2++;
					else
						cother++;
		}
		logger.fine(String.format(
				"%s conjugated rings\tatoms\tC.sp2:%s\tC[other]:%s",
				cother == 0 ? "" : "NO", csp2, cother));
		return cother > 0;

	}

}
