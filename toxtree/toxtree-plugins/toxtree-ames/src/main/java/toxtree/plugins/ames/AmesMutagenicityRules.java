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

package toxtree.plugins.ames;

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
import toxtree.plugins.ames.categories.CategoryCarcinogen;
import toxtree.plugins.ames.categories.CategoryError;
import toxtree.plugins.ames.categories.CategoryMutagenTA100;
import toxtree.plugins.ames.categories.CategoryNoGenotoxicAlert;
import toxtree.plugins.ames.categories.CategoryNoNongenotoxicAlert;
import toxtree.plugins.ames.categories.CategoryNonMutagen;
import toxtree.plugins.ames.categories.CategoryNotCarcinogen;
import toxtree.plugins.ames.categories.CategoryPositiveAlertGenotoxic;
import toxtree.plugins.ames.categories.CategoryPositiveAlertNongenotoxic;
import toxtree.plugins.ames.categories.QSARApplicable;
import toxtree.plugins.ames.rules.AmesMutagenicityTreeResult;
import toxtree.plugins.ames.rules.RuleABUnsaturatedAldehyde;
import toxtree.plugins.ames.rules.RuleAlertsForGenotoxicCarcinogenicity;
import toxtree.plugins.ames.rules.RuleAlertsNongenotoxicCarcinogenicity;
import toxtree.plugins.ames.rules.RuleAromaticAmineNoSulfonicGroup;
import toxtree.plugins.ames.rules.RuleAromaticDiazo;
import toxtree.plugins.ames.rules.RuleDACancerogenicityAromaticAmines;
import toxtree.plugins.ames.rules.RuleDAMutagenicityABUnsaturatedAldehydes;
import toxtree.plugins.ames.rules.RuleDAMutagenicityAromaticAmines;
import toxtree.plugins.ames.rules.RuleDerivedAromaticAmines;
import toxtree.plugins.ames.rules.SA10_gen;
import toxtree.plugins.ames.rules.SA11_gen;
import toxtree.plugins.ames.rules.SA12_gen;
import toxtree.plugins.ames.rules.SA13_gen;
import toxtree.plugins.ames.rules.SA14_gen;
import toxtree.plugins.ames.rules.SA15_gen;
import toxtree.plugins.ames.rules.SA16_gen;
import toxtree.plugins.ames.rules.SA17_nogen;
import toxtree.plugins.ames.rules.SA18_gen;
import toxtree.plugins.ames.rules.SA19_gen;
import toxtree.plugins.ames.rules.SA1_gen;
import toxtree.plugins.ames.rules.SA20_nogen;
import toxtree.plugins.ames.rules.SA21_gen;
import toxtree.plugins.ames.rules.SA22_gen;
import toxtree.plugins.ames.rules.SA23_gen;
import toxtree.plugins.ames.rules.SA24_gen;
import toxtree.plugins.ames.rules.SA25_gen;
import toxtree.plugins.ames.rules.SA26_gen;
import toxtree.plugins.ames.rules.SA27_gen;
import toxtree.plugins.ames.rules.SA28_gen;
import toxtree.plugins.ames.rules.SA28bis_gen;
import toxtree.plugins.ames.rules.SA28ter_gen;
import toxtree.plugins.ames.rules.SA29_gen;
import toxtree.plugins.ames.rules.SA2_gen;
import toxtree.plugins.ames.rules.SA30_gen;
import toxtree.plugins.ames.rules.SA31a_nogen;
import toxtree.plugins.ames.rules.SA31b_nogen;
import toxtree.plugins.ames.rules.SA31c_nogen;
import toxtree.plugins.ames.rules.SA37_gen;
import toxtree.plugins.ames.rules.SA38_gen;
import toxtree.plugins.ames.rules.SA39_gen_and_nogen;
import toxtree.plugins.ames.rules.SA3_gen;
import toxtree.plugins.ames.rules.SA40_nogen;
import toxtree.plugins.ames.rules.SA41_nogen;
import toxtree.plugins.ames.rules.SA42_nogen;
import toxtree.plugins.ames.rules.SA43_nogen;
import toxtree.plugins.ames.rules.SA44_nogen;
import toxtree.plugins.ames.rules.SA45_nogen;
import toxtree.plugins.ames.rules.SA46_nogen;
import toxtree.plugins.ames.rules.SA47_nogen;
import toxtree.plugins.ames.rules.SA48_nogen;
import toxtree.plugins.ames.rules.SA49_nogen;
import toxtree.plugins.ames.rules.SA4_gen;
import toxtree.plugins.ames.rules.SA50_nogen;
import toxtree.plugins.ames.rules.SA51_nogen;
import toxtree.plugins.ames.rules.SA52_nogen;
import toxtree.plugins.ames.rules.SA53_nogen;
import toxtree.plugins.ames.rules.SA54_nogen;
import toxtree.plugins.ames.rules.SA55_nogen;
import toxtree.plugins.ames.rules.SA56_nogen;
import toxtree.plugins.ames.rules.SA5_gen;
import toxtree.plugins.ames.rules.SA6_gen;
import toxtree.plugins.ames.rules.SA7_gen;
import toxtree.plugins.ames.rules.SA8_gen;
import toxtree.plugins.ames.rules.SA9_gen;
import toxtree.plugins.ames.rules.UserInputABUnsaturatedAldehyde;
import toxtree.plugins.ames.rules.UserInputAromaticAmine;
import toxtree.plugins.ames.rules.VerifyAlertsGenotoxic;
import toxtree.plugins.ames.rules.VerifyAlertsNongenotoxic;

