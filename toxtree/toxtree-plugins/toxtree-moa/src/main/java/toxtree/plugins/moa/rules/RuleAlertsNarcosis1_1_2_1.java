package toxtree.plugins.moa.rules;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.exceptions.DecisionMethodException;
import toxTree.tree.rules.smarts.RuleSMARTSSubstructureAmbit;

public class RuleAlertsNarcosis1_1_2_1 extends RuleSMARTSSubstructureAmbit{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8928684416446264772L;
	private static final String name = "Aniline.With acetophenone or benzamide substructures on aniline ring"; 
	protected Object[][] smarts = {
			{"1","c1cccc(C(=O)C)c1N",Boolean.TRUE},
			{"2","c1ccc(C(=O)C)cc1N",Boolean.TRUE},
			{"3","c1cc(C(=O)C)ccc1N",Boolean.TRUE},
	};		
	
	public RuleAlertsNarcosis1_1_2_1() {
		super();
		id = "1.2.1";
		setTitle(name);
		setContainsAllSubstructures(false);
		for (Object[] smart: smarts) try { 
			addSubstructure(smart[0].toString(),smart[1].toString(),!(Boolean) smart[2]);
		} catch (Exception x) {}
		
		examples[0] = "c1ccccc1N";  //epoxide C1OC1 , peroxide  X-O-O-X
		examples[1] = "c1ccc(C(=O)C)cc1N";
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
