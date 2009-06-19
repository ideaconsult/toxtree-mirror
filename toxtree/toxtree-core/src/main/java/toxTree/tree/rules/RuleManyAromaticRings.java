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

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionRuleEditor;
import toxTree.core.IRuleRange;
import toxTree.exceptions.DecisionMethodException;
import toxTree.query.MolFlags;
import toxTree.tree.AbstractRule;
import toxTree.ui.tree.rules.RuleRangeEditor;

/**
 * Verifies if there are many aromatic rings
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-8-19
 */
public class RuleManyAromaticRings extends AbstractRule implements IRuleRange {
	protected double minValue = 1;
	protected double maxValue = 10000;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 8793386207062962918L;
	/**
	 * 
	 */
	public RuleManyAromaticRings() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		logger.info(toString());
	    //should be set via MolAnalyser
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    double rings =  mf.getAromaticRings();
	    return (rings > minValue) && (rings < maxValue);
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractRule#isImplemented()
	 */
	@Override
	public boolean isImplemented() {
		return true;
	}
	public void setMinValue(double min) {
		this.minValue = min;
		setChanged();
		notifyObservers();
		
	}
	public void setMaxValue(double max) {
		this.maxValue = max;
		setChanged();
		notifyObservers();
		
	}
	public double getMinValue() {
		return minValue;
	}
	public double getMaxValue() {
		return maxValue;
	}
	@Override
	public IDecisionRuleEditor getEditor() {
		RuleRangeEditor e = new RuleRangeEditor(this);
		e.setRule(this);
		e.setSetPropertyEditable(false);
		return e;
	}
	public Object getProperty() {
		return "Number of aromatic rings";
	}
	public void setProperty(Object value) {
		// TODO Auto-generated method stub
		
	}	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RuleManyAromaticRings)
			return (getMaxValue()== ((RuleManyAromaticRings)obj).getMaxValue()) &&
				(getMinValue()== ((RuleManyAromaticRings)obj).getMinValue());
		else return false;
	}
}
