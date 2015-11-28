/*
Copyright (C) 2005-2008  

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

package toxTree.tree.rules.smarts;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolFlags;
import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.SMARTSException;
import ambit2.smarts.query.SmartsPatternFactory;
import ambit2.smarts.query.SmartsPatternFactory.SmartsParser;

/**
 * An IDecisionRule, making use of Ambit SMARTS parser.
 * http://ambit.sourceforge.net/AMBIT2-LIBS/ambit2-smarts/index.html
 * http://onlinelibrary.wiley.com/doi/10.1002/minf.201100028/abstract
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 *
 */
public class RuleSMARTSSubstructureAmbit extends AbstractRuleSmartSubstructure<IAtomContainer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4541044864575270095L;

	@Override
	protected IAtomContainer getObjectToVerify(IAtomContainer mol) {
		return mol;
	}

	public ISmartsPattern createSmartsPattern(String smarts, boolean negate) throws SMARTSException {
		return SmartsPatternFactory.createSmartsPattern(
				SmartsParser.smarts_nk, smarts, negate);		
	}	

    protected IRingSet hasRingsToProcess(IAtomContainer  mol) throws DecisionMethodException {
        MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
        if (mf == null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
        return mf.getRingset();
    }
}


