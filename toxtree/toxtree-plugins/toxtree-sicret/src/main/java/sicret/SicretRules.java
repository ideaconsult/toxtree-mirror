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
package sicret;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Observable;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionInteractive;
import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNodesList;
import toxTree.tree.UserDefinedTree;

/**
 * Decision tree for estimating skin irritation and corrosion potential.
 * Implements rules publlished in 
 * <i>The Skin Irritation Corrosion Rules Estimation Tool (SICRET), John D. Walker, Ingrid Gerner, Etje Hulzebos, Kerstin Schlegel, QSAR Comb. Sci. 2005, 24, pp378-384</i>. <br>
 * <p> Rules: 
 * <ul>
 * <li>physicochemical property limits
 * <li>structural rules 
 * <li>implementation in {@link sicret.rules} package
 * </ul>
 * <p>Categories:
 * <ul>
 * <li>{@link sicret.categories.CategoryNotCorrosive}
 * <li>{@link sicret.categories.CategoryNotIrritatingOrCorrosive}
 * <li>{@link sicret.categories.CategoryNotIrritating}
 * <li>{@link sicret.categories.CategoryIrritating}
 * <li>{@link sicret.categories.CategoryCorrosive}
 * <li>{@link sicret.categories.CategoryIrritatingOrCorrosive}
 * <li>{@link sicret.categories.CategoryUnknown} 
 * </ul>
 * 
 * @author Nina Jeliazkova 
 * @author Martin Martinov 
 * @version 0.1, 2005-4-30
 */
public class SicretRules extends UserDefinedTree implements IDecisionInteractive {
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 0;
    protected boolean residuesIDVisible;
    protected  boolean interactive = true;
    
