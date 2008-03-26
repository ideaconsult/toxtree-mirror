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

import org.openscience.cdk.interfaces.IAtomContainer;
import toxTree.core.IDecisionRuleEditor;
import toxTree.core.IRuleSubstructures;
import toxTree.query.FunctionalGroups;
import toxTree.query.QueryAtomContainers;
import toxTree.tree.AbstractRule;
import toxTree.ui.tree.rules.SubstructureRulePanel;

/**
 * An abstract class to implement substructure rules
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-8-14
 */
public abstract class RuleSubstructures extends AbstractRule implements IRuleSubstructures {
	public static transient String MSG_HASGROUP = "Has group\t";
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -2500957952948758410L;
	protected QueryAtomContainers query = null;
	protected ArrayList ids = null;	
	/**
	 * 
	 */
	public RuleSubstructures() {
		super();
		query = new QueryAtomContainers();
		ids = new ArrayList();
	    ids.add(FunctionalGroups.C);	    
	    ids.add(FunctionalGroups.CH);
	    ids.add(FunctionalGroups.CH2);
	    ids.add(FunctionalGroups.CH3);		
	}

	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractRule#isImplemented()
	 */
	@Override
	public boolean isImplemented() {
		return getSubstructuresCount()>0;
	}
	/**
	 * The fragment will be added to the list of substructures to be searched for
	 * @param fragment
	/* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleSubstructures#addSubstructure(IAtomContainer)
	 */
	public void addSubstructure(IAtomContainer fragment) {
		query.add(fragment);
		ids.add(fragment.getID());
		setChanged();
		notifyObservers();
	}


	/* (non-Javadoc)
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
        if (index < query.getAtomContainerCount())
            return (IAtomContainer) query.get(index);
        else return null;
	}
	/* (non-Javadoc)
	 * @see toxTree.core.IRuleSubstructures#setSubstructure(int, org.openscience.cdk.interfaces.AtomContainer)
	 */
	public void setSubstructure(int index, IAtomContainer atomContainer) {
		query.set(index,atomContainer);
		setChanged();
		notifyObservers();

	}
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractRule#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) return false;
		if (obj instanceof RuleSubstructures) {
			RuleSubstructures rule = (RuleSubstructures) obj;
			if (query.size() != rule.query.size()) return false;
			else return ids.containsAll(rule.ids);
		} else return false;
	}
	
	public IAtomContainer removeSubstructure(int index) {
		IAtomContainer a = getSubstructure(index);
		if (a != null) {
			ids.remove(a.getID());
			query.remove(a);
		}
		setChanged();
		notifyObservers();
		return a;
	}
	public List getSubstructures() {
		return query;
	}
	@Override
	public IDecisionRuleEditor getEditor() {
		return new SubstructureRulePanel(this);
	}

	public QueryAtomContainers getQuery() {
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
}
