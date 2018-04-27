package toxtree.plugins.skinsensitisation.split;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.openscience.cdk.interfaces.IAtomContainer;

import ambit2.base.interfaces.IStructureRecord;
import ambit2.core.io.RawIteratingSDFReader;
import ambit2.core.processors.structure.MoleculeReader;
import ambit2.smarts.query.ISmartsPattern;
import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleList;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.MolAnalyseException;
import toxTree.query.MolAnalyser;
import toxTree.tree.MultiLabelDecisionNode;
import toxTree.tree.rules.StructureAlert;
import toxtree.plugins.skinsensitisation.SkinSensitisationPlugin;

public class SplitByAlerts {
	Hashtable<String, String> sa_folders = new Hashtable<String, String>();
	Hashtable<String, Boolean> fp = new Hashtable<String, Boolean>();
	
	public static void main(String[] args) {
		SplitByAlerts split = new SplitByAlerts();
		split.run(args[0]);
	}	
	/*
	public void runCSV(String filename) {		
		try {
			SkinSensitisationPlugin rules = new SkinSensitisationPlugin();
			IDecisionRuleList list = rules.getRules();

			MoleculeReader mreader = new MoleculeReader();
			
			File file = new File(filename);
			IteratingDelimitedFileReader reader = new IteratingDelimitedFileReader(new FileReader(file));
			DelimitedFileWriter writer = new DelimitedFileWriter(new FileWriter(new File("results.csv")));
			
			
			while (reader.hasNext()) {
				IAtomContainer mol = (IAtomContainer) reader.next();

					//normalize molecule, sets various flags
					try {
						MolAnalyser.analyse(mol);
					} catch (MolAnalyseException x) {
					    throw new DecisionMethodException(x);
					}
					
					for (int i=0; i < list.size();i++) {
						IDecisionRule rule = list.get(i);
						int found = 0;
						if (rule instanceof MultiLabelDecisionNode) {
							rule = ((MultiLabelDecisionNode)rule).getRule();
							
							if (rule instanceof StructureAlert) {
								
								Hashtable<String, ISmartsPattern<IAtomContainer>> smartsPatterns = ((StructureAlert)rule).getSmartsPatterns();
								Enumeration<String> smarts = smartsPatterns.keys();
								while (smarts.hasMoreElements()) {
									
									ISmartsPattern<IAtomContainer> smartsPattern = smartsPatterns.get(smarts.nextElement());
									int result = smartsPattern.hasSMARTSPattern(mol);
									
									if (result>0) {
										
										if (rule.getID().equals(mol.getProperty("ReactionDomain"))) {
											writeMol(record, smartsPattern.getSmarts(), true);
										} else {
											writeMol(record, smartsPattern.getSmarts(), false);
											System.out.println("\nMOLECULE "+ mol.getProperty("SMILES"));
											System.out.println("ReactionDomain "+ mol.getProperty("ReactionDomain"));
											System.out.println("PREDICTED "+ rule.getID());
											System.out.println(String.format("SMARTS [%d] %s",result,smartsPattern));
										}

									}
								}
								
							}
						}
					}
				}

			reader.close();
			FileWriter writer = new FileWriter(new File("dictionary.csv"));
			writer.write("SMARTS,File,\"Has False Positives\"\n");
			Enumeration<String> keys = sa_folders.keys();
			while (keys.hasMoreElements()) {
				String element = keys.nextElement();
				writer.write(String.format("\"%s\",%s,%s\n",
						element,sa_folders.get(element),fp.get(element)));
			}
			writer.close();

		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	*/
	public void run(String filename) {		
		try {
			SkinSensitisationPlugin rules = new SkinSensitisationPlugin();
			IDecisionRuleList list = rules.getRules();

			MoleculeReader mreader = new MoleculeReader();
			
			File file = new File(filename);
			RawIteratingSDFReader reader = new RawIteratingSDFReader(new FileReader(file));
			
			
			
			while (reader.hasNext()) {
				IStructureRecord record = reader.nextRecord();
				IAtomContainer mol = mreader.process(record);

					//normalize molecule, sets various flags
					try {
						MolAnalyser.analyse(mol);
					} catch (MolAnalyseException x) {
					    throw new DecisionMethodException(x);
					}
					//System.out.println("ReactionDomain "+ mol.getProperty("ReactionDomain"));
					//launch decision tree as a CDK descriptor 
					//DescriptorValue value = rules.calculate((IMolecule)mol);
					//System.out.println(value.getValue());
					
					
					for (int i=0; i < list.size();i++) {
						IDecisionRule rule = list.get(i);
						int found = 0;
						if (rule instanceof MultiLabelDecisionNode) {
							rule = ((MultiLabelDecisionNode)rule).getRule();
							
							if (rule instanceof StructureAlert) {
								
								Map<String, ISmartsPattern<IAtomContainer>> smartsPatterns = ((StructureAlert)rule).getSmartsPatterns();
								Iterator<String> smarts = smartsPatterns.keySet().iterator();
								while (smarts.hasNext()) {
									
									ISmartsPattern<IAtomContainer> smartsPattern = smartsPatterns.get(smarts.next());
									int result = smartsPattern.hasSMARTSPattern(mol);
									
									if (result>0) {
										
										if (rule.getID().equals(mol.getProperty("ReactionDomain"))) {
											writeMol(record, smartsPattern.getSmarts(), true);
										} else {
											writeMol(record, smartsPattern.getSmarts(), false);
											/*
											System.out.println("\nMOLECULE "+ mol.getProperty("SMILES"));
											System.out.println("ReactionDomain "+ mol.getProperty("ReactionDomain"));
											System.out.println("PREDICTED "+ rule.getID());
											System.out.println(String.format("SMARTS [%d] %s",result,smartsPattern));
											*/
										}

									}
								}
								
							}
						}
					}
				}

			reader.close();
			FileWriter writer = new FileWriter(new File("dictionary.csv"));
			writer.write("SMARTS,File,\"Has False Positives\"\n");
			Enumeration<String> keys = sa_folders.keys();
			while (keys.hasMoreElements()) {
				String element = keys.nextElement();
				writer.write(String.format("\"%s\",%s,%s\n",
						element,sa_folders.get(element),fp.get(element)));
			}
			writer.close();

		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	/*
	protected void writeMol(IMolecule record, String smarts, boolean match) throws Exception {
		String fileName = sa_folders.get(smarts);

		if (fileName == null) {
			fileName = String.format("file_%d",sa_folders.size()+1);
		}
		sa_folders.put(smarts,fileName);
		
		fp.put(smarts,!match);
		File file = new File(String.format("%s_%s.sdf",fileName,match?"TP":"FP"));
		
		FileWriter writer = new FileWriter(file,file.exists());
		writer.write(record.getContent());
		writer.close();
		
	}
	*/
	protected void writeMol(IStructureRecord record, String smarts, boolean match) throws Exception {
		String fileName = sa_folders.get(smarts);
		boolean append = true;
		if (fileName == null) {
			fileName = String.format("file_%d",sa_folders.size()+1);
			append = false;
			File file = new File(String.format("%s_%s.sdf",fileName,"FP"));
			if (file.exists()) file.delete();
			file = new File(String.format("%s_%s.sdf",fileName,"TP"));
			if (file.exists()) file.delete();
		}
		sa_folders.put(smarts,fileName);
		
		Object m = fp.get(smarts);
		if (m==null) fp.put(smarts,!match);
		else if (!((Boolean)m).booleanValue()) fp.put(smarts,!match);
			
		
		File file = new File(String.format("%s_%s.sdf",fileName,match?"TP":"FP"));
		
		FileWriter writer = new FileWriter(file,append);
		writer.write(record.getContent());
		writer.close();

	}
}
