package toxtree.plugins.smartcyp.categories;

import toxTree.tree.DefaultCategory;

public class SitesHigherRank extends DefaultCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1866885340195239907L;
	public SitesHigherRank() {
		super("SMARTCyp rank>3 sites of metabolism", 4);
		setExplanation("SMARTCyp higher rank sites of metabolism");
		setThreshold("");
	}
}
