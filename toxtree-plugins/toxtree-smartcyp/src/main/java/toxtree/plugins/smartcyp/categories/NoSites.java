package toxtree.plugins.smartcyp.categories;

import toxTree.tree.DefaultCategory;

public class NoSites extends DefaultCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1866885340195239907L;
	public NoSites() {
		super("No sites", 5);
		setExplanation("No sites");
		setThreshold("");
	}
	
	@Override
	public CategoryType getCategoryType() {
		return CategoryType.hasInconclusiveCategory;
	}
}