package toxtree.tree.cramer3.rules;

import toxtree.tree.BundleRuleResource;
import toxtree.tree.Reactor;
import ambit2.smarts.query.SMARTSException;

public class RuleQ8 extends RuleSMARTSSubstructureHydrolysis {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6748804084637824083L;

	/*
	 * 8. Is the substance heterocyclic because it contains a cyclic hemiacetal,
	 * acetal, hemiketal, ketal, or cyclic carbonate?
	 */

	public Reactor getReactor() {
		return reactor;
	}

	public RuleQ8() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		for (String[] smarts : getSMARTS())
			super.addSubstructure(smarts[0], smarts[1], false);

		super.setContainsAllSubstructures(false);

	}

	@Override
	protected String[][] getSmirks() {
		return new String[][] {
				{
						"acetal hydrolysis",
						"[$([CX4H][#6]),$([CX4H2]):1]([O:2][#6:3])([O:4][#6:5])>>[$([CX4H][#6]),$([CX4H2]):1]=[O].[H][O:4][#6:5].[H][O:2][#6:3]",
						"CCCCOC(CCc1ccccc1)OCCCC" },
				{
						"ketal hydrolysis",
						"[$([CX4]([#6])[#6]):1]([O:2][#6:3])([O:4][#6:5])>>[$([CX4]([#6])[#6]):1]=[O].[H][O:2][#6:3].[H][O:4][#6:5]",
						"COC(C)(C)OC" },
				{
						"hemiacetal hydrolysis",
						"[$([CX4H][#6:3]),$([CX4H2]):1]([OH1:2])([O:4][#6:5])>>[$([CX4H][#6:3]),$([CX4H2]):1]=[O:2].[H][O:4][#6:5]",
						"CCCCOC(O)(C)" },
				{
						"hemiketal hydrolysis",
						"[$([CX4]([#6:3])[#6:6]):1]([OH1:2])([O:4][#6:5])>>[$([CX4]([#6:3])[#6:6]):1]=[O:2].[H][O:4][#6:5]",
						"COC(O)(C)(C)" },
				{
						"cyclic carbonate hydrolysis 5",
						"[OX2H0:1]1[C;R:2][C;R:3][OX2H0:4][CX3H0:5](=[O:6])1>>[OX2H0:1][C;R:2][C;R:3][OX2H0:4].[CX3H0:5](=[O:6])=[O]",
						"" },
				{
						"cyclic carbonate hydrolysis 6",
						"[OX2H0:1]1[C;R:2][C;R:3][C;R:4][OX2H0:5][CX3H0:6](=[O:7])1>>[OX2H0:1][C;R:2][C;R:3][C;R:4][OX2H0:5].[CX3H0:6](=[O:7])=[O]" }

		};
	}

	@Override
	public String[][] getSMARTS() {
		return new String[][] {

				{ "cyclic hemiacetal or hemiketal",
						"[C;R;$([CX4]([#6])[#6]),$([CX4H][#6]),$([CX4H2])]([OH1])([O;R][#6])" },
				{ "cyclic acetal or ketal",
						"[C;R;$([CX4]([#6])[#6]),$([CX4H][#6]),$([CX4H2])]([O;R][#6])([O;R][#6])" },
				{ "cyclic carbonate 5",
						"[OX2H0]1[C;R][C;R][OX2H0][CX3H0](=[O])1" },
				{ "cyclic carbonate 6",
						"[OX2H0]1[C;R][C;R][C;R][OX2H0][CX3H0](=[O])1" } };

	}

}
