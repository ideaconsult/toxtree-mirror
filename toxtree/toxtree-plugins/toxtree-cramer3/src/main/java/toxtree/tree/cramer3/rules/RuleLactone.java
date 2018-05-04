package toxtree.tree.cramer3.rules;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.exceptions.DecisionMethodException;
import toxtree.tree.cramer3.groups.E_Groups;
import ambit2.smarts.query.SMARTSException;

public abstract class RuleLactone extends RuleSMARTSSubstructureHydrolysis {
	protected static final String tag = RuleLactone.class.getName();
	protected String tag_ring = "ring";
	/**
	 * 
	 */
	private static final long serialVersionUID = 4887225841264312163L;

	public RuleLactone() throws SMARTSException {
		super();
	}
	
	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		boolean result = super.verifyRule(mol, selected);
		//cleanup
		for (IAtom atom : mol.atoms()) {
			atom.removeProperty(tag_ring);
			atom.removeProperty(tag);
		}
		return result;
	}

	@Override
	public String[][] getSMARTS() {
		return new String[][] { { E_Groups.lactone.name(),
				E_Groups.lactone.getSMARTS() }, };
	}

	protected IRingSet tagRings(IAtomContainer mol) throws DecisionMethodException {
		IRingSet rings = hasRingsToProcess(mol);
		if (rings == null || rings.getAtomContainerCount() == 0)
			throw new DecisionMethodException(
					"This is a lactone' can't be acyclic!");
		else if (rings.getAtomContainerCount() > 1) {
			for (IAtomContainer ring : rings.atomContainers()) {
				for (IAtom atom : ring.atoms()) {
					Object o = atom.getProperty(tag_ring);
					if (o == null)
						atom.setProperty(tag_ring, 1);
					else
						atom.setProperty(tag_ring, ((Integer) o).intValue() + 1);
				}
			}
			/*
			for (IAtom atom : mol.atoms()) {
				System.out.print(String.format("%s:%s:%s",atom.getAtomTypeName(),atom.getProperty(tag_ring),atom.getFlag(CDKConstants.ISAROMATIC)?"aromatic":""));
				System.out.print(" ");
			}
			*/

		}
		return rings;
	}
	protected boolean allowedRingsOfAtom(IRingSet allrings, IAtom atom) {
		return true;
	}
	/**
	 * if we have fused rings, the tag_ring will appear in more than one ring
	 * 
	 * @param mol
	 * @param tag_ring
	 * @return
	 * @throws DecisionMethodException
	 */
	protected boolean isFused(IAtomContainer mol, String tag_ring, IRingSet rings)
			throws DecisionMethodException {
		String key = E_Groups.lactone.name();
		try {
			if (smartsPatterns.get(key).hasSMARTSPattern(mol) > 0) {
				IAtomContainer match = smartsPatterns.get(key)
						.getMatchingStructure(mol);

				for (IAtom atom : match.atoms()) {
					atom.setProperty(tag, key);
					Object o = atom.getProperty(tag_ring);
					if (o != null && ((Integer) o).intValue() > 1) {
						logger.fine(String.format("%s\t%s rings", tag,
								o.toString()));
						return true;
					}
				}
				// if we have fused rings, the tag will appear in more than one
				// ring

			}
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
		return false;
	}
}
