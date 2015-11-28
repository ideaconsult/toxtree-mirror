package toxtree.test.plugins.smartcyp;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.query.MolAnalyser;
import toxTree.tree.AbstractTree;
import toxtree.plugins.smartcyp.SMARTCYPPlugin;

public class RulesSelectorTest {

	@Test
	public void testRulesWithSelector() throws Exception {
		SMARTCYPPlugin rules = new SMARTCYPPlugin();
		int na = ((AbstractTree) rules).testRulesWithSelector();

		Assert.assertEquals(0, na);
	}

	@Test
	public void testRuleHilights() throws Exception {
		SMARTCYPPlugin rules = new SMARTCYPPlugin();
		IAtomContainer mol = MoleculeFactory.makeAlkane(6);
		BufferedImage image = rules.getImage(mol, "2", 200, 200, false);
		File file = new File(String.format("%s/testRuleHilights.png",
				System.getProperty("java.io.tmpdir")));
		file.deleteOnExit();
		ImageIO.write(image, "png", file);
		Assert.assertTrue(file.exists());

	}

	@Test
	public void testTreeHilights() throws Exception {
		SMARTCYPPlugin rules = new SMARTCYPPlugin();
		SmilesParser parser = new SmilesParser(
				SilentChemObjectBuilder.getInstance());
		IAtomContainer mol = parser.parseSmiles("NCCC1=CC(O)=C(O)C=C1"); // NCCc1ccc(O)c(O)c1
		MolAnalyser.analyse(mol);
		BufferedImage image = rules.getImage(mol, null, 200, 200, false);

		File file = new File(String.format("%s/testTreeHilights.png",
				System.getProperty("java.io.tmpdir")));
		if (file.exists())
			file.delete();
		file.deleteOnExit();
		ImageIO.write(image, "png", file);
		Assert.assertTrue(file.exists());

	}
}
