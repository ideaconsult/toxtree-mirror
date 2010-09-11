package toxtree.plugins.lewis.rules;

import toxTree.tree.rules.RuleDescriptorRange;

public class Rule_high_AreaDepthRatio extends RuleDescriptorRange {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8576568085110630524L;

	public Rule_high_AreaDepthRatio() {
		super();
		id = "a/d2";
		//setDescriptor(new PKASmartsDescriptor());
		setMaxValue(Double.MAX_VALUE);
		setMinValue(2.244);
		/*
		try {
			getDescriptor().setParameters(params);
		} catch (CDKException x) {
			logger.error(x);
		}
		*/
		setTitle("Area/depth^2 > 2.244");
		explanation = new StringBuffer();
		explanation.append("High Area/depth^2 > 2.244 "
				);
		examples[0] = ""; 
		examples[1] = ""; 
		editable = false;
	}
}