	public final static transient String[] c_rules = { 
			"sicret.rules.RuleMeltingPoint", //1 Rule1
			"sicret.rules.RuleLogP",  //2 Rule2
			"sicret.rules.RuleLipidSolubility", //3 Rule3
			"sicret.rules.RuleHasOnlyC_H_O", //4 RuleHasOnlyC_H_O
			"sicret.rules.Rule4", //5 Rule4
            "sicret.rules.RuleMolWeight", //6 Rule5
            "sicret.rules.RuleSurfaceTension",  //7 Rule6
			"sicret.rules.RuleVapourPressure", //8 Rule7
            "sicret.rules.RuleHasOnlyC_H_O_N", //9 RuleHasOnlyC_H_O_N
			"sicret.rules.Rule8", //10 Rule8
			"sicret.rules.Rule9", //11 Rule9
			"sicret.rules.RuleAqueousSolubility",//12 Rule10
			"sicret.rules.Rule11",//13 Rule11
			"sicret.rules.Rule12",//14 Rule12
			"sicret.rules.Rule13",//15 Rule13
			"sicret.rules.Rule14", //16 Rule14
			"sicret.rules.Rule15", //17 Rule15
			"sicret.rules.Rule16", //18 Rule16
			"sicret.rules.RuleHasOnlyC_H_O_N_Halogen", //19 RuleHasOnlyC_H_O_N_Halogen
			"sicret.rules.Rule17",//20 Rule17
			"sicret.rules.Rule18",//21 Rule18
			"sicret.rules.Rule19",//22 Rule19
			"sicret.rules.Rule20",//23 Rule20
			"sicret.rules.Rule21",//24 Rule21
			"sicret.rules.Rule22",//25 Rule22
			"sicret.rules.Rule23",//26 Rule23
			"sicret.rules.RuleHasOnlyC_H_O_N_S",//27 RuleHasOnlyC_H_O_N_S
			"sicret.rules.Rule24",  //28 Rule24
			"sicret.rules.Rule25", //29 Rule25
			"sicret.rules.Rule26",  //30 Rule26
			"sicret.rules.Rule27",  //31 Rule27
			"sicret.rules.Rule28",	//32 Rule28
			"sicret.rules.RuleHasOnlyC_H_O_Halogen",   //33 RuleHasOnlyC_H_O_Halogen Y/N
			"sicret.rules.Rule29", //34 Rule29
			"sicret.rules.Rule30", //35 Rule30
			"sicret.rules.RuleAlphaAlkynes", //36  //RuleAlphaAlkynes
			"sicret.rules.RuleHasOnlyC_H_O",//37 RuleHasOnlyC_H_O			
			"sicret.rules.RuleAcrylicAcids", //38 RuleAcrylicAcids
			"sicret.rules.RuleOandPQuinones", //39 RuleOandPQuinones
			"sicret.rules.RuleAliphaticSaturatedAcidsAndHalogenatedAcids",//40 RuleAliphaticSaturatedAcidsAndHalogenatedAcids
			"sicret.rules.RuleAldehydes",//41 RuleAldehydes
			"sicret.rules.RulePhenols",//42  RulePhenols
			"sicret.rules.RuleCatecholsResorcinolsHydroquinones",//43 RuleCatecholsResorcinolsHydroquinones
			"sicret.rules.RuleCatecholsResorcinolsHydroquinones",//44 RuleCatecholsResorcinolsHydroquinones
			"sicret.rules.RuleAcidAnhydrides",//45 RuleAcidAnhydrides
			"sicret.rules.RuleKetenes",//46 RuleKetenes
			"sicret.rules.RuleBetaLactones",//47 RuleBetaLactones
			"sicret.rules.RuleLactonesFusedOrUnsaturated",//48 RuleLactonesFusedOrUnsaturated
			"sicret.rules.RuleEpoxides",//49 RuleEpoxides
			"sicret.rules.RuleAcrylicAndMethacrylicEsters",//50 RuleAcrylicAndMethacrylicEsters
			"sicret.rules.RuleKetones",//51 RuleKetones
			"sicret.rules.RuleC10_C20AliphaticAlcohols",//52 RuleC10_C20AliphaticAlcohols
			"sicret.rules.RuleEthyleneGlycolEthers",	//53 RuleEthyleneGlycolEthers		
			"sicret.rules.RuleHydroPeroxides",//54 RuleHydroPeroxides
			"sicret.rules.RuleHasOnlyC_H_O_N",//55 RuleHasOnlyC_H_O_N
			"sicret.rules.RuleQuaternaryOrganicAmmoniumAndPhosphoniumSalts",//56 RuleQuaternaryOrganicAmmoniumAndPhosphoniumSalts
			"sicret.rules.RuleDi_Tri_Nitrobenzenes",//57 RuleDi_Tri_Nitrobenzenes 
			"sicret.rules.RuleAlkylAlkanolAmines",//58 RuleAlkylAlkanolAmines
			"sicret.rules.RuleA_Lactams",//59 RuleA_Lactams 		
			"sicret.rules.RuleAcidImides",//60 RuleAcidImides 
			"sicret.rules.RuleAromaticAmines",//61 RuleAromaticAmines
			"sicret.rules.RuleHasOnlyC_H_O_N_Halogen",//62 RuleHasOnlyC_H_O_N_Halogen
			"sicret.rules.RuleCarbamoylHalide",//63 RuleCarbamoylHalide
			"sicret.rules.RuleHalonitrobenzene",//64 RuleHalonitrobenzene
			"sicret.rules.RuleHasOnlyC_H_O_N_S",//65 RuleHasOnlyC_H_O_N_S
			"sicret.rules.RuleAlphaHalogenatedAmidesAndThioamides",//66 RuleAlphaHalogenatedAmidesAndThioamides 
			"sicret.rules.RuleHasOnlyC_H_O_Halogen",//67 RuleHasOnlyC_H_O_Halogen
			"sicret.rules.RuleBenzylHalides",//68 RuleBenzylHalides
			"sicret.rules.RuleHalogenatedAlkanesAndAlkenes",//69 //RuleHalogenatedAlkanesAndAlkenes
			"sicret.rules.RuleTriAndTetraHalogenatedBenzenes"//70//RuleTriAndTetraHalogenatedBenzenes		
			};
	private final static transient int c_transitions[][] ={
		//{if no go to, if yes go to, assign if no, assign if yes}
		{2,0,0,2}, //Q1 ok
		{3,0,0,2}, //Q2 ok
		{4,0,0,1}, //Q3 ok
		{9,5,0,0}, //Q4 ok
		{6,0,0,2}, //Q5
		{7,0,0,1}, //Q6
		{8,0,0,1}, //Q7
		{9,0,0,3}, //Q8		 
		{19,10,0,0}, //Q9 
		{11,0,0,2}, //Q10
		{12,0,0,1}, //Q11
		{13,0,0,1}, //Q12
		{14,0,0,1}, //Q13
		{15,0,0,1}, //Q14
		{16,0,0,3}, //Q15 
		{17,0,0,3}, //Q16
		{18,0,0,3}, //Q17  
		{19,0,0,3}, //Q18
		{27,20,0,0}, //Q19
		{21,0,0,2}, //Q20
		{22,0,0,1}, //Q21
		{23,0,0,1}, //Q22
		{24,0,0,1}, //Q23
		{25,0,0,3}, //Q24		
		{26,0,0,3}, //Q25
		{27,0,0,3}, //Q26		
		{33,28,0,0}, //Q27		
		{29,0,0,1}, //Q28		
		{30,0,0,1}, //Q29		
		{31,0,0,1}, //Q30 		
		{32,0,0,3}, //Q31 		
		{33,0,0,3}, //Q32		
		{36,34,0,0}, //Q33 		
		{35,0,0,2},  //Q34		
		{36,0,0,1},  //Q35		
		{37,0,0,4},  //Q36			
		{55,38,0,0},  //Q37 		
		{39,0,0,5},  //Q38 
		{40,0,0,5},  //Q39 
		{41,0,0,6},  //Q40
		{42,0,0,6},   //41
		{43,0,0,6},   //42
		{44,0,0,6},   //43
		{45,0,0,6},   //44
		{46,0,0,6},   //45
		{47,0,0,6},   //46
		{48,0,0,6},   //47
		{49,0,0,6},   //48
		{50,0,0,6},   //49
		{51,0,0,4},   //50
		{52,0,0,4},   //51
		{53,0,0,4},   //52
		{54,0,0,4},   //53
		{55,0,0,4},   //54
		{62,56,0,0},  //55		
		{57,0,0,5},   //56
		{58,0,0,5},   //57
		{59,0,0,6},   //58
		{60,0,0,6},   //59
		{61,0,0,6},   //60
		{62,0,0,4},   //61
		{65,63,0,0},  //62
		{64,0,0,6},   //63
		{65,0,0,6},   //64
		{67,66,0,0},  //65
		{67,0,0,4},   //66
		{0,68,7,0},   //67
		{69,0,0,6},   //68
		{70,0,0,4},   //69
		{0,0,7,4}    //70
	};

