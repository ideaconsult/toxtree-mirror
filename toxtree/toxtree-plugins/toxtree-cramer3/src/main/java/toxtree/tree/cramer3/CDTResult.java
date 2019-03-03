/*
Copyright Ideaconsult Ltd. (C) 2005-2013 

Contact: www.ideaconsult.net

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

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionRule;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.ProgressStatus;
import toxTree.tree.TreeResult;

/**
 * A descendant ot {@link TreeResult}, to be used by {@link toxTree.tree.cramer.CramerRules}.
 * 
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-9-3
 */
public class CDTResult extends TreeResult {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -7883635712321700678L;

	/**
	 * 
	 */
	public CDTResult() {
		super();
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.TreeResult#addRuleResult(int, boolean)
	 */
	@Override
	public void addRuleResult(IDecisionRule rule, boolean value, IAtomContainer molecule)
			throws DecisionResultException {
		super.addRuleResult(rule, value,molecule);
		//if (rule.getNum() > 33)			setSilent(true);
	}
    @Override
	public void assignResult(IAtomContainer mol) throws DecisionResultException {
    	if (mol ==null) return;
    	if (getCategory()!=null)
        mol.setProperty(
        		getResultPropertyNames()[0],
                getCategory().toString());
        mol.setProperty(
                getClass().getName(),
                explain(false).toString());
        firePropertyChangeEvent(ProgressStatus._pRuleResult, null, status);
    }
    @Override
    public String[] getResultPropertyNames() {
    	return new String[] {getDecisionMethod().getTitle()};
    }
    
}
