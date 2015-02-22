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
package sicret.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

import junit.framework.TestCase;

import org.openscience.cdk.Molecule;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionResult;
import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleList;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.query.MolAnalyser;

public abstract class RulesTestCase extends TestCase {
	public static Logger logger = Logger.getLogger(RulesTestCase.class.getName());
	protected IDecisionMethod rules = null;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(RulesTestCase.class);
	}

	public RulesTestCase() {
		super();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	protected boolean verifyRule(Molecule mol,int id) throws DecisionMethodException {
		return rules.getRule(id).verifyRule(mol);
	}
	protected void classify(IAtomContainer mol,IDecisionMethod rules, int categories){
		assertEquals(rules.getCategories().size(),categories);
		IDecisionResult result = rules.createDecisionResult();
		result.setDecisionMethod(rules);
		try {
			result.classify(mol);
			System.out.println(result.toString());
		} catch (DecisionResultException x) {
			//if (rules != null) x.printStackTrace();
			assertTrue(rules == null);
		}
		try {
		    System.out.println(result.explain(true));
		} catch (DecisionResultException x) {
			//if (rules != null) x.printStackTrace();
		}    
		System.out.println(result.getCategory());		
	}

	
	public boolean goTestRule(IDecisionRule rule) throws Exception {
		    IAtomContainer m1 = rule.getExampleMolecule(true);
		    
		    MolAnalyser.analyse(m1);
		    boolean r1 = rule.verifyRule(m1);
		    IAtomContainer m2 = rule.getExampleMolecule(false);
		    
		    MolAnalyser.analyse(m2);
		    boolean r2 = rule.verifyRule(m2);
		    
		    String message = rule.toString();
		    if (!r1) message += "\t'Yes' example failed.";
		    if (r2) message += message + "\t'No' example failed.";
		    if (!r1 || r2) throw new DecisionMethodException(message);
		    else return true;
	}
	public void testImplementedRules() {
		tryImplementedRules();
	}
	public void tryImplementedRules() {
	    int nr = rules.getNumberOfRules();
	    int ne = 0;
	    int ni = 0;
	    int ok = 0;
	    int na = 0;
	    for (int i = 0; i < nr; i++) {
	        IDecisionRule rule = rules.getRule(i);
	        if (rule.isImplemented()) {
	            try {
	                if (goTestRule(rule)) {
	                	ok++;
	                }
	                else 
	                    System.err.println(rule.toString() + "\t" + rule.toString() + "\tError");
	            } catch (Exception x) {
	                System.err.println(rule.toString() + "\t" + x.getMessage());
	                ne++;
	            }
	            ni++;
	        } else  System.err.println(rule.toString() + "\tNOT IMPLEMENTED");
	        na++;
	    }
	    System.err.println("Number of rules available\t"+ nr);
	    System.err.println("Number of rules verified\t"+ na);
	    System.err.println("Number of implemented rules\t"+ ni);
	    System.err.println("Number of correclty implemented rules\t"+ ok);
	    System.err.println("Number of exceptions\t"+ ne);
	    /*
	    assertEquals(nr-2,ni);
	    assertEquals(nr-2,ok);
	    */
	    assertEquals(nr,ni);
	    assertEquals(nr,ok);	    
	    assertEquals(0,ne);
	}	

	public void testHasExamples() {
	    System.err.println();
	    int nr = rules.getNumberOfRules();
	    int ne = 0;
	    for (int i = 0; i < nr; i++) {
	        IDecisionRule rule = rules.getRule(i);
            try {
       		    rule.getExampleMolecule(true);
            } catch (DecisionMethodException x) {
                System.err.println(rule.toString() + "\t'Yes' example not available");
                ne++;
            }
            try {
       		    rule.getExampleMolecule(false);
            } catch (DecisionMethodException x) {
                System.err.println(rule.toString() + "\t'No' example not available");
                ne++;
            }            
	    }
	    System.err.println("Number of rules available\t"+ nr);
	    System.err.println("Number of missing examples\t"+ ne);	    
	    assertEquals(0,ne);
	}	
    public void testHasUnreachableRules() {
    	IDecisionRuleList unvisited = rules.hasUnreachableRules();
    	if (unvisited != null) {
    		System.err.println("Unvisited rules:");
    		System.err.println(unvisited);
    	}
    	assertNull(unvisited);
    }
	protected Object objectRoundTrip(Object rule,String filename) {		
		try {
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
			System.out.println(rule.toString());
			assertEquals(rule,rule2);
			return rule2;
			
		} catch (IOException x) {
			x.printStackTrace();
			fail();
		} catch (ClassNotFoundException x) {
			x.printStackTrace();
			fail();			
		}
		return null;
	}	
	
}
