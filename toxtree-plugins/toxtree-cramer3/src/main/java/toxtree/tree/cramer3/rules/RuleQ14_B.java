package toxtree.tree.cramer3.rules;

import java.util.Iterator;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRing;

import ambit2.smarts.query.SMARTSException;
import toxTree.exceptions.DecisionMethodException;
import toxtree.tree.BundleRuleResource;

public class RuleQ14_B extends RuleRingsSMARTSSubstituents implements IRuleSMARTS {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5956724793035095684L;
	/**
	 * 
	 */
	private static final String tag = RuleQ14_B.class.getName();
	protected String[] only_incombination_withothertags = new String[] { "methyl", "ethyl", "aryl ring" };

	public RuleQ14_B() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		for (String[] smarts : getSMARTS())
			super.addSubstructure(smarts[0], smarts[1], false);

		super.setContainsAllSubstructures(false);
	}

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected) throws DecisionMethodException {
		int count = 0;
		int count_combinationonly = 0;
		Iterator<String> keys = smartsPatterns.keySet().iterator();
		try {

			while (keys.hasNext()) {
				String key = keys.next();
				if (smartsPatterns.get(key).hasSMARTSPattern(mol) > 0) {
					count++;
					for (String t : only_incombination_withothertags)
						if (t.equals(key)) {
							count_combinationonly++;
							break;
						}

					logger.fine("Found " + key + " allowed=" + !smartsPatterns.get(key).isNegate());
					// this is not very efficient, as atomcontainer copy is
					// involved
					// todo callback
					IAtomContainer match = smartsPatterns.get(key).getMatchingStructure(mol);
					for (IAtom atom : match.atoms()) {
						atom.setProperty(tag, true);
					}
				}
			}
		} catch (SMARTSException x) {
			throw new DecisionMethodException(x);
		}

		if (count_combinationonly == count) {
			logger.fine(String.format("%d\t%d", count_combinationonly,count));
			return false;
		}
		return processRings(mol, selected);

	}

	@Override
	protected boolean process(int allowedSubstituents, int allsubstituents) {
		if (allsubstituents == 0)
			return false;
		return allowedSubstituents == 0;
	}

	@Override
	protected boolean analyze(IRing r) {
		// only heteroaromatic rings
		boolean thering = false;

		if (!r.getFlag(CDKConstants.ISAROMATIC))
			return false;

		for (IAtom atom : r.atoms())
			if (!"C".equals(atom.getSymbol()) && (atom.getProperty(tag) == null)) {
				atom.setProperty(tag, true);
				thering = true;
				break;
			}
		if (thering)
			for (IAtom atom : r.atoms())
				atom.setProperty(tag, true);
		return thering;
	}

	@Override
	public boolean substituentIsAllowed(IAtomContainer a, int[] place) throws DecisionMethodException {
		// will count only substituents which are not tagged
		for (IAtom atom : a.atoms()) {
			if ("H".equals(atom.getSymbol()))
				continue;
			if (atom.getProperty(tag) == null)
				return true;
		}
		return false;
	}

	@Override
	public String[][] getSMARTS() {
		return new String[][] { { "aryl ring", "[c;R1]" },
				// false negatives are due to methyl, but if adding it , the
				// example No fails, and many more in #58 fail
				// {"aliphatic chain","[C;R0]!@[C;R0]"},
				{ "primary alcohol", "[C;H2][OX2H]" }, { "secondary alcohol", "[C;$([CX4H][#6])][OX2H]" },
				{ "tertiary alcohol", "[C;$([CX4H0]([#6])[#6])][OX2H]" }, { "aldehydes", "[CX3H1](=O)" },
				{ "acetals", "[$([CX4]([#6])[#6]),$([CX4H][#6]),$([CX4H2])]([O][#6])([O][#6])" },
				{ "ketones", "[CX3](=O)[#6]" }, { "ketals", "[$([CX4]([#6])[#6])]([O][#6])([O][#6])" },
				{ "acids", "[C;H1,$(C[#6])](=[OX1])[OH]" }, { "methyl ethers", "[OD2]([#6])[#6;$([CH3])]" },
				{ "ethyl ethers", "[OD2]([#6])[#6;$([CH2][CH3])]" },
				{ "alkyl chain<3", "[c]!@[C;R0]!@[C;R0;!$([CX4;H3])]" }, { "esters", "[CX3](=O)[OX2H0][#6]" },
				{ "sulfides", "[#16X2H0]" }, // won't hit thiol; [SX2] hits
												// Thiol, Sulfide or Disulfide
												// Sulfur
				{ "methyl", "[CX4;H3]" }, { "ethyl", "[CX4;H2][CX4;H3]" }

		};
	}
}
