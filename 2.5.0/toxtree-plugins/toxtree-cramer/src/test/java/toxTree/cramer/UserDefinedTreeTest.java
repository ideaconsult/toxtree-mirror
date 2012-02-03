package toxTree.cramer;

import java.util.Observable;
import java.util.Observer;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNodesFactory;
import toxTree.tree.DefaultCategory;
import toxTree.tree.SimpleTreePrinter;
import toxTree.tree.UserDefinedTree;
import toxTree.tree.cramer.CramerRules;

/**
 * CramerRules related classes moved here from toxtree.core
 * @author nina
 *
 */
public class UserDefinedTreeTest {

	@Test
	public void testUnusedCategories() throws Exception {
		try {
			CramerRules cr = new CramerRules();
			final int  nc = cr.getCategories().size();
			IDecisionCategory c = new DefaultCategory("test",4);
			cr.getCategories().addCategory(c);
			
			((Observable)cr.getCategories()).addObserver(new Observer() {
				public void update(Observable arg0, Object arg1) {
					Assert.assertEquals(nc,((IDecisionCategories)arg0).size());
					
				}
			});
			IDecisionCategories unused = cr.hasUnusedCategories();
			Assert.assertNotNull(unused);
			Assert.assertEquals(1,unused.size());
			Assert.assertEquals(c,unused.get(0));
			cr.getCategories().remove(unused.get(0));
			unused = cr.hasUnusedCategories();
			Assert.assertNull(unused);
			c = (IDecisionCategory)cr.getCategories().get(0);
			c.setName("new name");
			
			//c.getEditor().edit(null, c);
			Assert.assertEquals(nc,cr.getCategories().size());
			//UserDefinedTree tree = new UserDefinedTree(cr);
			//assertEquals(cr.getRules().size(),tree.getRules().size());
		} catch (DecisionMethodException x) {
			x.printStackTrace();
		}
	}
	@Test
	public void testWalkTree() {
		try {
			CramerRules cr = new CramerRules();
			/*
			IProcessRule processor = new IProcessRule() {
				public void process(IDecisionMethod method, IDecisionRule rule) {
					System.out.println(rule);
					
			    	final boolean[] answers = {true,false};
			    	final String[] answersLabels = {"YES","NO"};
			    	for (int i=0; i < answers.length;i++) {
			    		IDecisionCategory category = method.getCategory(rule, answers[i]);
			    		IDecisionRule nextrule = method.getBranch(rule, answers[i]);
			    		if (category != null)
			    			System.out.println(answersLabels[i]+"\tAssign label\t"+category);
			    		if (nextrule != null)
			    			System.out.println(answersLabels[i]+"\tGo to\tQ"+nextrule.getID());
			    	}
				};
			};
			*/
			cr.walkRules(cr.getTopRule(),new SimpleTreePrinter(System.out));
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	@Test
	public void testMultipleLabels() throws Exception {
		IDecisionCategories classes = new CategoriesList();
		classes.add(new DefaultCategory("First",1));
		classes.add(new DefaultCategory("Second",2));
		classes.add(new DefaultCategory("Third",3));
		classes.add(new DefaultCategory("Fourth",4));
		String[] customRules = {
				"toxTree.tree.rules.RuleAromatic",
				"toxTree.tree.cramer.RuleHasOtherThanC_H_O_N_S2"
				};
		int[][] customTransitions = {
				{0,2,1,2},
				{0,0,3,4}
		};
			IDecisionMethod tree = new UserDefinedTree(classes,customRules,customTransitions,new DecisionNodesFactory(true));
			IAtomContainer m = FunctionalGroups.createAtomContainer("c1ccc(P)cc1",true);
			IDecisionResult r = tree.createDecisionResult();
			r.classify(m);
			tree.walkRules(tree.getTopRule(), new SimpleTreePrinter(System.out));

	}
}
