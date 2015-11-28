package dk.smartcyp.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IChemFile;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.io.ISimpleChemObjectReader;
import org.openscience.cdk.io.MDLReader;
import org.openscience.cdk.io.ReaderFactory;
import org.openscience.cdk.io.SMILESReader;
import org.openscience.cdk.silent.AtomContainerSet;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.openscience.cdk.tools.manipulator.ChemFileManipulator;

import dk.smartcyp.core.MoleculeKU;
import dk.smartcyp.core.SMARTSData;

public class SMARTCyp {

	public static void main(String[] arguments)
			throws CloneNotSupportedException, CDKException {

		SMARTCyp SMARTCypMain = new SMARTCyp();

		// Check that the arguments (molecule files) have been given
		if (arguments.length < 1) {
			System.out.println("Wrong number of arguments!" + '\n'
					+ "Usage: SMARTCyp <One or more moleculeFiles>");
			System.exit(0);
		}

		// check for input flags and copy input files to filenames array
		int nohtml = 0;
		int dirwanted = 0;
		int nocsv = 0;
		int printall = 0;
		String outputdir = "";
		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i].equals("-nohtml")) {
				nohtml = 1;
			}
			if (arguments[i].equals("-nocsv")) {
				nocsv = 1;
			}
			if (arguments[i].equals("-printall")) {
				printall = 1;
			}
			if (arguments[i].equals("-outputdir")) {
				outputdir = arguments[i + 1];
				dirwanted = 1;
			}
		}
		String[] filenames;
		if (nohtml == 1 || dirwanted == 1 || nocsv == 1 || printall == 1) {
			ArrayList<String> tmplist = new ArrayList<String>();
			Collections.addAll(tmplist, arguments);
			if (dirwanted == 1) {
				// a specific output directory has been requested
				tmplist.remove(outputdir);
				tmplist.remove("-outputdir");
				File dir = new File(outputdir);
				// check if the directory exists, otherwise create it
				if (!dir.exists()) {
					dir.mkdir();
				}
				outputdir = outputdir + File.separator;
			}
			if (nohtml == 1)
				tmplist.remove("-nohtml");
			if (nocsv == 1)
				tmplist.remove("-nocsv");
			if (printall == 1)
				tmplist.remove("-printall");
			filenames = (String[]) tmplist.toArray(new String[0]);
		} else {
			filenames = arguments;
		}
		// end of input flags

		// Date and Time is used as part of the names of outfiles
		String dateAndTime = SMARTCypMain.getDateAndTime();

		// Produce SMARTSnEnergiesTable object
		System.out
				.println("\n ************** Processing SMARTS and Energies **************");
		HashMap<String, SMARTSData> SMARTSnEnergiesTable = dk.smartcyp.core.SMARTSnEnergiesTable
				.getSMARTSnEnergiesTable();

		// Read in structures/molecules
		System.out
				.println("\n ************** Reading molecule structures **************");
		IAtomContainerSet moleculeSet = SMARTCypMain.readInStructures(
				filenames, SMARTSnEnergiesTable);

		MoleculeKU moleculeKU;
		for (int moleculeIndex = 0; moleculeIndex < moleculeSet
				.getAtomContainerCount(); moleculeIndex++) {
			moleculeKU = (MoleculeKU) moleculeSet
					.getAtomContainer(moleculeIndex);

			System.out
					.println("\n ************** Matching SMARTS to assign Energies **************");
			moleculeKU.assignAtomEnergies(SMARTSnEnergiesTable);

			System.out
					.println("\n ************** Calculating Accessabilities and Atom Scores**************");
			moleculeKU.calculateAtomAccessabilities();

			System.out
					.println("\n ************** Identifying, sorting and ranking C, N, P and S atoms **************");
			moleculeKU.sortAtoms();
			moleculeKU.rankAtoms();
		}

		// don't print csv if there are no molecules in the input
		if (moleculeSet.getAtomContainerCount() > 0) {
			if (nocsv == 0) {
				// Write results as CSV
				System.out
						.println("\n ************** Writing Results as CSV **************");
				WriteResultsAsCSV writeResultsAsCSV = new WriteResultsAsCSV(
						dateAndTime, arguments, outputdir, printall);
				writeResultsAsCSV.writeCSV(moleculeSet);
			}
		}

		if (nohtml == 0) {
			// Write Images
			System.out
					.println("\n ************** Writing Images **************");
			GenerateImages generateImages = new GenerateImages(dateAndTime,
					outputdir);
			generateImages.generateAndWriteImages(moleculeSet);

			// Write results as HTML
			System.out
					.println("\n ************** Writing Results as HTML **************");
			WriteResultsAsHTML writeResultsAsHTML = new WriteResultsAsHTML(
					dateAndTime, arguments, outputdir);
			writeResultsAsHTML.writeHTML(moleculeSet);
		}

	}

	// The Date and Time is used as part of the output filenames
	private String getDateAndTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	// Reads the molecule infiles
	// Stores MoleculeKUs and AtomKUs
	public static IAtomContainerSet readInStructures(String[] inFileNames,
			HashMap<String, SMARTSData> SMARTSnEnergiesTable)
			throws CloneNotSupportedException, CDKException {

		IAtomContainerSet moleculeSet = new AtomContainerSet();

		List<IAtomContainer> moleculeList;
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
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
				} else if (infileName.endsWith(".smi")) {
					reader = new SMILESReader(new FileReader(infileName));
				} else
					reader = readerFactory.createReader(new FileReader(
							inputFile));

				emptyChemFile = builder.newInstance(IChemFile.class);

				chemFile = (IChemFile) reader.read(emptyChemFile);

				if (chemFile == null)
					continue;

				// System.out.println(chemFile.toString());

				// Get Molecules
				moleculeList = ChemFileManipulator
						.getAllAtomContainers(chemFile);

				// Iterate Molecules
				MoleculeKU moleculeKU;
				IAtomContainer iAtomContainerTmp;
				IAtomContainer iAtomContainer;
				CDKHydrogenAdder adder = CDKHydrogenAdder
						.getInstance(SilentChemObjectBuilder.getInstance());
				for (int atomContainerNr = 0; atomContainerNr < moleculeList
						.size(); atomContainerNr++) {
					iAtomContainerTmp = moleculeList.get(atomContainerNr);

					iAtomContainer = AtomContainerManipulator
							.removeHydrogens(iAtomContainerTmp);

					// check number of atoms, if less than 2 don't add molecule
					if (iAtomContainer.getAtomCount() > 1) {

						AtomContainerManipulator
								.percieveAtomTypesAndConfigureAtoms(iAtomContainer);

						adder.addImplicitHydrogens(iAtomContainer);
						CDKHueckelAromaticityDetector
								.detectAromaticity(iAtomContainer);

						moleculeKU = new MoleculeKU(iAtomContainer,
								SMARTSnEnergiesTable);
						moleculeSet.addAtomContainer(moleculeKU);
						moleculeKU.setID(Integer.toString(highestMoleculeID));
						// set the molecule title in the moleculeKU object
						if (iAtomContainer.getProperty("SMIdbNAME") != ""
								&& iAtomContainer.getProperty("SMIdbNAME") != null) {
							iAtomContainer.setProperty(CDKConstants.TITLE,
									iAtomContainer.getProperty("SMIdbNAME"));
						}
						moleculeKU.setProperty(CDKConstants.TITLE,
								iAtomContainer.getProperty(CDKConstants.TITLE));
						highestMoleculeID++;
					}

				}
				System.out.println(moleculeList.size()
						+ " molecules were read from the file "
						+ inFileNames[moleculeFileNr]);

			} catch (FileNotFoundException e) {
				System.out.println("File " + inFileNames[moleculeFileNr]
						+ " not found");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return moleculeSet;
	}

}
