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

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainerSet;

import dk.smartcyp.core.MoleculeKU;
import dk.smartcyp.core.MoleculeKU.SMARTCYP_PROPERTY;



public class WriteResultsAsHTML {


	PrintWriter outfile;
	TreeSet<IAtom> sortedAtomsTreeSet;
	String moleculeID;
	private String dateAndTime;
	String[] namesOfInfiles;
	DecimalFormat twoDecimalFormat = new DecimalFormat();
	String OutputDir;
	
	
	public WriteResultsAsHTML(String dateTime, String[] infileNames, String outputdir){
		dateAndTime = dateTime;
		namesOfInfiles = infileNames;
		OutputDir = outputdir;
		
		// DecimalFormat for A
		twoDecimalFormat.setDecimalSeparatorAlwaysShown(false);
		DecimalFormatSymbols decformat = new DecimalFormatSymbols();
		decformat.setDecimalSeparator('.');
		decformat.setGroupingSeparator(',');
		twoDecimalFormat.setMaximumFractionDigits(2);
		twoDecimalFormat.setDecimalFormatSymbols(decformat);
	}

	
	public void writeHTML(IAtomContainerSet moleculeSet) {


		try {
			outfile = new PrintWriter(new BufferedWriter(new FileWriter(this.OutputDir +"SMARTCyp_Results_" + this.dateAndTime + ".html")));
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
		outfile.println(".highlight1 {  background: rgb(255,204,102); font-weight : bold; } ");
		outfile.println(".highlight2 {  background: rgb(223,189,174); font-weight : bold; } ");
		outfile.println(".highlight3 {  background: rgb(214,227,181); font-weight : bold; } ");
		outfile.println(".hiddenPic {display:none;} ");
		outfile.println("-->");
		outfile.println("</style>");
		outfile.println("<script type=\"text/javascript\">");
		outfile.println("function roll_over(img_name, img_src)");
		outfile.println("   {");
		outfile.println("   document[img_name].src = img_src;");
		outfile.println("   }");
		outfile.println("</script>");
		outfile.println("</head>");
		outfile.println("");

	}

	public void writeBody(IAtomContainerSet moleculeSet){

		outfile.println("<body>");
		//error message if problems
		if (moleculeSet.getAtomContainerCount()==0){
			outfile.println("<h1>There were no molecules in the input</h1>");
		}
		else {
		//no error message, print normal output
		outfile.println("<h1>Results from SMARTCyp version 1.5.3</h1>");
		outfile.println("\n These results were produced: " + this.dateAndTime + ".");
		outfile.println("\n The infiles were: " + Arrays.toString(namesOfInfiles) + ".");	
		outfile.println("\n <br /><br /><i>To alternate between heteroatoms and atom numbers, move the mouse cursor over the figure.</i>");
		outfile.println("<table>");

		// Iterate MoleculKUs
		for (int moleculeIndex=0; moleculeIndex < moleculeSet.getAtomContainerCount(); moleculeIndex++) {

			MoleculeKU moleculeKU = (MoleculeKU) moleculeSet.getAtomContainer(moleculeIndex);
			this.writeMoleculeKUTable(moleculeKU);
		}

		outfile.println("</table>");
		// Iterate again to preload images with atom numbers
		for (int moleculeIndex=0; moleculeIndex < moleculeSet.getAtomContainerCount(); moleculeIndex++) {

			MoleculeKU moleculeKU = (MoleculeKU) moleculeSet.getAtomContainer(moleculeIndex);
			this.writePreLoadImage(moleculeKU);
		}
		}

		outfile.println("</body>");
		outfile.println("</html>");

	}

	public void writeMoleculeKUTable(MoleculeKU moleculeKU) {

		moleculeID = moleculeKU.getID();

		outfile.println("");
		outfile.println("<!-- Molecule " + moleculeID + " -->");	// Invisible code marker for molecule
		outfile.println("<tr>");
		outfile.println("<td>");

		// Table row, contains 1 molecule images and mouseover to a second image with atom numbers
		String image1 = "smartcyp_images_"+ this.dateAndTime +"/" +"molecule_" + moleculeID + "_heteroAtoms.png";
		String image2 = "smartcyp_images_"+ this.dateAndTime + "/" + "molecule_" + moleculeID + "_atomNumbers.png";
		outfile.println("<img src=\"" + image1 + "\" name=\"img" + moleculeID + "\" onmouseover=\"roll_over('img" + moleculeID + "', '" + image2 + "')\" onmouseout=\"roll_over('img" + moleculeID + "', '" + image1 + "')\" /><br />");


		outfile.println("</td>");
		outfile.println("<td>");

		// Visible header for Molecule
		String title = (String) moleculeKU.getProperty(CDKConstants.TITLE);
		if (title==null || title==""){
			title = "Molecule " + moleculeID;
		}
		outfile.println("<span class=\"boldlarge\">" + title + "</span><br />");

		// Table of Atom data
		outfile.println("<table class=\"molecule\">");
		outfile.println("<tr><th>Rank</th><th>Atom</th><th>Score</th><th>Energy</th><th>Accessability</th></tr>");

		// Iterate over the Atoms in this sortedAtomsTreeSet
		sortedAtomsTreeSet = (TreeSet<IAtom>) moleculeKU.getAtomsSortedByEnA();
		Iterator<IAtom> sortedAtomsTreeSetIterator = sortedAtomsTreeSet.iterator();
		IAtom currentAtom;
		
		while(sortedAtomsTreeSetIterator.hasNext()){
			currentAtom = sortedAtomsTreeSetIterator.next();
			this.writeAtomRowinMoleculeKUTable(currentAtom);
		}
		outfile.println("</table>");
		outfile.println("</td>");
		outfile.println("</tr>");
		outfile.println("<tr><td colspan=\"2\"><hr /></td></tr>");
	}
	
	public void writePreLoadImage(MoleculeKU moleculeKU) {

		moleculeID = moleculeKU.getID();

		outfile.println("");
		String image2 = "smartcyp_images_"+ this.dateAndTime + "/" + "molecule_" + moleculeID + "_atomNumbers.png";
		outfile.println("<img src=\"" + image2 + "\" class=\"hiddenPic\"  />");
	}



	public void writeAtomRowinMoleculeKUTable(IAtom atom){

//		System.out.println(atom.toString());
		if(SMARTCYP_PROPERTY.Ranking.getNumber(atom).intValue() == 1) outfile.println("<tr class=\"highlight1\">");
		else if(SMARTCYP_PROPERTY.Ranking.getNumber(atom).intValue() == 2) outfile.println("<tr class=\"highlight2\">");
		else if(SMARTCYP_PROPERTY.Ranking.getNumber(atom).intValue() == 3) outfile.println("<tr class=\"highlight3\">");
		else outfile.println("<tr>");

		outfile.println("<td>" + SMARTCYP_PROPERTY.Ranking.getNumber(atom).intValue() + "</td>");
		outfile.println("<td>" + atom.getSymbol() + "."+ atom.getID() + "</td>");			// For example C.22 or N.9
		if(SMARTCYP_PROPERTY.Score.getNumber(atom) == null) outfile.println("<td>-</td>");
		else outfile.println("<td>" + twoDecimalFormat.format(SMARTCYP_PROPERTY.Score.getNumber(atom)) + "</td>");
		if(SMARTCYP_PROPERTY.Energy.getData(atom) == null) outfile.println("<td>-</td>");
		else outfile.println("<td>" + SMARTCYP_PROPERTY.Energy.getData(atom).getEnergy() + "</td>");
		outfile.println("<td>" +  twoDecimalFormat.format(SMARTCYP_PROPERTY.Accessibility.getNumber(atom)) + "</td>");
		outfile.println("</tr>");
	}

}


