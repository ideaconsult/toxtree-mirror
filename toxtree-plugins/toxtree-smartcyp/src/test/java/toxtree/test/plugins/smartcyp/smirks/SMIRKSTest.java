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
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.io.SDFWriter;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.templates.MoleculeFactory;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.core.IDecisionResult;
import toxTree.query.MolAnalyser;
import toxTree.tree.stats.ConfusionMatrix;
import toxtree.plugins.smartcyp.SMARTCYPPlugin;
import ambit2.core.data.IStructureDiagramHighlights;
import ambit2.core.data.MoleculeTools;
import ambit2.core.helper.CDKHueckelAromaticityDetector;
import ambit2.core.processors.structure.AtomConfigurator;
import ambit2.core.processors.structure.HydrogenAdderProcessor;
import ambit2.rendering.CompoundImageTools;
import ambit2.rendering.IAtomContainerHighlights;
import ambit2.smarts.SMIRKSManager;
import ambit2.smarts.SMIRKSReaction;
import dk.smartcyp.core.MoleculeKU.SMARTCYP_PROPERTY;
import dk.smartcyp.core.SMARTSData;
import dk.smartcyp.smirks.SMARTCYPReaction;

public class SMIRKSTest {
	protected CompoundImageTools tool = new CompoundImageTools();

	IAtomContainer applySMIRKSReaction(SMIRKSManager smrkMan, String smirks,
			IAtomContainer target) throws Exception {

		/*
		 * CDKHydrogenAdder adder = CDKHydrogenAdder.getInstance(
		 * SilentChemObjectBuilder.getInstance() );
		 */

		// System.out.println("No Impl H Atoms = " +
		// target.getAtom(0).getImplicitHydrogenCount().intValue());

		SMIRKSReaction reaction = smrkMan.parse(smirks);
		if (!smrkMan.getErrors().equals("")) {
			System.out.println(smrkMan.getErrors());
			throw (new Exception("Smirks Parser errors:\n"
					+ smrkMan.getErrors()));
		}

		if (smrkMan.applyTransformation(target, reaction))
			return target; // all products inside the atomcontainer, could be
							// disconnected
		else
			return null;
	}

	/*
	 * SMARTS matches , but SMIRKS does not Expected Predicted All Alcohol
	 * oxidation [10] + Expected Predicted All Aldehyde oxidation [4] + Expected
	 * Predicted All Aliphatic hydroxylation [107] + Expected Predicted All
	 * Aromatization of dihydropyridines [7] + Expected Predicted All
	 * Desulphurization of phosphor [4] + Expected Predicted All Epoxidation
	 * [12] + Expected Predicted All N-dealkylation [174] + Expected Predicted
	 * All N-oxidation [8] + Expected Predicted All new1 [35] + Expected
	 * Predicted All new3 [11] + Expected Predicted All new4 [1] +
	 * 
	 * Not found new 2 [O:1][C:2]([H])>>[O:1][H].[C:2]=[O] S-Oxidation
	 * [#16:1][#6:2]>>[#16:1](=[O])[#6:2] Aromatic_hydroxylation
	 * [c:1][H:2]>>[c:1][O][H:2] O_dealkylation
	 * [O;H0:1][C:2]([H])>>[O:1][H].[C:2]=[O] Thioesther_bond_breaking
	 * [S:1][C:2]=[O:3]>>[S:1][H].[C:2](O)=[O:3]
	 */

