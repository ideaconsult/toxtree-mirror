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
            "mutant.rules.SA1", //2
            "mutant.rules.SA2", //3
            "mutant.rules.SA3", //4
            "mutant.rules.SA4", //5
            "mutant.rules.SA5", //6
            "mutant.rules.SA6", //7
            "mutant.rules.SA7", //8
            "mutant.rules.SA8", //9
            "mutant.rules.SA9", //10
            "mutant.rules.SA11", //11
            "mutant.rules.SA12", //12
            "mutant.rules.SA13", //13
            "mutant.rules.SA14", //14
            "mutant.rules.SA15", //15
            "mutant.rules.SA16", //16
            "mutant.rules.SA18", //17
            "mutant.rules.SA19", //18 
            "mutant.rules.SA21", //29
            "mutant.rules.SA22", //20
            "mutant.rules.SA23", //21
            "mutant.rules.SA24", //22
            "mutant.rules.SA25", //23
            "mutant.rules.SA26", //24
            "mutant.rules.SA27", //25
            "mutant.rules.SA28", //26
            "mutant.rules.SA28bis", //27
            "mutant.rules.SA28ter", //28   
            "mutant.rules.SA29",    //29
            "mutant.rules.SA30", //30
            "mutant.rules.VerifyAlertsGenotoxic", //31
            "mutant.rules.RuleAlertsNongenotoxicCarcinogenicity", //32
            "mutant.rules.SA17", //33 nongenotoxic
            "mutant.rules.SA20", //34 nongenotoxic
            "mutant.rules.SA31a", //35 nongenotoxic
            "mutant.rules.SA31b", //36 nongenotoxic
            "mutant.rules.SA31c", //37 nongenotoxic
            "mutant.rules.VerifyAlertsNongenotoxic", //38
            "mutant.rules.SA10", //39
            "mutant.rules.RuleAromaticDiazo", //40
            "mutant.rules.RuleDerivedAromaticAmines", //41
            "mutant.rules.RuleAromaticAmineNoSulfonicGroup", //is aromatic amine Rule  42
            "mutant.rules.UserInputAromaticAmine", //43
            "mutant.rules.RuleDACancerogenicityAromaticAmines", //QSAR8  Rule 44
            //"mutant.rules.QSAR6Applicable", //Aromatic amine with no nitro group on the ring  Rule 45
            "mutant.rules.RuleDAMutagenicityAromaticAmines", //QSAR6 Rule 46
            //get here by No alerts for genotoxic carc. branch 
            "mutant.rules.RuleAlertsNongenotoxicCarcinogenicity", //47
            "mutant.rules.SA17", //48 nongenotoxic
            "mutant.rules.SA20", //49 nongenotoxic
            "mutant.rules.SA31a", //50 nongenotoxic
            "mutant.rules.SA31b", //51 nongenotoxic
            "mutant.rules.SA31c", //52 nongenotoxic
            "mutant.rules.VerifyAlertsNongenotoxic", //53
            "mutant.rules.RuleABUnsaturatedAldehyde", //is a,b unsaturated aldehyde Rule 54
            
            "mutant.rules.SA10", //55
            //finally, QSAR13
            "mutant.rules.UserInputABUnsaturatedAldehyde", //56
            "mutant.rules.RuleDAMutagenicityABUnsaturatedAldehydes", //QSAR13 Rule 57
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
            {46,32,9,1}, //any alert 31
            //if yes go to nongenotoxic alerts #32, if no, go to nongenotoxic alerts #x
            {33,33,0,0}, //nongenotoxic alerts 32
            {34,34,0,0}, //sa17 33
            {35,35,0,0}, //sa20 34
            {36,36,0,0}, //sa31a 35
            {37,37,0,0}, //sa31b 36 
            {38,38,0,0}, //sa31c 37
            {39,39,10,2}, //any alert 38
            {40,40,0,1}, //SA10 39 if yes assign SA for genotoxic carc. 
            //amines
            {41,41,0,0}, //Rule 40 aN=Na  - if yes will be split into ar amines, otherwise will work with the original compound 41
            {42,42,0,0}, //Rule 41 (ii)     
            {0,43,0,0}, //Rule 42 ar amine 
            {0,44,8,0}, //Rule 43 user input 
            {45,45,6,7}, //Rule 44 QSAR8
            //{0,46,0,0}, //Rule 45 QSAR6 applicable 
            {0,0,5,4}, //Rule 45 QSAR6 
            
            //No alerts for genotoxic carc
            {47,47,0,0}, //nongenotoxic alerts 46
            {48,48,0,0}, //sa17 47
            {49,49,0,0}, //sa20 48
            {50,50,0,0}, //sa31a 49
            {51,51,0,0}, //sa31b 50 
            {52,52,0,0}, //sa31c 51
            {53,39,10,2}, //any alert 52
            
            {39,54,0,0}, //Rule 53 a,b aldehyde; if no  can't be aldehyde and apply QSAR13 , go to check SA10 at the other branch
            {55,55,0,1}, //SA10 54 if yes assign SA for genotoxic carc.;if no, can still be (aromatic) aldehyde and apply QSAR13 
            //come here when SA10 is applied on NO_ALERTS branch (no genotoxic, no non genotoxic alerts). 
            //the only option is to apply sa10 and then qsar13 if a,b unsaturated aldehyde 
            {0,56,8,0}, //Rule 55 user input 
            {40,40,5,4}, //Rule 56 QSAR13 //that's it, the end 

            
        };	
	
		private final static transient String c_categories[] ={
			"mutant.categories.CategoryPositiveAlertGenotoxic", //1
			"mutant.categories.CategoryPositiveAlertNongenotoxic", //2
			//"mutant.categories.CategoryNoAlert",		//3
			"mutant.categories.CategoryMutagenTA100",	//4
			"mutant.categories.CategoryNonMutagen",	//5
			"mutant.categories.CategoryCarcinogen",		//6
			"mutant.categories.CategoryNotCarcinogen", //7
			"mutant.categories.QSARApplicable",	//8
			"mutant.categories.CategoryNoGenotoxicAlert", //9
			"mutant.categories.CategoryNoNongenotoxicAlert"	//10
		};
	/**
	 * 
	 */
	public BB_CarcMutRules() throws DecisionMethodException {
		super(new CategoriesList(c_categories,true),c_rules,c_transitions,new DecisionNodesFactory(true));
        //getRule(53).setID("SA10a");
		setChanged();
		notifyObservers();
		setTitle("Benigni / Bossa rulebase (for mutagenicity and carcinogenicity)");
		setExplanation(
				"Predicts the possibility of carcinogenicity and mutagenicity by discriminant analysis and structural rules. See The Reference guide." 
				);
        setPriority(20);
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


