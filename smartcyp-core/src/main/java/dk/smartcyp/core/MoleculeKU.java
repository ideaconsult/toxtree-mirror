package dk.smartcyp.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.graph.PathTools;
import org.openscience.cdk.graph.invariant.EquivalentClassPartitioner;
import org.openscience.cdk.graph.matrix.AdjacencyMatrix;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.AtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.smarts.SMARTSQueryTool;

public class MoleculeKU extends AtomContainer implements IAtomContainer {

	public enum SMARTCYP_PROPERTY {
		SymmetryNumber, Score {
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

			public String atomProperty2String(IAtom atom) {
				return String.format("%s:%s", getLabel(), getData(atom)
						.getEnergy());
			}

			@Override
			public Number getNumber(IAtom atom) {
				SMARTSData data = getData(atom);
				return data == null ? null : data.getEnergy();
			}
		},
		Accessibility {
			@Override
			public String getLabel() {
				return "A";
			}
		},
		Reaction {
			@Override
			public String getLabel() {
				return "K";
			}

			public String atomProperty2String(IAtom atom) {
				return String.format("%s:%s", getLabel(), Energy.getData(atom)
						.getReaction().toString());
			}

			public SMARTSData getData(IAtom atom) {
				Object o = atom.getProperty(Energy.name());
				return o instanceof SMARTSData ? (SMARTSData) o : null;
			}

			@Override
			public Number getNumber(IAtom atom) {
				return null;
			}
		},
		SMIRKS {
			@Override
			public String getLabel() {
				return "M";
			}

			public String atomProperty2String(IAtom atom) {
				return String.format("%s:%s", getLabel(), Energy.getData(atom)
						.getReaction().getSMIRKS());
			}

			public SMARTSData getData(IAtom atom) {
				Object o = atom.getProperty(Energy.name());
				return o instanceof SMARTSData ? (SMARTSData) o : null;
			}

			@Override
			public Number getNumber(IAtom atom) {
				return null;
			}
		};

		public String getLabel() {
			return "";
		};

		public void set(IAtom atom, Object value) {
			atom.setProperty(name(), value);
		}

		public SMARTSData getData(IAtom atom) {
			Object o = atom.getProperty(name());
			return o instanceof SMARTSData ? (SMARTSData) o : null;
		}

		public Number getNumber(IAtom atom) {
			Object o = atom.getProperty(name());
			return (o == null) ? null : o instanceof Number ? (Number) o : null;
		}

		public String atomProperty2String(IAtom atom) {
			return String.format("%s:%s", getLabel(), getNumber(atom));
		}

	}

	// Local variables
	private static final long serialVersionUID = 1L;
	AtomComparator atomComparator = new AtomComparator();
	private TreeSet<IAtom> atomsSortedByEnA = new TreeSet<IAtom>(atomComparator);

	// Constructor
	// This constructor also calls the methods that calculate MaxTopDist,
	// Energies and sorts C, N, P and S atoms
	// This constructor is the only way to create MoleculeKU and Atom objects,
	// -there is no add() method
	public MoleculeKU(IAtomContainer iAtomContainer,
			HashMap<String, SMARTSData> SMARTSnEnergiesTable)
			throws CloneNotSupportedException {
		// Calls the constructor in org.openscience.cdk.AtomContainer
		// Atoms are stored in the array atoms[] and accessed by getAtom() and
		// setAtom()
		super(iAtomContainer);
		int number = 1;
		for (int atomIndex = 0; atomIndex < iAtomContainer.getAtomCount(); atomIndex++) {
			iAtomContainer.getAtom(atomIndex).setID(String.valueOf(number));
			number++;
		}
	}

