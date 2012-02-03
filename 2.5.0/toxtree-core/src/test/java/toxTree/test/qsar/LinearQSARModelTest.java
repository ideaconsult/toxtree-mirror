/*
Copyright Ideaconsult Ltd. (C) 2005-2007  
Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/


package toxTree.test.qsar;


import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.IMolecularDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.XLogPDescriptor;
import org.openscience.cdk.templates.MoleculeFactory;

import toxTree.logging.TTLogger;
import toxTree.qsar.LinearQSARModel;
import toxTree.query.MolAnalyser;
import ambit2.base.exceptions.QSARModelException;

public class LinearQSARModelTest extends TestCase {
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		TTLogger.configureLog4j(true);
	}
	public void testPredictIAtomContainer() {
		List<String> names = new ArrayList<String>();
		names.add("LogP");
		
		List<IMolecularDescriptor> descriptors = new ArrayList<IMolecularDescriptor>();
		descriptors.add(new XLogPDescriptor());
		
		String result = "Result";
		
		double weights[] = new double[] {0.5,0};
		LinearQSARModel model = new LinearQSARModel(names,null,result,weights);
		
		IAtomContainer ac = MoleculeFactory.makeAlkane(5);
		
		//No value and no descriptor assigned, should throw exception
		try {
			MolAnalyser.analyse(ac);
			model.verify();
			model.predict(ac);
		} catch (QSARModelException x) {
			assertTrue(true);
		} catch (Exception x) {
			x.printStackTrace();
			fail(x.getMessage());
		}
		
		//value assigned , no descriptors set, should calculate value
		double logp = 3.1849999999999996;
		try {
			ac.setProperty(names.get(0),logp);
			model.verify();
			double value = model.predict(ac);
			assertEquals(logp*weights[0],value,0);
		} catch (Exception x) {
			x.printStackTrace();
			fail();
		}	
		
		//value not assigned , but descriptors set, should calculate value
		try {
			ac.removeProperty(names.get(0));
			model.setDescriptors(descriptors);
			model.verify();
			double value = model.predict(ac);
			assertEquals(logp*weights[0],value,0);
			assertEquals(logp,new Double(ac.getProperty(names.get(0)).toString()),0.0);
		} catch (Exception x) {
			x.printStackTrace();
			fail();
		}				
		
		System.out.println(model.toString());
	}

}


