package toxtree.plugins.smartcyp.cyp450;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.openscience.cdk.Atom;
import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.graph.PathTools;
import org.openscience.cdk.graph.matrix.AdjacencyMatrix;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.smiles.smarts.SMARTSQueryTool;


public class MoleculeKU extends AtomContainer implements IMolecule {

	public enum SMARTCYP_PROPERTY {
		MorganNumber,
		Score {
			@Override
			public String getLabel() {
				return "S";
			}
		},
		Ranking {
			@Override
			public String getLabel() {
				return "R";
			}
		},
		Energy {
			@Override
			public String getLabel() {
				return "E";
			}
		},
		Accessibility {
			@Override
			public String getLabel() {
				return "A";
			}
		};

		public String  getLabel()  { return "";};

		public void set(IAtom atom, Number value) {
			atom.setProperty(toString(), value);
		}

		public Number get(IAtom atom) {
			Object o = atom.getProperty(toString());
			return (o==null)?null:(Number)o;
		}

		public String atomProperty2String(IAtom atom) {
			return String.format("%s:%s",getLabel(),get(atom));
		}

	}
	// Local variables
	private static final long serialVersionUID = 1L;	
	AtomComparator atomComparator = new AtomComparator();
	private TreeSet<Atom> atomsSortedByEnA = new TreeSet<Atom>(atomComparator);


	// Constructor
	// This constructor also calls the methods that calculate MaxTopDist, Energies and sorts C, N, P and S atoms
	// This constructor is the only way to create MoleculeKU and Atom objects, -there is no add() method
	public MoleculeKU(IAtomContainer iAtomContainer, HashMap<String, Double> SMARTSnEnergiesTable) throws CloneNotSupportedException
	{
		// Calls the constructor in org.openscience.cdk.AtomContainer
		// Atoms are stored in the array atoms[] and accessed by getAtom() and setAtom()
		super(iAtomContainer);			
		int number = 1;
		for (int atomIndex=0; atomIndex < iAtomContainer.getAtomCount(); atomIndex++) {
			iAtomContainer.getAtom(atomIndex).setID(String.valueOf(number));
			number++;
		}
	}



	public void assignAtomEnergies(HashMap<String, Double> SMARTSnEnergiesTable) throws CDKException {

		// Variables
		int numberOfSMARTSmatches = 0;															// Number of SMARTS matches = number of metabolic sites

		// Iterate over the SMARTS in SMARTSnEnergiesTable
		Set<String> keySetSMARTSnEnergies = (Set<String>) SMARTSnEnergiesTable.keySet();
		Iterator<String> keySetIteratorSMARTSnEnergies = keySetSMARTSnEnergies.iterator();

		String currentSMARTS = "C";
		SMARTSQueryTool querytool = new SMARTSQueryTool(currentSMARTS);					// Creates the Query Tool


		while(keySetIteratorSMARTSnEnergies.hasNext()){

			try {
				currentSMARTS = keySetIteratorSMARTSnEnergies.next();
				querytool.setSmarts(currentSMARTS);

				// Check if there are any SMARTS matches
				boolean status = querytool.matches(this);
				if (status) {


					numberOfSMARTSmatches = querytool.countMatches();		// Count the number of matches				
					List<List<Integer>> matchingAtomsIndicesList_1;				// List of List objects each containing the indices of the atoms in the target molecule
					List<Integer> matchingAtomsIndicesList_2 = null;						// List of atom indices
					double energy = (Double) SMARTSnEnergiesTable.get(currentSMARTS);		// Energy of currentSMARTS

					//					System.out.println("\n The SMARTS " + currentSMARTS + " has " + numberOfSMARTSmatches + " matches in the molecule " + this.getID());

					matchingAtomsIndicesList_1 = querytool.getMatchingAtoms();													// This list contains the C, N, P and S atom indices

					for(int listObjectIndex = 0; listObjectIndex < numberOfSMARTSmatches; listObjectIndex++){						

						matchingAtomsIndicesList_2 = matchingAtomsIndicesList_1.get(listObjectIndex);							// Contains multiple atoms

						// System.out.println("How many times numberOfSMARTSmatches: " + numberOfSMARTSmatches);							
						// System.out.println("atomID " +this.getAtom(atomNr).getID()+ ", energy " + energy);


						// Set the Energies of the atoms
						int indexOfMatchingAtom;
						Atom matchingAtom;
						for (int atomNr = 0; atomNr < matchingAtomsIndicesList_2.size(); atomNr++){								// Contains 1 atom
							indexOfMatchingAtom = matchingAtomsIndicesList_2.get(atomNr);

							// An atom can be matched by several SMARTS and thus assigned several energies
							// The if clause assures that atoms will get the lowest possible energy
							matchingAtom = (Atom) this.getAtom(indexOfMatchingAtom);

							if(SMARTCYP_PROPERTY.Energy.get(matchingAtom) == null 
									|| energy < SMARTCYP_PROPERTY.Energy.get(matchingAtom).doubleValue())
								SMARTCYP_PROPERTY.Energy.set(matchingAtom,energy);
						}
					}
				}
			}	
			catch (CDKException e) {System.out.println("There is something fishy with the SMARTS: " + currentSMARTS); e.printStackTrace();}
		}
	}



