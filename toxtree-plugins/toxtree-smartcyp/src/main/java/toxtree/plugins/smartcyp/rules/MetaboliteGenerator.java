package toxtree.plugins.smartcyp.rules;

import java.util.Vector;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.nonotify.NoNotificationChemObjectBuilder;

import toxTree.core.IMetaboliteGenerator;
import toxTree.tree.AbstractRule;
import toxtree.plugins.smartcyp.SMARTCYPReactions;
import ambit2.smarts.IAcceptable;
import ambit2.smarts.SMIRKSManager;
import ambit2.smarts.SMIRKSReaction;
import dk.smartcyp.core.MoleculeKU.SMARTCYP_PROPERTY;

public abstract class MetaboliteGenerator extends AbstractRule implements IMetaboliteGenerator, IAcceptable {
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
	public IAtomContainerSet getProducts(IAtomContainer reactant) throws Exception {
		IAtomContainerSet products = null;

		if (smrkMan==null) smrkMan = new SMIRKSManager();
		for (SMARTCYPReactions reaction : SMARTCYPReactions.values()) {
			SMIRKSReaction smr = smrkMan.parse(reaction.getSMIRKS());
			IAtomContainer product = (IAtomContainer) reactant.clone();
			if (smrkMan.applyTransformation(product, this,smr)) {
				if (products ==null) products = NoNotificationChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class);
				products.addAtomContainer(product);
			} //else not transformed
		}
		return products;
	}
	
	@Override
	public boolean accept(Vector<IAtom> atoms) {
		boolean ok = false;
		for (IAtom atom: atoms) {
			Number atom_rank = SMARTCYP_PROPERTY.Ranking.get(atom);
			if (atom_rank==null) continue;
			System.out.println(String.format("%s %s %d",atom.getID(),atom.getSymbol(),atom_rank));
			if (atom_rank.intValue()==getRank()) ok = true; //any atom with rank 1
		}
		return ok;
	}
	
}
