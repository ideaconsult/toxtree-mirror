package toxtree.plugins.lewis.rules;

public class Rule_areadepthratio extends Rule_high_AreaDepthRatio {
	public Rule_areadepthratio() {
		super();
		id = "Area/depth^2 > 1.735";
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
		setTitle("Area/depth^2 > 1.735");
		explanation = new StringBuffer();
		explanation.append("Medium Area/depth^2 > 1.735 "
				);
		examples[0] = ""; 
		examples[1] = ""; 
		editable = false;
	}
}
