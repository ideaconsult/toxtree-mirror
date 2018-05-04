package toxtree.tree;

import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.silent.AtomContainerSet;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import ambit2.smarts.CMLUtilities;
import ambit2.smarts.SMIRKSManager;
import ambit2.smarts.SMIRKSReaction;
import net.idea.modbcum.p.DefaultAmbitProcessor;
import toxTree.core.IImplementationDetails;
import toxTree.exceptions.ReactionException;

public class Reactor extends DefaultAmbitProcessor<IAtomContainer,IAtomContainerSet> implements IImplementationDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4413944157455777834L;
	protected final transient SMIRKSManager smrkMan;
	private SMIRKSReaction[] reactions;
	private String[][] smirks;

	public String[][] getSmirks() {
		return smirks;
	}

	/**
	 * <pre>
	 * String[][] smirks = {
	 * 			{ "Aliphatic hydroxylation", "[C;X4:1][H:2]>>[C:1][O][H:2]" },
	 * 			{ "Aromatic hydroxylation", "[c:1][H:2]>>[c:1][O][H:2]" }
	 * 			}
	 * </pre>
	 * 
	 * @param smirks
	 */
	public void setSmirks(String[][] smirks) {
		this.smirks = smirks;
		reactions = new SMIRKSReaction[smirks.length];
		setEnabled(true);
	}

	public Reactor() {
		smrkMan = new SMIRKSManager(SilentChemObjectBuilder.getInstance());
		smrkMan.setFlagProcessResultStructures(true);
		smrkMan.setFlagAddImplicitHAtomsOnResultProcess(true);
		setEnabled(false);
	}
	public Reactor(String[][] smirks) {
		this();
		setSmirks(smirks);
	}
	@Override
	public IAtomContainerSet process(IAtomContainer molecule)
			throws ReactionException, CloneNotSupportedException {

		IAtomContainerSet products = null;
		for (int r = 0; r < smirks.length; r++) {
			if (reactions[r] == null) {
				reactions[r] = smrkMan.parse(smirks[r][1]);
				if (!"".equals(smrkMan.getErrors())) 
					throw new ReactionException("Parse SMIRKS error:" + smirks[r][1] + " " +smrkMan.getErrors());
				
			}
			IAtomContainer reactant = molecule.clone();
			try {
				if (smrkMan.applyTransformation(reactant, reactions[r])) {
					AtomContainerManipulator
							.percieveAtomTypesAndConfigureAtoms(reactant);
					reactant.setProperty("REACTION", "PRODUCT");
					//clear the flags just in case, we'll run MolAnalyser and ring detections afterwards
					for (IBond bond : reactant.bonds()) {
						bond.setIsInRing(false);
					}
					for (IAtom a : reactant.atoms()) {
						a.removeProperty(CMLUtilities.RingData2);
						a.removeProperty(CMLUtilities.RingData);
					}					
					
					IAtomContainerSet set = ConnectivityChecker
							.partitionIntoMolecules(reactant);
					if (products==null) products = new AtomContainerSet();
					products.add(set);
				}
			} catch (Exception x) {
				throw new ReactionException(x);
			}
		}
		return products;
	}

	@Override
	public String getImplementationDetails() {

		StringBuffer b = new StringBuffer();
		b.append("\t\tName\tSMIRKS\n");
		for (String[] s : smirks) {
			b.append("<b>");
			b.append(s[0]);
			b.append("</b>");
			b.append("\t");			
			b.append(s[1]);
			b.append("\n");
		}
		return b.toString();
	}
}
