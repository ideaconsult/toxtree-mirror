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

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorSpecification;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.result.BooleanResult;

import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.SmartsPatternCDK;

/**
 * Returns true if aniline, false otherwise. Used in {@link RuleDACancerogenicityAromaticAmines}.
 * @author nina
 *
 */
public class DescriptorIsAniline extends DescriptorStructurePresence<IAtomContainer> {
	protected  static String Aniline = "I(An)";
	protected ISmartsPattern<IAtomContainer> smp;
	public DescriptorIsAniline() {
		super();
		try {
			smp = new SmartsPatternCDK("c3ccccc3!@[*]!@c2ccccc2");
		setParameters(new Object[] {
				//"c1ccccc1N([#1])[#1]",
                //"[$([NX3][cR1r6]);!$([cR1r6]!@[*]!@[cR1r6])]",
                //"[$([cR1r6]-;!@[!R]-;!@[cR1r6])]",
                
				
				"[$(c([#1,A])1c([#1,A])c([#1,A])c([#1,A])c([#1,A])c1[Nv3]);!$(c3ccccc3!@[*]!@c2ccccc2)]",
				
				
				//"c([#1,A])1c([#1,A&!$([Ac])])c([#1,A&!$([Ac])])c([#1,A&!$([Ac])])c([#1,A])c1[Nv3]",
				
                
                //"[NX3][$(C=C),$(cc)]",
                //"[NX3][$(c1[cR1r6]([#1,!R])[cR1r6]([#1,!R])[cR1r6]([#1,!R])[cR1r6]([#1,!R])c1)]",
				Aniline}
				);
		} catch (Exception x) {
			setResultName(Aniline);
			logger.log(Level.SEVERE,x.getMessage(),x);
		}
	}
	
	 public DescriptorSpecification getSpecification() {
	        return new DescriptorSpecification(
	            "True if aniline, false otherwise",
	            this.getClass().getName(),
	            "$Id: DescriptorIsAniline.java 2007-04-09 13:01 nina$",
	            "ToxTree Mutant plugin");
	    }

    @Override
    protected ISmartsPattern<IAtomContainer> createSmartsPattern() {
        try {
            return new SmartsPatternCDK();
        } catch (Exception x) {
            return null;
        }
    }
    @Override
    public DescriptorValue calculate(IAtomContainer container)  {
    	boolean biphenyl = false;
    	try {
    		biphenyl = smp.match(container) > 0;
    	} catch (Exception x) {
            return new DescriptorValue(getSpecification(), getParameterNames(), getParameters(),
                    new BooleanResult(false), getDescriptorNames(),x);    		
    	}
    	if (biphenyl) {
    		logger.finer("Diphenyl");
            return new DescriptorValue(getSpecification(), getParameterNames(), getParameters(),
                    new BooleanResult(false), getDescriptorNames());
    	} else
    		return super.calculate(container);
    }
    
    /*
    public DescriptorValue calculate(IAtomContainer container) throws CDKException {
        MolFlags mf = (MolFlags) container.getProperty(MolFlags.MOLFLAGS);
        if (mf == null) throw new CDKException("Structure should be preprocessed by MolAnalyser");
        if (mf.getRingset().getAtomContainerCount() ==1) { //one ring only
            return super.calculate(container);
        } else 
            return new DescriptorValue(getSpecification(), getParameterNames(), getParameters(),
                    new BooleanResult(false), new String[]{getResultName()});
    
    }
    */
    

}


