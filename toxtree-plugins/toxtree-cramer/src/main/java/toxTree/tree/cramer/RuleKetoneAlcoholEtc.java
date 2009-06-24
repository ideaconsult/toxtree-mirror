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
 * Filename: RuleKetoneAlcoholEtc.java
 */
package toxTree.tree.cramer;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.query.FunctionalGroups;
import toxTree.query.MolFlags;
import toxTree.query.QueryAtomContainers;
import toxTree.tree.AbstractRule;

/**
 * Rule 18 of the Cramer scheme (see {@link toxTree.tree.cramer.CramerRules})
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-5-2
 */
public class RuleKetoneAlcoholEtc extends AbstractRule {
	public final static transient String MSG_18H="Acyclic aliphatic ketone,ketal or ketoalcohol with >=4 carbons on either side of the keto group\t";
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -7970454685982033314L;

    /**
	 * Constructor
	 * 
	 */
	public RuleKetoneAlcoholEtc() {
		//TODO fix sterically hindered condition (example NO fails)
		super();
		id = "18";
		title = "One of the list (see explanation)";
		explanation.append("<html>Is the substance one of the following: <UL>");
		explanation.append("<LI>(a)a vicinal diketone; or a ketone or ketal of a ketone attached to a terminal vynil group");
		explanation.append("<LI>(b)a secondary alcohol or ester of a secondary alcohol attached to a terminal vinyl group");
		explanation.append("<LI>(c)allyl alcohol or its acetal, ketal or ester derivative");
		explanation.append("<LI>(cellbox)allyl mercaptan, an allyl sulphide, an allyl thioester or allyl amine");
		explanation.append("<LI>(e)acrolein, a methacrolein or ther acetals");
		explanation.append("<LI>(f)acrylic or methacrylic acid");
		explanation.append("<LI>(g)an acetylenic compound");
		explanation.append("<LI>(h)an acyclic <i>aliphatic</i> (A) ketone,ketal or ketoalcohol with no other functional groups and with four or more carbons on either side of the keto group");		
		explanation.append("<LI>(i)a substance in which the <i>functional groups</i> (E) are all <i>sterically hindered</i> (J)");
		explanation.append("</UL>");
		explanation.append("Q18 examines the terpenes and later the open-chain and mononuclear substances by reference) to determine whether they contain certail structural features generally thought to be associated with some enhanced toxicity.");
		explanation.append("</html>");
		examples[0] = "C1C(OC(=C)C)C(CC(C)C1C)C(C)C";
		examples[1] = "COC(C)(OC)C=C";
		editable = false;
	}
	/**
	 * {@link toxTree.core.IDecisionRule#verifyRule(IAtomContainer)}
	 */
	public boolean verifyRule(IAtomContainer  mol) throws DecisionMethodException {
		logger.info(toString());
		logger.debug("Molecule\t",mol.getID());
		//a)
		logger.debug("Q18 a)");
		QueryAtomContainer q = FunctionalGroups.vicinalDiKetone();
		if (FunctionalGroups.hasGroup(mol,q)) return true;
		
		q = FunctionalGroups.ketoneAttachedToTerminalVinyl();
		if (FunctionalGroups.hasGroup(mol,q)) return true;
		
		q = FunctionalGroups.ketalAttachedToTerminalVinyl();
		if (FunctionalGroups.hasGroup(mol,q)) return true;
		
		//b)
		logger.debug("Q18 b)");
		q = FunctionalGroups.alcoholSecondaryAttachedToTerminalVinyl();
		if (FunctionalGroups.hasGroup(mol,q)) return true;
		
		q = FunctionalGroups.esterOfalcoholSecondaryAttachedToTerminalVinyl();
		if (FunctionalGroups.hasGroup(mol,q)) return true;
		
		//c)
		logger.debug("Q18 c)");
		IAtomContainer a = FunctionalGroups.allylAlcohol();
		if (FunctionalGroups.isSubstance(mol,a)) return true;

		q = FunctionalGroups.allylAlcoholAcetal();
		if (FunctionalGroups.hasGroup(mol,q)) return true;

		q = FunctionalGroups.allylAlcoholEsterDerivative();
		if (FunctionalGroups.hasGroup(mol,q)) return true;
		
		//cellbox)
		logger.debug("Q18 cellbox)");
		a = FunctionalGroups.allylMercaptan();
		if (FunctionalGroups.isSubstance(mol,a)) return true;

		a = FunctionalGroups.allylSulphide();
		if (FunctionalGroups.isSubstance(mol,a)) return true;

		a = FunctionalGroups.allylAmine();
		if (FunctionalGroups.isSubstance(mol,a)) return true;
		
		a = FunctionalGroups.allylThioester();
		if (FunctionalGroups.isSubstance(mol,a)) return true;
		//e)
		logger.debug("Q18 e)");
		a = FunctionalGroups.acrolein();
		if (FunctionalGroups.isSubstance(mol,a)) return true;

		a = FunctionalGroups.methacrolein();
		if (FunctionalGroups.isSubstance(mol,a)) return true;
		
		a = FunctionalGroups.methacroleinAcetal();
		if (FunctionalGroups.isSubstance(mol,a)) return true;
		
		logger.debug("is acrolein acetal - not implemented, assuming NO");
		//f)
		logger.debug("Q18 f)");
		a = FunctionalGroups.acrylicAcid();
		if (FunctionalGroups.isSubstance(mol,a)) return true;

		a = FunctionalGroups.methacrylicAcid();
		if (FunctionalGroups.isSubstance(mol,a)) return true;
		//g)
		logger.debug("Q18 g)");
	    MolFlags mf = (MolFlags) mol.getProperty(MolFlags.MOLFLAGS);
	    if (mf == null) throw new DecisionMethodException(AbstractRule.ERR_STRUCTURENOTPREPROCESSED);
	    if (mf.isAcetylenic()) return true;;
		//h)
	    logger.debug("Q18 h)");
		if (mf.isAliphatic() && !mf.isAlicyclic()) {
			//ketone, ketal, ketoalcohol as in h)
			
			QueryAtomContainer qKetone4 = FunctionalGroups.createQuery("CCCCC(=O)C","Ketone with >=4 C");
			QueryAtomContainer qKetal4 = FunctionalGroups.createQuery("CCCCC(OC)(OC)C","Ketal with >=4 C");
			QueryAtomContainer qAlcohol = FunctionalGroups.alcohol(true);
			FunctionalGroups.markCHn(mol);
			
			ArrayList ids = new ArrayList();
			QueryAtomContainers qs = new QueryAtomContainers();
			qs.add(qKetone4);
			ids.add(qKetone4.getID());
			ids.add(FunctionalGroups.C);
			ids.add(FunctionalGroups.CH);
			ids.add(FunctionalGroups.CH2);
			ids.add(FunctionalGroups.CH3);
			
			if (FunctionalGroups.hasGroup(mol,qKetone4) &&
				FunctionalGroups.hasOnlyTheseGroups(mol,qs,ids,true)) {
				logger.info(qKetone4.getID(),"\tYES");
				return true;
			} else logger.debug(qKetone4.getID(),"\tNO");
			
			qs.add(qAlcohol);
			ids.add(qAlcohol.getID());
			if (FunctionalGroups.hasGroup(mol,qKetone4) &&
			    FunctionalGroups.hasGroup(mol,qAlcohol) &&	
				FunctionalGroups.hasOnlyTheseGroups(mol,qs,ids,true)) {
				logger.info("Ketoalcohol ...","\tYES");
				return true;
			} else logger.debug("Ketoalcohol ...","\tNO");
			
			qs.clear();
			qs.add(qKetal4);
			ids.clear();
			ids.add(qKetal4.getID());
			ids.add(FunctionalGroups.C);
			ids.add(FunctionalGroups.CH);
			ids.add(FunctionalGroups.CH2);
			ids.add(FunctionalGroups.CH3);
			if (FunctionalGroups.hasGroup(mol,qKetal4) &&
				FunctionalGroups.hasOnlyTheseGroups(mol,qs,ids,true)) {
				logger.info(qKetal4.getID(),"\tYES");
				return true;
			} else logger.debug(qKetal4.getID(),"\tNO");			

		}
		
		//i)
		if (logger.isDebugEnabled()) {
			logger.debug("Q18 i)");
			q = FunctionalGroups.stericallyHindered();
			List list = FunctionalGroups.getUniqueBondMap(mol,q,false);
			FunctionalGroups.markMaps(mol,q,list);
			logger.debug(FunctionalGroups.mapToString(mol));

		}
		if (FunctionalGroups.hasGroup(mol,FunctionalGroups.stericallyHindered())) {
			return true;
		} else logger.info("Q18\tNO");
		return false;
		
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractRule#isImplemented()
	 */
	@Override
	public boolean isImplemented() {
		return true;
	}

}
