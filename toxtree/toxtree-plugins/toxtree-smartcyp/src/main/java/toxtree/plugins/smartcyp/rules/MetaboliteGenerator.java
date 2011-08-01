package toxtree.plugins.smartcyp.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.nonotify.NoNotificationChemObjectBuilder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.core.IMetaboliteGenerator;
import toxTree.tree.AbstractRule;
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
	protected ResourceBundle bundle;
	
	public MetaboliteGenerator() {
		super();
		bundle = ResourceBundle.getBundle(getClass().getName(),Locale.ENGLISH,getClass().getClassLoader());
	}
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
	//	AtomConfigurator cfg = new AtomConfigurator();
		if (smrkMan == null) {
			smrkMan = new SMIRKSManager();
			//smrkMan.setSSMode(SmartsConst.SSM_NON_IDENTICAL_FIRST);
			smrkMan.setSSMode(SmartsConst.SSM_NON_IDENTICAL);
			smrkMan.FlagFilterEquivalentMappings = true;
		}
		List<SMARTCYPReaction> reactions = new ArrayList<SMARTCYPReaction>();
		for (IAtom atom : reactant.atoms()) {

			
			Number atom_rank = SMARTCYP_PROPERTY.Ranking.getNumber(atom);
			if (atom_rank == null)
				continue;
			if (atom_rank.intValue() != getRank())
				continue;
			
			SMARTSData data = SMARTCYP_PROPERTY.Energy.getData(atom);
			if (data == null) {
				if (products == null)
					products = NoNotificationChemObjectBuilder.getInstance()
							.newInstance(IAtomContainerSet.class);
				//IAtomContainer product = NoNotificationChemObjectBuilder.getInstance().newInstance(IAtomContainer.class);
				//product.setID(String.format("No energy for rank 1 atom!"));
				//products.addAtomContainer(product);
				continue;
			}
				//throw new Exception("Energy property missing for atom of rank "		+ atom_rank);
			if (reactions.indexOf(data.getReaction()) < 0)
				reactions.add(data.getReaction());
		}

		for (SMARTCYPReaction reaction : reactions) {

			SMIRKSReaction smr = smrkMan.parse(reaction.getSMIRKS());

			IAtomContainer product = reactant; //(IAtomContainer) reactant.clone();
			IAtomContainerSet rproducts = smrkMan.applyTransformationWithSingleCopyForEachPos(product, this, smr);
			
			if (rproducts!=null) {
			//if (smrkMan.applyTransformation(product, this, smr)) {
				
				if (products == null)
					products = NoNotificationChemObjectBuilder.getInstance()
							.newInstance(IAtomContainerSet.class);
				for (IAtomContainer ac : rproducts.atomContainers()) {
					ac.setID(reaction.toString());
					
					for (IAtom a: ac.atoms()) { 
					   a.setFormalNeighbourCount(null);
					   a.setAtomTypeName(null);
					   a.setHybridization(null);
					}
					//AtomContainerManipulator.clearAtomConfigurations(ac); //not all!
					AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(ac);
					//AtomContainerManipulator.percieveAtomTypesAndConfigureUnsetProperties(ac);
					CDKHueckelAromaticityDetector.detectAromaticity(ac);
					
				}
				products.add(rproducts);
				//product.setID(reaction.toString());
	
				//products.addAtomContainer(product);
			} else {
				if (products == null)
					products = NoNotificationChemObjectBuilder.getInstance()
							.newInstance(IAtomContainerSet.class);
				product = NoNotificationChemObjectBuilder.getInstance().newInstance(IAtomContainer.class);
				product.setID(String.format("Can't generate products! (SMIRKS doesn't match?)<br>%s<br>%s<br>%s",
						reaction.name(),
						reaction.getSMIRKS(),
						smrkMan.getErrors()));
				products.addAtomContainer(product);
				
				System.err.println(String.format("%s %s", reactant.getID(),
						reaction.name()));

			}
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

	@Override
	public String getHelp() {

		return bundle.getString("metabolite_help");
	}
}
