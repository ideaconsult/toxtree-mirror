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

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolFlags;
import toxTree.query.QueryAtomContainers;
import verhaar.rules.helper.RuleOnlyAllowedSubstructuresCounter;

/**
 * 
 * Aliphatic secondary or tertiary amines.
 * 
 * @author Nina Jeliazkova jeliazkova.nina@gmail.com <b>Modified</b> July 12,
 *         2011
 */
public class Rule161 extends RuleOnlyAllowedSubstructuresCounter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8043348345646950482L;

	public Rule161() throws Exception {
		super();
		id = "1.6.1";
		setTitle("Be aliphatic secondary or tertiary amines");
		examples[0] = "CCCCCCCCN"; // primary
		examples[1] = "CCCCN(CCC)"; // secondary
		editable = false;
	}

	@Override
	protected QueryAtomContainers initQuery() throws Exception {
		setQuery(super.initQuery());
		addSubstructure(FunctionalGroups.secondaryAmine(true));
		addSubstructure(FunctionalGroups.tertiaryAmine());
		return getQuery();
	}

	@Override
	public boolean verifyRule(IAtomContainer mol)
			throws DecisionMethodException {
		return verifyRule(mol, null);
	}

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {

		logger.finer(toString());
		MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
		if (mf == null)
			throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
		if (mf.isAliphatic()) {
			logger.finer("Aliphatic\t" + MSG_YES);
			return super.verifyRule(mol, selected);
		} else
			return false;

	}

	public boolean isImplemented() {
		return true;
	}
}
