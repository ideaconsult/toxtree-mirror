package toxtree.test.plugins.smartcyp;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.tree.AbstractTree;
import toxtree.plugins.smartcyp.SMARTCYPPlugin;

public class RulesSelectorTest {
	
	@Test
	public void testRulesWithSelector() throws Exception {
		SMARTCYPPlugin rules = new SMARTCYPPlugin();
	    int na = ((AbstractTree)rules).testRulesWithSelector();
		 
	    Assert.assertEquals(0,na);
	}
	
	@Test
	public void testRuleHilights() throws Exception {
		SMARTCYPPlugin rules = new SMARTCYPPlugin();
		IMolecule mol = MoleculeFactory.makeAlkane(6);
	    BufferedImage image = rules.getImage(mol, "2", 200,200,false);
	    File file = new File(String.format("%s/test.png",System.getProperty("java.io.tmpdir")));
	    file.deleteOnExit();
		ImageIO.write(image, "png", file) ;
		Assert.assertTrue(file.exists());

	}
	
	@Test
	public void testTreeHilights() throws Exception {
		SMARTCYPPlugin rules = new SMARTCYPPlugin();
		IMolecule mol = MoleculeFactory.makeAlkane(6);
	    BufferedImage image = rules.getImage(mol,null, 200,200,false);
	    File file = new File(String.format("%s/test.png",System.getProperty("java.io.tmpdir")));
	    file.deleteOnExit();
		ImageIO.write(image, "png", file) ;
		Assert.assertTrue(file.exists());

	}	
}

