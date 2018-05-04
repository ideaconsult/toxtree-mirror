package toxtree.tree.cramer3.rules;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IBond.Order;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.smiles.SmilesGenerator;

import ambit2.smarts.query.SMARTSException;
import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolFlags;
import toxtree.tree.BundleRuleResource;
import toxtree.tree.cramer3.groups.E_Groups;

/**
 * For this question, if  A or B or C is true, then Q16 = YES
 *		If A and B and C are false, then Q16 = NO
 * @author nina
 *
 */
public class RuleQ16 extends RuleSMARTSSubstructureTagged implements
		IRuleSMARTS {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8965415169616600433L;
	private static final String tag = RuleQ16.class.getName();

	/*
	 * 16. Is the structure a linear or simply branched aliphatic substance
	 * containing any one or a combination of only the following functional
	 * groups:
	 */

	public RuleQ16() throws SMARTSException {
		super();
		BundleRuleResource.retrieveStrings(this, examples);
		for (String[] smarts : getSMARTS())
			super.addSubstructure(smarts[0], smarts[1], false);
		super.setContainsAllSubstructures(false);
	}

	private static final E_Groups[] tags_16A = { E_Groups.primary_alcohol,
			E_Groups.methanol, E_Groups.secondary_alcohol, E_Groups.aldehyde,
			E_Groups.carboxylic_acid, E_Groups.ester };

	private static final E_Groups[] tags_16B = { E_Groups.ketone_16B,
			E_Groups.ketal };

	private static final String[] tags_16C = { E_Groups.acetal.name(),
			E_Groups.tertiary_alcohol.name(), E_Groups.primary_amine.name(),
			"tertiary amine (aromatic, aliphatic, mixed)",
			"amide", "methoxy or ethoxy",
			E_Groups.sulfide.name(), E_Groups.polyoxyethylene.name(), };

	private static final String[] tags_16C_mercapctanetc = {E_Groups.polysulfide.name(),  E_Groups.mercaptan.name(), E_Groups.thioester.name()};


	@Override
	public String[][] getSMARTS() {
		return new String[][] {
				// 16A
				{ E_Groups.methanol.name(), E_Groups.methanol.getSMARTS() },
				// 16A
				{ E_Groups.primary_alcohol.name(),
						E_Groups.primary_alcohol.getSMARTS() },
				{ E_Groups.secondary_alcohol.name(),
						E_Groups.secondary_alcohol.getSMARTS() },
				{ E_Groups.aldehyde.name(), E_Groups.aldehyde.getSMARTS() },
				{ E_Groups.carboxylic_acid.name(),
						E_Groups.carboxylic_acid.getSMARTS() },
				{ E_Groups.ester.name(), E_Groups.ester.getSMARTS() },
				{ E_Groups.ketone_16B.name(), E_Groups.ketone_16B.getSMARTS() },
				{ E_Groups.ketal.name(), E_Groups.ketal.getSMARTS() },
				{ E_Groups.acetal.name(), E_Groups.acetal.getSMARTS() },
				{ E_Groups.tertiary_alcohol.name(),
						E_Groups.tertiary_alcohol.getSMARTS() },
				{ E_Groups.primary_amine.name(),
						E_Groups.primary_amine.getSMARTS() },
				{ "tertiary amine (aromatic, aliphatic, mixed)",
						"[#6][$([NX3H0])]([#6])[#6]" },
				{ "amide",
						//"[#6][CX3](=O)[$([NX3H0]([#6])[#6]),$([NX3H2])]" },
						"[#6][CX3](=O)[$([NX3H0]([#6])[#6]),$([NX3H2]),$([NX3H])]" },
				{ "methoxy or ethoxy",
						"[#6][OX2H0][C;!R;$([CH3]),$([CX4H2][CH3])]" },
				{ E_Groups.sulfide.name(), E_Groups.sulfide.getSMARTS() },
				{ E_Groups.polysulfide.name(), E_Groups.polysulfide.getSMARTS() },
				{ E_Groups.mercaptan.name(), E_Groups.mercaptan.getSMARTS() },
				{ E_Groups.thioester.name(), E_Groups.thioester.getSMARTS() },
				{ E_Groups.polyoxyethylene.name(),
						E_Groups.polyoxyethylene.getSMARTS() },
				{ E_Groups.acetylenic.name(), E_Groups.acetylenic.getSMARTS() },
				{ E_Groups.aliphatic_chain.name(),
						E_Groups.aliphatic_chain.getSMARTS() }

		};
	}

	@Override
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		return this.verifyRule(mol, null);
	}

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		// We don't need any ring
		MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
		IRingSet rings = mf.getRingset();
		if (rings != null && rings.getAtomContainerCount() > 0) {
			logger.fine("At least one ring");
			return false;
		}
		//try {
		//System.out.println(SmilesGenerator.generic().create(mol));
		//} catch (Exception x) {}
		Map<String, Integer> histogram = tagAtoms(mol, selected, smartsPatterns);
		if (histogram.get(E_Groups.acetylenic.name()) != null)
			return false;

		int count = 0;
		Iterator<Entry<String, Integer>> i = histogram.entrySet().iterator();
		while (i.hasNext())
			count += i.next().getValue().intValue();

		//untagged atoms
		if (mol.getAtomCount() > count) {
			logger.fine(String.format("Q16. untagged atoms (i.e. forbidden groups found) %s\ttagged %s\t%s",
					mol.getAtomCount(), count, histogram));			
			for (IAtom atom : mol.atoms()) {
				if (atom.getProperty(tag)==null)
					System.out.println(String.format("Q16. untagged atom %s",
							atom.getAtomTypeName()));
			}
			return false;
		}	

		// 16.A
		int count_taga = 0;
		for (E_Groups tag : tags_16A) {

			Object o = histogram.get(tag.name());
			if (o == null)
				continue;
			if ((((Integer) o).intValue()) > tag.natoms(4)) {
				logger.fine(String.format(
						"Q16A. Found %s atoms [allowed %s] groups of [%s]",
						o.toString(), 4, tag));
				return false;
			}
			count_taga++;
		}

		int count_tagb = 0;
		for (E_Groups tag : tags_16B) {
			Object o = histogram.get(tag.name());
			if (o == null)
				continue;
			if ((((Integer) o).intValue()) > tag.natoms(2)) {
				logger.fine(String.format(
						"Q16B. Found %s atoms [allowed %s] groups of [%s]",
						o.toString(), 2, tag));
				return false;
			}
			count_tagb ++;
		}

		int count_tagc = 0;
		for (String tag : tags_16C) {
			Object o = histogram.get(tag);
			if (o == null)
				continue;
			count_tagc++;
		}
		/*
		int count_tagc2 = 0;
		for (String tag : tags_16C_mercapctanetc) {
			Object o = histogram.get(tag);
			if (o == null)
				continue;
			count_tagc2++;
		}
		*/
		//one of each in 16C2 but only in combination of those in A or B , C1
		//if (count_tagc2>0)
		//	return (count_taga + count_tagb + count_tagc)>0;
		//else 
		return (count_taga + count_tagb + count_tagc)>0;

	}

	

	protected boolean isSpecialTagC(String tagValue) {
		return E_Groups.mercaptan.name().equals(tagValue)
				|| E_Groups.thioester.name().equals(tagValue)
				|| E_Groups.polysulfide.name().equals(tagValue);
	}

}
