package toxtree.tree.cramer3.rules;

import ambit2.smarts.query.SMARTSException;
import toxTree.tree.cramer.RuleAcyclicAcetalEsterOfQ30;
import toxtree.tree.BundleRuleResource;

/**
 * Looks like similar to {@link RuleAcyclicAcetalEsterOfQ30}, although not
 * specified as such by the rule authors.
 * 
 * @author nina
 * 
 */
public class RuleQ1 extends RuleSMARTSSubstructureHydrolysis implements IRuleSMARTS {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2378697331944455708L;

	public RuleQ1() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		for (String[] smarts : getSMARTS())
			super.addSubstructure(smarts[0], smarts[1], false);
		// will look for either of these
		super.setContainsAllSubstructures(false);

	}

	@Override
	protected String[][] getSmirks() {
		return new String[][] {
				// carbon-based ester, including di-and tri-esters hydrolysis:
				// in
				// progress
				{ "ester hydrolysis",
						"[C;H1,$(C[#6]):1](=[OX1:2])[O:3][#6:4]>>[C;H1,$(C[#6]):1](=[OX1:2])[OH].[H][O:3][#6:4]",
						"CCCCCC(OCC=C)=O" },
				{ "orthoester hydrolysis",
						"[$([CX4]([#6])),$([CX4H]):1]([O:2][#6:3])([O][#6])([O][#6])>>[$([CX4]([#6])),$([CX4H]):1](=[O])([O:2][#6:3])",
						"O(C)C(C)(OC)OC" },
				//{ "thioester hydrolysis",
						//"[#6:5][#6:1](=[OX1:2])[S:3][#6:4]>>[#6:5][#6:1](=[OX1:2])[OX2H].[#6:4][SH:3]", "CC(=O)SC" },
				{ "thioester hydrolysis",
						"[$([#6]),$([H]):5][#6:1](=[OX1:2])[S:3][#6:4]>>[$([#6]),$([H]):5][#6:1](=[OX1:2])[OX2H].[#6:4][SH:3]", "CC(=O)SC" },
				{ "acetal hydrolysis",
						"[$([CX4H][#6]),$([CX4H2]):1]([O:2][#6:3])([O:4][#6:5])>>[$([CX4H][#6]),$([CX4H2]):1]=[O].[H][O:4][#6:5].[H][O:2][#6:3]",
						"CCCCOC(CCc1ccccc1)OCCCC" },
				{ "ketal hydrolysis",
						"[$([CX4]([#6])[#6]):1]([O:2][#6:3])([O:4][#6:5])>>[$([CX4]([#6])[#6]):1]=[O].[H][O:2][#6:3].[H][O:4][#6:5]",
						"COC(C)(C)OC" },
				{ "hemiacetal hydrolysis",
						"[$([CX4H][#6:3]),$([CX4H2]):1]([OH1:2])([O:4][#6:5])>>[$([CX4H][#6:3]),$([CX4H2]):1]=[O:2].[H][O:4][#6:5]",
						"CCCCOC(O)(C)" },
				{ "hemiketal hydrolysis",
						"[$([CX4]([#6:3])[#6:6]):1]([OH1:2])([O:4][#6:5])>>[$([CX4]([#6:3])[#6:6]):1]=[O:2].[H][O:4][#6:5]",
						"COC(O)(C)(C)" },

				{ "carbonate hydrolysis", // CO2 and H20 not included
						"[#6:1][O:2][C](=[O])[O:3][#6:4]>>[#6:1][O:2][H].[#6:4][O:3]", "O=C(OC)OC" },

				{ "disulfide hydrolysis", "[#6,H:1][#16:2][#16:3][#6,H:4]>>[#6,H:1][#16:2].[#16:3][#6,H:4]",
						"O=CC(SSC)(C)C" },
				{ "polysulfide reduction",
						"[#6,#16:1][#16X2H0:2][#16X2H0:3][#6,#16:4]>>[#6,#16:1][#16X2H:2].[#6,#16:4][#16X2H:3]",
						"O=CC(SSSC)(C)C" },
				{ "sulfoxide reduction", "[SX3:1](=[OX1])([#6:2])([#6:3])>>[SX3:1]([#6:2])([#6:3])", "CS(=O)CCC" }

				,
				{ "Reaction hydrolise P(=O)(OR)(OR)OR",
						"[O:4]=[P:5]([O:6][#6:1])([O:2])([O:3])>>[O:4]=[P:5]([O:6])([O:2])([O:3]).[#6:1][OH]",
						"O=P(O)(OCCNC(=O)C(O)C)O" }
				/*
				 * these are hydrolysis reactins from CramerRules - adding them
				 * doesn't seem to make a difference {
				 * "Reaction hydrolise S(=O)(=O)O",
				 * "[O:1]=[S:2](=[O:3])([O:4][#6:5])([#6:6])>>[O:1]=[S:2](=[O:3])([O:4][H])([#6:6]).[#6:5][OH]"
				 * , "CCCS(=O)(=O)OC" }
				 * 
				 * 
				 * { "Reaction hydrolise C(=S)O",
				 * "[#6:5][#6:1](=[SX1:2])[O:3][#6:4]>>[#6:5][#6:1](=[SX1:2])[OX2H].[#6:4][O:3]"
				 * , "CC(=S)OCCC" } { "Reaction hydrolise S(=S)(S)",
				 * "[C;H1,$(C[#6]):1](=[SX1:2])[S:3][#6:4]>>[C;H1,$(C[#6]):1](=[SX1:2])[OH].[H][S:3][#6:4]"
				 * , "CC(=S)SCCC" }
				 */
		};
	}

	@Override
	public String[][] getSMARTS() {
		// add more smarts for the rest of the required functional groups
		return new String[][] { { "ester (but not lactone)", "[C;H1,$(C[#6])](=[OX1])[O;R0][#6]" },
				{ "triester", "[#6][CX3](=[O])[OX2H0][C;$([C][C]([OX2H0][C](=[O])[#6])[C][OX2H0][C](=[O])[#6])]" },
				{ "orthoester", "[$([CX4]([#6])),$([CX4H])]([O][#6])([O][#6])([O][#6])" },
				//{ "thioester", "[C;$(C[#6])](=[OX1])[S][#6]" },
				{ "thioester", "[C;$(C[#6]),$(C[H])](=[OX1])[S][#6]" },
				{ "acetal", "[$([CX4H][#6]),$([CX4H2])]([O][#6])([O][#6])" },
				{ "ketal", "[$([CX4]([#6])[#6])]([O][#6])([O][#6])" },
				{ "hemiacetal", "[$([CX4H][#6]),$([CX4H2])]([OH1])([O][#6])" },
				{ "hemiketal", "[$([CX4]([#6])[#6])]([OH1])([O][#6])" },
				{ "polysulfide ", "[#6,#16][#16X2H0][#16X2H0][#6,#16]" },
				//{ "polysulfide", "[R0;#6]!@[#16X2H0]!@[#16X2H0]!@[#6,#16]" },
				{ "sulfoxide", "[SX3](=[OX1])([#6])([#6])" }, { "carbonates", "[CX3H0](=[O])([OX2H0][#6])[OX2H0][#6]" },
				{ "formates", "[C;!R;H1](=[OX1])[O][#6]" }, { "phosphates", "P(=O)(O)(O)(O)" }
				// , {" S(=O)(=O)O","S(=O)(=O)O"}

				// , {" C(=S)O","C(=S)O"}
				// , {" S(=S)(S)","S(=S)(S)"}

		};

	}

	/**
	 * If yes, the substance is expected to undergo hydrolysis or reduction,
	 * consider the resulting product structures, in turn, in question 2. Note:
	 * The highest CDT class for any of the products will be assigned to the
	 * original substance.
	 */

}
