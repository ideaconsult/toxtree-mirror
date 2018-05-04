package toxtree.tree.cramer3.rules;

import java.util.Hashtable;
import java.util.Map;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.exceptions.DecisionMethodException;
import toxtree.tree.BundleRuleResource;
import toxtree.tree.cramer3.groups.E_Groups;
import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.SMARTSException;

public class RuleQ18_JM extends RuleSMARTSSubstructureTagged implements
		IRuleSMARTS {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4636737246727317637L;
	protected Hashtable<String, ISmartsPattern<IAtomContainer>> uniqueGroups;

	public RuleQ18_JM() throws SMARTSException {
		super();
		for (String[] smarts : getSMARTS())
			super.addSubstructure(smarts[0], smarts[1], false);

		BundleRuleResource.retrieveStrings(this, examples);
		super.setContainsAllSubstructures(false);
		uniqueGroups = new Hashtable<String, ISmartsPattern<IAtomContainer>>();
		uniqueGroups.put(E_Groups.mercaptan.name(),
				createSmartsPattern(E_Groups.mercaptan.getSMARTS(), false));
		uniqueGroups.put(E_Groups.thioester.name(),
				createSmartsPattern(E_Groups.thioester.getSMARTS(), false));
		uniqueGroups.put(E_Groups.polysulfide.name(),
				createSmartsPattern(E_Groups.polysulfide.getSMARTS(), false));
		uniqueGroups
				.put(E_Groups.aliphatic_chain.name(),
						createSmartsPattern(
								E_Groups.aliphatic_chain.getSMARTS(), false));
	}

	private static final String _18K_alcohol = "K. secondary alcohol directly bonded to a terminal alkene";
	private static final String _18K_ketone = "K. secondary ketone directly bonded to a terminal alkene";
	private static final String _18L = "L. αβ-unsaturated aldehyde  or ketone";
	private static final String _18J_alpha = "L. alpha-unsaturated aldehyde  or ketone";
	private static final String _18J_beta = "L. beta-unsaturated aldehyde  or ketone ";

	@Override
	public String[][] getSMARTS() {
		return new String[][] {
				{
						_18J_alpha,
						"[C;!R;$([CX4H2][OX2H]),$([CX3H1]=[OX1]),$([CX3H0]([OX2H])=[OX1])][C;$([C]([C])([C][C][C][C])[C][C][C][C])][C][C][C][C]" },
						  //"[C;!R;$([CX4H2][OX2H]),$([CX3H1]=[OX1]),$([CX3H0]([OX2H])=[OX1])][C][C][C][C]" },
				{
						_18J_beta,
						"[C;!R;$([CX4H2][OX2H]),$([CX3H1]=[OX1]),$([CX3H0]([OX2H])=[OX1])][C][C;$([C]([C][C])([C][C][C][C])[C][C][C][C])][C][C][C][C]" },
						  //"[C;!R;$([CX4H2][OX2H]),$([CX3H1]=[OX1]),$([CX3H0]([OX2H])=[OX1])][C][C;$([C][C][C][C])]" },
				//{ _18K_alcohol, "[CX3H2]=[CX3H][C;$([CX4][#6])][OX2H]" },
				{ _18K_alcohol, "[CX3H2]=[CX3][C;$([CX4H][#6])][OX2H]" },
				{ _18K_ketone, "[CX3H2]=[CX3][CX3](=O)[#6]" },
				{
						_18L,
						"[CX3;$([C](=O)(C)[C]),$([CH](=[O])[C])](=[OX1])[C]=[#6;$([CH1]),$([CH2]),$([#6;H0])]" } };
	}

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		Map<String, Integer> histogram = tagAtoms(mol, selected, uniqueGroups);
		int allatoms = mol.getAtomCount();
		if (uniqueGroup(allatoms, E_Groups.mercaptan.name(), histogram))
			return true;
		if (uniqueGroup(allatoms, E_Groups.polysulfide.name(), histogram))
			return true;
		if (uniqueGroup(allatoms, E_Groups.thioester.name(), histogram))
			return true;

		for (IAtom atom : mol.atoms())
			atom.removeProperty(tag);

		histogram = tagAtoms(mol, selected, smartsPatterns);

		if (histogram.get(_18J_alpha) != null
				|| histogram.get(_18J_beta) != null) {
			IRingSet rings = hasRingsToProcess(mol);
			if ((rings == null) || (rings.getAtomContainerCount() == 0)) {
				return true;
			} else
				logger.fine("Cyclic structure, not allowed by 18J");
		}
		if (histogram.get(_18K_alcohol) != null)
			return true;
		if (histogram.get(_18K_ketone) != null)
			return true;
		if (histogram.get(_18L) != null) {
			return true;
		}
		return false;

	}

}
