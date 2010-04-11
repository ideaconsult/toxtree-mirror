package toxtree.plugins.smartcyp.categories;

import toxTree.tree.DefaultCategory;

public class SitesHigherRank extends DefaultCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1866885340195239907L;
	public SitesHigherRank() {
		super("Sites of Rank>3", 4);
		setExplanation("SMARTCyp Rank4 or higher sites found");
		setThreshold("");
	}
}
