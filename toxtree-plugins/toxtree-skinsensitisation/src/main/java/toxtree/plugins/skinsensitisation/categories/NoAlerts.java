package toxtree.plugins.skinsensitisation.categories;


/**
 * No alerts
 * @author nina
 *
 */
public class NoAlerts extends SkinSensitisationCategory {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6055850368701920221L;
	public NoAlerts() {
		super(SkinSensitisationAlerts.NO_ALERTS);
	}
	
	@Override
	public CategoryType getCategoryType() {
		return CategoryType.NontoxicCategory;
	}
	@Override
	public CategoryType getNegativeCategoryType() {
		return CategoryType.ToxicCategory;
	}
}
