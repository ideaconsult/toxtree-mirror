package michaelacceptors;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionRule;
import toxTree.exceptions.DecisionResultException;
import toxTree.tree.DecisionNode;
import toxTree.tree.TreeResult;
import toxTree.tree.rules.RuleInitAlertCounter;
import toxTree.tree.rules.RuleVerifyAlertsCounter;

public class MATreeResult extends TreeResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1467549068891674646L;

	public void addRuleResult(IDecisionRule rule, boolean value, IAtomContainer molecule)
	throws DecisionResultException {
			super.addRuleResult(rule, value,molecule);
			if  ((rule instanceof RuleInitAlertCounter) && ! (rule instanceof RuleVerifyAlertsCounter))
				setSilent(true);
			else setSilent((rule instanceof DecisionNode) &&	
					(
				(((DecisionNode)rule).getRule() instanceof RuleInitAlertCounter) 
					&&
					! (((DecisionNode)rule).getRule() instanceof RuleVerifyAlertsCounter)
				)
				);
				
					
	}
}
