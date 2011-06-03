package toxtree.test.plugins.smartcyp.smirks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Hashtable;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.io.iterator.IteratingMDLReader;
import org.openscience.cdk.nonotify.NoNotificationChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesGenerator;

import ambit2.core.io.MDLWriter;
import ambit2.core.processors.structure.AtomConfigurator;
import ambit2.smarts.SMIRKSManager;
import ambit2.smarts.SMIRKSReaction;

public class SMIRKSTest {
	protected String[][] reactions  = {
	{		
	"N-dealkylation",
	"[N:1][C:2]([H])>>[N:1][H].[C:2]=[O]" 
	},

	{
	"N-oxidation",
	"[N:1][C:2]([H])>>[N:1](-[O])[C:2]"
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
	"[O:1][C:2]([H])>>[O:1][H].[C:2]=[O]"
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
	"[C:1]=[C:2]>>[C:1]1=[C:2][O]1"
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
	public void test() throws Exception {
		AtomConfigurator  cfg = new AtomConfigurator();
		SMIRKSManager smrkMan = new SMIRKSManager();
		SmilesGenerator g = new SmilesGenerator();
		
		File file = new File(getClass().getClassLoader().getResource("toxtree/test/plugins/smartcyp/3A4_substrates.sdf").getFile());

		MDLWriter[] writers = new MDLWriter[reactions.length];
		
		File masterFile = new File(String.format("%s/targets_and_reaction_products.sdf", file.getParentFile()));
		if (masterFile.exists()) masterFile.delete();
		MDLWriter masterWriter = new MDLWriter(new FileOutputStream(masterFile));
		
		File htmlFile = new File(String.format("%s/targets_and_reaction_products.html", file.getParentFile()));
		if (htmlFile.exists()) htmlFile.delete();
		FileWriter htmlFileWriter = new FileWriter(htmlFile);
		htmlFileWriter.write("<html><head></head><body>");
		htmlFileWriter.write("<a href='#Compounds'>Compounds<a>&nbsp;<a href='#Reactions'>Reactions<a><hr>");
		htmlFileWriter.write("<a name='#Compounds'/a>");
		IMolecule placeholder = NoNotificationChemObjectBuilder.getInstance().newInstance(IMolecule.class);
		IteratingMDLReader reader = new IteratingMDLReader(new FileInputStream(file), NoNotificationChemObjectBuilder.getInstance());
		
		Hashtable<String, String> compounds = new Hashtable<String, String>();
		
		try {
			while (reader.hasNext()) {
				IChemObject mol = reader.next();
				Assert.assertTrue(mol instanceof IAtomContainer);
				
				cfg.process((IAtomContainer)mol);
				CDKHueckelAromaticityDetector.detectAromaticity((IAtomContainer)mol);
				
				masterWriter.setSdFields(mol.getProperties());
				masterWriter.writeMolecule((IMolecule)mol) ;
				
				Object molid = mol.getProperty("ID");
				
				htmlFileWriter.write(String.format("<h3><a name='%s'>%s</a></h3>", molid,molid));
				
				htmlFileWriter.write("<table>");
				htmlFileWriter.write("<tr>");
				
				String smiles = g.createSMILES((IMolecule)mol);
				htmlFileWriter.write(String.format("<td><img src='http://apps.ideaconsult.net:8080/ambit2/depict/cdk?search=%s'></td>",smiles));
				
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
						htmlFileWriter.write(String.format("<img src='http://apps.ideaconsult.net:8080/ambit2/depict/cdk?search=%s'><br>",smiles)); 
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
			htmlFileWriter.write("<table width='100%'>");
			for (int i=0; i < reactions.length; i++) {
				
				htmlFileWriter.write(String.format("<tr><th width='20%%'><a name='%s'>%s</a></th><td width='20%%'>%s</td><td width='60%%'><div width='60%%' style='position: absolute;  word-wrap:break-word;''>", 
						reactions[i][0], reactions[i][0], reactions[i][1]));
				if (compounds.get(reactions[i][0])!=null) htmlFileWriter.write(compounds.get(reactions[i][0]));
				htmlFileWriter.write(String.format("</div></td></tr>"));
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

}
