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
package cramer2;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Observable;

import org.openscience.cdk.qsar.DescriptorSpecification;

import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNodesList;
import toxTree.tree.UserDefinedTree;
import cramer2.categories.CramerTreeResult;

/**
 * TODO update
 * An {@link toxTree.tree.UserDefinedTree} descendant, implementing 
 * the decision tree described in "Cramer G. M., R. A. Ford, R. L. Hall, Estimation of Toxic Hazard - A Decision Tree Approach, J.
 * Cosmet. Toxicol., Vol.16, pp. 255-276, Pergamon Press, 1978". <br>
 * Uses all {@link toxTree.core.IDecisionRule} rules in {@link cramer2} package
 * Assigns categories:
 * <ul>
 * <li>{@link cramer2.categories.CramerClass1}
 * <li>{@link cramer2.categories.CramerClass2}
 * <li>{@link cramer2.categories.CramerClass2}
 * </ul>
 * 
 * @author Nina Jeliazkova <br>
 * @version 0.1, 2005-4-30
 */
public class CramerRulesWithExtensions extends UserDefinedTree  {
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 2709354449315583323L;
    protected boolean residuesIDVisible;
    
	public final static transient String[] c_rules = { 
			cramer2.rules.RuleNormalBodyConstituent.class.getName(), //1 
			toxTree.tree.cramer.RuleToxicFunctionalGroups.class.getName(),  //2
			toxTree.tree.cramer.RuleHasOtherThanC_H_O_N_S2.class.getName(), //3
			cramer2.rules.RuleHasOnlySaltSulphonateSulphate.class.getName(), //4 
			toxTree.tree.cramer.RuleSimplyBranchedAliphaticHydrocarbon.class.getName(), //5
            "toxTree.tree.cramer.RuleSomeBenzeneDerivatives", //6
            "toxTree.tree.cramer.RuleIsHeterocyclic",  //7
			"toxTree.tree.cramer.RuleLactoneOrCyclicDiester", //8 
            "toxTree.tree.cramer.RuleLactonesFusedOrUnsaturated", //9 
			"toxTree.tree.cramer.Rule3MemberedHeterocycle", //10
			"toxTree.tree.cramer.RuleHeterocyclicComplexSubstituents", //11
			"toxTree.tree.cramer.RuleHeteroaromatic",//12
			"toxTree.tree.cramer.RuleRingWithSubstituents",//13
			"toxTree.tree.cramer.RuleManyAromaticRings14",//14
			"toxTree.tree.cramer.RuleReadilyHydrolysedMononuclear",//15
			"toxTree.tree.cramer.RuleIsCommonTerpene", //16
			"toxTree.tree.cramer.RuleReadilyHydrolysedToCommonterpene", //17
			"toxTree.tree.cramer.RuleKetoneAlcoholEtc", //18
			"toxTree.tree.cramer.RuleIsOpenChain", //19
			"toxTree.tree.cramer.RuleAliphaticWithSomeFuncGroups",//20
			"toxTree.tree.cramer.Rule3FuncGroups",//21
			"toxTree.tree.cramer.RuleCommonComponentOfFood",//22
			"toxTree.tree.cramer.RuleIsAromatic",//23
			"toxTree.tree.cramer.RuleMonocarbocyclic",//24
			"toxTree.tree.cramer.RuleCyclopropaneEtc",//25
			"toxTree.tree.cramer.RuleMonocycloalkanoneEtc",//26
			"toxTree.tree.cramer.RuleRingsWithSubstituents",//27
			"toxTree.tree.cramer.RuleManyAromaticRings28",  //this is the same as Q14
			"toxTree.tree.cramer.RuleReadilyHydrolysed29", //this is the same as Q15
			"toxTree.tree.cramer.RuleRingComplexSubstituents30",  //30
			"toxTree.tree.cramer.RuleAcyclicAcetalEsterOfQ30",  //31
			"toxTree.tree.cramer.Rule32",			//"cramer2.rules.RuleOnlyFuncGroupsQ30",		//32
			"toxTree.tree.cramer.RuleSufficientSulphonateGroups",   //33
			"toxTree.tree.rules.RuleOpenChain", //34 (Q9)
			"toxTree.tree.rules.RuleHeterocyclic", //35
			"toxTree.tree.rules.RuleAromatic", //36  (Q29)
			"toxTree.tree.rules.RuleHeterocyclic", //37 (Q15)
			"toxTree.tree.rules.RuleCommonTerpene", //38 (Q17)
			"toxTree.tree.rules.RuleAromatic", //39  (Q30)
			"cramer2.rules.RuleUnchargedOrganophosphates", //40
			"cramer2.rules.RuleReadilyHydrolysedPO4", //41
            "cramer2.rules.RuleBenzeneAnalogues", //42
            "cramer2.rules.RuleDivalentSulphur", //43
            "cramer2.rules.RuleFreeABUnsaturatedHetero" //44
			};
	private final static transient int c_transitions[][] ={
		//{if no go to, if yes go to, assign if no, assign if yes}
		{2,0,0,1}, //Q1
		{3,0,0,3}, //Q2
		{43,4,0,0}, //Q3 //{5,4,0,0}
		{0,40,3,0}, //Q4 //{0,7,3,0},
		{6,0,0,1}, //Q5
		{42,0,0,3}, //Q6 //7,0,0,3
		{16,8,0,0}, //Q7
		{10,9,0,0}, //Q8
		//see the paper - INTRODUCING ADDITIONAL RULES TO COPE WITH Q9 
		{34,0,0,3}, //Q9 XX{10,0,0,3},
		{11,0,0,3}, //Q10
		{12,33,0,0}, //Q11
		{22,13,0,0}, //Q12
		{0,14,3,0}, //Q13
		{22,15,0,0}, //Q14
		{33,37,0,0}, //Q15 XX{33,22,0,0}, //Q15
		{17,0,0,1}, //Q16
		{19,38,0,0}, //Q17 XX{19,18,0,0},
		{0,0,1,2}, //Q18
		{23,20,0,0}, //Q19
		{22,21,0,0}, //Q20
		{44,0,0,3}, //Q21 //{18,0,0,3}, //Q21
		{33,0,0,2}, //Q22
		{24,27,0,0}, //Q23
		{25,18,0,0}, //Q24		
		{26,0,0,2}, //Q25
		{22,0,0,2}, //Q26
		{0,28,3,0}, //Q27
		{30,29,0,0}, //Q28
		{33,36,0,0}, //Q29 XX{33,30,0,0},
		{39,31,0,0}, //Q30 XX{18,31,0,0},
		{32,39,0,0}, //Q31 XX{32,18,0,0}, //Q31
		{22,0,0,2}, //Q32
		{0,0,3,1}, //Q33 //this  is the last original question
		{35,20,0,0},  //Q34 from Q9
		{23,10,0,0},  //Q35 
		{19,30,0,0},  //Q36 from Q29
		{16,22,0,0},  //Q37 from Q15
		{19,18,0,0},  //Q38 from Q17
		{19,18,0,0},  //Q39 from Q30 or Q31
        {41,0,0,3},  //Q40 {41,0,0,3},
        {7,0,0,1},  //Q41 always false, only 1 if only PO4
		{7,0,0,3}, //Q42
		{5,0,0,3}, //Q43
		{18,0,0,3} //Q44
	};

