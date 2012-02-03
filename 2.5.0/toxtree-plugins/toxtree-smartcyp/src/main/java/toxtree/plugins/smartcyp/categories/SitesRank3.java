package toxtree.plugins.smartcyp.categories;

import toxTree.tree.DefaultCategory;
import toxtree.plugins.smartcyp.SMARTCypTreeResult;

public class SitesRank3 extends DefaultCategory  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4437712178229753247L;

	public SitesRank3() {
		super(String.format(SMARTCypTreeResult.FORMAT, "",3, "sites"), 3);
		setExplanation("SMARTCyp tertiary sites of metabolism found");
		setThreshold("");
	}
}
