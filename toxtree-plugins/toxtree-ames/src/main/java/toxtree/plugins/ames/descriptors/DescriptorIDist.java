/*
Copyright Ideaconsult Ltd. (C) 2005-2012 

Contact: jeliazkova.nina@gmail.com

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

package toxtree.plugins.ames.descriptors;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.qsar.DescriptorSpecification;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.IMolecularDescriptor;
import org.openscience.cdk.qsar.result.IDescriptorResult;
import org.openscience.cdk.qsar.result.IntegerResult;

import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.SMARTSException;
import ambit2.smarts.query.SmartsPatternCDK;

/**
  * The indicator variable Idist is a structural parameter coding for the
  * presence (Idist = 1, otherwise Idist = 0) of crowded substituents on the positions
  * 3'-, 4'- and 5'- of 4-aminobiphenyl.
  * <br>
  * In particular, Methyl, Ethyl or Propyl substituents are not defined as
  * crowded, whilst tri-substituted methyl and butyl (or bigger) substituents are defined
  * as crowded substituents.
  * <br>
  * For the other functional groups, those who have Partial MR values >= 1.96 (butyl), should have Idist =1.
  * <br>
  * Please note that trifluoromethyl has Idist=1 irrespective of the MR value (0.5)
 * @author Nina Jeliazkova
 *
 */
public class DescriptorIDist implements IMolecularDescriptor {
	protected String[] names = {"Idist"};
	protected ISmartsPattern[] smarts= null;
	
	public DescriptorIDist() throws Exception {
		super();
		smarts = new SmartsPatternCDK[7];
		smarts[0] = new SmartsPatternCDK("c(C([A&!#1])([A&!#1])([A&!#1]))ccc!@c1ccc([NH2])cc1");
		smarts[1] = new SmartsPatternCDK("cc(C([A&!#1])([A&!#1])([A&!#1]))cc!@c1ccc([NH2])cc1");
        smarts[2] = new SmartsPatternCDK("c(C([A&!#1][A&!#1][A&!#1]))ccc!@c1ccc([NH2])cc1");
        smarts[3] = new SmartsPatternCDK("cc(C([A&!#1][A&!#1][A&!#1]))cc!@c1ccc([NH2])cc1");            
        smarts[4] = new SmartsPatternCDK("NC=1C=CC3=C(C=1)CC=2C=C([!#1])C=CC=23");
        //smarts[5] = new SmartsPatternCDK("cc(C)cc!@c1ccc([NH2])cc1");
        
        smarts[5] = new SmartsPatternCDK("c([$(C3C1CC2CC(C1)CC3(C2))])ccc-c1ccc([NH2])cc1");
        smarts[6] = new SmartsPatternCDK("cc([$(C3C1CC2CC(C1)CC3(C2))])cc-c1ccc([NH2])cc1");        
            

	}
	
	public DescriptorValue calculate(IAtomContainer arg0)  {
		if (smarts == null)
	        return new DescriptorValue(getSpecification(), getParameterNames(), getParameters(),
	                new IntegerResult(0), getDescriptorNames(),
	                new CDKException("Substructure not assigned!"));

		if (arg0 instanceof IMolecule) {
			
	        Object mol = null;
			int ok = 0;
			try {
				for (int i=0; i < smarts.length;i++) {
					if (mol == null)
						mol = smarts[i].getObjectToVerify(arg0);
					if (smarts[i].hasSMARTSPattern(mol)>0) {
						ok = 1;
						//System.out.print("\tYES");
						break;
					} //else System.out.print("\tNO");

				}	
				
		        return new DescriptorValue(getSpecification(), getParameterNames(), getParameters(),
		                new IntegerResult(ok), getDescriptorNames());
			} catch (SMARTSException x) {
		        return new DescriptorValue(getSpecification(), getParameterNames(), getParameters(),
		                new IntegerResult(ok), getDescriptorNames(),x);				
			}
		} else 
	        return new DescriptorValue(getSpecification(), getParameterNames(), getParameters(),
	                new IntegerResult(0), getDescriptorNames(),new CDKException("IMolecule expected!"));
	}

	public IDescriptorResult getDescriptorResultType() {
		return new IntegerResult(0);
	}

	public String[] getDescriptorNames() {
		return names;
	}
	public String[] getParameterNames() {
		return null;
	}

	public Object getParameterType(String arg0) {
		return null;
	}

	public Object[] getParameters() {
		return null;
	}

	public DescriptorSpecification getSpecification() {
        StringBuffer b = new StringBuffer();
        b.append("The indicator variable Idist is a structural parameter coding for the ");
        b.append("presence (Idist = 1, otherwise Idist = 0) of crowded substituents on the positions ");
        b.append("3'-, 4'- and 5'- of 4-aminobiphenyl. ");
        b.append("In particular, Methyl, Ethyl or Propyl substituents are not defined as ");
        b.append("crowded, whilst tri-substituted methyl and butyl (or bigger) substituents are defined ");
        b.append("as crowded substituents.");
        b.append("For the other functional groups, those who have Partial MR values >= 1.96 (butyl), should have Idist =1.");
        b.append("Please note that trifluoromethyl has Idist=1 irrespective of the MR value (0.5)");


        return new DescriptorSpecification(
	            b.toString(),
	            this.getClass().getName(),
	            "$Id: DescriptorIDist.java  2007-08-21 12:59 nina$",
	            "ToxTree Mutant plugin");
	}

	public void setParameters(Object[] arg0) throws CDKException {
		throw new CDKException("No parameters are expected!");

	}

}
