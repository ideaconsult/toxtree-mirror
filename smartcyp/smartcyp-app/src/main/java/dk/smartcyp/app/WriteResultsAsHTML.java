package dk.smartcyp.app;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

import org.openscience.cdk.Atom;
import org.openscience.cdk.MoleculeSet;

import dk.smartcyp.core.MoleculeKU;
import dk.smartcyp.core.MoleculeKU.SMARTCYP_PROPERTY;



public class WriteResultsAsHTML {


	PrintWriter outfile;
	TreeSet<Atom> sortedAtomsTreeSet;
	String moleculeID;
	private String dateAndTime;
	String[] namesOfInfiles;
	DecimalFormat twoDecimalFormat = new DecimalFormat();

	
	public WriteResultsAsHTML(String dateTime, String[] infileNames){
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

	
	public void writeHTML(MoleculeSet moleculeSet) {


		try {
			outfile = new PrintWriter(new BufferedWriter(new FileWriter("SMARTCyp_Results_" + this.dateAndTime + ".html")));
		} catch (IOException e) {
			System.out.println("Could not create HTML outfile");
			e.printStackTrace();
		}

		this.writeHead();

		this.writeBody(moleculeSet);

		outfile.close();

	}


	public void writeHead(){

		outfile.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		outfile.println("");
		outfile.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		outfile.println("");
		outfile.println("<head>");
		outfile.println("");
		outfile.println("<title>Results from SMARTCyp</title>");
		outfile.println("<style type=\"text/css\">");
		outfile.println("<!--");
		outfile.println("body {");
		outfile.println("\tbackground: #FFFFFF;");
		outfile.println("\tcolor: #000000;");
		outfile.println("\tmargin: 5px;");
		outfile.println("\tfont-family: Verdana, Arial, sans-serif;");
		outfile.println("\tfont-size: 16px;");
		outfile.println("\t}");
		outfile.println(".boldlarge { margin-left: 20px; font-size: 20px; font-weight : bold; }");
		outfile.println(".molecule { margin-left: 20px }");
		outfile.println("table { }");
		outfile.println("th { text-align:center; font-weight : bold; }");
		outfile.println(".highlight {  background: #FFFF00; font-weight : bold; } ");
		outfile.println("-->");
		outfile.println("</style>");
		outfile.println("</head>");
		outfile.println("");

	}

	public void writeBody(MoleculeSet moleculeSet){

		outfile.println("<body>");
		outfile.println("<h1>Results from SMARTCyp version 1.0</h1>");
		outfile.println("\n These results were produced: " + this.dateAndTime + ".");
		outfile.println("\n The infiles were: " + Arrays.toString(namesOfInfiles) + ".");	
		outfile.println("<table>");

		// Iterate MoleculKUs
		for (int moleculeIndex=0; moleculeIndex < moleculeSet.getMoleculeCount(); moleculeIndex++) {

			MoleculeKU moleculeKU = (MoleculeKU) moleculeSet.getMolecule(moleculeIndex);
			this.writeMoleculeKUTable(moleculeKU);
		}

		outfile.println("</table>");
		outfile.println("</body>");
		outfile.println("</html>");

	}

	public void writeMoleculeKUTable(MoleculeKU moleculeKU) {

		moleculeID = moleculeKU.getID();

		outfile.println("");
		outfile.println("<!-- Molecule " + moleculeID + " -->");	// Invisible code marker for molecule
		outfile.println("<tr>");
		outfile.println("<td>");

		// Table row, contains 2 molecule images					
		outfile.println("<img src=\"smartcyp_images_"+ this.dateAndTime +"/" +"molecule_" + moleculeID + "_heteroAtoms.png\" /><br />");
		outfile.println("<img src=\"smartcyp_images_"+ this.dateAndTime + "/" + "molecule_" + moleculeID + "_atomNumbers.png\" />");


		outfile.println("</td>");
		outfile.println("<td>");

		// Visible header for Molecule
		outfile.println("<span class=\"boldlarge\">Molecule " + moleculeID + "</span><br />");

		// Table of Atom data
		outfile.println("<table class=\"molecule\">");
		outfile.println("<tr><th>Rank</th><th>Atom</th><th>Score</th><th>Energy</th><th>Accessability</th></tr>");

		// Iterate over the Atoms in this sortedAtomsTreeSet
		sortedAtomsTreeSet = (TreeSet<Atom>) moleculeKU.getAtomsSortedByEnA();
		Iterator<Atom> sortedAtomsTreeSetIterator = sortedAtomsTreeSet.iterator();
		Atom currentAtom;
		
		while(sortedAtomsTreeSetIterator.hasNext()){
			currentAtom = sortedAtomsTreeSetIterator.next();
			this.writeAtomRowinMoleculeKUTable(currentAtom);
		}
		outfile.println("</table>");
		outfile.println("</td>");
		outfile.println("</tr>");
		outfile.println("<tr><td colspan=\"2\"><hr /></td></tr>");
	}


	public void writeAtomRowinMoleculeKUTable(Atom atom){

//		System.out.println(atom.toString());
		if(SMARTCYP_PROPERTY.Ranking.get(atom).intValue() <= 3) outfile.println("<tr class=\"highlight\">");
		else outfile.println("<tr>");

		outfile.println("<td>" + SMARTCYP_PROPERTY.Ranking.get(atom).intValue() + "</td>");
		outfile.println("<td>" + atom.getSymbol() + "."+ atom.getID() + "</td>");			// For example C.22 or N.9
		if(SMARTCYP_PROPERTY.Score.get(atom) == null) outfile.println("<td>-</td>");
		else outfile.println("<td>" + twoDecimalFormat.format(SMARTCYP_PROPERTY.Score.get(atom)) + "</td>");
		if(SMARTCYP_PROPERTY.Energy.get(atom) == null) outfile.println("<td>-</td>");
		else outfile.println("<td>" + SMARTCYP_PROPERTY.Energy.get(atom) + "</td>");
		outfile.println("<td>" +  twoDecimalFormat.format(SMARTCYP_PROPERTY.Accessibility.get(atom)) + "</td>");
		outfile.println("</tr>");
	}

}


