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

import java.util.Enumeration;
import java.util.Iterator;
import java.util.logging.Level;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionRule;
import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.smarts.RuleSMARTSubstructure;
import ambit2.smarts.query.ISmartsPattern;
import ambit2.smarts.query.SMARTSException;

/**
 * Biodegradation rule for unbranched chemicals with two terminal double-bonded
 * carbons.
 * 
 * @version $Id: RuleTwoTerminalDoubleBondsOnUnbranched.java 936 2008-12-04
 *          17:43:31Z joerg $
 * @author <a href="mailto:info@molecular-networks.com">Molecular Networks</a>
 * @author $Author: joerg $
 */
public class RuleTwoTerminalDoubleBondsOnUnbranched extends RuleSMARTSubstructure {

    /**
     * 
     */
    private static final long serialVersionUID = 5246790669401145621L;

    /**
     * Default constructor
     */
    public RuleTwoTerminalDoubleBondsOnUnbranched() {
	super();
	try {
	    super.addSubstructure("1", "[$([*]~C([!#1])~[*])]", true);
	    super.addSubstructure("2", "[$([C&H2]=[C])]");
	    super.setContainsAllSubstructures(true);
	    super.setExplanation("Unbranched chemicals with two terminal "
		    + "double-bonded carbons are associated with low biodegradability.");
	    id = "11";
	    title = "Two terminal double-bonded carbons on unbranched molecule";
	    examples[0] = "C(C)CCC(C)C";
	    examples[1] = "C=CCCCC=C";
	    editable = false;
	} catch (SMARTSException x) {
	    logger.log(Level.SEVERE, x.getMessage(), x);
	}
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
    @Override
    public boolean verifyRule(org.openscience.cdk.interfaces.IAtomContainer mol) throws DecisionMethodException {
	logger.finer(getID());
	IAtomContainer moltotest = getObjectToVerify(mol);
	if (!isAPossibleHit(mol, moltotest)) {
	    logger.finer("Not a possible hit due to the prescreen step.");
	    return false;
	}
	Iterator e = smartsPatterns.keySet().iterator();
	boolean is_true = false;
	String temp_id = "";
	while (e.hasNext()) {
	    temp_id = e.next().toString();
	    ISmartsPattern pattern = smartsPatterns.get(temp_id);
	    if (null == pattern) {
		throw new DecisionMethodException("ID '" + id + "' is missing in " + getClass().getName());
	    }
	    try {
		int matchCount = pattern.hasSMARTSPattern(moltotest);
		if ("2".equals(temp_id)) {
		    is_true = matchCount == 2;
		} else {
		    is_true = matchCount > 0;
		}
		logger.finer("SMARTS " + temp_id + "\t" + pattern.toString() + "\tmatches " + matchCount
			+ "times\tresult is " + is_true);
	    } catch (Exception x) {
		throw new DecisionMethodException(x);
	    }
	    if (pattern.isNegate()) {
		is_true = !is_true;
	    }
	    if (containsAllSubstructures && !is_true) {
		return false;
	    } else if (!containsAllSubstructures && is_true) {
		is_true = true;
		break;
	    }
	}
	return is_true;
    }

}
