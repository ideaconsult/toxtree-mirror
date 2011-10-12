package toxtree.plugins.moa.categories;

import toxTree.tree.DefaultCategory;

public class CategoryUnknown extends DefaultCategory {
	protected static final String title="Unknown";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1960597169224055722L;

	public CategoryUnknown() {
		super(title,8);
		setExplanation(title);

	}
}
