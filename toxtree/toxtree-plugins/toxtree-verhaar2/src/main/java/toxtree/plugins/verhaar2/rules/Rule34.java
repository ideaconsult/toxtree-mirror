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
import toxTree.tree.cramer.Rule3MemberedHeterocycle;
import toxTree.tree.rules.DefaultAlertCounter;
import toxTree.tree.rules.IAlertCounter;



/**
 * 
 * Possess a three-membered heterocyclic ring. Compounds containing an epoxide or azaridine function.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule34 extends Rule3MemberedHeterocycle implements IAlertCounter  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9151362364966315658L;
	protected IAlertCounter alertsCounter;
	
	public Rule34() {
		super();
		id = "3.4";
		setTitle("Possess a three-membered heterocyclic ring. Compounds containing an epoxide or azaridine function");
		explanation = new StringBuffer();
		explanation.append("<UL>");
		explanation.append("<LI>");
		explanation.append("O1C([*])C1[*]");
		explanation.append("<LI>");
		explanation.append("N1C([*])C1[*]");
		explanation.append("</UL>");
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