	public void testSMARTCypMetabolitesGenerator() throws Exception {
		boolean explicitH = true;
		AtomConfigurator cfg = new AtomConfigurator();
		HydrogenAdderProcessor hadder = new HydrogenAdderProcessor();
		SmilesGenerator g = SmilesGenerator.generic();
		SMARTCYPPlugin smartcyp = new SMARTCYPPlugin();
		File file = new File(
				getClass()
						.getClassLoader()
						.getResource(
								"toxtree/test/plugins/smartcyp/3A4_substrates.sdf")
						.getFile());
		IteratingSDFReader reader = new IteratingSDFReader(new FileInputStream(
				file), SilentChemObjectBuilder.getInstance());

		File htmlFile = new File(String.format("%s/metabolites.html",
				file.getParentFile()));
		if (htmlFile.exists())
			htmlFile.delete();
		FileWriter htmlFileWriter = new FileWriter(htmlFile);
		htmlFileWriter.write(String.format(
				"<html><head><title>%s</title></head><body>",
				explicitH ? "Explicit H" : "Implicit H"));

		// AtomConfigurator cfg = new AtomConfigurator();
		// HydrogenAdderProcessor hadder = new HydrogenAdderProcessor();
		// hadder.setAddEexplicitHydrogens(explicitH);

		IDecisionResult result = smartcyp.createDecisionResult();
		int record = 0;
		ConfusionMatrix matrix = new ConfusionMatrix<String, Comparable>();
		matrix.setExpectedTitle("SOM expected");
		matrix.setPredictedTitle("SOM predicted");
		try {
			while (reader.hasNext()) {
				record++;
				// if (record>1) break;

				IChemObject mol = reader.next();
				Object molid = mol.getProperty("ID");

				// if (!molid.equals("fentanyl")) continue;
				// //http://www.daylight.com/daycgi_tutorials/react.cgi

				mol.setID(molid.toString());
				MolAnalyser.analyse((IAtomContainer)mol);
				// IAtomContainer mol =
				// AtomContainerManipulator.removeHydrogens((IAtomContainer)origin);

				hadder.process((IAtomContainer) mol);
				cfg.process((IAtomContainer) mol);
				CDKHueckelAromaticityDetector
						.detectAromaticity((IAtomContainer) mol);

				htmlFileWriter.write(String.format("<h3><a name='%s'>%s</a>",
						molid, molid));

				htmlFileWriter
						.write(String
								.format("&nbsp;<a href='https://apps.ideaconsult.net/ambit2/compound?feature_uris[]=https://apps.ideaconsult.net/ambit2/feature/28402&property=ID&search=%s&feature_uris[]=http://apps.ideaconsult.net:8080/ambit2/dataset/1736/feature' target=_blank>Search</a></h3>",
										URLEncoder.encode(molid.toString())));
				htmlFileWriter.write("\n<table border='1'>");
				htmlFileWriter.write("\n<tr>");

				String smiles = g.create((IAtomContainer)mol);

				String uri = getImageURI(smiles);

				String imguri = getImageURI((IAtomContainer)mol, smartcyp,
						file.getParentFile(), molid.toString());

				htmlFileWriter
						.write(String
								.format("<td bgcolor='#DDDDDD'><a href='%s&w=400&h=400' target=_blank><img src='%s' title='%s' alt='%s'></a></td>",
										uri, imguri, smiles, smiles));

				// System.out.println(molid);
				if (result.classify((IAtomContainer) mol)) {
					result.assignResult((IAtomContainer) mol);
					Object som_expected = mol.getProperty("PRIMARY_SOM");
					Object som_predicted = mol
							.getProperty("SMARTCYP_PRIMARY_SOM");
					/*
					 * matrix.addEntry(som_expected==null?"NA":String.format("'%s'"
					 * ,som_expected.toString()), som_predicted==null?"NA":
					 * String.format("'%s'",som_predicted.toString()));
					 */
					// htmlFileWriter.write(String.format("<td>Result: %s</td>",mol.getProperties()));
					htmlFileWriter.write(String.format("<td>%s</td>",
							result.getAssignedCategories()));

					htmlFileWriter.write(String.format("<td>"));
					for (IAtom atom : ((IAtomContainer) mol).atoms())
						if (SMARTCYP_PROPERTY.Energy.getData(atom) != null) {
							htmlFileWriter.write("[");
							if (SMARTCYP_PROPERTY.Ranking.getData(atom) != null)
								htmlFileWriter.write(SMARTCYP_PROPERTY.Ranking
										.getData(atom).toString());
							htmlFileWriter.write("]");
							htmlFileWriter.write(atom.getID());
							htmlFileWriter.write(".");
							htmlFileWriter.write(SMARTCYP_PROPERTY.Energy
									.getData(atom).toString());
							htmlFileWriter.write("<br>");
						}
					htmlFileWriter.write(String.format("</td>"));

					IAtomContainerSet set = smartcyp.getProducts(result
							.getRuleResult(0).getMolecule(), null);
					// Assert.assertNotNull(set);
					// Assert.assertTrue(set.getAtomContainerCount()>0);
					if (set != null) {
						// System.out.println(set.getAtomContainerCount());
						for (int i = 0; i < set.getAtomContainerCount(); i++) {
							htmlFileWriter.write("<td>");
							htmlFileWriter.write(set.getAtomContainer(i)
									.getID());
							matrix.addEntry("All", set.getAtomContainer(i)
									.getID());

							htmlFileWriter.write("<br>");
							uri = getImageURI(set.getAtomContainer(i), null,
									file.getParentFile(),
									String.format("%s_%d", molid, i + 1));
							// System.out.println(uri);
							htmlFileWriter.write(String.format(
									"<img src='%s'><br>", uri, uri));
							htmlFileWriter.write("</td>");
						}
					} else {

					}
				}
				htmlFileWriter.write("</tr></table>");
			}
			htmlFileWriter.write(matrix.toString().replace("\n", "<br>"));
			htmlFileWriter.write("</body></html>");
		} finally {
			reader.close();
			htmlFileWriter.close();
		}

	}

