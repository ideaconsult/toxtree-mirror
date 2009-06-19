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

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.MolAnalyseException;
import toxTree.tree.rules.RuleAnySubstructure;
import toxTree.tree.rules.smarts.RuleSMARTSubstructure;
import toxTree.query.MolAnalyser;
import toxTree.query.MolFlags;


/**
 * C10-C20 Aliphatic Alcohols.<br>
 * SMARTS pattern  <ul>
 * <li>[AR0][OH]
 * </ul>
 * @author Nina Jeliazkova nina@acad.bg
 * @author Martin Martinov
 * <b>Modified</b> Dec 17, 2006
 */
public class RuleC10_C20AliphaticAlcohols extends RuleAnySubstructure{
	public final static transient String MSG_18H="Acyclic group";
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 0;

    /**
	 * Constructor
	 * 
	 */
	public RuleC10_C20AliphaticAlcohols() {

		super();		
		
		
		id = "52";
		title = "C10_C20AliphaticAlcohols";
		
		examples[0] = "OCCCCCCCCC";
		examples[1] = "OCCCCCCCCCC";	
		editable = false;
	}
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		logger.info(toString());
		RuleSMARTSubstructure rule = new RuleSMARTSubstructure();
		String C10_C20_Aliphatic_alcohols = "[C][OX2H]";
		rule.initSingleSMARTS(rule.getSmartsPatterns(),"1", C10_C20_Aliphatic_alcohols);
		int c = 0;
		
		for (int i=0; i < mol.getAtomCount();i++) {
			IAtom a = mol.getAtom(i);					
			if (a.getSymbol().equals("C")) {
				 c++;
			}
				
		}
		try {
			MolAnalyser.analyse(mol);
		} catch (MolAnalyseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
		if (mf ==null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
		
		if(rule.verifyRule(mol) && (c >= 10 && c<=20 && !mf.isAromatic()))
			return true;
		else
			return false;

	}
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractRule#isImplemented()
	 */
	public boolean isImplemented() {
		return true;
	}
}
