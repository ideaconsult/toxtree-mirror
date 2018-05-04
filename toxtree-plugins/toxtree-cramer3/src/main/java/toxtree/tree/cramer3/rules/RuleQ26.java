package toxtree.tree.cramer3.rules;

import java.util.Enumeration;
import java.util.Iterator;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolFlags;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;
import toxtree.tree.BundleRuleResource;
import ambit2.smarts.query.SMARTSException;

/**
 * Similar to Cramer rules Q6
 * {@link toxTree.tree.cramer.RuleSomeBenzeneDerivatives} with addtions
 * 
 * @author nina
 * 
 */
public class RuleQ26 extends RuleRingsSMARTSSubstituents implements IRuleSMARTS {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7957054526252029184L;
	private static final String tag = RuleQ26.class.getName();

	public RuleQ26() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		// smarts of substituents
		for (String[] smarts : getSMARTS())
			super.addSubstructure(smarts[0], smarts[1], false);

		super.setContainsAllSubstructures(false);
	}

	private static String _aliphatic_chain = "aliphatic chain";

	/**
	 * SMARTS of substituents Not complete
	 */
	@Override
	public String[][] getSMARTS() {
		return new String[][] {
				/*{ "2-alkene", "cCC=C" },
				{ "2-alkyne", "cCC#C" },
				*/
				{ _aliphatic_chain, "C!@C" }, // don't remove, used to mark aliphatic atoms!
				/*
				{ "1'-hydroxy or 1’- hydroxy ester-substituted 2’-alkene or alkyne",
						"c[C;$([C]([O])),$([C]([O])([C](=[O])[OX2][C]))]C=CCc" },
				{ "p-alkoxy", "[C]!:cccc!:[OX2H0][C;!R;$([CH3]),$([CX4H2][CH3])]" } };*/
		{ "p-alkoxy-2-alk", "[C][OX2H0]!:cccc!:[C;$([CX4H2]),$([C][OX2H1]),$([C][OX2H0][C])][C]=,#[C]" } };
	}

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected) throws DecisionMethodException {
		Iterator<String> keys = smartsPatterns.keySet().iterator();
		try {
			// first mark aliphatic atoms
			if (smartsPatterns.get(_aliphatic_chain).hasSMARTSPattern(mol) > 0) {
				IAtomContainer match = smartsPatterns.get(_aliphatic_chain).getMatchingStructure(mol);
				for (IAtom atom : match.atoms())
					atom.setProperty(_aliphatic_chain, true);
			}
			// them mark everything else
			while (keys.hasNext()) {
				String key = keys.next();
				if (_aliphatic_chain.equals(key))
					continue;
				if (smartsPatterns.get(key).hasSMARTSPattern(mol) > 0) {
					logger.fine("Found " + key + " allowed=" + !smartsPatterns.get(key).isNegate());
					// this is not very efficient, as atomcontainer copy is
					// involved
					// todo callback
					// System.out.println(key);
					IAtomContainer match = smartsPatterns.get(key).getMatchingStructure(mol);
					for (IAtom atom : match.atoms())
						atom.setProperty(tag, true);
				}
			}
		} catch (SMARTSException x) {
			throw new DecisionMethodException(x);
		}

		return processRings(mol, selected);
	}

	@Override
	protected boolean analyze(IRing r) {
		// benzene check if aromatic
		if (r.getAtomCount() != 6)
			return false;

		for (IAtom atom : r.atoms())
			if (!"C".equals(atom.getSymbol())) {
				return false;
			}

		for (IAtom atom : r.atoms())
			atom.setProperty(tag, true);
		return true;
	}

	@Override
	public boolean substituentIsAllowed(IAtomContainer a, int[] place) throws DecisionMethodException {
		// will count only substituents which are not tagged
		// System.out.println(place);
		/*
		 * for (IAtom atom : a.atoms()) {
		 * System.out.println(String.format("%s\t%s"
		 * ,atom.getSymbol(),atom.getProperty(tag))); }
		 */
		int aliphaticchain = 0;
		int nottagged = 0;
		int h = 0;
		for (IAtom atom : a.atoms()) {
			if ("H".equals(atom.getSymbol())) {
				h++;
				continue;
			}	
			if (atom.getProperty(tag) == null) {
				nottagged++;
				if (atom.getProperty(_aliphatic_chain) != null)
					aliphaticchain++;
			}

		}
		if ((nottagged + h) == a.getAtomCount())
			return true;
		if (nottagged == aliphaticchain)
			return false;
		return true;
	}

	@Override
	protected boolean process(int allowedSubstituents, int allsubstituents) {
		return allowedSubstituents == 0;
	}
	/*
	 * @Override public boolean verifyRule(IAtomContainer mol, IAtomContainer
	 * selected) throws DecisionMethodException {
	 * 
	 * // row new DecisionMethodException("count C atoms by code"); // Rings
	 * MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS); if (mf ==
	 * null) throw new DecisionMethodException("Not preprocessed"); IRingSet
	 * rings = mf.getRingset(); if (rings == null ||
	 * rings.getAtomContainerCount() == 0) { logger.fine("No rings"); return
	 * false; } else logger.info("At least one ring");
	 * 
	 * for (int r = 0; r < rings.getAtomContainerCount(); r++) { for (IAtom atom
	 * : rings.getAtomContainer(r).atoms()) { // tag the ring atoms
	 * atom.setProperty(tag, true); } } // substituents Enumeration<String> keys
	 * = smartsPatterns.keys(); try { while (keys.hasMoreElements()) { String
	 * key = keys.nextElement(); if
	 * (smartsPatterns.get(key).hasSMARTSPattern(mol) > 0) {
	 * logger.info("Found " + key + " allowed=" +
	 * !smartsPatterns.get(key).isNegate()); // this is not very efficient, as
	 * atomcontainer copy is // involved // todo callback IAtomContainer match =
	 * smartsPatterns.get(key) .getMatchingStructure(mol); for (IAtom atom :
	 * match.atoms()) { if (smartsPatterns.get(key).isNegate()) {
	 * atom.setProperty(tag, false); } else { atom.setProperty(tag, true); } } }
	 * } } catch (SMARTSException x) { throw new DecisionMethodException(x); }
	 * for (IAtom atom : mol.atoms()) { if ("H".equals(atom.getSymbol()))
	 * continue; // System.out.println(String.format("%s\t%s\t%s",
	 * atom.getSymbol(), // atom.getProperty(tag), atom)); if
	 * (atom.getProperty(tag) == null || !(Boolean) atom.getProperty(tag)) {
	 * logger.info("Found untagged atom " + atom.getSymbol()); return false; } }
	 * return true; }
	 */

}