	/**
	 * smartcyp + all reactions
	 */
	public void testSmartCypAndApplyAllReactions() throws Exception {
		applyAllReactions(SMARTCYPReaction.values(), true);
	}

	/**
	 * No smartcyp, just try all reactions
	 */
	public void testApplyAllReactions() throws Exception {
		applyAllReactions(SMARTCYPReaction.values(), false);
	}

	public void applyAllReactions(SMARTCYPReaction[] reactions_to_apply,
			boolean products) throws Exception {
		ConfusionMatrix matrix = new ConfusionMatrix();
		SMARTCYPPlugin smartcyp = new SMARTCYPPlugin();
		smartcyp.setImageSize(new Dimension(200, 200));
		boolean explicitH = true;
		AtomConfigurator cfg = new AtomConfigurator();
		SMIRKSManager smrkMan = new SMIRKSManager(
				SilentChemObjectBuilder.getInstance());
		SmilesGenerator g = new SmilesGenerator();
		HydrogenAdderProcessor hadder = new HydrogenAdderProcessor();
		hadder.setAddEexplicitHydrogens(explicitH);

		File file = new File(
				getClass()
						.getClassLoader()
						.getResource(
								"toxtree/test/plugins/smartcyp/3A4_substrates.sdf")
						.getFile());

		SDFWriter[] writers = new SDFWriter[SMARTCYPReaction.values().length];

		File masterFile = new File(String.format(
				"%s/targets_and_reaction_products.sdf", file.getParentFile()));
		if (masterFile.exists())
			masterFile.delete();
		SDFWriter masterWriter = new SDFWriter(new FileOutputStream(masterFile));

		File htmlFile = new File(String.format(
				"%s/targets_and_reaction_products.html", file.getParentFile()));
		if (htmlFile.exists())
			htmlFile.delete();
		FileWriter htmlFileWriter = new FileWriter(htmlFile);
		htmlFileWriter.write(String.format(
				"<html><head><title>%s</title></head><body>",
				explicitH ? "Explicit H" : "Implicit H"));

		htmlFileWriter
				.write("<a href='#Compounds'>Compounds<a>&nbsp;<a href='#Reactions'>Reactions<a><hr>");
		htmlFileWriter.write("<a name='#Compounds'/a>");
		IAtomContainer placeholder = SilentChemObjectBuilder.getInstance()
				.newInstance(IAtomContainer.class);
		IteratingSDFReader reader = new IteratingSDFReader(new FileInputStream(
				file), SilentChemObjectBuilder.getInstance());

		Hashtable<String, String> compounds = new Hashtable<String, String>();

		int record = 0;
		try {
			while (reader.hasNext()) {
				record++;
				// if (record > 1) break;
				IChemObject mol = reader.next();

				Object molid = mol.getProperty("ID");
				if (!"SENECIONINE".equals(molid))
					continue;
				// if (!"barnidipine".equals(molid)) continue;
				// if (!"dihydropyridine".equals(molid)) continue;

				Assert.assertTrue(mol instanceof IAtomContainer);
				hadder.process((IAtomContainer) mol);
				cfg.process((IAtomContainer) mol);
				CDKHueckelAromaticityDetector
						.detectAromaticity((IAtomContainer) mol);

				masterWriter.write(mol);

				htmlFileWriter.write(String.format("<h3><a name='%s'>%s</a>",
						molid, molid));

				htmlFileWriter
						.write(String
								.format("&nbsp;<a href='https://apps.ideaconsult.net/ambit3/compound?feature_uris[]=http://apps.ideaconsult.net:8080/ambit2/feature/28402&property=ID&search=%s&feature_uris[]=https://apps.ideaconsult.net/ambit2/dataset/1736/feature' target=_blank>Search</a></h3>",
										URLEncoder.encode(molid.toString())));
				htmlFileWriter.write("\n<table border='1'>");
				htmlFileWriter.write("\n<tr>");

				String smiles = g.create((IAtomContainer) mol);
				String uri = getImageURI(smiles);
				String imguri = getImageURI((IAtomContainer) mol, smartcyp,
						file.getParentFile(), molid.toString());

				htmlFileWriter
						.write(String
								.format("<td bgcolor='#DDDDDD'><a href='%s&w=400&h=400' target=_blank><img src='%s' title='%s' alt='%s'></a><br><textarea rows='5' cols='20'>%s</textarea></td>",
										uri, imguri, smiles, smiles, smiles));

				String smartcypReaction = "None";
				if (products) {
					IAtomContainerSet set = smartcyp.getProducts(
							(IAtomContainer) ((IAtomContainer) mol).clone(),
							null);
					// Assert.assertNotNull(set);
					// Assert.assertTrue(set.getAtomContainerCount()>0);
					if (set != null) {

						for (int i = 0; i < set.getAtomContainerCount(); i++) {
							if (set.getAtomContainer(i).getAtomCount() == 0)
								matrix.addEntry("No products", molid.toString());
							htmlFileWriter
									.write("<td border='2'><font color='red'>SMARTCyp:&nbsp;");
							htmlFileWriter.write(set.getAtomContainer(i)
									.getID());
							smartcypReaction = set.getAtomContainer(i).getID();

							htmlFileWriter.write("</font><br>");
							uri = getImageURI(set.getAtomContainer(i), null,
									file.getParentFile(),
									String.format("%s_%d", molid, i + 1));
							// System.out.println(uri);
							htmlFileWriter.write(String.format(
									"<img src='%s'><br>", uri, uri));
							htmlFileWriter.write("</td>");

							masterWriter.write(set.getAtomContainer(i));
						}
					} else {
						htmlFileWriter.write("<td border='2'>No products</td>");
						matrix.addEntry("No products", molid.toString());
					}
				}

				for (SMARTCYPReaction reaction : reactions_to_apply) {

					String reaction_name = reaction.name();
					String reaction_smirks = reaction.getSMIRKS();

					if (writers[reaction.ordinal()] == null) {
						File output = new File(String.format("%s/%s.sdf",
								file.getParentFile(), reaction_name));
						System.out.println(output);
						if (output.exists())
							output.delete();
						writers[reaction.ordinal()] = new SDFWriter(
								new FileOutputStream(output));
					}
					IAtomContainer c = (IAtomContainer) ((IAtomContainer) mol)
							.clone();
					try {
						//c.getProperties().clear();
						MoleculeTools.clearProperties(c);
						MoleculeTools.clearProperties(placeholder);
						//placeholder.getProperties().clear();						
						c = applySMIRKSReaction(smrkMan, reaction_smirks, c);

						if (c == null) {
							c = placeholder;

						}

						c.setProperty(reaction_name, reaction_smirks);
						c.setProperty("ID", molid);

					} catch (Exception x) {
						System.out.println(reaction);
						// x.printStackTrace();
						throw x;
					}

					if (c != placeholder) {

						String ptr = compounds.get(reaction_name);
						compounds.put(reaction_name, String.format(
								"%s&nbsp;<a href='#%s'>%s</a> ",
								ptr == null ? "" : ptr, molid, molid));

						smiles = g.createSMILES(c);
						htmlFileWriter.write(String.format(
								"<td><a href='#%s' title='%s'>%s<a><br>",
								reaction_name, reaction_smirks, reaction_name));
						uri = getImageURI(smiles);

						imguri = getImageURI(smiles, file.getParentFile(),
								String.format("%s_%s", molid.toString(),
										reaction_name));

						htmlFileWriter
								.write(String
										.format("<a href='%s&w=400&h=400' target=_blank><img src='%s' title='%s' alt='%s'></a><br>",
												uri, imguri, smiles, smiles));
						htmlFileWriter.write("</td>");

						SDFWriter writer = writers[reaction.ordinal()];
						writer.write(mol);
						writer.write(c);

						// masterWriter.setSdFields(c.getProperties());
						// masterWriter.writeMolecule(c) ;

					}

				}
				htmlFileWriter.write("</tr>");
				htmlFileWriter.write("</table>");
			}

			htmlFileWriter.write("<h3><a name='#Reactions'>Reactions<a></h3>");
			htmlFileWriter.write("<table width='100%' border='1'>");
			for (SMARTCYPReaction reaction : reactions_to_apply) {

				htmlFileWriter
						.write(String
								.format("\n<tr><th width='20%%'><a name='%s'>%s</a></th><td width='20%%'>%s</td><td width='60%%'>",
										reaction.name(), reaction.name(),
										reaction.getSMIRKS()));
				if (compounds.get(reaction.name()) != null)
					htmlFileWriter.write(compounds.get(reaction.name()));
				htmlFileWriter.write(String.format("</td></tr>"));
			}
			htmlFileWriter.write("</table>");
			htmlFileWriter.write(matrix.toString().replace("\n", "<br>"));
			htmlFileWriter.write("</body></html>");
		} finally {
			reader.close();
			for (SDFWriter writer : writers)
				if (writer != null)
					writer.close();
			masterWriter.close();
			htmlFileWriter.close();
		}
	}

