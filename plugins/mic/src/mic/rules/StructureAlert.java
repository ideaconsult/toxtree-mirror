/*
Copyright Ideaconsult Ltd. (C) 2005-2007  

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

package mic.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.smarts.RuleSMARTSubstructure;

public class StructureAlert extends RuleSMARTSubstructure implements IAlertCounter {
	IAlertCounter alertsCounter;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8723199576199214305L;

	public StructureAlert() {
		super();
		alertsCounter = new DefaultAlertCounter();
	}
    public void incrementCounter(IAtomContainer mol) {
        alertsCounter.incrementCounter(mol);
    }
	@Override
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		if (super.verifyRule(mol)) {
			incrementCounter(mol);
			return true;	
		} else return false;
	}
	@Override
	public String getImplementationDetails() {
		StringBuffer b = new StringBuffer();
		b.append(alertsCounter.getImplementationDetails());
		b.append("Uses Joelib SMARTS<br>");
		b.append(super.getImplementationDetails());
		
		return b.toString();
	}
	
}


