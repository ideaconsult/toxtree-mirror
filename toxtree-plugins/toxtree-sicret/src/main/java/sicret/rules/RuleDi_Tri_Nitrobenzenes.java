/*
Copyright Ideaconsult Ltd.(C) 2006  
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
package sicret.rules;


import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.QueryAtomContainers;
import toxTree.tree.rules.RuleAnySubstructure;

/**
 * Di & Tri-Nitrobenzenes.<br>
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleDi_Tri_Nitrobenzenes extends RuleAnySubstructure {
	/**
	 * 
	 */
	private static final long serialVersionUID = 727457812604632292L;
	public final static transient String MSG_18H="Acyclic group";

    /**
	 * Constructor
	 * 
	 */
	public RuleDi_Tri_Nitrobenzenes()  throws Exception {

		super();		
		
		id = "57";
		title = "Di_Tri_Nitrobenzenes";
		examples[0] = "O=N(=O)C1C(CC(C1)N(=O)=O)N(=O)=O";
		examples[1] = "O=N(=O)C1CC(CC(C1)N(=O)=O)N(=O)=O";	
		editable = false;
	}
	@Override
	protected QueryAtomContainers initQuery() throws Exception {
		query =  super.initQuery();
		addSubstructure(FunctionalGroups.createAtomContainer("O=[N+]([O-])c1ccccc1[N+](=O)[O-]",false));
		addSubstructure(FunctionalGroups.createAtomContainer("O=[N+]([O-])c1ccc(cc1)[N+](=O)[O-]",false));
		addSubstructure(FunctionalGroups.createAtomContainer("O=N(=O)C1CCCC(C1)N(=O)=O",false));
		addSubstructure(FunctionalGroups.createAtomContainer("O=N(=O)C1CCC(CC1)N(=O)=O",false));
		addSubstructure(FunctionalGroups.createAtomContainer("O=N(=O)C1CCCCC1(N(=O)=O)",false));
		addSubstructure(FunctionalGroups.createAtomContainer("O=[N+]([O-])c1cc(cc(c1)[N+](=O)[O-])[N+](=O)[O-]",false));
		addSubstructure(FunctionalGroups.createAtomContainer("O=N(=O)C1CC(CC(C1)N(=O)=O)N(=O)=O",false));
		return query;
	}
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		logger.finer(toString());		
		return super.verifyRule(mol);
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractRule#isImplemented()
	 */
	public boolean isImplemented() {
		return true;
	}
}
