/*
Copyright Ideaconsult Ltd. (C) 2005-2008  

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package mutant;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import mutant.rules.MutantTreeResult;

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
 * 

 * @author nina
 * @modified 04/07/2007
 *
 */
public class BB_CarcMutRules extends UserDefinedTree implements IDecisionInteractive{
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 0;
    protected boolean residuesIDVisible;
 

        public final static transient String[] c_rules = { 
            "mutant.rules.RuleAlertsForGenotoxicCarcinogenicity", // Rule  1         
            "mutant.rules.SA1_gen", //2
            "mutant.rules.SA2_gen", //3
            "mutant.rules.SA3_gen", //4
            "mutant.rules.SA4_gen", //5
            "mutant.rules.SA5_gen", //6
            "mutant.rules.SA6_gen", //7
            "mutant.rules.SA7_gen", //8
            "mutant.rules.SA8_gen", //9
            "mutant.rules.SA9_gen", //10
            "mutant.rules.SA11_gen", //11
            "mutant.rules.SA12_gen", //12
            "mutant.rules.SA13_gen", //13
            "mutant.rules.SA14_gen", //14
            "mutant.rules.SA15_gen", //15
            "mutant.rules.SA16_gen", //16
            "mutant.rules.SA18_gen", //17
            "mutant.rules.SA19_gen", //18 
            "mutant.rules.SA21_gen", //29
            "mutant.rules.SA22_gen", //20
            "mutant.rules.SA23_gen", //21
            "mutant.rules.SA24_gen", //22
            "mutant.rules.SA25_gen", //23
            "mutant.rules.SA26_gen", //24
            "mutant.rules.SA27_gen", //25
            "mutant.rules.SA28_gen", //26
            "mutant.rules.SA28bis_gen", //27
            "mutant.rules.SA28ter_gen", //28   
            "mutant.rules.SA29_gen",    //29
            "mutant.rules.SA30_gen", //30
            "mutant.rules.SA37_gen", //28//31   
            "mutant.rules.SA38_gen",    //32
            "mutant.rules.SA39_gen_and_nogen", //33
                                       
            "mutant.rules.VerifyAlertsGenotoxic", //31//34 no go to qsar 13, no go to sa10, cat 8 neg and 1 pos
            
            "mutant.rules.RuleABUnsaturatedAldehyde", //is a,b unsaturated aldehyde Rule 54//35 app13? no go to sa10, yes go to proceed next?
            "mutant.rules.UserInputABUnsaturatedAldehyde", //56//36 proceed qsar13? no go to sa 10, yes to to qsar 13, no cat 0 yes cat 7
            "mutant.rules.RuleDAMutagenicityABUnsaturatedAldehydes", //QSAR13 Rule 57// 37 both go to sa 10, cat. 4 neg 3 pos
            
            "mutant.rules.SA10_gen", //39// 38  sa10 yes no go next, cat no 0 pos 1 
            
            "mutant.rules.RuleAromaticDiazo", //40//39
            "mutant.rules.RuleDerivedAromaticAmines", //41//40
            "mutant.rules.RuleAromaticAmineNoSulfonicGroup", //is aromatic amine Rule  42/app 8 and 6//41 no go to nongen sa 45, yes go next
            "mutant.rules.UserInputAromaticAmine", //43//42  roceed qsar8 and 6? no go to sa nongen 45, yes to to next, no cat 0 yes cat 7
            "mutant.rules.RuleDACancerogenicityAromaticAmines", //QSAR8  Rule 44// 43 rules next, cat. 6 neg 5 pos
            "mutant.rules.RuleDAMutagenicityAromaticAmines", //QSAR6 Rule 46// 44 rules next, cat. 4 neg 3 pos
            //get here by No alerts for genotoxic carc. branch 
            
  
            "mutant.rules.RuleAlertsNongenotoxicCarcinogenicity", //32//45
            "mutant.rules.SA17_nogen", //33 nongenotoxic//46
            "mutant.rules.SA20_nogen", //34 nongenotoxic//47
            "mutant.rules.SA31a_nogen", //35 nongenotoxic//48
            "mutant.rules.SA31b_nogen", //36 nongenotoxic//49
            "mutant.rules.SA31c_nogen", //37 nongenotoxic//50
            
            "mutant.rules.SA39_gen_and_nogen", //51
            
            "mutant.rules.SA40_nogen", //33 nongenotoxic//52
            "mutant.rules.SA41_nogen", //33 nongenotoxic//53
            "mutant.rules.SA42_nogen", //34 nongenotoxic//54
            "mutant.rules.SA43_nogen", //33 nongenotoxic//55
            "mutant.rules.SA44_nogen", //34 nongenotoxic//56
            "mutant.rules.SA45_nogen", //33 nongenotoxic//57
            "mutant.rules.SA46_nogen", //34 nongenotoxic//58
            "mutant.rules.SA47_nogen", //33 nongenotoxic//59
            "mutant.rules.SA48_nogen", //34 nongenotoxic//60
            "mutant.rules.SA49_nogen", //33 nongenotoxic//61
            "mutant.rules.SA50_nogen", //34 nongenotoxic//62
            "mutant.rules.SA51_nogen", //34 nongenotoxic//63
            "mutant.rules.SA52_nogen", //34 nongenotoxic//64
            "mutant.rules.SA53_nogen", //34 nongenotoxic//65
            "mutant.rules.SA54_nogen", //34 nongenotoxic//66
            "mutant.rules.SA55_nogen", //34 nongenotoxic//67
            "mutant.rules.SA56_nogen", //34 nongenotoxic//68
            
            "mutant.rules.VerifyAlertsNongenotoxic", //38//69 rules 0 0  cat neg 9 pos 2
        
        
         
            
            };
        private final static transient int c_transitions[][] ={
            //{if no go to, if yes go to, assign if no, assign if yes}
        	
            {2,2,0,0}, //Rule 1  1
            
            {3,3,0,0}, //sa1 2
            {4,4,0,0}, //sa2 3
            {5,5,0,0}, //sa3  4
            {6,6,0,0}, //sa4 5
            {7,7,0,0}, //sa5 6
            {8,8,0,0}, //sa6 7
            {9,9,0,0}, //sa7 8
            {10,10,0,0}, //sa8 9
            {11,11,0,0}, //sa9 10
            {12,12,0,0}, //sa11 11
            {13,13,0,0}, //sa12 12
            {14,14,0,0}, //sa13 13 
            {15,15,0,0}, //sa14 14
            {16,16,0,0}, //sa15 15
            {17,17,0,0}, //sa16 16 
            {18,18,0,0}, //sa18 17
            {19,19,0,0}, //sa19 18
            {20,20,0,0}, //sa21 19
            {21,21,0,0}, //sa22 20
            {22,22,0,0}, //sa23 21
            {23,23,0,0}, //sa24 22
            {24,24,0,0}, //sa25 23
            {25,25,0,0}, //sa26 24
            {26,26,0,0}, //sa27 25
            {27,27,0,0}, //sa28 26
            {28,28,0,0}, //sa28bis 27
            {29,29,0,0}, //sa28ter 28
            {30,30,0,0}, //sa29 29
            {31,31,0,0}, //sa30 30
            {32,32,0,0}, //sa37 31
            {33,33,0,0}, //sa38 32
            {34,34,0,0}, //sa39mix 33
                        
            {35,38,8,1}, //counter non gen alert senza sa10 34
            //no go to qsar 13 next, yes go to sa10 38, cat 8 neg and 1 pos
            
                        
            {38,36,0,0}, //app Q13? 35
            //no go to sa10, yes go to proceed next
            
            {38,37,7,0}, //proceed Q13? 36
            //no go to sa 10, yes to to qsar 13, no cat 0 yes cat 7
                        
            {38,38,4,3}, //Q13 37
            //both go to sa 10, cat. 4 neg 3 pos
            
            {39,39,0,1}, //sa110 38
            //yes no go next, cat no 0 pos 1 
            
            {40,40,0,0}, // 39   aN=Na  - if yes will be split into ar amines, otherwise will work with the original compound 41
            {41,41,0,0}, //40  Rule  (ii)    
            
            {45,42,0,0}, //41  qsar 8 and 6 app? ar amine? 
            //no go to nongen sa 46, yes go next
            
            {45,43,7,0}, // 42 user input proceed?
            //no go to sa nongen 46, yes to to next, no cat 0 yes cat 7?
            
            {44,44,6,5}, //43 QSAR8 //Yes (>threshold)- carcinogen,No - not a carcinogen/next, cat. 6 neg 5 pos
            
            {45,45,4,3}, //44 QSAR6 //rules next, cat. 4 neg 3 pos
            
            
            
            {46,46,0,0}, //nongenotoxic alerts 45
            {47,47,0,0}, //sa17 46
            {48,48,0,0}, //sa20 47
            {49,49,0,0}, //sa31a 48
            
            
            {50,50,0,0}, //sa31b 49
            {51,51,0,0}, //sa31c 50
            {52,52,0,0}, //sa39mixed 51
            
            {53,53,0,0}, //sa40 52
            {54,54,0,0}, //sa41 53
            {55,53,0,0}, //sa42 54
            {56,56,0,0}, //sa43 55
            {57,57,0,0}, //sa44 56
            {58,58,0,0}, //sa45 57
            {59,59,0,0}, //sa46 58
            {60,60,0,0}, //sa47 59
            {61,61,0,0}, //sa48 60
            {62,62,0,0}, //sa49 61
            {63,63,0,0}, //sa50 62
            {64,64,0,0}, //sa51 63
            {65,65,0,0}, //sa52 64
            {66,66,0,0}, //sa53 65
            {67,67,0,0}, //sa54 66
            {68,68,0,0}, //sa55 67
            {69,69,0,0}, //sa56 68
            
            {0,0,9,2}, //any nongen alert? 69
            
          

            
        };	
	
