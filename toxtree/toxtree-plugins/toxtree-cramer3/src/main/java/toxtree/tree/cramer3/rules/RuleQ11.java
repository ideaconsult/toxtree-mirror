package toxtree.tree.cramer3.rules;

import java.util.Iterator;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRing;

import ambit2.smarts.query.SMARTSException;
import toxTree.exceptions.DecisionMethodException;
import toxtree.tree.BundleRuleResource;

public class RuleQ11 extends RuleRingsSMARTSSubstituents implements IRuleSMARTS {
	private static final String tag = RuleQ11.class.getName();
	/**
	 * 
	 */
	private static final long serialVersionUID = 2553043397388260375L;

	/*
	 * 11. Is the heterocyclic ring substituted by groups other than
	 * 
	 * A. simple hydrocarbons including a) aliphatic linear, branched chain, and
	 * bridged chain structures, an alicyclic ring fused or unfused, and/or one
	 * aryl ring either fused or unfused, AND/OR
	 * 
	 * B. primary, secondary or tertiary alcohols, aldehydes, acetals, ketones,
	 * ketals, acids, esters, sulfides, methyl or ethyl ethers on any of the
	 * ring systems cited above?
	 */

	public RuleQ11() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		for (String[] smarts : getSMARTS())
			super.addSubstructure(smarts[0], smarts[1], false);
		/**
		 * TODO - this might need custom code Yes - if at least one of the
		 * substituent is NOT in the list of the groups above No - if all of the
		 * substituents are IN the list of the group above
		 */
		super.setContainsAllSubstructures(false);
	}
	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
	
		Iterator<String> keys = smartsPatterns.keySet().iterator();
		try {
			while (keys.hasNext()) {
				String key = keys.next();
				if (smartsPatterns.get(key).hasSMARTSPattern(mol) > 0) {
					logger.finest("Found " + key + " allowed="
							+ !smartsPatterns.get(key).isNegate());
					// this is not very efficient, as atomcontainer copy is
					// involved
					// todo callback
					IAtomContainer match = smartsPatterns.get(key)
							.getMatchingStructure(mol);
					for (IAtom atom : match.atoms()) {
						atom.setProperty(tag, true);
					}
				}
			}
		} catch (SMARTSException x) {
			throw new DecisionMethodException(x);
		}

		return processRings(mol, null);
	}

	@Override
	protected boolean analyze(IRing r) {
		// only heterocyclic rings
		boolean thering = false;

		for (IAtom atom : r.atoms())
			if (!"C".equals(atom.getSymbol())
					&& (atom.getProperty(tag) == null)) {
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
	public boolean substituentIsAllowed(IAtomContainer a, int[] place)
			throws DecisionMethodException {
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
	protected boolean process(int allowedSubstituents, int allsubstituents) {

		return allowedSubstituents > 0;
	}

	@Override
	public String[][] getSMARTS() {
		return new String[][] {
				//
				// part A
				{"methyl", "[CX4H3;R0]"},
				/*
				{ "aliphatic linear", "[C;R0]!:[C;!$([C;R0](!@[A])!@[C])]" },
				{ "branched chain", "[#6;R0]!@[#6;$([C;R0](!@[A])!@[C])][#6]" },
				{ "bridged chain",
						"[A;R0][A;R0][A;$([A;R0]([A;R0]([A;R0][A;R0])([A;R0][A;R0])))][A;R0][A;R0]" }, // to
																										// be
																										// verified
						/*
				{ "alicyclic ring fused", "[A;R1]@[A;R2]" },
				{ "alicyclic ring unfused", "[A;R1]!@[A;R2]" },
				*/
				{"alicyclic ring","[C;R1]"},//do we want heterorings as substituents?
				{ "aryl ring", "[c;R1]" },
				{ "non sp carbon", "[#6;R0;!^1]" },
				/*
				{ "aryl ring fused", "[a;R2]@[a;R2]" },
				{ "aryl ring unfused", "[a;R1]!@[a;R2]" },
				*/

				// part B
				{ "alcohols attached to a ring","[OX2H]"},
						//"[#6;R;!$(C=O)&!$(CN)&!$(CS)&!$(CP)][OX2H]" },

				{ "aldehydes or ketones attached to a ring",
						"[$([CX3]([#6])),$([CX3H])](=O)[#6]" },

				{ "acetals or ketals attached to a ring",
						"[$([CX4]([#6])[#6]),$([CX4H][#6]),$([CX4H2])]([O][#6])([O][#6])" },

				{ "esters attached to a ring", "[#6][CX3](=O)[OX2H0][#6]" },

				{ "sulfides attached to a ring",
						"[#6][SX2H0;!$([S][#6](~[O])[#6]);!$([S]=[OX1])]" },

				{ "methyl or ethyl ethers attached to a ring",
						"[OD2]([#6])[$([CH3]),$([CH2][CH3])]" }

		};

	}

}
