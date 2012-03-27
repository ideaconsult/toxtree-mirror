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

*//**
 * Created on 2005-5-2

 * @author Nina Jeliazkova nina@acad.bg
 *
 * Project : toxtree
 * Package : toxTree.tree.rules
 * Filename: RuleHasOtherThanC_H_O_N_S2.java
 */
package toxTree.tree.cramer;

import java.util.List;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.RuleElements;

/**
 * Rule 3 of the Cramer scheme (see {@link toxTree.tree.cramer.CramerRules})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class RuleHasOtherThanC_H_O_N_S2 extends RuleElements {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 280559908671875462L;
    /**
	 * Constructor
	 * 
	 */
	public RuleHasOtherThanC_H_O_N_S2() {
		super();
		addElement("C");
		addElement("O");		
		addElement("H");
		addElement("N");
		addElement("S");
		setComparisonMode(modeOnlySpecifiedElements);
		title = "Contains elements other than C,H,O,N,divalent S";
		explanation = new StringBuffer();
		explanation.append("Does the structure contain elements other than C, H, O, N or divalent S?");
		id = "3";
		examples[0] = "CC(=S)N";
		examples[1] = "C(Cl)(Cl)Cl";
		editable = false;
	}
	/**
	 * Verifies the rule.
	 * @see toxTree.core.IDecisionRule#verifyRule(IAtomContainer)
	 */
	@Override
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
			return verifyRule(mol,null);
	}
	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		try {
			int c = 0;
		
			for (IAtom atom : mol.atoms()) {
				if (atom.getSymbol().equals("H")) c++;
				if (contains(atom.getSymbol())) {
					if ("S".equals(atom.getSymbol())) {
						if (atom.getValency()==2) c++;
						else selected.addAtom(atom);
					} else c++;
				} else 
					if ((selected!=null) && !atom.getSymbol().equals("H")) selected.addAtom(atom);
			}	
			return c != mol.getAtomCount();
		} catch (Exception x) { //if atom typing fails
				return oldVerifyRule(mol, selected);
		}
	}
	public boolean oldVerifyRule(IAtomContainer  mol,IAtomContainer selected) throws DecisionMethodException {
		logger.info(toString());
		int c = 0;
		double order;
		for (int i=0; i < mol.getAtomCount();i++) {
			IAtom a = mol.getAtom(i);
			if (a.getSymbol().equals("H")) c++;
			else {
				order = 0;
				if (contains(a.getSymbol())) {
					if (a.getSymbol().equals("S")) {
						List bonds = mol.getConnectedBondsList(a);
						
                        for (int b=0;b<bonds.size();b++) {
                            IBond.Order o = ((IBond) bonds.get(b)).getOrder();
                            if (((IBond) bonds.get(b)).getFlag(CDKConstants.ISAROMATIC)) order += 1.5;
                            else if (o.equals(IBond.Order.SINGLE)) order += 1.0;
                            else if (o.equals(IBond.Order.DOUBLE)) order += 2.0;
                            else if (o.equals(IBond.Order.TRIPLE)) order += 3.0;
                            else if (o.equals(IBond.Order.QUADRUPLE)) order += 4.0;
                        }    
    					/*
                    	https://sourceforge.net/tracker/?func=detail&aid=3020065&group_id=20024&atid=120024
                        order = order + a.getHydrogenCount();
                    	*/
    					order = order + a.getImplicitHydrogenCount();
						if ((order-2) < 0.1)
							c++;
						else {
							logger.info("Found S valency ",Double.toString(order));
							if ((selected!=null) && !a.getSymbol().equals("H")) selected.addAtom(a);
						}
					} else {
						c++;
						
					} 
				} else 
					if ((selected!=null) && !a.getSymbol().equals("H")) selected.addAtom(a);
			}
		}
		return c != mol.getAtomCount();

	}
	/* (non-Javadoc)
     * @see toxTree.tree.AbstractRule#isImplemented()
     */
    @Override
	public boolean isImplemented() {
        return true;
    }
    /* (non-Javadoc)
	 * @see toxTree.tree.rules.RuleElements#getName()
	 */
	@Override
	public String getTitle() {
		return title;
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractRule#setName(java.lang.String)
	 */
	@Override
	public void setTitle(String name) {
		this.title = name;
	}

}
