package toxtree.plugins.smartcyp.cyp450;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.atomtype.CDKAtomTypeMatcher;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.interfaces.IChemFile;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.io.ISimpleChemObjectReader;
import org.openscience.cdk.io.MDLReader;
import org.openscience.cdk.io.ReaderFactory;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.openscience.cdk.tools.manipulator.AtomTypeManipulator;
import org.openscience.cdk.tools.manipulator.ChemFileManipulator;

import ambit2.core.data.MoleculeTools;





// Ligand files
// pubchem.sdf
// #adrenaline.sdf formoterol.sdf indacaterol.sdf test_ligands.sdf
// exampleStructure.mdl test_ligands.sdf test_ligands.sdf




// ToDo
// Uncomment this line in GenerateImages.java
//generators.add(new BasicBondGenerator());


public class SMARTCyp {


	public static void main(String[] arguments) throws CloneNotSupportedException, CDKException{

		SMARTCyp SMARTCypMain = new SMARTCyp();

		// Check that the arguments (molecule files) have been given
		if (arguments.length < 1){
			System.out.println("Wrong number of arguments!" + '\n' + "Usage: SMARTCyp <One or more moleculeFiles>");
			System.exit(0);			
		}

		// Date and Time is used as part of the names of outfiles
		String dateAndTime = SMARTCypMain.getDateAndTime();


		// Produce SMARTSnEnergiesTable object
		System.out.println("\n ************** Processing SMARTS and Energies **************");
		SMARTSnEnergiesTable SMARTSnEnergiesTable = new SMARTSnEnergiesTable();

		
		// Read in structures/molecules
		System.out.println("\n ************** Reading molecule structures **************");
		MoleculeSet moleculeSet = SMARTCypMain.readInStructures(arguments, SMARTSnEnergiesTable.getSMARTSnEnergiesTable());



		MoleculeKU moleculeKU;
		for(int moleculeIndex = 0; moleculeIndex < moleculeSet.getMoleculeCount(); moleculeIndex++){
			moleculeKU = (MoleculeKU) moleculeSet.getMolecule(moleculeIndex);

			System.out.println("\n ************** Matching SMARTS to assign Energies **************");
			moleculeKU.assignAtomEnergies(SMARTSnEnergiesTable.getSMARTSnEnergiesTable());	

			System.out.println("\n ************** Calculating Accessabilities and Atom Scores**************");
			moleculeKU.calculateAtomAccessabilities();

			System.out.println("\n ************** Identifying, sorting and ranking C, N, P and S atoms **************");
			moleculeKU.sortAtoms();
			moleculeKU.rankAtoms();
		}


		// Write results as CSV
		System.out.println("\n ************** Writing Results as CSV **************");
		WriteResultsAsCSV writeResultsAsCSV = new WriteResultsAsCSV(dateAndTime, arguments);
		writeResultsAsCSV.writeCSV(moleculeSet);



		// Write Images	
		System.out.println("\n ************** Writing Images **************");
		GenerateImages generateImages = new GenerateImages(dateAndTime);
		generateImages.generateAndWriteImages(moleculeSet);


		// Write results as HTML
		System.out.println("\n ************** Writing Results as HTML **************");
		WriteResultsAsHTML writeResultsAsHTML = new WriteResultsAsHTML(dateAndTime, arguments);
		writeResultsAsHTML.writeHTML(moleculeSet);



	}


	// The Date and Time is used as part of the output filenames
	private String getDateAndTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}



	// Reads the molecule infiles
	// Stores MoleculeKUs and AtomKUs
	public static MoleculeSet readInStructures(String[] inFileNames, HashMap<String, Double> SMARTSnEnergiesTable) throws CloneNotSupportedException, CDKException{

		MoleculeSet moleculeSet = new MoleculeSet();


		List<IAtomContainer> moleculeList;
		IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
		ISimpleChemObjectReader reader;

		File inputFile;
		String infileName;
		ReaderFactory readerFactory;
		IChemFile emptyChemFile;		
		IChemFile chemFile;


		// Iterate over all molecule infiles (it can be a single file)
		int moleculeFileNr;
		int highestMoleculeID = 1;
		for (moleculeFileNr = 0; moleculeFileNr < inFileNames.length; moleculeFileNr++) {

			infileName = inFileNames[moleculeFileNr];
			inputFile = new File(infileName);

			readerFactory = new ReaderFactory();

			try {

				if (infileName.endsWith(".sdf")) {  
					reader = new MDLReader(new FileReader(infileName));
					//MoleculesIterator.useIterativeReader(new FileInputStream(infileName));
					//return null;
				}
				else	 reader = readerFactory.createReader(new FileReader(inputFile));


				emptyChemFile = MoleculeTools.newChemFile(builder);
				chemFile = (IChemFile) reader.read(emptyChemFile);

				if (chemFile == null) continue;	

				//System.out.println(chemFile.toString());

				// Get Molecules
				moleculeList = ChemFileManipulator.getAllAtomContainers(chemFile);

				// Iterate Molecules
				MoleculeKU moleculeKU;
				IAtomContainer iAtomContainerTmp;		
				IAtomContainer iAtomContainer;	
				CDKAtomTypeMatcher matcher = CDKAtomTypeMatcher.getInstance(DefaultChemObjectBuilder.getInstance());
				CDKHydrogenAdder adder = CDKHydrogenAdder.getInstance(DefaultChemObjectBuilder.getInstance());
				for(int atomContainerNr = 0; atomContainerNr < moleculeList.size() ; atomContainerNr++){				
					iAtomContainerTmp = moleculeList.get(atomContainerNr);	

					iAtomContainer = AtomContainerManipulator.removeHydrogens(iAtomContainerTmp);	

					Iterable<IAtom> atoms  = iAtomContainer.atoms();
					Iterator<IAtom> a = atoms.iterator();
					while (a.hasNext()) {
						IAtom atom = (IAtom)a.next();
						IAtomType type = matcher.findMatchingAtomType(iAtomContainer, atom);
						AtomTypeManipulator.configure(atom, type); 
					}

					adder.addImplicitHydrogens(iAtomContainer);
					moleculeKU = new MoleculeKU(iAtomContainer, SMARTSnEnergiesTable);	
					moleculeSet.addMolecule(moleculeKU);
					moleculeKU.setID(Integer.toString(highestMoleculeID));
					highestMoleculeID++;

				}
				System.out.println(moleculeList.size() + " molecules were read from the file "+ inFileNames[moleculeFileNr]);

			} 
			catch (FileNotFoundException e) {
				System.out.println("File " + inFileNames[moleculeFileNr] + "not found");
				e.printStackTrace();
			} 
			catch (IOException e) {e.printStackTrace();}

		}

		return moleculeSet;
	}

}