/**
 * 

 * @author nina
 * @modified 04/07/2007
 *
 */
public class AmesMutagenicityRules extends UserDefinedTree implements IDecisionInteractive{
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 0;
    protected boolean residuesIDVisible;
 

        public final static transient String[] c_rules = { 
            RuleAlertsForGenotoxicCarcinogenicity.class.getName(), // Rule  1         
            SA1_gen.class.getName(), //2
            SA2_gen.class.getName(), //3
            SA3_gen.class.getName(), //4
            SA4_gen.class.getName(), //5
            SA5_gen.class.getName(), //6
            SA6_gen.class.getName(), //7
            SA7_gen.class.getName(), //8
            SA8_gen.class.getName(), //9
            SA9_gen.class.getName(), //10
            SA11_gen.class.getName(), //11
            SA12_gen.class.getName(), //12
            SA13_gen.class.getName(), //13
            SA14_gen.class.getName(), //14
            SA15_gen.class.getName(), //15
            SA16_gen.class.getName(), //16
            SA18_gen.class.getName(), //17
            SA19_gen.class.getName(), //18 
            SA21_gen.class.getName(), //29
            SA22_gen.class.getName(), //20
            SA23_gen.class.getName(), //21
            SA24_gen.class.getName(), //22
            SA25_gen.class.getName(), //23
            SA26_gen.class.getName(), //24
            SA27_gen.class.getName(), //25
            SA28_gen.class.getName(), //26
            SA28bis_gen.class.getName(), //27
            SA28ter_gen.class.getName(), //28   
            SA29_gen.class.getName(),    //29
            SA30_gen.class.getName(), //30
            SA37_gen.class.getName(), //28//31   
            SA38_gen.class.getName(),    //32
            SA39_gen_and_nogen.class.getName(), //33
                                       
            VerifyAlertsGenotoxic.class.getName(), //31//34 no go to qsar 13, no go to sa10, cat 8 neg and 1 pos
            
            RuleABUnsaturatedAldehyde.class.getName(), //is a,b unsaturated aldehyde Rule 54//35 app13? no go to sa10, yes go to proceed next?
            UserInputABUnsaturatedAldehyde.class.getName(), //56//36 proceed qsar13? no go to sa 10, yes to to qsar 13, no cat 0 yes cat 7
            RuleDAMutagenicityABUnsaturatedAldehydes.class.getName(), //QSAR13 Rule 57// 37 both go to sa 10, cat. 4 neg 3 pos
            
            SA10_gen.class.getName(), //39// 38  sa10 yes no go next, cat no 0 pos 1 
            
            RuleAromaticDiazo.class.getName(), //40//39
            RuleDerivedAromaticAmines.class.getName(), //41//40
            RuleAromaticAmineNoSulfonicGroup.class.getName(), //is aromatic amine Rule  42/app 8 and 6//41 no go to nongen sa 45, yes go next
            UserInputAromaticAmine.class.getName(), //43//42  roceed qsar8 and 6? no go to sa nongen 45, yes to to next, no cat 0 yes cat 7
            RuleDACancerogenicityAromaticAmines.class.getName(), //QSAR8  Rule 44// 43 rules next, cat. 6 neg 5 pos
            RuleDAMutagenicityAromaticAmines.class.getName(), //QSAR6 Rule 46// 44 rules next, cat. 4 neg 3 pos
            //get here by No alerts for genotoxic carc. branch 
            
  
            RuleAlertsNongenotoxicCarcinogenicity.class.getName(), //32//45
            SA17_nogen.class.getName(), //33 nongenotoxic//46
            SA20_nogen.class.getName(), //34 nongenotoxic//47
            SA31a_nogen.class.getName(), //35 nongenotoxic//48
            SA31b_nogen.class.getName(), //36 nongenotoxic//49
            SA31c_nogen.class.getName(), //37 nongenotoxic//50
            
            SA39_gen_and_nogen.class.getName(), //51
            
            SA40_nogen.class.getName(), //33 nongenotoxic//52
            SA41_nogen.class.getName(), //33 nongenotoxic//53
            SA42_nogen.class.getName(), //34 nongenotoxic//54
            SA43_nogen.class.getName(), //33 nongenotoxic//55
            SA44_nogen.class.getName(), //34 nongenotoxic//56
            SA45_nogen.class.getName(), //33 nongenotoxic//57
            SA46_nogen.class.getName(), //34 nongenotoxic//58
            SA47_nogen.class.getName(), //33 nongenotoxic//59
            SA48_nogen.class.getName(), //34 nongenotoxic//60
            SA49_nogen.class.getName(), //33 nongenotoxic//61
            SA50_nogen.class.getName(), //34 nongenotoxic//62
            SA51_nogen.class.getName(), //34 nongenotoxic//63
            SA52_nogen.class.getName(), //34 nongenotoxic//64
            SA53_nogen.class.getName(), //34 nongenotoxic//65
            SA54_nogen.class.getName(), //34 nongenotoxic//66
            SA55_nogen.class.getName(), //34 nongenotoxic//67
            SA56_nogen.class.getName(), //34 nongenotoxic//68
            
            VerifyAlertsNongenotoxic.class.getName(), //38//69 rules 0 0  cat neg 9 pos 2
        
        
         
            
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
            {55,55,0,0}, //sa42 54
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
			CategoryPositiveAlertGenotoxic.class.getName(), //1 -1
			CategoryPositiveAlertNongenotoxic.class.getName(), //2 -2
			//CategoryNoAlert",		//3 
			CategoryMutagenTA100.class.getName(),	//4 -3
			CategoryNonMutagen.class.getName(),	//5 -4
			CategoryCarcinogen.class.getName(),		//6  -5
			CategoryNotCarcinogen.class.getName(), //7 -6
			QSARApplicable.class.getName(),	//8 -7
			CategoryNoGenotoxicAlert.class.getName(), //9 -8
			CategoryNoNongenotoxicAlert.class.getName(), //10 -9
			CategoryError.class.getName()//10 -10
		};
	/**
	 * 
	 */
	public AmesMutagenicityRules() throws DecisionMethodException {
		super(new CategoriesList(c_categories,true),c_rules,c_transitions,new DecisionNodesFactory(true));
        //getRule(53).setID("SA10a");
		setChanged();
		notifyObservers();
		setTitle("Ames test mutagenicity by ISS");
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
                "http://toxtree.sourceforge.net/ames.html",
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
		IDecisionResult result =  new AmesMutagenicityTreeResult();
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


