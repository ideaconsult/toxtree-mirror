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

package toxtree.plugins.verhaar2.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionResultException;
import toxTree.tree.ProgressStatus;
import toxTree.tree.TreeResult;

public class Verhaar2TreeResult extends TreeResult {

    /**
     * 
     */
    private static final long serialVersionUID = 2222797084633419611L;

    public Verhaar2TreeResult() {
        super();
    }
    @Override
    public void assignResult(IAtomContainer mol) throws DecisionResultException {
    	if (mol ==null) return;
    	if (getCategory()!=null)
        mol.setProperty(
        		getResultPropertyName(),
                getCategory().toString());
        
        mol.setProperty(
                getClass().getName(),
                explain(false).toString());
        firePropertyChangeEvent(ProgressStatus._pRuleResult, null, status);        
    }   
    
    public String[] getResultPropertyName() {
    	if (getDecisionMethod() == null) return new String[] {"Verhaar scheme"};
    	else return new String[] {getDecisionMethod().getClass().getName()};
    }
}
