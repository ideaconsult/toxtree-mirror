package toxtree.plugins.skinsensitisation.rules;

import toxTree.tree.rules.RuleInitAlertCounter;

public class RuleSkinSensitisationAlerts extends RuleInitAlertCounter {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5388334940521878939L;

	public RuleSkinSensitisationAlerts() {
		super();
		setID("Skin sensitisation");
		setTitle("Verify structural alerts for Skin sensitisation reactivity domains");
		setExplanation("");
		
	}
	@Override
	public boolean isImplemented() {
		return true;
	}

}
