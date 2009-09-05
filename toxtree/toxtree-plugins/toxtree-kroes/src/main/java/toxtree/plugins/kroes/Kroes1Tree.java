
package toxtree.plugins.kroes;

import java.util.Observable;

import org.openscience.cdk.qsar.DescriptorSpecification;

import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNodesList;
import toxTree.tree.UserDefinedTree;

public class Kroes1Tree extends UserDefinedTree
{

    public Kroes1Tree()
    {
        super(new CategoriesList(c_categories), null);
        rules = new DecisionNodesList(categories, c_rules, c_transitions);
        if(rules instanceof Observable)
            ((Observable)rules).addObserver(this);
        setTitle("Kroes TTC decision tree");
        setChanged();
        notifyObservers();
        setExplanation("Kroes, R., Renwick, A.G., Cheeseman, M., Kleiner, J., Mangelsdorf, I., Piersma, A., Schilter, B., Schlatter, J., van Schothorst, F., Vos, J.G., Würtzen, G. (2004). Structure based thresholds of toxicological concern (TTC): guidance for application to substances present at low levels in the diet. Food Chem. Toxicol. 42, 65–83");
        
    }

 
    private static final long serialVersionUID = 0x4b97b63a593def3bL;
    public static final transient String c_rules[] = {
        "toxtree.plugins.kroes.rules.RuleKroesFig1Q1",
        "mutant.rules.RuleAlertsForGenotoxicCarcinogenicity",
        "toxtree.plugins.kroes.rules.KroesRule3",
        "toxtree.plugins.kroes.rules.KroesRule4",
        "toxtree.plugins.kroes.rules.KroesRule5",
        "toxtree.plugins.kroes.rules.KroesRule6",
        "toxtree.plugins.kroes.rules.KroesRule7",
        "toxtree.plugins.kroes.rules.KroesRule8",
        "toxtree.plugins.kroes.rules.KroesRule9",
        "toxtree.plugins.kroes.rules.KroesRule10",
        "toxtree.plugins.kroes.rules.KroesRule11",
        "toxtree.plugins.kroes.rules.KroesRule12",
        
        "mutant.rules.SA1", //13
        "mutant.rules.SA2", //14
        "mutant.rules.SA3", //15
        "mutant.rules.SA4", //16
        "mutant.rules.SA5", //17
        "mutant.rules.SA6", //18
        "mutant.rules.SA7", //19
        "mutant.rules.SA8", //20
        "mutant.rules.SA9", //21
        "mutant.rules.SA11", //22
        "mutant.rules.SA12", //23
        "mutant.rules.SA13", //24
        "mutant.rules.SA14", //25
        "mutant.rules.SA15", //26
        "mutant.rules.SA16", //27
        "mutant.rules.SA18", //28
        "mutant.rules.SA19", //29 
        "mutant.rules.SA21", //30
        "mutant.rules.SA22", //31
        "mutant.rules.SA23", //32
        "mutant.rules.SA24", //33
        "mutant.rules.SA25", //34
        "mutant.rules.SA26", //35
        "mutant.rules.SA27", //36
        "mutant.rules.SA28", //37
        "mutant.rules.SA28bis", //38
        "mutant.rules.SA28ter", //39   
        "mutant.rules.SA29",    //40
        "mutant.rules.SA30", //41
        "toxtree.plugins.kroes.rules.KroesRule2" //42   
    };
    private static final transient int c_transitions[][] = {
        {2, 0, 0, 3}, //q1
      //  {5, 3, 0, 0}, //q2
        {13, 13, 0, 0}, //q2
        {4, 0, 0, 3}, //q3
        {0, 0, 2, 3}, //q4
        {0, 6, 1, 0}, //q5
        {8, 7, 0, 0}, //q6
        {0, 0, 1, 3}, //q7
        {10, 9, 0, 0}, //q8
        {0, 0, 1, 3}, //q9
        {12, 11, 0, 0}, //q10
        {0, 0, 1, 3}, //q11
        {0, 0, 1, 3}, //q12
        
        {14,14,0,0}, //q13
        {15,15,0,0}, //q14
        {16,16,0,0}, //q15
        {17,17,0,0}, //q16
        {18,18,0,0}, //q17
        {19,19,0,0}, //q18
        
        {20,20,0,0}, //q19
        {21,21,0,0}, //q20
        {22,22,0,0}, //q21
        {23,23,0,0}, //q22
        {24,24,0,0}, //q23
        {25,25,0,0}, //q24
        {26,26,0,0}, //q25
        {27,27,0,0}, //q26
        {28,28,0,0}, //q27
        {29,29,0,0}, //q28
        {30,30,0,0}, //q29

        {31,31,0,0}, //q30
        {32,32,0,0}, //q31
        {33,33,0,0}, //q32
        {34,34,0,0}, //q33
        {35,35,0,0}, //q34
        {36,36,0,0}, //q35
        {37,37,0,0}, //q36
        {38,38,0,0}, //q37
        {39,39,0,0}, //q38
        {40,40,0,0}, //q39
        
        {41,41,0,0}, //q40
        {42,42,0,0}, //q41
        {5,3,0,0}, //q42
        
    };
    private static final transient String c_categories[] = {
    	"toxtree.plugins.kroes.categories.NotASafetyConcern", //1
        "toxtree.plugins.kroes.categories.NegligibleRisk", //2
        "toxtree.plugins.kroes.categories.RequireCompoundSpecificToxicityData",  //3
    };
	public DescriptorSpecification getSpecification() {
        return new DescriptorSpecification(
                "http://toxtree.sourceforge.net/ttc.html",
                getTitle(),
                this.getClass().getName(),                
                "Toxtree plugin");
	}
	


}
