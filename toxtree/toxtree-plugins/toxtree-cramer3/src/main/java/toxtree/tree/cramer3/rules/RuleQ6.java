package toxtree.tree.cramer3.rules;

import java.util.Map;
import java.util.logging.Level;

import org.openscience.cdk.graph.Cycles;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IImplementationDetails;
import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolFlags;
import toxTree.tree.rules.RuleCarbohydrate;
import toxtree.tree.BundleRuleResource;
import toxtree.tree.cramer3.groups.E_Groups;

/**
 * Derived from Cramer rules Q5
 * {@link toxTree.tree.cramer.RuleSimplyBranchedAliphaticHydrocarbon} with
 * addition
 * 
 * @author nina
 * 
 */
public class RuleQ6 extends RuleSMARTSSubstructureTagged implements IImplementationDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8381134067452027516L;

	/**
	 * Is the substance a simply branched
	 * <ul>
	 * <li>(I) acyclic hydrocarbon containing alkane and/or alkene moieties (but
	 * not alkyne moieties),</li>
	 * <li>(II) a monocyclic or terpene bicyclic, or</li>
	 * <li>(III) a simple carbohydrate?</li>
	 * </ul>
	 * 
	 * @throws Exception
	 */
	protected RuleCarbohydrate carbohydrate;

	public RuleQ6() throws Exception {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		super.addSubstructure(E_Groups.isoprene.name(), E_Groups.isoprene.getSMARTS(), false);
		carbohydrate = new RuleCarbohydrate();
	}

	@Override
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		return this.verifyRule(mol, null);
	}

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected) throws DecisionMethodException {
		MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
		if (mf == null)
			throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);

		int c = 0;
		int h = 0;
		int o = 0;
		for (IAtom atom : mol.atoms()) {
			if ("C".equals(atom.getSymbol()))
				c++;
			else if ("H".equals(atom.getSymbol()))
				h++;
			else if ("O".equals(atom.getSymbol()))
				o++;
		}

		if ((mol.getAtomCount() == (c + h))) { // hydrocarbon
			if (mf.isOpenChain()) {
				logger.log(Level.FINE, "Q6.1 Acyclic hydrocarbon");
				return !mf.isAcetylenic();
			} else
				return verifyTerpene(mol, selected, c, h);

		} else if ((mol.getAtomCount() == (c + h + o)) && carbohydrate.verifyRule(mol, selected)) {
			logger.log(Level.FINE, "Q6.3. Simple carbohydrate");
			return true;
		}
		return false;
	}

	protected boolean verifyTerpene(IAtomContainer mol, IAtomContainer selected, int c, int h)
			throws DecisionMethodException {
		if (c == 10 || c == 15) {
			if (((c / 5) == (h / 8)) && ((c % 5) == 0) && ((h % 8) == 0)) {
				logger.log(Level.FINE, String.format("Q6.B Terpene formula C%dH%d ", c, h));
				int ncycles = Cycles.essential(mol).numberOfCycles();
				if (ncycles > 0 && ncycles < 3) {
					logger.log(Level.FINE, String.format("Q6.B %d rings", ncycles));
					Map<String, Integer> histogram = tagAtoms(mol, selected, smartsPatterns);
					// at least one isoprene unit
					Object count = histogram.get(E_Groups.isoprene.name());
					logger.log(Level.FINE, String.format("Atoms in isoprene uint(s)\t%s", count));
					return (count != null && ((Integer) count).intValue() > 0);
					// all atoms match isoprene unit(s)
					// int allatoms = mol.getAtomCount();
					// some examples fail if checking for all atoms being part
					// of isoprene unit
					// if (uniqueGroup(allatoms, E_Groups.isoprene.name(),
					// histogram)) return true;
				}
			}
		} else logger.log(Level.FINE, "Q6.B not C=10 or C=15");
		return false;
	}

	@Override
	public String getImplementationDetails() {

		return super.getImplementationDetails() + "\ttoxTree.tree.rules.RuleCarbohydrate";
	}
}
