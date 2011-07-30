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
package eye;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorSpecification;

import toxTree.core.IDecisionInteractive;
import toxTree.core.IDecisionResult;
import toxTree.exceptions.DecisionMethodException;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNodesFactory;
import toxTree.tree.UserDefinedTree;

/**
 * Decision tree for estimating skin irritation and corrosion potential.
 * Implements rules publlished in 
 * <i>?????????</i>. <br>
 * <p> Rules: 
 * <ul>
 * <li>physicochemical property limits
 * <li>structural rules 
 * <li>implementation in {@link eye.rules} package
 * </ul>
 * <p>Categories:
 * <ul>
 * <li>{@link eye.categories.CategoryNotCorrosive2Skin}
 * <li>{@link eye.categories.CategoryNotIrritatingOrCorrosive2Eye}
 * <li>{@link eye.categories.CategoryNotIrritating2Eye}
 * <li>{@link eye.categories.CategoryIrritating}
 * <li>{@link eye.categories.CategoryCorrosiveEye}
 * <li>{@link eye.categories.CategoryIrritatingOrCorrosive}
 * <li>{@link eye.categories.CategoryUnknown} 
 * </ul>
 * 
 * @author Nina Jeliazkova 
 * @author Vania Tsakovska
 * @version 0.1, 2008-3-31
 */
public class EyeIrritationRules extends UserDefinedTree implements IDecisionInteractive {
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 0;
    protected boolean residuesIDVisible;
    protected  boolean interactive = true;
    
	public final static transient String[] c_rules = { 
			"eye.rules.Rule1", //1 Rule1
			"eye.rules.RuleLogP",  //2 Rule2
			"eye.rules.RuleLogP1",  //3 RuleLogP1
			"eye.rules.RuleLipidSolubility", //4 Rule4
			"eye.rules.RuleAqueousSolubility1",//5 Rule5
			"eye.rules.RuleAqueousSolubility",//6 Rule6
			"eye.rules.RuleMolWeight", //7 Rule7
			"eye.rules.RuleHasOnlyC_H_O", //8 RuleHasOnlyC_H_O
			"eye.rules.RuleMeltingPoint_8_1", //9 RuleMeltingPoint_8_1
			"eye.rules.RuleMolWeight_8_2", //10 
			"eye.rules.RuleAqueousSolubility_8_3", //11
			"eye.rules.RuleAqueousSolubility_8_4", //12
			"eye.rules.RuleHasOnlyC_H_O_N", //13
			"eye.rules.RuleLipidSolubility_9_1", //14
			"eye.rules.RuleMolWeight_9_2", //15
			"eye.rules.RuleAqueousSolubility_9_3", //16
			"eye.rules.RuleLogP_9_4", //17
			"eye.rules.RuleHasOnlyC_H_O_N_Halogen", //18
			"eye.rules.RuleLogP_10_1", //19
			"eye.rules.RuleAqueousSolubility_10_5", //20
			"eye.rules.RuleMolWeight_10_3", //21
			"eye.rules.RuleLipidSolubility_10_4", //22
			"eye.rules.RuleAqueousSolubility_10_2", //23
			"eye.rules.RuleHasOnlyC_H_O_N_S", //24
			"eye.rules.RuleMolWeight_11_1", //25
			"eye.rules.RuleMeltingPoint_11_2", //26
			"eye.rules.RuleMelting_Point_11_3", //27
			"eye.rules.RuleLogP_11_4", //28
			"eye.rules.RuleLogP_11_5", //29
			"eye.rules.RuleLogP_11_6", //30
			"eye.rules.RuleAqueousSolubility_11_7", //31
			"eye.rules.RuleHasOnlyC_H_O_Halogen", //32
			"eye.rules.RuleMolweight_12_1", //33
			"eye.rules.RuleMolWeight_12_2", //34
			"eye.rules.RuleMeltingPoint_12_3", //35
			"eye.rules.RuleLogP_12_4", //36
			
			"eye.rules.Rule13_AliphaticMonoalcohols", //38
			"eye.rules.Rule14Aliphatic_glycerol_monoethers", //39
			"eye.rules.Rule15", //40
			"eye.rules.Rule16", //41
			"eye.rules.Rule17", //42
			"eye.rules.Rule18", //43
			"eye.rules.Rule19", //44
			"eye.rules.Rule20", //45
			"eye.rules.Rule21", //46
			"eye.rules.Rule22", //47
			"eye.rules.Rule23", //48
			"eye.rules.Rule24", //49
			"eye.rules.Rule25", //50
			"eye.rules.Rule26", //51
			"eye.rules.Rule27", //52
			"eye.rules.Rule28", //53
			"eye.rules.Rule29", //54
			"eye.rules.Rule30", //55
			"eye.rules.Rule31", //56
			"eye.rules.Rule32", //57
			"eye.rules.Rule33", //58
			"eye.rules.Rule34", //59
			"eye.rules.Rule35", //60
			"eye.rules.Rule36", //61
			"eye.rules.Rule37", //62
			"eye.rules.Rule38", //63
			"eye.rules.Rule39" //64				
			
	
			};
	private final static transient int c_transitions[][] ={
		//{if no go to, if yes go to, assign if no, assign if yes}
		{2,0,0,1}, //Q1 ok
		{3,0,0,2}, //Q2 ok
		{4,0,0,1}, //Q3 ok
		{5,0,0,1}, //Q4 ok
		{6,0,0,4}, //Q5 ok
		{7,0,0,3}, //Q6 ok
		{8,0,0,4}, //Q7 ok
		{13,9,0,0}, //Q8 ok
		{10,0,0,1}, //Q9 ok
		{11,0,0,2}, //Q10 ok
		{12,0,0,7}, //Q11 ok
		{13,0,0,2}, //Q12 ok
		{18,14,0,0}, //Q13 ok
		{15,0,0,1}, //Q14 ok  9.1
		{16,0,0,1}, //Q15 ok  9.2
		{17,0,0,1}, //Q16 ok  9.3
		{18,0,0,1}, //Q17 ok  9.4
		{24,19,0,0}, //Q18 ok  10
		{20,0,0,5}, //Q19 ok  10.1
		{21,0,0,3}, //Q20 ok  10.5
		{22,0,0,1}, //Q21 ok  10.3
		{23,0,0,1}, //Q22 ok  10.4
		{24,0,0,1}, //Q23 ok  10.2
		{32,25,0,0}, //Q24 ok  11
		{26,0,0,6}, //Q25 ok  11.1
		{27,0,0,4}, //Q26 ok  11.2
		{28,0,0,1}, //Q27 ok  11.3
		{29,0,0,1}, //Q28 ok  11.4
		{30,0,0,3}, //Q29 ok  11.5
		{31,0,0,4}, //Q30 ok  11.6
		{32,0,0,7}, //Q31 ok  11.7
		{37,33,0,0}, //Q32 ok  12
		{34,0,0,6}, //Q33 ok  12.1
		{35,0,0,1}, //Q34 ok  12.2
		{36,0,0,1}, //Q35 ok  12.3
		{37,0,0,5}, //Q36 ok  12.4
		
		{38,0,0,8}, //Q37 ok  13
		{39,0,0,8}, //Q38 ok  14
		{40,0,0,8}, //Q39 ok  15
		{41,0,0,8}, //Q40 ok  16
		{42,0,0,8}, //Q41 ok  17
		{43,0,0,8}, //Q42 ok  18
		{44,0,0,8}, //Q43 ok  19
		{45,0,0,8}, //Q44 ok  20
		{46,0,0,8}, //Q45 ok  21
		{47,0,0,8}, //Q46 ok  22
		{48,0,0,8}, //Q47 ok  23
		{49,0,0,8}, //Q48 ok  24
		{50,0,0,8}, //Q49 ok  25
		{51,0,0,8}, //Q50 ok  26
		{52,0,0,8}, //Q51 ok  27
		{53,0,0,8}, //Q52 ok  28
		{54,0,0,8}, //Q53 ok  29
		{55,0,0,9}, //Q54 ok  30
		{56,0,0,9}, //Q55 ok  31
		{57,0,0,9}, //Q56 ok  32
		{58,0,0,9}, //Q57 ok  33
		{59,0,0,10}, //Q58 ok  34
		{60,0,0,10}, //Q59 ok  35
		{61,0,0,10}, //Q60 ok  36
		{62,0,0,10}, //Q61 ok  37
		{63,0,0,10}, //Q62 ok  38
		{0,0,11,10}, //Q63 ok  39		
	};