	// Calculates the Accessabilities of all atoms
	public void calculateAtomAccessabilities() throws CloneNotSupportedException{


		int[][] adjacencyMatrix = AdjacencyMatrix.getMatrix(this);

		// Calculate the maximum topology distance
		// Takes an adjacency matrix and outputs and MaxTopDist matrix of the same size
		int[][] minTopDistMatrix = PathTools.computeFloydAPSP(adjacencyMatrix);


		// Find the longest Path of all, "longestMaxTopDistInMolecule"
		double longestMaxTopDistInMolecule = 0;
		double currentMaxTopDist = 0;
		for(int x = 0; x < this.getAtomCount(); x++){
			for(int y = 0; y < this.getAtomCount(); y++){
				currentMaxTopDist =  minTopDistMatrix[x][y];
				if(currentMaxTopDist > longestMaxTopDistInMolecule) longestMaxTopDistInMolecule = currentMaxTopDist;
			}
		}


		// Find the Accessability value ("longest shortestPath") for each atom

		// ITERATE REFERENCE ATOMS
		for (int refAtomNr=0; refAtomNr < this.getAtomCount(); refAtomNr++){

			// ITERATE COMPARISON ATOMS
			double highestMaxTopDistInMatrixRow = 0;
			IAtom refAtom;
			for (int compAtomNr = 0; compAtomNr < this.getAtomCount(); compAtomNr++){
				if(highestMaxTopDistInMatrixRow < minTopDistMatrix[refAtomNr][compAtomNr]) highestMaxTopDistInMatrixRow = minTopDistMatrix[refAtomNr][compAtomNr];
			}	

			refAtom = this.getAtom(refAtomNr);
			// Set the Accessability of the Atom
			SMARTCYP_PROPERTY.Accessibility.set(refAtom,(highestMaxTopDistInMatrixRow / longestMaxTopDistInMolecule));

			// Calculate the Atom scores
			if(SMARTCYP_PROPERTY.Accessibility.get(refAtom)!=null) {
				if(SMARTCYP_PROPERTY.Energy.get(refAtom) != null){
					double score = SMARTCYP_PROPERTY.Energy.get(refAtom).doubleValue() - 8 * SMARTCYP_PROPERTY.Accessibility.get(refAtom).doubleValue();
					SMARTCYP_PROPERTY.Score.set(refAtom,score);
				}
			}

		}
	}




