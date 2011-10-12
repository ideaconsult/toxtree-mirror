/*
Copyright Ideaconsult Ltd. (C) 2005-2011  

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package toxtree.plugins.moa.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;

public class RuleAlertsNarcosis1_1_1 extends RuleSMARTSSubstructureAmbit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7143766029103047956L;

	private static final String name = "Aldehyde"; 
	protected Object[][] smarts = {
			{name,
				"[CH1](=O)"
				,Boolean.TRUE},
	};		
	
	public RuleAlertsNarcosis1_1_1() {
		super();
		id = "1.0";
		setTitle(name);
		setContainsAllSubstructures(false);
		for (Object[] smart: smarts) try { 
			addSubstructure(smart[0].toString(),smart[1].toString(),!(Boolean) smart[2]);
		} catch (Exception x) {}
		
		examples[0] = "CCC";
		examples[1] = "CCC=O";
		editable = false;
	}
	@Override
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		return verifyRule(mol,null);
	}

	public boolean isImplemented() {
		return true;
	}	

}


