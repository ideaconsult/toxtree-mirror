package toxTree.cramer;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionResult;
import toxTree.core.IDecisionRuleList;
import toxTree.tree.DecisionNodesList;
import toxTree.tree.RulesList;
import toxTree.tree.UserDefinedTree;
import toxTree.tree.cramer.CramerClass1;
import toxTree.tree.cramer.CramerClass3;
import toxTree.tree.cramer.CramerRules;
import toxTree.tree.cramer.RuleCommonComponentOfFood;
import toxTree.tree.cramer.RuleRingComplexSubstituents30;
import toxTree.tree.rules.RuleLipinski5;

public class TreeSerializerTest  {

	protected static Logger logger = Logger.getLogger(TreeSerializerTest.class.getName());
	protected Object objectRoundTrip(Object rule,String filename) throws Exception  {		
			//writing
			File f = File.createTempFile(filename,"test");
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f));
			os.writeObject(rule);
			os.close();
			
			//reading
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(f));
			Object rule2 =is.readObject();
			is.close();
			f.delete();
			logger.finer(rule.toString());
			Assert.assertEquals(rule,rule2);
			return rule2;

	}	
	@Test
	public void testRulesList() throws Exception  {
		IDecisionRuleList rules = new RulesList();
		rules.addRule(new RuleCommonComponentOfFood());
		rules.addRule(new RuleLipinski5());
		objectRoundTrip(rules);
		Assert.assertTrue(rules.get(0) instanceof RuleCommonComponentOfFood);
		Assert.assertTrue(rules.get(1) instanceof RuleLipinski5);
	}
	@Test
	public void testDecisionNodesList()  throws Exception{
		IDecisionRuleList rules = new DecisionNodesList();
		rules.addRule(new RuleCommonComponentOfFood());
		rules.addRule(new RuleLipinski5());
		objectRoundTrip(rules);
		Assert.assertTrue(rules.getRule(0) instanceof RuleCommonComponentOfFood);
		Assert.assertTrue(rules.getRule(1) instanceof RuleLipinski5);
	}	
	@Test
	public void testTreeResult() throws Exception {
		CramerRules cr=null;
		cr = new CramerRules();
		IDecisionResult tr = cr.createDecisionResult();
		tr.setDecisionMethod(cr);
	    tr.classify(MoleculeFactory.makeAlkane(3));
			Object tr1 = objectRoundTrip(tr);
			Assert.assertTrue(tr1 instanceof IDecisionResult);
			//System.out.println(tr.explain(true).toString());
			//System.out.println(((IDecisionResult)tr1).explain(true).toString());
			Assert.assertEquals(tr,tr1);
	}
	
	public void testTreeResultNullMethod() throws Exception  {
		CramerRules rules = null;
		rules = new CramerRules();
		IDecisionResult tr = rules.createDecisionResult();
		tr.setDecisionMethod(null);
		Assert.assertEquals(tr.getCategory(),null);
			Object tr1 = objectRoundTrip(tr);
			Assert.assertTrue(tr1 instanceof IDecisionResult);
			Assert.assertNull(((IDecisionResult)tr1).getCategory());
	}		
	public void testRules() throws Exception  {
		objectRoundTrip(new RuleRingComplexSubstituents30());
	}
	
	protected Object objectRoundTrip(Object rule) throws Exception {		
		//writing
		FileOutputStream os = new FileOutputStream(rule.getClass().getName()+".xml");
		XMLEncoder encoder = new XMLEncoder(os);
		encoder.writeObject(rule);
		encoder.close();			
		
		//reading
		FileInputStream is = new FileInputStream(rule.getClass().getName()+".xml");
		XMLDecoder decoder = new XMLDecoder(is);
		Object rule2 = decoder.readObject();
		decoder.close();	
		
		logger.finer(rule.toString());
		Assert.assertEquals(rule,rule2);
		return rule2;

}		
	@Test
	public void testUserDefinedTree() throws Exception {
		UserDefinedTree rules = null;
		
			rules = new UserDefinedTree();
			rules.getRules().addRule(new RuleCommonComponentOfFood());
			rules.getCategories().addCategory(new CramerClass1());
			rules.getCategories().addCategory(new CramerClass3());
			Assert.assertEquals(rules.getCategories().size(),2);
			Object r1 = objectRoundTrip(rules);
			Assert.assertEquals(rules,r1);
			Assert.assertEquals(rules.getCategories().size(),((IDecisionMethod)r1).getCategories().size());
		
	}		
	@Test
	public void testCramer() throws Exception  {
		CramerRules rules = null;
		rules = new CramerRules();
		objectRoundTrip(rules,"CramerRules");
	}
		
}