	protected String getImageURI(String smiles) {
		return String.format(
				"https://apps.ideaconsult.net/ambit3/depict/cdk?search=%s",
				URLEncoder.encode(smiles));
	}

	/**
	 * tetranor tetranor_S-oxidation Cannot percieve atom type for the 9th atom:
	 * S
	 * 
	 * @param smiles
	 * @param folder
	 * @param name
	 * @return
	 */
	protected String getImageURI(String smiles, File folder, String name) {
		return getImageURI(smiles, null, folder, name);
	}

	protected String getImageURI(String smiles,
			IAtomContainerHighlights selector,
			File folder, String name) {

		File imgFolder = new File(String.format("%s/images/", folder));
		if (!imgFolder.exists())
			imgFolder.mkdir();
		String file = String.format("%s/%s.png", imgFolder.getAbsolutePath(),
				name);
		try {
			System.out.println(name);
			BufferedImage img = tool.generateImage(smiles, selector, true,
					false);
			ImageIO.write(img, "png", new FileOutputStream(file));
		} catch (Exception x) {
			System.out.println(file);
			x.printStackTrace();
		}
		return String.format("images/%s.png", name);

	}

	protected String getImageURI(IAtomContainer ac,
			IStructureDiagramHighlights hilights, File folder, String name) {

		File imgFolder = new File(String.format("%s/%s/", folder,
				hilights == null ? "images" : "images"));
		if (!imgFolder.exists())
			imgFolder.mkdir();
		String file = String.format("%s/%s.png", imgFolder.getAbsolutePath(),
				name);
		/**
		 * on S-oxidation java.lang.NullPointerException at
		 * org.openscience.cdk.renderer
		 * .generators.BasicAtomGenerator.showCarbon(
		 * BasicAtomGenerator.java:346) at
		 * org.openscience.cdk.renderer.generators
		 * .BasicAtomGenerator.invisibleCarbon(BasicAtomGenerator.java:218) at
		 * org.openscience.cdk.renderer.generators.BasicAtomGenerator.canDraw(
		 * BasicAtomGenerator.java:244) at
		 * org.openscience.cdk.renderer.generators
		 * .BasicAtomGenerator.generate(BasicAtomGenerator.java:262) at
		 * org.openscience.cdk.renderer.generators.BasicAtomGenerator.generate(
		 * BasicAtomGenerator.java:169) at
		 * org.openscience.cdk.renderer.generators
		 * .BasicAtomGenerator.generate(BasicAtomGenerator.java:53) at
		 * org.openscience
		 * .cdk.renderer.AbstractRenderer.generateDiagram(AbstractRenderer
		 * .java:116) at
		 * org.openscience.cdk.renderer.AtomContainerRenderer.paint
		 * (AtomContainerRenderer.java:197) at
		 * org.openscience.cdk.renderer.AtomContainerRenderer
		 * .paint(AtomContainerRenderer.java:101) at
		 * ambit2.rendering.CompoundImageTools
		 * .paint(CompoundImageTools.java:452)
		 */
		try {
			IAtomContainer c = (IAtomContainer) ac.clone();
			AtomContainerManipulator.removeHydrogensPreserveMultiplyBonded(c);

			BufferedImage img = hilights == null ? tool.getImage(c, null, true,
					false) : hilights.getImage(c);
			ImageIO.write(img, "png", new FileOutputStream(file));
		} catch (Exception x) {
			System.out.println(file);
			// x.printStackTrace();
		}
		return String.format("images/%s.png", name);

	}

