package toxtree.plugins.skinsensitisation.categories;

import toxTree.tree.DefaultCategory;

/*
 * Parent class for all skin sens categories
 */
public class SkinSensitisationCategory extends DefaultCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4309054614893919949L;
	public SkinSensitisationCategory(SkinSensitisationAlerts alert) {
		super(alert.getCategoryTitle(),alert.ordinal()+1);
        setExplanation(alert.getCategoryExplanation());
	}
}