	private final static transient String c_categories[] ={
		"eye.categories.CategoryNotCorrosive2Skin", //1
		"eye.categories.CategoryNotCorrosive2SkinAndIrritating2Eye", //2
		"eye.categories.CategoryNotCorrosive2Eye", //3
		"eye.categories.CategoryNotIrritating2Eye", //4
		"eye.categories.CategoryNotCorrosive2SkinEye", //5
		"eye.categories.CategoryNotCorrosive2SkinOrIrritating2Eye", //6
		"eye.categories.CategoryNotIrritatingOrCorrosive2Eye", //7
		"eye.categories.CategoryCorrosiveEye", //8
		"eye.categories.CategoryModerateIrritation2Eye", //9
		"eye.categories.CategoryCorrosiveSkin",	//10
		"eye.categories.CategoryUnknown" //11
	};



	/**
	 * 
	 */
	public EyeIrritationRules() throws DecisionMethodException {
		super(new CategoriesList(c_categories,true),c_rules,c_transitions,new DecisionNodesFactory(true));
		/*
		super(new CategoriesList(c_categories),null);
		rules = new DecisionNodesList(categories,c_rules,c_transitions);
		
		if (rules instanceof Observable) ((Observable)rules).addObserver(this);
		*/
		setChanged();
		notifyObservers();
		/*
		if (changes != null ) {
			changes.firePropertyChange("Rules", rules,null);
			changes.firePropertyChange("Transitions", transitions,null);
		}
		*/
		setTitle("Eye irritation and corrosion");
		setExplanation(
				"<html>Estimates eye irritation and corrosion potential by physicochemical property limits and structural rules, according to:<br><ul><li><i>"+ 
				"Gerner, I., Liebsch, M., Spielmann, H. (2005). Assessment of the eye irritating properties of chemicals by applying alternatives to the Draize rabbit eye test: the use of QSARs and in vitro tests for the classification of eye irritation. Alternatives to Laboratory Animals 33, 215-237.</i></ul></html>"
				
				);
        setPriority(8);
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
	public DescriptorSpecification getSpecification() {
        return new DescriptorSpecification(
                "http://toxtree.sourceforge.net/eye.html",
                getTitle(),
                this.getClass().getName(),                
                "Toxtree plugin");
	}
}