	private final static transient String c_categories[] ={
		"cramer2.categories.CramerClass1",
		"cramer2.categories.CramerClass2",
		"cramer2.categories.CramerClass3"
	};
	/**
	 * 
	 */
	public CramerRulesWithExtensions() throws DecisionMethodException {
		super(new CategoriesList(c_categories),null);
		rules = new DecisionNodesList(categories,c_rules,c_transitions);
		if (rules instanceof Observable) ((Observable)rules).addObserver(this);
		setTitle("Cramer rules, with extensions");
		
		setChanged();
		notifyObservers();
		/*
		if (changes != null ) {
			changes.firePropertyChange("Rules", rules,null);
			changes.firePropertyChange("Transitions", transitions,null);
		}
		*/

        setPriority(2);
	}


	/* (non-Javadoc)
     * @see toxTree.core.IDecisionMethod#addPropertyChangeListener(java.beans.PropertyChangeListener)
     */
    @Override
	public void addPropertyChangeListener(PropertyChangeListener l) {
        if (changes == null) 		changes = new PropertyChangeSupport(this);        
        changes.addPropertyChangeListener(l);
		for (int i=0; i < rules.size(); i++) 
			if (rules.getRule(i) != null)
				rules.getRule(i).addPropertyChangeListener(l);
    }
    /* (non-Javadoc)
     * @see toxTree.core.IDecisionMethod#removePropertyChangeListener(java.beans.PropertyChangeListener)
     */
    @Override
	public void removePropertyChangeListener(PropertyChangeListener l) {
        if (changes == null) {
	        changes.removePropertyChangeListener(l);
	        for (int i=0; i < rules.size(); i++) 
	        	if (rules.getRule(i) != null)
	        		rules.getRule(i).removePropertyChangeListener(l);
        }	
    }	
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString() {
        return getName();
    }
	


	/* (non-Javadoc)
     * @see toxTree.core.IDecisionMethod#getName()
     */
    public String getName() {
        return name;
    }
    /* (non-Javadoc)
     * @see toxTree.core.IDecisionMethod#setName(java.lang.String)
     */
    public void setName(String value) {
       name = value;

    }
	@Override
	public StringBuffer explainRules(IDecisionResult result,boolean verbose) throws DecisionMethodException{
		try {
			StringBuffer b = result.explain(verbose);
			return b;
		} catch (DecisionResultException x) {
			throw new DecisionMethodException(x);
		}
	}
	/* (non-Javadoc)
	 * @see toxTree.tree.AbstractTree#createDecisionResult()
	 */
	@Override
	public IDecisionResult createDecisionResult() {
		IDecisionResult result =  new CramerTreeResult();
		result.setDecisionMethod(this);
		return result;

	}


	public boolean isResiduesIDVisible() {
		return residuesIDVisible;
	}


	public void setResiduesIDVisible(boolean residuesIDVisible) {
		this.residuesIDVisible = residuesIDVisible;
		for (int i=0;i< rules.size(); i++) {
			rules.getRule(i).hideResiduesID(!residuesIDVisible);
		}
	}
	@Override
	public void setEditable(boolean value) {
		editable = value;
		for (int i=0;i<rules.size();i++)
			rules.getRule(i).setEditable(value);
	}
	/*
	@Override
	public Element toXML(Document document) throws XMLDecisionMethodException {
		return toShallowXML(document);
	}
	*/
	public DescriptorSpecification getSpecification() {
        return new DescriptorSpecification(
                "http://toxtree.sourceforge.net/cramer2.html",
                getTitle(),
                this.getClass().getName(),                
                "Toxtree plugin");
	}    
}
