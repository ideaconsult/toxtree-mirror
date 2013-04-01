package sicret.categories;

import toxTree.tree.ToxicCategory;

/**
 * Assigned when compound is estimated to be irritating or corrosive to skin.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public class CategoryIrritatingOrCorrosive extends ToxicCategory {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 0;

	/**
	 * 
	 */
	public CategoryIrritatingOrCorrosive() {
		super("Irritating or Corrosive to skin",6);
		setExplanation("Irritating or Corrosive to skin (R34 or R35 or R38)");
		
		

	}
}

