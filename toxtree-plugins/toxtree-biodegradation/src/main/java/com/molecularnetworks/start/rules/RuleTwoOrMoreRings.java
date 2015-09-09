/*
 * $Revision$ $Author: joerg $ $Date: 2008-12-04 18:43:31 +0100 (Thu, 04 Dec 2008) $
 *
 * Copyright (C) 1997-2008  $Author: joerg $
 *
 * Contact: info@molecular-networks.com
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
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

package com.molecularnetworks.start.rules;

//import java.util.logging.Level;
//import java.util.logging.Logger;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.graph.Cycles;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IRingSet;

import toxTree.core.IDecisionRule;
import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.AbstractRule;

/**
 * Biodegradation rule for chemicals with two or more rings.
 * 
 * @version $Id: RuleTwoOrMoreRings.java 936 2008-12-04 17:43:31Z joerg $
 * @author <a href="mailto:info@molecular-networks.com">Molecular Networks</a>
 * @author $Author: joerg $
 */
public class RuleTwoOrMoreRings extends AbstractRule {

    /**
     * 
     */
    private static final long serialVersionUID = -7413443885130587425L;

    /**
     * Default constructor
     */
    public RuleTwoOrMoreRings() {
	setExplanation("Chemicals with two or more rings " + "are associated with low biodegradability.");
	id = "9";
	title = "Two or more rings";
	examples[0] = "C1CCCC1CC";
	examples[1] = "C1CCCC1CCC1CCCC1C";
	editable = false;
    }

    /**
     * Overrides the default behaviour. Returns always TRUE.
     * 
     * @return is implemented result, boolean
     */
    @Override
    public boolean isImplemented() {
	return true;
    }

    /**
     * Overrides the default {@link IDecisionRule} behaviour. Returns TRUE, if
     * the answer of the rule is YES for the analyzed molecule
     * {@link org.openscience.cdk.interfaces.AtomContainer} Returns FALSE, if
     * the answer of the rule is NO for the analyzed molecule
     * {@link org.openscience.cdk.interfaces.AtomContainer}
     * 
     * @param mol
     *            {@link org.openscience.cdk.interfaces.AtomContainer}
     * @return rule result, boolean
     * @throws {@link DecisionMethodException}
     */
    public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
	try {
		//TODO this is redundant, there is already mol.getProperty() containing the rings
	    IRingSet rings = Cycles.all(mol).toRingSet();
	    return rings.getAtomContainerCount() >= 2;
	} catch (CDKException ex) {
	    throw new DecisionMethodException(ex);
	}
    }

}
