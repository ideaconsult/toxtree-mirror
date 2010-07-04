package toxtree.plugins.smartcyp.categories;

import toxTree.tree.DefaultCategory;
import toxtree.plugins.smartcyp.SMARTCypTreeResult;

public class SitesRank1 extends DefaultCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8169535167365480219L;
	public SitesRank1() {
		super(String.format(SMARTCypTreeResult.FORMAT, "",1, "sites"), 1);
		setExplanation("SMARTCyp primary sites of metabolism found");
		setThreshold("");
	}
}
