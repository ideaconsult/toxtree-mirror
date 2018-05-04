/*
Copyright Ideaconsult Ltd. (C) 2015 

Contact: jeliazkova.nina@gmail.com

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
package toxtree.tree.cramer3;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Observable;

import org.openscience.cdk.qsar.DescriptorSpecification;

import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNodesList;
import toxTree.tree.UserDefinedTree;

/**
 * Assigns categories:
 * <ul>
 * <li>{@link toxTree.tree.cramer3.CramerClass1}
 * <li>{@link toxTree.tree.cramer3.CramerClass2}
 * <li>{@link toxTree.tree.cramer3.CramerClass2}
 * </ul>
 * 
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2015-3-10
 */
public class RevisedCramerDecisionTree extends UserDefinedTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3817869410935325980L;

	protected boolean residuesIDVisible;

	/**
	 * 
	 */
	public RevisedCramerDecisionTree() throws DecisionMethodException {
		this(new TreeSpecification());
	}

	public RevisedCramerDecisionTree(TreeSpecification spec) throws DecisionMethodException {
		super(new CategoriesList(spec.getCategories()), null);
		rules = new DecisionNodesList(categories, spec.getRules(), spec.getTransitions());
		if (rules instanceof Observable)
			((Observable) rules).addObserver(this);
		setTitle("Revised Cramer Decision Tree");

		setChanged();
		notifyObservers();

		setPriority(1);
	}

	public DescriptorSpecification getSpecification() {
		return new DescriptorSpecification("http://toxtree.sourceforge.net/cramer3.html", getTitle(), this.getClass()
				.getName(), "Toxtree plugin");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.core.IDecisionMethod#addPropertyChangeListener(java.beans.
	 * PropertyChangeListener)
	 */
	@Override
	public void addPropertyChangeListener(PropertyChangeListener l) {
		if (changes == null)
			changes = new PropertyChangeSupport(this);
		changes.addPropertyChangeListener(l);
		for (int i = 0; i < rules.size(); i++)
			if (rules.getRule(i) != null)
				rules.getRule(i).addPropertyChangeListener(l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * toxTree.core.IDecisionMethod#removePropertyChangeListener(java.beans.
	 * PropertyChangeListener)
	 */
	@Override
	public void removePropertyChangeListener(PropertyChangeListener l) {
		if (changes == null) {
			changes.removePropertyChangeListener(l);
			for (int i = 0; i < rules.size(); i++)
				if (rules.getRule(i) != null)
					rules.getRule(i).removePropertyChangeListener(l);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.core.IDecisionMethod#getName()
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.core.IDecisionMethod#setName(java.lang.String)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see toxTree.tree.AbstractTree#createDecisionResult()
	 */
	@Override
	public IDecisionResult createDecisionResult() {
		IDecisionResult result = new CDTResult();
		result.setDecisionMethod(this);
		return result;

	}

	public boolean isResiduesIDVisible() {
		return residuesIDVisible;
	}

	/*
	 * public void setResiduesIDVisible(boolean residuesIDVisible) {
	 * this.residuesIDVisible = residuesIDVisible; for (int i=0;i< rules.size();
	 * i++) { rules.getRule(i).hideResiduesID(!residuesIDVisible); } }
	 */
	@Override
	public void setEditable(boolean value) {
		editable = value;
		for (int i = 0; i < rules.size(); i++)
			rules.getRule(i).setEditable(value);
	}

}
