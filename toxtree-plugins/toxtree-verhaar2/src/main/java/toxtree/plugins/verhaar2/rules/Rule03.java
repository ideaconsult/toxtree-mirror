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

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.DefaultAlertCounter;
import toxTree.tree.rules.IAlertCounter;
import toxTree.tree.rules.RuleMolecularMassRange;

/**
 * Have a molecular mass (MW) not more than 600 Daltons.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule03 extends RuleMolecularMassRange  implements IAlertCounter  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2799745152926450353L;
	protected IAlertCounter alertsCounter;
	public Rule03() {
		super();
		id = "0.3";
		setTitle("Have a molecular mass (MW) not more than 600 Daltons");
		explanation.append(
				"Generally speaking, compounds having a MW of over 600 Daltons are too bulky to be taken up across membranes."+
				"Because of the fact that a high proportion of all chemicals with an MW higher than 600 Daltons are compounds acting by a specific"+
				"mechanism anyway, we define an ad hoc limit of applicability of narcosis type QSAR equations at MW = 600 Daltons"
				);
		examples[1] = "Oc3ccc4CC1N(C)CCC2(CCCCC12)c4(c3)";
		examples[0] = "NCCCCC(NC(=O)C1CCCN1(C(=O)C(CCC(O)=O)NC(=O)C(CCC(N)=O)NC(=O)C(CO)NC(=O)CNC(=O)C(C)N))C(=O)NC(CCC(O)=O)C(=O)NC(CCC(N)=O)C(=O)NC(CO)C(=O)NC(CO)C(=O)NC(Cc2ccc(O)cc2)C(=O)NC(CO)C(=O)NC(CC3CNCN3)C(=O)NC(CO)C(O)=O";
		setMinValue(0);
		setMaxValue(600);
		editable = false;
		alertsCounter = new DefaultAlertCounter();
	}
	@Override
	public String getImplementationDetails() {
		StringBuffer b = new StringBuffer();
		b.append(alertsCounter.getImplementationDetails());
		
		return b.toString();
	}

	@Override
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		if (super.verifyRule(mol)) {
			incrementCounter(mol);
			return true;	
		} else return false;
	}
	
	@Override
	public void incrementCounter(IAtomContainer mol) {
		alertsCounter.incrementCounter(mol);
		
	}

}