		private final static transient String c_categories[] ={
			"mutant.categories.CategoryPositiveAlertGenotoxic", //1 -1
			"mutant.categories.CategoryPositiveAlertNongenotoxic", //2 -2
			//"mutant.categories.CategoryNoAlert",		//3 
			"mutant.categories.CategoryMutagenTA100",	//4 -3
			"mutant.categories.CategoryNonMutagen",	//5 -4
			"mutant.categories.CategoryCarcinogen",		//6  -5
			"mutant.categories.CategoryNotCarcinogen", //7 -6
			"mutant.categories.QSARApplicable",	//8 -7
			"mutant.categories.CategoryNoGenotoxicAlert", //9 -8
			"mutant.categories.CategoryNoNongenotoxicAlert", //10 -9
			"mutant.categories.CategoryError"//10 -10
		};
	/**
	 * 
	 */
	public BB_CarcMutRules() throws DecisionMethodException {
		super(new CategoriesList(c_categories,true),c_rules,c_transitions,new DecisionNodesFactory(true));
        //getRule(53).setID("SA10a");
		setChanged();
		notifyObservers();
		setTitle("Carcinogenicity (genotox and nongenotox) and mutagenicity rulebase by ISS");
        setPriority(6);
        setFalseIfRuleNotImplemented(false);
        
        //setFalseIfRuleNotImplemented(false); //this will cause exception if an error occurs in a rule
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
    
	public DescriptorSpecification getSpecification() {
        return new DescriptorSpecification(
                "http://toxtree.sourceforge.net/mutant.html",
                getTitle(),
                this.getClass().getName(),                
                "Toxtree plugin");
	}    
	public StringBuffer explainRules(IDecisionResult result,boolean verbose) throws DecisionMethodException{
		try {
			StringBuffer b = result.explain(verbose);
			return b;
		} catch (DecisionResultException x) {
			throw new DecisionMethodException(x);
		}
	}


	@Override
	public IDecisionResult createDecisionResult() {
		IDecisionResult result =  new MutantTreeResult();
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
	public void setEditable(boolean value) {
		editable = value;
		for (int i=0;i<rules.size();i++)
			rules.getRule(i).setEditable(value);
	}


    @Override
    public void setParameters(IAtomContainer mol) {
        if (getInteractive()) {
            JComponent c = optionsPanel(mol);
            if (c != null)
                JOptionPane.showMessageDialog(null,c,getTitle(),JOptionPane.PLAIN_MESSAGE);
        } 
    }
}