	public void assignAtomEnergies(
			HashMap<String, SMARTSData> SMARTSnEnergiesTable)
			throws CDKException {

		// Variables
		int numberOfSMARTSmatches = 0; // Number of SMARTS matches = number of
										// metabolic sites

		// Iterate over the SMARTS in SMARTSnEnergiesTable
		Set<String> keySetSMARTSnEnergies = (Set<String>) SMARTSnEnergiesTable
				.keySet();
		Iterator<String> keySetIteratorSMARTSnEnergies = keySetSMARTSnEnergies
				.iterator();

		String currentSMARTS = "C";
		SMARTSQueryTool querytool = new SMARTSQueryTool(currentSMARTS,
				SilentChemObjectBuilder.getInstance()); // Creates the Query
														// Tool

		while (keySetIteratorSMARTSnEnergies.hasNext()) {

			try {

				currentSMARTS = keySetIteratorSMARTSnEnergies.next();
				querytool.setSmarts(currentSMARTS);

				// Check if there are any SMARTS matches
				boolean status = querytool.matches(this);
				if (status) {

					numberOfSMARTSmatches = querytool.countMatches(); // Count
																		// the
																		// number
																		// of
																		// matches
					List<List<Integer>> matchingAtomsIndicesList_1; // List of
																	// List
																	// objects
																	// each
																	// containing
																	// the
																	// indices
																	// of the
																	// atoms in
																	// the
																	// target
																	// molecule
					List<Integer> matchingAtomsIndicesList_2 = null; // List of
																		// atom
																		// indices
					SMARTSData data = SMARTSnEnergiesTable.get(currentSMARTS); // Energy
																				// &
																				// SMIRKS
																				// of
																				// currentSMARTS

					// System.out.println(String.format("%s SMARTS %s has %d matches in the molecule %s",
					// data,currentSMARTS,numberOfSMARTSmatches, this.getID()));

					matchingAtomsIndicesList_1 = querytool.getMatchingAtoms(); // This
																				// list
																				// contains
																				// the
																				// C,
																				// N,
																				// P
																				// and
																				// S
																				// atom
																				// indices

					for (int listObjectIndex = 0; listObjectIndex < numberOfSMARTSmatches; listObjectIndex++) {

						matchingAtomsIndicesList_2 = matchingAtomsIndicesList_1
								.get(listObjectIndex); // Contains multiple
														// atoms

						// System.out.println("How many times numberOfSMARTSmatches: "
						// + numberOfSMARTSmatches);
						// System.out.println("atomID "
						// +this.getAtom(atomNr).getID()+ ", energy " + energy);

						// Set the Energies of the atoms
						int indexOfMatchingAtom;
						IAtom matchingAtom;
						for (int atomNr = 0; atomNr < matchingAtomsIndicesList_2
								.size(); atomNr++) { // Contains 1 atom
							indexOfMatchingAtom = matchingAtomsIndicesList_2
									.get(atomNr);

							// An atom can be matched by several SMARTS and thus
							// assigned several energies
							// The if clause assures that atoms will get the
							// lowest possible energy
							matchingAtom = this.getAtom(indexOfMatchingAtom);

							if (SMARTCYP_PROPERTY.Energy.getData(matchingAtom) == null
									|| data.getEnergy() < SMARTCYP_PROPERTY.Energy
											.getData(matchingAtom).getEnergy()) {

								SMARTCYP_PROPERTY.Energy
										.set(matchingAtom, data);
							} else {
								// System.out.println("Higher energy "+data);
							}

							// if
							// (SMARTCYP_PROPERTY.Ranking.getNumber(matchingAtom)!=null
							// &&
							// SMARTCYP_PROPERTY.Ranking.getNumber(matchingAtom).intValue()==1)
							// System.out.println(data );

						}
					}
				}
			} catch (CDKException e) {
				System.out.println("There is something fishy with the SMARTS: "
						+ currentSMARTS);
				throw e;
			}
		}

	}

	// Calculates the Accessabilities of all atoms
	public void calculateAtomAccessabilities()
			throws CloneNotSupportedException {

		int[][] adjacencyMatrix = AdjacencyMatrix.getMatrix(this);

		// Calculate the maximum topology distance
		// Takes an adjacency matrix and outputs and MaxTopDist matrix of the
		// same size
		int[][] minTopDistMatrix = PathTools.computeFloydAPSP(adjacencyMatrix);

		// Find the longest Path of all, "longestMaxTopDistInMolecule"
		double longestMaxTopDistInMolecule = 0;
		double currentMaxTopDist = 0;
		for (int x = 0; x < this.getAtomCount(); x++) {
			for (int y = 0; y < this.getAtomCount(); y++) {
				currentMaxTopDist = minTopDistMatrix[x][y];
				if (currentMaxTopDist > longestMaxTopDistInMolecule)
					longestMaxTopDistInMolecule = currentMaxTopDist;
			}
		}

		// Find the Accessability value ("longest shortestPath") for each atom

		// ITERATE REFERENCE ATOMS
		for (int refAtomNr = 0; refAtomNr < this.getAtomCount(); refAtomNr++) {

			// ITERATE COMPARISON ATOMS
			double highestMaxTopDistInMatrixRow = 0;
			IAtom refAtom;
			for (int compAtomNr = 0; compAtomNr < this.getAtomCount(); compAtomNr++) {
				if (highestMaxTopDistInMatrixRow < minTopDistMatrix[refAtomNr][compAtomNr])
					highestMaxTopDistInMatrixRow = minTopDistMatrix[refAtomNr][compAtomNr];
			}

			refAtom = this.getAtom(refAtomNr);
			// Set the Accessability of the Atom
			SMARTCYP_PROPERTY.Accessibility
					.set(refAtom,
							(highestMaxTopDistInMatrixRow / longestMaxTopDistInMolecule));

			// Calculate the Atom scores
			if (SMARTCYP_PROPERTY.Accessibility.getNumber(refAtom) != null) {
				if (SMARTCYP_PROPERTY.Energy.getData(refAtom) != null) {
					double score = SMARTCYP_PROPERTY.Energy.getData(refAtom)
							.getEnergy()
							- 8
							* SMARTCYP_PROPERTY.Accessibility
									.getNumber(refAtom).doubleValue();
					SMARTCYP_PROPERTY.Score.set(refAtom, score);
				}
			}

		}
	}

