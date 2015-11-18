/*
Copyright Nina Jeliazkova (C) 2005-2006  
Contact: nina@acad.bg

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

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.QueryAtomContainers;
import toxTree.tree.rules.DefaultAlertCounter;
import toxTree.tree.rules.IAlertCounter;
import toxTree.tree.rules.RuleSubstructures;
import verhaar.query.FunctionalGroups;

/**
 * 
 * Uses {@link verhaar.query.QueryAssociationBond} ro verify if the query
 * compound has an ionic bond. It relies on the preprocessing by
 * {@link toxTree.query.FunctionalGroups#associateIonic} which is called from
 * {@link toxTree.query.MolAnalyser}.
 * 
 * @author Nina Jeliazkova <b>Modified</b> 2005-10-30
 */
public class RuleIonicGroups extends RuleSubstructures implements IAlertCounter {
	protected IAlertCounter alertsCounter;
	/**
	 * 
	 */
	private static final long serialVersionUID = -9123772835964334753L;

	public RuleIonicGroups() throws Exception {
		super();
		setTitle("Not contain ionic groups");
		id = "1.2";
		examples[0] = "O=S(=O)([O-])NC1CCCCC1.[Na+]";
		examples[1] = "CCCCCCCCCC";
		editable = false;
		alertsCounter = new DefaultAlertCounter();

	}

	@Override
	protected QueryAtomContainers initQuery() throws Exception {
		query = super.initQuery();
		addSubstructure(FunctionalGroups.ionicGroup());
		return query;
	}

	@Override
	public String getImplementationDetails() {
		StringBuffer b = new StringBuffer();
		b.append(alertsCounter.getImplementationDetails());

		return b.toString();
	}

	@Override
	public void incrementCounter(IAtomContainer mol) {
		alertsCounter.incrementCounter(mol);

	}

	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		return verifyRule(mol, null);
	}

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		boolean ok = !FunctionalGroups.hasGroup(mol, getSubstructure(0),
				selected);
		if (ok)
			incrementCounter(mol);
		return ok;
	}

	public boolean isImplemented() {
		return true;
	}

}
