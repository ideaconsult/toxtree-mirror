package toxtree.plugins.smartcyp.cyp450;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.geometry.GeometryTools;
import org.openscience.cdk.geometry.Projector;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.jchempaint.renderer.Renderer;
import org.openscience.jchempaint.renderer.RenderingParameters;
import org.openscience.jchempaint.renderer.font.AWTFontManager;
import org.openscience.jchempaint.renderer.generators.AtomNumberGenerator;
import org.openscience.jchempaint.renderer.generators.BasicAtomGenerator;
import org.openscience.jchempaint.renderer.generators.BasicBondGenerator;
import org.openscience.jchempaint.renderer.generators.IGenerator;
import org.openscience.jchempaint.renderer.visitor.AWTDrawVisitor;


// This class writes the results to a HTML file
// This class is the last to be called
public class GenerateImages {


	// The draw area and the image should be the same size
	private int WIDTH = 800;
	private int HEIGHT = 400;
	private Rectangle drawArea = new Rectangle(WIDTH, HEIGHT);
	private Image image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private String dateAndTime;


	public GenerateImages(String dateTime){
		dateAndTime = dateTime;
	}



	// This is the "main" method that calls all other methods below
	public void generateAndWriteImages(MoleculeSet moleculeSet) throws CloneNotSupportedException{



		if(moleculeSet == null){
			System.out.println("moleculeSet is null");
		}


		List<IGenerator> generators;
		Renderer renderer;

		// Iterate MoleculKUs
		IAtomContainer iAtomContainer;
		for (int moleculeIndex=0; moleculeIndex < moleculeSet.getMoleculeCount(); moleculeIndex++) {

			iAtomContainer = moleculeSet.getMolecule(moleculeIndex);


			//			iAtomContainer = AtomContainerManipulator.removeHydrogensPreserveMultiplyBonded(iAtomContainer);

			// Generate 2D coordinates for moleculeKU
			iAtomContainer = this.generate2Dcoordinates(iAtomContainer);

			String id = moleculeSet.getMolecule(moleculeIndex).getID();
			// System.out.println(id);

			iAtomContainer.setID(moleculeSet.getMolecule(moleculeIndex).getID());

			// Generators make the image elements
			generators = this.getGenerators();

			// The renderer renders the picture
			renderer = new Renderer(generators, new AWTFontManager());

			// Set layout of molecule
			this.setMoleculeLayout(renderer);


			// Setup sets up the renderer
			// System.out.println("currentMoleculeKU.toString() " + currentMoleculeKU.toString());
			// System.out.println("drawArea " + drawArea.toString());


			// renderer.setup(currentMoleculeKU, drawArea);
			renderer.setup(iAtomContainer, drawArea);

			// Create smartcyp_images directory
			this.createDirectory("smartcyp_images_" + dateAndTime);

			// Write 2 types of images with: 1) heteroatoms and 2) atom Numbers
			this.paintAndWriteMolecule(renderer, iAtomContainer, "heteroAtoms");		
			renderer.getRenderer2DModel().setDrawNumbers(true);						
			this.paintAndWriteMolecule(renderer, iAtomContainer, "atomNumbers");

		}
	}



	// Generates 2D coordinates of molecules
	public IAtomContainer generate2Dcoordinates(IAtomContainer iAtomContainer){ 


		IMolecule molecule = new Molecule(iAtomContainer);


		//		boolean isConnected = ConnectivityChecker.isConnected(iAtomContainer);
		//		System.out.println("isConnected " + isConnected);

		final StructureDiagramGenerator structureDiagramGenerator = new StructureDiagramGenerator();

		// Generate 2D coordinates?
		if (GeometryTools.has2DCoordinates(iAtomContainer))
		{
			// System.out.println(iAtomContainer.toString() + " already had 2D coordinates");
			return iAtomContainer; // already has 2D coordinates.
		}
		else
		{

			// Generate 2D structure diagram (for each connected component).
			final IAtomContainer iAtomContainer2d = new AtomContainer();	

			/*
			final IMoleculeSet som = ConnectivityChecker.partitionIntoMolecules(iAtomContainer);
			for (int n = 0;
			n < som.getMoleculeCount();
			n++)
			{
			 */
			synchronized (structureDiagramGenerator)
			{
				//				IMolecule molecule = som.getMolecule(n);


				structureDiagramGenerator.setMolecule(molecule, true);
				try
				{
					// Generate 2D coords for this molecule.
					structureDiagramGenerator.generateCoordinates();
					molecule = structureDiagramGenerator.getMolecule();
				}
				catch (final Exception e)
				{
					// Use projection instead.
					Projector.project2D(molecule);
					System.out.println("Exception in generating 2D coordinates");
					e.printStackTrace();     			
				}

				iAtomContainer2d.add(molecule);  		// add 2D molecule.		

			}

			/*
	     		// Test
	     		Atom atom;
	     		for(int atomIndex = 0; atomIndex < iAtomContainer2d.getAtomCount(); atomIndex++){
	     			atom = (Atom) iAtomContainer2d.getAtom(atomIndex);
	     			System.out.println("atom.getPoint2d(): " + atom.getPoint2d());
	     		}
			 */
			//			}
			if(GeometryTools.has2DCoordinates(iAtomContainer2d)) return  iAtomContainer2d;
			else {
				System.out.println("Generating 2D coordinates for " + iAtomContainer2d + " failed.");
				return null;
			}
		}	
	}