	//  This method makes atomsSortedByEnA
	public void sortAtoms() throws CDKException{

		Atom currentAtom;
		String currentAtomType;					// Atom symbol i.e. C, H, N, P or S

		for (int atomNr = 0; atomNr < this.getAtomCount(); atomNr++){

			currentAtom = (Atom) this.getAtom(atomNr);

			// Match atom symbol
			currentAtomType = currentAtom.getSymbol();
			if(currentAtomType.equals("C") || currentAtomType.equals("N") || currentAtomType.equals("P") || currentAtomType.equals("S")) {

				// The Morgan Numbers are needed to compare the atoms (Atom class and the compareTo method) before adding them below
				this.setMorganNumbers();
			
				atomsSortedByEnA.add(currentAtom);

			}
		}
	}





	// Symmetric atoms have identical Morgan numbers
	public void setMorganNumbers() throws CDKException{
		/*
		IAtomContainer IatomContainerWithImplicitHydrogens = (IAtomContainer) this;


		CDKAtomTypeMatcher matcher = CDKAtomTypeMatcher.getInstance(IatomContainerWithImplicitHydrogens.getBuilder());

		for(IAtom atom : IatomContainerWithImplicitHydrogens.atoms()){
			IAtomType type = matcher.findMatchingAtomType(IatomContainerWithImplicitHydrogens, atom);
			AtomTypeManipulator.configure(atom, type);
		}

		CDKHydrogenAdder adder = CDKHydrogenAdder.getInstance(IatomContainerWithImplicitHydrogens.getBuilder());
		adder.addImplicitHydrogens(IatomContainerWithImplicitHydrogens);


		long[] morganNumbersArray = org.openscience.cdk.graph.invariant.MorganNumbersTools.getMorganNumbers(IatomContainerWithImplicitHydrogens);
		 */

		long[] morganNumbersArray = org.openscience.cdk.graph.invariant.MorganNumbersTools.getMorganNumbers((IAtomContainer) this);
		Atom atom;
		for(int atomIndex = 0; atomIndex < this.getAtomCount(); atomIndex++){
			atom = (Atom) this.getAtom(atomIndex);
			SMARTCYP_PROPERTY.MorganNumber.set(atom,morganNumbersArray[atomIndex]);
		}
	}

	// This method makes the ranking
	public void rankAtoms(){

		// Iterate over the Atoms in this sortedAtomsTreeSet
		int rankNr = 1;
		int loopNr = 1;
		Atom previousAtom = null;
		Atom currentAtom;
		Iterator<Atom> atomsSortedByEnAiterator = this.getAtomsSortedByEnA().iterator();
		while(atomsSortedByEnAiterator.hasNext()){

			currentAtom = (Atom) atomsSortedByEnAiterator.next();

			// First Atom
			if(previousAtom == null){}				// Do nothing												

			// Atoms have no score, compare Accessibility instead
			else if(SMARTCYP_PROPERTY.Score.get(currentAtom) == null){
				if(SMARTCYP_PROPERTY.Accessibility.get(currentAtom) != SMARTCYP_PROPERTY.Accessibility.get(previousAtom)) rankNr = loopNr;
			} 

			// Compare scores
			else if(SMARTCYP_PROPERTY.Score.get(currentAtom).doubleValue() > SMARTCYP_PROPERTY.Score.get(previousAtom).doubleValue()) rankNr = loopNr;

			// Else, Atoms have the same score
			SMARTCYP_PROPERTY.Ranking.set(currentAtom,rankNr);
			previousAtom = currentAtom;	
			loopNr++;			
		}
	}



	// Get the TreeSet containing the sorted C, N, P and S atoms
	public TreeSet<Atom> getAtomsSortedByEnA(){
		return this.atomsSortedByEnA;
	}

	public void setID(String id){
		super.setID(id);
	}

	public String toString(){
		for(int atomNr=0; atomNr < this.getAtomCount(); atomNr++) System.out.println(this.getAtom(atomNr).toString());
		return "MoleculeKU " + super.toString();
	}


}





