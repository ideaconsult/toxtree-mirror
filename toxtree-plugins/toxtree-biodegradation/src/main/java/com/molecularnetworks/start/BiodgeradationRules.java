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

package com.molecularnetworks.start;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Observable;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorSpecification;

import toxTree.core.IDecisionInteractive;
import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNodesList;
import toxTree.tree.UserDefinedTree;

/**
 * Wrapper class that knows the biodegradation rules that are available in the
 * toxBiodegradation plugin.
 * 
 * @version $Id: BiodgeradationRules.java 936 2008-12-04 17:43:31Z joerg $
 * @author <a href="mailto:info@molecular-networks.com">Molecular Networks</a>
 * @author $Author: joerg $
 */
public class BiodgeradationRules extends UserDefinedTree implements IDecisionInteractive {
    /**
     * 
     */
    private static final long serialVersionUID = 3790027027577230994L;
    protected boolean residuesIDVisible;
    boolean interactive = false;

    public final static transient String[] c_rules = { "com.molecularnetworks.start.rules.RuleTerminalTertButyl",
	    "com.molecularnetworks.start.rules.RuleEpoxide",
	    "com.molecularnetworks.start.rules.RuleAliphaticFusedRingsNonBranched",
	    "com.molecularnetworks.start.rules.RuleTerminalIsopropylNonCyclic",
	    "com.molecularnetworks.start.rules.RuleAliphaticCyclicNoBranches",
	    "com.molecularnetworks.start.rules.RuleHalogenSubstitutedBranched",
	    "com.molecularnetworks.start.rules.RuleTwoHalogensOnUnbranchedNonCyclic",
	    "com.molecularnetworks.start.rules.RuleMoreThanTwoHydroxyOnAromaticRing",
	    "com.molecularnetworks.start.rules.RuleTwoOrMoreRings",
	    "com.molecularnetworks.start.rules.RuleTwoTerminalDiaminoGroupsOnNonCyclic",
	    "com.molecularnetworks.start.rules.RuleTwoTerminalDoubleBondsOnUnbranched",
	    "com.molecularnetworks.start.rules.RuleCyanoGroupOnMoreThanEightAtomsChain",
	    "com.molecularnetworks.start.rules.RuleNNitroso", "com.molecularnetworks.start.rules.RuleAromaticHalogen",
	    "com.molecularnetworks.start.rules.RuleAromaticNGroups",
	    "com.molecularnetworks.start.rules.RuleAromaticSulphonicAcid",
	    "com.molecularnetworks.start.rules.RuleAliphaticEther",
	    "com.molecularnetworks.start.rules.RuleTertiaryAmine", "com.molecularnetworks.start.rules.RuleAzoGroup",
	    "com.molecularnetworks.start.rules.RuleTrifluoromethyl",
	    "com.molecularnetworks.start.rules.RuleTriazineRing", "com.molecularnetworks.start.rules.RulePyridineRing",
	    "com.molecularnetworks.start.rules.RuleKetone",
	    "com.molecularnetworks.start.rules.RuleOneHalogenOnUnbranched",
	    "com.molecularnetworks.start.rules.RuleNitrile", "com.molecularnetworks.start.rules.RuleAldehyde",
	    "com.molecularnetworks.start.rules.RuleAlcohols", "com.molecularnetworks.start.rules.RuleEsters",
	    "com.molecularnetworks.start.rules.RulePhosphateEsters",
	    "com.molecularnetworks.start.rules.RuleAminoAcids",
	    "com.molecularnetworks.start.rules.RuleAlyphaticSulphonicAcids",
	    "com.molecularnetworks.start.rules.RuleFormaldehyde" };

