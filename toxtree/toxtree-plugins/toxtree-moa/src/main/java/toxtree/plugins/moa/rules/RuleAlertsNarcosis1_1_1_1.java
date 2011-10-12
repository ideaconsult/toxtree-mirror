package toxtree.plugins.moa.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;

public class RuleAlertsNarcosis1_1_1_1 extends RuleSMARTSSubstructureAmbit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2841543622296627838L;
	/**
	 * 
	 */
	private static final String name = "Aldehyde.Carbon atom of aldehyde connected to a quaternary ammonium"; 
	protected Object[][] smarts = {
			{name,
				"[N+X4][CH1](=O)"
				,Boolean.TRUE},
	};		
	
	public RuleAlertsNarcosis1_1_1_1() {
		super();
		id = "1.1.1";
		setTitle(name);
		setContainsAllSubstructures(false);
		for (Object[] smart: smarts) try { 
			addSubstructure(smart[0].toString(),smart[1].toString(),!(Boolean) smart[2]);
		} catch (Exception x) {}
		
		examples[0] = "CCC(=O)";  //epoxide C1OC1 , peroxide  X-O-O-X
		examples[1] = "[N+]C=O";
		editable = false;
	}
	@Override
	public boolean verifyRule(IAtomContainer mol) throws DecisionMethodException {
		return verifyRule(mol,null);
	}

	public boolean isImplemented() {
		return true;
	}	
}
