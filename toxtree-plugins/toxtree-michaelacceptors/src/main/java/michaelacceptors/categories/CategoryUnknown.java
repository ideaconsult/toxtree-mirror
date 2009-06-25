package michaelacceptors.categories;

import toxTree.tree.DefaultCategory;

public class CategoryUnknown extends DefaultCategory {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 0;

	/**
	 * 
	 */
	public CategoryUnknown() {
		super("Unknown",2);
		setExplanation("Unable to classify");

	}
}