    private final static transient int c_transitions[][] = {
	    // {if no go to, if yes go to, assign if no, assign if yes}
	    { 2, 0, 0, 2 }, { 3, 0, 0, 2 }, { 4, 0, 0, 2 }, { 5, 0, 0, 2 }, { 6, 0, 0, 2 }, { 7, 0, 0, 2 },
	    { 8, 0, 0, 2 }, { 9, 0, 0, 2 }, { 10, 0, 0, 2 }, { 11, 0, 0, 2 }, { 12, 0, 0, 2 }, { 13, 0, 0, 2 },
	    { 14, 0, 0, 2 }, { 15, 0, 0, 2 }, { 16, 0, 0, 2 }, { 17, 0, 0, 2 }, { 18, 0, 0, 2 }, { 19, 0, 0, 2 },
	    { 20, 0, 0, 2 }, { 21, 0, 0, 2 }, { 22, 0, 0, 2 }, { 23, 0, 0, 2 }, { 24, 0, 0, 2 }, { 25, 0, 0, 1 },
	    { 26, 0, 0, 1 }, { 27, 0, 0, 1 }, { 28, 0, 0, 1 }, { 29, 0, 0, 1 }, { 30, 0, 0, 1 }, { 31, 0, 0, 1 },
	    { 32, 0, 0, 1 }, { 0, 0, 3, 1 } };

    private final static transient String c_categories[] = {
	    "com.molecularnetworks.start.categories.CategoryBiodegradable",
	    "com.molecularnetworks.start.categories.CategoryPersistent",
	    "com.molecularnetworks.start.categories.CategoryUnknown" };

    public BiodgeradationRules() throws DecisionMethodException {
	super(new CategoriesList(c_categories), null);
	rules = new DecisionNodesList(categories, c_rules, c_transitions);
	if (rules instanceof Observable)
	    ((Observable) rules).addObserver(this);

	setChanged();
	notifyObservers();
	setTitle("START Biodegradability");

	setInteractive(false);
	setPriority(12);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener l) {
	if (null == changes)
	    changes = new PropertyChangeSupport(this);
	changes.addPropertyChangeListener(l);
	for (int i = 0; i < rules.size(); i++)
	    if (rules.getRule(i) != null)
		rules.getRule(i).addPropertyChangeListener(l);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener l) {
	if (null != changes) {
	    changes.removePropertyChangeListener(l);
	    for (int i = 0; i < rules.size(); i++)
		if (rules.getRule(i) != null)
		    rules.getRule(i).removePropertyChangeListener(l);
	}
    }

    @Override
    public String toString() {
	return getName();
    }

    public String getName() {
	return name;
    }

    public void setName(String value) {
	name = value;
    }

    @Override
    public StringBuffer explainRules(IDecisionResult result, boolean verbose) throws DecisionMethodException {
	try {
	    StringBuffer b = result.explain(verbose);
	    return b;
	} catch (DecisionResultException x) {
	    throw new DecisionMethodException(x);
	}
    }

    public boolean isResiduesIDVisible() {
	return residuesIDVisible;
    }

    public void setResiduesIDVisible(boolean residuesIDVisible) {
	this.residuesIDVisible = residuesIDVisible;
	for (int i = 0; i < rules.size(); i++) {
	    rules.getRule(i).hideResiduesID(!residuesIDVisible);
	}
    }

    @Override
    public void setEditable(boolean value) {
	editable = value;
	for (int i = 0; i < rules.size(); i++)
	    rules.getRule(i).setEditable(value);
    }

    @Override
    public void setParameters(IAtomContainer mol) {
	if (interactive) {
	    JComponent c = optionsPanel(mol);
	    if (null != c)
		JOptionPane.showMessageDialog(null, c, "Enter properties", JOptionPane.PLAIN_MESSAGE);
	}
    }

    @Override
    public boolean getInteractive() {
	return interactive;
    }

    @Override
    public void setInteractive(boolean value) {
	interactive = value;
    }

    public DescriptorSpecification getSpecification() {
	return new DescriptorSpecification("http://toxtree.sourceforge.net/start.html", getTitle(), this.getClass()
		.getName(), "Toxtree plugin");
    }

}