	// Generators make the image elements
	public List<IGenerator> getGenerators() {

		List<IGenerator> generators = new ArrayList<IGenerator>();
		// generators.add(new AtomContainerBoundsGenerator());
		generators.add(new BasicBondGenerator());
		generators.add(new BasicAtomGenerator());
		generators.add(new AtomNumberGenerator());
		// generators.add(new HighlightBondGenerator());
		// generators.add(new RingGenerator());
		// generators.add(new SelectBondGenerator());	       

		// System.out.println("generators.toString() " + generators.toString());

		return generators;
	}



	public void setMoleculeLayout(Renderer renderer) {

		// Changes representation
		// renderer.getRenderer2DModel().setIsCompact(true);

		renderer.getRenderer2DModel().setBondWidth(3);
		//		renderer.getRenderer2DModel().setBondDistance(22);
		// renderer.getRenderer2DModel().setFitToScreen(true);
		renderer.getRenderer2DModel().setShowExplicitHydrogens(false);
		//		renderer.getRenderer2DModel().setShowImplicitHydrogens(false);
		//		renderer.getRenderer2DModel().setDrawNumbers(true);
		renderer.getRenderer2DModel().setColorAtomsByType(true);
		renderer.getRenderer2DModel().setCompactShape(RenderingParameters.AtomShape.OVAL);
		renderer.getRenderer2DModel().setAtomRadius(5);

		// renderer.getRenderer2DModel().setBackgroundDimension(imgSize);
		//  renderer.getRenderer2DModel().setBackColor(Color.WHITE);
		//renderer.getRenderer2DModel().setDrawNumbers(false);
		renderer.getRenderer2DModel().setUseAntiAliasing(true);
		//        renderer.getRenderer2DModel().setShowImplicitHydrogens(true);
		renderer.getRenderer2DModel().setShowReactionBoxes(false);
		renderer.getRenderer2DModel().setKekuleStructure(false);
		renderer.getRenderer2DModel().setShowAromaticityCDKStyle(true);

		renderer.getRenderer2DModel().setFontName("SansSerif");
		//       IFontManager iFontManager = new AWTFontManager();
		//IFontManager.FontStyle fontStyle = IFontManager.FontStyle();
		//  iFontManager.setFontStyle( NORMAL);
		//  renderer.getRenderer2DModel().setFontManager(IFontManager.FontStyle.valueOf(NORMAL));

		//       renderer.getRenderer2DModel().fireChange();



		//System.out.println("renderer.toString()" + renderer.toString());

	}




	// Creates a directory with the name directoryName
	public void createDirectory(String directoryName){
		try{

			// Create one directory
			boolean success = (new File(directoryName)).mkdir();
			if (success) {
				System.out.println("Directory: " + directoryName + " created");
			}    

		}catch (Exception e){//Catch exception if any
			System.err.println("Could not create image directory smartcyp_images \n Error: " + e.getMessage());
		}
	}




	public void paintAndWriteMolecule(Renderer renderer, IAtomContainer iAtomContainer, String nameBase){


		// Paint background
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, WIDTH, HEIGHT);

		// the paint method also needs a toolkit-specific renderer
		renderer.paintMolecule(iAtomContainer, new AWTDrawVisitor(g2), drawArea, true);


		String moleculeID = iAtomContainer.getID();
		//System.out.println(moleculeID);
		String moleculeIDstartingFromOne = Integer.toString(Integer.parseInt(moleculeID));
		String fileName = "smartcyp_images_" + this.dateAndTime + "/" + "molecule_" + moleculeIDstartingFromOne + "_" + nameBase + ".png";

		try {ImageIO.write((RenderedImage)image, "PNG", new File(fileName));} 
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Molecule images could not be written to file. " +
			"If you have not already you need to create the directory 'smartcyp_images' in which the images are to be written");
		}

	}
}





