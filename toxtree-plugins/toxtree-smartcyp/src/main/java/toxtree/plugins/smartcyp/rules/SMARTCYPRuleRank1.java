package toxtree.plugins.smartcyp.rules;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.nonotify.NoNotificationChemObjectBuilder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.openscience.jchempaint.renderer.selection.IChemObjectSelection;
import org.openscience.jchempaint.renderer.selection.SingleSelection;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;
import toxTree.ui.tree.categories.CategoriesRenderer;
import toxtree.plugins.smartcyp.cyp450.MoleculeKU;
import toxtree.plugins.smartcyp.cyp450.SMARTSnEnergiesTable;
import toxtree.plugins.smartcyp.cyp450.MoleculeKU.SMARTCYP_PROPERTY;
import ambit2.base.exceptions.AmbitException;
import ambit2.base.interfaces.IProcessor;
import ambit2.core.data.MoleculeTools;
import ambit2.jchempaint.CompoundImageTools;

public class SMARTCYPRuleRank1 extends AbstractRule {
	protected static final CategoriesRenderer categoriesRenderer = new CategoriesRenderer(4);
	/**
	 * 
	 */
	private static final long serialVersionUID = -8348454708993409448L;
	protected int rank;
	
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public SMARTCYPRuleRank1() {
		this(1);
	}
	public SMARTCYPRuleRank1(int rank) {
		super();
		setRank(rank);
		setID(String.format("%d",rank));
		setTitle("SMARTCyp primary sites of metabolism");
		setExplanation(String.format("Rank%d",rank));
		setEditable(false);
		setExamples(new String[] {
				"",
				"c1cc(c(cc1Cl)Cl)Cl"
		});
	}

	/**
	 * returns true if at least one atom has a score assigned
	 */
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		try {
			IAtomContainer newmol = mol;
			for (IAtom atom: mol.atoms())
				if (SMARTCYP_PROPERTY.Accessibility.get(atom)==null) {
					AtomContainerManipulator.removeHydrogens(mol);
					newmol = calculate(mol);
					break;
				}
			
			for (IAtom atom: newmol.atoms()) {
				Number atom_rank = SMARTCYP_PROPERTY.Ranking.get(atom);
				if((atom_rank!=null) && hasRank(atom_rank.intValue()))
					return true;
			}
			return false;			
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
	}
	
	
	protected boolean hasRank(int atom_rank) {
		return atom_rank==getRank();
	}
	public MoleculeKU calculate(IAtomContainer iAtomContainer) throws DecisionMethodException {
		try {
			MoleculeKU moleculeKU = new MoleculeKU(iAtomContainer, SMARTSnEnergiesTable.getSMARTSnEnergiesTable());
			logger.info("************** Matching SMARTS to assign Energies **************");
			moleculeKU.assignAtomEnergies(SMARTSnEnergiesTable.getSMARTSnEnergiesTable());	

			logger.info("************** Calculating Accessabilities and Atom Scores**************");
			moleculeKU.calculateAtomAccessabilities();

			logger.info("************** Identifying, sorting and ranking C, N, P and S atoms **************");
			moleculeKU.sortAtoms();
			moleculeKU.rankAtoms();		
			return moleculeKU;
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
	}
	@Override
	   public IProcessor<IAtomContainer, IChemObjectSelection> getSelector() {
    	return new IProcessor<IAtomContainer, IChemObjectSelection>() {
    		public IChemObjectSelection process(IAtomContainer mol)
    				throws AmbitException {
    			try {
    				IAtomContainer selected = MoleculeTools.newAtomContainer(NoNotificationChemObjectBuilder.getInstance());
	    			verifyRule(mol, selected);
	    			return new SingleSelection<IAtomContainer>(selected);
    			} catch (DecisionMethodException x) {
    				throw new AmbitException(x);
    			}
    		}
    		public boolean isEnabled() {
    			return true;
    		}
    		public long getID() {
    			return 0;
    		}
    		public void setEnabled(boolean arg0) {
    		}
    	};
    }
	
	public boolean  verifyRule(org.openscience.cdk.interfaces.IAtomContainer mol,IAtomContainer selected) throws DecisionMethodException {
		try {
			IAtomContainer newmol = mol;
			for (IAtom atom: mol.atoms())
				if (SMARTCYP_PROPERTY.Accessibility.get(atom)==null) {
					AtomContainerManipulator.removeHydrogens(mol);
					newmol = calculate(mol);
					break;
				}
			
			for (IAtom atom: newmol.atoms()) {
				Number atom_rank = SMARTCYP_PROPERTY.Ranking.get(atom);
				if((atom_rank!=null) && hasRank(atom_rank.intValue())) {
						atom.setProperty(CompoundImageTools.SELECTED_ATOM_COLOR,categoriesRenderer.getShowColor(atom_rank.intValue()-1));
					selected.addAtom(atom);
				}
			}
			return selected.getAtomCount()>0;
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
	}
	@Override
	public boolean isImplemented() {
		return true;
	}
	
}
