package toxtree.test.plugins.smartcyp.smirks;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.net.URLEncoder;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.io.iterator.IteratingMDLReader;
import org.openscience.cdk.nonotify.NoNotificationChemObjectBuilder;
import org.openscience.cdk.renderer.selection.IChemObjectSelection;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.core.IDecisionResult;
import toxtree.plugins.smartcyp.SMARTCYPPlugin;
import ambit2.base.interfaces.IProcessor;
import ambit2.core.data.IStructureDiagramHighlights;
import ambit2.core.io.MDLWriter;
import ambit2.core.processors.structure.AtomConfigurator;
import ambit2.core.processors.structure.HydrogenAdderProcessor;
import ambit2.rendering.CompoundImageTools;
import ambit2.smarts.SMIRKSManager;
import ambit2.smarts.SMIRKSReaction;

public class SMIRKSTest {
	protected CompoundImageTools tool = new CompoundImageTools();
	protected String[][] reactions  = {
	{		
	"N-dealkylation",
	"[N:1][C:2]([H])>>[N:1][H].[C:2]=[O]" 
	},

	{
	"N-oxidation",
	//"[N:1][C:2]([H])>>[N:1](-[O])[C:2]"
	"[N:1][C:2]([H])>>[N:1](=[O])[C:2]"
	},
	{
	"S-oxidation",
	"[#16:1][#6:2]>>[#16:1](=[O])[#6:2]"
	},
	{
	"Aromatic hydroxylation",
	"[c:1][H:2]>>[c:1][O][H:2]"
	},
	
	{
	"Aliphatic hydroxylation",
	"[C;X4:1][H:2]>>[C:1][O][H:2]",
	},
	
	{
	"O-dealkylation",
	"[O;H0:1][C:2]([H])>>[O:1][H].[C:2]=[O]"
	},
	
	{
	"Aromatization of dihydropyridines",
	"[N;X3:1]1([H])[#6:2]=[#6:3][#6;X4:4][#6:5]=[#6:6]1>>[n;H0:1]1=[#6:2][#6:3]=[#6:4][#6:5]=[#6:6]1",
	},
	
	{
	"Thioesther bond breaking",
	"[S:1][C:2]=[O:3]>>[S:1][H].[C:2](O)=[O:3]"
	},
	{
	"Aldehyde oxidation",
	"[C;H1:1]=[O:2]>>[C:1](O)=[O:2]"
	},
	{
	"Alcohol oxidation",
	"[C:1]([H])[O:2][H]>>[C:1]=[O:2]"
	},
	{
	"Desulphurization of phosphor",
	"[*:1][P:2](=S)([*:3])[*:4]>>[*:1][P:2](=O)([*:3])[*:4]"
	},
	{
	"Epoxidation",
	//"[C:1]=[C:2]>>[C:1]1=[C:2][O]1"
	"[C:1]=[C:2]>>[C:1]1[C:2][O]1"
	}
	
	};
	
	
	IAtomContainer applySMIRKSReaction(SMIRKSManager smrkMan, String smirks,IAtomContainer target) throws Exception
	{
	
		/*
		CDKHydrogenAdder adder = CDKHydrogenAdder.getInstance(
				DefaultChemObjectBuilder.getInstance()
		);
		*/
		
		//System.out.println("No Impl H Atoms = " + target.getAtom(0).getImplicitHydrogenCount().intValue());
				
		SMIRKSReaction reaction = smrkMan.parse(smirks);
		if (!smrkMan.getErrors().equals(""))
		{
			System.out.println(smrkMan.getErrors());
			throw(new Exception("Smirks Parser errors:\n" + smrkMan.getErrors()));
		}
		
		if (smrkMan.applyTransformation(target, reaction)) 
			return target; //all products inside the atomcontainer, could be disconnected
		else return null;
	}	
	@Test
	public void testMGenerator() throws Exception {
		SmilesGenerator g = new SmilesGenerator();
		SMARTCYPPlugin smartcyp = new SMARTCYPPlugin();
		File file = new File(getClass().getClassLoader().getResource("toxtree/test/plugins/smartcyp/3A4_substrates.sdf").getFile());
		IteratingMDLReader reader = new IteratingMDLReader(new FileInputStream(file), NoNotificationChemObjectBuilder.getInstance());
		
		File htmlFile = new File(String.format("%s/metabolites.html", file.getParentFile()));
		if (htmlFile.exists()) htmlFile.delete();
		FileWriter htmlFileWriter = new FileWriter(htmlFile);
		htmlFileWriter.write(String.format("<html><head><title>%s</title></head><body>",true?"Explicit H":"Implicit H"));
		
		
		AtomConfigurator  cfg = new AtomConfigurator();
		HydrogenAdderProcessor hadder = new HydrogenAdderProcessor();
		hadder.setAddEexplicitHydrogens(true);
		
		IDecisionResult result = smartcyp.createDecisionResult();
		int record = 0;
		try {
			while (reader.hasNext()) {
				record++;
				//if (record>4) break;
				
				IChemObject mol = reader.next();
				Object molid = mol.getProperty("ID");
				hadder.process((IAtomContainer)mol);
				cfg.process((IAtomContainer)mol);
				CDKHueckelAromaticityDetector.detectAromaticity((IAtomContainer)mol);	
				
				htmlFileWriter.write(String.format("<h3><a name='%s'>%s</a>",molid,molid));
				
				htmlFileWriter.write(String.format("&nbsp;<a href='http://apps.ideaconsult.net:8080/ambit2/compound?feature_uris[]=http://apps.ideaconsult.net:8080/ambit2/feature/28402&property=ID&search=%s&feature_uris[]=http://apps.ideaconsult.net:8080/ambit2/dataset/1736/feature' target=_blank>Search</a></h3>",URLEncoder.encode(molid.toString())));
				htmlFileWriter.write("\n<table border='1'>");
				htmlFileWriter.write("\n<tr>");
				
				String smiles = g.createSMILES((IMolecule)mol);
				String uri = getImageURI(smiles);
				String imguri = getImageURI((IMolecule)mol,smartcyp,file.getParentFile(),molid.toString());
				
				htmlFileWriter.write(String.format("<td bgcolor='#DDDDDD'><a href='%s&w=400&h=400' target=_blank><img src='%s' title='%s' alt='%s'></a></td>",
								uri,imguri,smiles,smiles));
				
				System.out.println(molid);
				if (result.classify((IAtomContainer)mol)) {
					IAtomContainerSet set = smartcyp.getProducts((IAtomContainer)mol);
					//Assert.assertNotNull(set);
					//Assert.assertTrue(set.getAtomContainerCount()>0);
					if (set != null) {
						System.out.println(set.getAtomContainerCount());
						for (int i=0; i < set.getAtomContainerCount(); i++) {
							htmlFileWriter.write("<td>");
							uri = getImageURI(set.getAtomContainer(i), null, file.getParentFile(), String.format("%s_%d",molid,i+1));
							System.out.println(uri);
							htmlFileWriter.write(String.format("<img src='%s'><br>",
									uri,uri)); 
							htmlFileWriter.write("</td>");
						}
					}
				}
				htmlFileWriter.write("</tr></table>");
			}
			
			htmlFileWriter.write("</body></html>");
		} finally {
			reader.close();
			htmlFileWriter.close();
		}
		
	}
	@Test
	public void test() throws Exception {
		SMARTCYPPlugin smartcyp = new SMARTCYPPlugin();
		smartcyp.setImageSize(new Dimension(200,200));
		boolean explicitH = true;
		AtomConfigurator  cfg = new AtomConfigurator();
		SMIRKSManager smrkMan = new SMIRKSManager();
		SmilesGenerator g = new SmilesGenerator();
		HydrogenAdderProcessor hadder = new HydrogenAdderProcessor();
		hadder.setAddEexplicitHydrogens(explicitH);
		
		File file = new File(getClass().getClassLoader().getResource("toxtree/test/plugins/smartcyp/3A4_substrates.sdf").getFile());

		MDLWriter[] writers = new MDLWriter[reactions.length];
		
		File masterFile = new File(String.format("%s/targets_and_reaction_products.sdf", file.getParentFile()));
		if (masterFile.exists()) masterFile.delete();
		MDLWriter masterWriter = new MDLWriter(new FileOutputStream(masterFile));
		
		File htmlFile = new File(String.format("%s/targets_and_reaction_products.html", file.getParentFile()));
		if (htmlFile.exists()) htmlFile.delete();
		FileWriter htmlFileWriter = new FileWriter(htmlFile);
		htmlFileWriter.write(String.format("<html><head><title>%s</title></head><body>",explicitH?"Explicit H":"Implicit H"));
		
					
		htmlFileWriter.write("<a href='#Compounds'>Compounds<a>&nbsp;<a href='#Reactions'>Reactions<a><hr>");
		htmlFileWriter.write("<a name='#Compounds'/a>");
		IMolecule placeholder = NoNotificationChemObjectBuilder.getInstance().newInstance(IMolecule.class);
		IteratingMDLReader reader = new IteratingMDLReader(new FileInputStream(file), NoNotificationChemObjectBuilder.getInstance());
		
		Hashtable<String, String> compounds = new Hashtable<String, String>();
		
		int record = 0;
		try {
			while (reader.hasNext()) {
				record++;
				if (record > 1) break;
				IChemObject mol = reader.next();

				Assert.assertTrue(mol instanceof IAtomContainer);
				hadder.process((IAtomContainer)mol);
				cfg.process((IAtomContainer)mol);
				CDKHueckelAromaticityDetector.detectAromaticity((IAtomContainer)mol);
				
				Object molid = mol.getProperty("ID");
				
				masterWriter.setSdFields(mol.getProperties());
				masterWriter.writeMolecule((IMolecule)mol) ;
				

				htmlFileWriter.write(String.format("<h3><a name='%s'>%s</a>",molid,molid));
				
				htmlFileWriter.write(String.format("&nbsp;<a href='http://apps.ideaconsult.net:8080/ambit2/compound?feature_uris[]=http://apps.ideaconsult.net:8080/ambit2/feature/28402&property=ID&search=%s&feature_uris[]=http://apps.ideaconsult.net:8080/ambit2/dataset/1736/feature' target=_blank>Search</a></h3>",URLEncoder.encode(molid.toString())));
				htmlFileWriter.write("\n<table border='1'>");
				htmlFileWriter.write("\n<tr>");
				
				String smiles = g.createSMILES((IMolecule)mol);
				String uri = getImageURI(smiles);
				String imguri = getImageURI((IMolecule)mol,smartcyp,file.getParentFile(),molid.toString());
				
				htmlFileWriter.write(String.format("<td bgcolor='#DDDDDD'><a href='%s&w=400&h=400' target=_blank><img src='%s' title='%s' alt='%s'></a></td>",
								uri,imguri,smiles,smiles));
				
				System.out.println(molid);
				for (int i=0; i < reactions.length; i++) {
					
					if (writers[i] == null) {
						File output = new File(String.format("%s/%s.sdf", file.getParentFile(),reactions[i][0]));
						System.out.println(output);
						if (output.exists()) output.delete();
						writers[i] = new MDLWriter(new FileOutputStream(output));
					}
					IAtomContainer c = (IAtomContainer) ((IAtomContainer)mol).clone();
					try {
						c.getProperties().clear();
						placeholder.getProperties().clear();
						c = applySMIRKSReaction(smrkMan, reactions[i][1], c);
						if (c==null) c = placeholder;
						c.setProperty(reactions[i][0],  reactions[i][1]);
						c.setProperty("ID",molid);
						
						
					} catch (Exception x) {
						System.out.println(reactions[i][0]);
						//x.printStackTrace();
						throw x;
					}

					
					if (c!= placeholder) {
						
						String ptr = compounds.get(reactions[i][0]);
						compounds.put(reactions[i][0],String.format("%s&nbsp;<a href='#%s'>%s</a> ",ptr==null?"":ptr,molid,molid));
						
						smiles = g.createSMILES(c);
						htmlFileWriter.write(String.format("<td><a href='#%s' title='%s'>%s<a><br>",reactions[i][0],reactions[i][1], reactions[i][0]));
						uri = getImageURI(smiles);
						
						imguri = getImageURI(smiles,file.getParentFile(),String.format("%s_%s",molid.toString(),reactions[i][0]));
						
						htmlFileWriter.write(String.format("<a href='%s&w=400&h=400' target=_blank><img src='%s' title='%s' alt='%s'></a><br>",
								uri,imguri,smiles,smiles)); 
						htmlFileWriter.write("</td>");
						
						writers[i].setSdFields(mol.getProperties());
						writers[i].writeMolecule((IMolecule)mol) ;		
						writers[i].setSdFields(c.getProperties());
						writers[i].writeMolecule(c);
						
						masterWriter.setSdFields(c.getProperties());
						masterWriter.writeMolecule(c) ;
					}
				}
				htmlFileWriter.write("</tr>");
				htmlFileWriter.write("</table>");
			}
			
			
			htmlFileWriter.write("<h3><a name='#Reactions'>Reactions<a></h3>");
			htmlFileWriter.write("<table width='100%' border='1'>");
			for (int i=0; i < reactions.length; i++) {
				
				htmlFileWriter.write(String.format("\n<tr><th width='20%%'><a name='%s'>%s</a></th><td width='20%%'>%s</td><td width='60%%'>", 
						reactions[i][0], reactions[i][0], reactions[i][1]));
				if (compounds.get(reactions[i][0])!=null) htmlFileWriter.write(compounds.get(reactions[i][0]));
				htmlFileWriter.write(String.format("</td></tr>"));
			}
			htmlFileWriter.write("</table>");
			htmlFileWriter.write("</body></html>");
		} finally {
			reader.close();
			for (MDLWriter writer:writers) if (writer!=null) writer.close();
			masterWriter.close();
			htmlFileWriter.close();
		}
	}
	
