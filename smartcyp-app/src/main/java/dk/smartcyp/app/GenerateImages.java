package dk.smartcyp.app;

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

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.geometry.GeometryTools;
import org.openscience.cdk.geometry.Projector;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.renderer.AtomContainerRenderer;
import org.openscience.cdk.renderer.IRenderer;
import org.openscience.cdk.renderer.RendererModel;
import org.openscience.cdk.renderer.font.AWTFontManager;
import org.openscience.cdk.renderer.generators.AtomNumberGenerator;
import org.openscience.cdk.renderer.generators.BasicAtomGenerator;
import org.openscience.cdk.renderer.generators.BasicAtomGenerator.Shape;
import org.openscience.cdk.renderer.generators.BasicBondGenerator;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator;
import org.openscience.cdk.renderer.generators.IGenerator;
import org.openscience.cdk.renderer.generators.RingGenerator;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;
import org.openscience.cdk.silent.AtomContainer;



// This class writes the results to a HTML file
// This class is the last to be called
public class GenerateImages {


	// The draw area and the image should be the same size
	private int WIDTH = 800;
	private int HEIGHT = 400;
	private Rectangle drawArea = new Rectangle(WIDTH, HEIGHT);
	private Image image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private String dateAndTime;
	private String OutputDir;




	public GenerateImages(String dateTime, String outputdir){
		dateAndTime = dateTime;
		OutputDir = outputdir;
	}



	// This is the "main" method that calls all other methods below
	public void generateAndWriteImages(IAtomContainerSet moleculeSet) throws CloneNotSupportedException, CDKException{



		if(moleculeSet == null){
			System.out.println("moleculeSet is null");
		}

		List<IGenerator<IAtomContainer>> generators;

		IRenderer<IAtomContainer> renderer;

		// Create smartcyp_images directory
		this.createDirectory(OutputDir + "smartcyp_images_" + dateAndTime);
		
		// Iterate MoleculKUs
		IAtomContainer iAtomContainer;
		for (int moleculeIndex=0; moleculeIndex < moleculeSet.getAtomContainerCount(); moleculeIndex++) {

			iAtomContainer = moleculeSet.getAtomContainer(moleculeIndex); 


			//			iAtomContainer = AtomContainerManipulator.removeHydrogensPreserveMultiplyBonded(iAtomContainer);

			// Generate 2D coordinates for moleculeKU
			iAtomContainer = this.generate2Dcoordinates(iAtomContainer);

			//String id = moleculeSet.getMolecule(moleculeIndex).getID();
			// System.out.println(id);

			iAtomContainer.setID(moleculeSet.getAtomContainer(moleculeIndex).getID());

			// Generators make the image elements
			generators = getGenerators();

			// The renderer renders the picture
			renderer = new AtomContainerRenderer(generators, new AWTFontManager()) ;

			setMoleculeLayout(renderer.getRenderer2DModel());
			renderer.setup(iAtomContainer, drawArea);

			// Set layout of molecule
			// This method is not used because default layout looks ok
			// this.setMoleculeLayout(r2dm);


			// Write 2 types of images with: 1) heteroatoms and 2) atom Numbers
			this.paintAndWriteMolecule(renderer, iAtomContainer, "heteroAtoms", OutputDir);		
			renderer.getRenderer2DModel().set(AtomNumberGenerator.WillDrawAtomNumbers.class, true);
			this.paintAndWriteMolecule(renderer, iAtomContainer, "atomNumbers", OutputDir);

		}
	}





	// Generates 2D coordinates of molecules
	public IAtomContainer generate2Dcoordinates(IAtomContainer iAtomContainer){ 


		IAtomContainer molecule = new AtomContainer(iAtomContainer);


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
	public List<IGenerator<IAtomContainer>> getGenerators() {

		List<IGenerator<IAtomContainer>> generators = new ArrayList<IGenerator<IAtomContainer>>();
		//generators.add(new rankedlabelgenerator());
		generators.add(new BasicSceneGenerator());
		generators.add(new RingGenerator());
		generators.add(new BasicBondGenerator());
		generators.add(new BasicAtomGenerator());
		generators.add(new AtomNumberGenerator());
		
		// System.out.println("generators.toString() " + generators.toString());

		return generators;
	}


	// This is not used, but kept to list parameters that can be set
	public void setMoleculeLayout(RendererModel r2dm) {

		// Changes representation
		// renderer.getRenderer2DModel().setIsCompact(true);

		r2dm.set(BasicBondGenerator.BondWidth.class,3.0);
		//		r2dm.setBondDistance(22);
		// r2dm.setFitToScreen(true);
		r2dm.set(BasicAtomGenerator.ShowExplicitHydrogens.class, false);
		//		r2dm.setShowImplicitHydrogens(false);
		//		r2dm.setDrawNumbers(true);
		r2dm.set(BasicAtomGenerator.ColorByType.class, true);
		r2dm.set(BasicAtomGenerator.CompactShape.class,Shape.OVAL);
		r2dm.set(BasicAtomGenerator.AtomRadius.class,5.0);
		// r2dm.setBackgroundDimension(imgSize);
		//r2dm.setBackColor(Color.WHITE);
		//r2dm.setDrawNumbers(false);
		r2dm.set(BasicSceneGenerator.UseAntiAliasing.class, true);
		//        r2dm.setShowImplicitHydrogens(true);
		//r2dm.setShowReactionBoxes(false); not sure how to set in the new cdk-rendering
		
		r2dm.set(BasicAtomGenerator.KekuleStructure.class, false);
//		r2dm.setShowAromaticityCDKStyle(true); not sure how to set in the new cdk-rendering
		
		
		//r2dm.setFontName("SansSerif");
		r2dm.set(BasicSceneGenerator.FontName.class,"SansSerif");
		//       IFontManager iFontManager = new AWTFontManager();
		//IFontManager.FontStyle fontStyle = IFontManager.FontStyle();
		//  iFontManager.setFontStyle( NORMAL);
		//  r2dm.setFontManager(IFontManager.FontStyle.valueOf(NORMAL));

		//       r2dm.fireChange();


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




	public void paintAndWriteMolecule(IRenderer renderer, IAtomContainer iAtomContainer, String nameBase, String outputdir){


		// Paint background
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		//	g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, WIDTH, HEIGHT);

		// the paint method also needs a toolkit-specific renderer
		renderer.paint(iAtomContainer, new AWTDrawVisitor(g2), drawArea, true);


		String moleculeID = iAtomContainer.getID();
		//System.out.println(moleculeID);
		String moleculeIDstartingFromOne = Integer.toString(Integer.parseInt(moleculeID));
		String fileName = outputdir + "smartcyp_images_" + this.dateAndTime + File.separator + "molecule_" + moleculeIDstartingFromOne + "_" + nameBase + ".png";

		try {ImageIO.write((RenderedImage)image, "PNG", new File(fileName));} 
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Molecule images could not be written to file. " +
			"If you have not already you need to create the directory 'smartcyp_images' in which the images are to be written");
		}

	}
}





