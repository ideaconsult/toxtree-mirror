package toxtree.plugins.lewis;
import java.util.Observable;

import org.openscience.cdk.qsar.DescriptorSpecification;

import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNodesList;
import toxTree.tree.UserDefinedTree;


/**
<pre>
derived by Weka from table2. data Lewis, "Human Cytochrome P450 Substrate Selectivity and characteristics"

vol <= 227.83  
|   vol <= 146.5: CYP2E1 (7.0/1.0)  ////rule low volume
|   vol > 146.5  //medium
|   |   a_d2 <= 2.244: CYP2A6 (7.0/2.0)
|   |   a_d2 > 2.244: CYP1A2 (7.0/1.0)
vol > 227.83  //rule high volume
|   logP <= 0.9: CYP2B6 (3.0)
|   logP > 0.9
|   |   pKa <= 7.97
|   |   |   vol <= 291.99: CYP2C (10.0/2.0)
|   |   |   vol > 291.99: CYP3A4 (3.0)
|   |   pKa > 7.97
|   |   |   a_d2 <= 1.735: CYP2D6 (5.0/1.0)
|   |   |   a_d2 > 1.735: CYP2B6 (6.0/4.0)
</pre>
 * @author nina
 *
 */
public class LewisTree extends UserDefinedTree {

    public LewisTree()
    {
        super(new CategoriesList(c_categories), null);
        rules = new DecisionNodesList(categories, c_rules, c_transitions);
        if(rules instanceof Observable)
            ((Observable)rules).addObserver(this);
        setTitle("Human Cytochrome P450 Substrate Selectivitys");
        setChanged();
        notifyObservers();
        setExplanation("Lewis, Human Cytochrome P450 Substrate Selectivity and characteristics,");
    }    
    
    private static final long serialVersionUID = 0x4b97b63a593def3bL;
    public static final transient String c_rules[] = {
        "toxtree.plugins.lewis.rules.Rule_highVolume",  //1 vol > 227.83  //rule high volume
        "toxtree.plugins.lewis.rules.Rule_lowVolume", //2
        "toxtree.plugins.lewis.rules.Rule_high_AreaDepthRatio",//3
        "toxtree.plugins.lewis.rules.Rule_highLogP", //logp > 0.9
        "toxtree.plugins.lewis.rules.Rule_high_pKa",
        "toxtree.plugins.lewis.rules.Rule_volume",
        "toxtree.plugins.lewis.rules.Rule_areadepthratio"
    };
    private static final transient int c_transitions[][] = {
        {2, 4, 0, 0}, //q1 vol
        {0, 3, 6, 0}, //q2 vol
        {0, 0, 1, 2}, //q3 a/d2
        {0, 5, 3, 0}, //q4 logp
        {6, 7, 0, 0}, //q5 pka
        {0, 0, 4, 7}, //q6 vol
        {0, 0, 5, 3}, //q7 a/d2
    };
    private static final transient String c_categories[] = {
    	"toxtree.plugins.lewis.categories.CYP1A2", //1
    	"toxtree.plugins.lewis.categories.CYP2A", //1
    	"toxtree.plugins.lewis.categories.CYP2B", //1
    	"toxtree.plugins.lewis.categories.CYP2C", //1
    	"toxtree.plugins.lewis.categories.CYP2D", //1
    	"toxtree.plugins.lewis.categories.CYP2E", //1
    	"toxtree.plugins.lewis.categories.CYP3A4", //1

    };
	public DescriptorSpecification getSpecification() {
        return new DescriptorSpecification(
                "http://toxtree.sourceforge.net/ttc.html",
                getTitle(),
                this.getClass().getName(),                
                "Toxtree plugin");
	}
	


}