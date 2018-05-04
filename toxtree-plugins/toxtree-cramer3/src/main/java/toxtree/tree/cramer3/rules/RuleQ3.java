package toxtree.tree.cramer3.rules;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.BundleRuleResource;
import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.SMARTSException;
import ambit2.smarts.query.SmartsPatternAmbit;

/**
 * Derived from Cramer Q2 {@link toxTree.tree.cramer.RuleToxicFunctionalGroups}
 * with additions and changes
 * 
 * @author nina
 * 
 */
public class RuleQ3 extends RuleSMARTSSubstructureAmbit implements IRuleSMARTS {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2805321618642492895L;
	protected ISmartsPattern<IAtomContainer> nheterocycle;
	private static final String[] rulesA = new String[] { "A.sec_amines",
			"A.sec_amines_salts" };

	public RuleQ3() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		for (String[] smarts : getSMARTS())
			super.addSubstructure(smarts[0], smarts[1], false);
		// there should not be any N heterocycle
		nheterocycle = new SmartsPatternAmbit("[$([#7;R])]");
		super.setContainsAllSubstructures(false);
	}

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		boolean hasnheterocycle = false;
		try {
			hasnheterocycle = (nheterocycle.hasSMARTSPattern(mol) > 0);
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}

		try {
			logger.finer(getID());
			Iterator<Entry<String,ISmartsPattern<IAtomContainer>>> i = smartsPatterns.entrySet().iterator();
			boolean is_true = false;
			String temp_id = "";
			while (i.hasNext()) {
				Entry<String,ISmartsPattern<IAtomContainer>> pattern = i.next();
				temp_id = pattern.getKey();

				if (pattern.getValue().hasSMARTSPattern(mol) > 0) {
					is_true = true;
					if (Arrays.binarySearch(rulesA, temp_id) >= 0) {
						if (hasnheterocycle)
							is_true = false;
					}
				} else
					is_true = false;

				if (is_true && (selected != null)) {
					IAtomContainer hit = pattern.getValue().getMatchingStructure(mol);
					if (hit != null)
						selected.add(hit);
				}

				if (is_true)
					return true;
			}
			return is_true;
		} catch (SMARTSException x) {
			throw new DecisionMethodException(x);
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
	}

	@Override
	public String[][] getSMARTS() {
		return new String[][] {
				//{ rulesA[0], "[#7;X3;H1;!R;!$(NC=O)]" },
				{ rulesA[0], "[#6][$([NX3H1;!$(NC=O)])][#6]" },
				{ rulesA[1], "[$([NX4H2;+1;!R]);!$(NC=O)]" },

				{ "B.CN-(cyano)", "[NX1]#[CX2]" },
				{ "B.N-NO (N-nitroso)", "[#7][NX2]=[OX1]" },
				{ "B.R-NO (nitroso)", "[#6][NX2]=[OX1]" },
				{ "B.CH2N2 (diazo)", "[#6X3]=[NX2+]=[NX1-]" },
				{ "B.CH2N2 (diazo)", "[#6X3-][NX2+]#[NX1]" },
				{ "B.RN=NNH2 (trazeno)", "[#6][NX2]=[NX2][NH2]" },
				{ "B.RN3 (azide)",
						"[$([#6]-[NX2-]-[NX2+]#[NX1]),$([#6]-[NX2]=[NX2+]=[NX1-])]" },
				{ "B.RNCO (isocyanate)", "[#6]N=C=O" },
				{ "B.RNHCOOR (carbamate)", "[#6]N=C=O" },

				{ "C.nitro group", "[$([NX3](=O)=O),$([NX3+](=O)[O-])][!#8]" },
				{
						"C.Quaternary N excluding N-oxides",
						"[NX4;+1;!$([#7+][OX1-])]" },
						{ "C.amine_cl_salt", "[#6][$([NX4H3;+1]),$([NX4H1;+1])][Cl-]" },
				{
						"C.amine_sulfate_salt",
						"[#6][$([NX4H3;+1]),$([NX4H1;+1])][O-][SX4](=[OX1])(=[OX1])[O-][$([NX4H3;+1]),$([NX4H1;+1])][#6]" },
				{ "C.iminium ions",
						"[$([CX3;!R]([#6])[#6]),$([CX3H;!R][#6]),$([CX3H2;!R])]=[$([NX2][#6]),$([NX2H])]" },
				{ "D.Thioamide (RC=SNR)",
						"[C;H1,$(C[#6])](=[SX1])[$([NX3]([#6])[#6]),$([NX3H][#6]),$([NX3H2])]" },
				{
						"D.thiocarbamide (NHC=SNH)",
						"[$([NX3]([#6])[#6]),$([NX3H][#6]),$([NX3H2])]C(=S)[$([NX3]([#6])[#6]),$([NX3H][#6]),$([NX3H2])]" },
				{ "D.thiocarbamate (RNC=SOR)",
						"[C;$(C[O][#6])](=[SX1])([$([NX3]([#6])[#6]),$([NX3H][#6]),$([NX3H2])])" },
				{ "D.thiocarbamate (RNC=OSR)",
						"[C;$(C[S][#6])](=[OX1])([$([NX3]([#6])[#6]),$([NX3H][#6]),$([NX3H2])])" },
				//{ "D.CH2N2 (diazo)", "[#6][NX2]=[NX2][#6]" },
				{ "D.dithiocarbamates (RNHC=SSR)",
						"[C;$(C[S][#6])](=[SX1])([$([NX3]([#6])[#6]),$([NX3H][#6]),$([NX3H2])])" },
				{ "D.carbon disulfide (S=C=S)", "[SX1H0]=[CX2H0]=[SX1H0]" }

		};
	}

}
