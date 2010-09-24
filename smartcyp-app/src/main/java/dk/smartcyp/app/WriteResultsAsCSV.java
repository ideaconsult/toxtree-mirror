package dk.smartcyp.app;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.openscience.cdk.Atom;
import org.openscience.cdk.MoleculeSet;

import dk.smartcyp.core.MoleculeKU;
import dk.smartcyp.core.MoleculeKU.SMARTCYP_PROPERTY;



public class WriteResultsAsCSV {

	PrintWriter outfile;
	String moleculeID;
	private String dateAndTime;
	String[] namesOfInfiles;
	

	// DecimalFormat twoDecimalFormat = new DecimalFormat("#.##");
	DecimalFormat twoDecimalFormat = new DecimalFormat();


	public WriteResultsAsCSV(String dateTime, String[] infileNames){
		dateAndTime = dateTime;
		namesOfInfiles = infileNames;

		// DecimalFormat for A
		twoDecimalFormat.setDecimalSeparatorAlwaysShown(false);
		DecimalFormatSymbols decformat = new DecimalFormatSymbols();
		decformat.setDecimalSeparator('.');
		decformat.setGroupingSeparator(',');
		twoDecimalFormat.setMaximumFractionDigits(2);
		twoDecimalFormat.setDecimalFormatSymbols(decformat);
	}



	public void writeCSV(MoleculeSet moleculeSet) {


		try {
			outfile = new PrintWriter(new BufferedWriter(new FileWriter("SMARTCyp_Results_" + this.dateAndTime + ".csv")));
		} catch (IOException e) {
			System.out.println("Could not create CSV outfile");
			e.printStackTrace();
		}

		outfile.println("Molecule,Atom,Ranking,Score,Energy,Accessability");
		Atom currentAtom;
		String currentAtomType;					// Atom symbol i.e. C, H, N, P or S


		// Iterate MoleculKUs
		for (int moleculeIndex=0; moleculeIndex < moleculeSet.getMoleculeCount(); moleculeIndex++) {

			// Set variables
			MoleculeKU moleculeKU = (MoleculeKU) moleculeSet.getMolecule(moleculeIndex);
			moleculeID = moleculeKU.getID();


			// Iterate Atoms
			for(int atomIndex = 0; atomIndex < moleculeKU.getAtomCount()  ; atomIndex++ ){

				currentAtom = (Atom) moleculeKU.getAtom(atomIndex);

				// Match atom symbol
				currentAtomType = currentAtom.getSymbol();
				if(currentAtomType.equals("C") || currentAtomType.equals("N") || currentAtomType.equals("P") || currentAtomType.equals("S")) {


					outfile.print((moleculeIndex + 1) + "," + currentAtom.getSymbol() + "."+ currentAtom.getID() + "," + SMARTCYP_PROPERTY.Ranking.get(currentAtom) + ",");				
					if(SMARTCYP_PROPERTY.Score.get(currentAtom) != null) 
						outfile.print(twoDecimalFormat.format(SMARTCYP_PROPERTY.Score.get(currentAtom)) + "," + SMARTCYP_PROPERTY.Energy.get(currentAtom));
					else outfile.print("-,-");
					outfile.print("," + twoDecimalFormat.format(SMARTCYP_PROPERTY.Accessibility.get(currentAtom)));
					outfile.print("\n");
				}
			}

		}


		outfile.close();
	}
}




