package toxtree.plugins.smartcyp.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.nonotify.NoNotificationChemObjectBuilder;

import toxTree.core.IMetaboliteGenerator;
import toxTree.tree.AbstractRule;
import ambit2.core.processors.structure.AtomConfigurator;
import ambit2.smarts.IAcceptable;
import ambit2.smarts.SMIRKSManager;
import ambit2.smarts.SMIRKSReaction;
import ambit2.smarts.SmartsConst;
import dk.smartcyp.core.MoleculeKU.SMARTCYP_PROPERTY;
import dk.smartcyp.core.SMARTSData;
import dk.smartcyp.smirks.SMARTCYPReaction;

public abstract class MetaboliteGenerator extends AbstractRule implements
		IMetaboliteGenerator, IAcceptable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3202810538351239202L;
	protected SMIRKSManager smrkMan;
	protected int rank;

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public IAtomContainerSet getProducts(IAtomContainer reactant)
			throws Exception {
		IAtomContainerSet products = null;
		AtomConfigurator cfg = new AtomConfigurator();
		if (smrkMan == null) {
			smrkMan = new SMIRKSManager();
			//smrkMan.setSSMode(SmartsConst.SSM_NON_IDENTICAL);
		}
		List<SMARTCYPReaction> reactions = new ArrayList<SMARTCYPReaction>();
		for (IAtom atom : reactant.atoms()) {

			
			Number atom_rank = SMARTCYP_PROPERTY.Ranking.getNumber(atom);
			if (atom_rank == null)
				continue;
			if (atom_rank.intValue() != getRank())
				continue;
			
			SMARTSData data = SMARTCYP_PROPERTY.Energy.getData(atom);
			if (data == null) continue;
				//throw new Exception("Energy property missing for atom of rank "		+ atom_rank);
			if (reactions.indexOf(data.getReaction()) < 0)
				reactions.add(data.getReaction());
		}

		for (SMARTCYPReaction reaction : reactions) {

			SMIRKSReaction smr = smrkMan.parse(reaction.getSMIRKS());

			IAtomContainer product = (IAtomContainer) reactant.clone();

			if (smrkMan.applyTransformation(product, this, smr)) {
				if (products == null)
					products = NoNotificationChemObjectBuilder.getInstance()
							.newInstance(IAtomContainerSet.class);
				product.setID(reaction.toString());
				try {
					cfg.process(product);
				} catch (Exception x) {
				}
				products.addAtomContainer(product);
			} else
				System.err.println(String.format("%s %s", reactant.getID(),
						reaction.name()));
		}

		return products;
	}

	@Override
	public boolean accept(Vector<IAtom> atoms) {

		boolean ok = false;
		for (IAtom atom : atoms) {
			Number atom_rank = SMARTCYP_PROPERTY.Ranking.getNumber(atom);
			if (atom_rank == null)
				continue;
			// System.out.println(String.format("%s %s %d",atom.getID(),atom.getSymbol(),atom_rank));
			if (atom_rank.intValue() == getRank())
				ok = true; // any atom with rank 1 within this mapping
		}
		return ok;
	}

}
