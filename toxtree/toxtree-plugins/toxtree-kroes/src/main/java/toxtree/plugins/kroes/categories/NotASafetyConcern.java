package toxtree.plugins.kroes.categories;

import toxTree.tree.DefaultCategory;

/**
 * Substance would not be expected to be a safety concern
 * @author nina
 *
 */
public class NotASafetyConcern extends DefaultCategory {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8551741293575416084L;

	public NotASafetyConcern()
    {
        this("Substance would not be expected to be a safety concern", 1);
    }

    public NotASafetyConcern(String name, int id)
    {
        super(name, id);
    }
}
