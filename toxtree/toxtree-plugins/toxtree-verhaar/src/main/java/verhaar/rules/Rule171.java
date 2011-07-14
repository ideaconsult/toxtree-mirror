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
package verhaar.rules;


import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.smarts.AbstractRuleSmartSubstructure;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;

/**
 * 
 * Halogenated compounds that comply with rule {@link Rule15} but not alpha-, beta- halogen substituted compounds.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class Rule171 extends RuleSMARTSSubstructureAmbit {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5540666661281282664L;
	protected AbstractRuleSmartSubstructure[] rules15 = new AbstractRuleSmartSubstructure[] {
		new Rule151(),new Rule152(),new Rule153(),new Rule154()
	};
	protected Object[][] smarts = {
	
			{"Halogen","[Cl,Br,I,F][!$(C=*);!$(CC=*)]",Boolean.TRUE}
	};		
	
	public Rule171()  {
		super();
		id = "1.7.1";
		setTitle("Are halogenated compounds that comply with rule Q.1.5 but not alpha-, beta- halogen substituted compounds");
		explanation = new StringBuffer();
		explanation.append("N.B.: It may be possible that some compounds, that are known to be more toxic than baseline ");
		explanation.append("toxicity, do classify as class 1 compounds. If this be the case, DO NOT treat these compounds as");
		explanation.append("baseline toxicants. An example of this would be lindane, or 2-hexachlorocyclohexane, which is much more toxic than the other");
		explanation.append(" hexaehlorocyclohexanes");
		examples[1] = "O(C)CC(C)Cl";
		examples[0] = "N(C)CC(C)Cl";
		editable = false;
		
		setContainsAllSubstructures(true);
		for (Object[] smart: smarts) try { 
			addSubstructure(smart[0].toString(),smart[1].toString(),!(Boolean) smart[2]);
		} catch (Exception x) {}		

	}

	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected) throws DecisionMethodException {

		if (super.verifyRule(mol,selected)) 
			for (AbstractRuleSmartSubstructure rule: rules15) 
				if (rule.verifyRule(mol,selected)) return true;
		return false;
	}
	
}
