package toxtree.plugins.kroes.rules;

import toxTree.tree.rules.RuleVerifyProperty;

/**
 * 
 * @author nina
 *
 */
public class RuleVerifyIntake extends RuleVerifyProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1145230849647499084L;
	public RuleVerifyIntake() {
		super();
		propertyName = "DailyIntake";
		propertyUnits = "\u00B5g/day";
		condition = condition_higher;
		setTitle(getCaption());
		setInteractive(true);
		
	}
	
}
