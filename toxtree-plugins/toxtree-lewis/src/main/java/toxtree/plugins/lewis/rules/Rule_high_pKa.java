package toxtree.plugins.lewis.rules;

import toxTree.tree.rules.RuleDescriptorRange;
import ambit2.descriptors.PKASmartsDescriptor;

public class Rule_high_pKa extends RuleDescriptorRange {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6527651750709174697L;

	public Rule_high_pKa() {
		super();
		id = "pKa";
		setDescriptor(new PKASmartsDescriptor());
		setMaxValue(Double.MAX_VALUE);
		setMinValue(7.97);
		/*
		try {
			getDescriptor().setParameters(params);
		} catch (CDKException x) {
			logger.error(x);
		}
		*/
		setTitle("pKa > 7.97");
		explanation = new StringBuffer();
		explanation.append("pKa > 7.97"
				);
		examples[0] = ""; 
		examples[1] = "";  
		editable = false;
	}
}
