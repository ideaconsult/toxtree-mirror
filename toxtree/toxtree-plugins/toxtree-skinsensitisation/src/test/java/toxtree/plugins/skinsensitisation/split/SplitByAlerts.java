package toxtree.plugins.skinsensitisation.split;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.io.iterator.IteratingMDLReader;
import org.openscience.cdk.nonotify.NoNotificationChemObjectBuilder;

import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleList;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.MolAnalyseException;
import toxTree.query.MolAnalyser;
import toxTree.tree.MultiLabelDecisionNode;
import toxTree.tree.rules.StructureAlert;
import toxtree.plugins.skinsensitisation.SkinSensitisationPlugin;
import ambit2.smarts.query.ISmartsPattern;

public class SplitByAlerts {
	
	public static void main(String[] args) {
		try {
			SkinSensitisationPlugin rules = new SkinSensitisationPlugin();
			IDecisionRuleList list = rules.getRules();

			
			File file = new File(args[0]);
			IteratingMDLReader reader = new IteratingMDLReader(new FileInputStream(file),NoNotificationChemObjectBuilder.getInstance());
			while (reader.hasNext()) {
				IChemObject object = reader.next();
				if (object instanceof IMolecule) {
					IMolecule mol = (IMolecule) object;
					System.out.println("\nMOLECULE "+ mol.getProperty("SMILES"));
					//normalize molecule, sets various flags
					try {
						MolAnalyser.analyse(mol);
					} catch (MolAnalyseException x) {
					    throw new DecisionMethodException(x);
					}
					System.out.println("ReactionDomain "+ mol.getProperty("ReactionDomain"));
					//launch decision tree as a CDK descriptor 
					//DescriptorValue value = rules.calculate((IMolecule)mol);
					//System.out.println(value.getValue());
					int found = 0;
					for (int i=0; i < list.size();i++) {
						IDecisionRule rule = list.get(i);
						if (rule instanceof MultiLabelDecisionNode) {
							rule = ((MultiLabelDecisionNode)rule).getRule();
							
							if (rule instanceof StructureAlert) {
								
								Hashtable<String, ISmartsPattern<IAtomContainer>> smartsPatterns = ((StructureAlert)rule).getSmartsPatterns();
								Enumeration<String> smarts = smartsPatterns.keys();
								while (smarts.hasMoreElements()) {
									
									ISmartsPattern<IAtomContainer> smartsPattern = smartsPatterns.get(smarts.nextElement());
									int result = smartsPattern.hasSMARTSPattern(mol);
									if (result>0) {
										if (found==0)	System.out.println("PREDICTED "+ rule.getClass().getName());
										System.out.println(String.format("SMARTS [%d] %s",result,smartsPattern));
										found++;
									}
								}
								
							}
						}
					}
				}
			}
			reader.close();
			
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}
