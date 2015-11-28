/*
Copyright Ideaconsult Ltd. (C) 2005-2007 

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

 */
package toxTree.tree.rules;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;

import toxTree.core.IRuleSubstructures;
import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.query.QueryAtomContainers;
import toxTree.tree.AbstractRuleHilightHits;

/**
 * An abstract class to implement substructure rules
 * 
 * @author Nina Jeliazkova <b>Modified</b> 2005-8-14
 */
public abstract class RuleSubstructures extends AbstractRuleHilightHits
		implements IRuleSubstructures {
	public static transient String MSG_HASGROUP = "Has group\t";
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -2500957952948758410L;
	private transient QueryAtomContainers query = null;
	protected ArrayList<String> ids = null;

	/**
	 * 
	 */
	public RuleSubstructures() throws Exception {
		super();
		ids = new ArrayList<String>();
		ids.add(FunctionalGroups.C);
		ids.add(FunctionalGroups.CH);
		ids.add(FunctionalGroups.CH2);
		ids.add(FunctionalGroups.CH3);
		query = initQuery();
	}

	protected QueryAtomContainers initQuery() throws Exception {
		return new QueryAtomContainers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.tree.AbstractRule#isImplemented()
	 */
	@Override
	public boolean isImplemented() {
		return getSubstructuresCount() > 0;
	}

	/**
	 * The fragment will be added to the list of substructures to be searched
	 * for
	 * 
	 * @param fragment
	 *            /* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleSubstructures#addSubstructure(IAtomContainer)
	 */
	public void addSubstructure(IAtomContainer fragment) {
		query.add(fragment);
		ids.add(fragment.getID());
		setChanged();
		notifyObservers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.tree.rules.RuleSubstructures#clearSubstructures()
	 */
	public void clearSubstructures() {
		ids.clear();
		ids.add(FunctionalGroups.C);
		ids.add(FunctionalGroups.CH);
		ids.add(FunctionalGroups.CH2);
		ids.add(FunctionalGroups.CH3);
		setChanged();
		notifyObservers();
	}

	public int getSubstructuresCount() {
		return query.size();
	}

	public IAtomContainer getSubstructure(int index) {
		if (index < getQuery().getAtomContainerCount())
			return getQuery().get(index);
		else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.core.IRuleSubstructures#setSubstructure(int,
	 * org.openscience.cdk.interfaces.AtomContainer)
	 */
	public void setSubstructure(int index, IAtomContainer atomContainer) {
		getQuery().set(index, atomContainer);
		setChanged();
		notifyObservers();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.tree.AbstractRule#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		if (obj instanceof RuleSubstructures) {
			RuleSubstructures rule = (RuleSubstructures) obj;

			if (getQuery().size() != rule.getQuery().size())
				return false;
			else
				return ids.containsAll(rule.ids);
		} else
			return false;
	}

	public IAtomContainer removeSubstructure(int index) {
		IAtomContainer a = getSubstructure(index);
		if (a != null) {
			ids.remove(a.getID());
			getQuery().remove(a);
		}
		setChanged();
		notifyObservers();
		return a;
	}

	public List getSubstructures() {
		return query;
	}

	/*
	 * @Override public IDecisionRuleEditor getEditor() { return new
	 * SubstructureRulePanel(this); }
	 */

	public QueryAtomContainers getQuery() {
		try {
			if (query == null)
				query = initQuery();
		} catch (Exception x) {
		}
		return query;
	}

	public void setQuery(QueryAtomContainers query) {
		this.query = query;
	}

	public ArrayList getIds() {
		return ids;
	}

	public void setIds(ArrayList ids) {
		this.ids = ids;
	}

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		logger.fine(toString());
		try {
			MolAnalyser.analyse(mol);
			boolean ok = verifyRule(mol);
			if (selected != null) {
				for (IAtom atom : mol.atoms())
					if (!"H".equals(atom.getSymbol())
							&& atom.getProperty(FunctionalGroups.ALLOCATED) != null)
						selected.addAtom(atom);

				for (IBond bond : mol.bonds())
					if ((bond.getProperty(FunctionalGroups.ALLOCATED) != null)
							&& !"H".equals(bond.getAtom(0).getSymbol())
							&& !"H".equals(bond.getAtom(1).getSymbol()))
						selected.addBond(bond);
			}

			return ok;
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
	}
}
