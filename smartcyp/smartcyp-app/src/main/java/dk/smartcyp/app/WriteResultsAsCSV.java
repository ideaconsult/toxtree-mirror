package dk.smartcyp.app;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.openscience.cdk.Atom;
import org.openscience.cdk.interfaces.IAtomContainerSet;

import dk.smartcyp.core.MoleculeKU;
import dk.smartcyp.core.MoleculeKU.SMARTCYP_PROPERTY;



public class WriteResultsAsCSV {

	PrintWriter outfile;
	String moleculeID;
	private String dateAndTime;
	String[] namesOfInfiles;
	String OutputDir;	
	int printall;

	// DecimalFormat twoDecimalFormat = new DecimalFormat("#.##");
	DecimalFormat twoDecimalFormat = new DecimalFormat();


	public WriteResultsAsCSV(String dateTime, String[] infileNames, String outputdir, int PrintAll){
		dateAndTime = dateTime;
		namesOfInfiles = infileNames;
		OutputDir = outputdir;
		printall = PrintAll;

		// DecimalFormat for A
		twoDecimalFormat.setDecimalSeparatorAlwaysShown(false);
		DecimalFormatSymbols decformat = new DecimalFormatSymbols();
		decformat.setDecimalSeparator('.');
		decformat.setGroupingSeparator(',');
		twoDecimalFormat.setMaximumFractionDigits(2);
		twoDecimalFormat.setDecimalFormatSymbols(decformat);
	}



	public void writeCSV(IAtomContainerSet moleculeSet) {


		try {
			outfile = new PrintWriter(new BufferedWriter(new FileWriter(this.OutputDir + "SMARTCyp_Results_" + this.dateAndTime + ".csv")));
		} catch (IOException e) {
			System.out.println("Could not create CSV outfile");
			e.printStackTrace();
		}

		outfile.println("Molecule,Atom,Ranking,Score,Energy,Accessability");
		Atom currentAtom;
		String currentAtomType;					// Atom symbol i.e. C, H, N, P or S


		// Iterate MoleculKUs
		for (int moleculeIndex=0; moleculeIndex < moleculeSet.getAtomContainerCount(); moleculeIndex++) {

			// Set variables
			MoleculeKU moleculeKU = (MoleculeKU) moleculeSet.getAtomContainer(moleculeIndex);
			moleculeID = moleculeKU.getID();


			// Iterate Atoms
			for(int atomIndex = 0; atomIndex < moleculeKU.getAtomCount()  ; atomIndex++ ){

				currentAtom = (Atom) moleculeKU.getAtom(atomIndex);

				// Match atom symbol
				currentAtomType = currentAtom.getSymbol();
				if(printall == 1 || currentAtomType.equals("C") || currentAtomType.equals("N") || currentAtomType.equals("P") || currentAtomType.equals("S")) {


					outfile.print((moleculeIndex + 1) + "," + currentAtom.getSymbol() + "."+ currentAtom.getID() + "," + SMARTCYP_PROPERTY.Ranking.getNumber(currentAtom) + ",");				
					if(SMARTCYP_PROPERTY.Score.getNumber(currentAtom) != null) 
						outfile.print(twoDecimalFormat.format(SMARTCYP_PROPERTY.Score.getNumber(currentAtom)) + "," + SMARTCYP_PROPERTY.Energy.getData(currentAtom).getEnergy());
					else outfile.print("-,-");
					outfile.print("," + twoDecimalFormat.format(SMARTCYP_PROPERTY.Accessibility.getNumber(currentAtom)));
					outfile.print("\n");
				}
			}

		}


		outfile.close();
	}
}