	private final static transient String c_categories[] ={
		"sicret.categories.CategoryNotCorrosive",
		"sicret.categories.CategoryNotIrritatingOrCorrosive",
		"sicret.categories.CategoryNotIrritating",
		"sicret.categories.CategoryIrritating",
		"sicret.categories.CategoryCorrosive",
		"sicret.categories.CategoryIrritatingOrCorrosive",
		"sicret.categories.CategoryUnknown"
	};
	/**
	 * 
	 */
	public SicretRules() throws DecisionMethodException {
		super(new CategoriesList(c_categories),null);
		rules = new DecisionNodesList(categories,c_rules,c_transitions);
		if (rules instanceof Observable) ((Observable)rules).addObserver(this);
		
		setChanged();
		notifyObservers();
		/*
		if (changes != null ) {
			changes.firePropertyChange("Rules", rules,null);
			changes.firePropertyChange("Transitions", transitions,null);
		}
		*/
		setTitle("Skin irritation / skin corrosion");
		setExplanation(
				"Estimates skin corrosion and irritatiion potential by physicochemical property limits and structural rules, according to:<br>\n" +
				"<i>1.Ingrid Gerner, Kerstin Schlegel, John D. Walker, and Etje Hulzebosc, Use of Physicochemical Property Limits to Develop Rules for Identifying Chemical Substances with no Skin Irritation or Corrosion Potential, QSAR Comb. Sci. 2004, 23, pp.726-733<br><br>\n"+
				"<i>2.John D. Walker, Ingrid Gerner, Etje Hulzebos, Kerstin Schlegel, The Skin Irritation Corrosion Rules Estimation Tool (SICRET), QSAR Comb. Sci. 2005, 24, pp.378-384<br><br>\n"+
				"<i>3.Etje Hulzebos, John D. Walker, Ingrid Gerner, and Kerstin Schlegel, Use of structural alerts to develop rules for identifying chemical substances with skin irritation or skin corrosion potential, QSAR Comb. Sci. 2005, 24, pp.332-342<br><br>\n"
				);
        setPriority(20);
	}


	/* (non-Javadoc)
     * @see toxTree.core.IDecisionMethod#addPropertyChangeListener(java.beans.PropertyChangeListener)
     */
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
	/*public IDecisionResult createDecisionResult() {
		IDecisionResult result =  new CramerTreeResult();
		result.setDecisionMethod(this);
		return result;

	}*/


	public boolean isResiduesIDVisible() {
		return residuesIDVisible;
	}


	public void setResiduesIDVisible(boolean residuesIDVisible) {
		this.residuesIDVisible = residuesIDVisible;
		for (int i=0;i< rules.size(); i++) {
			rules.getRule(i).hideResiduesID(!residuesIDVisible);
		}
	}
	public void setEditable(boolean value) {
		editable = value;
		for (int i=0;i<rules.size();i++)
			rules.getRule(i).setEditable(value);
	}
    @Override
    public void setParameters(IAtomContainer mol) {
        if (interactive) {
            JComponent c = optionsPanel(mol);
            if (c != null)
                JOptionPane.showMessageDialog(null,c,"Enter properties",JOptionPane.PLAIN_MESSAGE);
        }    
        
    }


    public boolean getInteractive() {
        return interactive;
    }




    public void setInteractive(boolean value) {
        interactive=value;
        
    }
}