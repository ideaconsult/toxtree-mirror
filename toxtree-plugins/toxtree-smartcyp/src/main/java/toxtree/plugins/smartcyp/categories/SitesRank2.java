package toxtree.plugins.smartcyp.categories;

import toxTree.tree.DefaultCategory;
import toxtree.plugins.smartcyp.SMARTCypTreeResult;

public class SitesRank2 extends DefaultCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6219341311785787592L;

	public SitesRank2() {
		super(String.format(SMARTCypTreeResult.FORMAT, "",2, "sites"), 2);
		setExplanation("SMARTCyp secondary sites of metabolism found");
		setThreshold("");
	}
	
	@Override
	public CategoryType getCategoryType() {
		return CategoryType.InconclusiveCategory;
	}

}
