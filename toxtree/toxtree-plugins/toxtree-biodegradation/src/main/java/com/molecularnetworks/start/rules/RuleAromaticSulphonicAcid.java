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
 * Biodegradation rule for aromatic sulphonic acids.
 * 
 * @version $Id: RuleAromaticSulphonicAcid.java 936 2008-12-04 17:43:31Z joerg $
 * @author <a href="mailto:info@molecular-networks.com">Molecular Networks</a>
 * @author $Author: joerg $
 */
public class RuleAromaticSulphonicAcid extends RuleSMARTSubstructure {

    /**
     * 
     */
    private static final long serialVersionUID = -4951096883397535662L;

    /**
     * Default constructor
     */
    public RuleAromaticSulphonicAcid() {
	super();
	try {
	    super.addSubstructure("1",
		    "[$([#16X4](=[OX1])(=[OX1])([a])[OX2H,OX1H0-]),$([#16X4+2]([OX1-])([OX1-])([a])[OX2H,OX1H0-])]",
		    false);
	    super.setContainsAllSubstructures(false);
	    super.setExplanation("Aromatic sulphonic acids " + "are associated with low biodegradability.");
	    id = "16";
	    title = "Aromatic sulphonic acid";
	    examples[0] = "C1CCCC1";
	    examples[1] = "c1ccccc1S(=O)(=O)O";
	    editable = false;
	} catch (SMARTSException x) {
	    logger.log(Level.SEVERE, x.getMessage(), x);
	}
    }

}
