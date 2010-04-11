package toxtree.plugins.smartcyp.cyp450;


import java.util.Comparator;

import org.openscience.cdk.Atom;

import toxtree.plugins.smartcyp.cyp450.MoleculeKU.SMARTCYP_PROPERTY;



public class AtomComparator implements Comparator<Atom> {

	private final int before = -1;
	private final int equal = 0;		// Only used for symmetric atoms, not atoms with same Score
	private final int after = 1;

	double currentAtomScore;
	double comparisonAtomScore;
	double currentAtomAccessibility;
	double comparisonAtomAccessibility;



	// Atoms sorted by Energy and A
	// My implementation of compare, compares E and A
	public int compare(Atom currentAtom, Atom comparisonAtom) {

		return this.compareScore(currentAtom, comparisonAtom);

	}



	private int compareScore(Atom currentAtom, Atom comparisonAtom){
		
		// Set Score values
		if(SMARTCYP_PROPERTY.Score.get(currentAtom) != null)  currentAtomScore = SMARTCYP_PROPERTY.Score.get(currentAtom).doubleValue();
		if(SMARTCYP_PROPERTY.Score.get(comparisonAtom) != null)  comparisonAtomScore = SMARTCYP_PROPERTY.Score.get(comparisonAtom).doubleValue();
		
		// Dual null Scores
		if (SMARTCYP_PROPERTY.Score.get(currentAtom) == null && SMARTCYP_PROPERTY.Score.get(comparisonAtom) == null){					
			//If scores are null the Energies are too, then compare the Accessibility
			return this.compareAccessibility(currentAtom, comparisonAtom);
		}

		// Single null scores
		else if(SMARTCYP_PROPERTY.Score.get(currentAtom) == null) return after;
		else if(SMARTCYP_PROPERTY.Score.get(comparisonAtom) == null) return before;

		// Compare 2 numeric scores
		else if(currentAtomScore < comparisonAtomScore) return before;
		else if(currentAtomScore > comparisonAtomScore) return after;

		// Distinguish symmetric atoms
		else return this.compareMorganNumberAndHcount(currentAtom, comparisonAtom);

	}



	private int compareAccessibility(Atom currentAtom, Atom comparisonAtom){

		// Compare 2 numeric Accessibility values
		currentAtomAccessibility = SMARTCYP_PROPERTY.Accessibility.get(currentAtom).doubleValue();
		comparisonAtomAccessibility = SMARTCYP_PROPERTY.Accessibility.get(comparisonAtom).doubleValue();
		
		if(currentAtomAccessibility < comparisonAtomAccessibility) return after;
		else if(currentAtomAccessibility > comparisonAtomAccessibility) return before;

		// Distinguish symmetric atoms
		else return this.compareMorganNumberAndHcount(currentAtom, comparisonAtom);

	}



	private int compareMorganNumberAndHcount(Atom currentAtom, Atom comparisonAtom){

		// Symmetric
		if(SMARTCYP_PROPERTY.MorganNumber.get(currentAtom).doubleValue() == SMARTCYP_PROPERTY.MorganNumber.get(comparisonAtom).doubleValue() 
				&& currentAtom.getHydrogenCount() == comparisonAtom.getHydrogenCount()) return equal;

		// Non-symmetric
		else return after;
	}


}

