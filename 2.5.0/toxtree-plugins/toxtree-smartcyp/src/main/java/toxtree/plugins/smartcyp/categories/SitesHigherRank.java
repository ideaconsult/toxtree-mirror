package toxtree.plugins.smartcyp.categories;

import toxTree.tree.DefaultCategory;
import toxtree.plugins.smartcyp.SMARTCypTreeResult;

public class SitesHigherRank extends DefaultCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1866885340195239907L;
	public SitesHigherRank() {
		super(String.format(SMARTCypTreeResult.FORMAT, ">=",4, "sites"), 4);
		setExplanation("SMARTCyp higher rank sites of metabolism");
		setThreshold("");
	}
}
