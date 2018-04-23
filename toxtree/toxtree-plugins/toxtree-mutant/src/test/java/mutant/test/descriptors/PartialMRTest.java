/*
Copyright (C) 2005-2007  

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package mutant.test.descriptors;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.query.FunctionalGroups;
import ambit2.core.helper.CDKHueckelAromaticityDetector;
import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.SmartsPatternCDK;

public class PartialMRTest  {
	protected static Logger logger = Logger.getLogger(PartialMRTest.class
			.getName());

	@Test
	public void test() {
		
	}
	
	public void xtest() throws Exception {
		Object[][] patterns = new Object[][] {
				{"0.8",new SmartsPatternCDK("[cr6]:[cR2r6]:[cr6]")},
				{"0.56",new SmartsPatternCDK("[cr5]")},
				{"0.65",new SmartsPatternCDK("[cr6]:[cR2r6]:[nR1r6]")} //C=1C=CC=2N=CC=CC=2(C=1)")},
				 						//"c1cc2c(cc1)c1ccccc1C2")} 
				
				//
				
		};
        Object[][] smiles = new Object[][] {
//testNaphtalenes        		
                {"Nc1ccc2ccccc2(c1)","group1",new Integer(10),"group2",new Integer(0),"MR5",new Double(0.1),"MR3",new Double(0.8),"MR4",new Double(0.8),"MR2",new Double(0.1),"MR6",new Double(0.1)},
                {"NCCNC=1C=CC2=CC=CC=C2(C=1)","group1",new Integer(10),"group2",new Integer(0),"MR5",new Double(0.1),"MR3",new Double(0.8),"MR2",new Double(0.8),"MR6",new Double(0.1)},
//testAnthracenes
                {"Nc1cccc2cc3ccccc3(cc12)","MR5",new Double(0.1),"MR3",new Double(0.8),"MR2",new Double(0.1),"MR6",new Double(0.1)},
                {"Nc1ccc2cc3ccccc3(cc2(c1))","MR5",new Double(0.1),"MR3",new Double(0.8),"MR2",new Double(0.8),"MR6",new Double(0.1)},
//testPhenanthrenes
                {"Nc3cccc2c3(ccc1ccccc12)","MR1",new Double(0.54),"MR5",new Double(0.1),"MR4",new Double(0.1),"MR3",new Double(0.8),"MR2",new Double(0.8),"MR6",new Double(0.1)},
                {"Nc3ccc2c(ccc1ccccc12)c3","MR1",new Double(0.54),"MR5",new Double(0.1),"MR3",new Double(0.8),"MR2",new Double(0.1),"MR4",new Double(0.8),"MR6",new Double(0.1)},
//testPyrenes                
                {"Nc1ccc2ccc3cccc4ccc1c2c34","MR5",new Double(0.1),"MR3",new Double(0.8),"MR2",new Double(0.8),"MR6",new Double(0.1)},
                {"Nc1cc2ccc3cccc4ccc(c1)c2c34","MR5",new Double(0.8),"MR3",new Double(0.8),"MR2",new Double(0.1),"MR6",new Double(0.1)},
                 {"Nc1cc4cccc3ccc2cccc1c2c34","MR5",new Double(0.8),"MR3",new Double(0.8),"MR2",new Double(0.8),"MR6",new Double(0.1)},
                                 
//test5Crings
                 {"CC(=O)NC3=CC=CC=2CC=1C=CC=CC=1C=23","MR5",new Double(0.1),"MR3",new Double(0.56),"MR2",new Double(0.56),"MR6",new Double(0.1)},
                 {"Nc1cccc2c4cccc3cccc(c12)c34","MR5",new Double(0.1),"MR3",new Double(0.56),"MR2",new Double(0.56),"MR6",new Double(0.1)},
                 {"Nc1ccc2c4cccc3cccc(c2(c1))c34","MR5",new Double(0.1),"MR3",new Double(0.56),"MR2",new Double(0.1),"MR6",new Double(0.1)},

//testMixedNaphtaleneFluorene                 
                 {"Nc2ccc3cccc4c1ccccc1c2c34","MR5",new Double(0.1),"MR3",new Double(0.8),"MR2",new Double(0.56),"MR6",new Double(0.1)},
                 {"Nc2cc3cccc4c1ccccc1c(c2)c34","MR5",new Double(0.8),"MR3",new Double(0.56),"MR2",new Double(0.1),"MR6",new Double(0.1)},
/*                 
//testHeterocyclicRings                 
                 //105
                 {"Nc1cccc2cccnc12","group4",new Integer(1),"MR5",new Double(0.1),"MR3",new Double(0.8),"MR2",new Double(0.65),"MR6",new Double(0.1)},                
                 //2
                 {"NC=1C=CC2=NC=3C(N)=CC=CC=3(N=C2(C=1))","group4",new Integer(4),"MR5",new Double(0.1),"MR3",new Double(0.65),"MR2",new Double(0.1),"MR6",new Double(0.1)},
                 //3
                 {"NC1=CC=CC2=NC3=CC=CC(N)=C3(N=C12)","group4",new Integer(4),"MR5",new Double(0.1),"MR3",new Double(0.65),"MR2",new Double(0.65),"MR6",new Double(0.1)},
*/                 
            };
        for (int i=0; i < smiles.length;i++) {
        	logger.log(Level.FINE,smiles[i][0].toString());
            IAtomContainer a = FunctionalGroups.createAtomContainer(smiles[i][0].toString(), false);
            AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(a);
            CDKHueckelAromaticityDetector.detectAromaticity(a);
            for (int p = 0; p < patterns.length;p++) {
	            int matches = ((ISmartsPattern)patterns[p][1]).match(a);
	            List<List<Integer>> list = ((ISmartsPattern)patterns[p][1]).getUniqueMatchingAtoms();
	            System.out.print(matches);
	            System.out.print('\t');
	            System.out.print(patterns[p][0]);
	            System.out.print('\t');
	            System.out.println(list.size());
	            for (int j=0; j < list.size();j++) {
	            	List<Integer> l = list.get(j); 
	            	for (int k=0; k < l.size();k++) {
	            		a.getAtom(l.get(k)).setProperty("PARTIAL_MR",patterns[p][0]);
	            		/*
	            		System.out.print(a.getAtom(l.get(k)).getSymbol());
	            		System.out.print('[');
	            		System.out.print(l.get(k));
	            		System.out.print(']');
	            		System.out.print('\t');
	            		*/
	            	}	
	            	//System.out.print('\n');
	            }		
            }
            for (int j=0; j < a.getAtomCount(); j++) {
        		System.out.print(a.getAtom(j).getSymbol());
        		System.out.print('[');
        		Object val = a.getAtom(j).getProperty("PARTIAL_MR");
        		System.out.print(val==null?"":val.toString());
        		System.out.print(']');
        		System.out.print('\t');
            }
            System.out.print('\n');            
        }     
		
	}
}


