package dk.smartcyp.core;


import java.util.Comparator;

import org.openscience.cdk.interfaces.IAtom;

import dk.smartcyp.core.MoleculeKU.SMARTCYP_PROPERTY;



public class AtomComparator implements Comparator<IAtom> {

	private final int before = -1;
	private final int equal = 0;		// Only used for symmetric atoms, not atoms with same Score
	private final int after = 1;

	double currentAtomScore;
	double comparisonAtomScore;
	double currentAtomAccessibility;
	double comparisonAtomAccessibility;



	// Atoms sorted by Energy and A
	// My implementation of compare, compares E and A
	public int compare(IAtom currentAtom, IAtom comparisonAtom) {

		return this.compareScore(currentAtom, comparisonAtom);

	}



	private int compareScore(IAtom currentAtom, IAtom comparisonAtom){
		
		// Set Score values
		if(SMARTCYP_PROPERTY.Score.getNumber(currentAtom) != null)  currentAtomScore = SMARTCYP_PROPERTY.Score.getNumber(currentAtom).doubleValue();
		if(SMARTCYP_PROPERTY.Score.getNumber(comparisonAtom) != null)  comparisonAtomScore = SMARTCYP_PROPERTY.Score.getNumber(comparisonAtom).doubleValue();
		
		// Dual null Scores
		if (SMARTCYP_PROPERTY.Score.getNumber(currentAtom) == null && SMARTCYP_PROPERTY.Score.getNumber(comparisonAtom) == null){					
			//If scores are null the Energies are too, then compare the Accessibility
			return this.compareAccessibility(currentAtom, comparisonAtom);
		}

		// Single null scores
		else if(SMARTCYP_PROPERTY.Score.getNumber(currentAtom) == null) return after;
		else if(SMARTCYP_PROPERTY.Score.getNumber(comparisonAtom) == null) return before;

		// Compare 2 numeric scores
		else if(currentAtomScore < comparisonAtomScore) return before;
		else if(currentAtomScore > comparisonAtomScore) return after;

		// Distinguish symmetric atoms
		else return this.checksymmetry(currentAtom, comparisonAtom);

	}



	private int compareAccessibility(IAtom currentAtom, IAtom comparisonAtom){

		// Compare 2 numeric Accessibility values
		currentAtomAccessibility = SMARTCYP_PROPERTY.Accessibility.getNumber(currentAtom).doubleValue();
		comparisonAtomAccessibility = SMARTCYP_PROPERTY.Accessibility.getNumber(comparisonAtom).doubleValue();
		
		if(currentAtomAccessibility < comparisonAtomAccessibility) return after;
		else if(currentAtomAccessibility > comparisonAtomAccessibility) return before;

		// Distinguish symmetric atoms
		else return this.checksymmetry(currentAtom, comparisonAtom);

	}

	private int checksymmetry(IAtom currentAtom, IAtom comparisonAtom){

		// Symmetric
		if(SMARTCYP_PROPERTY.SymmetryNumber.getNumber(currentAtom).intValue() == SMARTCYP_PROPERTY.SymmetryNumber.getNumber(comparisonAtom).intValue()) return equal;
		
		// Non-symmetric
		else return after;
	}


}

