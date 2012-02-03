package toxtree.plugins.lewis.rules;

import toxTree.tree.rules.RuleDescriptorRange;

public class Rule_lowVolume extends RuleDescriptorRange {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7013842756507774971L;

	public Rule_lowVolume() {
		super();
		id = "Volume < 146.5";
		//setDescriptor(new PKASmartsDescriptor());
		setMaxValue(146.5);
		setMinValue(Double.MIN_VALUE);
		/*
		try {
			getDescriptor().setParameters(params);
		} catch (CDKException x) {
			logger.error(x);
		}
		*/
		setTitle("Low Volume");
		explanation = new StringBuffer();
		explanation.append("Volume <= 146.5");
		examples[0] = ""; 
		examples[1] = ""; 
		editable = false;
	}
}
