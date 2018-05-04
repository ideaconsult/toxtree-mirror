package toxtree.tree.cramer3.rules;

import java.util.Hashtable;

import org.openscience.cdk.interfaces.IAtomContainer;

import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.SMARTSException;
import toxtree.tree.BundleRuleResource;
import toxtree.tree.cramer3.groups.E_Groups;

/**
 * Very similar to Cramer rule Q18 @{link RuleKetoneAlcoholEtc} with additions
 * 
 * @author nina
 * 
 */
public class RuleQ18_AI extends RuleSMARTSSubstructureTagged implements IRuleSMARTS {

	/**
	 * 
	 */
	private static final long serialVersionUID = -422190692814677580L;

	public RuleQ18_AI() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		for (String[] smarts : getSMARTS())
			super.addSubstructure(smarts[0], smarts[1], false);

		super.setContainsAllSubstructures(false);
	}

	@Override
	public String[][] getSMARTS() {
		return new String[][] {
				// { E_Groups.alpha_diketone.name(),
				// E_Groups.alpha_diketone.getSMARTS()},
				// A
				{ "allyl alcohol", "[CH2]=[C;H1][C;H2][O]" },
				{ "ester of allyl alcohol", "[CH2]=[C;H1][C;H2][OX2H0][CH1](=[O])[#6]" },

				// B
				{ "allyl mercaptan", "[CH2]=[C;H1][C;H2][SX2H]" },
				{ "allyl thioesters", "[CH2]=[C;H1][C;H2][S][C;$([C](=[OX1])[#6])]" },
				{ "allyl polysulfide", "[CH2]=[C;H1][C;H2][#16X2H0][#16X2H0][#6,#16]" },
				// C
				{ "allyl amine", "[CH2]=[C;H1][C;H2][NX3H2]" },
				// D
				{ "acrolein", "[CH2]=[C;H1][C;H1]=[OX1]" }, { "methacrolein", "[CH2]=[C;H1](C)[C;H1]=[OX1]" },
				// E
				{ "acrylic acid", "[CH2]=[C;H1][C;H1](=[OX1])[OX2H]" },
				{ "methacrylic acid", "[CH2]=[C;H1](C)[C;H1](=[OX1])[OX2H]" },
				// F
				{ "acetylenic substance", "[$([CX2]#C)]" },
				// G. An acyclic gamma diketone, its precursor diol, monoketone
				// or precursor hydrocarbon of less than ten carbon
				{ "acyclic gamma diketone",
						"[C][CX3H0;R0](=[OX1])[C;$([C]([C])),$([CH2])][C;$([C]([C])),$([CH2])][CX3H0;R0](=[OX1])[C]" },
				{ "acyclic gamma diol", "[$([CX4]([#6])([OX2H])[#6]),$([CX4H]([OX2H])[#6]),$([CX4H2]([OX2H]))][C][C][$([CX4]([#6])([OX2H])[#6]),$([CX4H]([OX2H])[#6]),$([CX4H2]([OX2H]))]" },
				// H
				{ "vicinal diacetyl groups",
						"[CX3H0;!$([CX3][O])](=[OX1])[CX3H0;!$([CX3][O])](=[OX1])" },
						//"[CX3H0](=[OX1])[CX3H0](=[OX1])" },
				{ "vicynal diols",
						"[CH1]([OH])[CH1]([OH])" } };
	}

	protected static final String[][] smirks = new String[][] {

			{ "allyl alcohol ester hydrolysis",
					"[#6:1]=[C:2][C:3][O:4][C:5](=[O:6])[#6:7].[H][O][H]>>[#6:1]=[C:2][C:3][O][H].[H][O:4][C:5](=[O:6])[#6:7]",
					"C=CCOC(=O)CCCC" },
			{ "allyl thioester hydrolysis",
					"[#6:1]=[C:2][C:3][S:4][C:5](=[O:6])[#6:7].[H][O][H]>>[#6:1]=[C:2][C:3][S:4][H].[H][O][C:5](=[O:6])[#6:7]",
					"C=CCSC(=O)CCCC" } };

}