	@Test
	public void testRules() throws Exception {
		SmilesGenerator g = SmilesGenerator.generic().aromatic();
		SMARTCYPPlugin smartcyp = new SMARTCYPPlugin();
		File file = new File(
				getClass()
						.getClassLoader()
						.getResource(
								"toxtree/test/plugins/smartcyp/3A4_substrates.sdf")
						.getFile());
		IteratingSDFReader reader = new IteratingSDFReader(new FileInputStream(
				file), SilentChemObjectBuilder.getInstance());

		AtomConfigurator cfg = new AtomConfigurator();
		HydrogenAdderProcessor hadder = new HydrogenAdderProcessor();
		hadder.setAddEexplicitHydrogens(true);

		IDecisionResult result = smartcyp.createDecisionResult();
		int record = 0;
		try {
			while (reader.hasNext()) {
				record++;
				// if (record>10) break;

				IAtomContainer mol = reader.next();
				Object molid = mol.getProperty("ID");


				mol.setID(molid.toString());
				hadder.process(mol);
				cfg.process((IAtomContainer) mol);
				CDKHueckelAromaticityDetector
						.detectAromaticity((IAtomContainer) mol);

				if (result.classify((IAtomContainer) mol)) {
					IAtomContainer ac = (IAtomContainer) mol;

					for (IAtom atom : ac.atoms()) {
						Number num = SMARTCYP_PROPERTY.Ranking.getNumber(atom);

						if (num == null)
							continue;

						if (num.intValue() == 1) {
							SMARTSData data = SMARTCYP_PROPERTY.Energy
									.getData(atom);
							Number energy = SMARTCYP_PROPERTY.Energy
									.getNumber(atom);
							Assert.assertNotNull(data);
						}

					}

				}

			}

		} finally {
			reader.close();

		}

	}

	@Test
	public void testPyridine() throws Exception {
		IAtomContainer ac = MoleculeFactory.makePyridine();
		ac = AtomContainerManipulator.removeHydrogens(ac);
		AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(ac);
		// AtomContainerManipulator.percieveAtomTypesAndConfigureUnsetProperties(ac);
		CDKHueckelAromaticityDetector.detectAromaticity(ac);
		int aaromatic = 0;
		for (IAtom a : ac.atoms()) {
			// if (a.getFlag(CDKConstants.ISAROMATIC)) {
			aaromatic++;

			// } else
			// if (a.getFlag(CDKConstants.ISINRING)) {
			System.out.print(a.getSymbol());
			System.out.print(" ");
			System.out.print(a.getImplicitHydrogenCount());
			System.out.print(" ");
			System.out.print(a.getFormalNeighbourCount());
			System.out.print(" ");
			System.out.print(a.getAtomTypeName());
			System.out.print(" ");
			System.out.println(a.getBondOrderSum());
			// }
		}
	}
}
