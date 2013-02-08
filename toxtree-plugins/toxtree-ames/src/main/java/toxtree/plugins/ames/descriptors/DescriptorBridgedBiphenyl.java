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

import java.util.logging.Level;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorSpecification;

import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.SmartsPatternCDK;

public class DescriptorBridgedBiphenyl extends DescriptorStructurePresence<IAtomContainer> {
	protected static String BiBr = "I(BiBr)";

	public DescriptorBridgedBiphenyl() {
		super();
		try {
		setParameters(new Object[] {
//				"c1ccccc1!@[*;!N]!@c2ccccc2",
				//"[$(c1ccccc1!@[*;!N]!@c2ccccc2),$(c1ccccc1!@[N]!@[$(ccN),$(cccN),$(ccccN)])]",
				"[$(c1ccccc1!@[*;!N]!@c2ccccc2),$(c1ccccc1!@[N]!@c2c(N)cccc2),$(c1ccccc1!@[N]!@c2cc(N)ccc2),$(c1ccccc1!@[N]!@c2ccc(N)cc2),$(c1ccccc1!@[N]!@c2cccc(N)c2),$(c1ccccc1!@[N]!@c2ccccc(N)2)]",

				//"c1ccccc1!@[N]!@c2ccccc2",
				BiBr}
				);
		} catch (CDKException x) {

			setResultName(BiBr);
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
	}
	
	 public DescriptorSpecification getSpecification() {
	        return new DescriptorSpecification(
	            "True if contains bridged biphenyl group, false otherwise. The bridge should not be composed by a functional amino group.",
	            this.getClass().getName(),
	            "$Id: DescriptorBridgedBiphenyl.java  2007-08-21 10:31 nina$",
	            "ToxTree Ames mutagenicity plugin");
	    }

    @Override
    protected ISmartsPattern<IAtomContainer> createSmartsPattern() {
        return new SmartsPatternCDK();
    }
}
