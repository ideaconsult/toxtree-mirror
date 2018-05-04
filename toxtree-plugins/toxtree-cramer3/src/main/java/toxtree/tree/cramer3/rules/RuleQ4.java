package toxtree.tree.cramer3.rules;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.cramer.RuleHasOtherThanC_H_O_N_S2;
import toxtree.tree.BundleRuleResource;
import ambit2.smarts.query.SMARTSException;

/**
 * Derived from Cramer Rules Q3 {@link RuleHasOtherThanC_H_O_N_S2} with
 * additions and changes
 * 
 * @author nina
 * 
 */
public class RuleQ4 extends RuleSMARTSSubstructureTagged {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3818857360140289187L;
	private static final String tag = RuleQ4.class.getName();

	/*
	 * Does this structure contain elements other than C, H, O, N, divalent S,
	 * trivalent S as sulfoxide, pentavalent P bonded only to O with at least
	 * one -P-O- M+ or -P-OH moiety?
	 */

	public RuleQ4() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		// we'll tag C,H,O,N directly in code, much faster
		super.addSubstructure("divalentS", "[#16;v2]", false);
		//super.addSubstructure("trivalentS", "[#16;v3]", false);
		//super.addSubstructure("sulfoxide", "[SX3](=[OX1])([#6])([#6])", false);
		super.addSubstructure("sulfoxide", "[#16X3+]([OX1-])([#6])([#6])", false);
		super.addSubstructure("pentavalent P bonded only to O",
				"[P;v5;!$([P]~[!O]);$([P][OH1,O-])]", false);
		/**
		 * the long original SMARTS is not correct logically (chemically the
		 * groups are fine). it will return true if these groups are not there,
		 * not if there are other groups besides these
		 * */
		/*
		 * super.addSubstructure( "OtherThanC_H_O_N_S_P",
		 * "[!#1;!#6;!#7;!#8;!$([S;v2]);!$([S;v3]);!$([SX3](=[OX1])([#6])([#6]));!$([P;v5](=[O])([O][#6])([O])[$([OX2H0,O-])])]"
		 * , false);
		 */
		super.setContainsAllSubstructures(false);
	}

	private static final String[] e = new String[] { "C", "O", "N" };

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {

		Map<String, Integer> histogram = tagAtoms(mol, selected, smartsPatterns);
		// H is already tagged
		for (IAtom atom : mol.atoms())
			if (atom.getProperty(tag) == null)
				for (String key : e)
					if (key.equals(atom.getSymbol())) {
						Integer i = histogram.get(key);
						if (i == null)
							histogram.put(key, 1);
						else
							histogram.put(key, i.intValue() + 1);
					}

		int allatoms = mol.getAtomCount();
		Iterator<Entry<String, Integer>> i = histogram.entrySet().iterator();
		int c = 0;
		while (i.hasNext())
			c += i.next().getValue();
		logger.fine(String.format("All atoms %d\ttagged %d\tother elements %s",allatoms,c,allatoms>c));		
		return (allatoms > c);
	}
}
