/*
Copyright Ideaconsult Ltd. (C) 2005-2007 

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/
package toxTree.test.tree;

import java.util.Observable;
import java.util.Observer;

import junit.framework.TestCase;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionCategory;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionResult;
import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleList;
import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNode;
import toxTree.tree.DecisionNodesFactory;
import toxTree.tree.DefaultCategory;
import toxTree.tree.SimpleTreePrinter;
import toxTree.tree.UserDefinedTree;
import toxTree.tree.cramer.CramerRules;
import toxTree.tree.rules.RuleAnySubstructure;
import toxTree.tree.rules.RuleElements;
import toxTree.ui.tree.TreeLayout;

/**
 * Test for {@link UserDefinedTree}
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-7
 */
public class UserDefinedTreeTest extends TestCase {
	public int count = 0;
	public static void main(String[] args) {
		junit.textui.TestRunner.run(UserDefinedTreeTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Constructor for UserDefinedTreeTest.
	 * @param arg0
	 */
	public UserDefinedTreeTest(String arg0) {
		super(arg0);
	}
	public void test() {
		count = 0;
		UserDefinedTree tree = new UserDefinedTree();
		tree.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				IDecisionMethod tree = (IDecisionMethod) o;
				if (tree.getRules().size()>0) {
					count++;
				}
			}
		});
		IDecisionRuleList rules = tree.getRules();
		DecisionNode rule1 = new DecisionNode(new RuleAnySubstructure());
		rules.add(rule1);
		IDecisionRule rule2 = new DecisionNode(new RuleElements());
		rules.add(rule2);
		rule1.setBranch(true,rule2);
		assertEquals(3,count);
	}
	public void testTreeLayout() {
		count = 0;
		UserDefinedTree tree = new UserDefinedTree();
		TreeLayout treeLayout = new TreeLayout(tree);
		
		treeLayout.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				
				IDecisionMethod tree = ((TreeLayout) o).getMethod();
				if (tree.getRules().size()>0) {
					count++;
					//System.out.println(tree.getRules().size());
				}
			}
		});
		IDecisionRuleList rules = tree.getRules();
		IDecisionCategories categories = tree.getCategories();
		IDecisionCategory c1 = new DefaultCategory("First",1);
		IDecisionCategory c2 = new DefaultCategory("Second",2);
		categories.addCategory(c1);
		categories.addCategory(c2);
		
		DecisionNode node1 = new DecisionNode(new RuleAnySubstructure(),null,null,c1,c2);
		rules.add(node1);
		DecisionNode node2 = new DecisionNode(new RuleElements(),null,null,c1,c2); 
		rules.add(node2);
		node1.setBranch(false,node2);
		assertEquals(3,count);		
	}
	
	
	public void testUnusedCategories() {
		try {
			CramerRules cr = new CramerRules();
			final int  nc = cr.getCategories().size();
			IDecisionCategory c = new DefaultCategory("test",4);
			cr.getCategories().addCategory(c);
			
			((Observable)cr.getCategories()).addObserver(new Observer() {
				public void update(Observable arg0, Object arg1) {
					assertEquals(nc,((IDecisionCategories)arg0).size());
					
				}
			});
			IDecisionCategories unused = cr.hasUnusedCategories();
			assertNotNull(unused);
			assertEquals(1,unused.size());
			assertEquals(c,unused.get(0));
			cr.getCategories().remove(unused.get(0));
			unused = cr.hasUnusedCategories();
			assertNull(unused);
			c = (IDecisionCategory)cr.getCategories().get(0);
			c.setName("new name");
			
			//c.getEditor().edit(null, c);
			assertEquals(nc,cr.getCategories().size());
			//UserDefinedTree tree = new UserDefinedTree(cr);
			//assertEquals(cr.getRules().size(),tree.getRules().size());
		} catch (DecisionMethodException x) {
			x.printStackTrace();
		}
	}
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
	
	public void testMultipleLabels() {
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
		try {
			IDecisionMethod tree = new UserDefinedTree(classes,customRules,customTransitions,new DecisionNodesFactory(true));
			IAtomContainer m = FunctionalGroups.createAtomContainer("c1ccc(P)cc1",true);
			IDecisionResult r = tree.createDecisionResult();
			r.classify(m);
			System.out.println(r.explain(true));
			tree.walkRules(tree.getTopRule(), new SimpleTreePrinter(System.out));
		} catch (Exception x) {
			fail(x.getMessage());
		}
	}
}
