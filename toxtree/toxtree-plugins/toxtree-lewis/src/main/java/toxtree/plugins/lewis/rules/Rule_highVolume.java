package toxtree.plugins.lewis.rules;

import toxTree.tree.rules.RuleDescriptorRange;

public class Rule_highVolume extends RuleDescriptorRange {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4957022400807558093L;

	public Rule_highVolume() {
		super();
		id = "Volume>227.83";
		//setDescriptor(new PKASmartsDescriptor());
		setMaxValue(Double.MAX_VALUE);
		setMinValue(227.83);
		/*
		try {
			getDescriptor().setParameters(params);
		} catch (CDKException x) {
			logger.error(x);
		}
		*/
		setTitle("Medium Volume");
		explanation = new StringBuffer();
		explanation.append("Volume > 227.83");
		examples[0] = ""; 
		examples[1] = ""; 
		editable = false;
	}
}
