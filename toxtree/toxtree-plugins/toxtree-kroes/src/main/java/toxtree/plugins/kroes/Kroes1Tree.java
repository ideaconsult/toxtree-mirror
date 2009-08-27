
package toxtree.plugins.kroes;

import java.util.Observable;

import toxTree.core.IDecisionCategories;
import toxTree.core.IDecisionRuleList;
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

    public Kroes1Tree(IDecisionCategories arg0, IDecisionRuleList arg1)
    {
        super(arg0, arg1);
    }


    private static final long serialVersionUID = 0x4b97b63a593def3bL;
    public static final transient String c_rules[] = {
        "toxtree.plugins.kroes.rules.RuleKroesFig1Q1",
        "toxtree.plugins.kroes.rules.KroesRule2",
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
    };
    private static final transient int c_transitions[][] = {
        {2, 0, 0, 3}, //q1
        {5, 3, 0, 0}, //q2
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
        
    };
    private static final transient String c_categories[] = {
    	"toxtree.plugins.kroes.categories.NotASafetyConcern", //1
        "toxtree.plugins.kroes.categories.NegligibleRisk", //2
        "toxtree.plugins.kroes.categories.RequireCompoundSpecificToxicityData",  //3
    };

}
