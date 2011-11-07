package toxtree.plugins.dnabinding.categories;

import toxTree.tree.DefaultCategory;

/*
 * Parent class for all skin sens categories
 */
public class DNABindingCategory extends DefaultCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4309054614893919949L;
	public DNABindingCategory(DNABindingAlerts alert) {
		super(alert.getCategoryTitle(),alert.ordinal()+1);
        setExplanation(alert.getCategoryExplanation());
	}
}
