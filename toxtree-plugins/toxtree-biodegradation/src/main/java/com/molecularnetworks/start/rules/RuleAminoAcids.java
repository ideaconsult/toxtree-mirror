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

import java.util.logging.Level;

import toxTree.tree.rules.smarts.RuleSMARTSubstructure;
import ambit2.smarts.query.SMARTSException;

/**
 * Biodegradation rule for amino acids.
 * 
 * @version $Id: RuleAminoAcids.java 936 2008-12-04 17:43:31Z joerg $
 * @author <a href="mailto:info@molecular-networks.com">Molecular Networks</a>
 * @author $Author: joerg $
 */
public class RuleAminoAcids extends RuleSMARTSubstructure {

    /**
     * 
     */
    private static final long serialVersionUID = 67259564133839114L;

    /**
     * Default constructor
     */
    public RuleAminoAcids() {
	super();
	try {
	    super.addSubstructure("1", "[NX3,NX4+][CX4H]([*])[CX3](=[OX1])[O,N]", false);
	    super.addSubstructure("2", "[$([$([NX3H2,NX4H3+]),$([NX3H](C)(C))][CX4H2][CX3](=[OX1])[OX2H,OX1-,N])]",
		    false);
	    super.setContainsAllSubstructures(false);
	    super.setExplanation("Checks for amino acids. " + "Amino acids are associated with easy biodegradability.");
	    id = "30";
	    title = "Amino acids";
	    examples[0] = "CC1OC1CC";
	    examples[1] = "C[C@H](N)C(O)=O";
	    editable = false;
	} catch (SMARTSException x) {
	    logger.log(Level.SEVERE, x.getMessage(), x);
	}
    }

}
