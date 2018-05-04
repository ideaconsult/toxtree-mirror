package toxtree.tree.cramer3.test;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import junit.framework.Assert;

import org.junit.Test;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleList;
import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.DefaultCategory;
import toxTree.tree.ReportTreePrinter;
import toxtree.tree.cramer3.RevisedCramerDecisionTree;
import toxtree.tree.cramer3.TreeSpecification;

public class LoadTest {
	@Test
	public void test_loadrules() throws Exception {
		TreeSpecification treespec = new TreeSpecification();
		Assert.assertEquals(3, treespec.getCategories().length);
		for (int i = 0; i < 3; i++) {
			Assert.assertNotNull(treespec.getCategories()[i]);
			Object rule = Class.forName(treespec.getCategories()[i]).newInstance();
			Assert.assertNotNull(rule);
			Assert.assertTrue(rule instanceof IDecisionCategory);
		}

		Assert.assertEquals(35, treespec.getRules().length);
		for (int i = 0; i < 35; i++) {
			Assert.assertNotNull(treespec.getRules()[i]);
			Object rule = Class.forName(treespec.getRules()[i]).newInstance();
			Assert.assertNotNull(rule);
			Assert.assertTrue(rule instanceof IDecisionRule);
			Assert.assertNotNull(((IDecisionRule) rule).getTitle());
			Assert.assertNotNull(((IDecisionRule) rule).getExplanation());
			System.out.println(String.format("%d.\t%s\t%s", i,((IDecisionRule) rule).getID(),((IDecisionRule) rule).getTitle()));
		}

		Assert.assertEquals(35, treespec.getTransitions().length);
		for (int i = 0; i < 35; i++) {
			Assert.assertNotNull(treespec.getTransitions()[i]);
			Assert.assertTrue(treespec.getTransitions()[i][0]>0 || treespec.getTransitions()[i][1]>0 || treespec.getTransitions()[i][2]>0 || treespec.getTransitions()[i][3] >0);
			/*
			System.out.println(String.format("{%d,\t%d,\t%d,\t%d}", treespec.getTransitions()[i][0],
					treespec.getTransitions()[i][1], treespec.getTransitions()[i][2], treespec.getTransitions()[i][3]));
					*/
		}
	}
	
	@Test
	public void testUnusedCategories() throws Exception {
		try {
			RevisedCramerDecisionTree cr = new RevisedCramerDecisionTree();
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
			RevisedCramerDecisionTree cr = new RevisedCramerDecisionTree();
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
			//cr.walkRules(cr.getTopRule(),new SimpleTreePrinter(System.out));
			cr.walkRules(cr.getTopRule(),new ReportTreePrinter(new File("html/index.html")));
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	/*
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
	*/
	
	@Test
	public void testHasExamples()  throws Exception {
	    System.err.println();
	    RevisedCramerDecisionTree rules = new RevisedCramerDecisionTree();
	    int nr = rules.getNumberOfRules();
	    int n_yes = 0;
	    int n_no = 0;
	    for (int i = 0; i < nr; i++) {
	        IDecisionRule rule = rules.getRule(i);
            try {
       		    rule.getExampleMolecule(true);
            } catch (DecisionMethodException x) {
                System.err.println(rule.toString() + "\t'Yes' example not available");
                n_yes++;
            }
            try {
       		    rule.getExampleMolecule(false);
            } catch (DecisionMethodException x) {
                System.err.println(rule.toString() + "\t'No' example not available");
                n_no++;
            }            
	    }
	    System.err.println("Number of rules available\t"+ nr);
	    System.err.println(String.format("Number of missing examples yes: %d\tno: %d\t", n_yes,n_no));	    
	    Assert.assertEquals(0,n_yes);
	    Assert.assertEquals(0,n_no);	    
	}	
	@Test
    public void testHasUnreachableRules() throws Exception {
		RevisedCramerDecisionTree rules = new RevisedCramerDecisionTree();
    	IDecisionRuleList unvisited = rules.hasUnreachableRules();
    	if (unvisited != null) {
    		System.err.println("Unvisited rules:");
    		System.err.println(unvisited);
    	}
    	//Assert.assertTrue((unvisited==null) || (unvisited.size()==0));
    	/**
    	 * RuleQ2 #12
    	 */
    	Assert.assertTrue((unvisited!=null) && (unvisited.size()==1));  
    }

}
