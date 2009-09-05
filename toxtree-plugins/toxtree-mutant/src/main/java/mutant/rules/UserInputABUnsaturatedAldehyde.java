package mutant.rules;

import toxTree.tree.rules.UserInputRule;


public class UserInputABUnsaturatedAldehyde extends UserInputRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4030450494902430927L;

	public UserInputABUnsaturatedAldehyde() {
		super("<html>For a better assessment of a,b unsaturated aldehyde, QSAR calculation could be applied. Would you like to proceed?<br>Warning: the assessment could be time consuming!</html>");
		setID("Proceed with QSAR13?");
		setSilentvalue(true);
	}

	public void removeListener() {
		this.listener = null;
		
	}

}
