package toxtree.plugins.lewis.rules;

public class Rule_volume extends Rule_highVolume {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4957022400807558093L;

	public Rule_volume() {
		super();
		id = "Volume > 291.99";
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
		setTitle("High Volume");
		explanation = new StringBuffer();
		explanation.append("Volume > 291.99");
		examples[0] = ""; 
		examples[1] = ""; 
		editable = false;
	}
}