	protected String getImageURI(String smiles) {
		return String.format("http://apps.ideaconsult.net:8080/ambit2/depict/cdk?search=%s",URLEncoder.encode(smiles));
	}	
	/**
tetranor
tetranor_S-oxidation
Cannot percieve atom type for the 9th atom: S
	 * @param smiles
	 * @param folder
	 * @param name
	 * @return
	 */
	protected String getImageURI(String smiles, File folder, String name) {
		return getImageURI(smiles,null,folder,name);
	}
	protected String getImageURI(String smiles, IProcessor<IAtomContainer,IChemObjectSelection> selector, File folder, String name) {
		
		
		File imgFolder = new File(String.format("%s/images/",folder));
		if (!imgFolder.exists()) imgFolder.mkdir();
		String file = String.format("%s/%s.png", imgFolder.getAbsolutePath(),name);
		try {
			System.out.println(name);
			BufferedImage img = tool.generateImage(smiles,selector,true,false);
			ImageIO.write(img, "png",new FileOutputStream(file));
		} catch (Exception x) {
			System.out.println(file);
			x.printStackTrace();
		}
		return String.format("images/%s.png", name);
		
	}	
	protected String getImageURI(IAtomContainer ac, IStructureDiagramHighlights hilights, File folder, String name) {
		
		
		File imgFolder = new File(String.format("%s/%s/",folder,hilights==null?"images":"images"));
		if (!imgFolder.exists()) imgFolder.mkdir();
		String file = String.format("%s/%s.png", imgFolder.getAbsolutePath(),name);
	/**
	 * on S-oxidation
java.lang.NullPointerException
	at org.openscience.cdk.renderer.generators.BasicAtomGenerator.showCarbon(BasicAtomGenerator.java:346)
	at org.openscience.cdk.renderer.generators.BasicAtomGenerator.invisibleCarbon(BasicAtomGenerator.java:218)
	at org.openscience.cdk.renderer.generators.BasicAtomGenerator.canDraw(BasicAtomGenerator.java:244)
	at org.openscience.cdk.renderer.generators.BasicAtomGenerator.generate(BasicAtomGenerator.java:262)
	at org.openscience.cdk.renderer.generators.BasicAtomGenerator.generate(BasicAtomGenerator.java:169)
	at org.openscience.cdk.renderer.generators.BasicAtomGenerator.generate(BasicAtomGenerator.java:53)
	at org.openscience.cdk.renderer.AbstractRenderer.generateDiagram(AbstractRenderer.java:116)
	at org.openscience.cdk.renderer.AtomContainerRenderer.paint(AtomContainerRenderer.java:197)
	at org.openscience.cdk.renderer.AtomContainerRenderer.paint(AtomContainerRenderer.java:101)
	at ambit2.rendering.CompoundImageTools.paint(CompoundImageTools.java:452)
	 */
		try {
			IAtomContainer c = (IAtomContainer) ac.clone();
			AtomContainerManipulator.removeHydrogensPreserveMultiplyBonded(c);

			BufferedImage img = hilights==null?tool.getImage(c,null,true,false):hilights.getImage(c);
			ImageIO.write(img, "png",new FileOutputStream(file));
		} catch (Exception x) {
			System.out.println(file);
			x.printStackTrace();
		}
		return String.format("images/%s.png", name);
		
	}

}