	// This method makes atomsSortedByEnA
	public void sortAtoms() throws CDKException {

		IAtom currentAtom;
		String currentAtomType; // Atom symbol i.e. C, H, N, P or S

		// The Symmetry Numbers are needed to compare the atoms (Atom class and
		// the compareTo method) before adding them below
		this.setSymmetryNumbers();

		for (int atomNr = 0; atomNr < this.getAtomCount(); atomNr++) {

			currentAtom = (IAtom) this.getAtom(atomNr);

			// Match atom symbol
			currentAtomType = currentAtom.getSymbol();
			if (currentAtomType.equals("C") || currentAtomType.equals("N")
					|| currentAtomType.equals("P")
					|| currentAtomType.equals("S")) {

				atomsSortedByEnA.add(currentAtom);

			}
		}
	}

	// Symmetric atoms have identical values in the array from
	// getTopoEquivClassbyHuXu
	public void setSymmetryNumbers() throws CDKException {
		IAtom atom;
		// set charges so that they are not null
		for (int atomIndex = 0; atomIndex < this.getAtomCount(); atomIndex++) {
			atom = this.getAtom(atomIndex);
			atom.setCharge((double) atom.getFormalCharge());
		}
		// compute symmetry
		EquivalentClassPartitioner symmtest = new EquivalentClassPartitioner(
				(IAtomContainer) this);
		int[] symmetryNumbersArray = symmtest
				.getTopoEquivClassbyHuXu((IAtomContainer) this);
		for (int atomIndex = 0; atomIndex < this.getAtomCount(); atomIndex++) {
			atom = (IAtom) this.getAtom(atomIndex);
			SMARTCYP_PROPERTY.SymmetryNumber.set(atom,
					symmetryNumbersArray[atomIndex + 1]);
		}
	}

	// This method makes the ranking
	public void rankAtoms() throws CDKException {

		// Iterate over the Atoms in this sortedAtomsTreeSet
		int rankNr = 1;
		int loopNr = 1;
		IAtom previousAtom = null;
		IAtom currentAtom;
		Iterator<IAtom> atomsSortedByEnAiterator = this.getAtomsSortedByEnA()
				.iterator();
		while (atomsSortedByEnAiterator.hasNext()) {

			currentAtom = (IAtom) atomsSortedByEnAiterator.next();

			// First Atom
			if (previousAtom == null) {
			} // Do nothing

			// Atoms have no score, compare Accessibility instead
			else if (SMARTCYP_PROPERTY.Score.getNumber(currentAtom) == null) {
				if (SMARTCYP_PROPERTY.Accessibility.getNumber(currentAtom) != SMARTCYP_PROPERTY.Accessibility
						.getNumber(previousAtom))
					rankNr = loopNr;
			}

			// Compare scores
			else if (SMARTCYP_PROPERTY.Score.getNumber(currentAtom)
					.doubleValue() > SMARTCYP_PROPERTY.Score.getNumber(
					previousAtom).doubleValue())
				rankNr = loopNr;

			// Else, Atoms have the same score
			SMARTCYP_PROPERTY.Ranking.set(currentAtom, rankNr);
			previousAtom = currentAtom;
			loopNr++;
		}

		this.rankSymmetricAtoms();
	}

	// This method makes the ranking of symmetric atoms
	public void rankSymmetricAtoms() throws CDKException {

		IAtom currentAtom;
		String currentAtomType; // Atom symbol i.e. C, H, N, P or S

		for (int atomNr = 0; atomNr < this.getAtomCount(); atomNr++) {

			currentAtom = this.getAtom(atomNr);

			// Match atom symbol
			currentAtomType = currentAtom.getSymbol();
			if (currentAtomType.equals("C") || currentAtomType.equals("N")
					|| currentAtomType.equals("P")
					|| currentAtomType.equals("S")) {

				// This clause finds symmetric atoms which have not been
				// assigned a ranking
				if (SMARTCYP_PROPERTY.Ranking.getNumber(currentAtom) == null) {

					// AtomsSortedByEnA contains the ranked atoms
					// We just need to find the symmetric atom and use its
					// ranking for the unranked symmetric atom
					Iterator<IAtom> atomsSortedByEnAiterator = this
							.getAtomsSortedByEnA().iterator();
					IAtom rankedAtom;
					Number rankNr;
					while (atomsSortedByEnAiterator.hasNext()) {

						rankedAtom = (IAtom) atomsSortedByEnAiterator.next();

						if (SMARTCYP_PROPERTY.SymmetryNumber.getNumber(
								currentAtom).intValue() == SMARTCYP_PROPERTY.SymmetryNumber
								.getNumber(rankedAtom).intValue()) {

							rankNr = SMARTCYP_PROPERTY.Ranking
									.getNumber(rankedAtom);
							SMARTCYP_PROPERTY.Ranking.set(currentAtom, rankNr);

						}
					}

				}

			}
		}
	}

	// Get the TreeSet containing the sorted C, N, P and S atoms
	public TreeSet<IAtom> getAtomsSortedByEnA() {
		return this.atomsSortedByEnA;
	}

	public void setID(String id) {
		super.setID(id);
	}

	public String toString() {
		for (int atomNr = 0; atomNr < this.getAtomCount(); atomNr++)
			System.out.println(this.getAtom(atomNr).toString());
		return "MoleculeKU " + super.toString();
	}

}
