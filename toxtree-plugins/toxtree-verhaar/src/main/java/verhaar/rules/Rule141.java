/*
Copyright Nina Jeliazkova (C) 2005-2011  
Contact: jeliazkova.nina@gmail.com

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/
package verhaar.rules;


import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.cramer.RuleIsOpenChain;
import toxTree.tree.rules.DefaultAlertCounter;
import toxTree.tree.rules.IAlertCounter;
import verhaar.query.FunctionalGroups;

/**
 * 
 * Acyclic compound NOT containing halogen at beta-positions from unsaturations (e.g. allylic / propargilyc halogens).
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com
 * <b>Modified</b> July 12, 2011
 */
public class Rule141 extends RuleIsOpenChain implements IAlertCounter {
	protected IAlertCounter alertsCounter;
	protected transient QueryAtomContainer x;
	protected static transient String[] halogens = {"Cl","Br","F","I"};
	/**
	 * 
	 */
	private static final long serialVersionUID = 6769690721536931384L;

	public Rule141() {
		super();
		id = "1.4.1";
		setTitle("Be acyclic compound NOT containing halogen at beta-positions from unsaturations (e.g. allylic / propargilyc halogens)");
		setExplanation("Be acyclic compound NOT containing halogen at beta-positions from unsaturations (e.g. allylic / propargilyc halogens)");
		examples[0] = "CCCCC=CC(Cl)CCC";
		examples[1] = "CCCCC=C(Cl)CCC";
		editable = false;
		x = FunctionalGroups.halogenAtBetaFromUnsaturation(halogens);
		alertsCounter = new DefaultAlertCounter();
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleOpenChain#verifyRule(org.openscience.cdk.interfaces.AtomContainer)
	 */
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		logger.finer(toString());
		if (super.verifyRule(mol)) {  //acyclic
			boolean ok = !FunctionalGroups.hasGroup(mol,x);
			if (ok) incrementCounter(mol);
			return ok;
		} else return false; 
	}
	public boolean isImplemented() {
		return true;
	}
	@Override
	public void incrementCounter(IAtomContainer mol) {
		alertsCounter.incrementCounter(mol);
		
	}
	@Override
	public String getImplementationDetails() {
		StringBuffer b = new StringBuffer();
		b.append(alertsCounter.getImplementationDetails());
		
		return b.toString();
	}

}
