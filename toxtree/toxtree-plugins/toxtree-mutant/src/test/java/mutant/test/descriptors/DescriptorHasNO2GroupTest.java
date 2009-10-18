/* DescriptorHasNO2GroupTest.java
 * Author: Nina Jeliazkova
 * Date: Feb 16, 2008 
 * Revision: 0.1 
 * 
 * Copyright (C) 2005-2008  Nina Jeliazkova
 * 
 * Contact: nina@acad.bg
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All we ask is that proper credit is given for our work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * 
 */

package mutant.test.descriptors;

import mutant.descriptors.DescriptorHasNO2Group;

import org.openscience.cdk.qsar.IMolecularDescriptor;

public class DescriptorHasNO2GroupTest extends DescriptorsTest {

    protected void setUp() throws Exception {
        super.setUp();
        addPropertiesToTest("I(NO2)_expected","I(NO2)");        
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected IMolecularDescriptor createDescriptorToTest() throws Exception {
        return new DescriptorHasNO2Group();
    }

    @Override
    public String getResultsFile() {
        return "aromatic_amines/qsar8train_no2.csv";      
    }

    @Override
    public String getSourceFile() {
        return "aromatic_amines/qsar8train.csv";

    }

    @Override
    public String getStructureID() {
        return "CAS Number";
    }

}
