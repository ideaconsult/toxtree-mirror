package toxtree.plugins.proteinbinding.categories;


/**
 * No alerts
 * @author nina
 *
 */
public class NoAlerts extends ProteinBindingCategory {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6055850368701920221L;
	public NoAlerts() {
		super(ProteinBindingAlerts.NO_ALERTS);
	}
	
	@Override
	public CategoryType getCategoryType() {
		return CategoryType.hasNontoxicCategory;
	}
}
