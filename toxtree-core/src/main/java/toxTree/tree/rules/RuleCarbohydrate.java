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

*/
package toxTree.tree.rules;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolAnalyser;
import toxTree.query.MolFlags;
import ambit2.core.data.MoleculeTools;

/**
 * Verifies if the molecule is a carbohydrate
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-18
 */
public class RuleCarbohydrate extends RuleSubstructures {
	public static transient String MSG_COMMONCARBOHYDRATE = "Common carbohydrate\t";
	public static transient String MSG_NOTACOMMONCARBOHYDRATE = "\tcan't be common carbohydrate\t";
	public static transient String MSG_MANYGROUPSOFAKIND = "\tMore than one\t";
	
	protected int index_alcohol = 0;
	protected int index_ether = 1;
	protected int index_aldehyde = 2;
	protected int index_ketone = 3;
	
	protected ArrayList idsCyclic;
	protected ArrayList idsAcyclic;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3126510203326706185L;

	public RuleCarbohydrate() {
		super();
		
		idsCyclic = new ArrayList();
		idsAcyclic = new ArrayList();
		
		idsCyclic.add(FunctionalGroups.C);	    
		idsCyclic.add(FunctionalGroups.CH);
		idsCyclic.add(FunctionalGroups.CH2);
		idsCyclic.add(FunctionalGroups.CH3);

		idsAcyclic.add(FunctionalGroups.C);	    
		idsAcyclic.add(FunctionalGroups.CH);
		idsAcyclic.add(FunctionalGroups.CH2);
		idsAcyclic.add(FunctionalGroups.CH3);
		
		addSubstructure(FunctionalGroups.alcohol(false));
		addSubstructure(FunctionalGroups.ether());
		addSubstructure(FunctionalGroups.aldehyde());
		addSubstructure(FunctionalGroups.ketone());
		
		idsCyclic.add(getSubstructure(index_alcohol).getID());
		idsCyclic.add(getSubstructure(index_ether).getID());
		
		idsAcyclic.add(getSubstructure(index_alcohol).getID());
		idsAcyclic.add(getSubstructure(index_aldehyde).getID());
		idsAcyclic.add(getSubstructure(index_ketone).getID());
		
		examples[1]="OC[C@H]1OC(O)[C@H](O)[C@@H](O)[C@@H]1O";
		examples[0]="C(=O)C(=O)C(=O)C(=O)C(=O)C(=O)";
	}
	@Override
	public boolean verifyRule(IAtomContainer mol, IAtomContainer selected)
			throws DecisionMethodException {
		logger.info(toString());
		try {
			MolAnalyser.analyse(mol);
			boolean ok = verifyRule(mol);
		
			if (ok && (selected != null)) {
				for (IAtom atom: mol.atoms())
					if (!"H".equals(atom.getSymbol()))
						selected.addAtom(atom);
				
				for (IBond bond:mol.bonds()) 
					if (
						 !"H".equals(bond.getAtom(0).getSymbol()) &&
						 !"H".equals(bond.getAtom(1).getSymbol())
						 )
						selected.addBond(bond);				
			}				
			
			return ok;
		} catch (Exception x) {
			throw new DecisionMethodException(x);
		}
	}	
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		logger.info(toString());
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf ==null) throw new DecisionMethodException(ERR_STRUCTURENOTPREPROCESSED);
	    
	    FunctionalGroups.markCHn(mol);
		IMolecularFormula formula = MolecularFormulaManipulator.getMolecularFormula(mol);
		int c = MolecularFormulaManipulator.getElementCount(formula,MoleculeTools.newElement(formula.getBuilder(),"C"));
		int h = MolecularFormulaManipulator.getElementCount(formula,MoleculeTools.newElement(formula.getBuilder(),"H"));
		int o = MolecularFormulaManipulator.getElementCount(formula,MoleculeTools.newElement(formula.getBuilder(),"O"));
		List list =null;
	    if ((mol.getAtomCount() == (c+h+o)) && (o>0)) {
			IRingSet rings = mf.getRingset();
			if ((rings !=null) && (rings.getAtomContainerCount() > 0)) { 
			    if (mf.isAromatic()) {
			    	logger.fine("Aromatic, can't be\t"+MSG_COMMONCARBOHYDRATE);
			    	return false;
			    } else for (int i=0;i<rings.getAtomContainerCount();i++) {
			    	IRing ring = (IRing)rings.getAtomContainer(i);
			    	if ((ring.getAtomCount() < 5) || (ring.getAtomCount() > 6)) {
				    	logger.fine("Ring but not 5 or 6 membered\t"+MSG_NOTACOMMONCARBOHYDRATE);
				    	return false;			    		
			    	}
			    }
			    //cyclic, should have only alcohol or ether groups
			    list = FunctionalGroups.getUniqueBondMap(mol,getSubstructure(index_alcohol),true);
				if ((list == null) || (list.size()==0)) {
			    	logger.fine(MSG_HASGROUP+getSubstructure(index_alcohol).getID()+MSG_NO+MSG_NOTACOMMONCARBOHYDRATE);					
					return false;
				} else if ((list.size()<3)) {
			    	logger.fine(MSG_HASGROUP+getSubstructure(index_alcohol).getID()+MSG_NO+MSG_NOTACOMMONCARBOHYDRATE);					
					return false;					
				} else FunctionalGroups.markMaps(mol,getSubstructure(index_alcohol),list);
				//ether
			    list = FunctionalGroups.getUniqueBondMap(mol,getSubstructure(index_ether),true);
				if ((list == null) || (list.size()==0)) {
			    	logger.fine(MSG_HASGROUP +getSubstructure(index_ether).getID()+MSG_NO+MSG_NOTACOMMONCARBOHYDRATE);					
					return false;
				} else {
					FunctionalGroups.markMaps(mol,getSubstructure(index_ether),list);
				}
				//and nothing else
				if (FunctionalGroups.hasMarkedOnlyTheseGroups(mol,idsCyclic)) {
					logger.fine(MSG_COMMONCARBOHYDRATE+MSG_YES);
					return true;
				} else {
					logger.fine(MSG_COMMONCARBOHYDRATE+MSG_NO);
					return false;
				}
			    
			} else { //acyclic should have only aldehyde, ketone and alcohol groups
				//checking for alcohol groups
				list = FunctionalGroups.getUniqueBondMap(mol,getSubstructure(index_alcohol),true);
				if ((list == null) || (list.size()==0)) {
			    	logger.fine(MSG_HASGROUP+getSubstructure(index_alcohol).getID()+MSG_NO+MSG_NOTACOMMONCARBOHYDRATE);					
					return false;
				} else if ((list.size()<3)) {
			    	logger.fine(MSG_HASGROUP+getSubstructure(index_alcohol).getID()+MSG_NO+MSG_NOTACOMMONCARBOHYDRATE);					
					return false;					
				} else FunctionalGroups.markMaps(mol,getSubstructure(index_alcohol),list);
				//one aldehyde group
				list = FunctionalGroups.getUniqueBondMap(mol,getSubstructure(index_aldehyde),true);
				if ((list == null) || (list.size()==0)) {
			    	logger.fine(MSG_HASGROUP+getSubstructure(index_aldehyde).getID()+MSG_NO);					
				} else if (list.size() > 1) {
			    	logger.fine(MSG_MANYGROUPSOFAKIND+getSubstructure(index_aldehyde).getID()+ MSG_NOTACOMMONCARBOHYDRATE);
			    	return false;
				} else FunctionalGroups.markMaps(mol,getSubstructure(index_aldehyde),list);
				//or one ketone group
				list = FunctionalGroups.getUniqueBondMap(mol,getSubstructure(index_ketone),true);
				if ((list == null) || (list.size()==0)) {
			    	logger.fine(MSG_HASGROUP+getSubstructure(index_ketone)+MSG_NO);					
				} else if (list.size() > 1) {
			    	logger.fine(MSG_MANYGROUPSOFAKIND+getSubstructure(index_ketone).getID()+ MSG_NOTACOMMONCARBOHYDRATE);
			    	return false;
				} else FunctionalGroups.markMaps(mol,getSubstructure(index_ketone),list);
				
				if (FunctionalGroups.hasMarkedOnlyTheseGroups(mol,idsAcyclic)) {
					logger.info(MSG_COMMONCARBOHYDRATE+MSG_YES);
					return true;
				} else {
					logger.fine(MSG_COMMONCARBOHYDRATE+MSG_NO);
					return false;
				}
			}
	    } else {
	    	logger.info(MSG_NOTACOMMONCARBOHYDRATE);
	    	return false;
	    }
	}
	@Override
	public boolean isImplemented() {
		return true;
	}

}
