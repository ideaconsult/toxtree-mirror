package toxtree.plugins.search;

import java.util.Observable;

import toxTree.tree.CategoriesList;
import toxTree.tree.DecisionNodesList;
import toxTree.tree.UserDefinedTree;

public class CompoundLookup extends UserDefinedTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2673316974202532787L;
	
	public final static transient String[] c_rules = { 
		"toxtree.plugins.search.rules.Find",  
	};
	private final static transient String c_categories[] ={
		"toxtree.plugins.search.categories.Found",
		"toxtree.plugins.search.categories.NotFound"
	};	
	private final static transient int c_transitions[][] ={
		//{if no go to, if yes go to, assign if no, assign if yes}
		{0,0,2,1} //Q1
	};
	
	public CompoundLookup() {
		super(new CategoriesList(c_categories),null);
		rules = new DecisionNodesList(categories,c_rules,c_transitions);
		if (rules instanceof Observable) ((Observable)rules).addObserver(this);
		setTitle("Compound lookup");
		
		setChanged();
		notifyObservers();
        setPriority(Integer.MAX_VALUE);		
	}
}
