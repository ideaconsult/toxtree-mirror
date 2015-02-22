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


import joelib.desc.types.MolarRefractivity;
import joelib.molecule.JOEMol;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorSpecification;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.IMolecularDescriptor;
import org.openscience.cdk.qsar.result.DoubleResult;
import org.openscience.cdk.qsar.result.IDescriptorResult;

import toxTree.tree.rules.smarts.Convertor;

/**
 * This is now a wrapper of Joelib MolarRefractivity, but needs to be rewritten without joelib.
 * [wc99] S. A. Wildman and G. M. Crippen, Prediction of Physicochemical Parameters by Atomic Contributions, J. Chem. Inf. Comput. Sci., 39, 868-873, 1999
 * @author Nina Jeliazkova
 *
 */
public class DescriptorMolarRefractivity implements IMolecularDescriptor {
	protected static String[] names = {"MR"};
	protected MolarRefractivity mr = new MolarRefractivity();
	
	public DescriptorValue calculate(IAtomContainer arg0)  {
		try {
			JOEMol converted = Convertor.convert((IAtomContainer)arg0);
	        return new DescriptorValue(getSpecification(), 
	        		getParameterNames(), getParameters(),
	                new DoubleResult(mr.getDoubleValue(converted)), names);
			
		} catch (Exception x) {
	        return new DescriptorValue(getSpecification(), 
	        		getParameterNames(), getParameters(),
	                new DoubleResult(0), names,x);			
		}
	}

	public IDescriptorResult getDescriptorResultType() {
		return new DoubleResult(0);
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
	        return new DescriptorSpecification(
	            "MolarRefractivity",
	            this.getClass().getName(),
	            "$Id: DescriptorMolarRefractivity.java 2007-08-21 5:56 nina$",
	            "ToxTree Mutant plugin");
	    }

	public void setParameters(Object[] arg0) throws CDKException {
		throw new CDKException("No parameters expected!");
	}
	public String[] getDescriptorNames() {
		return names;
	}

}
