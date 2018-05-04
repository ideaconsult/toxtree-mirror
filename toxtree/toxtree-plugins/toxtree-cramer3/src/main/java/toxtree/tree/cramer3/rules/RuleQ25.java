package toxtree.tree.cramer3.rules;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.logging.Level;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRing;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.cramer.RuleRingComplexSubstituents30;
import toxtree.tree.BundleRuleResource;
import toxtree.tree.cramer3.groups.E_Groups;
import ambit2.smarts.query.SMARTSException;

/**
 * Similar to Cramer rules Q30 {@link RuleRingComplexSubstituents30} with
 * addtions and changes
 * 
 * @author nina
 * 
 */
public class RuleQ25 extends RuleRingsSMARTSSubstituents implements IRuleSMARTS {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4261398581406881430L;
	private static final String tag = RuleQ25.class.getName();


	public RuleQ25() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		for (String[] smarts : getSMARTS())
			super.addSubstructure(smarts[0], smarts[1], Boolean.parseBoolean(smarts[2]));
		super.setContainsAllSubstructures(false);
	}


	/**
	 * SMARTS of substituents Not complete
	 */
	@Override
	public String[][] getSMARTS() {
		return new String[][] {
				{ "7 carbons (not allowed)", "C!@C!@C!@C!@C!@C!@C", "true" },
				{ "acetylenic not allowed", "C#C", "true" },
				{"hydroxyl FG attached to a ring","[#6;R][OX2H]","false"},
				{ "methoxy attached to a ring", "[#6;R][OX2H0][CH3]", "false" },
				{ "ethoxy FG attached to a ring", "[#6;R][OX2H0][CX4H2][CH3]", "false" },
				//{ E_Groups.aliphatic_chain.name(), E_Groups.aliphatic_chain.getSMARTS(), "false" },
				// aliphatic groups of 1-6 carbons - code? TODO
				// saturated or unsaturated hydrocarbon (but not acetylenic) -
				// code?

				{ "non sp carbon", "[#6;R0;!^1]" ,"false"},
				{  E_Groups.alcohol.name(), E_Groups.alcohol.getSMARTS(), "false" },
				{ "aldehyde/ketone", "[CX3](=O)[#6]", "false" },
				{ "carbocylic acid", "C(=[OX1])[O]", "false" },
				{ "monosulfide,not mercaptan", "[#6][SX2H0][!#16]", "false" }  
				
				
		};
	}
	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
	
		Iterator<String> keys = smartsPatterns.keySet().iterator();
		try {
			while (keys.hasNext()) {
				String key = keys.next();
				if (smartsPatterns.get(key).hasSMARTSPattern(mol) > 0) {
					logger.fine("Found " + key + " allowed="
							+ !smartsPatterns.get(key).isNegate());
					
					if (smartsPatterns.get(key).isNegate()) return true;
					// this is not very efficient, as atomcontainer copy is
					// involved
					// todo callback
					IAtomContainer match = smartsPatterns.get(key)
							.getMatchingStructure(mol);
					for (IAtom atom : match.atoms()) 
						atom.setProperty(tag, true);
					
				}
			}
		} catch (SMARTSException x) {
			throw new DecisionMethodException(x);
		}

		return processRings(mol, null);
	}

	@Override
	protected boolean analyze(IRing r) {
		return true;
	}

	@Override
	public boolean substituentIsAllowed(IAtomContainer a, int[] place)
			throws DecisionMethodException {
		// will count only substituents which are not tagged
		int untagged = 0;
		for (IAtom atom : a.atoms()) {
			if ("H".equals(atom.getSymbol()))
				continue;
			Object tagValue = atom.getProperty(tag);
			if (tagValue == null) {
				logger.log(Level.FINEST, String.format("Untagged atom %s", atom.getSymbol()));
				untagged ++;
			} else
				logger.log(Level.FINEST, String.format("Tagged atom %s\t%s", atom.getSymbol(),tagValue));
		}
		logger.log(Level.FINE, String.format("Untagged atoms %s/%s", untagged,a.getAtomCount()));
		return untagged >0;
	}

	@Override
	protected boolean process(int allowedSubstituents, int allsubstituents) {
		logger.log(Level.FINE, String.format("allowedSubstituents %d", allowedSubstituents));
		return allowedSubstituents > 0;
	}

}
